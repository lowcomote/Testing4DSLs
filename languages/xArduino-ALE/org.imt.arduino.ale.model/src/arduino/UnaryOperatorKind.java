/**
 */
package arduino;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Unary Operator Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see arduino.ArduinoPackage#getUnaryOperatorKind()
 * @model
 * @generated
 */
public enum UnaryOperatorKind implements Enumerator {
	/**
	 * The '<em><b>Minus</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MINUS_VALUE
	 * @generated
	 * @ordered
	 */
	MINUS(0, "minus", "minus"),

	/**
	 * The '<em><b>Neg</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NEG_VALUE
	 * @generated
	 * @ordered
	 */
	NEG(1, "neg", "neg");

	/**
	 * The '<em><b>Minus</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MINUS
	 * @model name="minus"
	 * @generated
	 * @ordered
	 */
	public static final int MINUS_VALUE = 0;

	/**
	 * The '<em><b>Neg</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NEG
	 * @model name="neg"
	 * @generated
	 * @ordered
	 */
	public static final int NEG_VALUE = 1;

	/**
	 * An array of all the '<em><b>Unary Operator Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final UnaryOperatorKind[] VALUES_ARRAY =
		new UnaryOperatorKind[] {
			MINUS,
			NEG,
		};

	/**
	 * A public read-only list of all the '<em><b>Unary Operator Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<UnaryOperatorKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Unary Operator Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UnaryOperatorKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UnaryOperatorKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Unary Operator Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UnaryOperatorKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UnaryOperatorKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Unary Operator Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UnaryOperatorKind get(int value) {
		switch (value) {
			case MINUS_VALUE: return MINUS;
			case NEG_VALUE: return NEG;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private UnaryOperatorKind(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //UnaryOperatorKind
