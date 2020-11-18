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
		generatePrimitiveDataTypes();
		generateTypeForGeneralEvents();
		generateVerdicts();
		generateAnnotations();
	}
	public void generateDSLSpecificPackage(IFile dslFile){
		Resource res = (new ResourceSetImpl()).getResource(URI.createURI(dslFile.getFullPath().toOSString()), true);
		Dsl dsl = (Dsl) res.getContents().get(0);
		this.dslSpecificPackage = factory.createPackage();
		
	}
	private void generatePrimitiveDataTypes() {
		SimpleDataType String = factory.createSimpleDataType();
		SimpleDataType Integer = factory.createSimpleDataType();
		SimpleDataType Boolean = factory.createSimpleDataType();
		this.commonPackage.getPackagedElement().add(String);
		this.commonPackage.getPackagedElement().add(Integer);
		this.commonPackage.getPackagedElement().add(Boolean);
		
		StructuredDataType OCL = factory.createStructuredDataType();
		Member query = factory.createMember();
		query.setDataType(String);
		OCL.getMember().add(query);
	}
	private void generateTypeForGeneralEvents() {
		SimpleDataType runModel = factory.createSimpleDataType();
		SimpleDataInstance runMUT = factory.createSimpleDataInstance();
		runMUT.setDataType(runModel);
		this.commonPackage.getPackagedElement().add(runModel);
		this.commonPackage.getPackagedElement().add(runMUT);
		
		SimpleDataType getState = factory.createSimpleDataType();
		SimpleDataInstance getModelState = factory.createSimpleDataInstance();
		runMUT.setDataType(getState);
		this.commonPackage.getPackagedElement().add(getState);
		this.commonPackage.getPackagedElement().add(getModelState);
	}
	private void generateVerdicts() {
		SimpleDataType Verdict = factory.createSimpleDataType();
		SimpleDataInstance PASS = factory.createSimpleDataInstance();
		SimpleDataInstance FAIL = factory.createSimpleDataInstance();
		PASS.setDataType(Verdict);
		FAIL.setDataType(Verdict);
		this.commonPackage.getPackagedElement().add(Verdict);
		this.commonPackage.getPackagedElement().add(PASS);
		this.commonPackage.getPackagedElement().add(FAIL);
	}
	private void generateAnnotations() {
		AnnotationType GIVEN = factory.createAnnotationType();
		AnnotationType WHEN = factory.createAnnotationType();
		AnnotationType THEN = factory.createAnnotationType();
		AnnotationType MUTPath = factory.createAnnotationType();
		this.commonPackage.getPackagedElement().add(GIVEN);
		this.commonPackage.getPackagedElement().add(WHEN);
		this.commonPackage.getPackagedElement().add(THEN);
		this.commonPackage.getPackagedElement().add(MUTPath);
	}
	public void savePackageIntoFile(String dslName) {
		// Register the XMI resource factory for the .tdlan2 extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("tdlan2", new XMIResourceFactoryImpl());
        
        // Obtain a new resource set
        ResourceSet resSet = new ResourceSetImpl();

        // create a resource
        Resource commonPackageResource = resSet.createResource(URI.createURI("commonPackage.tdlan2"));
        //Resource dslSpecificPackageResource = resSet.createResource(URI.createURI(dslName + "SpecificPackage.tdlan2"));
        // Get the first model element and cast it to the right type
        commonPackageResource.getContents().add(this.commonPackage);
        //dslSpecificPackageResource.getContents().add(this.dslSpecificPackage);
        // now save the content.
        try {
            commonPackageResource.save(Collections.EMPTY_MAP);
           // dslSpecificPackageResource.save(Collections.EMPTY_MAP);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
