package org.imt.ale.customLaunchConfig;

import java.util.List;

import org.eclipse.emf.ecore.EClass;



import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;
import org.imt.bpmn.Microflow;
import org.imt.bpmn.bpmnPackage;

public class OCLLauncher {
	
	protected OCL ocl = null;
    protected OCLHelper oclHelper = null;
    
    protected OCLExpression<EClassifier> expression = null;
    protected Query<EClassifier, EClass, EObject> queryEval = null;
    
    public void setUp() {
    	this.ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
        this.oclHelper = ocl.createOCLHelper();
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
        //define the source that the ocl query have to be evaluated on it
        //TODO: Define the source programmatically
        Microflow rootMUTMicroflow = (Microflow) MUTResource.getContents().get(0);
        Object res = this.queryEval.evaluate(rootMUTMicroflow);
        return res;
    }
    public void tearDown() throws Exception {   
        this.oclHelper = null;
        this.ocl = null;
        this.expression = null;
        this.queryEval = null;
        Runtime.getRuntime().gc();
    }
}
