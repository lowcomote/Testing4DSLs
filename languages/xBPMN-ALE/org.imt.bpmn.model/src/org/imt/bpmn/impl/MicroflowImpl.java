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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.imt.bpmn.Microflow;
import org.imt.bpmn.MicroflowElement;
import org.imt.bpmn.Variable;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Microflow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.impl.MicroflowImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.imt.bpmn.impl.MicroflowImpl#getOwnedElements <em>Owned Elements</em>}</li>
 *   <li>{@link org.imt.bpmn.impl.MicroflowImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.imt.bpmn.impl.MicroflowImpl#getCurrentNode <em>Current Node</em>}</li>
 *   <li>{@link org.imt.bpmn.impl.MicroflowImpl#getValuedVariables <em>Valued Variables</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MicroflowImpl extends MinimalEObjectImpl.Container implements Microflow {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOwnedElements() <em>Owned Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedElements()
	 * @generated
	 * @ordered
	 */
	protected EList<MicroflowElement> ownedElements;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> parameters;

	/**
	 * The cached value of the '{@link #getCurrentNode() <em>Current Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentNode()
	 * @generated
	 * @ordered
	 */
	protected MicroflowElement currentNode;

	/**
	 * The cached value of the '{@link #getValuedVariables() <em>Valued Variables</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValuedVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> valuedVariables;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MicroflowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return bpmnPackage.Literals.MICROFLOW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.MICROFLOW__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MicroflowElement> getOwnedElements() {
		if (ownedElements == null) {
			ownedElements = new EObjectContainmentWithInverseEList<MicroflowElement>(MicroflowElement.class, this, bpmnPackage.MICROFLOW__OWNED_ELEMENTS, bpmnPackage.MICROFLOW_ELEMENT__OWNER_MICROFLOW);
		}
		return ownedElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Variable> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Variable>(Variable.class, this, bpmnPackage.MICROFLOW__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MicroflowElement getCurrentNode() {
		if (currentNode != null && currentNode.eIsProxy()) {
			InternalEObject oldCurrentNode = (InternalEObject)currentNode;
			currentNode = (MicroflowElement)eResolveProxy(oldCurrentNode);
			if (currentNode != oldCurrentNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, bpmnPackage.MICROFLOW__CURRENT_NODE, oldCurrentNode, currentNode));
			}
		}
		return currentNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MicroflowElement basicGetCurrentNode() {
		return currentNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentNode(MicroflowElement newCurrentNode) {
		MicroflowElement oldCurrentNode = currentNode;
		currentNode = newCurrentNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.MICROFLOW__CURRENT_NODE, oldCurrentNode, currentNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Variable> getValuedVariables() {
		if (valuedVariables == null) {
			valuedVariables = new EObjectResolvingEList<Variable>(Variable.class, this, bpmnPackage.MICROFLOW__VALUED_VARIABLES);
		}
		return valuedVariables;
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
			case bpmnPackage.MICROFLOW__OWNED_ELEMENTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOwnedElements()).basicAdd(otherEnd, msgs);
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
			case bpmnPackage.MICROFLOW__OWNED_ELEMENTS:
				return ((InternalEList<?>)getOwnedElements()).basicRemove(otherEnd, msgs);
			case bpmnPackage.MICROFLOW__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
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
			case bpmnPackage.MICROFLOW__NAME:
				return getName();
			case bpmnPackage.MICROFLOW__OWNED_ELEMENTS:
				return getOwnedElements();
			case bpmnPackage.MICROFLOW__PARAMETERS:
				return getParameters();
			case bpmnPackage.MICROFLOW__CURRENT_NODE:
				if (resolve) return getCurrentNode();
				return basicGetCurrentNode();
			case bpmnPackage.MICROFLOW__VALUED_VARIABLES:
				return getValuedVariables();
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
			case bpmnPackage.MICROFLOW__NAME:
				setName((String)newValue);
				return;
			case bpmnPackage.MICROFLOW__OWNED_ELEMENTS:
				getOwnedElements().clear();
				getOwnedElements().addAll((Collection<? extends MicroflowElement>)newValue);
				return;
			case bpmnPackage.MICROFLOW__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Variable>)newValue);
				return;
			case bpmnPackage.MICROFLOW__CURRENT_NODE:
				setCurrentNode((MicroflowElement)newValue);
				return;
			case bpmnPackage.MICROFLOW__VALUED_VARIABLES:
				getValuedVariables().clear();
				getValuedVariables().addAll((Collection<? extends Variable>)newValue);
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
			case bpmnPackage.MICROFLOW__NAME:
				setName(NAME_EDEFAULT);
				return;
			case bpmnPackage.MICROFLOW__OWNED_ELEMENTS:
				getOwnedElements().clear();
				return;
			case bpmnPackage.MICROFLOW__PARAMETERS:
				getParameters().clear();
				return;
			case bpmnPackage.MICROFLOW__CURRENT_NODE:
				setCurrentNode((MicroflowElement)null);
				return;
			case bpmnPackage.MICROFLOW__VALUED_VARIABLES:
				getValuedVariables().clear();
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
			case bpmnPackage.MICROFLOW__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case bpmnPackage.MICROFLOW__OWNED_ELEMENTS:
				return ownedElements != null && !ownedElements.isEmpty();
			case bpmnPackage.MICROFLOW__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case bpmnPackage.MICROFLOW__CURRENT_NODE:
				return currentNode != null;
			case bpmnPackage.MICROFLOW__VALUED_VARIABLES:
				return valuedVariables != null && !valuedVariables.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //MicroflowImpl
