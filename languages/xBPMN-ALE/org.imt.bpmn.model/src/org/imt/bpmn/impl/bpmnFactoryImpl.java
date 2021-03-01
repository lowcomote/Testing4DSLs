/**
 */
package org.imt.bpmn.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.imt.bpmn.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class bpmnFactoryImpl extends EFactoryImpl implements bpmnFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static bpmnFactory init() {
		try {
			bpmnFactory thebpmnFactory = (bpmnFactory)EPackage.Registry.INSTANCE.getEFactory(bpmnPackage.eNS_URI);
			if (thebpmnFactory != null) {
				return thebpmnFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new bpmnFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public bpmnFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case bpmnPackage.MICROFLOW: return createMicroflow();
			case bpmnPackage.MICROFLOW_ELEMENT: return createMicroflowElement();
			case bpmnPackage.SEQUENCE_FLOW: return createSequenceFlow();
			case bpmnPackage.CREATE_OBJECT: return createCreateObject();
			case bpmnPackage.DELETE_OBJECT: return createDeleteObject();
			case bpmnPackage.CHANGE_OBJECT: return createChangeObject();
			case bpmnPackage.RETRIEVE_OBJECT: return createRetrieveObject();
			case bpmnPackage.MICROFLOW_CALL: return createMicroflowCall();
			case bpmnPackage.CREATE_VARIABLE: return createCreateVariable();
			case bpmnPackage.CHANGE_VARIABLE: return createChangeVariable();
			case bpmnPackage.SHOW_MESSAGE: return createShowMessage();
			case bpmnPackage.START_EVENT: return createStartEvent();
			case bpmnPackage.END_EVENT: return createEndEvent();
			case bpmnPackage.ENTITY: return createEntity();
			case bpmnPackage.REFERENCE: return createReference();
			case bpmnPackage.BASIC_VARIABLE: return createBasicVariable();
			case bpmnPackage.INTEGER_VARIABLE: return createIntegerVariable();
			case bpmnPackage.BOOLEAN_VARIABLE: return createBooleanVariable();
			case bpmnPackage.STRING_VARIABLE: return createStringVariable();
			case bpmnPackage.INTEGER_VALUE: return createIntegerValue();
			case bpmnPackage.BOOLEAN_VALUE: return createBooleanValue();
			case bpmnPackage.STRING_VALUE: return createStringValue();
			case bpmnPackage.FORK_DECISION: return createForkDecision();
			case bpmnPackage.MERGE_DECISION: return createMergeDecision();
			case bpmnPackage.INTEGER_COMPARISON_EXPRESSION: return createIntegerComparisonExpression();
			case bpmnPackage.STRING_COMPARISON_EXPRESSION: return createStringComparisonExpression();
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION: return createBooleanBinaryExpression();
			case bpmnPackage.BOOLEAN_UNARY_EXPRESSION: return createBooleanUnaryExpression();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case bpmnPackage.BOOLEAN_BINARY_OPERATOR:
				return createBooleanBinaryOperatorFromString(eDataType, initialValue);
			case bpmnPackage.BOOLEAN_UNARY_OPERATOR:
				return createBooleanUnaryOperatorFromString(eDataType, initialValue);
			case bpmnPackage.INTEGER_COMPARISON_OPERATOR:
				return createIntegerComparisonOperatorFromString(eDataType, initialValue);
			case bpmnPackage.STRING_COMPARISON_OPERATOR:
				return createStringComparisonOperatorFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case bpmnPackage.BOOLEAN_BINARY_OPERATOR:
				return convertBooleanBinaryOperatorToString(eDataType, instanceValue);
			case bpmnPackage.BOOLEAN_UNARY_OPERATOR:
				return convertBooleanUnaryOperatorToString(eDataType, instanceValue);
			case bpmnPackage.INTEGER_COMPARISON_OPERATOR:
				return convertIntegerComparisonOperatorToString(eDataType, instanceValue);
			case bpmnPackage.STRING_COMPARISON_OPERATOR:
				return convertStringComparisonOperatorToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Microflow createMicroflow() {
		MicroflowImpl microflow = new MicroflowImpl();
		return microflow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MicroflowElement createMicroflowElement() {
		MicroflowElementImpl microflowElement = new MicroflowElementImpl();
		return microflowElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SequenceFlow createSequenceFlow() {
		SequenceFlowImpl sequenceFlow = new SequenceFlowImpl();
		return sequenceFlow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CreateObject createCreateObject() {
		CreateObjectImpl createObject = new CreateObjectImpl();
		return createObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeleteObject createDeleteObject() {
		DeleteObjectImpl deleteObject = new DeleteObjectImpl();
		return deleteObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeObject createChangeObject() {
		ChangeObjectImpl changeObject = new ChangeObjectImpl();
		return changeObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RetrieveObject createRetrieveObject() {
		RetrieveObjectImpl retrieveObject = new RetrieveObjectImpl();
		return retrieveObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MicroflowCall createMicroflowCall() {
		MicroflowCallImpl microflowCall = new MicroflowCallImpl();
		return microflowCall;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CreateVariable createCreateVariable() {
		CreateVariableImpl createVariable = new CreateVariableImpl();
		return createVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeVariable createChangeVariable() {
		ChangeVariableImpl changeVariable = new ChangeVariableImpl();
		return changeVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShowMessage createShowMessage() {
		ShowMessageImpl showMessage = new ShowMessageImpl();
		return showMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StartEvent createStartEvent() {
		StartEventImpl startEvent = new StartEventImpl();
		return startEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndEvent createEndEvent() {
		EndEventImpl endEvent = new EndEventImpl();
		return endEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity createEntity() {
		EntityImpl entity = new EntityImpl();
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Reference createReference() {
		ReferenceImpl reference = new ReferenceImpl();
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasicVariable createBasicVariable() {
		BasicVariableImpl basicVariable = new BasicVariableImpl();
		return basicVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerVariable createIntegerVariable() {
		IntegerVariableImpl integerVariable = new IntegerVariableImpl();
		return integerVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanVariable createBooleanVariable() {
		BooleanVariableImpl booleanVariable = new BooleanVariableImpl();
		return booleanVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringVariable createStringVariable() {
		StringVariableImpl stringVariable = new StringVariableImpl();
		return stringVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerValue createIntegerValue() {
		IntegerValueImpl integerValue = new IntegerValueImpl();
		return integerValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanValue createBooleanValue() {
		BooleanValueImpl booleanValue = new BooleanValueImpl();
		return booleanValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringValue createStringValue() {
		StringValueImpl stringValue = new StringValueImpl();
		return stringValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForkDecision createForkDecision() {
		ForkDecisionImpl forkDecision = new ForkDecisionImpl();
		return forkDecision;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeDecision createMergeDecision() {
		MergeDecisionImpl mergeDecision = new MergeDecisionImpl();
		return mergeDecision;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerComparisonExpression createIntegerComparisonExpression() {
		IntegerComparisonExpressionImpl integerComparisonExpression = new IntegerComparisonExpressionImpl();
		return integerComparisonExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringComparisonExpression createStringComparisonExpression() {
		StringComparisonExpressionImpl stringComparisonExpression = new StringComparisonExpressionImpl();
		return stringComparisonExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanBinaryExpression createBooleanBinaryExpression() {
		BooleanBinaryExpressionImpl booleanBinaryExpression = new BooleanBinaryExpressionImpl();
		return booleanBinaryExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanUnaryExpression createBooleanUnaryExpression() {
		BooleanUnaryExpressionImpl booleanUnaryExpression = new BooleanUnaryExpressionImpl();
		return booleanUnaryExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanBinaryOperator createBooleanBinaryOperatorFromString(EDataType eDataType, String initialValue) {
		BooleanBinaryOperator result = BooleanBinaryOperator.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBooleanBinaryOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanUnaryOperator createBooleanUnaryOperatorFromString(EDataType eDataType, String initialValue) {
		BooleanUnaryOperator result = BooleanUnaryOperator.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBooleanUnaryOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerComparisonOperator createIntegerComparisonOperatorFromString(EDataType eDataType, String initialValue) {
		IntegerComparisonOperator result = IntegerComparisonOperator.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIntegerComparisonOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringComparisonOperator createStringComparisonOperatorFromString(EDataType eDataType, String initialValue) {
		StringComparisonOperator result = StringComparisonOperator.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStringComparisonOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public bpmnPackage getbpmnPackage() {
		return (bpmnPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static bpmnPackage getPackage() {
		return bpmnPackage.eINSTANCE;
	}

} //bpmnFactoryImpl
