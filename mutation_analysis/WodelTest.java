package tdl.mutation.testing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.core.JavaCore;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TDLan2StandaloneSetup;
import org.etsi.mts.tdl.TestDescription;
import org.imt.tdl.runner.Failure;
import org.imt.tdl.runner.Result;
import org.imt.tdl.runner.TDLCore;

import com.google.inject.Injector;

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
	String seedModelPath;
	
	List<Package> testPackages = new ArrayList<>();
	Result seedModelTestVerdict;
	
	//keep the mutation testing result
	int numOfGeneratedMutants;
	int numOfKilledMutants;
	
	@Override
	public String getProjectName() {
		return "XArduinoMutation";
	}

	@Override
	public String getNatureId() {
		return JavaCore.NATURE_ID;
	}
	
	@Override
	public void compile(IProject project) {
	}

	@Override
	public List<String> artifactPaths(IProject project, String projectPath, File outputFolder, List<String> blockNames) {
		List<String> artifactPaths = new ArrayList<String>();
		File projectFolder = new File(projectPath);
		for (File file : projectFolder.listFiles()) {
			artifactPathsHelper(project.getName(), file, artifactPaths);
		}
		numOfGeneratedMutants = artifactPaths.size();
		return artifactPaths;
	}
	
	private void artifactPathsHelper(String projectName, File file, List<String> artifactPaths) {
		if (file.isFile() && file.getName().endsWith(".model")) {
			String filePath = file.getPath();
			if (workspacePath == null) {
				workspacePath = filePath.substring(0, filePath.indexOf(projectName)-1);
			}		
			//get the relative path of the file
			filePath = filePath.replace(workspacePath, "");
			if (!file.getName().contains("Output")) {//file is the seed model
				seedModelPath = filePath;
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
	public WodelTestGlobalResult run(IProject project, IProject testSuiteProject, String artifactPath) {
		WodelTestGlobalResult globalResult = new WodelTestGlobalResult();
		if (testPackages.isEmpty()) {
			loadTestSuite(testSuiteProject);
		}	
		for (Package testPackage : testPackages) {
			seedModelTestVerdict =  (new TDLCore()).run(testPackage, seedModelPath);
			runTest(globalResult, project, testPackage, artifactPath);
			if (globalResult.getStatus() != Status.OK) {
				break;
			}	
		}
		return globalResult;
	}

	private void runTest(WodelTestGlobalResult globalResult, IProject project, Package testPackage, String artifactPath) {
		List<WodelTestResultClass> results = globalResult.getResults();
		List<WodelTestInfo> testsInfo = new ArrayList<WodelTestInfo>();

		TDLCore tdlCore = new TDLCore();
		Result mutantTestVerdict = tdlCore.run(testPackage, artifactPath);
		
		List<Failure> mutantFailures  = mutantTestVerdict.getFailures();
		if (mutantFailures.size() == 0) {
			WodelTestInfo info = new WodelTestInfo(testPackage.getName(), false, testPackage.getName(), EQUALS);
			testsInfo.add(info);
		}else {
			for (Failure failure : mutantFailures) {
				String message = failure.getMessage() != null ? failure.getMessage().replaceAll("\n", "-") : DIFFERENT;
				WodelTestInfo info = new WodelTestInfo(failure.getFailedTestName(), true, failure.getTestHeader(), message);
				testsInfo.add(info);
			}
		}
		Map<String, Boolean> test_mutationValue = new HashMap<>();
		for (Entry<String, Boolean> entry:mutantTestVerdict.getTests_verdicts().entrySet()) {
			test_mutationValue.put(entry.getKey(), !entry.getValue());
		}
		
		String mutantAbsolutePath = (workspacePath + artifactPath).replaceAll("\\\\", "/");
		String testSuitePath = testPackage.eResource().getURI().toString();
		testSuitePath = testSuitePath.replace("platform:/resource", workspacePath).replaceAll("\\\\", "/");
		WodelTestResult wtr = new WodelTestResult(testPackage.getName(), testSuitePath, test_mutationValue, testsInfo);
		globalResult.incNumTestsExecuted(mutantTestVerdict.getRunCount());
		globalResult.incNumTestsFailed(mutantTestVerdict.getFailureCount());
		globalResult.incNumTestsError(wtr.getErrorCount());
		WodelTestResultClass resultClass = WodelTestResultClass.getWodelTestResultClassByName(results, mutantAbsolutePath);
		if (resultClass == null) {
			resultClass = new WodelTestResultClass(mutantAbsolutePath);
			results.add(resultClass);
		}
		resultClass.addResult(wtr);
		globalResult.setStatus(Status.OK);
	}
	
	private void loadTestSuite(IProject testSuiteProject) {
		Injector injector = new TDLan2StandaloneSetup().createInjectorAndDoEMFRegistration();
		ResourceSet resSet = injector.getInstance(ResourceSet.class);
		String testSuitePath = ModelManager.getWorkspaceAbsolutePath() + "/" + testSuiteProject.getName();
		String testResourcePath = "platform:/resource/"+ testSuiteProject.getName() + "/";
		File testsFolder = new File(testSuitePath);
		for (File testFile : testsFolder.listFiles()) {
			testPackageHelper(resSet, testResourcePath, testFile);
		}
		EcoreUtil.resolveAll(resSet);
	}
	
	private void testPackageHelper(ResourceSet resSet, String testResourcePath, File testFile) {
		if (testFile.isFile() && testFile.getName().endsWith(".tdlan2")) {
			String testFilePath = testResourcePath + testFile.getName();
			Resource testRes = resSet.getResource(URI.createURI(testFilePath), true);
			org.etsi.mts.tdl.Package testPackage = (Package) testRes.getContents().get(0);
			for (Object element: testPackage.getPackagedElement()) {
				if (element instanceof TestDescription) {
					testPackages.add(testPackage);
				}
			}			
		}
		else if (testFile.isDirectory()){
			testResourcePath += (testFile.getName() + "/");
			for (File innerFile : testFile.listFiles()) {
				testPackageHelper(resSet, testResourcePath, innerFile);
			}
		}
	}

	@Override
	public void projectToModel(String projectName, Class<?> cls) {
		String projectPath = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).getLocation().toOSString();
		String targetPath = ModelManager.getMetaModelPath(cls);
		File sourceFolder = new File(projectPath);
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
		String targetPath = ModelManager.getWorkspaceAbsolutePath() + "/" + projectName + "/mutants/" + className + "/" + folderName + "/" + modelName;
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

