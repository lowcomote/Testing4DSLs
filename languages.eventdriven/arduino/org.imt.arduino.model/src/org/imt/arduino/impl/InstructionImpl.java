/**
 */
package org.imt.arduino.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.imt.arduino.Instruction;
import org.imt.arduino.arduinoPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Instruction</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class InstructionImpl extends MinimalEObjectImpl.Container implements Instruction {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstructionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return arduinoPackage.Literals.INSTRUCTION;
	}

} //InstructionImpl
