package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
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
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.TDLan2StandaloneSetup;
import org.etsi.mts.tdl.tdlFactory;
import org.etsi.mts.tdl.util.tdlResourceFactoryImpl;

import com.google.inject.Injector;

public class DSLSpecificPackageGenerator {
	private String dslName;
	private IFile dslFile;
	private EPackage metamodelRootElement;
	private EPackage aleSemanticRootElement;
	private BehavioralInterface interfaceRootElement;
	
	private tdlFactory factory;
	private Package dslSpecificPackage;
	private CommonPackageGenerator commonPackageGenerator;
	
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	private DataType modelState;
	private List<DataType> dslInterfaceTypes = new ArrayList<DataType>();
	private DataType TypeForGeneralEvents;
	
	public DSLSpecificPackageGenerator(IFile dslFile) {
		this.factory = tdlFactory.eINSTANCE;
		this.commonPackageGenerator = new CommonPackageGenerator();
		this.dslName = getDslName(dslFile);
		this.metamodelRootElement = getMetamodelRootElement(dslFile);
		this.aleSemanticRootElement = getAleSemanticsRootElement(dslFile);
		this.interfaceRootElement = getBehavioralInterfaceRootElement(dslFile);
		generateDSLSpecificPackage();
	}
	
	public void generateDSLSpecificPackage(){
		this.dslSpecificPackage = factory.createPackage();
		this.dslSpecificPackage.setName(this.dslName + "SpecificPackage");
		generateImports();
		generateDSLSpecificTypes();
		generateTypeForModelState();
		if (this.interfaceRootElement != null) {
			generateTypeForDSLInterfaces();
		}
		generateTypeForSetState();
	}
	private void generateImports() {
		ElementImport commonPackageImport = factory.createElementImport();
		commonPackageImport.setImportedPackage(this.commonPackageGenerator.getCommonPackage());
		this.dslSpecificPackage.getImport().add(commonPackageImport);
	}
	private void generateDSLSpecificTypes() {
		//TODO: Which entities of metamodel have to be transformed to TDL types?
	}
	private void generateTypeForDSLInterfaces() {
		AnnotationType acceptedEvent = factory.createAnnotationType();
		acceptedEvent.setName("AcceptedEvent");
		AnnotationType exposedEvent = factory.createAnnotationType();
		exposedEvent.setName("ExposedEvent");
		this.dslSpecificPackage.getPackagedElement().add(acceptedEvent);
		this.dslSpecificPackage.getPackagedElement().add(exposedEvent);
		
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
				//the parameters are the metamodel elements that have to be transformed to TDL types
			}
		}
	}
	private void generateTypeForModelState() {
		StructuredDataType modelState = factory.createStructuredDataType();
		modelState.setName("modelState");
		//TODO: Define the members based on the model state
		this.dslSpecificPackage.getPackagedElement().add(modelState);
		this.modelState = modelState;
	}
	private void generateTypeForSetState() {
		StructuredDataType setState = factory.createStructuredDataType();
		setState.setName("setState");
		Member state = factory.createMember();
		state.setName("state");
		state.setDataType(this.modelState);
		setState.getMember().add(state);
		this.dslSpecificPackage.getPackagedElement().add(setState);
		this.TypeForGeneralEvents = setState;
	}
	public CommonPackageGenerator getCommonPackageGenerator() {
		return this.commonPackageGenerator;
	}
	public Package getDSLSpecificPackage() {
		return this.dslSpecificPackage;
	}
	public Map<String, DataType> getDslSpecificTypes(){
		return this.dslSpecificTypes;
	}
	public DataType getTypeOfModelState() {
		return this.modelState;
	}
	public List<DataType> getTypesOfDslInterfaces(){
		return this.dslInterfaceTypes;
	}
	public DataType getTypeOfGeneralEvents() {
		return this.TypeForGeneralEvents;
	}
	
	protected String getDslName(IFile dslFile) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFile.getFullPath().toOSString()), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String dslFullName = dsl.getEntry("name").getValue();
		String[] nameParts = dslFullName.split(".");
		return nameParts[nameParts.length-1];
	}
	protected static EPackage getMetamodelRootElement(IFile dslFile) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFile.getFullPath().toOSString()), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String metamodelPath = dsl.getEntry("ecore").getValue();
		Resource metamodelRes = (new ResourceSetImpl()).getResource(URI.createURI(metamodelPath), true);
		EPackage metamodelRootElement = (EPackage) metamodelRes.getContents().get(0);
		return metamodelRootElement;
	}
	//TODO: has to be changed
	protected static EPackage getAleSemanticsRootElement(IFile dslFile) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFile.getFullPath().toOSString()), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String interpreterPath = dsl.getEntry("ale").getValue();
		Resource interpreterRes = (new ResourceSetImpl()).getResource(URI.createURI(interpreterPath), true);
		EPackage interpreterRootClass = (EPackage) interpreterRes.getContents().get(0);
		return interpreterRootClass;
	}
	private BehavioralInterface getBehavioralInterfaceRootElement(IFile dslFile) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFile.getFullPath().toOSString()), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String interfacePath = dsl.getEntry("behavioralInterface").getValue();
		Resource interfaceRes = (new ResourceSetImpl()).getResource(URI.createURI(interfacePath), true);
		BehavioralInterface interfaceRootElement = (BehavioralInterface) interfaceRes.getContents().get(0);
		return interfaceRootElement;
	}
	public void savePackage(Injector injector, ResourceSet rs) {
		this.commonPackageGenerator.savePackage(injector, rs);
		Resource r = rs.createResource(URI.createURI(this.dslSpecificPackage.getName()+ ".tdlan2"));
		r.getContents().add(this.dslSpecificPackage);
		try {
			r.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		r.unload();
		rs = null;
	}
}
