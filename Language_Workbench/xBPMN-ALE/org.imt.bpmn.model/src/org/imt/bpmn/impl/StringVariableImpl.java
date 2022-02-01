/**
 */
package org.imt.bpmn.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.imt.bpmn.StringValue;
import org.imt.bpmn.StringVariable;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>String Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.impl.StringVariableImpl#getValueObject <em>Value Object</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StringVariableImpl extends BasicVariableImpl implements StringVariable {
	/**
	 * The cached value of the '{@link #getValueObject() <em>Value Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueObject()
	 * @generated
	 * @ordered
	 */
	protected StringValue valueObject;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StringVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return bpmnPackage.Literals.STRING_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringValue getValueObject() {
		return valueObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValueObject(StringValue newValueObject, NotificationChain msgs) {
		StringValue oldValueObject = valueObject;
		valueObject = newValueObject;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, bpmnPackage.STRING_VARIABLE__VALUE_OBJECT, oldValueObject, newValueObject);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueObject(StringValue newValueObject) {
		if (newValueObject != valueObject) {
			NotificationChain msgs = null;
			if (valueObject != null)
				msgs = ((InternalEObject)valueObject).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - bpmnPackage.STRING_VARIABLE__VALUE_OBJECT, null, msgs);
			if (newValueObject != null)
				msgs = ((InternalEObject)newValueObject).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - bpmnPackage.STRING_VARIABLE__VALUE_OBJECT, null, msgs);
			msgs = basicSetValueObject(newValueObject, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.STRING_VARIABLE__VALUE_OBJECT, newValueObject, newValueObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case bpmnPackage.STRING_VARIABLE__VALUE_OBJECT:
				return basicSetValueObject(null, msgs);
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
			case bpmnPackage.STRING_VARIABLE__VALUE_OBJECT:
				return getValueObject();
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
			case bpmnPackage.STRING_VARIABLE__VALUE_OBJECT:
				setValueObject((StringValue)newValue);
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
			case bpmnPackage.STRING_VARIABLE__VALUE_OBJECT:
				setValueObject((StringValue)null);
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
			case bpmnPackage.STRING_VARIABLE__VALUE_OBJECT:
				return valueObject != null;
		}
		return super.eIsSet(featureID);
	}

} //StringVariableImpl
