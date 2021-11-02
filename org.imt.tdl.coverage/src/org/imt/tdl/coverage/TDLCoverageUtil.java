package org.imt.tdl.coverage;

import fr.inria.diverse.k3.al.annotationprocessor.Step;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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

public class TDLCoverageUtil {
	
	private static TDLCoverageUtil instance = new TDLCoverageUtil();
	
	private String MUTPath;
	private String DSLPath;
	private TreeIterator<EObject> modelContents;
	private int modelSize;
	
	private List<Class<?>> coverableClasses = new ArrayList<>();
	public HashMap<EObject, String> objectCoverageStatus = new HashMap<>();
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

	public String getMUTPath() {
		return MUTPath;
	}

	public void setMUTPath(String MUTPath) {
		this.MUTPath = MUTPath;
		Resource MUTResource = (new ResourceSetImpl()).getResource(URI.createURI(MUTPath), true);
		modelContents = MUTResource.getAllContents();
	}

	public String getDSLPath() {
		return DSLPath;
	}

	public void setDSLPath(String DSLPath) {
		this.DSLPath = DSLPath;
		findCoverableClasses();
		findNotCoverableObjects();
		this.testSuiteCoverage.calculateTSCoverage();
	}

	public int getModelSize() {
		return modelSize;
	}

	public TreeIterator<EObject> getModelContents() {
		return modelContents;
	}
	
	//if a class is opened and has a stepping rule in the xDSL's interpreter, 
	//the objects of the class are coverable
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
		
		for (Class clazz : classes) {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method: methods) {
				if (method.getAnnotationsByType(Step.class).length > 0) {
					coverableClasses.add(clazz);
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
			modelSize++;
			EObject modelObject = this.modelContents.next();
			modelObject.getClass();
			//TODO: here we compare Impl classes with Aspect classes, what to do?
			if (this.coverableClasses.contains(modelObject.getClass())) {
				this.objectCoverageStatus.put(modelObject, COVERABLE);
			}else {
				this.objectCoverageStatus.put(modelObject, NOT_COVERABLE);
			}
		}
	}
}
