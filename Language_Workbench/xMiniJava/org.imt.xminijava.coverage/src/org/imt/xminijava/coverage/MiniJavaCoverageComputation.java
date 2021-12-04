package org.imt.xminijava.coverage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
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
import org.imt.tdl.coverage.IDSLSpecificCoverage;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.coverage.TDLTestCaseCoverage;

public class MiniJavaCoverageComputation implements IDSLSpecificCoverage{

	private List<EObject> modelObjects ;
	private TDLTestCaseCoverage testCaseCoverage;
	
	@Override
	public List<String> getNewCoverableClasses() {
		List<String> coverableClasses = new ArrayList<>();
		coverableClasses.add(Class.class.getName());
		return coverableClasses;
	}

	@Override
	public void specializeCoverage(TDLTestCaseCoverage testCaseCoverage) {
		this.testCaseCoverage = testCaseCoverage;
		this.modelObjects = testCaseCoverage.modelObjects;
		for (int i=0; i<this.modelObjects.size(); i++) {
			EObject modelObject = this.modelObjects.get(i);
			String coverage = this.testCaseCoverage.tcObjectCoverageStatus.get(i);
			if (modelObject instanceof Program) {
				programCoverage ((Program) modelObject);
			}
			else if (modelObject instanceof Class && coverage != TDLCoverageUtil.COVERED) {
				classCoverage ((Class) modelObject);
			}
			else if (modelObject instanceof Method) {
				methodCoverage ((Method) modelObject);
			}
		}
	}

	private void methodCoverage(Method method) {
		//to be compatible with JaCoCo, a method signature should not be considered in the coverage computation
		//only its inner instructions/assignments are considered
		int index = this.modelObjects.indexOf(method);
		this.testCaseCoverage.tcObjectCoverageStatus.set(index, TDLCoverageUtil.NOT_COVERABLE);
	}

	private void programCoverage(Program program) {
		//to be compatible with JaCoCo, a program should not be considered in the coverage computation
		int index = this.modelObjects.indexOf(program);
		this.testCaseCoverage.tcObjectCoverageStatus.set(index, TDLCoverageUtil.NOT_COVERABLE);
	}

	private void classCoverage(Class clazz) {
		//a class is covered when at least one of its methods is covered
		int clazzIndex = this.modelObjects.indexOf(clazz);
		for (Member member: clazz.getMembers()) {
			if (member instanceof Method) {
				int methodIndex = this.modelObjects.indexOf(member);
				String methodCoverage = this.testCaseCoverage.tcObjectCoverageStatus.get(methodIndex);
				if (methodCoverage == TDLCoverageUtil.COVERED) {
					this.testCaseCoverage.tcObjectCoverageStatus.set(clazzIndex, TDLCoverageUtil.COVERED);
					break;
				}
			}
		}
		String classCoverage = this.testCaseCoverage.tcObjectCoverageStatus.get(clazzIndex);
		if (classCoverage != TDLCoverageUtil.COVERED) {
			this.testCaseCoverage.tcObjectCoverageStatus.set(clazzIndex, TDLCoverageUtil.NOT_COVERED);
		}
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
