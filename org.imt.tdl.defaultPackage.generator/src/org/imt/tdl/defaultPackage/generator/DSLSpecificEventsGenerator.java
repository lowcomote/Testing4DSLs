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
import org.eclipse.emf.ecoretools.ale.BoolType;
import org.eclipse.emf.ecoretools.ale.IntType;
import org.eclipse.emf.ecoretools.ale.RealType;
import org.eclipse.emf.ecoretools.ale.SeqType;
import org.eclipse.emf.ecoretools.ale.SetType;
import org.eclipse.emf.ecoretools.ale.Unit;
import org.eclipse.emf.ecoretools.ale.typeLiteral;
import org.eclipse.emf.ecoretools.ale.StringType;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.EventType;
import org.etsi.mts.tdl.Annotation;
import org.etsi.mts.tdl.AnnotationType;
import org.etsi.mts.tdl.AnyValue;
import org.etsi.mts.tdl.AnyValueOrOmit;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Member;
import org.etsi.mts.tdl.MemberAssignment;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.StructuredDataInstance;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.UnassignedMemberTreatment;
import org.etsi.mts.tdl.tdlFactory;

public class DSLSpecificEventsGenerator {
	private String dslName;
	private EPackage metamodelRootElement;
	//private Unit aleSemanticRootElement;
	private BehavioralInterface interfaceRootElement;
	
	private tdlFactory factory;
	private Package dslSpecificEventsPackage;
	private Package dslSpecificTypesPackage;
	
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	private List<DataType> dslInterfaceTypes = new ArrayList<DataType>();
	
	public DSLSpecificEventsGenerator(String dslFilePath) throws IOException {
		this.factory = tdlFactory.eINSTANCE; 
		this.dslName = validName(getDslName(dslFilePath));
		this.metamodelRootElement = getMetamodelRootElement(dslFilePath);
		//this.aleSemanticRootElement = getAleSemanticsRootElement(dslFilePath);
		this.interfaceRootElement = getBehavioralInterfaceRootElement(dslFilePath);
	}
	
	public void generateDSLSpecificEventsPackage(String dslFilePath) throws IOException{
		this.dslSpecificEventsPackage = factory.createPackage();
		this.dslSpecificEventsPackage.setName(this.dslName + "SpecificEvents");
		generateImports();
		if (this.interfaceRootElement != null) {
			generateTypeForDSLInterfaces();
		}
	}
	private void generateImports() {		
		ElementImport PackageImport = factory.createElementImport();
		PackageImport.setImportedPackage(this.dslSpecificTypesPackage);
		this.dslSpecificEventsPackage.getImport().add(PackageImport);
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
				if (this.dslSpecificTypes.get(paramType) != null) {
					Member member = factory.createMember();
					member.setName(paramName);
					member.setDataType(this.dslSpecificTypes.get(paramType));
					typeForEvent.getMember().add(member);
				}		
			}
			this.dslSpecificEventsPackage.getPackagedElement().add(typeForEvent);
			this.dslInterfaceTypes.add(typeForEvent);
		}
	}
	public Package getDslSpecificEventsPackage() {
		return this.dslSpecificEventsPackage;
	}
	public List<DataType> getTypesOfDslInterfaces(){
		return this.dslInterfaceTypes;
	}
	public void setDslSpecificTypesPackage (Package typesPackage) {
		this.dslSpecificTypesPackage = typesPackage;
	}
	public void setDslSpecificTypes(Map<String, DataType> dslSpecificTypes) {
		this.dslSpecificTypes = dslSpecificTypes;
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
			return this.dslSpecificTypes.get("EString".toLowerCase());
		} else if (typeLiteral instanceof IntType) {
			return this.dslSpecificTypes.get("EInt".toLowerCase());
		} else if (typeLiteral instanceof RealType) {
			return this.dslSpecificTypes.get("EDouble".toLowerCase());
		} else if (typeLiteral instanceof BoolType) {
			return this.dslSpecificTypes.get("EBoolean".toLowerCase());
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
