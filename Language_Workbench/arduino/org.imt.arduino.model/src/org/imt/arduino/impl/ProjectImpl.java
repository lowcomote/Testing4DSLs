/**
 */
package org.imt.arduino.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.imt.arduino.Board;
import org.imt.arduino.Project;
import org.imt.arduino.Sketch;
import org.imt.arduino.arduinoPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.imt.arduino.impl.ProjectImpl#getBoard <em>Board</em>}</li>
 *   <li>{@link org.imt.arduino.impl.ProjectImpl#getSketch <em>Sketch</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectImpl extends NamedElementImpl implements Project {
	/**
	 * The cached value of the '{@link #getBoard() <em>Board</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBoard()
	 * @generated
	 * @ordered
	 */
	protected Board board;

	/**
	 * The cached value of the '{@link #getSketch() <em>Sketch</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSketch()
	 * @generated
	 * @ordered
	 */
	protected Sketch sketch;

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
		return arduinoPackage.Literals.PROJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBoard(Board newBoard, NotificationChain msgs) {
		Board oldBoard = board;
		board = newBoard;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, arduinoPackage.PROJECT__BOARD, oldBoard, newBoard);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBoard(Board newBoard) {
		if (newBoard != board) {
			NotificationChain msgs = null;
			if (board != null)
				msgs = ((InternalEObject)board).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - arduinoPackage.PROJECT__BOARD, null, msgs);
			if (newBoard != null)
				msgs = ((InternalEObject)newBoard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - arduinoPackage.PROJECT__BOARD, null, msgs);
			msgs = basicSetBoard(newBoard, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, arduinoPackage.PROJECT__BOARD, newBoard, newBoard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sketch getSketch() {
		return sketch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSketch(Sketch newSketch, NotificationChain msgs) {
		Sketch oldSketch = sketch;
		sketch = newSketch;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, arduinoPackage.PROJECT__SKETCH, oldSketch, newSketch);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSketch(Sketch newSketch) {
		if (newSketch != sketch) {
			NotificationChain msgs = null;
			if (sketch != null)
				msgs = ((InternalEObject)sketch).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - arduinoPackage.PROJECT__SKETCH, null, msgs);
			if (newSketch != null)
				msgs = ((InternalEObject)newSketch).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - arduinoPackage.PROJECT__SKETCH, null, msgs);
			msgs = basicSetSketch(newSketch, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, arduinoPackage.PROJECT__SKETCH, newSketch, newSketch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case arduinoPackage.PROJECT__BOARD:
				return basicSetBoard(null, msgs);
			case arduinoPackage.PROJECT__SKETCH:
				return basicSetSketch(null, msgs);
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
			case arduinoPackage.PROJECT__BOARD:
				return getBoard();
			case arduinoPackage.PROJECT__SKETCH:
				return getSketch();
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
			case arduinoPackage.PROJECT__BOARD:
				setBoard((Board)newValue);
				return;
			case arduinoPackage.PROJECT__SKETCH:
				setSketch((Sketch)newValue);
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
			case arduinoPackage.PROJECT__BOARD:
				setBoard((Board)null);
				return;
			case arduinoPackage.PROJECT__SKETCH:
				setSketch((Sketch)null);
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
			case arduinoPackage.PROJECT__BOARD:
				return board != null;
			case arduinoPackage.PROJECT__SKETCH:
				return sketch != null;
		}
		return super.eIsSet(featureID);
	}

} //ProjectImpl
