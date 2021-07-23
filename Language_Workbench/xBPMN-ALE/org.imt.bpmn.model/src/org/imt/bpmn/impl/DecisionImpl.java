/**
 */
package org.imt.bpmn.impl;

import org.eclipse.emf.ecore.EClass;

import org.imt.bpmn.Decision;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Decision</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class DecisionImpl extends MicroflowElementImpl implements Decision {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DecisionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return bpmnPackage.Literals.DECISION;
	}

} //DecisionImpl
