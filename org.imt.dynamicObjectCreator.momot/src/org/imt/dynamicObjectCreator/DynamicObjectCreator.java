package org.imt.dynamicObjectCreator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;

public class DynamicObjectCreator {
	
	String metamodelName;
	List<EPackage> epackages;
	
	public DynamicObjectCreator (String metamodelName, List<EPackage> epackages) {
		this.metamodelName = metamodelName;
		this.epackages = epackages;
	}
	
	public List<EObject> createDynamicEObjects(){
		//finding all the root dynamic EClasses (assumption: they must be contained by the static classes)
		List<EClassifier> rootDynamicEClasses = new ArrayList<>();
		for (EPackage p:epackages) {
			for (EClassifier clazz:p.getEClassifiers()) {
				if (isDynamicClass(clazz) && isContainedByStaticClass(clazz)) {
					rootDynamicEClasses.add(clazz);
				}
			}
		}
		//generate the henshin transformation for the creation of dynamic eobjects
		HenshinTransformationGenerator henshinGenerator = new HenshinTransformationGenerator(metamodelName);
		Module transformation = henshinGenerator.generateHenshinTransformation(rootDynamicEClasses);
		//TODO
		//generating fitness function, modules, momot configuration file, connection between momot and henshin
		//execute momot and return the list of generated objects
		return null;
	}

	private boolean isDynamicClass(EClassifier clazz) {
		if ((clazz.getEAnnotation("dynamic") != null) || (clazz.getEAnnotation("aspect") != null)) {
			return true;
		}
		return false;
	}
	
	private boolean isContainedByStaticClass(EClassifier dynamicClazz) {
		for (EPackage p:epackages) {
			List<EClass> staticContainers = p.getEClassifiers().stream().filter(c -> c instanceof EClass).map(c -> (EClass) c).
				filter(c -> !isDynamicClass(c) && c.getEAllContainments().stream().
						filter(cr -> cr.getEContainingClass() == dynamicClazz).findFirst().isPresent()).toList();
			if (staticContainers != null && !staticContainers.isEmpty()) {
				return true;
			}
		}
		return false;
	}
}
