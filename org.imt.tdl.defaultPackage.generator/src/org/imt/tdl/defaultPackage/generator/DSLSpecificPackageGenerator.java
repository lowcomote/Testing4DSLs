package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecoretools.ale.Attribute;
import org.eclipse.emf.ecoretools.ale.BoolType;
import org.eclipse.emf.ecoretools.ale.IntType;
import org.eclipse.emf.ecoretools.ale.RealType;
import org.eclipse.emf.ecoretools.ale.SeqType;
import org.eclipse.emf.ecoretools.ale.SetType;
import org.eclipse.emf.ecoretools.ale.Unit;
import org.eclipse.emf.ecoretools.ale.rType;
import org.eclipse.emf.ecoretools.ale.typeLiteral;
import org.eclipse.emf.ecoretools.ale.StringType;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.EventType;
import org.etsi.mts.tdl.Annotation;
import org.etsi.mts.tdl.AnnotationType;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Member;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.SimpleDataType;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.tdlFactory;

public class DSLSpecificPackageGenerator {
	private String dslName;
	private EPackage metamodelRootElement;
	private Unit aleSemanticRootElement;
	private BehavioralInterface interfaceRootElement;
	
	private tdlFactory factory;
	private Package dslSpecificEventsPackage;
	private Package requiredTypesPackage;
	private Map<String, DataType> requiredTypes = new HashMap<String, DataType>();
	
	private DataType modelState;
	private List<DataType> dslInterfaceTypes = new ArrayList<DataType>();
	private List<DataType> TypesForGeneralEvents = new ArrayList<DataType>();
	
	public DSLSpecificPackageGenerator(String dslFilePath) throws IOException {
		this.factory = tdlFactory.eINSTANCE; 
		this.dslName = validName(getDslName(dslFilePath));
		this.metamodelRootElement = getMetamodelRootElement(dslFilePath);
		this.aleSemanticRootElement = getAleSemanticsRootElement(dslFilePath);
		this.interfaceRootElement = getBehavioralInterfaceRootElement(dslFilePath);
	}
	
	public void generateDSLSpecificPackage(String dslFilePath) throws IOException{
		this.dslSpecificEventsPackage = factory.createPackage();
		this.dslSpecificEventsPackage.setName(this.dslName + "_SpecificPackage");
		generateImports();
		if (this.metamodelRootElement != null && this.aleSemanticRootElement != null) {
			generateTypeForModelState();
			generateTypeForSetState();
		}
		if (this.interfaceRootElement != null) {
			generateTypeForDSLInterfaces();
		}
	}
	private void generateImports() {		
		ElementImport requiredTypesPackageImport = factory.createElementImport();
		requiredTypesPackageImport.setImportedPackage(this.requiredTypesPackage);
		this.dslSpecificEventsPackage.getImport().add(requiredTypesPackageImport);
	}
	private void generateTypeForModelState() {
		StructuredDataType modelState = factory.createStructuredDataType();
		modelState.setName("ModelState");
		//generate members for modelState based on the attributes of the extended classes in ale file
		for (int i=0; i< this.aleSemanticRootElement.getXtendedClasses().size(); i++) {
			List<Attribute> attributes = this.aleSemanticRootElement.getXtendedClasses().get(i).getAttributes();
			for (int j=0; j< attributes.size(); j++) {
				String memberName = validName(attributes.get(j).getName());
				rType memberAleType = attributes.get(j).getType();
				DataType memberTDLType = null;
				if (memberAleType instanceof typeLiteral) {
					memberTDLType = aleTypeLiteral2tdlType((typeLiteral) memberAleType);
				} else {
					if (this.requiredTypes.get(validName(memberAleType.getName().toLowerCase())) != null) {
						memberTDLType = this.requiredTypes.get(validName(memberAleType.getName().toLowerCase()));
					}else {
						SimpleDataType newType = factory.createSimpleDataType();
						newType.setName(validName(memberAleType.getName()));
						this.requiredTypes.put(newType.getName().toLowerCase(), newType);
						this.dslSpecificEventsPackage.getPackagedElement().add(newType);
						memberTDLType = newType;
					}
				}
				Member member = factory.createMember();
				member.setName(memberName);
				member.setDataType(memberTDLType);
				modelState.getMember().add(member);
			}
		}
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
			typeForEvent.setName(validName(event.getName()));
			Annotation annotation = factory.createAnnotation();
			if (event.getType() == EventType.ACCEPTED) {
				annotation.setKey(acceptedEvent);
			}else if (event.getType() ==  EventType.EXPOSED) {
				annotation.setKey(exposedEvent);
			}
			annotation.setAnnotatedElement(typeForEvent);
			typeForEvent.getAnnotation().add(annotation);
			for (int j=0; j<event.getParams().size();j++) {
				String paramName = validName(event.getParams().get(j).getName());
				String paramType = event.getParams().get(j).getType().toLowerCase();
				if (this.requiredTypes.get(paramType) != null) {
					Member member = factory.createMember();
					member.setName(paramName);
					member.setDataType(this.requiredTypes.get(paramType));
					typeForEvent.getMember().add(member);
				}		
			}
			this.dslSpecificEventsPackage.getPackagedElement().add(typeForEvent);
			this.dslInterfaceTypes.add(typeForEvent);
		}
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
	public void setRequiredTypes(Map<String, DataType> requiredTypes) {
		this.requiredTypes = requiredTypes;
	}
	public void setRequiredTypesPackage (Package typesPackage) {
		this.requiredTypesPackage = typesPackage;
	}
	//if a name is a keyword in tdl language, put '_' before it
	private String validName (String name) {
		if (Arrays.stream(TDLCodeGenerator.tokenNames).anyMatch(name::equals)) {
			return "_"+name;
		}
		return name;
	}
	//mapping from ALE basic data types to the ecore basic data types which are transformed to TDL types
	private DataType aleTypeLiteral2tdlType (typeLiteral typeLiteral) {
		if (typeLiteral instanceof StringType) {
			return this.requiredTypes.get("EString".toLowerCase());
		} else if (typeLiteral instanceof IntType) {
			return this.requiredTypes.get("EInt".toLowerCase());
		} else if (typeLiteral instanceof RealType) {
			return this.requiredTypes.get("EDouble".toLowerCase());
		} else if (typeLiteral instanceof BoolType) {
			return this.requiredTypes.get("EBoolean".toLowerCase());
		} else if (typeLiteral instanceof SeqType || typeLiteral instanceof SetType) {
			SeqType seqType = (SeqType) typeLiteral;
			return aleTypeLiteral2tdlType(seqType.getType());
		}
		return null;
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
		if (dsl.getEntry("ecore") != null) {
			String metamodelPath = dsl.getEntry("ecore").getValue().replaceFirst("resource", "plugin");
			Resource metamodelRes = (new ResourceSetImpl()).getResource(URI.createURI(metamodelPath), true);
			EPackage metamodelRootElement = (EPackage) metamodelRes.getContents().get(0);
			return metamodelRootElement;
		}
		return null;
	}
	//TODO: ModelUnit is defined in ale metamodel but Unit is used in ale files??
	protected static Unit getAleSemanticsRootElement(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		if (dsl.getEntry("ale") != null) {
			String interpreterPath = dsl.getEntry("ale").getValue().replaceFirst("resource", "plugin");
			Resource interpreterRes = (new ResourceSetImpl()).getResource(URI.createURI(interpreterPath), true);
			Unit interpreterRootClass = (Unit) interpreterRes.getContents().get(0);
			return interpreterRootClass;
		}
		return null;
	}
	protected static BehavioralInterface getBehavioralInterfaceRootElement(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		if (dsl.getEntry("behavioralInterface") != null) {
			String interfacePath = dsl.getEntry("behavioralInterface").getValue().replaceFirst("resource", "plugin");
			Resource interfaceRes = (new ResourceSetImpl()).getResource(URI.createURI(interfacePath), true);
			BehavioralInterface interfaceRootElement = (BehavioralInterface) interfaceRes.getContents().get(0);
			return interfaceRootElement;
		}
		return null;
	}
}
