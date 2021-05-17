/**
 */
package org.imt.bpmn.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.imt.bpmn.BasicVariable;
import org.imt.bpmn.ChangeObject;
import org.imt.bpmn.Value;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Change Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.impl.ChangeObjectImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link org.imt.bpmn.impl.ChangeObjectImpl#getVariableToBeChanged <em>Variable To Be Changed</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ChangeObjectImpl extends ObjectActivityImpl implements ChangeObject {
	/**
	 * The cached value of the '{@link #getNewValue() <em>New Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewValue()
	 * @generated
	 * @ordered
	 */
	protected Value newValue;
	/**
	 * The cached value of the '{@link #getVariableToBeChanged() <em>Variable To Be Changed</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariableToBeChanged()
	 * @generated
	 * @ordered
	 */
	protected BasicVariable variableToBeChanged;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChangeObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return bpmnPackage.Literals.CHANGE_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value getNewValue() {
		if (newValue != null && newValue.eIsProxy()) {
			InternalEObject oldNewValue = (InternalEObject)newValue;
			newValue = (Value)eResolveProxy(oldNewValue);
			if (newValue != oldNewValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, bpmnPackage.CHANGE_OBJECT__NEW_VALUE, oldNewValue, newValue));
			}
		}
		return newValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value basicGetNewValue() {
		return newValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewValue(Value newNewValue) {
		Value oldNewValue = newValue;
		newValue = newNewValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.CHANGE_OBJECT__NEW_VALUE, oldNewValue, newValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasicVariable getVariableToBeChanged() {
		if (variableToBeChanged != null && variableToBeChanged.eIsProxy()) {
			InternalEObject oldVariableToBeChanged = (InternalEObject)variableToBeChanged;
			variableToBeChanged = (BasicVariable)eResolveProxy(oldVariableToBeChanged);
			if (variableToBeChanged != oldVariableToBeChanged) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, bpmnPackage.CHANGE_OBJECT__VARIABLE_TO_BE_CHANGED, oldVariableToBeChanged, variableToBeChanged));
			}
		}
		return variableToBeChanged;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasicVariable basicGetVariableToBeChanged() {
		return variableToBeChanged;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariableToBeChanged(BasicVariable newVariableToBeChanged) {
		BasicVariable oldVariableToBeChanged = variableToBeChanged;
		variableToBeChanged = newVariableToBeChanged;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.CHANGE_OBJECT__VARIABLE_TO_BE_CHANGED, oldVariableToBeChanged, variableToBeChanged));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case bpmnPackage.CHANGE_OBJECT__NEW_VALUE:
				if (resolve) return getNewValue();
				return basicGetNewValue();
			case bpmnPackage.CHANGE_OBJECT__VARIABLE_TO_BE_CHANGED:
				if (resolve) return getVariableToBeChanged();
				return basicGetVariableToBeChanged();
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
			case bpmnPackage.CHANGE_OBJECT__NEW_VALUE:
				setNewValue((Value)newValue);
				return;
			case bpmnPackage.CHANGE_OBJECT__VARIABLE_TO_BE_CHANGED:
				setVariableToBeChanged((BasicVariable)newValue);
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
			case bpmnPackage.CHANGE_OBJECT__NEW_VALUE:
				setNewValue((Value)null);
				return;
			case bpmnPackage.CHANGE_OBJECT__VARIABLE_TO_BE_CHANGED:
				setVariableToBeChanged((BasicVariable)null);
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
			case bpmnPackage.CHANGE_OBJECT__NEW_VALUE:
				return newValue != null;
			case bpmnPackage.CHANGE_OBJECT__VARIABLE_TO_BE_CHANGED:
				return variableToBeChanged != null;
		}
		return super.eIsSet(featureID);
	}

} //ChangeObjectImpl
