/**
 */
package org.imt.minijava.xminiJava.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.imt.minijava.xminiJava.AccessLevel;
import org.imt.minijava.xminiJava.And;
import org.imt.minijava.xminiJava.ArrayAccess;
import org.imt.minijava.xminiJava.ArrayInstance;
import org.imt.minijava.xminiJava.ArrayLength;
import org.imt.minijava.xminiJava.ArrayRefValue;
import org.imt.minijava.xminiJava.ArrayTypeRef;
import org.imt.minijava.xminiJava.Assignee;
import org.imt.minijava.xminiJava.Assignment;
import org.imt.minijava.xminiJava.Block;
import org.imt.minijava.xminiJava.BoolConstant;
import org.imt.minijava.xminiJava.BooleanTypeRef;
import org.imt.minijava.xminiJava.BooleanValue;
import org.imt.minijava.xminiJava.Call;
import org.imt.minijava.xminiJava.ClassRef;
import org.imt.minijava.xminiJava.Context;
import org.imt.minijava.xminiJava.Division;
import org.imt.minijava.xminiJava.Equality;
import org.imt.minijava.xminiJava.Expression;
import org.imt.minijava.xminiJava.Field;
import org.imt.minijava.xminiJava.FieldAccess;
import org.imt.minijava.xminiJava.FieldBinding;
import org.imt.minijava.xminiJava.ForStatement;
import org.imt.minijava.xminiJava.Frame;
import org.imt.minijava.xminiJava.IfStatement;
import org.imt.minijava.xminiJava.Import;
import org.imt.minijava.xminiJava.Inequality;
import org.imt.minijava.xminiJava.Inferior;
import org.imt.minijava.xminiJava.InferiorOrEqual;
import org.imt.minijava.xminiJava.IntConstant;
import org.imt.minijava.xminiJava.IntegerTypeRef;
import org.imt.minijava.xminiJava.IntegerValue;
import org.imt.minijava.xminiJava.Interface;
import org.imt.minijava.xminiJava.Member;
import org.imt.minijava.xminiJava.Method;
import org.imt.minijava.xminiJava.MethodCall;
import org.imt.minijava.xminiJava.MethodCall2;
import org.imt.minijava.xminiJava.Minus;
import org.imt.minijava.xminiJava.Multiplication;
import org.imt.minijava.xminiJava.NamedElement;
import org.imt.minijava.xminiJava.Neg;
import org.imt.minijava.xminiJava.NewArray;
import org.imt.minijava.xminiJava.NewCall;
import org.imt.minijava.xminiJava.NewObject;
import org.imt.minijava.xminiJava.Not;
import org.imt.minijava.xminiJava.Null;
import org.imt.minijava.xminiJava.NullValue;
import org.imt.minijava.xminiJava.ObjectInstance;
import org.imt.minijava.xminiJava.ObjectRefValue;
import org.imt.minijava.xminiJava.Or;
import org.imt.minijava.xminiJava.OutputStream;
import org.imt.minijava.xminiJava.Parameter;
import org.imt.minijava.xminiJava.Plus;
import org.imt.minijava.xminiJava.PrintStatement;
import org.imt.minijava.xminiJava.Program;
import org.imt.minijava.xminiJava.Return;
import org.imt.minijava.xminiJava.SingleTypeRef;
import org.imt.minijava.xminiJava.State;
import org.imt.minijava.xminiJava.Statement;
import org.imt.minijava.xminiJava.StringConstant;
import org.imt.minijava.xminiJava.StringTypeRef;
import org.imt.minijava.xminiJava.StringValue;
import org.imt.minijava.xminiJava.Super;
import org.imt.minijava.xminiJava.Superior;
import org.imt.minijava.xminiJava.SuperiorOrEqual;
import org.imt.minijava.xminiJava.Symbol;
import org.imt.minijava.xminiJava.SymbolBinding;
import org.imt.minijava.xminiJava.SymbolRef;
import org.imt.minijava.xminiJava.This;
import org.imt.minijava.xminiJava.TypeDeclaration;
import org.imt.minijava.xminiJava.TypeRef;
import org.imt.minijava.xminiJava.TypedDeclaration;
import org.imt.minijava.xminiJava.Value;
import org.imt.minijava.xminiJava.VariableDeclaration;
import org.imt.minijava.xminiJava.VoidTypeRef;
import org.imt.minijava.xminiJava.WhileStatement;
import org.imt.minijava.xminiJava.XminiJavaFactory;
import org.imt.minijava.xminiJava.XminiJavaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class XminiJavaFactoryImpl extends EFactoryImpl implements XminiJavaFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XminiJavaFactory init() {
		try {
			XminiJavaFactory theXminiJavaFactory = (XminiJavaFactory)EPackage.Registry.INSTANCE.getEFactory(XminiJavaPackage.eNS_URI);
			if (theXminiJavaFactory != null) {
				return theXminiJavaFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new XminiJavaFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XminiJavaFactoryImpl() {
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
			case XminiJavaPackage.PROGRAM: return createProgram();
			case XminiJavaPackage.IMPORT: return createImport();
			case XminiJavaPackage.TYPE_DECLARATION: return createTypeDeclaration();
			case XminiJavaPackage.CLASS: return createClass();
			case XminiJavaPackage.INTERFACE: return createInterface();
			case XminiJavaPackage.MEMBER: return createMember();
			case XminiJavaPackage.METHOD: return createMethod();
			case XminiJavaPackage.PARAMETER: return createParameter();
			case XminiJavaPackage.FIELD: return createField();
			case XminiJavaPackage.BLOCK: return createBlock();
			case XminiJavaPackage.STATEMENT: return createStatement();
			case XminiJavaPackage.PRINT_STATEMENT: return createPrintStatement();
			case XminiJavaPackage.RETURN: return createReturn();
			case XminiJavaPackage.IF_STATEMENT: return createIfStatement();
			case XminiJavaPackage.WHILE_STATEMENT: return createWhileStatement();
			case XminiJavaPackage.FOR_STATEMENT: return createForStatement();
			case XminiJavaPackage.TYPE_REF: return createTypeRef();
			case XminiJavaPackage.SINGLE_TYPE_REF: return createSingleTypeRef();
			case XminiJavaPackage.CLASS_REF: return createClassRef();
			case XminiJavaPackage.NAMED_ELEMENT: return createNamedElement();
			case XminiJavaPackage.TYPED_DECLARATION: return createTypedDeclaration();
			case XminiJavaPackage.SYMBOL: return createSymbol();
			case XminiJavaPackage.VARIABLE_DECLARATION: return createVariableDeclaration();
			case XminiJavaPackage.ASSIGNMENT: return createAssignment();
			case XminiJavaPackage.ASSIGNEE: return createAssignee();
			case XminiJavaPackage.EXPRESSION: return createExpression();
			case XminiJavaPackage.ARRAY_TYPE_REF: return createArrayTypeRef();
			case XminiJavaPackage.INTEGER_TYPE_REF: return createIntegerTypeRef();
			case XminiJavaPackage.BOOLEAN_TYPE_REF: return createBooleanTypeRef();
			case XminiJavaPackage.STRING_TYPE_REF: return createStringTypeRef();
			case XminiJavaPackage.VOID_TYPE_REF: return createVoidTypeRef();
			case XminiJavaPackage.OR: return createOr();
			case XminiJavaPackage.AND: return createAnd();
			case XminiJavaPackage.EQUALITY: return createEquality();
			case XminiJavaPackage.INEQUALITY: return createInequality();
			case XminiJavaPackage.SUPERIOR_OR_EQUAL: return createSuperiorOrEqual();
			case XminiJavaPackage.INFERIOR_OR_EQUAL: return createInferiorOrEqual();
			case XminiJavaPackage.SUPERIOR: return createSuperior();
			case XminiJavaPackage.INFERIOR: return createInferior();
			case XminiJavaPackage.PLUS: return createPlus();
			case XminiJavaPackage.MINUS: return createMinus();
			case XminiJavaPackage.MULTIPLICATION: return createMultiplication();
			case XminiJavaPackage.DIVISION: return createDivision();
			case XminiJavaPackage.ARRAY_ACCESS: return createArrayAccess();
			case XminiJavaPackage.ARRAY_LENGTH: return createArrayLength();
			case XminiJavaPackage.NOT: return createNot();
			case XminiJavaPackage.NEG: return createNeg();
			case XminiJavaPackage.FIELD_ACCESS: return createFieldAccess();
			case XminiJavaPackage.METHOD_CALL: return createMethodCall();
			case XminiJavaPackage.STRING_CONSTANT: return createStringConstant();
			case XminiJavaPackage.INT_CONSTANT: return createIntConstant();
			case XminiJavaPackage.BOOL_CONSTANT: return createBoolConstant();
			case XminiJavaPackage.THIS: return createThis();
			case XminiJavaPackage.SUPER: return createSuper();
			case XminiJavaPackage.NULL: return createNull();
			case XminiJavaPackage.NEW_OBJECT: return createNewObject();
			case XminiJavaPackage.NEW_ARRAY: return createNewArray();
			case XminiJavaPackage.SYMBOL_REF: return createSymbolRef();
			case XminiJavaPackage.CONTEXT: return createContext();
			case XminiJavaPackage.VALUE: return createValue();
			case XminiJavaPackage.INTEGER_VALUE: return createIntegerValue();
			case XminiJavaPackage.SYMBOL_BINDING: return createSymbolBinding();
			case XminiJavaPackage.FIELD_BINDING: return createFieldBinding();
			case XminiJavaPackage.STRING_VALUE: return createStringValue();
			case XminiJavaPackage.BOOLEAN_VALUE: return createBooleanValue();
			case XminiJavaPackage.OUTPUT_STREAM: return createOutputStream();
			case XminiJavaPackage.STATE: return createState();
			case XminiJavaPackage.FRAME: return createFrame();
			case XminiJavaPackage.NULL_VALUE: return createNullValue();
			case XminiJavaPackage.CALL: return createCall();
			case XminiJavaPackage.NEW_CALL: return createNewCall();
			case XminiJavaPackage.METHOD_CALL2: return createMethodCall2();
			case XminiJavaPackage.OBJECT_INSTANCE: return createObjectInstance();
			case XminiJavaPackage.ARRAY_INSTANCE: return createArrayInstance();
			case XminiJavaPackage.OBJECT_REF_VALUE: return createObjectRefValue();
			case XminiJavaPackage.ARRAY_REF_VALUE: return createArrayRefValue();
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
			case XminiJavaPackage.ACCESS_LEVEL:
				return createAccessLevelFromString(eDataType, initialValue);
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
			case XminiJavaPackage.ACCESS_LEVEL:
				return convertAccessLevelToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Program createProgram() {
		ProgramImpl program = new ProgramImpl();
		return program;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Import createImport() {
		ImportImpl import_ = new ImportImpl();
		return import_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeDeclaration createTypeDeclaration() {
		TypeDeclarationImpl typeDeclaration = new TypeDeclarationImpl();
		return typeDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.imt.minijava.xminiJava.Class createClass() {
		ClassImpl class_ = new ClassImpl();
		return class_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interface createInterface() {
		InterfaceImpl interface_ = new InterfaceImpl();
		return interface_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Member createMember() {
		MemberImpl member = new MemberImpl();
		return member;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Method createMethod() {
		MethodImpl method = new MethodImpl();
		return method;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Field createField() {
		FieldImpl field = new FieldImpl();
		return field;
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
	public Statement createStatement() {
		StatementImpl statement = new StatementImpl();
		return statement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrintStatement createPrintStatement() {
		PrintStatementImpl printStatement = new PrintStatementImpl();
		return printStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Return createReturn() {
		ReturnImpl return_ = new ReturnImpl();
		return return_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IfStatement createIfStatement() {
		IfStatementImpl ifStatement = new IfStatementImpl();
		return ifStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WhileStatement createWhileStatement() {
		WhileStatementImpl whileStatement = new WhileStatementImpl();
		return whileStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForStatement createForStatement() {
		ForStatementImpl forStatement = new ForStatementImpl();
		return forStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRef createTypeRef() {
		TypeRefImpl typeRef = new TypeRefImpl();
		return typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SingleTypeRef createSingleTypeRef() {
		SingleTypeRefImpl singleTypeRef = new SingleTypeRefImpl();
		return singleTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassRef createClassRef() {
		ClassRefImpl classRef = new ClassRefImpl();
		return classRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedElement createNamedElement() {
		NamedElementImpl namedElement = new NamedElementImpl();
		return namedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypedDeclaration createTypedDeclaration() {
		TypedDeclarationImpl typedDeclaration = new TypedDeclarationImpl();
		return typedDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Symbol createSymbol() {
		SymbolImpl symbol = new SymbolImpl();
		return symbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariableDeclaration createVariableDeclaration() {
		VariableDeclarationImpl variableDeclaration = new VariableDeclarationImpl();
		return variableDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Assignment createAssignment() {
		AssignmentImpl assignment = new AssignmentImpl();
		return assignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Assignee createAssignee() {
		AssigneeImpl assignee = new AssigneeImpl();
		return assignee;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression createExpression() {
		ExpressionImpl expression = new ExpressionImpl();
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayTypeRef createArrayTypeRef() {
		ArrayTypeRefImpl arrayTypeRef = new ArrayTypeRefImpl();
		return arrayTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerTypeRef createIntegerTypeRef() {
		IntegerTypeRefImpl integerTypeRef = new IntegerTypeRefImpl();
		return integerTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanTypeRef createBooleanTypeRef() {
		BooleanTypeRefImpl booleanTypeRef = new BooleanTypeRefImpl();
		return booleanTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringTypeRef createStringTypeRef() {
		StringTypeRefImpl stringTypeRef = new StringTypeRefImpl();
		return stringTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VoidTypeRef createVoidTypeRef() {
		VoidTypeRefImpl voidTypeRef = new VoidTypeRefImpl();
		return voidTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Or createOr() {
		OrImpl or = new OrImpl();
		return or;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public And createAnd() {
		AndImpl and = new AndImpl();
		return and;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Equality createEquality() {
		EqualityImpl equality = new EqualityImpl();
		return equality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Inequality createInequality() {
		InequalityImpl inequality = new InequalityImpl();
		return inequality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SuperiorOrEqual createSuperiorOrEqual() {
		SuperiorOrEqualImpl superiorOrEqual = new SuperiorOrEqualImpl();
		return superiorOrEqual;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InferiorOrEqual createInferiorOrEqual() {
		InferiorOrEqualImpl inferiorOrEqual = new InferiorOrEqualImpl();
		return inferiorOrEqual;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Superior createSuperior() {
		SuperiorImpl superior = new SuperiorImpl();
		return superior;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Inferior createInferior() {
		InferiorImpl inferior = new InferiorImpl();
		return inferior;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Plus createPlus() {
		PlusImpl plus = new PlusImpl();
		return plus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Minus createMinus() {
		MinusImpl minus = new MinusImpl();
		return minus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Multiplication createMultiplication() {
		MultiplicationImpl multiplication = new MultiplicationImpl();
		return multiplication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Division createDivision() {
		DivisionImpl division = new DivisionImpl();
		return division;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayAccess createArrayAccess() {
		ArrayAccessImpl arrayAccess = new ArrayAccessImpl();
		return arrayAccess;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayLength createArrayLength() {
		ArrayLengthImpl arrayLength = new ArrayLengthImpl();
		return arrayLength;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Not createNot() {
		NotImpl not = new NotImpl();
		return not;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Neg createNeg() {
		NegImpl neg = new NegImpl();
		return neg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldAccess createFieldAccess() {
		FieldAccessImpl fieldAccess = new FieldAccessImpl();
		return fieldAccess;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MethodCall createMethodCall() {
		MethodCallImpl methodCall = new MethodCallImpl();
		return methodCall;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringConstant createStringConstant() {
		StringConstantImpl stringConstant = new StringConstantImpl();
		return stringConstant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntConstant createIntConstant() {
		IntConstantImpl intConstant = new IntConstantImpl();
		return intConstant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoolConstant createBoolConstant() {
		BoolConstantImpl boolConstant = new BoolConstantImpl();
		return boolConstant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public This createThis() {
		ThisImpl this_ = new ThisImpl();
		return this_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Super createSuper() {
		SuperImpl super_ = new SuperImpl();
		return super_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Null createNull() {
		NullImpl null_ = new NullImpl();
		return null_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NewObject createNewObject() {
		NewObjectImpl newObject = new NewObjectImpl();
		return newObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NewArray createNewArray() {
		NewArrayImpl newArray = new NewArrayImpl();
		return newArray;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolRef createSymbolRef() {
		SymbolRefImpl symbolRef = new SymbolRefImpl();
		return symbolRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Context createContext() {
		ContextImpl context = new ContextImpl();
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value createValue() {
		ValueImpl value = new ValueImpl();
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerValue createIntegerValue() {
		IntegerValueImpl integerValue = new IntegerValueImpl();
		return integerValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolBinding createSymbolBinding() {
		SymbolBindingImpl symbolBinding = new SymbolBindingImpl();
		return symbolBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldBinding createFieldBinding() {
		FieldBindingImpl fieldBinding = new FieldBindingImpl();
		return fieldBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringValue createStringValue() {
		StringValueImpl stringValue = new StringValueImpl();
		return stringValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanValue createBooleanValue() {
		BooleanValueImpl booleanValue = new BooleanValueImpl();
		return booleanValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OutputStream createOutputStream() {
		OutputStreamImpl outputStream = new OutputStreamImpl();
		return outputStream;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State createState() {
		StateImpl state = new StateImpl();
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Frame createFrame() {
		FrameImpl frame = new FrameImpl();
		return frame;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NullValue createNullValue() {
		NullValueImpl nullValue = new NullValueImpl();
		return nullValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Call createCall() {
		CallImpl call = new CallImpl();
		return call;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NewCall createNewCall() {
		NewCallImpl newCall = new NewCallImpl();
		return newCall;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MethodCall2 createMethodCall2() {
		MethodCall2Impl methodCall2 = new MethodCall2Impl();
		return methodCall2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectInstance createObjectInstance() {
		ObjectInstanceImpl objectInstance = new ObjectInstanceImpl();
		return objectInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayInstance createArrayInstance() {
		ArrayInstanceImpl arrayInstance = new ArrayInstanceImpl();
		return arrayInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectRefValue createObjectRefValue() {
		ObjectRefValueImpl objectRefValue = new ObjectRefValueImpl();
		return objectRefValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayRefValue createArrayRefValue() {
		ArrayRefValueImpl arrayRefValue = new ArrayRefValueImpl();
		return arrayRefValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AccessLevel createAccessLevelFromString(EDataType eDataType, String initialValue) {
		AccessLevel result = AccessLevel.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAccessLevelToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XminiJavaPackage getXminiJavaPackage() {
		return (XminiJavaPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static XminiJavaPackage getPackage() {
		return XminiJavaPackage.eINSTANCE;
	}

} //XminiJavaFactoryImpl
