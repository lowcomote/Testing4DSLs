/**
 */
package org.imt.arduino.nonreactive.arduino;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Board</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.arduino.nonreactive.arduino.Board#getProject <em>Project</em>}</li>
 * </ul>
 *
 * @see org.imt.arduino.nonreactive.arduino.ArduinoPackage#getBoard()
 * @model abstract="true"
 * @generated
 */
public interface Board extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Project</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.imt.arduino.nonreactive.arduino.Project#getBoards <em>Boards</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project</em>' container reference.
	 * @see #setProject(Project)
	 * @see org.imt.arduino.nonreactive.arduino.ArduinoPackage#getBoard_Project()
	 * @see org.imt.arduino.nonreactive.arduino.Project#getBoards
	 * @model opposite="boards" required="true" transient="false"
	 * @generated
	 */
	Project getProject();

	/**
	 * Sets the value of the '{@link org.imt.arduino.nonreactive.arduino.Board#getProject <em>Project</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' container reference.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(Project value);

} // Board
