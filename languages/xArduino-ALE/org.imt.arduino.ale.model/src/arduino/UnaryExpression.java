/**
 */
package arduino;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unary Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link arduino.UnaryExpression#getOperator <em>Operator</em>}</li>
 *   <li>{@link arduino.UnaryExpression#getOperand <em>Operand</em>}</li>
 * </ul>
 *
 * @see arduino.ArduinoPackage#getUnaryExpression()
 * @model
 * @generated
 */
public interface UnaryExpression extends Expression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link arduino.UnaryOperatorKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see arduino.UnaryOperatorKind
	 * @see #setOperator(UnaryOperatorKind)
	 * @see arduino.ArduinoPackage#getUnaryExpression_Operator()
	 * @model required="true"
	 * @generated
	 */
	UnaryOperatorKind getOperator();

	/**
	 * Sets the value of the '{@link arduino.UnaryExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see arduino.UnaryOperatorKind
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
	 * @see arduino.ArduinoPackage#getUnaryExpression_Operand()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getOperand();

	/**
	 * Sets the value of the '{@link arduino.UnaryExpression#getOperand <em>Operand</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operand</em>' containment reference.
	 * @see #getOperand()
	 * @generated
	 */
	void setOperand(Expression value);

} // UnaryExpression
