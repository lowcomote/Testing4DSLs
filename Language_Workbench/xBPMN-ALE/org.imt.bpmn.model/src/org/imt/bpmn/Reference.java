/**
 */
package org.imt.bpmn;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.Reference#getName <em>Name</em>}</li>
 *   <li>{@link org.imt.bpmn.Reference#getReferencedEntity <em>Referenced Entity</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getReference()
 * @model
 * @generated
 */
public interface Reference extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.imt.bpmn.bpmnPackage#getReference_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.Reference#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Referenced Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Entity</em>' reference.
	 * @see #setReferencedEntity(Entity)
	 * @see org.imt.bpmn.bpmnPackage#getReference_ReferencedEntity()
	 * @model required="true"
	 * @generated
	 */
	Entity getReferencedEntity();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.Reference#getReferencedEntity <em>Referenced Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Referenced Entity</em>' reference.
	 * @see #getReferencedEntity()
	 * @generated
	 */
	void setReferencedEntity(Entity value);

} // Reference
