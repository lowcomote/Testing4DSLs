package org.imt.tdl.sbfl.evaluation;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
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
	public void addMainClassToMutant(String MUTPath, String mutantPath, String mutantTestPath) {
		ResourceSet resSet = new ResourceSetImpl();
		//find the 'Main' class from the seed MUTResource
		Resource seedResource = resSet.getResource(URI.createPlatformResourceURI(MUTPath, false), true);
		Program seedPackage = (Program) seedResource.getContents().get(0);
		Class mainClass = getMainClass(seedPackage);
		if (mainClass != null) {
			//add main class to mutant and save the result in a new resource
			Resource mutantResource = resSet.getResource(URI.createPlatformResourceURI(mutantPath, false), true);
			Program mutantPackage = (Program) mutantResource.getContents().get(0);
			mutantPackage.getClasses().add(0, mainClass);
			EcoreUtil.resolveAll(mutantPackage);
			Resource mutantTestResource = (new ResourceSetImpl()).createResource(URI.createPlatformResourceURI(mutantTestPath, false));
			mutantTestResource.getContents().add(mutantPackage);
			EcoreUtil.resolveAll(mutantTestResource);
			try {			
				mutantTestResource.save(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Class getMainClass(Program miniJavaPackage) {
		for (TypeDeclaration cclass: miniJavaPackage.getClasses()) {
			if (cclass instanceof Class javaClass) {
				for (Member member:javaClass.getMembers()) {
					if (member instanceof Method method) {
						if (method.getName().equals("main") 
								&& method.isStatic() 
								&& (method.getParams().get(0).getTypeRef() instanceof ArrayTypeRef) 
								&& (((ArrayTypeRef) method.getParams().get(0).getTypeRef()).getTypeRef() instanceof StringTypeRef)) {
							return javaClass;
						}
					}
				}
			}
		}
		return null;
	}
}
