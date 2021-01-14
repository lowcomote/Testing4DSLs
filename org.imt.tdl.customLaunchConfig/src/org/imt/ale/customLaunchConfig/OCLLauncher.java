package org.imt.ale.customLaunchConfig;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;

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
    	this.oclHelper.setContext(MUTResource.getContents().get(0).eClass());
        this.expression = this.oclHelper.createQuery(query);
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
}
