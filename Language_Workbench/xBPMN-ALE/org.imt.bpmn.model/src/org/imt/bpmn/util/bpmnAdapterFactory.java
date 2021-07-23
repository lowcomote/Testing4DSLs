/**
 */
package org.imt.bpmn.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.imt.bpmn.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.imt.bpmn.bpmnPackage
 * @generated
 */
public class bpmnAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static bpmnPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public bpmnAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = bpmnPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected bpmnSwitch<Adapter> modelSwitch =
		new bpmnSwitch<Adapter>() {
			@Override
			public Adapter caseMicroflow(Microflow object) {
				return createMicroflowAdapter();
			}
			@Override
			public Adapter caseMicroflowElement(MicroflowElement object) {
				return createMicroflowElementAdapter();
			}
			@Override
			public Adapter caseSequenceFlow(SequenceFlow object) {
				return createSequenceFlowAdapter();
			}
			@Override
			public Adapter caseActivity(Activity object) {
				return createActivityAdapter();
			}
			@Override
			public Adapter caseObjectActivity(ObjectActivity object) {
				return createObjectActivityAdapter();
			}
			@Override
			public Adapter caseCreateObject(CreateObject object) {
				return createCreateObjectAdapter();
			}
			@Override
			public Adapter caseDeleteObject(DeleteObject object) {
				return createDeleteObjectAdapter();
			}
			@Override
			public Adapter caseChangeObject(ChangeObject object) {
				return createChangeObjectAdapter();
			}
			@Override
			public Adapter caseRetrieveObject(RetrieveObject object) {
				return createRetrieveObjectAdapter();
			}
			@Override
			public Adapter caseActionCallActivity(ActionCallActivity object) {
				return createActionCallActivityAdapter();
			}
			@Override
			public Adapter caseMicroflowCall(MicroflowCall object) {
				return createMicroflowCallAdapter();
			}
			@Override
			public Adapter caseVariableActivity(VariableActivity object) {
				return createVariableActivityAdapter();
			}
			@Override
			public Adapter caseCreateVariable(CreateVariable object) {
				return createCreateVariableAdapter();
			}
			@Override
			public Adapter caseChangeVariable(ChangeVariable object) {
				return createChangeVariableAdapter();
			}
			@Override
			public Adapter caseClientActivity(ClientActivity object) {
				return createClientActivityAdapter();
			}
			@Override
			public Adapter caseShowMessage(ShowMessage object) {
				return createShowMessageAdapter();
			}
			@Override
			public Adapter caseEvent(Event object) {
				return createEventAdapter();
			}
			@Override
			public Adapter caseStartEvent(StartEvent object) {
				return createStartEventAdapter();
			}
			@Override
			public Adapter caseEndEvent(EndEvent object) {
				return createEndEventAdapter();
			}
			@Override
			public Adapter caseVariable(Variable object) {
				return createVariableAdapter();
			}
			@Override
			public Adapter caseEntity(Entity object) {
				return createEntityAdapter();
			}
			@Override
			public Adapter caseReference(Reference object) {
				return createReferenceAdapter();
			}
			@Override
			public Adapter caseBasicVariable(BasicVariable object) {
				return createBasicVariableAdapter();
			}
			@Override
			public Adapter caseIntegerVariable(IntegerVariable object) {
				return createIntegerVariableAdapter();
			}
			@Override
			public Adapter caseBooleanVariable(BooleanVariable object) {
				return createBooleanVariableAdapter();
			}
			@Override
			public Adapter caseStringVariable(StringVariable object) {
				return createStringVariableAdapter();
			}
			@Override
			public Adapter caseValue(Value object) {
				return createValueAdapter();
			}
			@Override
			public Adapter caseIntegerValue(IntegerValue object) {
				return createIntegerValueAdapter();
			}
			@Override
			public Adapter caseBooleanValue(BooleanValue object) {
				return createBooleanValueAdapter();
			}
			@Override
			public Adapter caseStringValue(StringValue object) {
				return createStringValueAdapter();
			}
			@Override
			public Adapter caseDecision(Decision object) {
				return createDecisionAdapter();
			}
			@Override
			public Adapter caseForkDecision(ForkDecision object) {
				return createForkDecisionAdapter();
			}
			@Override
			public Adapter caseMergeDecision(MergeDecision object) {
				return createMergeDecisionAdapter();
			}
			@Override
			public Adapter caseExpression(Expression object) {
				return createExpressionAdapter();
			}
			@Override
			public Adapter caseIntegerComparisonExpression(IntegerComparisonExpression object) {
				return createIntegerComparisonExpressionAdapter();
			}
			@Override
			public Adapter caseStringComparisonExpression(StringComparisonExpression object) {
				return createStringComparisonExpressionAdapter();
			}
			@Override
			public Adapter caseBooleanExpression(BooleanExpression object) {
				return createBooleanExpressionAdapter();
			}
			@Override
			public Adapter caseBooleanBinaryExpression(BooleanBinaryExpression object) {
				return createBooleanBinaryExpressionAdapter();
			}
			@Override
			public Adapter caseBooleanUnaryExpression(BooleanUnaryExpression object) {
				return createBooleanUnaryExpressionAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.Microflow <em>Microflow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.Microflow
	 * @generated
	 */
	public Adapter createMicroflowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.MicroflowElement <em>Microflow Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.MicroflowElement
	 * @generated
	 */
	public Adapter createMicroflowElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.SequenceFlow <em>Sequence Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.SequenceFlow
	 * @generated
	 */
	public Adapter createSequenceFlowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.Activity <em>Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.Activity
	 * @generated
	 */
	public Adapter createActivityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.ObjectActivity <em>Object Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.ObjectActivity
	 * @generated
	 */
	public Adapter createObjectActivityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.CreateObject <em>Create Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.CreateObject
	 * @generated
	 */
	public Adapter createCreateObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.DeleteObject <em>Delete Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.DeleteObject
	 * @generated
	 */
	public Adapter createDeleteObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.ChangeObject <em>Change Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.ChangeObject
	 * @generated
	 */
	public Adapter createChangeObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.RetrieveObject <em>Retrieve Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.RetrieveObject
	 * @generated
	 */
	public Adapter createRetrieveObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.ActionCallActivity <em>Action Call Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.ActionCallActivity
	 * @generated
	 */
	public Adapter createActionCallActivityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.MicroflowCall <em>Microflow Call</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.MicroflowCall
	 * @generated
	 */
	public Adapter createMicroflowCallAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.VariableActivity <em>Variable Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.VariableActivity
	 * @generated
	 */
	public Adapter createVariableActivityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.CreateVariable <em>Create Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.CreateVariable
	 * @generated
	 */
	public Adapter createCreateVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.ChangeVariable <em>Change Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.ChangeVariable
	 * @generated
	 */
	public Adapter createChangeVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.ClientActivity <em>Client Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.ClientActivity
	 * @generated
	 */
	public Adapter createClientActivityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.ShowMessage <em>Show Message</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.ShowMessage
	 * @generated
	 */
	public Adapter createShowMessageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.Event
	 * @generated
	 */
	public Adapter createEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.StartEvent <em>Start Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.StartEvent
	 * @generated
	 */
	public Adapter createStartEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.EndEvent <em>End Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.EndEvent
	 * @generated
	 */
	public Adapter createEndEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.Variable
	 * @generated
	 */
	public Adapter createVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.Entity
	 * @generated
	 */
	public Adapter createEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.Reference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.Reference
	 * @generated
	 */
	public Adapter createReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.BasicVariable <em>Basic Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.BasicVariable
	 * @generated
	 */
	public Adapter createBasicVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.IntegerVariable <em>Integer Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.IntegerVariable
	 * @generated
	 */
	public Adapter createIntegerVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.BooleanVariable <em>Boolean Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.BooleanVariable
	 * @generated
	 */
	public Adapter createBooleanVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.StringVariable <em>String Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.StringVariable
	 * @generated
	 */
	public Adapter createStringVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.Value
	 * @generated
	 */
	public Adapter createValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.IntegerValue <em>Integer Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.IntegerValue
	 * @generated
	 */
	public Adapter createIntegerValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.BooleanValue <em>Boolean Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.BooleanValue
	 * @generated
	 */
	public Adapter createBooleanValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.StringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.StringValue
	 * @generated
	 */
	public Adapter createStringValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.Decision <em>Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.Decision
	 * @generated
	 */
	public Adapter createDecisionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.ForkDecision <em>Fork Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.ForkDecision
	 * @generated
	 */
	public Adapter createForkDecisionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.MergeDecision <em>Merge Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.MergeDecision
	 * @generated
	 */
	public Adapter createMergeDecisionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.Expression
	 * @generated
	 */
	public Adapter createExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.IntegerComparisonExpression <em>Integer Comparison Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.IntegerComparisonExpression
	 * @generated
	 */
	public Adapter createIntegerComparisonExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.StringComparisonExpression <em>String Comparison Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.StringComparisonExpression
	 * @generated
	 */
	public Adapter createStringComparisonExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.BooleanExpression <em>Boolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.BooleanExpression
	 * @generated
	 */
	public Adapter createBooleanExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.BooleanBinaryExpression <em>Boolean Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.BooleanBinaryExpression
	 * @generated
	 */
	public Adapter createBooleanBinaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.imt.bpmn.BooleanUnaryExpression <em>Boolean Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.imt.bpmn.BooleanUnaryExpression
	 * @generated
	 */
	public Adapter createBooleanUnaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //bpmnAdapterFactory
