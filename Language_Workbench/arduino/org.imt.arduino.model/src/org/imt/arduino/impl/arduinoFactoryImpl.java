/**
 */
package org.imt.arduino.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.imt.arduino.BinaryExpression;
import org.imt.arduino.BinaryOperatorKind;
import org.imt.arduino.Block;
import org.imt.arduino.Board;
import org.imt.arduino.Constant;
import org.imt.arduino.Delay;
import org.imt.arduino.If;
import org.imt.arduino.Led;
import org.imt.arduino.ModuleGet;
import org.imt.arduino.Project;
import org.imt.arduino.PushButton;
import org.imt.arduino.SetLed;
import org.imt.arduino.Sketch;
import org.imt.arduino.UnaryExpression;
import org.imt.arduino.UnaryOperatorKind;
import org.imt.arduino.WaitFor;
import org.imt.arduino.While;
import org.imt.arduino.arduinoFactory;
import org.imt.arduino.arduinoPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class arduinoFactoryImpl extends EFactoryImpl implements arduinoFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static arduinoFactory init() {
		try {
			arduinoFactory thearduinoFactory = (arduinoFactory)EPackage.Registry.INSTANCE.getEFactory(arduinoPackage.eNS_URI);
			if (thearduinoFactory != null) {
				return thearduinoFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new arduinoFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public arduinoFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case arduinoPackage.PROJECT: return createProject();
			case arduinoPackage.BOARD: return createBoard();
			case arduinoPackage.LED: return createLed();
			case arduinoPackage.PUSH_BUTTON: return createPushButton();
			case arduinoPackage.SKETCH: return createSketch();
			case arduinoPackage.BLOCK: return createBlock();
			case arduinoPackage.IF: return createIf();
			case arduinoPackage.WHILE: return createWhile();
			case arduinoPackage.SET_LED: return createSetLed();
			case arduinoPackage.DELAY: return createDelay();
			case arduinoPackage.WAIT_FOR: return createWaitFor();
			case arduinoPackage.CONSTANT: return createConstant();
			case arduinoPackage.UNARY_EXPRESSION: return createUnaryExpression();
			case arduinoPackage.BINARY_EXPRESSION: return createBinaryExpression();
			case arduinoPackage.MODULE_GET: return createModuleGet();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case arduinoPackage.UNARY_OPERATOR_KIND:
				return createUnaryOperatorKindFromString(eDataType, initialValue);
			case arduinoPackage.BINARY_OPERATOR_KIND:
				return createBinaryOperatorKindFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case arduinoPackage.UNARY_OPERATOR_KIND:
				return convertUnaryOperatorKindToString(eDataType, instanceValue);
			case arduinoPackage.BINARY_OPERATOR_KIND:
				return convertBinaryOperatorKindToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Project createProject() {
		ProjectImpl project = new ProjectImpl();
		return project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Board createBoard() {
		BoardImpl board = new BoardImpl();
		return board;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Led createLed() {
		LedImpl led = new LedImpl();
		return led;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PushButton createPushButton() {
		PushButtonImpl pushButton = new PushButtonImpl();
		return pushButton;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sketch createSketch() {
		SketchImpl sketch = new SketchImpl();
		return sketch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Block createBlock() {
		BlockImpl block = new BlockImpl();
		return block;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public If createIf() {
		IfImpl if_ = new IfImpl();
		return if_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public While createWhile() {
		WhileImpl while_ = new WhileImpl();
		return while_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SetLed createSetLed() {
		SetLedImpl setLed = new SetLedImpl();
		return setLed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Delay createDelay() {
		DelayImpl delay = new DelayImpl();
		return delay;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WaitFor createWaitFor() {
		WaitForImpl waitFor = new WaitForImpl();
		return waitFor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Constant createConstant() {
		ConstantImpl constant = new ConstantImpl();
		return constant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnaryExpression createUnaryExpression() {
		UnaryExpressionImpl unaryExpression = new UnaryExpressionImpl();
		return unaryExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryExpression createBinaryExpression() {
		BinaryExpressionImpl binaryExpression = new BinaryExpressionImpl();
		return binaryExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleGet createModuleGet() {
		ModuleGetImpl moduleGet = new ModuleGetImpl();
		return moduleGet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnaryOperatorKind createUnaryOperatorKindFromString(EDataType eDataType, String initialValue) {
		UnaryOperatorKind result = UnaryOperatorKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertUnaryOperatorKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryOperatorKind createBinaryOperatorKindFromString(EDataType eDataType, String initialValue) {
		BinaryOperatorKind result = BinaryOperatorKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBinaryOperatorKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public arduinoPackage getarduinoPackage() {
		return (arduinoPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static arduinoPackage getPackage() {
		return arduinoPackage.eINSTANCE;
	}

} //arduinoFactoryImpl
