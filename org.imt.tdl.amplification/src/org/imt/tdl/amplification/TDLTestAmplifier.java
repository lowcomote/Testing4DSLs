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
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlPackage;

public class TDLTestAmplifier {

	public static void amplifyTestSuite(IFile testSuiteFile) {
		IPath path = testSuiteFile.getFullPath();
		URI testSuiteURI = URI.createPlatformResourceURI(path.toString(), true);
		ResourceSet resSet = new ResourceSetImpl();
//		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("tdlan2", new tdlResourceFactoryImpl());
		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		resSet.getPackageRegistry().put(tdlPackage.eNS_URI, tdlPackage.eINSTANCE);
		Resource testSuiteRes = (resSet).getResource(testSuiteURI, true);
		Package tdlTestSuite = (Package)testSuiteRes.getContents().get(0);
		List<TestDescription> tdlTestCases = tdlTestSuite.getPackagedElement().stream().
				filter(p -> p instanceof TestDescription).map(t -> (TestDescription) t).collect(Collectors.toList());
		
		for (TestDescription testCase: tdlTestCases) {
			System.out.println("Phase (1): Removing assertions from the test case");
			AssertionRemover assertionRemover = new AssertionRemover();
			assertionRemover.removeAssertionsFromTestCase(testCase);
			System.out.println("Phase (1) Done!");
			
			System.out.println("Phase (2): Mutating test input Data to generate new test cases");
			TDLTestInputDataMutation mutator = new TDLTestInputDataMutation();
			List<TestDescription> newTdlTestCases = mutator.generateNewTestsByInputMutation(testCase);
			System.out.println("Phase (2) Done: #of generated test cases by mutation = " + newTdlTestCases.size());
			
			System.out.println("Phase (3): Running new tests and generating assertions");
			List<TestDescription> validNewTdlTestCases = new ArrayList<>();
			for (TestDescription newTestCase: newTdlTestCases) {
				AssertionGenerator generator = new AssertionGenerator();
				if (generator.generateAssertionsForTestCase(newTestCase)) {
					validNewTdlTestCases.add(newTestCase);
				}
			}
			System.out.println("Phase (3) Done: #of valid generated test cases (having assertions) = " + validNewTdlTestCases.size());	
			
			System.out.println("Phase (4): Saving valid new test cases");
			String sourcePath = testSuiteURI.toString();
			String outputPath = sourcePath.substring(0, sourcePath.lastIndexOf("/")) + "amplifiedTestSuite.xmi";
			Resource newTestSuiteRes = (resSet).createResource(URI.createFileURI(outputPath));
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
			System.out.println("Phase (4) Done!");	
			System.out.println("Test Amplification has been performed successfully.");	
		}
	}
}
