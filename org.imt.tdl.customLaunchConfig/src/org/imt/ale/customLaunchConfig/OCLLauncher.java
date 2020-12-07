package org.imt.ale.customLaunchConfig;

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

public class OCLLauncher {
	
	protected OCL ocl = null;
    protected OCLHelper oclHelper = null;
    
    protected OCLExpression<EClassifier> expression = null;
    protected Query<EClassifier, EClass, EObject> query = null;
    
    public void setUp() {
    	this.ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
        this.oclHelper = ocl.createOCLHelper();
    }
    public Object runQuery(Resource MUTResource, String query) throws ParserException {
    	this.oclHelper.setContext(MUTResource);
        try {
            this.expression = this.oclHelper.createQuery(query);
        } catch (ParserException e) {
            e.printStackTrace();
            throw e;
        }
        this.query = this.ocl.createQuery(this.expression);
        Object res = this.query.evaluate();
        return res;
    }
    public void tearDown() throws Exception {   
        this.oclHelper = null;
        this.ocl = null;
        this.expression = null;
        this.query = null;
        Runtime.getRuntime().gc();
    }
}
