package org.imt.tdl.coverage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.osgi.framework.Bundle;

import fr.inria.diverse.k3.al.annotationprocessor.Aspect;
import fr.inria.diverse.k3.al.annotationprocessor.Step;

public class TDLCoverageUtil {
	
	private static TDLCoverageUtil instance = new TDLCoverageUtil();
	
	private Resource MUTResource;
	private String DSLPath;
	private TreeIterator<EObject> modelContents;
	private int modelSize = 0;
	
	private List<String> coverableClasses = new ArrayList<>();
	public LinkedHashMap<EObject, String> objectCoverageStatus = new LinkedHashMap<>();
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
		instance.modelSize = 0;
		instance.coverableClasses = new ArrayList<>();
		instance.objectCoverageStatus = new LinkedHashMap<>();
	}

	public Resource getMUTResource() {
		return instance.MUTResource;
	}

	public void setMUTResource(Resource MUTResource) {
		instance.MUTResource = MUTResource;
		instance.modelContents = MUTResource.getAllContents();
	}

	public String getDSLPath() {
		return DSLPath;
	}

	public void setDSLPath(String DSLPath) {
		this.DSLPath = DSLPath;
		findCoverableClasses();
		findNotCoverableObjects();
		instance.testSuiteCoverage.calculateTSCoverage();
	}

	public int getModelSize() {
		return instance.modelSize;
	}

	public TreeIterator<EObject> getModelContents() {
		return instance.modelContents;
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
					String ecoreClassName = clazz.getDeclaredAnnotation(Aspect.class).className().getName();
					ecoreClassName = ecoreClassName.substring(ecoreClassName.lastIndexOf(".") + 1);
					instance.coverableClasses.add(ecoreClassName + "Impl");
					break;
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
	
	public void findNotCoverableObjects() {
		while (this.modelContents.hasNext()) {
			EObject modelObject = this.modelContents.next();
			instance.modelSize++;
			String objectClassName = modelObject.getClass().getName();
			objectClassName = objectClassName.substring(objectClassName.lastIndexOf(".") + 1);
			if (instance.coverableClasses.contains(objectClassName)) {
				instance.objectCoverageStatus.put(modelObject, COVERABLE);
			}else {
				instance.objectCoverageStatus.put(modelObject, NOT_COVERABLE);
			}
		}
	}
}
