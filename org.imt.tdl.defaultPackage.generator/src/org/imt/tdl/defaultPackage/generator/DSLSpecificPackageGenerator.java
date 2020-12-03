package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecoretools.ale.*;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.emf.ecoretools.ale.implementation.ModelUnit;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.EventType;
import org.eclipse.m2m.atl.common.ATLExecutionException;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.etsi.mts.tdl.Annotation;
import org.etsi.mts.tdl.AnnotationType;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Member;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.tdlFactory;
import org.imt.atl.ecore2tdl.files.Ecore2tdl;

import com.google.inject.Injector;

public class DSLSpecificPackageGenerator {
	private String dslName;
	private EPackage metamodelRootElement;
	private ModelUnit aleSemanticRootElement;
	private BehavioralInterface interfaceRootElement;
	
	private tdlFactory factory;
	private Package dslSpecificEventsPackage;
	private Package dslSpecificTypesPackage;
	private CommonPackageGenerator commonPackageGenerator;
	
	private DataType modelState;
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	private List<DataType> dslInterfaceTypes = new ArrayList<DataType>();
	private List<DataType> TypesForGeneralEvents = new ArrayList<DataType>();
	
	public DSLSpecificPackageGenerator(String dslFilePath) throws IOException {
		System.out.println("Start dsl-specific package generation");
		this.factory = tdlFactory.eINSTANCE;
		this.commonPackageGenerator = new CommonPackageGenerator(); 
		this.dslName = getDslName(dslFilePath);
		this.metamodelRootElement = getMetamodelRootElement(dslFilePath);
		//this.aleSemanticRootElement = getAleSemanticsRootElement(dslFilePath);
		//System.out.println(this.aleSemanticRootElement.getName());
		this.interfaceRootElement = getBehavioralInterfaceRootElement(dslFilePath);
		generateDSLSpecificPackage(dslFilePath);
		System.out.println("dsl-specific package generated successfully");
	}
	
	private void generateDSLSpecificPackage(String dslFilePath) throws IOException{
		this.dslSpecificEventsPackage = factory.createPackage();
		this.dslSpecificEventsPackage.setName(this.dslName + "_SpecificPackage");
		generateImports();
		generateTypeForModelState();
		if (this.interfaceRootElement != null) {
			generateTypeForDSLInterfaces();
		}
		generateTypeForSetState();
	}
	private void generateImports() {
		ElementImport commonPackageImport = factory.createElementImport();
		commonPackageImport.setImportedPackage(this.commonPackageGenerator.getCommonPackage());
		this.dslSpecificEventsPackage.getImport().add(commonPackageImport);
		
		ElementImport dslSpecificTypesPackageImport = factory.createElementImport();
		dslSpecificTypesPackageImport.setImportedPackage(this.dslSpecificTypesPackage);
		this.dslSpecificEventsPackage.getImport().add(dslSpecificTypesPackageImport);
	}
	private void generateTypeForDSLInterfaces() {
		AnnotationType acceptedEvent = factory.createAnnotationType();
		acceptedEvent.setName("AcceptedEvent");
		AnnotationType exposedEvent = factory.createAnnotationType();
		exposedEvent.setName("ExposedEvent");
		this.dslSpecificEventsPackage.getPackagedElement().add(acceptedEvent);
		this.dslSpecificEventsPackage.getPackagedElement().add(exposedEvent);
		
		for (int i=0; i<this.interfaceRootElement.getEvents().size();i++) {
			Event event = this.interfaceRootElement.getEvents().get(i);
			StructuredDataType typeForEvent = factory.createStructuredDataType();
			typeForEvent.setName(event.getName());
			Annotation annotation = factory.createAnnotation();
			if (event.getType() == EventType.ACCEPTED) {
				annotation.setKey(acceptedEvent);
			}else if (event.getType() ==  EventType.EXPOSED) {
				annotation.setKey(exposedEvent);
			}
			annotation.setAnnotatedElement(typeForEvent);
			typeForEvent.getAnnotation().add(annotation);
			for (int j=0; j<event.getParams().size();j++) {
				String paramName = event.getParams().get(j).getName();
				if (this.dslSpecificTypes.get(paramName) != null) {
					Member member = factory.createMember();
					member.setName(paramName);
					member.setDataType(this.dslSpecificTypes.get(paramName));
					typeForEvent.getMember().add(member);
				}		
			}
			this.dslSpecificEventsPackage.getPackagedElement().add(typeForEvent);
			this.dslInterfaceTypes.add(typeForEvent);
		}
	}
	private void generateTypeForModelState() {
		StructuredDataType modelState = factory.createStructuredDataType();
		modelState.setName("modelState");
		//TODO: Define the members based on the model state
		this.dslSpecificEventsPackage.getPackagedElement().add(modelState);
		this.modelState = modelState;
	}
	private void generateTypeForSetState() {
		StructuredDataType setState = factory.createStructuredDataType();
		setState.setName("setState");
		Member state = factory.createMember();
		state.setName("state");
		state.setDataType(this.modelState);
		setState.getMember().add(state);
		this.dslSpecificEventsPackage.getPackagedElement().add(setState);
		this.TypesForGeneralEvents.add(setState);
	}
	public Package getDSLSpecificPackage() {
		return this.dslSpecificEventsPackage;
	}
	public DataType getTypeOfModelState() {
		return this.modelState;
	}
	public List<DataType> getTypesOfDslInterfaces(){
		return this.dslInterfaceTypes;
	}
	public List<DataType> getTypesOfGeneralEvents() {
		return this.TypesForGeneralEvents;
	}
	public CommonPackageGenerator getCommonPackageGenerator() {
		return this.commonPackageGenerator;
	}
	public void setDslSpecificTypes(Map<String, DataType> dslSpecificTypes) {
		this.dslSpecificTypes = dslSpecificTypes;
	}
	public void setDSLSpecificTypePackage (Package typesPackage) {
		this.dslSpecificTypesPackage = typesPackage;
	}
	protected String getDslName(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String[] dslFullName = dsl.getEntry("name").getValue().split("\\.");
		return dslFullName[dslFullName.length-1];
	}
	protected static EPackage getMetamodelRootElement(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String metamodelPath = dsl.getEntry("ecore").getValue();
		Resource metamodelRes = (new ResourceSetImpl()).getResource(URI.createURI(metamodelPath), true);
		EPackage metamodelRootElement = (EPackage) metamodelRes.getContents().get(0);
		return metamodelRootElement;
	}
	//TODO: ModelUnit is defined in ale metamodel but Unit is used in ale files??
	protected static ModelUnit getAleSemanticsRootElement(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String interpreterPath = dsl.getEntry("ale").getValue();
		Resource interpreterRes = (new ResourceSetImpl()).getResource(URI.createURI(interpreterPath), true);
		ModelUnit interpreterRootClass = (ModelUnit) interpreterRes.getContents().get(0);
		return interpreterRootClass;
	}
	private BehavioralInterface getBehavioralInterfaceRootElement(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String interfacePath = dsl.getEntry("behavioralInterface").getValue();
		Resource interfaceRes = (new ResourceSetImpl()).getResource(URI.createURI(interfacePath), true);
		BehavioralInterface interfaceRootElement = (BehavioralInterface) interfaceRes.getContents().get(0);
		return interfaceRootElement;
	}
}
