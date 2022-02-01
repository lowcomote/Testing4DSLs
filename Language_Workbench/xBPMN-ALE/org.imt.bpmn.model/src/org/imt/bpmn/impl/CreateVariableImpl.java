/**
 */
package org.imt.bpmn.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.imt.bpmn.BasicVariable;
import org.imt.bpmn.CreateVariable;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Create Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.impl.CreateVariableImpl#getNewVariable <em>New Variable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CreateVariableImpl extends VariableActivityImpl implements CreateVariable {
	/**
	 * The cached value of the '{@link #getNewVariable() <em>New Variable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewVariable()
	 * @generated
	 * @ordered
	 */
	protected BasicVariable newVariable;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CreateVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return bpmnPackage.Literals.CREATE_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasicVariable getNewVariable() {
		return newVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNewVariable(BasicVariable newNewVariable, NotificationChain msgs) {
		BasicVariable oldNewVariable = newVariable;
		newVariable = newNewVariable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, bpmnPackage.CREATE_VARIABLE__NEW_VARIABLE, oldNewVariable, newNewVariable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewVariable(BasicVariable newNewVariable) {
		if (newNewVariable != newVariable) {
			NotificationChain msgs = null;
			if (newVariable != null)
				msgs = ((InternalEObject)newVariable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - bpmnPackage.CREATE_VARIABLE__NEW_VARIABLE, null, msgs);
			if (newNewVariable != null)
				msgs = ((InternalEObject)newNewVariable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - bpmnPackage.CREATE_VARIABLE__NEW_VARIABLE, null, msgs);
			msgs = basicSetNewVariable(newNewVariable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.CREATE_VARIABLE__NEW_VARIABLE, newNewVariable, newNewVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case bpmnPackage.CREATE_VARIABLE__NEW_VARIABLE:
				return basicSetNewVariable(null, msgs);
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
			case bpmnPackage.CREATE_VARIABLE__NEW_VARIABLE:
				return getNewVariable();
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
			case bpmnPackage.CREATE_VARIABLE__NEW_VARIABLE:
				setNewVariable((BasicVariable)newValue);
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
			case bpmnPackage.CREATE_VARIABLE__NEW_VARIABLE:
				setNewVariable((BasicVariable)null);
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
			case bpmnPackage.CREATE_VARIABLE__NEW_VARIABLE:
				return newVariable != null;
		}
		return super.eIsSet(featureID);
	}

} //CreateVariableImpl
