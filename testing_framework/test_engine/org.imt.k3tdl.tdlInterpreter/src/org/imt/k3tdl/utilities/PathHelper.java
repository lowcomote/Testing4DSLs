package org.imt.k3tdl.utilities;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.etsi.mts.tdl.Annotation;
import org.etsi.mts.tdl.ComponentInstance;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlPackage;
import org.etsi.mts.tdl.util.tdlResourceFactoryImpl;

public class PathHelper {

	IFile testSuiteFile;
	Path testSuiteFilePath;
	String testSuiteFileName;
	
	Resource testSuiteResource;
	URI testSuiteURI;
	
	Package testSuite;

	Path workspacePath;
	Path modelsProjectPath;
	Path modelUnderTestPath;
	Resource MUTResource;
	
	String DSLName;
	Path DSLPath;
	
	public PathHelper (IFile testSuiteFile) {
		this.testSuiteFile = testSuiteFile;
		String filePath = testSuiteFile.getFullPath().toString();
		testSuiteURI = URI.createPlatformResourceURI(filePath, true);
		loadTestSuite (testSuiteURI);
		setup();
	}
	
	public PathHelper (Resource testSuiteResource) {
		this.testSuiteResource = testSuiteResource;
		testSuite = (Package) testSuiteResource.getContents().get(0);
		setup();
	}
	
	public PathHelper (Package testSuite) {
		this.testSuite = testSuite;
		testSuiteResource = testSuite.eResource();
		setup();
	}
	
	private void setup() {
		findTestSuiteNameAndPath();
		findModelAndDSLPathOfTestSuite();
		//when the modelUnderTestPath found, find the workspace path
		findWorkspacePath();
	}
	
	public PathHelper (Path modelUnderTestPath) {
		this.modelUnderTestPath = modelUnderTestPath;
		//when the modelUnderTestPath found, find the workspace path
		findWorkspacePath();
	}
	
	public PathHelper() {
		
	}
	
	private void findTestSuiteNameAndPath() {
		testSuiteURI = testSuiteResource.getURI();
		testSuiteFilePath = Paths.get(testSuiteURI.path());
		testSuiteFileName = testSuiteURI.lastSegment();
	}

	public void loadTestSuite (URI testSuiteURI) {
		ResourceSet resSet = new ResourceSetImpl();
		if (testSuiteURI.toString().endsWith(".xmi")) {
			resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
			resSet.getPackageRegistry().put(tdlPackage.eNS_URI, tdlPackage.eINSTANCE);
		} 
		else if (testSuiteURI.toString().endsWith(".model")) {
			resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("model", new XMIResourceFactoryImpl());
			resSet.getPackageRegistry().put(tdlPackage.eNS_URI, tdlPackage.eINSTANCE);
		}
		else if (testSuiteURI.toString().endsWith(".tdl")) {
			resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("tdl", new XMIResourceFactoryImpl());
			resSet.getPackageRegistry().put(tdlPackage.eNS_URI, tdlPackage.eINSTANCE);
		}
		else if (testSuiteURI.toString().endsWith(".tdlan2")) {
			resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(".tdlan2", new tdlResourceFactoryImpl());
			resSet.getPackageRegistry().put(tdlPackage.eNS_URI, tdlPackage.eINSTANCE);
		}
		testSuiteResource = (resSet).getResource(testSuiteURI, true);
		testSuite = (Package) testSuiteResource.getContents().get(0);
	}

	public void findModelAndDSLPathOfTestSuite() {
		TestDescription testCase = testSuite.getPackagedElement().stream().
				filter(p -> p instanceof TestDescription).
				map(p -> (TestDescription) p).
				findFirst().get();
		findModelAndDSLPathOfTestCase(testCase);
	}
	
	public void findModelAndDSLPathOfTestCase(TestDescription testCase) {
		ComponentInstance sutComponent = testCase.getTestConfiguration().getComponentInstance().stream().
				filter(ci -> ci.getRole().toString().equals("SUT")).
				findFirst().get();
		for (Annotation a:sutComponent.getAnnotation()){
			if (a.getKey().getName().equals("MUTPath")){
				modelUnderTestPath = Paths.get(a.getValue().substring(1, a.getValue().length()-1));
			}
			else if (a.getKey().getName().equals("DSLName")) {
				DSLName = a.getValue().substring(1, a.getValue().length()-1);
				DSLPath = findDSLPath (DSLName);
			}
		}
	}
	
	public Path findDSLPath (String DSLName){
		IConfigurationElement language = Arrays
				.asList(Platform.getExtensionRegistry()
						.getConfigurationElementsFor("org.eclipse.gemoc.gemoc_language_workbench.xdsml"))
				.stream().filter(l -> l.getAttribute("xdsmlFilePath").endsWith(".dsl")
						&& l.getAttribute("name").equals(DSLName))
				.findFirst().orElse(null);
		if (language != null) {
			return Paths.get(URI.createPlatformPluginURI(language.getAttribute("xdsmlFilePath"), false).toString());
		}
		return null;
	}
	
	private void findWorkspacePath() {
		IProject mutProject = getModelUnderTestProject();
		String path = mutProject.getLocation().toString();
		path = path.substring(0, path.lastIndexOf(File.separator));
		workspacePath = Paths.get(path);
	}
	
	public Path getWorkspacePath(Path path) {
		return getWorkspacePath(getProject(path));
	}
	
	public Path getWorkspacePath(String projectName) {
		return getWorkspacePath(getProject(projectName));
	}
	
	public Path getWorkspacePath(IProject project) {
		String projectPath = project.getLocation().toString();
		projectPath = projectPath.substring(0, projectPath.lastIndexOf(File.separator));
		return Paths.get(projectPath);
	}
	
	public Path getWorkspacePath() {
		return workspacePath;
	}
	
	public IProject getProject (Path path) {
		String projectName = path.getParent().toString().substring(1);
		return getProject(projectName);
	}
	
	public IProject getProject (String projectName) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	}
	
	public IProject getTestSuiteProject () {
		return getProject(testSuiteFilePath);
	}
	
	public IProject getModelUnderTestProject () {
		return getProject(modelUnderTestPath);
	}
	
	public IFile getTestSuiteFile() {
		return testSuiteFile;
	}

	public Resource getTestSuiteResource() {
		return testSuiteResource;
	}
	
	public Path getTestSuiteFilePath() {
		return testSuiteFilePath;
	}

	public String getTestSuiteProjectName() {
		return testSuiteFilePath.getParent().toString().substring(1);
	}

	public String getTestSuiteFileName() {
		return testSuiteFileName;
	}
	
	public Package getTestSuite() {
		return testSuite;
	}

	public Path getModelUnderTestPath() {
		return modelUnderTestPath;
	}

	public Resource getMUTResource() {
		if (MUTResource == null && modelUnderTestPath != null) {
			MUTResource = getMUTResource(modelUnderTestPath);
		}
		return MUTResource;
	}
	
	public Resource getMUTResource(Path modelUnderTestPath) {
		String path = Paths.get(modelUnderTestPath.toString()).toString();
		return (new ResourceSetImpl()).getResource(URI.createURI(path), true);
	}
	
	public String getDSLName() {
		return DSLName;
	}

	public Path getDSLPath() {
		return DSLPath;
	}
}
