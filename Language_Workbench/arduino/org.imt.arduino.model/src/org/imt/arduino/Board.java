/**
 */
package org.imt.arduino;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Board</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.arduino.Board#getModules <em>Modules</em>}</li>
 * </ul>
 *
 * @see org.imt.arduino.arduinoPackage#getBoard()
 * @model
 * @generated
 */
public interface Board extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Modules</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.arduino.Module}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modules</em>' containment reference list.
	 * @see org.imt.arduino.arduinoPackage#getBoard_Modules()
	 * @model containment="true"
	 * @generated
	 */
	EList<org.imt.arduino.Module> getModules();

} // Board
