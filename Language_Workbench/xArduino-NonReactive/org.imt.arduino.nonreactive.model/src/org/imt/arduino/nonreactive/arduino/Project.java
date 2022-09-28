/**
 */
package org.imt.arduino.nonreactive.arduino;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.arduino.nonreactive.arduino.Project#getBoards <em>Boards</em>}</li>
 *   <li>{@link org.imt.arduino.nonreactive.arduino.Project#getSketches <em>Sketches</em>}</li>
 *   <li>{@link org.imt.arduino.nonreactive.arduino.Project#getPinChanges <em>Pin Changes</em>}</li>
 * </ul>
 *
 * @see org.imt.arduino.nonreactive.arduino.ArduinoPackage#getProject()
 * @model
 * @generated
 */
public interface Project extends EObject {
	/**
	 * Returns the value of the '<em><b>Boards</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.arduino.nonreactive.arduino.Board}.
	 * It is bidirectional and its opposite is '{@link org.imt.arduino.nonreactive.arduino.Board#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Boards</em>' containment reference list.
	 * @see org.imt.arduino.nonreactive.arduino.ArduinoPackage#getProject_Boards()
	 * @see org.imt.arduino.nonreactive.arduino.Board#getProject
	 * @model opposite="project" containment="true"
	 * @generated
	 */
	EList<Board> getBoards();

	/**
	 * Returns the value of the '<em><b>Sketches</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.arduino.nonreactive.arduino.Sketch}.
	 * It is bidirectional and its opposite is '{@link org.imt.arduino.nonreactive.arduino.Sketch#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sketches</em>' containment reference list.
	 * @see org.imt.arduino.nonreactive.arduino.ArduinoPackage#getProject_Sketches()
	 * @see org.imt.arduino.nonreactive.arduino.Sketch#getProject
	 * @model opposite="project" containment="true"
	 * @generated
	 */
	EList<Sketch> getSketches();

	/**
	 * Returns the value of the '<em><b>Pin Changes</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.arduino.nonreactive.arduino.Pin}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pin Changes</em>' containment reference list.
	 * @see org.imt.arduino.nonreactive.arduino.ArduinoPackage#getProject_PinChanges()
	 * @model containment="true"
	 *        annotation="aspect"
	 * @generated
	 */
	EList<Pin> getPinChanges();

} // Project
