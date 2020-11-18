package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.SimpleDataInstance;
import org.etsi.mts.tdl.tdlFactory;
import org.etsi.mts.tdl.SimpleDataType;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.AnnotationType;
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
		generateTypeForGeneralEvents();
		generateVerdicts();
		generateAnnotations();
	}
	public void generateDSLSpecificPackage(IFile dslFile){
		Resource res = (new ResourceSetImpl()).getResource(URI.createURI(dslFile.getFullPath().toOSString()), true);
		Dsl dsl = (Dsl) res.getContents().get(0);
		this.dslSpecificPackage = factory.createPackage();
		this.dslSpecificPackage.setName("");
		
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
		
		StructuredDataType OCL = factory.createStructuredDataType();
		OCL.setName("OCL");
		Member query = factory.createMember();
		query.setName("query");
		query.setDataType(String);
		OCL.getMember().add(query);
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
	public void savePackageIntoFile(String dslName) {
		// Register the XMI resource factory for the .tdlan2 extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("tdl", new XMIResourceFactoryImpl());
        
        // Obtain a new resource set
        ResourceSet resSet = new ResourceSetImpl();

        // create a resource
        Resource commonPackageResource = resSet.createResource(URI.createURI("commonPackage.tdl"));
        //Resource dslSpecificPackageResource = resSet.createResource(URI.createURI(dslName + "SpecificPackage.tdlan2"));
        // Get the first model element and cast it to the right type
        commonPackageResource.getContents().add(this.commonPackage);
        //dslSpecificPackageResource.getContents().add(this.dslSpecificPackage);
        //save the content.
        try {
            commonPackageResource.save(Collections.EMPTY_MAP);
           // dslSpecificPackageResource.save(Collections.EMPTY_MAP);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
