package org.imt.tdl.libraryGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.EventType;
import org.etsi.mts.tdl.Annotation;
import org.etsi.mts.tdl.AnnotationType;
import org.etsi.mts.tdl.AnyValue;
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
	private BehavioralInterface interfaceRootElement;
	
	private tdlFactory factory;
	private Package dslSpecificEventsPackage;
	
	private Package dslSpecificTypesPackage;
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	
	private List<DataType> dslInterfaceTypes = new ArrayList<DataType>();
	
	public DSLSpecificEventsGenerator(String dslFilePath, DSLSpecificTypesGenerator dslSpecificTypesGenerator) throws IOException {
		factory = tdlFactory.eINSTANCE; 
		dslName = validName(getDslName(dslFilePath));
		interfaceRootElement = getBehavioralInterfaceRootElement(dslFilePath);
		dslSpecificTypesPackage = dslSpecificTypesGenerator.getDslSpecificTypesPackage();
		dslSpecificTypes = dslSpecificTypesGenerator.getDslSpecificTypes();
	}
	
	public Package generateDSLSpecificEventsPackage(String dslFilePath) throws IOException{
		dslSpecificEventsPackage = factory.createPackage();
		dslSpecificEventsPackage.setName(dslName.toLowerCase() + "SpecificEvents");
		generateImports();
		generateTypeForDSLInterfaces();
		System.out.println("dsl-specific events package generated successfully");
		return dslSpecificEventsPackage;
	}
	
	private void generateImports() {		
		ElementImport PackageImport = factory.createElementImport();
		PackageImport.setImportedPackage(dslSpecificTypesPackage);
		dslSpecificEventsPackage.getImport().add(PackageImport);
	}

	private void generateTypeForDSLInterfaces() {
		AnnotationType acceptedEvent = factory.createAnnotationType();
		acceptedEvent.setName("AcceptedEvent");
		AnnotationType exposedEvent = factory.createAnnotationType();
		exposedEvent.setName("ExposedEvent");
		dslSpecificEventsPackage.getPackagedElement().add(acceptedEvent);
		dslSpecificEventsPackage.getPackagedElement().add(exposedEvent);
		
		for (int i=0; i<interfaceRootElement.getEvents().size();i++) {
			Event event = interfaceRootElement.getEvents().get(i);
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
			//generating an instance of the event type to be able to use it when writing test cases
			StructuredDataInstance eventInstance = factory.createStructuredDataInstance();
			eventInstance.setName(typeForEvent.getName());
			eventInstance.setDataType(typeForEvent);
			eventInstance.setUnassignedMember(UnassignedMemberTreatment.ANY_VALUE);
			
			for (int j=0; j<event.getParams().size();j++) {
				String paramName = validName(event.getParams().get(j).getName());
				String paramType = event.getParams().get(j).getType().toLowerCase();
				if (paramType.contains(".")) {
					paramType = paramType.substring(paramType.lastIndexOf(".") + 1, paramType.length());
				}
				if (dslSpecificTypes.get(paramType) != null) {
					Member member = factory.createMember();
					member.setName(paramName);
					member.setDataType(dslSpecificTypes.get(paramType));
					typeForEvent.getMember().add(member);
					
					MemberAssignment memberAssign = factory.createMemberAssignment();
					memberAssign.setMember(member);
					AnyValue anyValue = factory.createAnyValue();
					anyValue.setName("?");
					memberAssign.setMemberSpec(anyValue);
					eventInstance.getMemberAssignment().add(memberAssign);
				}		
			}
			dslSpecificEventsPackage.getPackagedElement().add(typeForEvent);
			dslSpecificEventsPackage.getPackagedElement().add(eventInstance);
			dslInterfaceTypes.add(typeForEvent);	
		}
	}
	
	public Package getDslSpecificEventsPackage() {
		return dslSpecificEventsPackage;
	}
	
	public List<DataType> getTypesOfDslInterfaces(){
		return dslInterfaceTypes;
	}

	//if a name is a keyword in tdl language, put '_' before it
	private String validName (String name) {
		return Arrays.stream(TDLCodeGenerator.tokenNames).anyMatch(name::equals)
				 ? "_" + name : name;
	}
	
	protected String getDslName(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String[] dslFullName = dsl.getEntry("name").getValue().split("\\.");
		return dslFullName[dslFullName.length-1];
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
