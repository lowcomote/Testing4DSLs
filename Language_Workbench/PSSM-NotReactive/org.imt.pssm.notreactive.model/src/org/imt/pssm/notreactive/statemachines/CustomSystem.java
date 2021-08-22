/**
 */
package org.imt.pssm.notreactive.statemachines;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Custom System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.pssm.notreactive.statemachines.CustomSystem#getStatemachine <em>Statemachine</em>}</li>
 *   <li>{@link org.imt.pssm.notreactive.statemachines.CustomSystem#getSignals <em>Signals</em>}</li>
 *   <li>{@link org.imt.pssm.notreactive.statemachines.CustomSystem#getOperations <em>Operations</em>}</li>
 *   <li>{@link org.imt.pssm.notreactive.statemachines.CustomSystem#getEventOccurrences <em>Event Occurrences</em>}</li>
 *   <li>{@link org.imt.pssm.notreactive.statemachines.CustomSystem#getPerformedBehaviors <em>Performed Behaviors</em>}</li>
 * </ul>
 *
 * @see org.imt.pssm.notreactive.statemachines.StatemachinesPackage#getCustomSystem()
 * @model
 * @generated
 */
public interface CustomSystem extends EObject {
	/**
	 * Returns the value of the '<em><b>Statemachine</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Statemachine</em>' containment reference.
	 * @see #setStatemachine(StateMachine)
	 * @see org.imt.pssm.notreactive.statemachines.StatemachinesPackage#getCustomSystem_Statemachine()
	 * @model containment="true" required="true"
	 * @generated
	 */
	StateMachine getStatemachine();

	/**
	 * Sets the value of the '{@link org.imt.pssm.notreactive.statemachines.CustomSystem#getStatemachine <em>Statemachine</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Statemachine</em>' containment reference.
	 * @see #getStatemachine()
	 * @generated
	 */
	void setStatemachine(StateMachine value);

	/**
	 * Returns the value of the '<em><b>Signals</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.pssm.notreactive.statemachines.Signal}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signals</em>' containment reference list.
	 * @see org.imt.pssm.notreactive.statemachines.StatemachinesPackage#getCustomSystem_Signals()
	 * @model containment="true"
	 * @generated
	 */
	EList<Signal> getSignals();

	/**
	 * Returns the value of the '<em><b>Operations</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.pssm.notreactive.statemachines.Operation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operations</em>' containment reference list.
	 * @see org.imt.pssm.notreactive.statemachines.StatemachinesPackage#getCustomSystem_Operations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Operation> getOperations();

	/**
	 * Returns the value of the '<em><b>Event Occurrences</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.pssm.notreactive.statemachines.EventOccurrence}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Occurrences</em>' containment reference list.
	 * @see org.imt.pssm.notreactive.statemachines.StatemachinesPackage#getCustomSystem_EventOccurrences()
	 * @model containment="true"
	 *        annotation="dynamic"
	 * @generated
	 */
	EList<EventOccurrence> getEventOccurrences();

	/**
	 * Returns the value of the '<em><b>Performed Behaviors</b></em>' reference list.
	 * The list contents are of type {@link org.imt.pssm.notreactive.statemachines.Behavior}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Performed Behaviors</em>' reference list.
	 * @see org.imt.pssm.notreactive.statemachines.StatemachinesPackage#getCustomSystem_PerformedBehaviors()
	 * @model annotation="dynamic"
	 * @generated
	 */
	EList<Behavior> getPerformedBehaviors();

} // CustomSystem
