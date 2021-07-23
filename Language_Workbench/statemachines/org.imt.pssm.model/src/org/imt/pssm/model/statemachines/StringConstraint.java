/**
 */
package org.imt.pssm.model.statemachines;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>String Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.pssm.model.statemachines.StringConstraint#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.imt.pssm.model.statemachines.StringConstraint#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.imt.pssm.model.statemachines.StatemachinesPackage#getStringConstraint()
 * @model
 * @generated
 */
public interface StringConstraint extends Constraint {
	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(StringAttribute)
	 * @see org.imt.pssm.model.statemachines.StatemachinesPackage#getStringConstraint_Attribute()
	 * @model
	 * @generated
	 */
	StringAttribute getAttribute();

	/**
	 * Sets the value of the '{@link org.imt.pssm.model.statemachines.StringConstraint#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(StringAttribute value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.imt.pssm.model.statemachines.StatemachinesPackage#getStringConstraint_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.imt.pssm.model.statemachines.StringConstraint#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // StringConstraint
