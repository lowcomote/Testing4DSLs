/**
 */
package org.imt.arduino;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Module Get</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.arduino.ModuleGet#getModule <em>Module</em>}</li>
 * </ul>
 *
 * @see org.imt.arduino.ArduinoPackage#getModuleGet()
 * @model abstract="true"
 * @generated
 */
public interface ModuleGet extends Expression {
	/**
	 * Returns the value of the '<em><b>Module</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module</em>' reference.
	 * @see #setModule(org.imt.arduino.Module)
	 * @see org.imt.arduino.ArduinoPackage#getModuleGet_Module()
	 * @model required="true"
	 * @generated
	 */
	org.imt.arduino.Module getModule();

	/**
	 * Sets the value of the '{@link org.imt.arduino.ModuleGet#getModule <em>Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module</em>' reference.
	 * @see #getModule()
	 * @generated
	 */
	void setModule(org.imt.arduino.Module value);

} // ModuleGet
