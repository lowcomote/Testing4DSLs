/**
 */
package org.imt.bpmn;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Microflow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.Microflow#getName <em>Name</em>}</li>
 *   <li>{@link org.imt.bpmn.Microflow#getOwnedElements <em>Owned Elements</em>}</li>
 *   <li>{@link org.imt.bpmn.Microflow#getCurrentNode <em>Current Node</em>}</li>
 *   <li>{@link org.imt.bpmn.Microflow#getValuedVariables <em>Valued Variables</em>}</li>
 *   <li>{@link org.imt.bpmn.Microflow#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getMicroflow()
 * @model
 * @generated
 */
public interface Microflow extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.imt.bpmn.bpmnPackage#getMicroflow_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.Microflow#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Owned Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.bpmn.MicroflowElement}.
	 * It is bidirectional and its opposite is '{@link org.imt.bpmn.MicroflowElement#getOwnerMicroflow <em>Owner Microflow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Elements</em>' containment reference list.
	 * @see org.imt.bpmn.bpmnPackage#getMicroflow_OwnedElements()
	 * @see org.imt.bpmn.MicroflowElement#getOwnerMicroflow
	 * @model opposite="ownerMicroflow" containment="true"
	 * @generated
	 */
	EList<MicroflowElement> getOwnedElements();

	/**
	 * Returns the value of the '<em><b>Current Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Node</em>' reference.
	 * @see #setCurrentNode(MicroflowElement)
	 * @see org.imt.bpmn.bpmnPackage#getMicroflow_CurrentNode()
	 * @model annotation="dynamic"
	 * @generated
	 */
	MicroflowElement getCurrentNode();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.Microflow#getCurrentNode <em>Current Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Node</em>' reference.
	 * @see #getCurrentNode()
	 * @generated
	 */
	void setCurrentNode(MicroflowElement value);

	/**
	 * Returns the value of the '<em><b>Valued Variables</b></em>' reference list.
	 * The list contents are of type {@link org.imt.bpmn.Variable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Valued Variables</em>' reference list.
	 * @see org.imt.bpmn.bpmnPackage#getMicroflow_ValuedVariables()
	 * @model annotation="dynamic"
	 * @generated
	 */
	EList<Variable> getValuedVariables();

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.bpmn.Variable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.imt.bpmn.bpmnPackage#getMicroflow_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getParameters();

} // Microflow
