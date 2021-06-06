package org.imt.TdlTest4pssm;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlPackage;
import org.imt.k3tdl.k3dsa.TestDescriptionAspect;
import org.imt.tdl.testResult.TDLTestCaseResult;

public class TestSuiteRunner {
	private static String pluginName = "/TDLTestSuite4PSSM";
	private ResourceSet tdlResourceSet;
	
	public void loadTdlPackages(){
		this.tdlResourceSet = new ResourceSetImpl();
		tdlPackage p = tdlPackage.eINSTANCE;
		tdlResourceSet.getPackageRegistry().put(p.getNsURI(), p);
		
		tdlResourceSet.getResource(getTDLPackageURI("pssmSpecificTypes"), true);
		tdlResourceSet.getResource(getTDLPackageURI("common"), true);
		tdlResourceSet.getResource(getTDLPackageURI("pssmSpecificEvents"), true);
		tdlResourceSet.getResource(getTDLPackageURI("testConfiguration"), true);
		
		File tdlTestSuite = new File(pluginName + "/TDLTestSuite/");
	}
	
	public void run(Package testPackage) {
		for (int i=0; i<testPackage.getPackagedElement().size(); i++) {
			Object o = testPackage.getPackagedElement().get(i);
			if (o instanceof TestDescription) {
				TestDescription testCase = (TestDescription) o;
				TestDescriptionAspect testCaseRunner = new TestDescriptionAspect();
				System.out.println("Test case: " + testCase.getName());
				testCaseRunner.executeTestCase(testCase);
				TDLTestCaseResult verdict = testCaseRunner.testCaseResult(testCase);
				if (verdict.getValue() == "FAIL") {
					
				}else {
					
				}
			}
		}
	}
	
	private URI getTDLPackageURI(String packageName) {
		return URI.createFileURI( pluginName + "/TDLTestSuite/TDLLibrary4PSSM/" + packageName + ".tdlan2");
	}
}
