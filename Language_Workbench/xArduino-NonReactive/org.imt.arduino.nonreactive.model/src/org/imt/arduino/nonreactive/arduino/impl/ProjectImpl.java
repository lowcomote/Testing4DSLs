/**
 */
package org.imt.arduino.nonreactive.arduino.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.imt.arduino.nonreactive.arduino.ArduinoPackage;
import org.imt.arduino.nonreactive.arduino.Board;
import org.imt.arduino.nonreactive.arduino.Pin;
import org.imt.arduino.nonreactive.arduino.Project;
import org.imt.arduino.nonreactive.arduino.Sketch;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.imt.arduino.nonreactive.arduino.impl.ProjectImpl#getBoards <em>Boards</em>}</li>
 *   <li>{@link org.imt.arduino.nonreactive.arduino.impl.ProjectImpl#getSketches <em>Sketches</em>}</li>
 *   <li>{@link org.imt.arduino.nonreactive.arduino.impl.ProjectImpl#getPinChanges <em>Pin Changes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectImpl extends MinimalEObjectImpl.Container implements Project {
	/**
	 * The cached value of the '{@link #getBoards() <em>Boards</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBoards()
	 * @generated
	 * @ordered
	 */
	protected EList<Board> boards;

	/**
	 * The cached value of the '{@link #getSketches() <em>Sketches</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSketches()
	 * @generated
	 * @ordered
	 */
	protected EList<Sketch> sketches;

	/**
	 * The cached value of the '{@link #getPinChanges() <em>Pin Changes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPinChanges()
	 * @generated
	 * @ordered
	 */
	protected EList<Pin> pinChanges;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArduinoPackage.Literals.PROJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Board> getBoards() {
		if (boards == null) {
			boards = new EObjectContainmentWithInverseEList<Board>(Board.class, this, ArduinoPackage.PROJECT__BOARDS, ArduinoPackage.BOARD__PROJECT);
		}
		return boards;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Sketch> getSketches() {
		if (sketches == null) {
			sketches = new EObjectContainmentWithInverseEList<Sketch>(Sketch.class, this, ArduinoPackage.PROJECT__SKETCHES, ArduinoPackage.SKETCH__PROJECT);
		}
		return sketches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Pin> getPinChanges() {
		if (pinChanges == null) {
			pinChanges = new EObjectContainmentEList<Pin>(Pin.class, this, ArduinoPackage.PROJECT__PIN_CHANGES);
		}
		return pinChanges;
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
			case ArduinoPackage.PROJECT__BOARDS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getBoards()).basicAdd(otherEnd, msgs);
			case ArduinoPackage.PROJECT__SKETCHES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSketches()).basicAdd(otherEnd, msgs);
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
			case ArduinoPackage.PROJECT__BOARDS:
				return ((InternalEList<?>)getBoards()).basicRemove(otherEnd, msgs);
			case ArduinoPackage.PROJECT__SKETCHES:
				return ((InternalEList<?>)getSketches()).basicRemove(otherEnd, msgs);
			case ArduinoPackage.PROJECT__PIN_CHANGES:
				return ((InternalEList<?>)getPinChanges()).basicRemove(otherEnd, msgs);
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
			case ArduinoPackage.PROJECT__BOARDS:
				return getBoards();
			case ArduinoPackage.PROJECT__SKETCHES:
				return getSketches();
			case ArduinoPackage.PROJECT__PIN_CHANGES:
				return getPinChanges();
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
			case ArduinoPackage.PROJECT__BOARDS:
				getBoards().clear();
				getBoards().addAll((Collection<? extends Board>)newValue);
				return;
			case ArduinoPackage.PROJECT__SKETCHES:
				getSketches().clear();
				getSketches().addAll((Collection<? extends Sketch>)newValue);
				return;
			case ArduinoPackage.PROJECT__PIN_CHANGES:
				getPinChanges().clear();
				getPinChanges().addAll((Collection<? extends Pin>)newValue);
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
			case ArduinoPackage.PROJECT__BOARDS:
				getBoards().clear();
				return;
			case ArduinoPackage.PROJECT__SKETCHES:
				getSketches().clear();
				return;
			case ArduinoPackage.PROJECT__PIN_CHANGES:
				getPinChanges().clear();
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
			case ArduinoPackage.PROJECT__BOARDS:
				return boards != null && !boards.isEmpty();
			case ArduinoPackage.PROJECT__SKETCHES:
				return sketches != null && !sketches.isEmpty();
			case ArduinoPackage.PROJECT__PIN_CHANGES:
				return pinChanges != null && !pinChanges.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProjectImpl
