/**
 */
package org.imt.bpmn.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.imt.bpmn.ActionCallActivity;
import org.imt.bpmn.Activity;
import org.imt.bpmn.BasicVariable;
import org.imt.bpmn.BooleanBinaryExpression;
import org.imt.bpmn.BooleanBinaryOperator;
import org.imt.bpmn.BooleanExpression;
import org.imt.bpmn.BooleanUnaryExpression;
import org.imt.bpmn.BooleanUnaryOperator;
import org.imt.bpmn.BooleanValue;
import org.imt.bpmn.BooleanVariable;
import org.imt.bpmn.ChangeObject;
import org.imt.bpmn.ChangeVariable;
import org.imt.bpmn.ClientActivity;
import org.imt.bpmn.CreateObject;
import org.imt.bpmn.CreateVariable;
import org.imt.bpmn.Decision;
import org.imt.bpmn.DeleteObject;
import org.imt.bpmn.EndEvent;
import org.imt.bpmn.Entity;
import org.imt.bpmn.Event;
import org.imt.bpmn.Expression;
import org.imt.bpmn.ForkDecision;
import org.imt.bpmn.IntegerComparisonExpression;
import org.imt.bpmn.IntegerComparisonOperator;
import org.imt.bpmn.IntegerValue;
import org.imt.bpmn.IntegerVariable;
import org.imt.bpmn.MergeDecision;
import org.imt.bpmn.Microflow;
import org.imt.bpmn.MicroflowCall;
import org.imt.bpmn.MicroflowElement;
import org.imt.bpmn.ObjectActivity;
import org.imt.bpmn.Reference;
import org.imt.bpmn.RetrieveObject;
import org.imt.bpmn.SequenceFlow;
import org.imt.bpmn.ShowMessage;
import org.imt.bpmn.StartEvent;
import org.imt.bpmn.StringComparisonExpression;
import org.imt.bpmn.StringComparisonOperator;
import org.imt.bpmn.StringValue;
import org.imt.bpmn.StringVariable;
import org.imt.bpmn.Value;
import org.imt.bpmn.Variable;
import org.imt.bpmn.VariableActivity;
import org.imt.bpmn.bpmnFactory;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class bpmnPackageImpl extends EPackageImpl implements bpmnPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass microflowEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass microflowElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sequenceFlowEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass activityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectActivityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deleteObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass changeObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass retrieveObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionCallActivityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass microflowCallEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableActivityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass changeVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass clientActivityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass showMessageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass startEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass endEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass referenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass basicVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass decisionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass forkDecisionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mergeDecisionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerComparisonExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringComparisonExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanBinaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanUnaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum booleanBinaryOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum booleanUnaryOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum integerComparisonOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum stringComparisonOperatorEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.imt.bpmn.bpmnPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private bpmnPackageImpl() {
		super(eNS_URI, bpmnFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link bpmnPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static bpmnPackage init() {
		if (isInited) return (bpmnPackage)EPackage.Registry.INSTANCE.getEPackage(bpmnPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredbpmnPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		bpmnPackageImpl thebpmnPackage = registeredbpmnPackage instanceof bpmnPackageImpl ? (bpmnPackageImpl)registeredbpmnPackage : new bpmnPackageImpl();

		isInited = true;

		// Create package meta-data objects
		thebpmnPackage.createPackageContents();

		// Initialize created meta-data
		thebpmnPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thebpmnPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(bpmnPackage.eNS_URI, thebpmnPackage);
		return thebpmnPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMicroflow() {
		return microflowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMicroflow_Name() {
		return (EAttribute)microflowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMicroflow_OwnedElements() {
		return (EReference)microflowEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMicroflow_Parameters() {
		return (EReference)microflowEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMicroflow_CurrentNode() {
		return (EReference)microflowEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMicroflow_ValuedVariables() {
		return (EReference)microflowEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMicroflowElement() {
		return microflowElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMicroflowElement_IncomingFlows() {
		return (EReference)microflowElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMicroflowElement_OutgoingFlows() {
		return (EReference)microflowElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMicroflowElement_Label() {
		return (EAttribute)microflowElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMicroflowElement_OwnerMicroflow() {
		return (EReference)microflowElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSequenceFlow() {
		return sequenceFlowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSequenceFlow_Constraint() {
		return (EAttribute)sequenceFlowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSequenceFlow_Source() {
		return (EReference)sequenceFlowEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSequenceFlow_Target() {
		return (EReference)sequenceFlowEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActivity() {
		return activityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectActivity() {
		return objectActivityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectActivity_Entity() {
		return (EReference)objectActivityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreateObject() {
		return createObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateObject_NewEntity() {
		return (EReference)createObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeleteObject() {
		return deleteObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChangeObject() {
		return changeObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeObject_NewValue() {
		return (EReference)changeObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeObject_VariableToBeChanged() {
		return (EReference)changeObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRetrieveObject() {
		return retrieveObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRetrieveObject_Constraint() {
		return (EAttribute)retrieveObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActionCallActivity() {
		return actionCallActivityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMicroflowCall() {
		return microflowCallEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMicroflowCall_Target() {
		return (EReference)microflowCallEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariableActivity() {
		return variableActivityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreateVariable() {
		return createVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateVariable_NewVariable() {
		return (EReference)createVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChangeVariable() {
		return changeVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeVariable_TargetVariable() {
		return (EReference)changeVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeVariable_NewValue() {
		return (EReference)changeVariableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClientActivity() {
		return clientActivityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getShowMessage() {
		return showMessageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShowMessage_Message() {
		return (EAttribute)showMessageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEvent() {
		return eventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStartEvent() {
		return startEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEndEvent() {
		return endEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEndEvent_ReturnVariable() {
		return (EReference)endEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariable() {
		return variableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVariable_Name() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntity() {
		return entityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntity_Attributes() {
		return (EReference)entityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntity_References() {
		return (EReference)entityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReference() {
		return referenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReference_Name() {
		return (EAttribute)referenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReference_ReferencedEntity() {
		return (EReference)referenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBasicVariable() {
		return basicVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerVariable() {
		return integerVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIntegerVariable_ValueObject() {
		return (EReference)integerVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanVariable() {
		return booleanVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBooleanVariable_ValueObject() {
		return (EReference)booleanVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringVariable() {
		return stringVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStringVariable_ValueObject() {
		return (EReference)stringVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValue() {
		return valueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerValue() {
		return integerValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegerValue_Value() {
		return (EAttribute)integerValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanValue() {
		return booleanValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanValue_Value() {
		return (EAttribute)booleanValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringValue() {
		return stringValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringValue_Value() {
		return (EAttribute)stringValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDecision() {
		return decisionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getForkDecision() {
		return forkDecisionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getForkDecision_Expression() {
		return (EReference)forkDecisionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMergeDecision() {
		return mergeDecisionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpression() {
		return expressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerComparisonExpression() {
		return integerComparisonExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegerComparisonExpression_Operator() {
		return (EAttribute)integerComparisonExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIntegerComparisonExpression_Operand1() {
		return (EReference)integerComparisonExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIntegerComparisonExpression_Operand2() {
		return (EReference)integerComparisonExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringComparisonExpression() {
		return stringComparisonExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringComparisonExpression_Operator() {
		return (EAttribute)stringComparisonExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStringComparisonExpression_Operand1() {
		return (EReference)stringComparisonExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStringComparisonExpression_Operand2() {
		return (EReference)stringComparisonExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanExpression() {
		return booleanExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanBinaryExpression() {
		return booleanBinaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanBinaryExpression_Operator() {
		return (EAttribute)booleanBinaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBooleanBinaryExpression_Operand1() {
		return (EReference)booleanBinaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBooleanBinaryExpression_Operand2() {
		return (EReference)booleanBinaryExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanUnaryExpression() {
		return booleanUnaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanUnaryExpression_Operator() {
		return (EAttribute)booleanUnaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBooleanUnaryExpression_Operand() {
		return (EReference)booleanUnaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBooleanBinaryOperator() {
		return booleanBinaryOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBooleanUnaryOperator() {
		return booleanUnaryOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getIntegerComparisonOperator() {
		return integerComparisonOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getStringComparisonOperator() {
		return stringComparisonOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public bpmnFactory getbpmnFactory() {
		return (bpmnFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		microflowEClass = createEClass(MICROFLOW);
		createEAttribute(microflowEClass, MICROFLOW__NAME);
		createEReference(microflowEClass, MICROFLOW__OWNED_ELEMENTS);
		createEReference(microflowEClass, MICROFLOW__CURRENT_NODE);
		createEReference(microflowEClass, MICROFLOW__VALUED_VARIABLES);
		createEReference(microflowEClass, MICROFLOW__PARAMETERS);

		microflowElementEClass = createEClass(MICROFLOW_ELEMENT);
		createEReference(microflowElementEClass, MICROFLOW_ELEMENT__INCOMING_FLOWS);
		createEReference(microflowElementEClass, MICROFLOW_ELEMENT__OUTGOING_FLOWS);
		createEAttribute(microflowElementEClass, MICROFLOW_ELEMENT__LABEL);
		createEReference(microflowElementEClass, MICROFLOW_ELEMENT__OWNER_MICROFLOW);

		sequenceFlowEClass = createEClass(SEQUENCE_FLOW);
		createEAttribute(sequenceFlowEClass, SEQUENCE_FLOW__CONSTRAINT);
		createEReference(sequenceFlowEClass, SEQUENCE_FLOW__SOURCE);
		createEReference(sequenceFlowEClass, SEQUENCE_FLOW__TARGET);

		activityEClass = createEClass(ACTIVITY);

		objectActivityEClass = createEClass(OBJECT_ACTIVITY);
		createEReference(objectActivityEClass, OBJECT_ACTIVITY__ENTITY);

		createObjectEClass = createEClass(CREATE_OBJECT);
		createEReference(createObjectEClass, CREATE_OBJECT__NEW_ENTITY);

		deleteObjectEClass = createEClass(DELETE_OBJECT);

		changeObjectEClass = createEClass(CHANGE_OBJECT);
		createEReference(changeObjectEClass, CHANGE_OBJECT__NEW_VALUE);
		createEReference(changeObjectEClass, CHANGE_OBJECT__VARIABLE_TO_BE_CHANGED);

		retrieveObjectEClass = createEClass(RETRIEVE_OBJECT);
		createEAttribute(retrieveObjectEClass, RETRIEVE_OBJECT__CONSTRAINT);

		actionCallActivityEClass = createEClass(ACTION_CALL_ACTIVITY);

		microflowCallEClass = createEClass(MICROFLOW_CALL);
		createEReference(microflowCallEClass, MICROFLOW_CALL__TARGET);

		variableActivityEClass = createEClass(VARIABLE_ACTIVITY);

		createVariableEClass = createEClass(CREATE_VARIABLE);
		createEReference(createVariableEClass, CREATE_VARIABLE__NEW_VARIABLE);

		changeVariableEClass = createEClass(CHANGE_VARIABLE);
		createEReference(changeVariableEClass, CHANGE_VARIABLE__TARGET_VARIABLE);
		createEReference(changeVariableEClass, CHANGE_VARIABLE__NEW_VALUE);

		clientActivityEClass = createEClass(CLIENT_ACTIVITY);

		showMessageEClass = createEClass(SHOW_MESSAGE);
		createEAttribute(showMessageEClass, SHOW_MESSAGE__MESSAGE);

		eventEClass = createEClass(EVENT);

		startEventEClass = createEClass(START_EVENT);

		endEventEClass = createEClass(END_EVENT);
		createEReference(endEventEClass, END_EVENT__RETURN_VARIABLE);

		variableEClass = createEClass(VARIABLE);
		createEAttribute(variableEClass, VARIABLE__NAME);

		entityEClass = createEClass(ENTITY);
		createEReference(entityEClass, ENTITY__ATTRIBUTES);
		createEReference(entityEClass, ENTITY__REFERENCES);

		referenceEClass = createEClass(REFERENCE);
		createEAttribute(referenceEClass, REFERENCE__NAME);
		createEReference(referenceEClass, REFERENCE__REFERENCED_ENTITY);

		basicVariableEClass = createEClass(BASIC_VARIABLE);

		integerVariableEClass = createEClass(INTEGER_VARIABLE);
		createEReference(integerVariableEClass, INTEGER_VARIABLE__VALUE_OBJECT);

		booleanVariableEClass = createEClass(BOOLEAN_VARIABLE);
		createEReference(booleanVariableEClass, BOOLEAN_VARIABLE__VALUE_OBJECT);

		stringVariableEClass = createEClass(STRING_VARIABLE);
		createEReference(stringVariableEClass, STRING_VARIABLE__VALUE_OBJECT);

		valueEClass = createEClass(VALUE);

		integerValueEClass = createEClass(INTEGER_VALUE);
		createEAttribute(integerValueEClass, INTEGER_VALUE__VALUE);

		booleanValueEClass = createEClass(BOOLEAN_VALUE);
		createEAttribute(booleanValueEClass, BOOLEAN_VALUE__VALUE);

		stringValueEClass = createEClass(STRING_VALUE);
		createEAttribute(stringValueEClass, STRING_VALUE__VALUE);

		decisionEClass = createEClass(DECISION);

		forkDecisionEClass = createEClass(FORK_DECISION);
		createEReference(forkDecisionEClass, FORK_DECISION__EXPRESSION);

		mergeDecisionEClass = createEClass(MERGE_DECISION);

		expressionEClass = createEClass(EXPRESSION);

		integerComparisonExpressionEClass = createEClass(INTEGER_COMPARISON_EXPRESSION);
		createEAttribute(integerComparisonExpressionEClass, INTEGER_COMPARISON_EXPRESSION__OPERATOR);
		createEReference(integerComparisonExpressionEClass, INTEGER_COMPARISON_EXPRESSION__OPERAND1);
		createEReference(integerComparisonExpressionEClass, INTEGER_COMPARISON_EXPRESSION__OPERAND2);

		stringComparisonExpressionEClass = createEClass(STRING_COMPARISON_EXPRESSION);
		createEAttribute(stringComparisonExpressionEClass, STRING_COMPARISON_EXPRESSION__OPERATOR);
		createEReference(stringComparisonExpressionEClass, STRING_COMPARISON_EXPRESSION__OPERAND1);
		createEReference(stringComparisonExpressionEClass, STRING_COMPARISON_EXPRESSION__OPERAND2);

		booleanExpressionEClass = createEClass(BOOLEAN_EXPRESSION);

		booleanBinaryExpressionEClass = createEClass(BOOLEAN_BINARY_EXPRESSION);
		createEAttribute(booleanBinaryExpressionEClass, BOOLEAN_BINARY_EXPRESSION__OPERATOR);
		createEReference(booleanBinaryExpressionEClass, BOOLEAN_BINARY_EXPRESSION__OPERAND1);
		createEReference(booleanBinaryExpressionEClass, BOOLEAN_BINARY_EXPRESSION__OPERAND2);

		booleanUnaryExpressionEClass = createEClass(BOOLEAN_UNARY_EXPRESSION);
		createEAttribute(booleanUnaryExpressionEClass, BOOLEAN_UNARY_EXPRESSION__OPERATOR);
		createEReference(booleanUnaryExpressionEClass, BOOLEAN_UNARY_EXPRESSION__OPERAND);

		// Create enums
		booleanBinaryOperatorEEnum = createEEnum(BOOLEAN_BINARY_OPERATOR);
		booleanUnaryOperatorEEnum = createEEnum(BOOLEAN_UNARY_OPERATOR);
		integerComparisonOperatorEEnum = createEEnum(INTEGER_COMPARISON_OPERATOR);
		stringComparisonOperatorEEnum = createEEnum(STRING_COMPARISON_OPERATOR);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		activityEClass.getESuperTypes().add(this.getMicroflowElement());
		objectActivityEClass.getESuperTypes().add(this.getActivity());
		createObjectEClass.getESuperTypes().add(this.getObjectActivity());
		deleteObjectEClass.getESuperTypes().add(this.getObjectActivity());
		changeObjectEClass.getESuperTypes().add(this.getObjectActivity());
		retrieveObjectEClass.getESuperTypes().add(this.getObjectActivity());
		actionCallActivityEClass.getESuperTypes().add(this.getActivity());
		microflowCallEClass.getESuperTypes().add(this.getActionCallActivity());
		variableActivityEClass.getESuperTypes().add(this.getActivity());
		createVariableEClass.getESuperTypes().add(this.getVariableActivity());
		changeVariableEClass.getESuperTypes().add(this.getVariableActivity());
		clientActivityEClass.getESuperTypes().add(this.getActivity());
		showMessageEClass.getESuperTypes().add(this.getClientActivity());
		eventEClass.getESuperTypes().add(this.getMicroflowElement());
		startEventEClass.getESuperTypes().add(this.getEvent());
		endEventEClass.getESuperTypes().add(this.getEvent());
		entityEClass.getESuperTypes().add(this.getVariable());
		basicVariableEClass.getESuperTypes().add(this.getVariable());
		integerVariableEClass.getESuperTypes().add(this.getBasicVariable());
		booleanVariableEClass.getESuperTypes().add(this.getBasicVariable());
		stringVariableEClass.getESuperTypes().add(this.getBasicVariable());
		integerValueEClass.getESuperTypes().add(this.getValue());
		booleanValueEClass.getESuperTypes().add(this.getValue());
		stringValueEClass.getESuperTypes().add(this.getValue());
		decisionEClass.getESuperTypes().add(this.getMicroflowElement());
		forkDecisionEClass.getESuperTypes().add(this.getDecision());
		mergeDecisionEClass.getESuperTypes().add(this.getDecision());
		integerComparisonExpressionEClass.getESuperTypes().add(this.getExpression());
		stringComparisonExpressionEClass.getESuperTypes().add(this.getExpression());
		booleanExpressionEClass.getESuperTypes().add(this.getExpression());
		booleanBinaryExpressionEClass.getESuperTypes().add(this.getBooleanExpression());
		booleanUnaryExpressionEClass.getESuperTypes().add(this.getBooleanExpression());

		// Initialize classes, features, and operations; add parameters
		initEClass(microflowEClass, Microflow.class, "Microflow", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMicroflow_Name(), ecorePackage.getEString(), "name", null, 1, 1, Microflow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMicroflow_OwnedElements(), this.getMicroflowElement(), this.getMicroflowElement_OwnerMicroflow(), "ownedElements", null, 0, -1, Microflow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMicroflow_CurrentNode(), this.getMicroflowElement(), null, "currentNode", null, 0, 1, Microflow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMicroflow_ValuedVariables(), this.getVariable(), null, "valuedVariables", null, 0, -1, Microflow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMicroflow_Parameters(), this.getVariable(), null, "parameters", null, 0, -1, Microflow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(microflowElementEClass, MicroflowElement.class, "MicroflowElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMicroflowElement_IncomingFlows(), this.getSequenceFlow(), this.getSequenceFlow_Target(), "incomingFlows", null, 0, -1, MicroflowElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMicroflowElement_OutgoingFlows(), this.getSequenceFlow(), this.getSequenceFlow_Source(), "outgoingFlows", null, 0, -1, MicroflowElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMicroflowElement_Label(), ecorePackage.getEString(), "label", null, 1, 1, MicroflowElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMicroflowElement_OwnerMicroflow(), this.getMicroflow(), this.getMicroflow_OwnedElements(), "ownerMicroflow", null, 1, 1, MicroflowElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sequenceFlowEClass, SequenceFlow.class, "SequenceFlow", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSequenceFlow_Constraint(), ecorePackage.getEString(), "constraint", null, 0, 1, SequenceFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSequenceFlow_Source(), this.getMicroflowElement(), this.getMicroflowElement_OutgoingFlows(), "source", null, 1, 1, SequenceFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSequenceFlow_Target(), this.getMicroflowElement(), this.getMicroflowElement_IncomingFlows(), "target", null, 1, 1, SequenceFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(activityEClass, Activity.class, "Activity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(objectActivityEClass, ObjectActivity.class, "ObjectActivity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getObjectActivity_Entity(), this.getEntity(), null, "entity", null, 1, 1, ObjectActivity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(createObjectEClass, CreateObject.class, "CreateObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCreateObject_NewEntity(), this.getEntity(), null, "newEntity", null, 1, 1, CreateObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deleteObjectEClass, DeleteObject.class, "DeleteObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(changeObjectEClass, ChangeObject.class, "ChangeObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChangeObject_NewValue(), this.getValue(), null, "newValue", null, 1, 1, ChangeObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeObject_VariableToBeChanged(), this.getBasicVariable(), null, "variableToBeChanged", null, 1, 1, ChangeObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(retrieveObjectEClass, RetrieveObject.class, "RetrieveObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRetrieveObject_Constraint(), ecorePackage.getEString(), "constraint", null, 0, 1, RetrieveObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(actionCallActivityEClass, ActionCallActivity.class, "ActionCallActivity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(microflowCallEClass, MicroflowCall.class, "MicroflowCall", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMicroflowCall_Target(), this.getMicroflow(), null, "target", null, 1, 1, MicroflowCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableActivityEClass, VariableActivity.class, "VariableActivity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(createVariableEClass, CreateVariable.class, "CreateVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCreateVariable_NewVariable(), this.getBasicVariable(), null, "newVariable", null, 1, 1, CreateVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(changeVariableEClass, ChangeVariable.class, "ChangeVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChangeVariable_TargetVariable(), this.getBasicVariable(), null, "targetVariable", null, 1, 1, ChangeVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeVariable_NewValue(), this.getValue(), null, "newValue", null, 1, 1, ChangeVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(clientActivityEClass, ClientActivity.class, "ClientActivity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(showMessageEClass, ShowMessage.class, "ShowMessage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getShowMessage_Message(), ecorePackage.getEString(), "message", null, 1, 1, ShowMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventEClass, Event.class, "Event", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(startEventEClass, StartEvent.class, "StartEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(endEventEClass, EndEvent.class, "EndEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEndEvent_ReturnVariable(), this.getVariable(), null, "returnVariable", null, 0, 1, EndEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableEClass, Variable.class, "Variable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariable_Name(), ecorePackage.getEString(), "name", null, 1, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityEClass, Entity.class, "Entity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntity_Attributes(), this.getBasicVariable(), null, "attributes", null, 0, -1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_References(), this.getReference(), null, "references", null, 0, -1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(referenceEClass, Reference.class, "Reference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getReference_Name(), ecorePackage.getEString(), "name", null, 1, 1, Reference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getReference_ReferencedEntity(), this.getEntity(), null, "referencedEntity", null, 1, 1, Reference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(basicVariableEClass, BasicVariable.class, "BasicVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(integerVariableEClass, IntegerVariable.class, "IntegerVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIntegerVariable_ValueObject(), this.getIntegerValue(), null, "valueObject", null, 0, 1, IntegerVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanVariableEClass, BooleanVariable.class, "BooleanVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBooleanVariable_ValueObject(), this.getBooleanValue(), null, "valueObject", null, 0, 1, BooleanVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringVariableEClass, StringVariable.class, "StringVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStringVariable_ValueObject(), this.getStringValue(), null, "valueObject", null, 0, 1, StringVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valueEClass, Value.class, "Value", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(integerValueEClass, IntegerValue.class, "IntegerValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerValue_Value(), ecorePackage.getEInt(), "value", null, 1, 1, IntegerValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanValueEClass, BooleanValue.class, "BooleanValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanValue_Value(), ecorePackage.getEBoolean(), "value", "false", 1, 1, BooleanValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringValueEClass, StringValue.class, "StringValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringValue_Value(), ecorePackage.getEString(), "value", null, 1, 1, StringValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(decisionEClass, Decision.class, "Decision", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(forkDecisionEClass, ForkDecision.class, "ForkDecision", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getForkDecision_Expression(), this.getExpression(), null, "expression", null, 1, 1, ForkDecision.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mergeDecisionEClass, MergeDecision.class, "MergeDecision", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(expressionEClass, Expression.class, "Expression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(integerComparisonExpressionEClass, IntegerComparisonExpression.class, "IntegerComparisonExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerComparisonExpression_Operator(), this.getIntegerComparisonOperator(), "operator", null, 1, 1, IntegerComparisonExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIntegerComparisonExpression_Operand1(), this.getIntegerVariable(), null, "operand1", null, 1, 1, IntegerComparisonExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIntegerComparisonExpression_Operand2(), this.getIntegerVariable(), null, "operand2", null, 1, 1, IntegerComparisonExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringComparisonExpressionEClass, StringComparisonExpression.class, "StringComparisonExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringComparisonExpression_Operator(), this.getStringComparisonOperator(), "operator", null, 1, 1, StringComparisonExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStringComparisonExpression_Operand1(), this.getStringVariable(), null, "operand1", null, 1, 1, StringComparisonExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStringComparisonExpression_Operand2(), this.getStringVariable(), null, "operand2", null, 1, 1, StringComparisonExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanExpressionEClass, BooleanExpression.class, "BooleanExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(booleanBinaryExpressionEClass, BooleanBinaryExpression.class, "BooleanBinaryExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanBinaryExpression_Operator(), this.getBooleanBinaryOperator(), "operator", null, 1, 1, BooleanBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBooleanBinaryExpression_Operand1(), this.getBooleanVariable(), null, "operand1", null, 1, 1, BooleanBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBooleanBinaryExpression_Operand2(), this.getBooleanVariable(), null, "operand2", null, 1, 1, BooleanBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanUnaryExpressionEClass, BooleanUnaryExpression.class, "BooleanUnaryExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanUnaryExpression_Operator(), this.getBooleanUnaryOperator(), "operator", null, 1, 1, BooleanUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBooleanUnaryExpression_Operand(), this.getBooleanVariable(), null, "operand", null, 1, 1, BooleanUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(booleanBinaryOperatorEEnum, BooleanBinaryOperator.class, "BooleanBinaryOperator");
		addEEnumLiteral(booleanBinaryOperatorEEnum, BooleanBinaryOperator.AND);
		addEEnumLiteral(booleanBinaryOperatorEEnum, BooleanBinaryOperator.OR);

		initEEnum(booleanUnaryOperatorEEnum, BooleanUnaryOperator.class, "BooleanUnaryOperator");
		addEEnumLiteral(booleanUnaryOperatorEEnum, BooleanUnaryOperator.NOT);

		initEEnum(integerComparisonOperatorEEnum, IntegerComparisonOperator.class, "IntegerComparisonOperator");
		addEEnumLiteral(integerComparisonOperatorEEnum, IntegerComparisonOperator.SMALLER);
		addEEnumLiteral(integerComparisonOperatorEEnum, IntegerComparisonOperator.SMALLER_EQUALS);
		addEEnumLiteral(integerComparisonOperatorEEnum, IntegerComparisonOperator.EQUALS);
		addEEnumLiteral(integerComparisonOperatorEEnum, IntegerComparisonOperator.GREATER_EQUALS);
		addEEnumLiteral(integerComparisonOperatorEEnum, IntegerComparisonOperator.GREATER);

		initEEnum(stringComparisonOperatorEEnum, StringComparisonOperator.class, "StringComparisonOperator");
		addEEnumLiteral(stringComparisonOperatorEEnum, StringComparisonOperator.EQUALS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// dynamic
		createDynamicAnnotations();
	}

	/**
	 * Initializes the annotations for <b>dynamic</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createDynamicAnnotations() {
		String source = "dynamic";
		addAnnotation
		  (getMicroflow_CurrentNode(),
		   source,
		   new String[] {
		   });
		addAnnotation
		  (getMicroflow_ValuedVariables(),
		   source,
		   new String[] {
		   });
		addAnnotation
		  (getMicroflow_Parameters(),
		   source,
		   new String[] {
		   });
		addAnnotation
		  (variableEClass,
		   source,
		   new String[] {
		   });
		addAnnotation
		  (getEntity_Attributes(),
		   source,
		   new String[] {
		   });
		addAnnotation
		  (getEntity_References(),
		   source,
		   new String[] {
		   });
		addAnnotation
		  (basicVariableEClass,
		   source,
		   new String[] {
		   });
		addAnnotation
		  (integerVariableEClass,
		   source,
		   new String[] {
		   });
		addAnnotation
		  (getIntegerVariable_ValueObject(),
		   source,
		   new String[] {
		   });
		addAnnotation
		  (booleanVariableEClass,
		   source,
		   new String[] {
		   });
		addAnnotation
		  (getBooleanVariable_ValueObject(),
		   source,
		   new String[] {
		   });
		addAnnotation
		  (stringVariableEClass,
		   source,
		   new String[] {
		   });
		addAnnotation
		  (getStringVariable_ValueObject(),
		   source,
		   new String[] {
		   });
		addAnnotation
		  (valueEClass,
		   source,
		   new String[] {
		   });
		addAnnotation
		  (integerValueEClass,
		   source,
		   new String[] {
		   });
		addAnnotation
		  (getIntegerValue_Value(),
		   source,
		   new String[] {
		   });
		addAnnotation
		  (booleanValueEClass,
		   source,
		   new String[] {
		   });
		addAnnotation
		  (getBooleanValue_Value(),
		   source,
		   new String[] {
		   });
		addAnnotation
		  (stringValueEClass,
		   source,
		   new String[] {
		   });
		addAnnotation
		  (getStringValue_Value(),
		   source,
		   new String[] {
		   });
	}

} //bpmnPackageImpl
