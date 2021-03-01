/**
 */
package org.imt.bpmn.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.imt.bpmn.EndEvent;
import org.imt.bpmn.Variable;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>End Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.impl.EndEventImpl#getReturnVariable <em>Return Variable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EndEventImpl extends EventImpl implements EndEvent {
	/**
	 * The cached value of the '{@link #getReturnVariable() <em>Return Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable returnVariable;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EndEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return bpmnPackage.Literals.END_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getReturnVariable() {
		if (returnVariable != null && returnVariable.eIsProxy()) {
			InternalEObject oldReturnVariable = (InternalEObject)returnVariable;
			returnVariable = (Variable)eResolveProxy(oldReturnVariable);
			if (returnVariable != oldReturnVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, bpmnPackage.END_EVENT__RETURN_VARIABLE, oldReturnVariable, returnVariable));
			}
		}
		return returnVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable basicGetReturnVariable() {
		return returnVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnVariable(Variable newReturnVariable) {
		Variable oldReturnVariable = returnVariable;
		returnVariable = newReturnVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.END_EVENT__RETURN_VARIABLE, oldReturnVariable, returnVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case bpmnPackage.END_EVENT__RETURN_VARIABLE:
				if (resolve) return getReturnVariable();
				return basicGetReturnVariable();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case bpmnPackage.END_EVENT__RETURN_VARIABLE:
				setReturnVariable((Variable)newValue);
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
			case bpmnPackage.END_EVENT__RETURN_VARIABLE:
				setReturnVariable((Variable)null);
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
			case bpmnPackage.END_EVENT__RETURN_VARIABLE:
				return returnVariable != null;
		}
		return super.eIsSet(featureID);
	}

} //EndEventImpl
