/**
 */
package arduino;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Set Led</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link arduino.SetLed#getLed <em>Led</em>}</li>
 * </ul>
 *
 * @see arduino.ArduinoPackage#getSetLed()
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
	 * @see arduino.ArduinoPackage#getSetLed_Led()
	 * @model required="true"
	 * @generated
	 */
	Led getLed();

	/**
	 * Sets the value of the '{@link arduino.SetLed#getLed <em>Led</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Led</em>' reference.
	 * @see #getLed()
	 * @generated
	 */
	void setLed(Led value);

} // SetLed
