package org.imt.tdl.amplification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TDLan2StandaloneSetup;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlFactory;
import org.etsi.mts.tdl.tdlPackage;

import com.google.inject.Injector;

public class TDLTestAmplifier {
	
	public static void amplifyTestSuite(IFile testSuiteFile) {
		ResourceSet resSet = new ResourceSetImpl();
		Resource testSuiteRes = readTestSuiteResource(resSet, testSuiteFile);
		Package tdlTestSuite = (Package)testSuiteRes.getContents().get(0);
		List<TestDescription> tdlTestCases = tdlTestSuite.getPackagedElement().stream().
				filter(p -> p instanceof TestDescription).map(t -> (TestDescription) t).collect(Collectors.toList());
		
		List<TestDescription> newTdlTestCases = new ArrayList<>();
		int numNewTests = 0;
		
		for (TestDescription testCase: tdlTestCases) {
			TestDescription copyTdlTestCase = tdlFactory.eINSTANCE.createTestDescription();
			copyTdlTestCase.setName(testCase.getName());
			copyTdlTestCase.setTestConfiguration(testCase.getTestConfiguration());
			copyTdlTestCase.setBehaviourDescription(EcoreUtil.copy(testCase.getBehaviourDescription()));
			
			System.out.println("Phase (1): Removing assertions from the test case");
			AssertionRemover assertionRemover = new AssertionRemover();
			assertionRemover.removeAssertionsFromTestCase(copyTdlTestCase);
			
			System.out.println("Phase (2): Mutating test input Data to generate new test cases");
			TDLTestInputDataMutation mutator = new TDLTestInputDataMutation();
			newTdlTestCases = mutator.generateNewTestsByInputMutation(copyTdlTestCase);
			System.out.println("Done: #of generated test cases by mutation = " + newTdlTestCases.size());
			
			System.out.println("\nPhase (3): Running new tests and generating assertions");
			int i = newTdlTestCases.size();
			for (TestDescription newTestCase: newTdlTestCases) {
				tdlTestSuite.getPackagedElement().add(newTestCase);
				AssertionGenerator generator = new AssertionGenerator();
				boolean result = generator.generateAssertionsForTestCase(newTestCase);
				if (!result) {
					tdlTestSuite.getPackagedElement().remove(newTestCase);
					i--;
				}
			}
			System.out.println("Done: #of valid generated test cases (having assertions) = " + i);
			numNewTests += i;
		}
		System.out.println("\nPhase (4): Saving valid new test cases");
		String sourcePath = testSuiteRes.getURI().toString();
		String extension = ".tdlan2";
		if (sourcePath.endsWith(".xmi")) {
			extension = ".xmi";
		}
		String outputPath = sourcePath.substring(0, sourcePath.lastIndexOf(extension)) + "_amplified" + extension;
		Resource newTestSuiteRes = (resSet).createResource(URI.createURI(outputPath));
		//all the new elements are in the testSuiteRes
		newTestSuiteRes.getContents().addAll(EcoreUtil.copyAll(testSuiteRes.getContents()));
		try {
			newTestSuiteRes.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testSuiteRes.unload();
		newTestSuiteRes.unload();
		System.out.println("\nTest Amplification has been performed successfully.");
		System.out.println("Total number of valid generated test cases: " + numNewTests);
	}

	private static Resource readTestSuiteResource(ResourceSet resSet, IFile testSuiteFile){
		IPath path = testSuiteFile.getFullPath();
		URI testSuiteURI = URI.createPlatformResourceURI(path.toString(), true);
		if (testSuiteURI.toString().endsWith(".xmi")) {
			resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
			resSet.getPackageRegistry().put(tdlPackage.eNS_URI, tdlPackage.eINSTANCE);
		}
		else if (testSuiteURI.toString().endsWith(".tdlan2")) {
			Injector injector = new TDLan2StandaloneSetup().createInjectorAndDoEMFRegistration();
			//resSet = injector.getInstance(XtextResourceSet.class);
			XtextResourceSet xtextResSet = injector.getInstance(XtextResourceSet.class);
			xtextResSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
			resSet = xtextResSet;
		}
		Resource testSuiteRes = (resSet).getResource(testSuiteURI, true);
		return testSuiteRes;
	}
}
