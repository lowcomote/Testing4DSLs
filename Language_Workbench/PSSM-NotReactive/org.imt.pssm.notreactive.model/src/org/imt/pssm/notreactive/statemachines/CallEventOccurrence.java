/**
 */
package org.imt.pssm.notreactive.statemachines;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Call Event Occurrence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.pssm.notreactive.statemachines.CallEventOccurrence#getOperation <em>Operation</em>}</li>
 *   <li>{@link org.imt.pssm.notreactive.statemachines.CallEventOccurrence#getInParameterValues <em>In Parameter Values</em>}</li>
 *   <li>{@link org.imt.pssm.notreactive.statemachines.CallEventOccurrence#getOutParameterValues <em>Out Parameter Values</em>}</li>
 *   <li>{@link org.imt.pssm.notreactive.statemachines.CallEventOccurrence#getReturnValue <em>Return Value</em>}</li>
 * </ul>
 *
 * @see org.imt.pssm.notreactive.statemachines.StatemachinesPackage#getCallEventOccurrence()
 * @model
 * @generated
 */
public interface CallEventOccurrence extends EventOccurrence {
	/**
	 * Returns the value of the '<em><b>Operation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation</em>' reference.
	 * @see #setOperation(Operation)
	 * @see org.imt.pssm.notreactive.statemachines.StatemachinesPackage#getCallEventOccurrence_Operation()
	 * @model required="true"
	 * @generated
	 */
	Operation getOperation();

	/**
	 * Sets the value of the '{@link org.imt.pssm.notreactive.statemachines.CallEventOccurrence#getOperation <em>Operation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation</em>' reference.
	 * @see #getOperation()
	 * @generated
	 */
	void setOperation(Operation value);

	/**
	 * Returns the value of the '<em><b>In Parameter Values</b></em>' reference list.
	 * The list contents are of type {@link org.imt.pssm.notreactive.statemachines.AttributeValue}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Parameter Values</em>' reference list.
	 * @see org.imt.pssm.notreactive.statemachines.StatemachinesPackage#getCallEventOccurrence_InParameterValues()
	 * @model annotation="dynamic"
	 * @generated
	 */
	EList<AttributeValue> getInParameterValues();

	/**
	 * Returns the value of the '<em><b>Out Parameter Values</b></em>' reference list.
	 * The list contents are of type {@link org.imt.pssm.notreactive.statemachines.AttributeValue}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out Parameter Values</em>' reference list.
	 * @see org.imt.pssm.notreactive.statemachines.StatemachinesPackage#getCallEventOccurrence_OutParameterValues()
	 * @model annotation="dynamic"
	 * @generated
	 */
	EList<AttributeValue> getOutParameterValues();

	/**
	 * Returns the value of the '<em><b>Return Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Value</em>' reference.
	 * @see #setReturnValue(AttributeValue)
	 * @see org.imt.pssm.notreactive.statemachines.StatemachinesPackage#getCallEventOccurrence_ReturnValue()
	 * @model annotation="dynamic"
	 * @generated
	 */
	AttributeValue getReturnValue();

	/**
	 * Sets the value of the '{@link org.imt.pssm.notreactive.statemachines.CallEventOccurrence#getReturnValue <em>Return Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Value</em>' reference.
	 * @see #getReturnValue()
	 * @generated
	 */
	void setReturnValue(AttributeValue value);

} // CallEventOccurrence
