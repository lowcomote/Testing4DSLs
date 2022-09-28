/**
 */
package org.imt.bpmn;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Microflow Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.MicroflowElement#getIncomingFlows <em>Incoming Flows</em>}</li>
 *   <li>{@link org.imt.bpmn.MicroflowElement#getOutgoingFlows <em>Outgoing Flows</em>}</li>
 *   <li>{@link org.imt.bpmn.MicroflowElement#getLabel <em>Label</em>}</li>
 *   <li>{@link org.imt.bpmn.MicroflowElement#getOwnerMicroflow <em>Owner Microflow</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getMicroflowElement()
 * @model
 * @generated
 */
public interface MicroflowElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Incoming Flows</b></em>' reference list.
	 * The list contents are of type {@link org.imt.bpmn.SequenceFlow}.
	 * It is bidirectional and its opposite is '{@link org.imt.bpmn.SequenceFlow#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Flows</em>' reference list.
	 * @see org.imt.bpmn.bpmnPackage#getMicroflowElement_IncomingFlows()
	 * @see org.imt.bpmn.SequenceFlow#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<SequenceFlow> getIncomingFlows();

	/**
	 * Returns the value of the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.bpmn.SequenceFlow}.
	 * It is bidirectional and its opposite is '{@link org.imt.bpmn.SequenceFlow#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Flows</em>' containment reference list.
	 * @see org.imt.bpmn.bpmnPackage#getMicroflowElement_OutgoingFlows()
	 * @see org.imt.bpmn.SequenceFlow#getSource
	 * @model opposite="source" containment="true"
	 * @generated
	 */
	EList<SequenceFlow> getOutgoingFlows();

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.imt.bpmn.bpmnPackage#getMicroflowElement_Label()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.MicroflowElement#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Owner Microflow</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.imt.bpmn.Microflow#getOwnedElements <em>Owned Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Microflow</em>' container reference.
	 * @see #setOwnerMicroflow(Microflow)
	 * @see org.imt.bpmn.bpmnPackage#getMicroflowElement_OwnerMicroflow()
	 * @see org.imt.bpmn.Microflow#getOwnedElements
	 * @model opposite="ownedElements" required="true" transient="false"
	 * @generated
	 */
	Microflow getOwnerMicroflow();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.MicroflowElement#getOwnerMicroflow <em>Owner Microflow</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner Microflow</em>' container reference.
	 * @see #getOwnerMicroflow()
	 * @generated
	 */
	void setOwnerMicroflow(Microflow value);

} // MicroflowElement
