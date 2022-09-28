/**
 */
package org.eclipse.gemoc.sample.ale.fsm;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.StateMachine#getOwnedStates <em>Owned States</em>}</li>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.StateMachine#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.StateMachine#getOwnedTransitions <em>Owned Transitions</em>}</li>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.StateMachine#getCurrentState <em>Current State</em>}</li>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.StateMachine#getUnprocessedString <em>Unprocessed String</em>}</li>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.StateMachine#getConsumedString <em>Consumed String</em>}</li>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.StateMachine#getProducedString <em>Produced String</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gemoc.sample.ale.fsm.FsmPackage#getStateMachine()
 * @model
 * @generated
 */
public interface StateMachine extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Owned States</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.gemoc.sample.ale.fsm.State}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.gemoc.sample.ale.fsm.State#getOwningFSM <em>Owning FSM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned States</em>' containment reference list.
	 * @see org.eclipse.gemoc.sample.ale.fsm.FsmPackage#getStateMachine_OwnedStates()
	 * @see org.eclipse.gemoc.sample.ale.fsm.State#getOwningFSM
	 * @model opposite="owningFSM" containment="true"
	 * @generated
	 */
	EList<State> getOwnedStates();

	/**
	 * Returns the value of the '<em><b>Initial State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial State</em>' reference.
	 * @see #setInitialState(State)
	 * @see org.eclipse.gemoc.sample.ale.fsm.FsmPackage#getStateMachine_InitialState()
	 * @model required="true"
	 * @generated
	 */
	State getInitialState();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.sample.ale.fsm.StateMachine#getInitialState <em>Initial State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial State</em>' reference.
	 * @see #getInitialState()
	 * @generated
	 */
	void setInitialState(State value);

	/**
	 * Returns the value of the '<em><b>Owned Transitions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.gemoc.sample.ale.fsm.Transition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Transitions</em>' containment reference list.
	 * @see org.eclipse.gemoc.sample.ale.fsm.FsmPackage#getStateMachine_OwnedTransitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Transition> getOwnedTransitions();

	/**
	 * Returns the value of the '<em><b>Current State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current State</em>' reference.
	 * @see #setCurrentState(State)
	 * @see org.eclipse.gemoc.sample.ale.fsm.FsmPackage#getStateMachine_CurrentState()
	 * @model annotation="dynamic"
	 * @generated
	 */
	State getCurrentState();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.sample.ale.fsm.StateMachine#getCurrentState <em>Current State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current State</em>' reference.
	 * @see #getCurrentState()
	 * @generated
	 */
	void setCurrentState(State value);

	/**
	 * Returns the value of the '<em><b>Unprocessed String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unprocessed String</em>' attribute.
	 * @see #setUnprocessedString(String)
	 * @see org.eclipse.gemoc.sample.ale.fsm.FsmPackage#getStateMachine_UnprocessedString()
	 * @model annotation="dynamic"
	 * @generated
	 */
	String getUnprocessedString();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.sample.ale.fsm.StateMachine#getUnprocessedString <em>Unprocessed String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unprocessed String</em>' attribute.
	 * @see #getUnprocessedString()
	 * @generated
	 */
	void setUnprocessedString(String value);

	/**
	 * Returns the value of the '<em><b>Consumed String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Consumed String</em>' attribute.
	 * @see #setConsumedString(String)
	 * @see org.eclipse.gemoc.sample.ale.fsm.FsmPackage#getStateMachine_ConsumedString()
	 * @model annotation="dynamic"
	 * @generated
	 */
	String getConsumedString();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.sample.ale.fsm.StateMachine#getConsumedString <em>Consumed String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Consumed String</em>' attribute.
	 * @see #getConsumedString()
	 * @generated
	 */
	void setConsumedString(String value);

	/**
	 * Returns the value of the '<em><b>Produced String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Produced String</em>' attribute.
	 * @see #setProducedString(String)
	 * @see org.eclipse.gemoc.sample.ale.fsm.FsmPackage#getStateMachine_ProducedString()
	 * @model annotation="dynamic"
	 * @generated
	 */
	String getProducedString();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.sample.ale.fsm.StateMachine#getProducedString <em>Produced String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Produced String</em>' attribute.
	 * @see #getProducedString()
	 * @generated
	 */
	void setProducedString(String value);

} // StateMachine
