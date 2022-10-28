package org.imt.k3tdl.utilities

import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.gemoc.dsl.Dsl
import org.etsi.mts.tdl.Package

class DSLProcessor {
	
	static DSLProcessor instance = new DSLProcessor
	static PathHelper pathHelper;
	
	String DSLPath = null;
	
	
	def static DSLProcessor getInstance(){
		return instance
	}
	
	var EList<EClassifier> eClassifiers;
	
	def void loadDSL (String DSLName){
		val dslPath = findDSLPath(DSLName)
		if (DSLPath === null || !DSLPath.equals(dslPath)){
			DSLPath = dslPath
			var dslRes = (new ResourceSetImpl()).getResource(URI.createURI(DSLPath), true);
			var dsl = dslRes.getContents().get(0) as Dsl;
			if (dsl.getEntry("ecore") !== null) {
				var metamodelPath = dsl.getEntry("ecore").getValue().replaceFirst("resource", "plugin");
				var metamodelRes = (new ResourceSetImpl()).getResource(URI.createURI(metamodelPath), true);
				var metamodelRootElement = metamodelRes.getContents().get(0) as EPackage;
				eClassifiers = new BasicEList<EClassifier>()
				eClassifiers.addAll(metamodelRootElement.EClassifiers)
			}
		}
	}
	
	def boolean isConcreteEcoreType(String typeName) {
		return eClassifiers.exists[c | 
				c.name.equals(getValidName(typeName)) && !c.eClass.abstract
			]
	}
		
	def String getValidName(String name){
		if (name.startsWith("_")){
			return name.substring(1)
		}
		return name
	}
	
	def void initPathHelper(Package testSuite){
		pathHelper = new PathHelper(testSuite)
	}
	
	def String findDSLPath (String DSLName){
		return pathHelper.findDSLPath(DSLName).toString
	}
	
	def String getDSLPath (){
		return DSLPath
	}
	
	def PathHelper getPathHelper(){
		return instance.pathHelper
	}
}