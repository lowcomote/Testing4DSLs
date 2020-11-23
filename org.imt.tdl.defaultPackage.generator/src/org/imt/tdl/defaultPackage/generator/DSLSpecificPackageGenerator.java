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
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.tdlFactory;
import org.etsi.mts.tdl.util.tdlResourceFactoryImpl;

public class DSLSpecificPackageGenerator {
	private tdlFactory factory;
	private Package dslSpecificPackage;
	private CommonPackageGenerator commonPackageGenerator;
	
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	private DataType modelState;
	private List<DataType> dslInterfaceTypes = new ArrayList<DataType>();
	private DataType TypeForGeneralEvents;
	
	public DSLSpecificPackageGenerator(String dslName) {
		this.factory = tdlFactory.eINSTANCE;
		this.commonPackageGenerator = new CommonPackageGenerator();
		generateDSLSpecificPackage(dslName);
		saveDSLSpecificPackageIntoFile(dslName);
	}
	
	public void generateDSLSpecificPackage(String dslName){
		this.dslSpecificPackage = factory.createPackage();
		this.dslSpecificPackage.setName(dslName + "SpecificPackage");
		generateImports();
		generateDSLSpecificTypes();
		generateTypeForModelState();
		generateTypeForDSLInterfaces();
		generateTypeForSetState();
	}
	private void generateImports() {
		ElementImport commonPackageImport = factory.createElementImport();
		commonPackageImport.setImportedPackage(this.commonPackageGenerator.getCommonPackage());
		this.dslSpecificPackage.getImport().add(commonPackageImport);
	}
	private void generateDSLSpecificTypes() {
		
	}
	private void generateTypeForModelState() {
		
	}
	private void generateTypeForDSLInterfaces() {
		
	}
	private void generateTypeForSetState() {
		
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
	public void saveDSLSpecificPackageIntoFile(String dslName) {
		String extension = "tdl";
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        tdlResourceFactoryImpl factory = new tdlResourceFactoryImpl();
        m.put(extension, factory);
       
        // create a resource
        Resource dslSpecificPackageResource = factory.createResource(URI.createURI(this.dslSpecificPackage.getName() + ".tdlan2"));
        // Get the first model element and cast it to the right type
        dslSpecificPackageResource.getContents().add(this.dslSpecificPackage);
        Map options = new HashMap<>();
	    options.put(XMIResourceImpl.OPTION_ENCODING, "UTF-8");
        //save the content.
        try {
            dslSpecificPackageResource.save(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
