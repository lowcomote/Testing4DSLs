/**
 */
package org.imt.minijava.xminiJava;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>New Array</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.minijava.xminiJava.NewArray#getType <em>Type</em>}</li>
 *   <li>{@link org.imt.minijava.xminiJava.NewArray#getSize <em>Size</em>}</li>
 * </ul>
 *
 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getNewArray()
 * @model
 * @generated
 */
public interface NewArray extends Expression {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(TypeRef)
	 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getNewArray_Type()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getType();

	/**
	 * Sets the value of the '{@link org.imt.minijava.xminiJava.NewArray#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' containment reference.
	 * @see #setSize(Expression)
	 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getNewArray_Size()
	 * @model containment="true"
	 * @generated
	 */
	Expression getSize();

	/**
	 * Sets the value of the '{@link org.imt.minijava.xminiJava.NewArray#getSize <em>Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' containment reference.
	 * @see #getSize()
	 * @generated
	 */
	void setSize(Expression value);

} // NewArray
