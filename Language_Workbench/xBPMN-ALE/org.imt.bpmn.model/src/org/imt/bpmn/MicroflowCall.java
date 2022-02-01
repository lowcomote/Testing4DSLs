/**
 */
package org.imt.bpmn;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Microflow Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.MicroflowCall#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getMicroflowCall()
 * @model
 * @generated
 */
public interface MicroflowCall extends ActionCallActivity {
	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Microflow)
	 * @see org.imt.bpmn.bpmnPackage#getMicroflowCall_Target()
	 * @model required="true"
	 * @generated
	 */
	Microflow getTarget();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.MicroflowCall#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Microflow value);

} // MicroflowCall
