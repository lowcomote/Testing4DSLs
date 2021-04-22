package mutator.wodeltest.xFSMMutation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.core.JavaCore;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.tdl.runner.Failure;
import org.imt.tdl.runner.Result;
import org.imt.tdl.runner.TDLCore;

import manager.IOUtils;
import manager.IWodelTest;
import manager.ModelManager;
import manager.WodelTestGlobalResult;
import manager.WodelTestGlobalResult.Status;
import manager.WodelTestInfo;
import manager.WodelTestResult;
import manager.WodelTestResultClass;

public class WodelTest implements IWodelTest {
	
	String workspacePath;
	List<String> seedModels = new ArrayList<>();
	Map<String, Result> seedModelsTestResult = new HashMap<>();
	
	@Override
	public String getProjectName() {
		return "xFSMMutation";
	}

	@Override
	public String getNatureId() {
		return JavaCore.NATURE_ID;
	}
	
	@Override
	public void compile(IProject project) {
	}
	
	private void artifactPathsHelper(String projectName, File file, List<String> artifactPaths) {
		if (file.isFile() && file.getName().endsWith(".model")) {
			String filePath = file.getPath();
			if (this.workspacePath == null) {
				this.workspacePath = filePath.substring(0, filePath.lastIndexOf(projectName)-1);
			}		
			//get the relative path of the file
			filePath = filePath.replace(this.workspacePath, "");
			//get the name of the seed model
			String seedName = filePath.substring(filePath.indexOf("\\model\\") + "\\model\\".length(), filePath.length());
			if (file.getName().equals(seedName)) {//file is the seed model
				this.seedModels.add(filePath);
			}else {
				artifactPaths.add(filePath);
			}			
		}
		else if (file.isDirectory()){
			for (File innerFile : file.listFiles()) {
				artifactPathsHelper(projectName, innerFile, artifactPaths);
			}
		}
	}

	@Override
	public List<String> artifactPaths(IProject project, String projectPath, File outputFolder, List<String> blockNames) {
		List<String> artifactPaths = new ArrayList<String>();
		File projectFolder = new File(projectPath + "\\model");
		for (File file : projectFolder.listFiles()) {
			artifactPathsHelper(project.getName(), file, artifactPaths);
		}
		return artifactPaths;
	}
	
	private void runTest(WodelTestGlobalResult globalResult, IProject project, Package testPackage, String artifactPath) {
		List<WodelTestResultClass> results = globalResult.getResults();
		List<WodelTestInfo> testsInfo = new ArrayList<WodelTestInfo>();
		
		TDLCore tdlCore = new TDLCore();
		String seedModelPath = getSeedModel(artifactPath);
		String seedModel_testcase = seedModelPath + "_" + testPackage.getName();
		if (!this.seedModelsTestResult.containsKey(seedModel_testcase)) {//if the tests are not executed on the seed model
			this.seedModelsTestResult.put(seedModel_testcase, tdlCore.run(testPackage, seedModelPath));
		}
		Result seedTestVerdict = this.seedModelsTestResult.get(seedModel_testcase);
		Result mutantTestVerdict = tdlCore.run(testPackage, artifactPath);
		
		boolean value = true;
		if (seedTestVerdict.equals(mutantTestVerdict)) {
			value = false;//when the tests has passed on the mutated model, the mutant is live otherwise is killed
		}
		String message = value ? DIFFERENT : EQUALS;//EQUALS if the value is false and so the mutant is live
		String MUTName = artifactPath.substring(artifactPath.lastIndexOf('\\'), artifactPath.length());
		List<Failure> mutantFailures = mutantTestVerdict.getFailures();
		for (Failure failure : mutantFailures) {
			WodelTestInfo info = new WodelTestInfo(failure.getFailedTestName(), value, MUTName, message);
			testsInfo.add(info);
		}
		if (mutantFailures.size() == 0) {
			WodelTestInfo info = new WodelTestInfo(testPackage.getName(), value, MUTName, message);
			testsInfo.add(info);
		}
		String artifactAbsolutePath = (this.workspacePath + artifactPath).replaceAll("\\\\", "/");
		WodelTestResult wtr = new WodelTestResult(testPackage.getName(), artifactAbsolutePath, mutantTestVerdict.getTests(), testsInfo);
		globalResult.incNumTestsExecuted(mutantTestVerdict.getRunCount());
		globalResult.incNumTestsFailed(mutantTestVerdict.getFailureCount());
		globalResult.incNumTestsError(wtr.getErrorCount());
		WodelTestResultClass resultClass = WodelTestResultClass.getWodelTestResultClassByName(results, artifactAbsolutePath);
		if (resultClass == null) {
			resultClass = new WodelTestResultClass(artifactAbsolutePath);
			results.add(resultClass);
		}
		resultClass.addResult(wtr);
		globalResult.setStatus(Status.OK);
	}

	@Override
	public WodelTestGlobalResult run(IProject project, IProject testSuiteProject, String artifactPath) {
		WodelTestGlobalResult globalResult = new WodelTestGlobalResult();
		List<WodelTestResultClass> results = globalResult.getResults();

		String testSuitePath = ModelManager.getWorkspaceAbsolutePath() + "/" + testSuiteProject.getName();
		String testResourcePath = "platform:/resource/"+ testSuiteProject.getName() + "/";
		File testsFolder = new File(testSuitePath);
		List<Package> testPackages = new ArrayList<Package>();
		for (File testFile : testsFolder.listFiles()) {
			testPackageHelper(testResourcePath, testFile, testPackages);
		}		
		for (Package testPackage : testPackages) {
			runTest(globalResult, project, testPackage, artifactPath);
			if (globalResult.getStatus() != Status.OK) {
				break;
			}	
		}
		return globalResult;
	}
	private String getSeedModel (String artifactPath) {
		String seedName = artifactPath.substring(artifactPath.indexOf("\\model\\") + "\\model\\".length(), artifactPath.length());
		seedName = seedName.substring(0, seedName.indexOf("\\"));
		for (String seedModel: this.seedModels) {
			if (seedModel.contains(seedName)) {
				return seedModel;
			}
		}
		return null;
	}
	private Package getTestPackage(String testFilePath) {
		Resource testSuiteRes = (new ResourceSetImpl()).getResource(URI.createURI(testFilePath), true);
		org.etsi.mts.tdl.Package testPackage = (Package) testSuiteRes.getContents().get(0);
		for (Object element: testPackage.getPackagedElement()) {
			if (element instanceof TestDescription) {
				return testPackage;
			}
		}
		return null;
	}
	
	private void testPackageHelper(String testResourcePath, File testFile, List<Package> testPackages) {
		if (testFile.isFile() && testFile.getName().endsWith(".tdlan2")) {
			String testFilePath = testResourcePath + testFile.getName();
			Package testPackage = getTestPackage(testFilePath);
			if (testPackage != null) {
				testPackages.add(testPackage);
			}			
		}
		else if (testFile.isDirectory()){
			testResourcePath += (testFile.getName() + "/");
			for (File innerFile : testFile.listFiles()) {
				testPackageHelper(testResourcePath, innerFile, testPackages);
			}
		}
	}

	@Override
	public void projectToModel(String projectName, Class<?> cls) {
		String projectPath = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).getLocation().toOSString();
		String targetPath = ModelManager.getMetaModelPath(cls);
		File sourceFolder = new File(projectPath + "/model");
		for (File source : sourceFolder.listFiles()) {
			if (source.getName().endsWith(".model")) {
				try {
					IOUtils.copyFile(source, new File(targetPath + "/" + source.getName()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean modelToProject(String className, Resource model, String folderName, String modelName, String projectName, Class<?> cls) {
		String targetPath = ModelManager.getWorkspaceAbsolutePath() + "/" + projectName + "/model/" + className + "/" + folderName + "/" + modelName;
		ModelManager.saveOutModel(model, targetPath);
		return false;
	}

	@Override
	public String getContainerEClassName() {
		return "";
	}

	@Override
	public boolean annotateMutation(Resource model, EObject container, String annotation) {
		return false;
	}
}
