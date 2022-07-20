package org.imt.xminijava.coverage;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.imt.minijava.xminiJava.ArrayTypeRef;
import org.imt.minijava.xminiJava.Class;
import org.imt.minijava.xminiJava.Member;
import org.imt.minijava.xminiJava.Method;
import org.imt.minijava.xminiJava.Program;
import org.imt.minijava.xminiJava.StringTypeRef;
import org.imt.minijava.xminiJava.TypeDeclaration;
import org.imt.tdl.coverage.dslSpecific.IDSLSpecificCoverage;

import DSLSpecificCoverage.DomainSpecificCoverage;

public class MiniJavaCoverageComputation implements IDSLSpecificCoverage{

	@Override
	public DomainSpecificCoverage getDomainSpecificCoverage() {
		//ignoring conditional elements from coverage computation because only their conditions must be considered
		//ignoring method signature from coverage computation
		//ignoring Program from coverage computation
		//a TypeDeclaration is covered when at least one of its members is covered
		return null;
	}
	
	//NOTCHECKED
//	private void methodCallCoverage(MethodCall modelObject) {
//		//if the container of a methodCall is a block, it is indeed a statement and its coverage status must be considered
//		//but if it is contained by other elements such as if, while, ..., it is not coverable
//		MethodCall methodCall = (MethodCall) modelObject;
//		if (!(methodCall.eContainer() instanceof Block)) {
//			int index = this.modelObjects.indexOf(methodCall);
//			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.NOT_COVERABLE);
//		}
//	}

//	@SuppressWarnings("unused")
//	private void classCoverage(Class clazz) {
//		//a class is covered when at least one of its methods is covered
//		//this method is useful when we need to have class coverage
//		int clazzIndex = this.modelObjects.indexOf(clazz);
//		for (Member member: clazz.getMembers()) {
//			if (member instanceof Method) {
//				int methodIndex = this.modelObjects.indexOf(member);
//				String methodCoverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(methodIndex);
//				if (methodCoverage == TDLCoverageUtil.COVERED) {
//					this.testCaseCoverage.getTcObjectCoverageStatus().set(clazzIndex, TDLCoverageUtil.COVERED);
//					break;
//				}
//			}
//		}
//		String classCoverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(clazzIndex);
//		if (classCoverage != TDLCoverageUtil.COVERED) {
//			this.testCaseCoverage.getTcObjectCoverageStatus().set(clazzIndex, TDLCoverageUtil.NOT_COVERED);
//		}
//	}
	
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
		try {
			javaPackage.getClasses().remove(javaClass);
		}catch (IllegalStateException e) {
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(javaPackage);
			try{
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						javaPackage.getClasses().remove(javaClass);	
					}
		   		});
	   		}catch(IllegalArgumentException e1){
				e.printStackTrace();
			}
		}
	}
}
