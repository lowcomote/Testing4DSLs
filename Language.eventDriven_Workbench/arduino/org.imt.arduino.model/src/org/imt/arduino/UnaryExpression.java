/**
 */
package org.imt.arduino;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unary Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.arduino.UnaryExpression#getOperator <em>Operator</em>}</li>
 *   <li>{@link org.imt.arduino.UnaryExpression#getOperand <em>Operand</em>}</li>
 * </ul>
 *
 * @see org.imt.arduino.arduinoPackage#getUnaryExpression()
 * @model
 * @generated
 */
public interface UnaryExpression extends Expression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.imt.arduino.UnaryOperatorKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.imt.arduino.UnaryOperatorKind
	 * @see #setOperator(UnaryOperatorKind)
	 * @see org.imt.arduino.arduinoPackage#getUnaryExpression_Operator()
	 * @model required="true"
	 * @generated
	 */
	UnaryOperatorKind getOperator();

	/**
	 * Sets the value of the '{@link org.imt.arduino.UnaryExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.imt.arduino.UnaryOperatorKind
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(UnaryOperatorKind value);

	/**
	 * Returns the value of the '<em><b>Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operand</em>' containment reference.
	 * @see #setOperand(Expression)
	 * @see org.imt.arduino.arduinoPackage#getUnaryExpression_Operand()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getOperand();

	/**
	 * Sets the value of the '{@link org.imt.arduino.UnaryExpression#getOperand <em>Operand</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operand</em>' containment reference.
	 * @see #getOperand()
	 * @generated
	 */
	void setOperand(Expression value);

} // UnaryExpression
