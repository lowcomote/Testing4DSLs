/**
 */
package org.imt.arduino.nonreactive.arduino;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unary Boolean Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.arduino.nonreactive.arduino.UnaryBooleanExpression#getOperator <em>Operator</em>}</li>
 * </ul>
 *
 * @see org.imt.arduino.nonreactive.arduino.ArduinoPackage#getUnaryBooleanExpression()
 * @model
 * @generated
 */
public interface UnaryBooleanExpression extends UnaryExpression, BooleanExpression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.imt.arduino.nonreactive.arduino.UnaryBooleanOperatorKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.imt.arduino.nonreactive.arduino.UnaryBooleanOperatorKind
	 * @see #setOperator(UnaryBooleanOperatorKind)
	 * @see org.imt.arduino.nonreactive.arduino.ArduinoPackage#getUnaryBooleanExpression_Operator()
	 * @model
	 * @generated
	 */
	UnaryBooleanOperatorKind getOperator();

	/**
	 * Sets the value of the '{@link org.imt.arduino.nonreactive.arduino.UnaryBooleanExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.imt.arduino.nonreactive.arduino.UnaryBooleanOperatorKind
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(UnaryBooleanOperatorKind value);

} // UnaryBooleanExpression
