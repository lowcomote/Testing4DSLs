package org.etsi.mts.tdl.graphical.extensions;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.sequence.ordering.EventEnd;
import org.etsi.mts.tdl.Behaviour;
import org.etsi.mts.tdl.Block;
import org.etsi.mts.tdl.MultipleCombinedBehaviour;
import org.etsi.mts.tdl.SingleCombinedBehaviour;
import org.etsi.mts.tdl.Target;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlFactory;

public class BehaviourModifications {
	
	private ModelHelper helper = new ModelHelper();
	
	public static void addBehaviour(EObject startingEndPredecessor, Object diagramElement, Behaviour behaviour) {
		DSemanticDiagram diagram =
				diagramElement instanceof DSemanticDiagram ? (DSemanticDiagram) diagramElement :
				(DSemanticDiagram) ((DDiagramElement)diagramElement).getParentDiagram();
		TestDescription td = (TestDescription) diagram.getTarget();
		addBehaviour(startingEndPredecessor, td, behaviour);
	}
		
	public static void addBehaviour(EObject endPredecessor, TestDescription td, Behaviour behaviour) {
		if (endPredecessor instanceof EventEnd)
			endPredecessor = ((EventEnd) endPredecessor).getSemanticEnd();
		
		Block parent = null;
		Behaviour prev = null;
		if (endPredecessor instanceof Behaviour) {
			prev = (Behaviour) endPredecessor;
			
		} else if (endPredecessor instanceof Block) {
			parent = (Block) endPredecessor;
			
		} else if (endPredecessor == null || endPredecessor instanceof TestDescription) {
			parent = ((SingleCombinedBehaviour)td.getBehaviourDescription().getBehaviour()).getBlock();
			
		} else {
			EObject begin = BehaviourProvider.getBegin(endPredecessor);
			if (begin instanceof Block)
				prev = (Behaviour)begin.eContainer();
			else
				prev = (Behaviour) begin;
		}
		
		if (parent == null && prev != null)
			parent = (Block) prev.eContainer();

		List<Behaviour> list = parent.getBehaviour();
		list.remove(behaviour);
		int index = 0;
		if (prev != null)
			index = list.indexOf(prev) + 1;
		list.add(index, behaviour);
	}
	
	public Object reorderBehaviour(Object selection, Object preAfter) {
		
		Behaviour b = null;
		if (selection instanceof Target)
			b = (Behaviour) ((Target) selection).eContainer();
		else
			b = (Behaviour) selection;
		
		addBehaviour((EObject)preAfter, helper.getTestDescription(b), b);
		
		return selection;
	}
	
	public static void addBlock(EObject endPredecessor, MultipleCombinedBehaviour combinedBheviour) {
		if (endPredecessor instanceof EventEnd)
			endPredecessor = ((EventEnd) endPredecessor).getSemanticEnd();

		Block prevBlock;
		if (endPredecessor instanceof Block)
			prevBlock = (Block) endPredecessor;
		else {
			EObject begin = BehaviourProvider.getBegin(endPredecessor);
			while (!(begin instanceof Block))
				begin = begin.eContainer();
			prevBlock = (Block) begin;
		}
		List<Block> blocks = combinedBheviour.getBlock();
		blocks.add(prevBlock == null || !blocks.contains(prevBlock) ? 0 : blocks.indexOf(prevBlock) + 1,
				tdlFactory.eINSTANCE.createBlock());
	}
}
