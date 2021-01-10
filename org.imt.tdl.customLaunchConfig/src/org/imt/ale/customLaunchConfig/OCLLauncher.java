package org.imt.ale.customLaunchConfig;

import java.util.jar.Manifest;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.jar.Attributes;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;
import org.imt.bpmn.bpmnPackage;

public class OCLLauncher {
	
	protected OCL ocl = null;
    protected OCLHelper oclHelper = null;
    
    protected OCLExpression<EClassifier> expression = null;
    protected Query<EClassifier, EClass, EObject> queryEval = null;
    
    public void setUp(String DSLPath) {
    	this.ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
        this.oclHelper = ocl.createOCLHelper();
        configureOCLBasedOnDSL(DSLPath);
    }
    public Object runQuery(Resource MUTResource, String query) throws ParserException {
    	//The root element of the dsl is the context for ocl
    	//TODO: Define the context programmatically
    	this.oclHelper.setContext(bpmnPackage.Literals.MICROFLOW);
        try {
            this.expression = this.oclHelper.createQuery(query);
        } catch (ParserException e) {
            e.printStackTrace();
            throw e;
        }
        this.queryEval = this.ocl.createQuery(this.expression);
        //the ocl query will be evaluated on the root element of MUT (e.g, Microflow element in BPMN Example)
        Object res = this.queryEval.evaluate(MUTResource.getContents().get(0));
        return res;
    }
    public void tearDown() throws Exception {   
        this.oclHelper = null;
        this.ocl = null;
        this.expression = null;
        this.queryEval = null;
        Runtime.getRuntime().gc();
    }
    
    private void configureOCLBasedOnDSL(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		if (dsl.getEntry("ecore") != null) {
			String metamodelPath = dsl.getEntry("ecore").getValue().replaceFirst("resource", "plugin");
			Resource metamodelRes = (new ResourceSetImpl()).getResource(URI.createURI(metamodelPath), true);
			EPackage metamodelRootPackage = (EPackage) metamodelRes.getContents().get(0);
			//finding the root class of the ecore file-> using the first class of the metamodel as the argument
			EClassifier metamodelRootClass = (EClassifier) metamodelRootPackage.getEClassifiers().get(0);
		}
	}
    private void updateManifest() throws Exception{
    	try{
    		String fileName = "C:\\Documents and Settings\\Administrator.ARDEN\\branchWorkspace\\org.jvnet.ja xbw.eclipse\\testData\\Manifest.mf ";
    		ManifestChanger manifestChanger = new ManifestChanger();
    		manifestChanger.addPluginDependency("MyPlugin", "1.5.0", false, true);
    		/*FileInputStream in = new FileInputStream(fileName);
    		manifestChanger.loadManifest(in);
    		in.close();
    		manifestChanger.addPluginDependency("MyPlugin", "1.5.0", false, true);
    		FileOutputStream out = new FileOutputStream(fileName);
    		manifestChanger.writeManifest(out);
    		out.close();*/
    	}catch (Throwable t){
    		System.err.println("Unexpected Exception: " + t);
    		t.printStackTrace();
    	}
    }
}
