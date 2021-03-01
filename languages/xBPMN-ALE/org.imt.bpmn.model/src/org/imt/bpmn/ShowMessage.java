/**
 */
package org.imt.bpmn;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Show Message</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.imt.bpmn.ShowMessage#getMessage <em>Message</em>}</li>
 * </ul>
 *
 * @see org.imt.bpmn.bpmnPackage#getShowMessage()
 * @model
 * @generated
 */
public interface ShowMessage extends ClientActivity {
	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see org.imt.bpmn.bpmnPackage#getShowMessage_Message()
	 * @model required="true"
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link org.imt.bpmn.ShowMessage#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

} // ShowMessage
