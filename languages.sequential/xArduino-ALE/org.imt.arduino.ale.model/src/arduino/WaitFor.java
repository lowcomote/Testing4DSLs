/**
 */
package arduino;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Wait For</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link arduino.WaitFor#getModule <em>Module</em>}</li>
 *   <li>{@link arduino.WaitFor#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see arduino.ArduinoPackage#getWaitFor()
 * @model
 * @generated
 */
public interface WaitFor extends Instruction {
	/**
	 * Returns the value of the '<em><b>Module</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module</em>' reference.
	 * @see #setModule(arduino.Module)
	 * @see arduino.ArduinoPackage#getWaitFor_Module()
	 * @model
	 * @generated
	 */
	arduino.Module getModule();

	/**
	 * Sets the value of the '{@link arduino.WaitFor#getModule <em>Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module</em>' reference.
	 * @see #getModule()
	 * @generated
	 */
	void setModule(arduino.Module value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(Constant)
	 * @see arduino.ArduinoPackage#getWaitFor_Value()
	 * @model containment="true"
	 * @generated
	 */
	Constant getValue();

	/**
	 * Sets the value of the '{@link arduino.WaitFor#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Constant value);

} // WaitFor
