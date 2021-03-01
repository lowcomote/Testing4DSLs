/**
 */
package org.imt.bpmn;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.imt.bpmn.bpmnPackage
 * @generated
 */
public interface bpmnFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	bpmnFactory eINSTANCE = org.imt.bpmn.impl.bpmnFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Microflow</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Microflow</em>'.
	 * @generated
	 */
	Microflow createMicroflow();

	/**
	 * Returns a new object of class '<em>Microflow Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Microflow Element</em>'.
	 * @generated
	 */
	MicroflowElement createMicroflowElement();

	/**
	 * Returns a new object of class '<em>Sequence Flow</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sequence Flow</em>'.
	 * @generated
	 */
	SequenceFlow createSequenceFlow();

	/**
	 * Returns a new object of class '<em>Create Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Create Object</em>'.
	 * @generated
	 */
	CreateObject createCreateObject();

	/**
	 * Returns a new object of class '<em>Delete Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delete Object</em>'.
	 * @generated
	 */
	DeleteObject createDeleteObject();

	/**
	 * Returns a new object of class '<em>Change Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Change Object</em>'.
	 * @generated
	 */
	ChangeObject createChangeObject();

	/**
	 * Returns a new object of class '<em>Retrieve Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Retrieve Object</em>'.
	 * @generated
	 */
	RetrieveObject createRetrieveObject();

	/**
	 * Returns a new object of class '<em>Microflow Call</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Microflow Call</em>'.
	 * @generated
	 */
	MicroflowCall createMicroflowCall();

	/**
	 * Returns a new object of class '<em>Create Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Create Variable</em>'.
	 * @generated
	 */
	CreateVariable createCreateVariable();

	/**
	 * Returns a new object of class '<em>Change Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Change Variable</em>'.
	 * @generated
	 */
	ChangeVariable createChangeVariable();

	/**
	 * Returns a new object of class '<em>Show Message</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Show Message</em>'.
	 * @generated
	 */
	ShowMessage createShowMessage();

	/**
	 * Returns a new object of class '<em>Start Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Start Event</em>'.
	 * @generated
	 */
	StartEvent createStartEvent();

	/**
	 * Returns a new object of class '<em>End Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>End Event</em>'.
	 * @generated
	 */
	EndEvent createEndEvent();

	/**
	 * Returns a new object of class '<em>Entity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Entity</em>'.
	 * @generated
	 */
	Entity createEntity();

	/**
	 * Returns a new object of class '<em>Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reference</em>'.
	 * @generated
	 */
	Reference createReference();

	/**
	 * Returns a new object of class '<em>Basic Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Basic Variable</em>'.
	 * @generated
	 */
	BasicVariable createBasicVariable();

	/**
	 * Returns a new object of class '<em>Integer Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer Variable</em>'.
	 * @generated
	 */
	IntegerVariable createIntegerVariable();

	/**
	 * Returns a new object of class '<em>Boolean Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Variable</em>'.
	 * @generated
	 */
	BooleanVariable createBooleanVariable();

	/**
	 * Returns a new object of class '<em>String Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Variable</em>'.
	 * @generated
	 */
	StringVariable createStringVariable();

	/**
	 * Returns a new object of class '<em>Integer Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer Value</em>'.
	 * @generated
	 */
	IntegerValue createIntegerValue();

	/**
	 * Returns a new object of class '<em>Boolean Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Value</em>'.
	 * @generated
	 */
	BooleanValue createBooleanValue();

	/**
	 * Returns a new object of class '<em>String Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Value</em>'.
	 * @generated
	 */
	StringValue createStringValue();

	/**
	 * Returns a new object of class '<em>Fork Decision</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Fork Decision</em>'.
	 * @generated
	 */
	ForkDecision createForkDecision();

	/**
	 * Returns a new object of class '<em>Merge Decision</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Merge Decision</em>'.
	 * @generated
	 */
	MergeDecision createMergeDecision();

	/**
	 * Returns a new object of class '<em>Integer Comparison Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer Comparison Expression</em>'.
	 * @generated
	 */
	IntegerComparisonExpression createIntegerComparisonExpression();

	/**
	 * Returns a new object of class '<em>String Comparison Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Comparison Expression</em>'.
	 * @generated
	 */
	StringComparisonExpression createStringComparisonExpression();

	/**
	 * Returns a new object of class '<em>Boolean Binary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Binary Expression</em>'.
	 * @generated
	 */
	BooleanBinaryExpression createBooleanBinaryExpression();

	/**
	 * Returns a new object of class '<em>Boolean Unary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Unary Expression</em>'.
	 * @generated
	 */
	BooleanUnaryExpression createBooleanUnaryExpression();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	bpmnPackage getbpmnPackage();

} //bpmnFactory
