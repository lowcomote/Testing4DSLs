/**
 */
package org.imt.bpmn.impl;

import org.eclipse.emf.ecore.EClass;

import org.imt.bpmn.Activity;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class ActivityImpl extends MicroflowElementImpl implements Activity {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActivityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return bpmnPackage.Literals.ACTIVITY;
	}

} //ActivityImpl
