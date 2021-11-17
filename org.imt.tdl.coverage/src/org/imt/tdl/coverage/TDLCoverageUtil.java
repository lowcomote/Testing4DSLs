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
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
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

	private Resource MUTResource;
	public List<EObject> modelObjects = new ArrayList<>();
	private int numOfCoverableElements = 0;
	
	private String DSLPath;
	private List<Class<?>> coverableClasses = new ArrayList<>();
	public List<String> objectCoverageStatus = new ArrayList<>();
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
		instance.coverableClasses.clear();
	}

	public String getDSLPath() {
		return DSLPath;
	}

	public void setDSLPath(String DSLPath) {
		this.DSLPath = DSLPath;
		findCoverableClasses();
		instance.testSuiteCoverage.calculateTSCoverage();
	}
	
	public void findCoverableClasses(){
		final ResourceSet resSet = new ResourceSetImpl();
		IConfigurationElement language = Arrays
				.asList(Platform.getExtensionRegistry()
						.getConfigurationElementsFor("org.eclipse.gemoc.gemoc_language_workbench.xdsml"))
				.stream().filter(l -> l.getAttribute("xdsmlFilePath").equals(DSLPath.substring(16)))
				.findFirst().orElse(null);
		
		final Resource res = resSet.getResource(URI.createURI(DSLPath), true);
		final Dsl dsl = (Dsl) res.getContents().get(0);
		final Bundle bundle = Platform.getBundle(language.getContributor().getName());
		
		if (dsl.getEntry("k3") != null) {
			findK3Classes(dsl, bundle);
		}else if (dsl.getEntry("ale") != null) {
			findAleClasses(dsl, bundle);
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
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method: methods) {
				if (method.getAnnotationsByType(Step.class).length > 0) {
					instance.coverableClasses.add(clazz.getDeclaredAnnotation(Aspect.class).className());
					break;
				}
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
			for (int i=0; i<operations.size(); i++) {
				for (Tag tag:operations.get(i).getTag()) {
					if (tag.getName().equals("step")) {
						//TODO: it doesn't work correctly. The clazz format in ALE is different from K3
						instance.coverableClasses.add(clazz.eClass().getInstanceClass());
						i = operations.size();
						break;
					}
				}
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
	
	public Resource getMUTResource() {
		return instance.MUTResource;
	}

	public void setMUTResource(Resource MUTResource) {
		instance.MUTResource = MUTResource;
		instance.numOfCoverableElements = 0;
		instance.modelObjects.clear();
		instance.objectCoverageStatus.clear();
	}
	
	public void findNotCoverableObjects() {
		TreeIterator<EObject> modelContents = instance.MUTResource.getAllContents();
		while (modelContents.hasNext()) {
			EObject modelObject = modelContents.next();
			List<Class<?>> interfaces = new ArrayList<>();
			interfaces.addAll(Arrays.asList(modelObject.getClass().getInterfaces()));
			//we also consider all super classes because if the superclass is coverable,
			//all its subclasses must be considered as coverable
			for (EClass superClass:modelObject.eClass().getEAllSuperTypes()) {
				interfaces.add(superClass.getInstanceClass());
			}
			boolean covered = false;
			for (Class<?> cInterface: interfaces) {
				if (instance.coverableClasses.contains(cInterface)) {
					instance.modelObjects.add(modelObject);
					instance.objectCoverageStatus.add(COVERABLE);
					instance.numOfCoverableElements++;
					covered = true;
					break;
				}
			}
			if (!covered) {
				instance.modelObjects.add(modelObject);
				instance.objectCoverageStatus.add(NOT_COVERABLE);
			}
		}
		checkContainmentRelations(instance.modelObjects.get(0));
	}
	
	private void checkContainmentRelations(EObject rootObject) {
		int rootObjectIndex = instance.modelObjects.indexOf(rootObject);
		String coverage = instance.objectCoverageStatus.get(rootObjectIndex);
		if (coverage == TDLCoverageUtil.NOT_COVERABLE && rootObject.eContents().size() > 0) {
			for (int j=0; j<rootObject.eContents().size(); j++) {
				EObject containmentRef = rootObject.eContents().get(j);
				int refIndexInObjectList = instance.modelObjects.indexOf(containmentRef);
				if (instance.objectCoverageStatus.get(refIndexInObjectList) == TDLCoverageUtil.NOT_COVERABLE) {
					checkContainmentRelations(containmentRef);
				}
			}
			//if all containments are COVERABLE, set the object as COVERABLE
			boolean coverable = true;
			for (int j=0; j<rootObject.eContents().size(); j++) {
				EObject containmentRef = rootObject.eContents().get(j);
				int refIndexInObjectList = instance.modelObjects.indexOf(containmentRef);
				if (instance.objectCoverageStatus.get(refIndexInObjectList) != TDLCoverageUtil.COVERABLE) {
					coverable = false;
					break;
				}
			}
			if (coverable) {
				instance.numOfCoverableElements++;
				instance.objectCoverageStatus.set(rootObjectIndex, TDLCoverageUtil.COVERABLE);
			}
		}
		//check containment relations for the next elements
		if (rootObjectIndex < instance.modelObjects.size() - 1) {
			checkContainmentRelations(instance.modelObjects.get(rootObjectIndex + 1));
		}	
	}

	public int getNumOfCoverableElements() {
		//this returns the size of the model in terms of its coverable elements
		return instance.numOfCoverableElements;
	}
}
