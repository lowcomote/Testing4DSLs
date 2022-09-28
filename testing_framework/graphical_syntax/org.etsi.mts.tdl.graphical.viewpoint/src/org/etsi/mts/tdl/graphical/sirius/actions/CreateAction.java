package org.etsi.mts.tdl.graphical.sirius.actions;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.etsi.mts.tdl.Action;
import org.etsi.mts.tdl.ActionBehaviour;
import org.etsi.mts.tdl.ActionReference;
import org.etsi.mts.tdl.AlternativeBehaviour;
import org.etsi.mts.tdl.Assertion;
import org.etsi.mts.tdl.Assignment;
import org.etsi.mts.tdl.AtomicBehaviour;
import org.etsi.mts.tdl.Behaviour;
import org.etsi.mts.tdl.Block;
import org.etsi.mts.tdl.BoundedLoopBehaviour;
import org.etsi.mts.tdl.ComponentInstance;
import org.etsi.mts.tdl.ComponentInstanceRole;
import org.etsi.mts.tdl.DataInstance;
import org.etsi.mts.tdl.DataInstanceUse;
import org.etsi.mts.tdl.DataUse;
import org.etsi.mts.tdl.Function;
import org.etsi.mts.tdl.FunctionCall;
import org.etsi.mts.tdl.GateReference;
import org.etsi.mts.tdl.Interaction;
import org.etsi.mts.tdl.LocalExpression;
import org.etsi.mts.tdl.MemberAssignment;
import org.etsi.mts.tdl.Message;
import org.etsi.mts.tdl.MultipleCombinedBehaviour;
import org.etsi.mts.tdl.ParallelBehaviour;
import org.etsi.mts.tdl.PeriodicBehaviour;
import org.etsi.mts.tdl.SingleCombinedBehaviour;
import org.etsi.mts.tdl.StaticDataUse;
import org.etsi.mts.tdl.Target;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.TestDescriptionReference;
import org.etsi.mts.tdl.TimeConstraint;
import org.etsi.mts.tdl.TimeLabel;
import org.etsi.mts.tdl.TimeOperation;
import org.etsi.mts.tdl.Timer;
import org.etsi.mts.tdl.TimerOperation;
import org.etsi.mts.tdl.TimerStart;
import org.etsi.mts.tdl.Variable;
import org.etsi.mts.tdl.VariableUse;
import org.etsi.mts.tdl.VerdictAssignment;
import org.etsi.mts.tdl.tdlFactory;
import org.etsi.mts.tdl.tdlPackage;
import org.etsi.mts.tdl.graphical.extensions.BehaviourModifications;
import org.etsi.mts.tdl.graphical.extensions.ModelHelper;

public class CreateAction implements IExternalJavaAction {

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		
		EObject endPredecessor = (EObject) parameters.get("startingEndPredecessor");
		List<GateReference> lifelines = (List<GateReference>) parameters.get("lifelines");
		EObject target = (EObject) parameters.get("target");
		EObject element = (EObject) parameters.get("element");
		
		String metaClassName = (String) parameters.get("class");
		EClassifier eClass = tdlPackage.eINSTANCE.getEClassifier(metaClassName);
		
		Behaviour behaviour = null;
		switch (eClass.getClassifierID()) {
		case tdlPackage.MESSAGE:
			behaviour = createMessage(parameters);
			break;
			
		case tdlPackage.ALTERNATIVE_BEHAVIOUR:
		case tdlPackage.BOUNDED_LOOP_BEHAVIOUR:
		case tdlPackage.CONDITIONAL_BEHAVIOUR:
		case tdlPackage.PARALLEL_BEHAVIOUR:
		case tdlPackage.UNBOUNDED_LOOP_BEHAVIOUR:
			behaviour = createCombinedBehaviour(parameters, eClass);
			break;

		case tdlPackage.DEFAULT_BEHAVIOUR:
		case tdlPackage.INTERRUPT_BEHAVIOUR:
		case tdlPackage.PERIODIC_BEHAVIOUR:
			return;

		case tdlPackage.BLOCK:
			BehaviourModifications.addBlock(endPredecessor, (MultipleCombinedBehaviour) target);
			return;
			
		case tdlPackage.ASSIGNMENT:
			for (ComponentInstance c: new ModelHelper().getTestDescription(endPredecessor).getTestConfiguration().getComponentInstance()) {
				if (c.getRole() == ComponentInstanceRole.TESTER && c.getType().getVariable().contains(element)) {
					behaviour = (Behaviour) EcoreUtil.create((EClass) eClass);
					VariableUse assignmentVarUse = tdlFactory.eINSTANCE.createVariableUse();
					assignmentVarUse.setVariable((Variable) element);
					assignmentVarUse.setComponentInstance(c);
					((Assignment)behaviour).setVariable(assignmentVarUse);
					break;
				}
			}
			break;
			
		case tdlPackage.BREAK:
		case tdlPackage.STOP:
			behaviour = (Behaviour) EcoreUtil.create((EClass) eClass);
			break;
			
		case tdlPackage.ACTION_REFERENCE:
			behaviour = (Behaviour) EcoreUtil.create((EClass) eClass);
			((ActionReference)behaviour).setAction((Action) element);
			break;
			
		case tdlPackage.INLINE_ACTION:
			for (GateReference gRef : lifelines) {
				ComponentInstance comp = gRef.getComponent();
				behaviour = (Behaviour) EcoreUtil.create((EClass) eClass);
				((ActionBehaviour)behaviour).setComponentInstance(comp);
				break;
			}
			break;
			
		case tdlPackage.ASSERTION:
			behaviour = (Behaviour) EcoreUtil.create((EClass) eClass);
			DataInstanceUse assertionDataUse = tdlFactory.eINSTANCE.createDataInstanceUse();
			assertionDataUse.setDataInstance((DataInstance) element);
			((Assertion)behaviour).setOtherwise(assertionDataUse);
			break;
			
		case tdlPackage.VERDICT_ASSIGNMENT:
			behaviour = (Behaviour) EcoreUtil.create((EClass) eClass);
			DataInstanceUse verdictDataUse = tdlFactory.eINSTANCE.createDataInstanceUse();
			verdictDataUse.setDataInstance((DataInstance) element);
			((VerdictAssignment)behaviour).setVerdict(verdictDataUse);
			break;

		case tdlPackage.TEST_DESCRIPTION_REFERENCE:
			behaviour = (Behaviour) EcoreUtil.create((EClass) eClass);
			((TestDescriptionReference)behaviour).setTestDescription((TestDescription) element);
			break;

		case tdlPackage.TIMER_START:
		case tdlPackage.TIMER_STOP:
			for (ComponentInstance c: new ModelHelper().getTestDescription(endPredecessor).getTestConfiguration().getComponentInstance()) {
				if (c.getType().getTimer().contains(element)) {
					behaviour = (Behaviour) EcoreUtil.create((EClass) eClass);
					((TimerOperation)behaviour).setTimer((Timer) element);
					((TimerOperation)behaviour).setComponentInstance(c);
					break;
				}
			}
			break;

		case tdlPackage.WAIT:
		case tdlPackage.QUIESCENCE:
			ComponentInstance tester = null;
			for (GateReference gRef : lifelines) {
				ComponentInstance comp = gRef.getComponent();
				if (comp.getRole() == ComponentInstanceRole.TESTER)
					if (tester == null)
						tester = comp;
					else {
						tester = null;
						break;
					}
			}
			behaviour = (Behaviour) EcoreUtil.create((EClass) eClass);
			((TimeOperation)behaviour).setComponentInstance(tester);
			
			break;

		case tdlPackage.TIME_LABEL:
			TimeLabel tl = (TimeLabel) EcoreUtil.create((EClass) eClass);
			if (element instanceof Target)
				element = element.eContainer();
			((AtomicBehaviour)element).setTimeLabel(tl);
			return;
			
		case tdlPackage.TIME_CONSTRAINT:
			TimeConstraint tc = (TimeConstraint) EcoreUtil.create((EClass) eClass);
			if (element instanceof Target)
				element = element.eContainer();
			((AtomicBehaviour)element).getTimeConstraint().add(tc);
			return;

		case tdlPackage.DATA_INSTANCE_USE:
			DataInstanceUse dataInstanceUse = tdlFactory.eINSTANCE.createDataInstanceUse();
			dataInstanceUse.setDataInstance((DataInstance) element);
			setDataUse(target, dataInstanceUse);
			return;

		case tdlPackage.VARIABLE_USE:
			VariableUse variableUse = tdlFactory.eINSTANCE.createVariableUse();
			variableUse.setVariable((Variable) element);
			for (ComponentInstance c: new ModelHelper().getTestDescription(target).getTestConfiguration().getComponentInstance()) {
				if (c.getType().getVariable().contains(element)) {
					variableUse.setComponentInstance(c);
					break;
				}
			}
			setDataUse(target, variableUse);
			return;

		case tdlPackage.FUNCTION_CALL:
			FunctionCall functionCall = tdlFactory.eINSTANCE.createFunctionCall();
			functionCall.setFunction((Function) element);
			setDataUse(target, functionCall);
			return;
			
		default:
			return;
		}
		
		if (behaviour == null)
			return;
		
		BehaviourModifications.addBehaviour(endPredecessor, parameters.get("diagramElement"), behaviour);

	}
	
	private Behaviour createMessage(Map<String, Object> parameters) {

		Message msg = tdlFactory.eINSTANCE.createMessage();
		
		msg.setSourceGate((GateReference) parameters.get("source"));

		Target t = tdlFactory.eINSTANCE.createTarget();
		msg.getTarget().add(t);
		t.setTargetGate((GateReference) parameters.get("target"));
		
		return msg;
	}

	
	private Behaviour createCombinedBehaviour(Map<String, Object> parameters, EClassifier eClass) {
		Behaviour behaviour = (Behaviour) EcoreUtil.create((EClass) eClass);
		Block block = tdlFactory.eINSTANCE.createBlock();
		if (behaviour instanceof SingleCombinedBehaviour)
			((SingleCombinedBehaviour) behaviour).setBlock(block);	
		else if (behaviour instanceof MultipleCombinedBehaviour)
			((MultipleCombinedBehaviour) behaviour).getBlock().add(block);
		
		if (behaviour instanceof AlternativeBehaviour ||
				behaviour instanceof ParallelBehaviour)
			((MultipleCombinedBehaviour) behaviour).getBlock().add(tdlFactory.eINSTANCE.createBlock());
			
		return behaviour;
	}
	
	private void setDataUse(EObject target, DataUse dataUse) {
		LocalExpression exp = null;
		if (target instanceof BoundedLoopBehaviour
				|| target instanceof PeriodicBehaviour
				|| target instanceof Block) {
			exp = tdlFactory.eINSTANCE.createLocalExpression();
			exp.setExpression(dataUse);
		}
		
		if (target instanceof Target) {
			Interaction interaction = (Interaction) target.eContainer();
			if (interaction instanceof Message)
				((Message) interaction).setArgument(dataUse);
		} else if (target instanceof BoundedLoopBehaviour)
			((BoundedLoopBehaviour) target).getNumIteration().add(exp);
		else if (target instanceof PeriodicBehaviour)
			((PeriodicBehaviour) target).getPeriod().add(exp);
		else if (target instanceof Block)
			((Block) target).getGuard().add(exp);
		else if (target instanceof Assertion)
			((Assertion) target).setCondition(dataUse);
		else if (target instanceof Assignment)
			((Assignment) target).setExpression(dataUse);
		else if (target instanceof TimeConstraint)
			((TimeConstraint) target).setTimeConstraintExpression(dataUse);
		else if (target instanceof TimerStart)
			((TimerStart) target).setPeriod(dataUse);
		else if (target instanceof TimeOperation)
			((TimeOperation) target).setPeriod(dataUse);
		else if (target instanceof TimeConstraint)
			((TimeConstraint) target).setTimeConstraintExpression(dataUse);
		else if (target instanceof MemberAssignment)
			((MemberAssignment) target).setMemberSpec((StaticDataUse) dataUse);
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}

}
