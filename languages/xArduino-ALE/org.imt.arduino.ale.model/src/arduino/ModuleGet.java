/**
 */
package arduino;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Module Get</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link arduino.ModuleGet#getModule <em>Module</em>}</li>
 * </ul>
 *
 * @see arduino.ArduinoPackage#getModuleGet()
 * @model
 * @generated
 */
public interface ModuleGet extends Expression {
	/**
	 * Returns the value of the '<em><b>Module</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module</em>' reference.
	 * @see #setModule(arduino.Module)
	 * @see arduino.ArduinoPackage#getModuleGet_Module()
	 * @model required="true"
	 * @generated
	 */
	arduino.Module getModule();

	/**
	 * Sets the value of the '{@link arduino.ModuleGet#getModule <em>Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module</em>' reference.
	 * @see #getModule()
	 * @generated
	 */
	void setModule(arduino.Module value);

} // ModuleGet
