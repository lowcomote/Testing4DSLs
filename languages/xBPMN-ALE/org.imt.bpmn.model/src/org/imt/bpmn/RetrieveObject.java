/**
 */
package org.imt.bpmn;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Retrieve Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.RetrieveObject#getConstraint <em>Constraint</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getRetrieveObject()
 * @model
 * @generated
 */
public interface RetrieveObject extends ObjectActivity {
	/**
	 * Returns the value of the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint</em>' attribute.
	 * @see #setConstraint(String)
	 * @see org.imt.bpmn.bpmnPackage#getRetrieveObject_Constraint()
	 * @model
	 * @generated
	 */
	String getConstraint();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.RetrieveObject#getConstraint <em>Constraint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraint</em>' attribute.
	 * @see #getConstraint()
	 * @generated
	 */
	void setConstraint(String value);

} // RetrieveObject
