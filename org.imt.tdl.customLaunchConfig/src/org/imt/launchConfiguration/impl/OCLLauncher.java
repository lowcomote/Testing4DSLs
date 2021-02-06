package org.imt.launchConfiguration.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.helper.OCLHelper;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.pivot.labels.DefaultLabelGeneratorBuilder;
import org.eclipse.ocl.pivot.labels.ILabelGenerator;
import org.eclipse.ocl.pivot.labels.ILabelGenerator.Registry;

public class OCLLauncher {

	protected OCL ocl = null;
	protected OCLHelper oclHelper = null;

	protected OCLExpression<EClassifier> expression = null;
	protected Query<EClassifier, EClass, EObject> queryEval = null;
	
	private Object[] resultAsObject = null;
	private ArrayList<String> resultAsString = new ArrayList<>();

	public void setUp() {
		this.ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
		this.oclHelper = ocl.createOCLHelper();
	}

	public void runQuery(Resource MUTResource, String query) throws ParserException {
		// The root element of the dsl is the context for ocl
		this.oclHelper.setContext(MUTResource.getContents().get(0).eClass());
		this.expression = this.oclHelper.createQuery(query);
		this.queryEval = this.ocl.createQuery(this.expression);
		// the ocl query will be evaluated on the root element of MUT
		Object res = this.queryEval.evaluate(MUTResource.getContents().get(0));
		if (res instanceof Collection<?>) {
			if (res instanceof LinkedHashSet<?>) {
				LinkedHashSet<?> queryResult =  (LinkedHashSet<?>) res;
				this.resultAsObject = queryResult.toArray();
				Iterator it = queryResult.iterator();
				while (it.hasNext()) {
					EObject object = (EObject) it.next();
					this.resultAsString.add(queryResultLabelProvider(object));
				}
			}else if (res instanceof ArrayList<?>) {
				ArrayList<?> queryResult =  (ArrayList<?>) res;
				this.resultAsObject = queryResult.toArray();
				for (int i = 0; i < queryResult.size(); i++) {
					if (queryResult.get(i)== null) {
						this.resultAsString.add("null");
					}else {
						this.resultAsString.add("'" + queryResult.get(i).toString() + "'");
					}
				}
			}
		}else {
			if (res instanceof EObject) {
				EObject object = (EObject) res;
				this.resultAsObject[0] = object;
				this.resultAsString.add(queryResultLabelProvider(object));
			}else {
				this.resultAsObject[0] = this.resultAsObject.toString();
				this.resultAsString.add("'" + this.resultAsObject.toString() + "'");
			}
		}
	}
	public String queryResultLabelProvider(EObject object) {
		final Class<?> IItemLabelProviderClass = IItemLabelProvider.class;
		final Class<?> ITreeItemContentProviderClass = ITreeItemContentProvider.class;
		ArrayList<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		factories.add(new ResourceItemProviderAdapterFactory());
		factories.add(new EcoreItemProviderAdapterFactory());
		factories.add(new ReflectiveItemProviderAdapterFactory());
		
		ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(factories);
	    IItemLabelProvider itemLabelProvider  ;  
	    ITreeItemContentProvider treeItemContentProvider ;
    	AdapterFactory adapterFactory = composedAdapterFactory;
    	
    	itemLabelProvider = (IItemLabelProvider)adapterFactory.adapt(object, IItemLabelProviderClass);
	    String objectLabel = itemLabelProvider.getText(object) ;
	    
	    treeItemContentProvider = (ITreeItemContentProvider)adapterFactory.adapt(object, ITreeItemContentProviderClass);
        Object container = treeItemContentProvider.getParent(object) ; 
        itemLabelProvider = (IItemLabelProvider)adapterFactory.adapt(container, IItemLabelProviderClass);
        String containerLabel = itemLabelProvider.getText(container);
        
		return (containerLabel + "::" + objectLabel);
	}
	public ArrayList<String> getResultAsString(){
		return this.resultAsString;
	}
	public Object[] getResultAsObject() {
		return this.resultAsObject;
	}
	public void tearDown() throws Exception {
		this.oclHelper = null;
		this.ocl = null;
		this.expression = null;
		this.queryEval = null;
		Runtime.getRuntime().gc();
	}
}
