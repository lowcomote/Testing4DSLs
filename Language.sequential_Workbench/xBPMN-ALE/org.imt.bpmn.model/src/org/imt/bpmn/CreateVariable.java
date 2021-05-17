/**
 */
package org.imt.bpmn;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Create Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.CreateVariable#getNewVariable <em>New Variable</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getCreateVariable()
 * @model
 * @generated
 */
public interface CreateVariable extends VariableActivity {
	/**
	 * Returns the value of the '<em><b>New Variable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Variable</em>' containment reference.
	 * @see #setNewVariable(BasicVariable)
	 * @see org.imt.bpmn.bpmnPackage#getCreateVariable_NewVariable()
	 * @model containment="true" required="true"
	 * @generated
	 */
	BasicVariable getNewVariable();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.CreateVariable#getNewVariable <em>New Variable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Variable</em>' containment reference.
	 * @see #getNewVariable()
	 * @generated
	 */
	void setNewVariable(BasicVariable value);

} // CreateVariable
