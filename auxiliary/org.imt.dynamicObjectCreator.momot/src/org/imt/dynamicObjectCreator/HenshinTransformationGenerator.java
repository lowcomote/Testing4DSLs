package org.imt.dynamicObjectCreator;

import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;

public class HenshinTransformationGenerator {
	
	String MetamodelName;
	
	public HenshinTransformationGenerator (String MetamodelName) {
		this.MetamodelName = MetamodelName;
	}
	
	public Module generateHenshinTransformation(List<EClassifier> rootDynamicEClasses) {
		//generate a transformation containing one rule for each dynamic class
		Module transformation = HenshinFactory.eINSTANCE.createModule();
		transformation.setName(MetamodelName + "_dynamicObjectCreator");
		for (EClassifier dynamicClass:rootDynamicEClasses) {
			transformation.getUnits().add(generateHenshinRule(dynamicClass));
		}
		return transformation;
	}
	
	private Rule generateHenshinRule(EClassifier dynamicClass) {
		Rule objectCreatorRule = HenshinFactory.eINSTANCE.createRule();
		objectCreatorRule.setName(dynamicClass.getName() + "ObjectCreator");
		//TODO: complete the body of the rule
		return objectCreatorRule;
	}
}
