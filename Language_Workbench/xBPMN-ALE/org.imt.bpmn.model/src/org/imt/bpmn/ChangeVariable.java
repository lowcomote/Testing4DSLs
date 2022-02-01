/**
 */
package org.imt.bpmn;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Change Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.ChangeVariable#getTargetVariable <em>Target Variable</em>}</li>
 *   <li>{@link org.imt.bpmn.ChangeVariable#getNewValue <em>New Value</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getChangeVariable()
 * @model
 * @generated
 */
public interface ChangeVariable extends VariableActivity {
	/**
	 * Returns the value of the '<em><b>Target Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Variable</em>' reference.
	 * @see #setTargetVariable(BasicVariable)
	 * @see org.imt.bpmn.bpmnPackage#getChangeVariable_TargetVariable()
	 * @model required="true"
	 * @generated
	 */
	BasicVariable getTargetVariable();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.ChangeVariable#getTargetVariable <em>Target Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Variable</em>' reference.
	 * @see #getTargetVariable()
	 * @generated
	 */
	void setTargetVariable(BasicVariable value);

	/**
	 * Returns the value of the '<em><b>New Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Value</em>' containment reference.
	 * @see #setNewValue(Value)
	 * @see org.imt.bpmn.bpmnPackage#getChangeVariable_NewValue()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Value getNewValue();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.ChangeVariable#getNewValue <em>New Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Value</em>' containment reference.
	 * @see #getNewValue()
	 * @generated
	 */
	void setNewValue(Value value);

} // ChangeVariable
