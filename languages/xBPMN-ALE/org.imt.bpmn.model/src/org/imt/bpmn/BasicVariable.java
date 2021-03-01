/**
 */
package org.imt.bpmn;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Basic Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.BasicVariable#getValueObject <em>Value Object</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getBasicVariable()
 * @model
 * @generated
 */
public interface BasicVariable extends Variable {
	/**
	 * Returns the value of the '<em><b>Value Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Object</em>' containment reference.
	 * @see #setValueObject(Value)
	 * @see org.imt.bpmn.bpmnPackage#getBasicVariable_ValueObject()
	 * @model containment="true"
	 * @generated
	 */
	Value getValueObject();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.BasicVariable#getValueObject <em>Value Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Object</em>' containment reference.
	 * @see #getValueObject()
	 * @generated
	 */
	void setValueObject(Value value);

} // BasicVariable
