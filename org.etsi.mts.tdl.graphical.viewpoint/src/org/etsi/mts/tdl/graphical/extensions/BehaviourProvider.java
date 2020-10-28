package org.etsi.mts.tdl.graphical.extensions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.etsi.mts.tdl.AtomicBehaviour;
import org.etsi.mts.tdl.Behaviour;
import org.etsi.mts.tdl.Block;
import org.etsi.mts.tdl.CombinedBehaviour;
import org.etsi.mts.tdl.Connection;
import org.etsi.mts.tdl.ExceptionalBehaviour;
import org.etsi.mts.tdl.GateReference;
import org.etsi.mts.tdl.MultipleCombinedBehaviour;
import org.etsi.mts.tdl.PeriodicBehaviour;
import org.etsi.mts.tdl.SingleCombinedBehaviour;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlPackage;

public class BehaviourProvider {
	
	private static final String EXTENSION_URI = "http://www.etsi.org/spec/TDL/1.2.1/viewer";
	
	private static final String END_CLASS = "End", END_BEGIN_FEATURE = "begin";
	
	public EObject owner(EObject e) {
		return e.eContainer();
	}
	
	public AtomicBehaviour getAtomicBehaviour(EObject e) {
		while (!(e instanceof AtomicBehaviour))
			e = e.eContainer();
		if (e instanceof AtomicBehaviour)
			return (AtomicBehaviour) e;
		return null;
	}
	
	public List<EObject> getOccurrences(TestDescription td) {
		List<EObject> items = new ArrayList<EObject>();
		addBehavior(td.getBehaviourDescription().getBehaviour(), items);
		//Ends of lifelines are mapped to gate references
		for (Connection c: td.getTestConfiguration().getConnection())
			for (GateReference ref: c.getEndPoint())
				if (!items.contains(ref))
					items.add(ref);
		return items;
	}
	
	private void addBehavior(Behaviour behaviour, List<EObject> items) {
		int classifier = behaviour.eClass().getClassifierID();
		List<Block> blocks;
		switch (classifier) {
		case tdlPackage.ALTERNATIVE_BEHAVIOUR:
		case tdlPackage.PARALLEL_BEHAVIOUR:
		case tdlPackage.CONDITIONAL_BEHAVIOUR:
		case tdlPackage.BOUNDED_LOOP_BEHAVIOUR:
		case tdlPackage.UNBOUNDED_LOOP_BEHAVIOUR:
		case tdlPackage.COMPOUND_BEHAVIOUR:
			items.add(behaviour);
			if (behaviour instanceof SingleCombinedBehaviour)
				blocks = Collections.singletonList(((SingleCombinedBehaviour)behaviour).getBlock());
			else
				blocks = ((MultipleCombinedBehaviour)behaviour).getBlock();
			addBlocks(items, blocks);

			for (PeriodicBehaviour inner: ((CombinedBehaviour)behaviour).getPeriodic())
				addBehavior(inner, items);
			for (ExceptionalBehaviour inner: ((CombinedBehaviour)behaviour).getExceptional())
				addBehavior(inner, items);
			
			items.add(getEnd(behaviour));
			
			break;
			
		case tdlPackage.DEFAULT_BEHAVIOUR:
		case tdlPackage.INTERRUPT_BEHAVIOUR:
			items.add(behaviour);
			blocks = Collections.singletonList(((ExceptionalBehaviour)behaviour).getBlock());
			addBlocks(items, blocks);
			items.add(getEnd(behaviour));
			break;
			
		case tdlPackage.PERIODIC_BEHAVIOUR:
			items.add(behaviour);
			blocks = Collections.singletonList(((PeriodicBehaviour)behaviour).getBlock());
			addBlocks(items, blocks);
			items.add(getEnd(behaviour));
			break;
			
		default:
			items.add(behaviour);
			if (behaviour instanceof AtomicBehaviour)
				items.add(getEnd(behaviour));
			break;
		}
	}
	
	private void addBlocks(List<EObject> items, List<Block> blocks) {
		for (Block block : blocks) {
			items.add(block);
			for (Behaviour bBehaviour : block.getBehaviour()) {
				addBehavior(bBehaviour, items);
			}
			items.add(getEnd(block));
		}
	}
	
	public EObject getEnd(EObject e) {
		Resource extResource = getExtensionResource(e);
		EPackage extPackage = EPackageRegistryImpl.INSTANCE.getEPackage(EXTENSION_URI);
		EClass endClass = (EClass) extPackage.getEClassifier(END_CLASS);
		EStructuralFeature beginFeature = endClass.getEStructuralFeature(END_BEGIN_FEATURE);
		for (EObject o: extResource.getContents()) {
			if (endClass.isInstance(o)) {
				if (e.equals(o.eGet(beginFeature, true)))
					return o;
			}
		}
		return createEnd(e);
	}
	
	private EObject createEnd(EObject e) {
		Resource extResource = getExtensionResource(e);
		
		EPackage extPackage = getExtensionPackage();
		EClass endClass = getEndClass();
		EObject end = extPackage.getEFactoryInstance().create(endClass);
		
		end.eSet(endClass.getEStructuralFeature(END_BEGIN_FEATURE), e);
		
		extResource.getContents().add(end);
		
		return end;
	}
	
	private static EPackage getExtensionPackage() {
		return EPackageRegistryImpl.INSTANCE.getEPackage(EXTENSION_URI);
	}
	
	private static EClass getEndClass() {
		return (EClass) getExtensionPackage().getEClassifier(END_CLASS);
	}
	
	public static EObject getBegin(EObject end) {
		return (EObject) end.eGet(getEndClass().getEStructuralFeature(END_BEGIN_FEATURE));
	}
	
	private Resource getExtensionResource(EObject e) {
		Resource mResource = e.eResource();
		ResourceSet rs = mResource.getResourceSet();
		URI extUri = mResource.getURI().appendFileExtension("viewerextension");
		Resource extResource = rs.getResource(extUri, false);
		if (extResource == null)
			extResource = rs.createResource(extUri);
		return extResource;
		
	}
	
	public List<EObject> getBlocks(Behaviour b) {
		List<EObject> blocks = new ArrayList<EObject>();
		if (b instanceof SingleCombinedBehaviour)
			blocks.add(((SingleCombinedBehaviour) b).getBlock());
		else if (b instanceof MultipleCombinedBehaviour)
			blocks.addAll(((MultipleCombinedBehaviour) b).getBlock());
		else if (b instanceof ExceptionalBehaviour)
			blocks.add(((ExceptionalBehaviour) b).getBlock());
		else if (b instanceof PeriodicBehaviour)
			blocks.add(((PeriodicBehaviour) b).getBlock());
		return blocks;
	}
	
}
