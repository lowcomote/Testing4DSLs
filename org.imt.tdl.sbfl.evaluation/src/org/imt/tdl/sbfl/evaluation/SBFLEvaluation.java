package org.imt.tdl.sbfl.evaluation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.coverage.TDLTestSuiteCoverage;
import org.imt.tdl.coverage.ObjectCoverageStatus;
import org.imt.tdl.faultLocalization.SBFLMeasures;
import org.imt.tdl.faultLocalization.SuspiciousnessRanking;
import org.imt.tdl.testResult.TDLTestSuiteResult;

import appliedMutations.AppMutation;
import appliedMutations.InformationChanged;
import appliedMutations.Mutations;

public class SBFLEvaluation {

	private List<String> mutants = new ArrayList<>();
	private List<String> registeries = new ArrayList<>();
	private HashMap<String, String> mutant_registry = new HashMap<>();
	
	private Package testSuite = null;
	
	private HashMap<String, TDLTestSuiteResult> mutant_Verdict = new HashMap<>();
	private HashMap<String, TDLTestSuiteCoverage> mutant_Coverage = new HashMap<>();
	private HashMap<String, SBFLMeasures> mutant_SBFLMeasures4FaultyObject = new HashMap<>();
	
	IProject mutantsProject;
	IProject testSuiteProject;
	String workspacePath;
	String seedModelPath;
	
	public SBFLEvaluation (IProject mutantsProject, IProject testSuiteProject) {
		this.mutantsProject = mutantsProject;
		this.testSuiteProject = testSuiteProject;
	}
	public void evaluateSBFLTechniques() {
		//finding mutants and mapping them to their registry
		findMutantRegistryMapping(this.mutantsProject);
		System.out.println("# of generated mutants: " + this.mutant_registry.size());
		
		//finding test suite and run it on mutants to find live ones and keep verdict and coverage of killed ones
		String testResourcePath = "platform:/resource/"+ testSuiteProject.getName() + "/";
		File testsFolder = new File(testSuiteProject.getLocation().toString());	
		for (File testFile : testsFolder.listFiles()) {
			this.testSuite = getTestSuite(testResourcePath, testFile);
		}
		testMutants();
		System.out.println("# of killed mutants: " + this.mutant_Verdict.size());
		
		if (this.mutant_registry.size()>0) {
			for (String mutant:this.mutant_registry.keySet()) {
				localizeFaultOfMutant(mutant);
			}
			//export results to Excel
			ExcelExporter excelExporter = new ExcelExporter(mutant_SBFLMeasures4FaultyObject);
			excelExporter.saveResults2Excelfile();
			System.out.println("Results saved in an Excel file");
		}
		System.out.println("Evaluation finished");
	}
	
	private void findMutantRegistryMapping(IProject mutantsProject) {
		File projectFolder = new File(mutantsProject.getLocation() + "\\model");
		for (File file : projectFolder.listFiles()) {
			pathsHelper(mutantsProject.getName(), file);
		}
		//if the mutants have registries, find their mappings.
		for (String mutantPath:this.mutants){
			if (this.registeries.size()>0){
				String mutator = getMutator(mutantPath);
				String mutantName = mutantPath.substring(mutantPath.lastIndexOf("\\") + 1, mutantPath.indexOf(".model"));
				Optional<String> registery = this.registeries.stream().filter(r -> r.contains(mutator) && r.contains(mutantName + "Registry")).findFirst();
				if (registery.isPresent()) {
					this.mutant_registry.put(mutantPath, registery.get());
				}
			}
			else {
				this.mutant_registry.put(mutantPath, null);
			}
		}
	}
	
	private void pathsHelper(String projectName, File file) {
		if (file.isFile() && file.getName().endsWith(".model")) {
			String filePath = file.getPath();
			if (this.workspacePath == null) {
				this.workspacePath = filePath.substring(0, filePath.lastIndexOf(projectName)-1);
			}		
			//get the relative path of the file
			filePath = filePath.replace(this.workspacePath, "");
			//get the name of the seed model
			if (this.seedModelPath == null) {
				String seedModelName = filePath.substring(1);
				seedModelName = filePath.substring(filePath.indexOf("\\model\\") + "\\model\\".length(), filePath.length());
				if (file.getName().equals(seedModelName)) {//file is the seed model
					this.seedModelPath = filePath;
				}
			}
			if (file.getName().endsWith("Registry.model")) {
				this.registeries.add(filePath);
			}else if (!filePath.equals(seedModelPath)){
				this.mutants.add(filePath);
			}
		}
		else if (file.isDirectory()){
			for (File innerFile : file.listFiles()) {
				pathsHelper(projectName, innerFile);
			}
		}
	}
	
	public String getMutator (String filePath) {
		filePath = filePath.replace(this.workspacePath, "");
		String[] filePathSections = filePath.split("\\\\");
		if (filePath.contains("Registry.model")) {
			return filePathSections[filePathSections.length-3];
		}else {
			return filePathSections[filePathSections.length-2];
		}
	}
	
	private Package getTestSuite(String testResourcePath, File testFile) {
		if (testFile.isFile() && testFile.getName().endsWith(".tdlan2")) {
			String testFilePath = testResourcePath + testFile.getName();
			org.etsi.mts.tdl.Package testPackage = getTestPackage(testFilePath);
			if (testPackage != null) {
				return testPackage;
			}			
		}
		else if (testFile.isDirectory()){
			testResourcePath += (testFile.getName() + "/");
			for (File innerFile : testFile.listFiles()) {
				return getTestSuite(testResourcePath, innerFile);
			}
		}
		return null;
	}
	
	private org.etsi.mts.tdl.Package getTestPackage(String testFilePath) {
		Resource testSuiteRes = (new ResourceSetImpl()).getResource(URI.createURI(testFilePath), true);
		org.etsi.mts.tdl.Package testPackage = (org.etsi.mts.tdl.Package) testSuiteRes.getContents().get(0);
		for (Object element: testPackage.getPackagedElement()) {
			if (element instanceof TestDescription) {
				return testPackage;
			}
		}
		return null;
	}
	
	private void testMutants() {
		for (String mutant:this.mutants) {
			MutationTestRunner testRunner = new MutationTestRunner();
			System.out.println("run tests on: " + mutant);
			testRunner.runTestAndCalculateCoverage(this.testSuite, mutant);
			if (testRunner.getTestSuiteResult().getNumOfFailedTestCases() == 0) {
				//the mutant is alive, so it must be removed from the evaluation data
				if (this.mutant_registry.size()>0) {
					this.mutant_registry.remove(mutant);
				}
			}
			else {
				//the mutant is killed, so its test result and its coverage must be kept
				this.mutant_Verdict.put(mutant, testRunner.getTestSuiteResult());
				this.mutant_Coverage.put(mutant, testRunner.getTestSuiteCoverage());
			}
		}
	}
	
	public void localizeFaultOfMutant(String mutant) {
		TDLTestSuiteResult testSuiteResult = this.mutant_Verdict.get(mutant);
		TDLTestSuiteCoverage testSuiteCoverage = this.mutant_Coverage.get(mutant);
		SuspiciousnessRanking suspComputing = new SuspiciousnessRanking(testSuiteResult, testSuiteCoverage);
		suspComputing.calculateMeasures();
		List<SBFLMeasures> mutantSBFLMeasures = suspComputing.getElementsSBFLMeasures();
		for (String sbflTechnique : suspComputing.sbflTechniques) {
			for (SBFLMeasures measure:mutantSBFLMeasures) {
				measure.currentTechnique = sbflTechnique;
				double susp = suspComputing.getSuspiciousness(measure);
				measure.getSusp().put(sbflTechnique, susp);
			}
			suspComputing.calculateRanks();
		}
		try {
			EObject faultyObject = getFaultyObjectOfMutant(mutant);
			int indexOfFaultyObject = findCoveredFaultyObject(faultyObject, suspComputing.getCoverageMatix());
			try {
				SBFLMeasures measures4faultyObject = mutantSBFLMeasures.get(indexOfFaultyObject);
				for (String sbflTechnique : suspComputing.sbflTechniques) {
					suspComputing.measureEXAMScores(measures4faultyObject, sbflTechnique);
				}
				mutant_SBFLMeasures4FaultyObject.put(mutant, measures4faultyObject);
			} catch (IndexOutOfBoundsException e){
				System.out.println("Cannot find the index of the faulty object for mutant: " + mutant);
			}
		}
		catch (NullPointerException e) {
			System.out.println("Cannot find the faulty object for mutant: " + mutant);
		}
	}
	
	private EObject getFaultyObjectOfMutant(String mutant) {
		//if there is a registry for the mutant, find the faulty object using the registry
		if (this.mutant_registry.get(mutant) != null) {
			String mutantRegistryPath = "platform:/resource" + this.mutant_registry.get(mutant).replace("\\", "/");
			Resource registryResource = (new ResourceSetImpl()).getResource(URI.createURI(mutantRegistryPath), true);
			Mutations mutations = (Mutations) registryResource.getContents().get(0);
			Optional<AppMutation> informationChangedMutation = mutations.getMuts().stream().filter(m -> m instanceof InformationChanged).findFirst();
			if (informationChangedMutation.isPresent()) {
				InformationChanged informationChanged = (InformationChanged)informationChangedMutation.get();
				if (informationChanged.getAttChanges().size()>0) {
					return ((InformationChanged)informationChangedMutation.get()).getObject();
				}
				if (informationChanged.getRefChanges().size()>0) {
					return informationChanged.getRefChanges().get(0).getMutantObject().get(0);
				}
			}
		}
		//otherwise, use EMF Compare to find the faulty object
		else {
			FaultyObjectFinder finder = new FaultyObjectFinder();
			String mutantPath = "platform:/resource" + mutant.replace("\\", "/");
			Resource mutantResource = (new ResourceSetImpl()).getResource(URI.createURI(mutantPath), true);
			String seedPath = "platform:/resource" + this.seedModelPath.replace("\\", "/");
			Resource originalModelResource = (new ResourceSetImpl()).getResource(URI.createURI(seedPath), true);
			return finder.findFaultyObjectOfMutant(mutantResource, originalModelResource);
		}
		return null;
	}
	
	private int findCoveredFaultyObject(EObject faultyObject, List<ObjectCoverageStatus> coverageMatrix) {
		//clean the runtime data of the faulty object and the objects captured in the coverage matrix
		clearRuntimeData(faultyObject);
		coverageMatrix.forEach(c -> clearRuntimeData(c.getModelObject()));
		//find the object of the coverage matrix that is equals to the faulty object
		List<ObjectCoverageStatus> relatedCoverages = coverageMatrix.stream().
				filter(info -> EcoreUtil.equals(info.getModelObject(), faultyObject)).collect(Collectors.toList());
		if (relatedCoverages.size()==1) {
			return coverageMatrix.indexOf(relatedCoverages.get(0));
		}
		else if (relatedCoverages.size() > 1){
			ObjectCoverageStatus relatedCoverageByContainer = coverageMatrix.stream().
					filter(info -> EcoreUtil.equals(info.getModelObject().eContainer(), faultyObject.eContainer())).findFirst().get();
			int index = coverageMatrix.indexOf(relatedCoverageByContainer);
			if (index > 0) {
				return index;
			}
		}
		//if the faulty object is not covered, find the index of its container
		if (faultyObject.eContainer() != null) {
			return findCoveredFaultyObject(faultyObject.eContainer(), coverageMatrix);
		}
		return -1;
	}
	
	private void clearRuntimeData(EObject faultyObject) {
		Resource mutantResource = faultyObject.eResource();
		TreeIterator<EObject> mutantContents = mutantResource.getAllContents();
		while (mutantContents.hasNext()) {
			EObject eobject = mutantContents.next();
			EClass eobjectType = eobject.eClass();
			Optional<EClass> eclass = TDLCoverageUtil.getInstance().getDynamicClasses().stream().
					filter(c -> c.getName().equals(eobjectType.getName())).findFirst();
			if (eclass.isPresent()) {
				eobjectType.getEAllStructuralFeatures().forEach(f -> clearRuntimeDataOfFeature(eobject, f));
			}
			else {
				eclass = TDLCoverageUtil.getInstance().getClassesWithDynamicFeatures().stream().
						filter(c -> c.getName().equals(eobjectType.getName())).findFirst();
				if (eclass.isPresent()) {
					List<EStructuralFeature> dynamicFeatures = eobjectType.getEAllStructuralFeatures().stream().
							filter(f -> isDynamicFeature(f)).collect(Collectors.toList());
					dynamicFeatures.forEach(f -> clearRuntimeDataOfFeature(eobject, f));
				}
			}
		}	
	}
	
	private void clearRuntimeDataOfFeature(EObject object, EStructuralFeature feature) {
		if (feature.eResource().getResourceSet() == null) {
			object.eSet(feature, feature.getDefaultValue());
		}else {
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(feature);
			try{
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						object.eSet(feature, feature.getDefaultValue());
					}
		   		});
	   		}catch(IllegalArgumentException e){
				e.printStackTrace();
			}
		}
	}
	private boolean isDynamicFeature(EStructuralFeature feature) {
		List<EAnnotation> featureDynamicAnnotations = feature.getEAnnotations().stream().
				filter(a -> a.getSource().equals("dynamic") || a.getSource().equals("aspect")).collect(Collectors.toList());
		return (featureDynamicAnnotations != null && featureDynamicAnnotations.size() > 0);
	}
}

