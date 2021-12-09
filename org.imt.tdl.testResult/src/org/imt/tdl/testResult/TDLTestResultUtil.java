package org.imt.tdl.testResult;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.etsi.mts.tdl.Behaviour;
import org.etsi.mts.tdl.CompoundBehaviour;
import org.etsi.mts.tdl.Message;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.PackageableElement;
import org.etsi.mts.tdl.TestDescription;
import org.imt.tdl.TDLTestReport.TDLTestReportFactory;
import org.imt.tdl.TDLTestReport.TestCaseResult;
import org.imt.tdl.TDLTestReport.TestMessageResult;
import org.imt.tdl.TDLTestReport.TestSuiteResult;
import org.imt.tdl.TDLTestReport.Verdicts;

public class TDLTestResultUtil {
	
	private static TDLTestResultUtil instance = new TDLTestResultUtil();
	private TDLTestSuiteResult testSuiteResult;
	
	public static final String PASS = "PASS";
	public static final String FAIL = "FAIL";
	public static final String INCONCLUSIVE = "INCONCLUSIVE";

   //make the constructor private so that this class cannot be
   //instantiated
   private TDLTestResultUtil(){}

   //Get the only object available
   public static TDLTestResultUtil getInstance(){
      return instance;
   }
   public TDLTestSuiteResult getTestSuiteResult() {
	   return instance.testSuiteResult;
   }
   public void setTestSuiteResult(TDLTestSuiteResult result) {
	   instance.testSuiteResult = result;
	   //saveTestReport();
   }
   
   private void saveTestReport() {
	   //TODO: Save the result under gemoc-gen folder
	   ResourceSet resSet = new ResourceSetImpl();
	   //copy the test suite as a seperate resource to be referenced by the test result file
	   Resource testSutieResource = instance.testSuiteResult.getTestSuite().eResource();
	   String testSuiteURI = testSutieResource.getURI().toString();
	   String testResultFolderPath = testSuiteURI.substring(0, testSuiteURI.lastIndexOf("/")) + "/test-result-gen/";
	   String testFileName = testSuiteURI.substring(testSuiteURI.lastIndexOf("/"));
	   Resource testSutieResourceCopy = resSet.createResource(URI.createURI(testResultFolderPath + testFileName));
	   testSutieResourceCopy.getContents().add(testSutieResource.getContents().get(0));
	   EcoreUtil.resolveAll(testSutieResourceCopy);
	   
	   //create test result according to teh TDLTestReport.ecore structure
	   Package copiedTestSuite = (Package) testSutieResourceCopy.getContents().get(0);
	   TestSuiteResult testSuiteResult = TDLTestReportFactory.eINSTANCE.createTestSuiteResult();
	   testSuiteResult.setTestSuite(copiedTestSuite);
	   for (TDLTestCaseResult tcResultObject : instance.testSuiteResult.getTestCaseResults()) {
		   String testCaseName = tcResultObject.getTestCaseName();
		   Optional<PackageableElement> optionalTC = copiedTestSuite.getPackagedElement().stream().filter(p -> p instanceof TestDescription).
			filter(t -> t.getName().equals(testCaseName)).findFirst();
		   if (optionalTC.isPresent()) {
			   TestDescription copiedTestCase = (TestDescription) optionalTC.get();
			   TestCaseResult testCaseResult = TDLTestReportFactory.eINSTANCE.createTestCaseResult();
			   testCaseResult.setTestCase(copiedTestCase);
			   String testCaseVerdict = tcResultObject.getValue();
			   if (testCaseVerdict == TDLTestResultUtil.PASS) {
				   testCaseResult.setValue(Verdicts.PASS);
			   }else if (testCaseVerdict == TDLTestResultUtil.FAIL) {
				   testCaseResult.setValue(Verdicts.FAIL);
			   }else if (testCaseVerdict == TDLTestResultUtil.INCONCLUSIVE) {
				   testCaseResult.setValue(Verdicts.INCONCLUSIVE);
			   }
			   testCaseResult.setDescription(tcResultObject.getDescription());
			   for (int i=0; i<tcResultObject.getTdlMessages().size(); i++) {
				   TDLMessageResult messageResultObject = tcResultObject.getTdlMessages().get(i);
				   TestMessageResult testMessageResult = TDLTestReportFactory.eINSTANCE.createTestMessageResult();
				   testMessageResult.setMessage(findEquivalentTDLMessage(copiedTestCase, i));
				   String testMsgVerdict = messageResultObject.getValue();
				   if (testMsgVerdict == TDLTestResultUtil.PASS) {
					   testMessageResult.setValue(Verdicts.PASS);
				   }else if (testMsgVerdict == TDLTestResultUtil.FAIL) {
					   testMessageResult.setValue(Verdicts.FAIL);
				   }else if (testMsgVerdict == TDLTestResultUtil.INCONCLUSIVE) {
					   testMessageResult.setValue(Verdicts.INCONCLUSIVE);
				   }
				   testMessageResult.setDescription(messageResultObject.getDescription());
				   testMessageResult.setTdlMessageId(messageResultObject.getTdlMessageId());
				   testCaseResult.getTestMessageResults().add(testMessageResult);
			   }
			   testSuiteResult.getTestCaseResults().add(testCaseResult);
		   }
	   }
	   
	   //create a resource for the test result
	   String testResultPath = testResultFolderPath + testFileName.substring(0, testFileName.indexOf(".")) +"-Report.xmi";
	   Resource testResultResource = resSet.createResource(URI.createURI(testResultPath));
	   testResultResource.getContents().add(testSuiteResult);
	   //saving resources
	   try {
			testSutieResourceCopy.save(null);
			testResultResource.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }

	private Message findEquivalentTDLMessage(TestDescription copiedTestCase, int index) {
		EList<Behaviour> behaviors =  ((CompoundBehaviour) copiedTestCase.getBehaviourDescription().getBehaviour()).getBlock().getBehaviour();
		Object[] messages = behaviors.stream().filter(b -> b instanceof Message).toArray();
		if (messages.length > index && messages[index] instanceof Message) {
			return (Message) messages[index];
		}
		return null;
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
