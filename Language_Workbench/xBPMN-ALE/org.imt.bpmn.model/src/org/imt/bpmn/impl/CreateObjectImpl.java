/**
 */
package org.imt.bpmn.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.imt.bpmn.CreateObject;
import org.imt.bpmn.Entity;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Create Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.impl.CreateObjectImpl#getNewEntity <em>New Entity</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CreateObjectImpl extends ObjectActivityImpl implements CreateObject {
	/**
	 * The cached value of the '{@link #getNewEntity() <em>New Entity</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewEntity()
	 * @generated
	 * @ordered
	 */
	protected Entity newEntity;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CreateObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return bpmnPackage.Literals.CREATE_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity getNewEntity() {
		return newEntity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNewEntity(Entity newNewEntity, NotificationChain msgs) {
		Entity oldNewEntity = newEntity;
		newEntity = newNewEntity;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, bpmnPackage.CREATE_OBJECT__NEW_ENTITY, oldNewEntity, newNewEntity);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewEntity(Entity newNewEntity) {
		if (newNewEntity != newEntity) {
			NotificationChain msgs = null;
			if (newEntity != null)
				msgs = ((InternalEObject)newEntity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - bpmnPackage.CREATE_OBJECT__NEW_ENTITY, null, msgs);
			if (newNewEntity != null)
				msgs = ((InternalEObject)newNewEntity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - bpmnPackage.CREATE_OBJECT__NEW_ENTITY, null, msgs);
			msgs = basicSetNewEntity(newNewEntity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.CREATE_OBJECT__NEW_ENTITY, newNewEntity, newNewEntity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case bpmnPackage.CREATE_OBJECT__NEW_ENTITY:
				return basicSetNewEntity(null, msgs);
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
			case bpmnPackage.CREATE_OBJECT__NEW_ENTITY:
				return getNewEntity();
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
			case bpmnPackage.CREATE_OBJECT__NEW_ENTITY:
				setNewEntity((Entity)newValue);
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
			case bpmnPackage.CREATE_OBJECT__NEW_ENTITY:
				setNewEntity((Entity)null);
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
			case bpmnPackage.CREATE_OBJECT__NEW_ENTITY:
				return newEntity != null;
		}
		return super.eIsSet(featureID);
	}

} //CreateObjectImpl
