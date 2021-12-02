/**
 */
package org.imt.minijava.xminiJava;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Print Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.minijava.xminiJava.PrintStatement#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getPrintStatement()
 * @model
 * @generated
 */
public interface PrintStatement extends Statement {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getPrintStatement_Expression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link org.imt.minijava.xminiJava.PrintStatement#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

} // PrintStatement
