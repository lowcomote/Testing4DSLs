package org.imt.tdl.coverage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.imt.tdl.coverage.dslSpecific.DSLSpecificCoverageHandler;
import org.imt.tdl.coverage.dslSpecific.IDSLSpecificCoverage;
import org.imt.tdl.utilities.DSLProcessor;

import DSLSpecificCoverage.DomainSpecificCoverage;

public class TDLCoverageUtil {
	
	private static TDLCoverageUtil instance = new TDLCoverageUtil();
	
	private DSLProcessor dslProcessor;
	private String DSLPath;
	private EPackage metamodelRootElement;
	private Set<String> coverableClasses = new HashSet<>();
	private Set<String> extendedClassesWithoutStep = new HashSet<>();

	private List<EClass> classesWithDynamicFeatures = new ArrayList<>();
	private List<EClass> dynamicClasses = new ArrayList<>();
	
	public static final String COVERED = "Covered";
	public static final String NOT_COVERED = "Not_Covered";
	public static final String NOT_TRACED = "Not_Traced";
	
	private IDSLSpecificCoverage dslSpecificCoverageExtension;
	private DomainSpecificCoverage dslSpecificCoverage;
	
	private TDLTestSuiteCoverage testSuiteCoverage;

	//make the constructor private so that this class cannot be
	//instantiated
	private TDLCoverageUtil(){}

	//Get the only object available
	public static TDLCoverageUtil getInstance(){
		return instance;
	}
	public TDLTestSuiteCoverage getTestSuiteCoverage() {
		return testSuiteCoverage;
	}
	public void setTestSuiteCoverage(TDLTestSuiteCoverage coverage) {
		testSuiteCoverage = coverage;
	}

	public String getDSLPath() {
		return DSLPath;
	}

	public void setDSLPath(String DSLPath) {
		instance.DSLPath = DSLPath;
	}
	
	public void runCoverageComputation() {
		coverableClasses.clear();
		extendedClassesWithoutStep.clear();
		dynamicClasses.clear();
		classesWithDynamicFeatures.clear();
		dslSpecificCoverage = null;
		dslSpecificCoverageExtension = null;
		
		dslProcessor = new DSLProcessor(DSLPath);
		dslProcessor.loadDSLMetaclasses();
		metamodelRootElement = dslProcessor.getMetamodelRootElement();
		findCoverableClassesFromDSLSemantics();
		
		testSuiteCoverage.calculateTSCoverage();
	}
	
	private void findCoverableClassesFromDSLSemantics(){
		dslProcessor.findDSLExtendedClasses();
		extendedClassesWithoutStep.addAll(dslProcessor.getExtendedClassesWithoutStep());
		coverableClasses.addAll(dslProcessor.getExtendedClassesWithStep());
		checkInheritanceForNotCoverableClasses();
	}
	
	//for each class that is not coverable (it is not extended in the interpreter)
	//if one of its super classes is coverable, the class must be set as coverable 
	public void checkInheritanceForNotCoverableClasses() {
		for (EClassifier clazz: metamodelRootElement.getEClassifiers()) {
			String className = clazz.getName();
			if (clazz instanceof EClass eclazz) {
				if (!coverableClasses.contains(className) && 
						!extendedClassesWithoutStep.contains(className)) {
					checkInheritance(eclazz);
				}
				checkDynamicAspectsOfClass(eclazz);	
			}
		}	
	}
	
	private void checkInheritance(EClass eClazz) {
		for (EClass superClass:eClazz.getEAllSuperTypes()) {
			if (coverableClasses.contains(superClass.getName())) {
				coverableClasses.add(eClazz.getName());
				break;
			}
		}
	}
	
	private void checkDynamicAspectsOfClass(EClassifier clazz) {
		List<EAnnotation> classDynamicAnnotations = clazz.getEAnnotations().stream().
				filter(a -> a.getSource().equals("dynamic") || a.getSource().equals("aspect")).collect(Collectors.toList());
		//if the type of the object is dynamic, all of its features must be set to the default values
		if (classDynamicAnnotations != null && classDynamicAnnotations.size()>0) {
			dynamicClasses.add((EClass) clazz);
		}
		else {
			List<EStructuralFeature> dynamicFeatures = ((EClass) clazz).getEAllStructuralFeatures().stream().
					filter(f -> isDynamicFeature(f)).collect(Collectors.toList());
			if (dynamicFeatures != null && dynamicFeatures.size()>0) {
				classesWithDynamicFeatures.add((EClass) clazz);
			}
		}
	}
	
	private boolean isDynamicFeature(EStructuralFeature feature) {
		List<EAnnotation> featureDynamicAnnotations = feature.getEAnnotations().stream().
				filter(a -> a.getSource().equals("dynamic") || 
						a.getSource().equals("aspect")).collect(Collectors.toList());
		return (featureDynamicAnnotations != null && featureDynamicAnnotations.size() > 0);
	}

	public boolean isClassCoverable(EClass clazz) {
		return coverableClasses.contains(clazz.getName());
	}
	
	//add a class and all of its sub classes
	public void addCoverableClass(EClass clazz) {
		if (!coverableClasses.contains(clazz.getName())) {
			coverableClasses.add(clazz.getName());
			List<String> notCoverableSubClasses = metamodelRootElement.getEClassifiers().stream().
					filter(c -> c instanceof EClass).map(c -> (EClass) c).
					filter(c -> c.getEAllSuperTypes().stream().
							filter(sc -> sc.getName().equals(clazz.getName())).findAny().isPresent() 
							&& !coverableClasses.contains(c.getName())
							&& !extendedClassesWithoutStep.contains(c.getName())).
					map (c -> c.getName()).collect(Collectors.toList());
				coverableClasses.addAll(notCoverableSubClasses);
		}
	}
	
	public void removeCoverableClass(EClass clazz) {
		if (coverableClasses.contains(clazz.getName())) {
			coverableClasses.remove(clazz.getName());
		}
	}
	//remove a class and all of its sub classes
	public void removeCoverableClass_subClass(EClass clazz) {
		if (coverableClasses.contains(clazz.getName())) {
			coverableClasses.remove(clazz.getName());
			List<String> coverableSubClasses = metamodelRootElement.getEClassifiers().stream().
					filter(c -> c instanceof EClass).map(c -> (EClass) c).
					filter(c -> c.getEAllSuperTypes().stream().
							filter(sc -> sc.getName().equals(clazz.getName())).findAny().isPresent() 
							&& coverableClasses.contains(c.getName())).
					map (c -> c.getName()).collect(Collectors.toList());
				coverableClasses.removeAll(coverableSubClasses);
		}
	}
	
	public List<EClass> getClassesWithDynamicFeatures() {
		return classesWithDynamicFeatures;
	}

	public List<EClass> getDynamicClasses() {
		return dynamicClasses;
	}
	
	public DomainSpecificCoverage getDslSpecificCoverage() {
		if (dslSpecificCoverage == null) {
			findDSLSpecificCoverage();
		}
		return dslSpecificCoverage;
	}
	
	public IDSLSpecificCoverage getDslSpecificCoverageExtension() {
		if (dslSpecificCoverageExtension == null) {
			findDSLSpecificCoverage();
		}
		return dslSpecificCoverageExtension;
	}
	
	private void findDSLSpecificCoverage() {
		//check if there is a DSL-Specific coverage extension
		DSLSpecificCoverageHandler dslSpecificCoverageHandler = new DSLSpecificCoverageHandler();
		dslSpecificCoverageExtension = dslSpecificCoverageHandler.getDSLSpecificCoverage();
		//1. if the rules are implemented in a java class, retrieve them using extension point
		if (dslSpecificCoverageExtension != null &&
				dslSpecificCoverageExtension.getDomainSpecificCoverage() != null) {
			dslSpecificCoverage = dslSpecificCoverageExtension.getDomainSpecificCoverage();
		}
		//2. else, check .dsl file for the path to the coverageRules.xmi file
		else {
			Resource coverageFileResource = loadDSLSpecificCoverageFile();
			if (coverageFileResource != null) {
				dslSpecificCoverage = (DomainSpecificCoverage) coverageFileResource.getContents().get(0);
			}
		}
	}
	
	private Resource loadDSLSpecificCoverageFile() {
		String coverageFilePath = getCoverageFilePath();
		if (coverageFilePath != null) {
			Resource coverageFileResource = (new ResourceSetImpl()).getResource(URI.createURI(coverageFilePath), true);
			return coverageFileResource;
		}
		return null;
	}
	
	private String getCoverageFilePath() {
		DSLProcessor dslProcessor = new DSLProcessor(DSLPath);
		String path = dslProcessor.getPath2CoverageRules();
		if (path != null) {
			path.replaceFirst("resource", "plugin");
			return path;
		}
		return null;
	}
}
