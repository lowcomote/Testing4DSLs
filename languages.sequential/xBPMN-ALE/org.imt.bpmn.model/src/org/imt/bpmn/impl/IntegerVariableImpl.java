/**
 */
package org.imt.bpmn.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.imt.bpmn.IntegerValue;
import org.imt.bpmn.IntegerVariable;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Integer Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.impl.IntegerVariableImpl#getValueObject <em>Value Object</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IntegerVariableImpl extends BasicVariableImpl implements IntegerVariable {
	/**
	 * The cached value of the '{@link #getValueObject() <em>Value Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueObject()
	 * @generated
	 * @ordered
	 */
	protected IntegerValue valueObject;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IntegerVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return bpmnPackage.Literals.INTEGER_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerValue getValueObject() {
		return valueObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValueObject(IntegerValue newValueObject, NotificationChain msgs) {
		IntegerValue oldValueObject = valueObject;
		valueObject = newValueObject;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, bpmnPackage.INTEGER_VARIABLE__VALUE_OBJECT, oldValueObject, newValueObject);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueObject(IntegerValue newValueObject) {
		if (newValueObject != valueObject) {
			NotificationChain msgs = null;
			if (valueObject != null)
				msgs = ((InternalEObject)valueObject).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - bpmnPackage.INTEGER_VARIABLE__VALUE_OBJECT, null, msgs);
			if (newValueObject != null)
				msgs = ((InternalEObject)newValueObject).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - bpmnPackage.INTEGER_VARIABLE__VALUE_OBJECT, null, msgs);
			msgs = basicSetValueObject(newValueObject, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.INTEGER_VARIABLE__VALUE_OBJECT, newValueObject, newValueObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case bpmnPackage.INTEGER_VARIABLE__VALUE_OBJECT:
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
			case bpmnPackage.INTEGER_VARIABLE__VALUE_OBJECT:
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
			case bpmnPackage.INTEGER_VARIABLE__VALUE_OBJECT:
				setValueObject((IntegerValue)newValue);
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
			case bpmnPackage.INTEGER_VARIABLE__VALUE_OBJECT:
				setValueObject((IntegerValue)null);
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
			case bpmnPackage.INTEGER_VARIABLE__VALUE_OBJECT:
				return valueObject != null;
		}
		return super.eIsSet(featureID);
	}

} //IntegerVariableImpl
