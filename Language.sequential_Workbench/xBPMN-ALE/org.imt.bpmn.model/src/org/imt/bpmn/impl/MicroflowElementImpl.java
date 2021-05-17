/**
 */
package org.imt.bpmn.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.imt.bpmn.Microflow;
import org.imt.bpmn.MicroflowElement;
import org.imt.bpmn.SequenceFlow;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Microflow Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.impl.MicroflowElementImpl#getIncomingFlows <em>Incoming Flows</em>}</li>
 *   <li>{@link org.imt.bpmn.impl.MicroflowElementImpl#getOutgoingFlows <em>Outgoing Flows</em>}</li>
 *   <li>{@link org.imt.bpmn.impl.MicroflowElementImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.imt.bpmn.impl.MicroflowElementImpl#getOwnerMicroflow <em>Owner Microflow</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MicroflowElementImpl extends MinimalEObjectImpl.Container implements MicroflowElement {
	/**
	 * The cached value of the '{@link #getIncomingFlows() <em>Incoming Flows</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncomingFlows()
	 * @generated
	 * @ordered
	 */
	protected EList<SequenceFlow> incomingFlows;

	/**
	 * The cached value of the '{@link #getOutgoingFlows() <em>Outgoing Flows</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoingFlows()
	 * @generated
	 * @ordered
	 */
	protected EList<SequenceFlow> outgoingFlows;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MicroflowElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return bpmnPackage.Literals.MICROFLOW_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SequenceFlow> getIncomingFlows() {
		if (incomingFlows == null) {
			incomingFlows = new EObjectWithInverseResolvingEList<SequenceFlow>(SequenceFlow.class, this, bpmnPackage.MICROFLOW_ELEMENT__INCOMING_FLOWS, bpmnPackage.SEQUENCE_FLOW__TARGET);
		}
		return incomingFlows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SequenceFlow> getOutgoingFlows() {
		if (outgoingFlows == null) {
			outgoingFlows = new EObjectContainmentWithInverseEList<SequenceFlow>(SequenceFlow.class, this, bpmnPackage.MICROFLOW_ELEMENT__OUTGOING_FLOWS, bpmnPackage.SEQUENCE_FLOW__SOURCE);
		}
		return outgoingFlows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.MICROFLOW_ELEMENT__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Microflow getOwnerMicroflow() {
		if (eContainerFeatureID() != bpmnPackage.MICROFLOW_ELEMENT__OWNER_MICROFLOW) return null;
		return (Microflow)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwnerMicroflow(Microflow newOwnerMicroflow, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwnerMicroflow, bpmnPackage.MICROFLOW_ELEMENT__OWNER_MICROFLOW, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerMicroflow(Microflow newOwnerMicroflow) {
		if (newOwnerMicroflow != eInternalContainer() || (eContainerFeatureID() != bpmnPackage.MICROFLOW_ELEMENT__OWNER_MICROFLOW && newOwnerMicroflow != null)) {
			if (EcoreUtil.isAncestor(this, newOwnerMicroflow))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwnerMicroflow != null)
				msgs = ((InternalEObject)newOwnerMicroflow).eInverseAdd(this, bpmnPackage.MICROFLOW__OWNED_ELEMENTS, Microflow.class, msgs);
			msgs = basicSetOwnerMicroflow(newOwnerMicroflow, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.MICROFLOW_ELEMENT__OWNER_MICROFLOW, newOwnerMicroflow, newOwnerMicroflow));
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
			case bpmnPackage.MICROFLOW_ELEMENT__INCOMING_FLOWS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncomingFlows()).basicAdd(otherEnd, msgs);
			case bpmnPackage.MICROFLOW_ELEMENT__OUTGOING_FLOWS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoingFlows()).basicAdd(otherEnd, msgs);
			case bpmnPackage.MICROFLOW_ELEMENT__OWNER_MICROFLOW:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOwnerMicroflow((Microflow)otherEnd, msgs);
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
			case bpmnPackage.MICROFLOW_ELEMENT__INCOMING_FLOWS:
				return ((InternalEList<?>)getIncomingFlows()).basicRemove(otherEnd, msgs);
			case bpmnPackage.MICROFLOW_ELEMENT__OUTGOING_FLOWS:
				return ((InternalEList<?>)getOutgoingFlows()).basicRemove(otherEnd, msgs);
			case bpmnPackage.MICROFLOW_ELEMENT__OWNER_MICROFLOW:
				return basicSetOwnerMicroflow(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case bpmnPackage.MICROFLOW_ELEMENT__OWNER_MICROFLOW:
				return eInternalContainer().eInverseRemove(this, bpmnPackage.MICROFLOW__OWNED_ELEMENTS, Microflow.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case bpmnPackage.MICROFLOW_ELEMENT__INCOMING_FLOWS:
				return getIncomingFlows();
			case bpmnPackage.MICROFLOW_ELEMENT__OUTGOING_FLOWS:
				return getOutgoingFlows();
			case bpmnPackage.MICROFLOW_ELEMENT__LABEL:
				return getLabel();
			case bpmnPackage.MICROFLOW_ELEMENT__OWNER_MICROFLOW:
				return getOwnerMicroflow();
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
			case bpmnPackage.MICROFLOW_ELEMENT__INCOMING_FLOWS:
				getIncomingFlows().clear();
				getIncomingFlows().addAll((Collection<? extends SequenceFlow>)newValue);
				return;
			case bpmnPackage.MICROFLOW_ELEMENT__OUTGOING_FLOWS:
				getOutgoingFlows().clear();
				getOutgoingFlows().addAll((Collection<? extends SequenceFlow>)newValue);
				return;
			case bpmnPackage.MICROFLOW_ELEMENT__LABEL:
				setLabel((String)newValue);
				return;
			case bpmnPackage.MICROFLOW_ELEMENT__OWNER_MICROFLOW:
				setOwnerMicroflow((Microflow)newValue);
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
			case bpmnPackage.MICROFLOW_ELEMENT__INCOMING_FLOWS:
				getIncomingFlows().clear();
				return;
			case bpmnPackage.MICROFLOW_ELEMENT__OUTGOING_FLOWS:
				getOutgoingFlows().clear();
				return;
			case bpmnPackage.MICROFLOW_ELEMENT__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case bpmnPackage.MICROFLOW_ELEMENT__OWNER_MICROFLOW:
				setOwnerMicroflow((Microflow)null);
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
			case bpmnPackage.MICROFLOW_ELEMENT__INCOMING_FLOWS:
				return incomingFlows != null && !incomingFlows.isEmpty();
			case bpmnPackage.MICROFLOW_ELEMENT__OUTGOING_FLOWS:
				return outgoingFlows != null && !outgoingFlows.isEmpty();
			case bpmnPackage.MICROFLOW_ELEMENT__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case bpmnPackage.MICROFLOW_ELEMENT__OWNER_MICROFLOW:
				return getOwnerMicroflow() != null;
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
		result.append(" (label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}

} //MicroflowElementImpl
