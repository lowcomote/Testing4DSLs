/**
 */
package org.imt.bpmn.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.imt.bpmn.BooleanBinaryExpression;
import org.imt.bpmn.BooleanBinaryOperator;
import org.imt.bpmn.BooleanVariable;
import org.imt.bpmn.bpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Boolean Binary Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.impl.BooleanBinaryExpressionImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link org.imt.bpmn.impl.BooleanBinaryExpressionImpl#getOperand1 <em>Operand1</em>}</li>
 *   <li>{@link org.imt.bpmn.impl.BooleanBinaryExpressionImpl#getOperand2 <em>Operand2</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BooleanBinaryExpressionImpl extends BooleanExpressionImpl implements BooleanBinaryExpression {
	/**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final BooleanBinaryOperator OPERATOR_EDEFAULT = BooleanBinaryOperator.AND;

	/**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected BooleanBinaryOperator operator = OPERATOR_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOperand1() <em>Operand1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperand1()
	 * @generated
	 * @ordered
	 */
	protected BooleanVariable operand1;

	/**
	 * The cached value of the '{@link #getOperand2() <em>Operand2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperand2()
	 * @generated
	 * @ordered
	 */
	protected BooleanVariable operand2;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BooleanBinaryExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return bpmnPackage.Literals.BOOLEAN_BINARY_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanBinaryOperator getOperator() {
		return operator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(BooleanBinaryOperator newOperator) {
		BooleanBinaryOperator oldOperator = operator;
		operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERATOR, oldOperator, operator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanVariable getOperand1() {
		if (operand1 != null && operand1.eIsProxy()) {
			InternalEObject oldOperand1 = (InternalEObject)operand1;
			operand1 = (BooleanVariable)eResolveProxy(oldOperand1);
			if (operand1 != oldOperand1) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERAND1, oldOperand1, operand1));
			}
		}
		return operand1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanVariable basicGetOperand1() {
		return operand1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperand1(BooleanVariable newOperand1) {
		BooleanVariable oldOperand1 = operand1;
		operand1 = newOperand1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERAND1, oldOperand1, operand1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanVariable getOperand2() {
		if (operand2 != null && operand2.eIsProxy()) {
			InternalEObject oldOperand2 = (InternalEObject)operand2;
			operand2 = (BooleanVariable)eResolveProxy(oldOperand2);
			if (operand2 != oldOperand2) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERAND2, oldOperand2, operand2));
			}
		}
		return operand2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanVariable basicGetOperand2() {
		return operand2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperand2(BooleanVariable newOperand2) {
		BooleanVariable oldOperand2 = operand2;
		operand2 = newOperand2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERAND2, oldOperand2, operand2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERATOR:
				return getOperator();
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERAND1:
				if (resolve) return getOperand1();
				return basicGetOperand1();
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERAND2:
				if (resolve) return getOperand2();
				return basicGetOperand2();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERATOR:
				setOperator((BooleanBinaryOperator)newValue);
				return;
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERAND1:
				setOperand1((BooleanVariable)newValue);
				return;
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERAND2:
				setOperand2((BooleanVariable)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERATOR:
				setOperator(OPERATOR_EDEFAULT);
				return;
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERAND1:
				setOperand1((BooleanVariable)null);
				return;
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERAND2:
				setOperand2((BooleanVariable)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERATOR:
				return operator != OPERATOR_EDEFAULT;
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERAND1:
				return operand1 != null;
			case bpmnPackage.BOOLEAN_BINARY_EXPRESSION__OPERAND2:
				return operand2 != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (operator: ");
		result.append(operator);
		result.append(')');
		return result.toString();
	}

} //BooleanBinaryExpressionImpl
