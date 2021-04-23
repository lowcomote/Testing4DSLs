/**
 */
package org.imt.bpmn;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.Entity#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.imt.bpmn.Entity#getReferences <em>References</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getEntity()
 * @model
 * @generated
 */
public interface Entity extends Variable {
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.bpmn.BasicVariable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see org.imt.bpmn.bpmnPackage#getEntity_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<BasicVariable> getAttributes();

	/**
	 * Returns the value of the '<em><b>References</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.bpmn.Reference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>References</em>' containment reference list.
	 * @see org.imt.bpmn.bpmnPackage#getEntity_References()
	 * @model containment="true"
	 * @generated
	 */
	EList<Reference> getReferences();

} // Entity
