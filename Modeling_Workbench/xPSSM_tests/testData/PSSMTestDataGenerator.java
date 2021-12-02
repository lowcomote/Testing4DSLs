package org.imt.tdl.libraryGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.etsi.mts.tdl.DataInstance;
import org.etsi.mts.tdl.DataInstanceUse;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.LiteralValueUse;
import org.etsi.mts.tdl.Member;
import org.etsi.mts.tdl.MemberAssignment;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.StructuredDataInstance;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.TDLan2StandaloneSetup;
import org.etsi.mts.tdl.UnassignedMemberTreatment;
import org.etsi.mts.tdl.tdlFactory;
import org.imt.pssm.reactive.model.statemachines.Attribute;
import org.imt.pssm.reactive.model.statemachines.Behavior;
import org.imt.pssm.reactive.model.statemachines.BooleanAttribute;
import org.imt.pssm.reactive.model.statemachines.IntegerAttribute;
import org.imt.pssm.reactive.model.statemachines.Operation;
import org.imt.pssm.reactive.model.statemachines.Signal;
import org.imt.pssm.reactive.model.statemachines.StateMachine;
import org.imt.pssm.reactive.model.statemachines.StringAttribute;

import com.google.inject.Injector;

public class PSSMTestDataGenerator {
	
	private List<Package> testDataPackages = new ArrayList<>();
	private Package dslSpecificTypesPackage;
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	
	public PSSMTestDataGenerator() {
		Resource xPSSMTypesResource = (new ResourceSetImpl()).getResource(URI.createURI("/xPSSM_tests/generated/xPSSMTypes.tdlan2"), true);
		this.dslSpecificTypesPackage = (Package) dslTypesRes.getContents().get(0);
		for (int i=0; i<this.dslSpecificTypesPackage.getPackagedElement().size();i++) {
			PackageableElement p = this.dslSpecificTypesPackage.getPackagedElement().get(i);
			if (p instanceof DataType) {
				this.dslSpecificTypes.put(p.getName().toLowerCase(), (DataType) p);
			}
		}
	}
	
	public void generateTestData() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] tdlProjects = root.getProjects();
		IProject xPSSM_Models_Project = null;
		for (int i=0; i<tdlProjects.length; i++) {
			if (tdlProjects[i].getName().equals("xPSSM_models")) {
				xPSSM_Models_Project = tdlProjects[i];
			}
		}

		String modelName = "BankATM";
		IFile modelFile = xPSSM_Models_Project.getFile(modelName + ".xmi");
		generateTestDataPackage(modelName, modelFile);
		
		modelName = "JavaEJB";
		modelFile = xPSSM_Models_Project.getFile(modelName + ".xmi");
		generateTestDataPackage(modelName, modelFile);
		
		modelName = "JavaThreadLifeCycle";
		modelFile = xPSSM_Models_Project.getFile(modelName + ".xmi");
		generateTestDataPackage(modelName, modelFile);
		
		modelName = "OnlineShopping";
		modelFile = xPSSM_Models_Project.getFile(modelName + ".xmi");
		generateTestDataPackage(modelName, modelFile);
		
		try {
			savePackages("/xPSSM_tests/testData/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void generateTestDataPackage (String modelName, IFile modelFile) {
		Package testDataPackage = tdlFactory.eINSTANCE.createPackage();
		testDataPackage.setName(validName(modelName) + "_TestData");
		ElementImport dslSpecificTypesPackageImport = tdlFactory.eINSTANCE.createElementImport();
		dslSpecificTypesPackageImport.setImportedPackage(this.dslSpecificTypesPackage);
		testDataPackage.getImport().add(dslSpecificTypesPackageImport);
		
		String modelPath = "platform:/resource" + modelFile.getFullPath();
		Resource modelRes = (new ResourceSetImpl()).getResource(URI.createURI(modelPath), true);
		TreeIterator<EObject> modelIterator = modelRes.getAllContents();
		while (modelIterator.hasNext()) {
			EObject modelObject = modelIterator.next();
			if (modelObject instanceof StateMachine) {
				createSMInstance(testDataPackage, (StateMachine) modelObject);
			}
			else if (modelObject instanceof Behavior) {
				createBehaviorInstance(testDataPackage, (Behavior) modelObject);	
			}
			else if (modelObject instanceof Signal) {
				createSignalInstance(testDataPackage, (Signal) modelObject);
			}
			else if (modelObject instanceof Operation) {
				createOperationInstance(testDataPackage, (Operation) modelObject);
			}
		}
		this.testDataPackages.add(testDataPackage);
	}
	
	private void createSMInstance(Package testDataPackage, StateMachine sm) {
		StructuredDataInstance smTdlInstance = tdlFactory.eINSTANCE.createStructuredDataInstance();
		smTdlInstance.setName(validName(sm.getName()));
		StructuredDataType smTdlType = (StructuredDataType) this.dslSpecificTypes.get("statemachine");
		smTdlInstance.setDataType(smTdlType);
		
		MemberAssignment ma = tdlFactory.eINSTANCE.createMemberAssignment();
		for (Member member:smTdlType.allMembers()) {
			if (member.getName().equals("_name")) {
				ma.setMember(member);
				break;
			}
		}
		LiteralValueUse nameValue = tdlFactory.eINSTANCE.createLiteralValueUse();
		nameValue.setValue("\"" + sm.getName() + "\"");
		ma.setMemberSpec(nameValue);
		smTdlInstance.getMemberAssignment().add(ma);
		testDataPackage.getPackagedElement().add(smTdlInstance);
	}
	
	private void createBehaviorInstance(Package testDataPackage, Behavior modelObject) {
		Behavior behavior = (Behavior) modelObject;
		StructuredDataInstance behaviorTdlInstance = tdlFactory.eINSTANCE.createStructuredDataInstance();
		behaviorTdlInstance.setName(validName(behavior.getName()));
		StructuredDataType behaviorTdlType = (StructuredDataType) this.dslSpecificTypes.get("behavior");
		behaviorTdlInstance.setDataType(behaviorTdlType);
		
		MemberAssignment ma = tdlFactory.eINSTANCE.createMemberAssignment();
		for (Member member:behaviorTdlType.allMembers()) {
			if (member.getName().equals("_name")) {
				ma.setMember(member);
				break;
			}
		}
		
		LiteralValueUse nameValue = tdlFactory.eINSTANCE.createLiteralValueUse();
		nameValue.setValue("\"" + behavior.getName() + "\"");
		ma.setMemberSpec(nameValue);
		behaviorTdlInstance.getMemberAssignment().add(ma);
		testDataPackage.getPackagedElement().add(behaviorTdlInstance);
	}

	private void createSignalInstance(Package testDataPackage, Signal signal) {
		StructuredDataInstance signalTdlInstance = tdlFactory.eINSTANCE.createStructuredDataInstance();
		signalTdlInstance.setName(validName(signal.getName()));
		StructuredDataType signalTdlType = (StructuredDataType) this.dslSpecificTypes.get("signal");
		signalTdlInstance.setDataType(signalTdlType);
		
		MemberAssignment signalName = tdlFactory.eINSTANCE.createMemberAssignment();
		for (Member member:signalTdlType.allMembers()) {
			if (member.getName().equals("_name")) {
				signalName.setMember(member);
				break;
			}
		}
		LiteralValueUse nameValue = tdlFactory.eINSTANCE.createLiteralValueUse();
		nameValue.setValue("\"" + signal.getName() + "\"");
		signalName.setMemberSpec(nameValue);
		signalTdlInstance.getMemberAssignment().add(signalName);
		
		//if signal has attributes, a memberAssignment for attributes must be defined for signal instance
		if (signal.getAttributes() != null && signal.getAttributes().size() > 0) {
			MemberAssignment signalAttributes = tdlFactory.eINSTANCE.createMemberAssignment();
			for (Member member:signalTdlType.allMembers()) {
				if (member.getName().equals("attributes")) {
					signalAttributes.setMember(member);
					break;
				}
			}
			//if the signal has several attributes, they should be contained in a upper level dataInstanceUse
			DataInstanceUse signalAttributesArray = null;
			if (signal.getAttributes().size()>1) {
				signalAttributesArray = tdlFactory.eINSTANCE.createDataInstanceUse();
				signalAttributesArray.setUnassignedMember(UnassignedMemberTreatment.ANY_VALUE);
			}
			//an instance for the signal attributes
			for (Attribute attribute:signal.getAttributes()) {
				DataInstance attributeTdlInstance = null;
				if (attribute instanceof BooleanAttribute) {
					attributeTdlInstance = createBooleanAttributeInstance(testDataPackage, (BooleanAttribute) attribute);
				}
				else if (attribute instanceof IntegerAttribute) {
					attributeTdlInstance = createIntegerAttributeInstance(testDataPackage, (IntegerAttribute) attribute);
				}
				else if (attribute instanceof StringAttribute) {
					attributeTdlInstance = createStringAttributeInstance(testDataPackage, (StringAttribute) attribute);
				}
				
				DataInstanceUse signalAttributesItem = tdlFactory.eINSTANCE.createDataInstanceUse();
				signalAttributesItem.setDataInstance(attributeTdlInstance);
				if (signal.getAttributes().size()>1) {
					signalAttributesArray.getItem().add(signalAttributesItem);
				}
				else {
					signalAttributes.setMemberSpec(signalAttributesItem);
				}
			}
			if (signalAttributesArray != null) {
				signalAttributes.setMemberSpec(signalAttributesArray);
			}
			signalTdlInstance.getMemberAssignment().add(signalAttributes);
		}
		testDataPackage.getPackagedElement().add(signalTdlInstance);
		
		//create an instance for the signal occurrence
		StructuredDataInstance signalOccTdlInstance = tdlFactory.eINSTANCE.createStructuredDataInstance();
		signalOccTdlInstance.setName(validName(signal.getName()) + "_occurrence");
		StructuredDataType signalOccTdlType = (StructuredDataType) this.dslSpecificTypes.get("signaleventoccurrence");
		signalOccTdlInstance.setDataType(signalOccTdlType);
		
		MemberAssignment occSignal = tdlFactory.eINSTANCE.createMemberAssignment();
		for (Member member:signalOccTdlType.allMembers()) {
			if (member.getName().equals("signal")) {
				occSignal.setMember(member);
				break;
			}
		}
		DataInstanceUse signalOccParam = tdlFactory.eINSTANCE.createDataInstanceUse();
		signalOccParam.setDataInstance(signalTdlInstance);
		occSignal.setMemberSpec(signalOccParam);
		signalOccTdlInstance.getMemberAssignment().add(occSignal);
		testDataPackage.getPackagedElement().add(signalOccTdlInstance);
	}
	
	private void createOperationInstance(Package testDataPackage, Operation operation) {
		StructuredDataInstance operationTdlInstance = tdlFactory.eINSTANCE.createStructuredDataInstance();
		operationTdlInstance.setName(validName(operation.getName()));
		StructuredDataType operationTdlType = (StructuredDataType) this.dslSpecificTypes.get("operation");
		operationTdlInstance.setDataType(operationTdlType);
		
		MemberAssignment operationName = tdlFactory.eINSTANCE.createMemberAssignment();
		for (Member member:operationTdlType.allMembers()) {
			if (member.getName().equals("_name")) {
				operationName.setMember(member);
				break;
			}
		}
		LiteralValueUse nameValue = tdlFactory.eINSTANCE.createLiteralValueUse();
		nameValue.setValue("\"" + operation.getName() + "\"");
		operationName.setMemberSpec(nameValue);
		operationTdlInstance.getMemberAssignment().add(operationName);
		
		//if signal has attributes, a memberAssignment for attributes must be defined for signal instance
		if (operation.getReturn() != null) {
			MemberAssignment opReturnAttribute = tdlFactory.eINSTANCE.createMemberAssignment();
			for (Member member:operationTdlType.allMembers()) {
				if (member.getName().equals("return")) {
					opReturnAttribute.setMember(member);
					break;
				}
			}
			//an instance for the signal attributes
			DataInstance returnTdlInstance = null;
			if (operation.getReturn() instanceof BooleanAttribute) {
				returnTdlInstance = createBooleanAttributeInstance(testDataPackage, (BooleanAttribute) operation.getReturn());
			}
			else if (operation.getReturn() instanceof IntegerAttribute) {
				returnTdlInstance = createIntegerAttributeInstance(testDataPackage, (IntegerAttribute) operation.getReturn());
			}
			else if (operation.getReturn() instanceof StringAttribute) {
				returnTdlInstance = createStringAttributeInstance(testDataPackage, (StringAttribute) operation.getReturn());
			}
			
			DataInstanceUse opReturnAttributeAssignment = tdlFactory.eINSTANCE.createDataInstanceUse();
			opReturnAttributeAssignment.setDataInstance(returnTdlInstance);
			opReturnAttribute.setMemberSpec(opReturnAttributeAssignment);
			operationTdlInstance.getMemberAssignment().add(opReturnAttribute);
		}
		testDataPackage.getPackagedElement().add(operationTdlInstance);
		
		//create an instance for the signal occurrence
		StructuredDataInstance callOccTdlInstance = tdlFactory.eINSTANCE.createStructuredDataInstance();
		callOccTdlInstance.setName(validName(operation.getName()) + "_occurrence");
		StructuredDataType callOccTdlType = (StructuredDataType) this.dslSpecificTypes.get("calleventoccurrence");
		callOccTdlInstance.setDataType(callOccTdlType);
		
		MemberAssignment occOperation = tdlFactory.eINSTANCE.createMemberAssignment();
		for (Member member:callOccTdlType.allMembers()) {
			if (member.getName().equals("operation")) {
				occOperation.setMember(member);
				break;
			}
		}
		DataInstanceUse callOccParam = tdlFactory.eINSTANCE.createDataInstanceUse();
		callOccParam.setDataInstance(operationTdlInstance);
		occOperation.setMemberSpec(callOccParam);
		callOccTdlInstance.getMemberAssignment().add(occOperation);
		testDataPackage.getPackagedElement().add(callOccTdlInstance);
	}
	private DataInstance createBooleanAttributeInstance(Package testDataPackage, BooleanAttribute attribute) {
		StructuredDataInstance attributeTdlInstance = tdlFactory.eINSTANCE.createStructuredDataInstance();
		attributeTdlInstance.setName(validName(attribute.getName()));
		StructuredDataType attributeTdlType = (StructuredDataType) this.dslSpecificTypes.get("booleanattribute");
		attributeTdlInstance.setDataType(attributeTdlType);
		
		MemberAssignment ma = tdlFactory.eINSTANCE.createMemberAssignment();
		for (Member member:attributeTdlType.allMembers()) {
			if (member.getName().equals("_name")) {
				ma.setMember(member);
				break;
			}
		}
		
		LiteralValueUse nameValue = tdlFactory.eINSTANCE.createLiteralValueUse();
		nameValue.setValue("\"" + attribute.getName() + "\"");
		ma.setMemberSpec(nameValue);
		attributeTdlInstance.getMemberAssignment().add(ma);
		testDataPackage.getPackagedElement().add(attributeTdlInstance);
		
		//create a value instance for this attribute
		StructuredDataInstance attributeValueTdlInstance = tdlFactory.eINSTANCE.createStructuredDataInstance();
		attributeValueTdlInstance.setName(validName(attribute.getName()) + "Value");
		StructuredDataType attributeValueTdlType = (StructuredDataType) this.dslSpecificTypes.get("booleanattributevalue");
		attributeValueTdlInstance.setDataType(attributeValueTdlType);
		
		MemberAssignment attributeMember = tdlFactory.eINSTANCE.createMemberAssignment();
		for (Member member:attributeValueTdlType.allMembers()) {
			if (member.getName().equals("attribute")) {
				attributeMember.setMember(member);
				break;
			}
		}
		
		DataInstanceUse attributeMemberData = tdlFactory.eINSTANCE.createDataInstanceUse();
		attributeMemberData.setDataInstance(attributeTdlInstance);
		attributeMember.setMemberSpec(attributeMemberData);
		attributeValueTdlInstance.getMemberAssignment().add(attributeMember);
		testDataPackage.getPackagedElement().add(attributeValueTdlInstance);
		
		return attributeTdlInstance;
	}

	private DataInstance createIntegerAttributeInstance(Package testDataPackage, IntegerAttribute attribute) {
		StructuredDataInstance attributeTdlInstance = tdlFactory.eINSTANCE.createStructuredDataInstance();
		attributeTdlInstance.setName(validName(attribute.getName()));
		StructuredDataType attributeTdlType = (StructuredDataType) this.dslSpecificTypes.get("integerattribute");
		attributeTdlInstance.setDataType(attributeTdlType);
		
		MemberAssignment ma = tdlFactory.eINSTANCE.createMemberAssignment();
		for (Member member:attributeTdlType.allMembers()) {
			if (member.getName().equals("_name")) {
				ma.setMember(member);
				break;
			}
		}
		
		LiteralValueUse nameValue = tdlFactory.eINSTANCE.createLiteralValueUse();
		nameValue.setValue("\"" + attribute.getName() + "\"");
		ma.setMemberSpec(nameValue);
		attributeTdlInstance.getMemberAssignment().add(ma);
		testDataPackage.getPackagedElement().add(attributeTdlInstance);
		
		//create a value instance for this attribute
		StructuredDataInstance attributeValueTdlInstance = tdlFactory.eINSTANCE.createStructuredDataInstance();
		attributeValueTdlInstance.setName(validName(attribute.getName()) + "Value");
		StructuredDataType attributeValueTdlType = (StructuredDataType) this.dslSpecificTypes.get("integerattributevalue");
		attributeValueTdlInstance.setDataType(attributeValueTdlType);
		
		MemberAssignment attributeMember = tdlFactory.eINSTANCE.createMemberAssignment();
		for (Member member:attributeValueTdlType.allMembers()) {
			if (member.getName().equals("attribute")) {
				attributeMember.setMember(member);
				break;
			}
		}
		
		DataInstanceUse attributeMemberData = tdlFactory.eINSTANCE.createDataInstanceUse();
		attributeMemberData.setDataInstance(attributeTdlInstance);
		attributeMember.setMemberSpec(attributeMemberData);
		attributeValueTdlInstance.getMemberAssignment().add(attributeMember);
		testDataPackage.getPackagedElement().add(attributeValueTdlInstance);
		
		return attributeTdlInstance;
	}
	
	private DataInstance createStringAttributeInstance(Package testDataPackage, StringAttribute attribute) {
		StructuredDataInstance attributeTdlInstance = tdlFactory.eINSTANCE.createStructuredDataInstance();
		attributeTdlInstance.setName(validName(attribute.getName()));
		StructuredDataType attributeTdlType = (StructuredDataType) this.dslSpecificTypes.get("stringattribute");
		attributeTdlInstance.setDataType(attributeTdlType);
		
		MemberAssignment ma = tdlFactory.eINSTANCE.createMemberAssignment();
		for (Member member:attributeTdlType.allMembers()) {
			if (member.getName().equals("_name")) {
				ma.setMember(member);
				break;
			}
		}
		
		LiteralValueUse nameValue = tdlFactory.eINSTANCE.createLiteralValueUse();
		nameValue.setValue("\"" + attribute.getName() + "\"");
		ma.setMemberSpec(nameValue);
		attributeTdlInstance.getMemberAssignment().add(ma);
		testDataPackage.getPackagedElement().add(attributeTdlInstance);
		
		//create a value instance for this attribute
		StructuredDataInstance attributeValueTdlInstance = tdlFactory.eINSTANCE.createStructuredDataInstance();
		attributeValueTdlInstance.setName(validName(attribute.getName()) + "Value");
		StructuredDataType attributeValueTdlType = (StructuredDataType) this.dslSpecificTypes.get("stringattributevalue");
		attributeValueTdlInstance.setDataType(attributeValueTdlType);
		
		MemberAssignment attributeMember = tdlFactory.eINSTANCE.createMemberAssignment();
		for (Member member:attributeValueTdlType.allMembers()) {
			if (member.getName().equals("attribute")) {
				attributeMember.setMember(member);
				break;
			}
		}
		
		DataInstanceUse attributeMemberData = tdlFactory.eINSTANCE.createDataInstanceUse();
		attributeMemberData.setDataInstance(attributeTdlInstance);
		attributeMember.setMemberSpec(attributeMemberData);
		attributeValueTdlInstance.getMemberAssignment().add(attributeMember);
		testDataPackage.getPackagedElement().add(attributeValueTdlInstance);
		
		return attributeTdlInstance;
	}

	private String validName(String name) {
		final String[] tokenNames = new String[] {
		   "Package", "{", "}", "with", "perform", "action", "(", ",", ")", "on", "test", "objectives", ":", ";", "name", "time", "label", "constraints", "Action", "alternatively", "or", "Annotation", "*", "?", "=", "assert", "otherwise", "set", "verdict", "to", "->", "[", "]", "times", "repeat", "break", "Note", "create", "of", "type", "bind", "Component", "Type", "having", "if", "else", "connect", "as", "Map", "in", ".", "new", "containing", "Use", "Signature", "Collection", "default", "+", "-", "/", "mod", ">", "<", ">=", "<=", "==", "!=", "and", "xor", "not", "size", "Import", "all", "from", "Function", "returns", "instance", "returned", "Predefined", "gate", "Gate", "accepts", "sends", "triggers", "calls", "responds", "response", "interrupt", "optional", "mapped", "omit", "argument", "optionally", "run", "parallel", "parameter", "every", "component", "is", "quiet", "for", "terminate", "where", "it", "assigned", "Test", "Configuration", "Description", "Implementation", "uses", "configuration", "execute", "bindings", "Objective", "description", "Time", "out", "timer", "start", "stop", "variable", "waits", "extends", "SUT", "Tester", "Message", "Procedure", "In", "Out", "Exception", "last", "previous", "first"
		};
		if (Arrays.stream(tokenNames).anyMatch(name::equals)) {
			name = "_"+ name;
		}
		name = name.replaceAll("[^a-zA-Z0-9]", "_");
		return name;
	}
	
	private void savePackages(String tdlProjectPath) throws IOException {
		Injector injector = new TDLan2StandaloneSetup().createInjectorAndDoEMFRegistration();
		ResourceSet rs = injector.getInstance(ResourceSet.class);
		Resource requiredTypesRes = rs.createResource(URI.createURI(tdlProjectPath + this.dslSpecificTypesPackage.getName()+ ".tdlan2"));
		requiredTypesRes.getContents().add(this.dslSpecificTypesPackage);
		requiredTypesRes.save(null);
		
		for (Package p:this.testDataPackages) {
			Resource testDataPackageRes = rs.createResource(URI.createURI(tdlProjectPath + p.getName()+ ".tdlan2"));
			testDataPackageRes.getContents().add(p);
			testDataPackageRes.save(null);
		}
		rs = null;
	}
}
