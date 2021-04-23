/**
 */
package org.imt.bpmn;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Create Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.CreateObject#getNewEntity <em>New Entity</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getCreateObject()
 * @model
 * @generated
 */
public interface CreateObject extends ObjectActivity {
	/**
	 * Returns the value of the '<em><b>New Entity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Entity</em>' containment reference.
	 * @see #setNewEntity(Entity)
	 * @see org.imt.bpmn.bpmnPackage#getCreateObject_NewEntity()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Entity getNewEntity();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.CreateObject#getNewEntity <em>New Entity</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Entity</em>' containment reference.
	 * @see #getNewEntity()
	 * @generated
	 */
	void setNewEntity(Entity value);

} // CreateObject
