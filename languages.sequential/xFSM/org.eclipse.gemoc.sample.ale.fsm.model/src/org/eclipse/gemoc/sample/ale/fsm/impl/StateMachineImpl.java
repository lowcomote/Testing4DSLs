/**
 */
package org.eclipse.gemoc.sample.ale.fsm.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.gemoc.sample.ale.fsm.FsmPackage;
import org.eclipse.gemoc.sample.ale.fsm.State;
import org.eclipse.gemoc.sample.ale.fsm.StateMachine;
import org.eclipse.gemoc.sample.ale.fsm.Transition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.impl.StateMachineImpl#getOwnedStates <em>Owned States</em>}</li>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.impl.StateMachineImpl#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.impl.StateMachineImpl#getOwnedTransitions <em>Owned Transitions</em>}</li>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.impl.StateMachineImpl#getCurrentState <em>Current State</em>}</li>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.impl.StateMachineImpl#getUnprocessedString <em>Unprocessed String</em>}</li>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.impl.StateMachineImpl#getConsumedString <em>Consumed String</em>}</li>
 *   <li>{@link org.eclipse.gemoc.sample.ale.fsm.impl.StateMachineImpl#getProducedString <em>Produced String</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StateMachineImpl extends NamedElementImpl implements StateMachine {
	/**
	 * The cached value of the '{@link #getOwnedStates() <em>Owned States</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> ownedStates;

	/**
	 * The cached value of the '{@link #getInitialState() <em>Initial State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialState()
	 * @generated
	 * @ordered
	 */
	protected State initialState;

	/**
	 * The cached value of the '{@link #getOwnedTransitions() <em>Owned Transitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> ownedTransitions;

	/**
	 * The cached value of the '{@link #getCurrentState() <em>Current State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentState()
	 * @generated
	 * @ordered
	 */
	protected State currentState;

	/**
	 * The default value of the '{@link #getUnprocessedString() <em>Unprocessed String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnprocessedString()
	 * @generated
	 * @ordered
	 */
	protected static final String UNPROCESSED_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUnprocessedString() <em>Unprocessed String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnprocessedString()
	 * @generated
	 * @ordered
	 */
	protected String unprocessedString = UNPROCESSED_STRING_EDEFAULT;

	/**
	 * The default value of the '{@link #getConsumedString() <em>Consumed String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConsumedString()
	 * @generated
	 * @ordered
	 */
	protected static final String CONSUMED_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConsumedString() <em>Consumed String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConsumedString()
	 * @generated
	 * @ordered
	 */
	protected String consumedString = CONSUMED_STRING_EDEFAULT;

	/**
	 * The default value of the '{@link #getProducedString() <em>Produced String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProducedString()
	 * @generated
	 * @ordered
	 */
	protected static final String PRODUCED_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProducedString() <em>Produced String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProducedString()
	 * @generated
	 * @ordered
	 */
	protected String producedString = PRODUCED_STRING_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateMachineImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FsmPackage.Literals.STATE_MACHINE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<State> getOwnedStates() {
		if (ownedStates == null) {
			ownedStates = new EObjectContainmentWithInverseEList<State>(State.class, this, FsmPackage.STATE_MACHINE__OWNED_STATES, FsmPackage.STATE__OWNING_FSM);
		}
		return ownedStates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public State getInitialState() {
		if (initialState != null && initialState.eIsProxy()) {
			InternalEObject oldInitialState = (InternalEObject)initialState;
			initialState = (State)eResolveProxy(oldInitialState);
			if (initialState != oldInitialState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FsmPackage.STATE_MACHINE__INITIAL_STATE, oldInitialState, initialState));
			}
		}
		return initialState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetInitialState() {
		return initialState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInitialState(State newInitialState) {
		State oldInitialState = initialState;
		initialState = newInitialState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FsmPackage.STATE_MACHINE__INITIAL_STATE, oldInitialState, initialState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Transition> getOwnedTransitions() {
		if (ownedTransitions == null) {
			ownedTransitions = new EObjectContainmentEList<Transition>(Transition.class, this, FsmPackage.STATE_MACHINE__OWNED_TRANSITIONS);
		}
		return ownedTransitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public State getCurrentState() {
		if (currentState != null && currentState.eIsProxy()) {
			InternalEObject oldCurrentState = (InternalEObject)currentState;
			currentState = (State)eResolveProxy(oldCurrentState);
			if (currentState != oldCurrentState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FsmPackage.STATE_MACHINE__CURRENT_STATE, oldCurrentState, currentState));
			}
		}
		return currentState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetCurrentState() {
		return currentState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCurrentState(State newCurrentState) {
		State oldCurrentState = currentState;
		currentState = newCurrentState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FsmPackage.STATE_MACHINE__CURRENT_STATE, oldCurrentState, currentState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUnprocessedString() {
		return unprocessedString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUnprocessedString(String newUnprocessedString) {
		String oldUnprocessedString = unprocessedString;
		unprocessedString = newUnprocessedString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FsmPackage.STATE_MACHINE__UNPROCESSED_STRING, oldUnprocessedString, unprocessedString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getConsumedString() {
		return consumedString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConsumedString(String newConsumedString) {
		String oldConsumedString = consumedString;
		consumedString = newConsumedString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FsmPackage.STATE_MACHINE__CONSUMED_STRING, oldConsumedString, consumedString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getProducedString() {
		return producedString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProducedString(String newProducedString) {
		String oldProducedString = producedString;
		producedString = newProducedString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FsmPackage.STATE_MACHINE__PRODUCED_STRING, oldProducedString, producedString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FsmPackage.STATE_MACHINE__OWNED_STATES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOwnedStates()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FsmPackage.STATE_MACHINE__OWNED_STATES:
				return ((InternalEList<?>)getOwnedStates()).basicRemove(otherEnd, msgs);
			case FsmPackage.STATE_MACHINE__OWNED_TRANSITIONS:
				return ((InternalEList<?>)getOwnedTransitions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FsmPackage.STATE_MACHINE__OWNED_STATES:
				return getOwnedStates();
			case FsmPackage.STATE_MACHINE__INITIAL_STATE:
				if (resolve) return getInitialState();
				return basicGetInitialState();
			case FsmPackage.STATE_MACHINE__OWNED_TRANSITIONS:
				return getOwnedTransitions();
			case FsmPackage.STATE_MACHINE__CURRENT_STATE:
				if (resolve) return getCurrentState();
				return basicGetCurrentState();
			case FsmPackage.STATE_MACHINE__UNPROCESSED_STRING:
				return getUnprocessedString();
			case FsmPackage.STATE_MACHINE__CONSUMED_STRING:
				return getConsumedString();
			case FsmPackage.STATE_MACHINE__PRODUCED_STRING:
				return getProducedString();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FsmPackage.STATE_MACHINE__OWNED_STATES:
				getOwnedStates().clear();
				getOwnedStates().addAll((Collection<? extends State>)newValue);
				return;
			case FsmPackage.STATE_MACHINE__INITIAL_STATE:
				setInitialState((State)newValue);
				return;
			case FsmPackage.STATE_MACHINE__OWNED_TRANSITIONS:
				getOwnedTransitions().clear();
				getOwnedTransitions().addAll((Collection<? extends Transition>)newValue);
				return;
			case FsmPackage.STATE_MACHINE__CURRENT_STATE:
				setCurrentState((State)newValue);
				return;
			case FsmPackage.STATE_MACHINE__UNPROCESSED_STRING:
				setUnprocessedString((String)newValue);
				return;
			case FsmPackage.STATE_MACHINE__CONSUMED_STRING:
				setConsumedString((String)newValue);
				return;
			case FsmPackage.STATE_MACHINE__PRODUCED_STRING:
				setProducedString((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case FsmPackage.STATE_MACHINE__OWNED_STATES:
				getOwnedStates().clear();
				return;
			case FsmPackage.STATE_MACHINE__INITIAL_STATE:
				setInitialState((State)null);
				return;
			case FsmPackage.STATE_MACHINE__OWNED_TRANSITIONS:
				getOwnedTransitions().clear();
				return;
			case FsmPackage.STATE_MACHINE__CURRENT_STATE:
				setCurrentState((State)null);
				return;
			case FsmPackage.STATE_MACHINE__UNPROCESSED_STRING:
				setUnprocessedString(UNPROCESSED_STRING_EDEFAULT);
				return;
			case FsmPackage.STATE_MACHINE__CONSUMED_STRING:
				setConsumedString(CONSUMED_STRING_EDEFAULT);
				return;
			case FsmPackage.STATE_MACHINE__PRODUCED_STRING:
				setProducedString(PRODUCED_STRING_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case FsmPackage.STATE_MACHINE__OWNED_STATES:
				return ownedStates != null && !ownedStates.isEmpty();
			case FsmPackage.STATE_MACHINE__INITIAL_STATE:
				return initialState != null;
			case FsmPackage.STATE_MACHINE__OWNED_TRANSITIONS:
				return ownedTransitions != null && !ownedTransitions.isEmpty();
			case FsmPackage.STATE_MACHINE__CURRENT_STATE:
				return currentState != null;
			case FsmPackage.STATE_MACHINE__UNPROCESSED_STRING:
				return UNPROCESSED_STRING_EDEFAULT == null ? unprocessedString != null : !UNPROCESSED_STRING_EDEFAULT.equals(unprocessedString);
			case FsmPackage.STATE_MACHINE__CONSUMED_STRING:
				return CONSUMED_STRING_EDEFAULT == null ? consumedString != null : !CONSUMED_STRING_EDEFAULT.equals(consumedString);
			case FsmPackage.STATE_MACHINE__PRODUCED_STRING:
				return PRODUCED_STRING_EDEFAULT == null ? producedString != null : !PRODUCED_STRING_EDEFAULT.equals(producedString);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (unprocessedString: ");
		result.append(unprocessedString);
		result.append(", consumedString: ");
		result.append(consumedString);
		result.append(", producedString: ");
		result.append(producedString);
		result.append(')');
		return result.toString();
	}

} //StateMachineImpl
