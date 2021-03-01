/**
 */
package org.imt.bpmn.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.imt.bpmn.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.imt.bpmn.bpmnPackage
 * @generated
 */
public class bpmnSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static bpmnPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public bpmnSwitch() {
		if (modelPackage == null) {
			modelPackage = bpmnPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case bpmnPackage.MICROFLOW: {
				Microflow microflow = (Microflow)theEObject;
				T result = caseMicroflow(microflow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.MICROFLOW_ELEMENT: {
				MicroflowElement microflowElement = (MicroflowElement)theEObject;
				T result = caseMicroflowElement(microflowElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.SEQUENCE_FLOW: {
				SequenceFlow sequenceFlow = (SequenceFlow)theEObject;
				T result = caseSequenceFlow(sequenceFlow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.ACTIVITY: {
				Activity activity = (Activity)theEObject;
				T result = caseActivity(activity);
				if (result == null) result = caseMicroflowElement(activity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.OBJECT_ACTIVITY: {
				ObjectActivity objectActivity = (ObjectActivity)theEObject;
				T result = caseObjectActivity(objectActivity);
				if (result == null) result = caseActivity(objectActivity);
				if (result == null) result = caseMicroflowElement(objectActivity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.CREATE_OBJECT: {
				CreateObject createObject = (CreateObject)theEObject;
				T result = caseCreateObject(createObject);
				if (result == null) result = caseObjectActivity(createObject);
				if (result == null) result = caseActivity(createObject);
				if (result == null) result = caseMicroflowElement(createObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.DELETE_OBJECT: {
				DeleteObject deleteObject = (DeleteObject)theEObject;
				T result = caseDeleteObject(deleteObject);
				if (result == null) result = caseObjectActivity(deleteObject);
				if (result == null) result = caseActivity(deleteObject);
				if (result == null) result = caseMicroflowElement(deleteObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.CHANGE_OBJECT: {
				ChangeObject changeObject = (ChangeObject)theEObject;
				T result = caseChangeObject(changeObject);
				if (result == null) result = caseObjectActivity(changeObject);
				if (result == null) result = caseActivity(changeObject);
				if (result == null) result = caseMicroflowElement(changeObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.RETRIEVE_OBJECT: {
				RetrieveObject retrieveObject = (RetrieveObject)theEObject;
				T result = caseRetrieveObject(retrieveObject);
				if (result == null) result = caseObjectActivity(retrieveObject);
				if (result == null) result = caseActivity(retrieveObject);
				if (result == null) result = caseMicroflowElement(retrieveObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.ACTION_CALL_ACTIVITY: {
				ActionCallActivity actionCallActivity = (ActionCallActivity)theEObject;
				T result = caseActionCallActivity(actionCallActivity);
				if (result == null) result = caseActivity(actionCallActivity);
				if (result == null) result = caseMicroflowElement(actionCallActivity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.MICROFLOW_CALL: {
				MicroflowCall microflowCall = (MicroflowCall)theEObject;
				T result = caseMicroflowCall(microflowCall);
				if (result == null) result = caseActionCallActivity(microflowCall);
				if (result == null) result = caseActivity(microflowCall);
				if (result == null) result = caseMicroflowElement(microflowCall);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.VARIABLE_ACTIVITY: {
				VariableActivity variableActivity = (VariableActivity)theEObject;
				T result = caseVariableActivity(variableActivity);
				if (result == null) result = caseActivity(variableActivity);
				if (result == null) result = caseMicroflowElement(variableActivity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.CREATE_VARIABLE: {
				CreateVariable createVariable = (CreateVariable)theEObject;
				T result = caseCreateVariable(createVariable);
				if (result == null) result = caseVariableActivity(createVariable);
				if (result == null) result = caseActivity(createVariable);
				if (result == null) result = caseMicroflowElement(createVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.CHANGE_VARIABLE: {
				ChangeVariable changeVariable = (ChangeVariable)theEObject;
				T result = caseChangeVariable(changeVariable);
				if (result == null) result = caseVariableActivity(changeVariable);
				if (result == null) result = caseActivity(changeVariable);
				if (result == null) result = caseMicroflowElement(changeVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.CLIENT_ACTIVITY: {
				ClientActivity clientActivity = (ClientActivity)theEObject;
				T result = caseClientActivity(clientActivity);
				if (result == null) result = caseActivity(clientActivity);
				if (result == null) result = caseMicroflowElement(clientActivity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.SHOW_MESSAGE: {
				ShowMessage showMessage = (ShowMessage)theEObject;
				T result = caseShowMessage(showMessage);
				if (result == null) result = caseClientActivity(showMessage);
				if (result == null) result = caseActivity(showMessage);
				if (result == null) result = caseMicroflowElement(showMessage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.EVENT: {
				Event event = (Event)theEObject;
				T result = caseEvent(event);
				if (result == null) result = caseMicroflowElement(event);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.START_EVENT: {
				StartEvent startEvent = (StartEvent)theEObject;
				T result = caseStartEvent(startEvent);
				if (result == null) result = caseEvent(startEvent);
				if (result == null) result = caseMicroflowElement(startEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.END_EVENT: {
				EndEvent endEvent = (EndEvent)theEObject;
				T result = caseEndEvent(endEvent);
				if (result == null) result = caseEvent(endEvent);
				if (result == null) result = caseMicroflowElement(endEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.VARIABLE: {
				Variable variable = (Variable)theEObject;
				T result = caseVariable(variable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.ENTITY: {
				Entity entity = (Entity)theEObject;
				T result = caseEntity(entity);
				if (result == null) result = caseVariable(entity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.REFERENCE: {
				Reference reference = (Reference)theEObject;
				T result = caseReference(reference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.BASIC_VARIABLE: {
				BasicVariable basicVariable = (BasicVariable)theEObject;
				T result = caseBasicVariable(basicVariable);
				if (result == null) result = caseVariable(basicVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.INTEGER_VARIABLE: {
				IntegerVariable integerVariable = (IntegerVariable)theEObject;
				T result = caseIntegerVariable(integerVariable);
				if (result == null) result = caseBasicVariable(integerVariable);
				if (result == null) result = caseVariable(integerVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.BOOLEAN_VARIABLE: {
				BooleanVariable booleanVariable = (BooleanVariable)theEObject;
				T result = caseBooleanVariable(booleanVariable);
				if (result == null) result = caseBasicVariable(booleanVariable);
				if (result == null) result = caseVariable(booleanVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.STRING_VARIABLE: {
				StringVariable stringVariable = (StringVariable)theEObject;
				T result = caseStringVariable(stringVariable);
				if (result == null) result = caseBasicVariable(stringVariable);
				if (result == null) result = caseVariable(stringVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.VALUE: {
				Value value = (Value)theEObject;
				T result = caseValue(value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.INTEGER_VALUE: {
				IntegerValue integerValue = (IntegerValue)theEObject;
				T result = caseIntegerValue(integerValue);
				if (result == null) result = caseValue(integerValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.BOOLEAN_VALUE: {
				BooleanValue booleanValue = (BooleanValue)theEObject;
				T result = caseBooleanValue(booleanValue);
				if (result == null) result = caseValue(booleanValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.STRING_VALUE: {
				StringValue stringValue = (StringValue)theEObject;
				T result = caseStringValue(stringValue);
				if (result == null) result = caseValue(stringValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.DECISION: {
				Decision decision = (Decision)theEObject;
				T result = caseDecision(decision);
				if (result == null) result = caseMicroflowElement(decision);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.FORK_DECISION: {
				ForkDecision forkDecision = (ForkDecision)theEObject;
				T result = caseForkDecision(forkDecision);
				if (result == null) result = caseDecision(forkDecision);
				if (result == null) result = caseMicroflowElement(forkDecision);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.MERGE_DECISION: {
				MergeDecision mergeDecision = (MergeDecision)theEObject;
				T result = caseMergeDecision(mergeDecision);
				if (result == null) result = caseDecision(mergeDecision);
				if (result == null) result = caseMicroflowElement(mergeDecision);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.EXPRESSION: {
				Expression expression = (Expression)theEObject;
				T result = caseExpression(expression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.INTEGER_COMPARISON_EXPRESSION: {
				IntegerComparisonExpression integerComparisonExpression = (IntegerComparisonExpression)theEObject;
				T result = caseIntegerComparisonExpression(integerComparisonExpression);
				if (result == null) result = caseExpression(integerComparisonExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.STRING_COMPARISON_EXPRESSION: {
				StringComparisonExpression stringComparisonExpression = (StringComparisonExpression)theEObject;
				T result = caseStringComparisonExpression(stringComparisonExpression);
				if (result == null) result = caseExpression(stringComparisonExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.BOOLEAN_EXPRESSION: {
				BooleanExpression booleanExpression = (BooleanExpression)theEObject;
				T result = caseBooleanExpression(booleanExpression);
				if (result == null) result = caseExpression(booleanExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION: {
				BooleanBinaryExpression booleanBinaryExpression = (BooleanBinaryExpression)theEObject;
				T result = caseBooleanBinaryExpression(booleanBinaryExpression);
				if (result == null) result = caseBooleanExpression(booleanBinaryExpression);
				if (result == null) result = caseExpression(booleanBinaryExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case bpmnPackage.BOOLEAN_UNARY_EXPRESSION: {
				BooleanUnaryExpression booleanUnaryExpression = (BooleanUnaryExpression)theEObject;
				T result = caseBooleanUnaryExpression(booleanUnaryExpression);
				if (result == null) result = caseBooleanExpression(booleanUnaryExpression);
				if (result == null) result = caseExpression(booleanUnaryExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Microflow</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Microflow</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMicroflow(Microflow object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Microflow Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Microflow Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMicroflowElement(MicroflowElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sequence Flow</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sequence Flow</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSequenceFlow(SequenceFlow object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Activity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActivity(Activity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Activity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseObjectActivity(ObjectActivity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Create Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Create Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCreateObject(CreateObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Delete Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Delete Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeleteObject(DeleteObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Change Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Change Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChangeObject(ChangeObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Retrieve Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Retrieve Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRetrieveObject(RetrieveObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Call Activity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Call Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionCallActivity(ActionCallActivity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Microflow Call</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Microflow Call</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMicroflowCall(MicroflowCall object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable Activity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariableActivity(VariableActivity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Create Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Create Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCreateVariable(CreateVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Change Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Change Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChangeVariable(ChangeVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Client Activity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Client Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClientActivity(ClientActivity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Show Message</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Show Message</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseShowMessage(ShowMessage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEvent(Event object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Start Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Start Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStartEvent(StartEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>End Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>End Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEndEvent(EndEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariable(Variable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntity(Entity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReference(Reference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Basic Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Basic Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBasicVariable(BasicVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerVariable(IntegerVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanVariable(BooleanVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringVariable(StringVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValue(Value object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerValue(IntegerValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanValue(BooleanValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringValue(StringValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Decision</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Decision</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDecision(Decision object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Fork Decision</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Fork Decision</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseForkDecision(ForkDecision object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merge Decision</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merge Decision</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergeDecision(MergeDecision object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpression(Expression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Comparison Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Comparison Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerComparisonExpression(IntegerComparisonExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Comparison Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Comparison Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringComparisonExpression(StringComparisonExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanExpression(BooleanExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Binary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Binary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanBinaryExpression(BooleanBinaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Unary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Unary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanUnaryExpression(BooleanUnaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //bpmnSwitch
