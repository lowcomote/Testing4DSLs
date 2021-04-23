/**
 */
package org.imt.bpmn.impl;

import org.eclipse.emf.ecore.EClass;

import org.imt.bpmn.VariableActivity;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class VariableActivityImpl extends ActivityImpl implements VariableActivity {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableActivityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return bpmnPackage.Literals.VARIABLE_ACTIVITY;
	}

} //VariableActivityImpl
