package org.imt.tdl.amplification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlPackage;

public class MainByWodel {

	public static void main(String[] args) {
//		String inputPath = "inputTests/AtmTestSuite.xmi";
//		ResourceSet resSet = new ResourceSetImpl();
////		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("tdlan2", new tdlResourceFactoryImpl());
//		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
//		resSet.getPackageRegistry().put(tdlPackage.eNS_URI, tdlPackage.eINSTANCE);
//		Resource testSuiteRes = (resSet).getResource(URI.createFileURI(inputPath), true);
		List<String> mutantsPaths = (new MutantPathHelper()).getMutantPaths();
		for (String path:mutantsPaths) {
			ResourceSet resSet = new ResourceSetImpl();
//			resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("model", new XMIResourceFactoryImpl());
//			resSet.getPackageRegistry().put(tdlPackage.eNS_URI, tdlPackage.eINSTANCE);
			Resource testSuiteRes = (resSet).getResource(URI.createFileURI(path), true);
			Package tdlTestSuite = (Package)testSuiteRes.getContents().get(0);
			List<TestDescription> tdlTestCases = tdlTestSuite.getPackagedElement().stream().
					filter(p -> p instanceof TestDescription).map(t -> (TestDescription) t).collect(Collectors.toList());
			
			for (TestDescription testCase: tdlTestCases) {
				System.out.println("Phase (3): Running new test case and generating assertions for it");
				List<TestDescription> validNewTdlTestCases = new ArrayList<>();
				AssertionGenerator generator = new AssertionGenerator();
				if (generator.generateAssertionsForTestCase(testCase)) {
					validNewTdlTestCases.add(testCase);
				}
				System.out.println("Phase (3) Done: #of valid generated test cases (having assertions) = " + validNewTdlTestCases.size());	
			}
			
			System.out.println("Phase (4): Saving valid new test cases");
			String outputPath = "amplifiedTests/amplifiedTestSuite.xmi";
			Resource newTestSuiteRes = (resSet).createResource(URI.createFileURI(outputPath));
			//all the new elements are in the testSuiteRes
			newTestSuiteRes.getContents().add(testSuiteRes.getContents().get(0));
			try {
				newTestSuiteRes.save(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Phase (4) Done!");	
			System.out.println("Test Amplification has been performed successfully.");	
		}	
	}
}
