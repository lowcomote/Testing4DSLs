/**
 */
package org.imt.bpmn;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sequence Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.SequenceFlow#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link org.imt.bpmn.SequenceFlow#getSource <em>Source</em>}</li>
 *   <li>{@link org.imt.bpmn.SequenceFlow#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getSequenceFlow()
 * @model
 * @generated
 */
public interface SequenceFlow extends EObject {
	/**
	 * Returns the value of the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint</em>' attribute.
	 * @see #setConstraint(String)
	 * @see org.imt.bpmn.bpmnPackage#getSequenceFlow_Constraint()
	 * @model
	 * @generated
	 */
	String getConstraint();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.SequenceFlow#getConstraint <em>Constraint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraint</em>' attribute.
	 * @see #getConstraint()
	 * @generated
	 */
	void setConstraint(String value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.imt.bpmn.MicroflowElement#getOutgoingFlows <em>Outgoing Flows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' container reference.
	 * @see #setSource(MicroflowElement)
	 * @see org.imt.bpmn.bpmnPackage#getSequenceFlow_Source()
	 * @see org.imt.bpmn.MicroflowElement#getOutgoingFlows
	 * @model opposite="outgoingFlows" required="true" transient="false"
	 * @generated
	 */
	MicroflowElement getSource();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.SequenceFlow#getSource <em>Source</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' container reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(MicroflowElement value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.imt.bpmn.MicroflowElement#getIncomingFlows <em>Incoming Flows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(MicroflowElement)
	 * @see org.imt.bpmn.bpmnPackage#getSequenceFlow_Target()
	 * @see org.imt.bpmn.MicroflowElement#getIncomingFlows
	 * @model opposite="incomingFlows" required="true"
	 * @generated
	 */
	MicroflowElement getTarget();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.SequenceFlow#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(MicroflowElement value);

} // SequenceFlow
