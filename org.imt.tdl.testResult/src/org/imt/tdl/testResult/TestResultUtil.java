package org.imt.tdl.testResult;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;

public class TestResultUtil {
	
	private static TestResultUtil instance = new TestResultUtil();
	private TDLTestPackageResult testResult;
	
	public static final String PASS = "PASS";
	public static final String FAIL = "FAIL";
	public static final String INCONCLUSIVE = "INCONCLUSIVE";

	   //make the constructor private so that this class cannot be
	   //instantiated
	   private TestResultUtil(){}

	   //Get the only object available
	   public static TestResultUtil getInstance(){
	      return instance;
	   }
	   public TDLTestPackageResult getTestPackageResult() {
		   return instance.testResult;
	   }
	   public void setTestPackageResult(TDLTestPackageResult result) {
		   instance.testResult = result;
	   }
	   public String eObjectLabelProvider(EObject object) {
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
	   public String getDataAsString(List<?> list){
			String data = "[";
			for (int i=0; i<list.size(); i++){
				data += eObjectLabelProvider((EObject)list.get(i));
				if (i < list.size()-1){
					data += ", ";
				}
			}
			data += "]";
			return data;
		}
}
