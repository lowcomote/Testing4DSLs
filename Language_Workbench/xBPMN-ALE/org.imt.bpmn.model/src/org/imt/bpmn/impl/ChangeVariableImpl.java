/**
 */
package org.imt.bpmn.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.imt.bpmn.BasicVariable;
import org.imt.bpmn.ChangeVariable;
import org.imt.bpmn.Value;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Change Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.impl.ChangeVariableImpl#getTargetVariable <em>Target Variable</em>}</li>
 *   <li>{@link org.imt.bpmn.impl.ChangeVariableImpl#getNewValue <em>New Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ChangeVariableImpl extends VariableActivityImpl implements ChangeVariable {
	/**
	 * The cached value of the '{@link #getTargetVariable() <em>Target Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetVariable()
	 * @generated
	 * @ordered
	 */
	protected BasicVariable targetVariable;

	/**
	 * The cached value of the '{@link #getNewValue() <em>New Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewValue()
	 * @generated
	 * @ordered
	 */
	protected Value newValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChangeVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return bpmnPackage.Literals.CHANGE_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasicVariable getTargetVariable() {
		if (targetVariable != null && targetVariable.eIsProxy()) {
			InternalEObject oldTargetVariable = (InternalEObject)targetVariable;
			targetVariable = (BasicVariable)eResolveProxy(oldTargetVariable);
			if (targetVariable != oldTargetVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, bpmnPackage.CHANGE_VARIABLE__TARGET_VARIABLE, oldTargetVariable, targetVariable));
			}
		}
		return targetVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasicVariable basicGetTargetVariable() {
		return targetVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetVariable(BasicVariable newTargetVariable) {
		BasicVariable oldTargetVariable = targetVariable;
		targetVariable = newTargetVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.CHANGE_VARIABLE__TARGET_VARIABLE, oldTargetVariable, targetVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value getNewValue() {
		return newValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNewValue(Value newNewValue, NotificationChain msgs) {
		Value oldNewValue = newValue;
		newValue = newNewValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, bpmnPackage.CHANGE_VARIABLE__NEW_VALUE, oldNewValue, newNewValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewValue(Value newNewValue) {
		if (newNewValue != newValue) {
			NotificationChain msgs = null;
			if (newValue != null)
				msgs = ((InternalEObject)newValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - bpmnPackage.CHANGE_VARIABLE__NEW_VALUE, null, msgs);
			if (newNewValue != null)
				msgs = ((InternalEObject)newNewValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - bpmnPackage.CHANGE_VARIABLE__NEW_VALUE, null, msgs);
			msgs = basicSetNewValue(newNewValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.CHANGE_VARIABLE__NEW_VALUE, newNewValue, newNewValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case bpmnPackage.CHANGE_VARIABLE__NEW_VALUE:
				return basicSetNewValue(null, msgs);
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
			case bpmnPackage.CHANGE_VARIABLE__TARGET_VARIABLE:
				if (resolve) return getTargetVariable();
				return basicGetTargetVariable();
			case bpmnPackage.CHANGE_VARIABLE__NEW_VALUE:
				return getNewValue();
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
			case bpmnPackage.CHANGE_VARIABLE__TARGET_VARIABLE:
				setTargetVariable((BasicVariable)newValue);
				return;
			case bpmnPackage.CHANGE_VARIABLE__NEW_VALUE:
				setNewValue((Value)newValue);
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
			case bpmnPackage.CHANGE_VARIABLE__TARGET_VARIABLE:
				setTargetVariable((BasicVariable)null);
				return;
			case bpmnPackage.CHANGE_VARIABLE__NEW_VALUE:
				setNewValue((Value)null);
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
			case bpmnPackage.CHANGE_VARIABLE__TARGET_VARIABLE:
				return targetVariable != null;
			case bpmnPackage.CHANGE_VARIABLE__NEW_VALUE:
				return newValue != null;
		}
		return super.eIsSet(featureID);
	}

} //ChangeVariableImpl
