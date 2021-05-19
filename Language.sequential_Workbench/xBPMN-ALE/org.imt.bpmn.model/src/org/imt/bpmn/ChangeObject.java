/**
 */
package org.imt.bpmn;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Change Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.ChangeObject#getNewValue <em>New Value</em>}</li>
 *   <li>{@link org.imt.bpmn.ChangeObject#getVariableToBeChanged <em>Variable To Be Changed</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getChangeObject()
 * @model
 * @generated
 */
public interface ChangeObject extends ObjectActivity {
	/**
	 * Returns the value of the '<em><b>New Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Value</em>' reference.
	 * @see #setNewValue(Value)
	 * @see org.imt.bpmn.bpmnPackage#getChangeObject_NewValue()
	 * @model required="true"
	 * @generated
	 */
	Value getNewValue();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.ChangeObject#getNewValue <em>New Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Value</em>' reference.
	 * @see #getNewValue()
	 * @generated
	 */
	void setNewValue(Value value);

	/**
	 * Returns the value of the '<em><b>Variable To Be Changed</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable To Be Changed</em>' reference.
	 * @see #setVariableToBeChanged(BasicVariable)
	 * @see org.imt.bpmn.bpmnPackage#getChangeObject_VariableToBeChanged()
	 * @model required="true"
	 * @generated
	 */
	BasicVariable getVariableToBeChanged();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.ChangeObject#getVariableToBeChanged <em>Variable To Be Changed</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable To Be Changed</em>' reference.
	 * @see #getVariableToBeChanged()
	 * @generated
	 */
	void setVariableToBeChanged(BasicVariable value);

} // ChangeObject
