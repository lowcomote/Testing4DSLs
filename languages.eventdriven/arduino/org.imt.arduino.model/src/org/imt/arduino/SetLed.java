/**
 */
package org.imt.arduino;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Set Led</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.arduino.SetLed#getLed <em>Led</em>}</li>
 * </ul>
 *
 * @see org.imt.arduino.arduinoPackage#getSetLed()
 * @model
 * @generated
 */
public interface SetLed extends ModuleSet {
	/**
	 * Returns the value of the '<em><b>Led</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Led</em>' reference.
	 * @see #setLed(Led)
	 * @see org.imt.arduino.arduinoPackage#getSetLed_Led()
	 * @model required="true"
	 * @generated
	 */
	Led getLed();

	/**
	 * Sets the value of the '{@link org.imt.arduino.SetLed#getLed <em>Led</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Led</em>' reference.
	 * @see #getLed()
	 * @generated
	 */
	void setLed(Led value);

} // SetLed
