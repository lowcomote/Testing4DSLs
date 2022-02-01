package org.imt.tdl.coverage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecoretools.ale.BehavioredClass;
import org.eclipse.emf.ecoretools.ale.Operation;
import org.eclipse.emf.ecoretools.ale.Tag;
import org.eclipse.emf.ecoretools.ale.Unit;
import org.eclipse.gemoc.dsl.Dsl;
import org.osgi.framework.Bundle;

import fr.inria.diverse.k3.al.annotationprocessor.Aspect;
import fr.inria.diverse.k3.al.annotationprocessor.Step;

public class TDLCoverageUtil {
	
	private static TDLCoverageUtil instance = new TDLCoverageUtil();
	
	private String DSLPath;
	public List<String> coverableClasses = new ArrayList<>();
	private List<String> extendedClassesWithStep = new ArrayList<>();
	private List<String> extendedClassesWithoutStep = new ArrayList<>();
	
	private List<EClass> classesWithDynamicFeatures = new ArrayList<>();
	private List<EClass> dynamicClasses = new ArrayList<>();
	
	public static final String COVERED = "Covered";
	public static final String NOT_COVERED = "Not_Covered";
	public static final String COVERABLE = "Coverable";
	public static final String NOT_COVERABLE = "Not_Coverable";
	
	private TDLTestSuiteCoverage testSuiteCoverage;

	//make the constructor private so that this class cannot be
	//instantiated
	private TDLCoverageUtil(){}

	//Get the only object available
	public static TDLCoverageUtil getInstance(){
		return instance;
	}
	public TDLTestSuiteCoverage getTestSuiteCoverage() {
		return instance.testSuiteCoverage;
	}
	public void setTestSuiteCoverage(TDLTestSuiteCoverage coverage) {
		instance.testSuiteCoverage = coverage;
	}

	public String getDSLPath() {
		return instance.DSLPath;
	}

	public void setDSLPath(String DSLPath) {
		instance.DSLPath = DSLPath;
	}
	
	public void runCoverageComputation() {
		instance.coverableClasses.clear();
		instance.extendedClassesWithStep.clear();
		instance.extendedClassesWithoutStep.clear();
		instance.dynamicClasses.clear();
		instance.classesWithDynamicFeatures.clear();
		
		findCoverableClasses();
		instance.testSuiteCoverage.calculateTSCoverage();
	}
	
	public void findCoverableClasses(){
		EPackage metamodelRootElement;
		final ResourceSet resSet = new ResourceSetImpl();
		IConfigurationElement language = Arrays
				.asList(Platform.getExtensionRegistry()
						.getConfigurationElementsFor("org.eclipse.gemoc.gemoc_language_workbench.xdsml"))
				.stream().filter(l -> l.getAttribute("xdsmlFilePath").equals(DSLPath.substring(16)))
				.findFirst().orElse(null);
		
		final Resource res = resSet.getResource(URI.createURI(DSLPath), true);
		final Dsl dsl = (Dsl) res.getContents().get(0);
		final Bundle bundle = Platform.getBundle(language.getContributor().getName());
		String ecoreFilePath = dsl.getEntry("ecore").getValue().replaceFirst("resource", "plugin");
		Resource ecoreResource = (new ResourceSetImpl()).getResource(URI.createURI(ecoreFilePath), true);
		metamodelRootElement = (EPackage) ecoreResource.getContents().get(0);
		
		if (dsl.getEntry("k3") != null) {
			findK3Classes(dsl, bundle);
		}else if (dsl.getEntry("ale") != null) {
			findAleClasses(dsl, bundle);
		} 
		
		int abstractSyntaxSize = 0;
		for (EClassifier clazz: metamodelRootElement.getEClassifiers()) {
			String className = clazz.getName();
			if (clazz instanceof EClass) {
				abstractSyntaxSize++;
				if (!instance.coverableClasses.contains(className) && !instance.extendedClassesWithoutStep.contains(className)) {
					checkInheritance((EClass) clazz);
				}
				checkDynamicAspectsOfClass(clazz);
			}
		}
		
		//System.out.println("Abstract Syntax Size (n. of EClasses): " + abstractSyntaxSize);
		double percentage = Math.ceil((double)(instance.extendedClassesWithStep.size()*100)/abstractSyntaxSize);
		//System.out.println("% of Extended classes with @Step rules: " + percentage);
		percentage = Math.ceil((double)(instance.coverableClasses.size()*100)/abstractSyntaxSize);
		//System.out.println("% of Coverable classes (considering inheritance): " + percentage);
	}

	public void updateCoverableClasses (List<String> newClasses) {
		if (newClasses != null) {
			for (String newClassName: newClasses) {
				if (!instance.coverableClasses.contains(newClassName)) {
					instance.coverableClasses.add(newClassName);
				}
			}
		}
	}
	private void findK3Classes(Dsl dsl, Bundle bundle) {
		final List<Class<?>> classes = dsl.getEntries().stream().filter(e -> e.getKey().equals("k3"))
				.findFirst()
				.map(os -> Arrays.asList(os.getValue().split(",")).stream().map(cn -> loadClass(cn, bundle))
						.filter(c -> c != null).collect(Collectors.toList()))
				.orElse(Collections.emptyList()).stream().map(c -> (Class<?>) c).collect(Collectors.toList());
		
		//if a class is opened and has a stepping rule in the xDSL's interpreter, 
		//the objects of the class are coverable
		for (Class<?> clazz : classes) {
			String className = clazz.getDeclaredAnnotation(Aspect.class).className().getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method: methods) {
				if (method.getAnnotationsByType(Step.class).length > 0) {
					instance.extendedClassesWithStep.add(className);
					instance.coverableClasses.add(className);
					break;
				}
			}
			//if the extended class had no @step rule
			if (!instance.extendedClassesWithStep.contains(className)) {
				instance.extendedClassesWithoutStep.add(className);
			}
		}
	}
	
	private void findAleClasses(Dsl dsl, Bundle bundle) {
		String aleFilePath = dsl.getEntry("ale").getValue().replaceFirst("resource", "plugin");
		Resource aleResource = (new ResourceSetImpl()).getResource(URI.createURI(aleFilePath), true);
		Unit interpreter = (Unit) aleResource.getContents().get(0);
		List<BehavioredClass> classes = interpreter.getXtendedClasses();
		//if a class is opened and has a stepping rule in the xDSL's interpreter, 
		//the objects of the class are coverable
		for (BehavioredClass clazz : classes) {
			EList<Operation> operations = clazz.getOperations();
			String className = clazz.eClass().getName();
			for (int i=0; i<operations.size(); i++) {
				for (Tag tag:operations.get(i).getTag()) {
					if (tag.getName().equals("step")) {
						instance.extendedClassesWithStep.add(className);
						instance.coverableClasses.add(className);
						i = operations.size();
						break;
					}
				}
			}
			//if the extended class had no @step rule
			if (!instance.extendedClassesWithStep.contains(className)) {
				instance.extendedClassesWithoutStep.add(className);
			}
		}
	}
	private Class<?> loadClass(String className, Bundle bundle) {
		Class<?> result = null;
		try {
			result = bundle.loadClass(className.replaceAll("\\s", "").trim());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private void checkInheritance(EClass eClazz) {
		//foreach class that is not coverable (it is not extended in the interpreter)
		//if one of its super classes is coverable, the class must be set as coverable 
		for (EClass superClass:eClazz.getEAllSuperTypes()) {
			if (instance.coverableClasses.contains(superClass.getName())) {
				instance.coverableClasses.add(eClazz.getName());
				break;
			}
		}
	}
	
	private void checkDynamicAspectsOfClass(EClassifier clazz) {
		List<EAnnotation> classDynamicAnnotations = clazz.getEAnnotations().stream().
				filter(a -> a.getSource().equals("dynamic") || a.getSource().equals("aspect")).collect(Collectors.toList());
		//if the type of the object is dynamic, all of its features must be set to the default values
		if (classDynamicAnnotations != null && classDynamicAnnotations.size()>0) {
			this.dynamicClasses.add((EClass) clazz);
		}
		else {
			List<EStructuralFeature> dynamicFeatures = ((EClass) clazz).getEAllStructuralFeatures().stream().
					filter(f -> isDynamicFeature(f)).collect(Collectors.toList());
			if (dynamicFeatures != null && dynamicFeatures.size()>0) {
				this.classesWithDynamicFeatures.add((EClass) clazz);
			}
		}
	}
	
	private boolean isDynamicFeature(EStructuralFeature feature) {
		List<EAnnotation> featureDynamicAnnotations = feature.getEAnnotations().stream().
				filter(a -> a.getSource().equals("dynamic") || a.getSource().equals("aspect")).collect(Collectors.toList());
		return (featureDynamicAnnotations != null && featureDynamicAnnotations.size() > 0);
	}

	public List<EClass> getClassesWithDynamicFeatures() {
		return classesWithDynamicFeatures;
	}

	public List<EClass> getDynamicClasses() {
		return dynamicClasses;
	}
	
	
}