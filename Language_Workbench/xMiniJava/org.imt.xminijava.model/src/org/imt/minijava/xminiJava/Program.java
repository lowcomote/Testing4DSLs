/**
 */
package org.imt.minijava.xminiJava;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Program</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.minijava.xminiJava.Program#getName <em>Name</em>}</li>
 *   <li>{@link org.imt.minijava.xminiJava.Program#getImports <em>Imports</em>}</li>
 *   <li>{@link org.imt.minijava.xminiJava.Program#getClasses <em>Classes</em>}</li>
 *   <li>{@link org.imt.minijava.xminiJava.Program#getState <em>State</em>}</li>
 * </ul>
 *
 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getProgram()
 * @model
 * @generated
 */
public interface Program extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getProgram_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.imt.minijava.xminiJava.Program#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.minijava.xminiJava.Import}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imports</em>' containment reference list.
	 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getProgram_Imports()
	 * @model containment="true"
	 * @generated
	 */
	EList<Import> getImports();

	/**
	 * Returns the value of the '<em><b>Classes</b></em>' containment reference list.
	 * The list contents are of type {@link org.imt.minijava.xminiJava.TypeDeclaration}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classes</em>' containment reference list.
	 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getProgram_Classes()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeDeclaration> getClasses();

	/**
	 * Returns the value of the '<em><b>State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' containment reference.
	 * @see #setState(State)
	 * @see org.imt.minijava.xminiJava.XminiJavaPackage#getProgram_State()
	 * @model containment="true"
	 *        annotation="aspect"
	 * @generated
	 */
	State getState();

	/**
	 * Sets the value of the '{@link org.imt.minijava.xminiJava.Program#getState <em>State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' containment reference.
	 * @see #getState()
	 * @generated
	 */
	void setState(State value);

} // Program
