/**
 */
package org.imt.minijava.xminiJava;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>For Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.minijava.xminiJava.ForStatement#getDeclaration <em>Declaration</em>}</li>
 *   <li>{@link org.imt.minijava.xminiJava.ForStatement#getCondition <em>Condition</em>}</li>
 *   <li>{@link org.imt.minijava.xminiJava.ForStatement#getProgression <em>Progression</em>}</li>
 *   <li>{@link org.imt.minijava.xminiJava.ForStatement#getBlock <em>Block</em>}</li>
 * </ul>
 *
 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getForStatement()
 * @model
 * @generated
 */
public interface ForStatement extends Statement {
	/**
	 * Returns the value of the '<em><b>Declaration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declaration</em>' containment reference.
	 * @see #setDeclaration(Assignment)
	 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getForStatement_Declaration()
	 * @model containment="true"
	 * @generated
	 */
	Assignment getDeclaration();

	/**
	 * Sets the value of the '{@link org.imt.minijava.xminiJava.ForStatement#getDeclaration <em>Declaration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declaration</em>' containment reference.
	 * @see #getDeclaration()
	 * @generated
	 */
	void setDeclaration(Assignment value);

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(Expression)
	 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getForStatement_Condition()
	 * @model containment="true"
	 * @generated
	 */
	Expression getCondition();

	/**
	 * Sets the value of the '{@link org.imt.minijava.xminiJava.ForStatement#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(Expression value);

	/**
	 * Returns the value of the '<em><b>Progression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Progression</em>' containment reference.
	 * @see #setProgression(Assignment)
	 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getForStatement_Progression()
	 * @model containment="true"
	 * @generated
	 */
	Assignment getProgression();

	/**
	 * Sets the value of the '{@link org.imt.minijava.xminiJava.ForStatement#getProgression <em>Progression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Progression</em>' containment reference.
	 * @see #getProgression()
	 * @generated
	 */
	void setProgression(Assignment value);

	/**
	 * Returns the value of the '<em><b>Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Block</em>' containment reference.
	 * @see #setBlock(Block)
	 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getForStatement_Block()
	 * @model containment="true"
	 * @generated
	 */
	Block getBlock();

	/**
	 * Sets the value of the '{@link org.imt.minijava.xminiJava.ForStatement#getBlock <em>Block</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Block</em>' containment reference.
	 * @see #getBlock()
	 * @generated
	 */
	void setBlock(Block value);

} // ForStatement
