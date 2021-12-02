package org.imt.xminijava.coverage;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.imt.minijava.xminiJava.Program;
import org.imt.minijava.xminiJava.StringTypeRef;
import org.imt.minijava.xminiJava.TypeDeclaration;
import org.imt.minijava.xminiJava.TypedDeclaration;
import org.imt.minijava.xminiJava.Class;
import org.imt.minijava.xminiJava.Member;
import org.imt.minijava.xminiJava.Method;
import org.imt.minijava.xminiJava.ArrayTypeRef;
import org.imt.tdl.coverage.IDSLSpecificCoverage;
import org.imt.tdl.coverage.TDLTestCaseCoverage;

public class MiniJavaCoverageComputation implements IDSLSpecificCoverage{

	private List<EObject> modelObjects ;
	private TDLTestCaseCoverage testCaseCoverage;
	
	@Override
	public List<String> getNewCoverableClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void specializeCoverage(TDLTestCaseCoverage testCaseCoverage) {
		this.testCaseCoverage = testCaseCoverage;
		this.modelObjects = testCaseCoverage.modelObjects;
		//if a method of a class is called, the class is covered
		//if an assignment is covered, all its contents are also covered
		
	}

	@Override
	public void ignoreModelObjects(Resource MUTResource) {
		Program javaPackage = (Program) MUTResource.getContents().get(0);
		removeModelState(MUTResource, javaPackage);
		boolean mainFound = false;
		for (TypeDeclaration cclass: javaPackage.getClasses()) {
			if (cclass instanceof Class) {
				Class javaClass = (Class) cclass;
				for (Member member:javaClass.getMembers()) {
					if (member instanceof Method) {
						Method method = (Method) member;
						if (method.getName().equals("main") && method.isStatic() && 
								(method.getParams().get(0).getTypeRef() instanceof ArrayTypeRef) &&
								(((ArrayTypeRef) method.getParams().get(0).getTypeRef()).getTypeRef() instanceof StringTypeRef)) {
							this.removeClassContainingMainMethod(MUTResource, javaPackage, javaClass);
							mainFound = true;
							break;
						}
					}
				}
				if (mainFound) {
					break;
				}
			}
		}
	}

	//the state of a minijava model should be ignored in coverage computation
	public void removeModelState (Resource MUTResource, Program javaPackage) {
		if (MUTResource.getResourceSet() == null) {
			javaPackage.setState(null);
		}
		else {
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(javaPackage);
			try{
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						javaPackage.setState(null);
					}
		   		});
	   		}catch(IllegalArgumentException e){
				e.printStackTrace();
			}
		}
	}
	
	//the class of a minijava model containing the main method should be ignored in coverage computation
	public void removeClassContainingMainMethod (Resource MUTResource, Program javaPackage, Class javaClass) {
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
