/**
 */
package org.imt.arduino.nonreactive.arduino;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unary Integer Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.arduino.nonreactive.arduino.UnaryIntegerExpression#getOperator <em>Operator</em>}</li>
 * </ul>
 *
 * @see org.imt.arduino.nonreactive.arduino.ArduinoPackage#getUnaryIntegerExpression()
 * @model
 * @generated
 */
public interface UnaryIntegerExpression extends UnaryExpression, IntegerExpression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.imt.arduino.nonreactive.arduino.UnaryIntegerOperatorKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.imt.arduino.nonreactive.arduino.UnaryIntegerOperatorKind
	 * @see #setOperator(UnaryIntegerOperatorKind)
	 * @see org.imt.arduino.nonreactive.arduino.ArduinoPackage#getUnaryIntegerExpression_Operator()
	 * @model
	 * @generated
	 */
	UnaryIntegerOperatorKind getOperator();

	/**
	 * Sets the value of the '{@link org.imt.arduino.nonreactive.arduino.UnaryIntegerExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.imt.arduino.nonreactive.arduino.UnaryIntegerOperatorKind
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(UnaryIntegerOperatorKind value);

} // UnaryIntegerExpression
