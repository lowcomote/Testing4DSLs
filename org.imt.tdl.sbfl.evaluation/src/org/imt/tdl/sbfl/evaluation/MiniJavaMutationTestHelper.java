package org.imt.tdl.sbfl.evaluation;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.etsi.mts.tdl.Annotation;
import org.etsi.mts.tdl.ComponentInstance;
import org.etsi.mts.tdl.TestDescription;
import org.imt.minijava.xminiJava.ArrayTypeRef;
import org.imt.minijava.xminiJava.Class;
import org.imt.minijava.xminiJava.Member;
import org.imt.minijava.xminiJava.Method;
import org.imt.minijava.xminiJava.Program;
import org.imt.minijava.xminiJava.StringTypeRef;
import org.imt.minijava.xminiJava.TypeDeclaration;

public class MiniJavaMutationTestHelper {

	//the minijava code of the test case is in the 'main' method of minijava model and must be added to the mutant to be able to run test case
	//the path to the minijava model is provided in the 'MUTPath' annotation of the test case's configuration
	public void addMainClassToMutant(String mutantPath, TestDescription testCase) {
		ComponentInstance sutComponent = testCase.getTestConfiguration().getComponentInstance().stream().
				filter(ci -> ci.getRole().toString().equals("SUT")).findFirst().get();
		String MUTPath = "";
		for (Annotation a:sutComponent.getAnnotation()){
			if (a.getKey().getName().equals("MUTPath")){
				MUTPath = a.getValue().substring(1, a.getValue().length()-1);
				break;
			}
		}
		Resource miniJaveResource = (new ResourceSetImpl()).getResource(URI.createURI("platform:/resource" + MUTPath), true);
		Program miniJavaPackage = (Program) miniJaveResource.getContents().get(0);
		Class mainClass = getMainClass(miniJavaPackage);
		if (mainClass != null) {
			Resource mutantResource = (new ResourceSetImpl()).getResource(URI.createURI("platform:/resource" + mutantPath), true);
			//add main class to mutant
			Program mutantPackage = (Program) mutantResource.getContents().get(0);
			mutantPackage.getClasses().add(mainClass);
			try {
				mutantResource.save(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void removeMainClassFromMutant(String mutantPath) {
		Resource mutantResource = (new ResourceSetImpl()).getResource(URI.createURI("platform:/resource" + mutantPath), true);
		Program miniJavaPackage = (Program) mutantResource.getContents().get(0);
		Class mainClass = getMainClass(miniJavaPackage);
		if (mainClass != null) {
			removeMainClass(mutantResource, miniJavaPackage, mainClass);
			try {
				mutantResource.save(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Class getMainClass(Program miniJavaPackage) {
		for (TypeDeclaration cclass: miniJavaPackage.getClasses()) {
			if (cclass instanceof Class) {
				Class javaClass = (Class) cclass;
				for (Member member:javaClass.getMembers()) {
					if (member instanceof Method) {
						Method method = (Method) member;
						if (method.getName().equals("main") && method.isStatic() && 
								(method.getParams().get(0).getTypeRef() instanceof ArrayTypeRef) &&
								(((ArrayTypeRef) method.getParams().get(0).getTypeRef()).getTypeRef() instanceof StringTypeRef)) {
							return javaClass;
						}
					}
				}
			}
		}
		return null;
	}
	
	public void removeMainClass (Resource MUTResource, Program javaPackage, Class javaClass) {
		if (MUTResource.getResourceSet() == null) {
			javaPackage.getClasses().remove(javaClass);
		}
		else {
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(javaPackage);
			try{
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						javaPackage.getClasses().remove(javaClass);	
					}
		   		});
	   		}catch(IllegalArgumentException e){
				e.printStackTrace();
			}
		}
	}
}
