package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.codegen.ecore.*;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.ale.core.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.dsl.Entry;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.PackageableElement;
import org.etsi.mts.tdl.SimpleDataInstance;
import org.etsi.mts.tdl.tdlFactory;
import org.etsi.mts.tdl.util.tdlResourceFactoryImpl;
import org.etsi.mts.tdl.SimpleDataType;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.AnnotationType;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.Member;

public class TdlPackageGenerator {
	private tdlFactory factory;
	private Package commonPackage;
	private Package dslSpecificPackage;

	public TdlPackageGenerator() {
		this.factory = tdlFactory.eINSTANCE;
	}
	public void generateCommonPackage(){
		this.commonPackage = factory.createPackage();
		this.commonPackage.setName("commonPackage");
		generatePrimitiveDataTypes();
		generateTypeForOCL();
		generateVerdicts();
		generateTypeForGeneralEvents();
		generateAnnotations();
	}
	public void generateDSLSpecificPackage(String dslName){
		this.dslSpecificPackage = factory.createPackage();
		this.dslSpecificPackage.setName(dslName + "SpecificPackage");
		generateDSLSpecificTypes();
		generateTypeForModelState();
		generateTypeForDSLInterfaces();
		generateConfiguration();
	}
	private void generatePrimitiveDataTypes() {
		SimpleDataType String = factory.createSimpleDataType();
		String.setName("String");
		SimpleDataType Integer = factory.createSimpleDataType();
		Integer.setName("Integer");
		SimpleDataType Boolean = factory.createSimpleDataType();
		Boolean.setName("Boolean");
		this.commonPackage.getPackagedElement().add(String);
		this.commonPackage.getPackagedElement().add(Integer);
		this.commonPackage.getPackagedElement().add(Boolean);
	}
	private void generateTypeForOCL() {
		StructuredDataType OCL = factory.createStructuredDataType();
		OCL.setName("OCL");
		Member query = factory.createMember();
		query.setName("query");
		DataType queryType = (DataType) getPackageableElement(this.commonPackage, "String");
		query.setDataType(queryType);
		OCL.getMember().add(query);
		this.commonPackage.getPackagedElement().add(OCL);
	}
	private void generateVerdicts() {
		SimpleDataType Verdict = factory.createSimpleDataType();
		Verdict.setName("Verdict");
		SimpleDataInstance PASS = factory.createSimpleDataInstance();
		PASS.setName("PASS");
		PASS.setDataType(Verdict);
		SimpleDataInstance FAIL = factory.createSimpleDataInstance();
		FAIL.setName("FAIL");
		FAIL.setDataType(Verdict);
		SimpleDataInstance INCONCLUSINVE = factory.createSimpleDataInstance();
		INCONCLUSINVE.setName("INCONCLUSINVE");
		INCONCLUSINVE.setDataType(Verdict);
		this.commonPackage.getPackagedElement().add(Verdict);
		this.commonPackage.getPackagedElement().add(PASS);
		this.commonPackage.getPackagedElement().add(FAIL);
		this.commonPackage.getPackagedElement().add(INCONCLUSINVE);
	}
	private void generateTypeForGeneralEvents() {
		SimpleDataType runModel = factory.createSimpleDataType();
		runModel.setName("runModel");
		SimpleDataInstance runMUT = factory.createSimpleDataInstance();
		runMUT.setName("runMUT");
		runMUT.setDataType(runModel);
		this.commonPackage.getPackagedElement().add(runModel);
		this.commonPackage.getPackagedElement().add(runMUT);
		
		SimpleDataType getState = factory.createSimpleDataType();
		getState.setName("getState");
		SimpleDataInstance getModelState = factory.createSimpleDataInstance();
		getModelState.setName("getModelState");
		runMUT.setDataType(getState);
		this.commonPackage.getPackagedElement().add(getState);
		this.commonPackage.getPackagedElement().add(getModelState);
	}
	private void generateAnnotations() {
		AnnotationType GIVEN = factory.createAnnotationType();
		GIVEN.setName("GIVEN");
		AnnotationType WHEN = factory.createAnnotationType();
		WHEN.setName("WHEN");
		AnnotationType THEN = factory.createAnnotationType();
		THEN.setName("THEN");
		AnnotationType MUTPath = factory.createAnnotationType();
		MUTPath.setName("MUTPath");
		this.commonPackage.getPackagedElement().add(GIVEN);
		this.commonPackage.getPackagedElement().add(WHEN);
		this.commonPackage.getPackagedElement().add(THEN);
		this.commonPackage.getPackagedElement().add(MUTPath);
	}
	private void generateDSLSpecificTypes() {
		
	}
	private void generateTypeForModelState() {
		
	}
	private void generateTypeForDSLInterfaces() {
		
	}
	private void generateConfiguration() {
		
		
	}
	private PackageableElement getPackageableElement (Package targetPackage, String elementName) {
		List<PackageableElement> elements = new ArrayList<PackageableElement>();
		elements.addAll(targetPackage.getPackagedElement());
		for (int i = 0; i< elements.size();i++) {
			if (elements.get(i).getName().equals(elementName)) {
				return elements.get(i);
			}
		}
		return null;
	}
	protected static EPackage getDslMetamodelRootElement(IFile dslFile) {
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
	private BehavioralInterface getBehavioralInterfaceRootElement(String interfaceName) {
		String extension = "bi";
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        ResourceSetImpl factory = new ResourceSetImpl();
        m.put(extension, factory);
        Resource interfaceResource = factory.getResource(URI.createURI(interfaceName + ".bi"), true);
        BehavioralInterface interfaceRootElement = (BehavioralInterface)interfaceResource.getContents().get(0);
        return interfaceRootElement;
	}
	public void savePackageIntoFile(String dslName) {
		String extension = "tdl";
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        tdlResourceFactoryImpl factory = new tdlResourceFactoryImpl();
        m.put(extension, factory);
       
        // create a resource
        Resource commonPackageResource = factory.createResource(URI.createURI("commonPackage.tdl"));
        //Resource dslSpecificPackageResource = resSet.createResource(URI.createURI(dslName + "SpecificPackage.tdlan2"));
        // Get the first model element and cast it to the right type
        commonPackageResource.getContents().add(this.commonPackage);
        Map options = new HashMap<>();
	    options.put(XMIResourceImpl.OPTION_ENCODING, "UTF-8");
        //save the content.
        try {
            commonPackageResource.save(options);
           // dslSpecificPackageResource.save(Collections.EMPTY_MAP);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
