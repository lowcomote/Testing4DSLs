/**
 */
package org.imt.bpmn;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>End Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.EndEvent#getReturnVariable <em>Return Variable</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getEndEvent()
 * @model
 * @generated
 */
public interface EndEvent extends Event {
	/**
	 * Returns the value of the '<em><b>Return Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Variable</em>' reference.
	 * @see #setReturnVariable(Variable)
	 * @see org.imt.bpmn.bpmnPackage#getEndEvent_ReturnVariable()
	 * @model
	 * @generated
	 */
	Variable getReturnVariable();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.EndEvent#getReturnVariable <em>Return Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Variable</em>' reference.
	 * @see #getReturnVariable()
	 * @generated
	 */
	void setReturnVariable(Variable value);

} // EndEvent
