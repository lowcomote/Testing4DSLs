/**
 */
package org.imt.bpmn;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Integer Comparison Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.IntegerComparisonExpression#getOperator <em>Operator</em>}</li>
 *   <li>{@link org.imt.bpmn.IntegerComparisonExpression#getOperand1 <em>Operand1</em>}</li>
 *   <li>{@link org.imt.bpmn.IntegerComparisonExpression#getOperand2 <em>Operand2</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getIntegerComparisonExpression()
 * @model
 * @generated
 */
public interface IntegerComparisonExpression extends Expression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.imt.bpmn.IntegerComparisonOperator}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.imt.bpmn.IntegerComparisonOperator
	 * @see #setOperator(IntegerComparisonOperator)
	 * @see org.imt.bpmn.bpmnPackage#getIntegerComparisonExpression_Operator()
	 * @model required="true"
	 * @generated
	 */
	IntegerComparisonOperator getOperator();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.IntegerComparisonExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.imt.bpmn.IntegerComparisonOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(IntegerComparisonOperator value);

	/**
	 * Returns the value of the '<em><b>Operand1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operand1</em>' reference.
	 * @see #setOperand1(IntegerVariable)
	 * @see org.imt.bpmn.bpmnPackage#getIntegerComparisonExpression_Operand1()
	 * @model required="true"
	 * @generated
	 */
	IntegerVariable getOperand1();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.IntegerComparisonExpression#getOperand1 <em>Operand1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operand1</em>' reference.
	 * @see #getOperand1()
	 * @generated
	 */
	void setOperand1(IntegerVariable value);

	/**
	 * Returns the value of the '<em><b>Operand2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operand2</em>' reference.
	 * @see #setOperand2(IntegerVariable)
	 * @see org.imt.bpmn.bpmnPackage#getIntegerComparisonExpression_Operand2()
	 * @model required="true"
	 * @generated
	 */
	IntegerVariable getOperand2();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.IntegerComparisonExpression#getOperand2 <em>Operand2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operand2</em>' reference.
	 * @see #getOperand2()
	 * @generated
	 */
	void setOperand2(IntegerVariable value);

} // IntegerComparisonExpression
