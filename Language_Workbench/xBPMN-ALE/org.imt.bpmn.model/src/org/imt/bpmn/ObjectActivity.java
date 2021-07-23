/**
 */
package org.imt.bpmn;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Object Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.ObjectActivity#getEntity <em>Entity</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getObjectActivity()
 * @model abstract="true"
 * @generated
 */
public interface ObjectActivity extends Activity {
	/**
	 * Returns the value of the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity</em>' reference.
	 * @see #setEntity(Entity)
	 * @see org.imt.bpmn.bpmnPackage#getObjectActivity_Entity()
	 * @model required="true"
	 * @generated
	 */
	Entity getEntity();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.ObjectActivity#getEntity <em>Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity</em>' reference.
	 * @see #getEntity()
	 * @generated
	 */
	void setEntity(Entity value);

} // ObjectActivity
