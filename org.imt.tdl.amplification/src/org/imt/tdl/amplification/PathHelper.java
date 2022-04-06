package org.imt.tdl.amplification;

import java.util.Arrays;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.etsi.mts.tdl.Annotation;
import org.etsi.mts.tdl.ComponentInstance;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TDLan2StandaloneSetup;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlPackage;

import com.google.inject.Injector;

public class PathHelper {
	
	static PathHelper instance = new PathHelper();

	IFile testSuiteFile;
	Resource testSuiteResource;
	Package testSuite;
	String testSuiteFilePath;
	String testSuiteProjectName;
	String testSuiteFileName;
	
	String workspacePath;
	String modelsProjectPath;
	String seedModelPath;
	
	String DSLName;
	String DSLPath;
	
	private PathHelper() {
	}
	
	public static PathHelper getInstance() {
		return instance;
	}
	
	public void setTestSuiteFile(IFile testSuiteFile) {
		this.testSuiteFile = testSuiteFile;
		ResourceSet resSet = new ResourceSetImpl();
		testSuiteResource = readTestSuiteResource(resSet, testSuiteFile);
		setTestSuite((Package)testSuiteResource.getContents().get(0));
	}
	
	private Resource readTestSuiteResource(ResourceSet resSet, IFile testSuiteFile){
		IPath path = testSuiteFile.getFullPath();
		URI testSuiteURI = URI.createPlatformResourceURI(path.toString(), true);
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
			Injector injector = new TDLan2StandaloneSetup().createInjectorAndDoEMFRegistration();
			//resSet = injector.getInstance(XtextResourceSet.class);
			XtextResourceSet xtextResSet = injector.getInstance(XtextResourceSet.class);
			xtextResSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		}
		Resource testSuiteRes = (resSet).getResource(testSuiteURI, true);
		return testSuiteRes;
	}
	
	public void setTestSuite(Package testSuite) {
		this.testSuite = testSuite;
		findModelPathAndDSLName();
		findDSLPath();
		findWorkspace();
		findTestSuiteProjectInfo();
	}
	
	private void findModelPathAndDSLName () {
		Optional<TestDescription> testCase = testSuite.getPackagedElement().stream().filter(p -> p instanceof TestDescription).
		map(p -> (TestDescription) p).findFirst();
		Optional<ComponentInstance> sutComponent = testCase.get().getTestConfiguration().getComponentInstance().
				stream().filter(ci -> ci.getRole().toString().equals("SUT")).findFirst();
		for (Annotation a:sutComponent.get().getAnnotation()){
			if (a.getKey().getName().equals("MUTPath")){
				seedModelPath = a.getValue().substring(1, a.getValue().length()-1);
			}
			else if (a.getKey().getName().equals("DSLName")) {
				DSLName = a.getValue().substring(1, a.getValue().length()-1);
			}
		}
	}
	
	public void findDSLPath (){
		IConfigurationElement language = Arrays
				.asList(Platform.getExtensionRegistry()
						.getConfigurationElementsFor("org.eclipse.gemoc.gemoc_language_workbench.xdsml"))
				.stream().filter(l -> l.getAttribute("xdsmlFilePath").endsWith(".dsl")
						&& l.getAttribute("name").equals(DSLName))
				.findFirst().orElse(null);
		
		if (language != null) {
			DSLPath = language.getAttribute("xdsmlFilePath");
			if (!DSLPath.startsWith("platform:/plugin")) {
				DSLPath = "platform:/plugin" + DSLPath;
			}
		}
	}
	
	private void findWorkspace() {
		String projectName = seedModelPath.substring(1, seedModelPath.lastIndexOf("/"));
		IProject mutProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		workspacePath = mutProject.getLocation().toString();
		workspacePath = workspacePath.substring(0, workspacePath.lastIndexOf("/")).replace("/", "\\");
	}
	
	private void findTestSuiteProjectInfo() {
		testSuiteFilePath = testSuiteFile.getFullPath().toString();
		testSuiteProjectName = testSuiteFilePath.substring(1, testSuiteFilePath.lastIndexOf("/"));
		testSuiteFileName = testSuiteFile.getName().substring(0, testSuiteFile.getName().lastIndexOf("."));
	}

	public IFile getTestSuiteFile() {
		return testSuiteFile;
	}

	public Resource getTestSuiteResource() {
		return testSuiteResource;
	}
	
	public String getTestSuiteProjectPath() {
		return testSuiteFilePath;
	}

	public String getTestSuiteProjectName() {
		return testSuiteProjectName;
	}

	public String getTestSuiteFileName() {
		return testSuiteFileName;
	}
	
	public Package getTestSuite() {
		return testSuite;
	}

	public String getWorkspacePath() {
		return workspacePath;
	}

	public String getSeedModelPath() {
		return seedModelPath;
	}

	public String getDSLName() {
		return DSLName;
	}

	public String getDSLPath() {
		return DSLPath;
	}
}
