/**
 */
package org.imt.bpmn;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.imt.bpmn.bpmnFactory
 * @model kind="package"
 * @generated
 */
public interface bpmnPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "bpmn";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "www.MendixBPMN.com";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "bpmn";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	bpmnPackage eINSTANCE = org.imt.bpmn.impl.bpmnPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.MicroflowImpl <em>Microflow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.MicroflowImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getMicroflow()
	 * @generated
	 */
	int MICROFLOW = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW__NAME = 0;

	/**
	 * The feature id for the '<em><b>Owned Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW__OWNED_ELEMENTS = 1;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW__PARAMETERS = 2;

	/**
	 * The feature id for the '<em><b>Current Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW__CURRENT_NODE = 3;

	/**
	 * The feature id for the '<em><b>Valued Variables</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW__VALUED_VARIABLES = 4;

	/**
	 * The number of structural features of the '<em>Microflow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Microflow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.MicroflowElementImpl <em>Microflow Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.MicroflowElementImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getMicroflowElement()
	 * @generated
	 */
	int MICROFLOW_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_ELEMENT__INCOMING_FLOWS = 0;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_ELEMENT__OUTGOING_FLOWS = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_ELEMENT__LABEL = 2;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_ELEMENT__OWNER_MICROFLOW = 3;

	/**
	 * The number of structural features of the '<em>Microflow Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_ELEMENT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Microflow Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.SequenceFlowImpl <em>Sequence Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.SequenceFlowImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getSequenceFlow()
	 * @generated
	 */
	int SEQUENCE_FLOW = 2;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_FLOW__CONSTRAINT = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_FLOW__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_FLOW__TARGET = 2;

	/**
	 * The number of structural features of the '<em>Sequence Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_FLOW_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Sequence Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_FLOW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.ActivityImpl <em>Activity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.ActivityImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getActivity()
	 * @generated
	 */
	int ACTIVITY = 3;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__INCOMING_FLOWS = MICROFLOW_ELEMENT__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__OUTGOING_FLOWS = MICROFLOW_ELEMENT__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__LABEL = MICROFLOW_ELEMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__OWNER_MICROFLOW = MICROFLOW_ELEMENT__OWNER_MICROFLOW;

	/**
	 * The number of structural features of the '<em>Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_FEATURE_COUNT = MICROFLOW_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_OPERATION_COUNT = MICROFLOW_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.ObjectActivityImpl <em>Object Activity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.ObjectActivityImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getObjectActivity()
	 * @generated
	 */
	int OBJECT_ACTIVITY = 4;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ACTIVITY__INCOMING_FLOWS = ACTIVITY__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ACTIVITY__OUTGOING_FLOWS = ACTIVITY__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ACTIVITY__LABEL = ACTIVITY__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ACTIVITY__OWNER_MICROFLOW = ACTIVITY__OWNER_MICROFLOW;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ACTIVITY__ENTITY = ACTIVITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Object Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ACTIVITY_FEATURE_COUNT = ACTIVITY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Object Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_ACTIVITY_OPERATION_COUNT = ACTIVITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.CreateObjectImpl <em>Create Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.CreateObjectImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getCreateObject()
	 * @generated
	 */
	int CREATE_OBJECT = 5;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_OBJECT__INCOMING_FLOWS = OBJECT_ACTIVITY__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_OBJECT__OUTGOING_FLOWS = OBJECT_ACTIVITY__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_OBJECT__LABEL = OBJECT_ACTIVITY__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_OBJECT__OWNER_MICROFLOW = OBJECT_ACTIVITY__OWNER_MICROFLOW;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_OBJECT__ENTITY = OBJECT_ACTIVITY__ENTITY;

	/**
	 * The feature id for the '<em><b>New Entity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_OBJECT__NEW_ENTITY = OBJECT_ACTIVITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Create Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_OBJECT_FEATURE_COUNT = OBJECT_ACTIVITY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Create Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_OBJECT_OPERATION_COUNT = OBJECT_ACTIVITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.DeleteObjectImpl <em>Delete Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.DeleteObjectImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getDeleteObject()
	 * @generated
	 */
	int DELETE_OBJECT = 6;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_OBJECT__INCOMING_FLOWS = OBJECT_ACTIVITY__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_OBJECT__OUTGOING_FLOWS = OBJECT_ACTIVITY__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_OBJECT__LABEL = OBJECT_ACTIVITY__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_OBJECT__OWNER_MICROFLOW = OBJECT_ACTIVITY__OWNER_MICROFLOW;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_OBJECT__ENTITY = OBJECT_ACTIVITY__ENTITY;

	/**
	 * The number of structural features of the '<em>Delete Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_OBJECT_FEATURE_COUNT = OBJECT_ACTIVITY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Delete Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_OBJECT_OPERATION_COUNT = OBJECT_ACTIVITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.ChangeObjectImpl <em>Change Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.ChangeObjectImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getChangeObject()
	 * @generated
	 */
	int CHANGE_OBJECT = 7;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_OBJECT__INCOMING_FLOWS = OBJECT_ACTIVITY__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_OBJECT__OUTGOING_FLOWS = OBJECT_ACTIVITY__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_OBJECT__LABEL = OBJECT_ACTIVITY__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_OBJECT__OWNER_MICROFLOW = OBJECT_ACTIVITY__OWNER_MICROFLOW;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_OBJECT__ENTITY = OBJECT_ACTIVITY__ENTITY;

	/**
	 * The number of structural features of the '<em>Change Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_OBJECT_FEATURE_COUNT = OBJECT_ACTIVITY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Change Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_OBJECT_OPERATION_COUNT = OBJECT_ACTIVITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.RetrieveObjectImpl <em>Retrieve Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.RetrieveObjectImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getRetrieveObject()
	 * @generated
	 */
	int RETRIEVE_OBJECT = 8;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETRIEVE_OBJECT__INCOMING_FLOWS = OBJECT_ACTIVITY__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETRIEVE_OBJECT__OUTGOING_FLOWS = OBJECT_ACTIVITY__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETRIEVE_OBJECT__LABEL = OBJECT_ACTIVITY__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETRIEVE_OBJECT__OWNER_MICROFLOW = OBJECT_ACTIVITY__OWNER_MICROFLOW;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETRIEVE_OBJECT__ENTITY = OBJECT_ACTIVITY__ENTITY;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETRIEVE_OBJECT__CONSTRAINT = OBJECT_ACTIVITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Retrieve Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETRIEVE_OBJECT_FEATURE_COUNT = OBJECT_ACTIVITY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Retrieve Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETRIEVE_OBJECT_OPERATION_COUNT = OBJECT_ACTIVITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.ActionCallActivityImpl <em>Action Call Activity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.ActionCallActivityImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getActionCallActivity()
	 * @generated
	 */
	int ACTION_CALL_ACTIVITY = 9;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CALL_ACTIVITY__INCOMING_FLOWS = ACTIVITY__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CALL_ACTIVITY__OUTGOING_FLOWS = ACTIVITY__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CALL_ACTIVITY__LABEL = ACTIVITY__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CALL_ACTIVITY__OWNER_MICROFLOW = ACTIVITY__OWNER_MICROFLOW;

	/**
	 * The number of structural features of the '<em>Action Call Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CALL_ACTIVITY_FEATURE_COUNT = ACTIVITY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Action Call Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CALL_ACTIVITY_OPERATION_COUNT = ACTIVITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.MicroflowCallImpl <em>Microflow Call</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.MicroflowCallImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getMicroflowCall()
	 * @generated
	 */
	int MICROFLOW_CALL = 10;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_CALL__INCOMING_FLOWS = ACTION_CALL_ACTIVITY__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_CALL__OUTGOING_FLOWS = ACTION_CALL_ACTIVITY__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_CALL__LABEL = ACTION_CALL_ACTIVITY__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_CALL__OWNER_MICROFLOW = ACTION_CALL_ACTIVITY__OWNER_MICROFLOW;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_CALL__TARGET = ACTION_CALL_ACTIVITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Microflow Call</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_CALL_FEATURE_COUNT = ACTION_CALL_ACTIVITY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Microflow Call</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROFLOW_CALL_OPERATION_COUNT = ACTION_CALL_ACTIVITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.VariableActivityImpl <em>Variable Activity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.VariableActivityImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getVariableActivity()
	 * @generated
	 */
	int VARIABLE_ACTIVITY = 11;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_ACTIVITY__INCOMING_FLOWS = ACTIVITY__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_ACTIVITY__OUTGOING_FLOWS = ACTIVITY__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_ACTIVITY__LABEL = ACTIVITY__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_ACTIVITY__OWNER_MICROFLOW = ACTIVITY__OWNER_MICROFLOW;

	/**
	 * The number of structural features of the '<em>Variable Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_ACTIVITY_FEATURE_COUNT = ACTIVITY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Variable Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_ACTIVITY_OPERATION_COUNT = ACTIVITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.CreateVariableImpl <em>Create Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.CreateVariableImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getCreateVariable()
	 * @generated
	 */
	int CREATE_VARIABLE = 12;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_VARIABLE__INCOMING_FLOWS = VARIABLE_ACTIVITY__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_VARIABLE__OUTGOING_FLOWS = VARIABLE_ACTIVITY__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_VARIABLE__LABEL = VARIABLE_ACTIVITY__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_VARIABLE__OWNER_MICROFLOW = VARIABLE_ACTIVITY__OWNER_MICROFLOW;

	/**
	 * The feature id for the '<em><b>New Variable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_VARIABLE__NEW_VARIABLE = VARIABLE_ACTIVITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Create Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_VARIABLE_FEATURE_COUNT = VARIABLE_ACTIVITY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Create Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_VARIABLE_OPERATION_COUNT = VARIABLE_ACTIVITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.ChangeVariableImpl <em>Change Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.ChangeVariableImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getChangeVariable()
	 * @generated
	 */
	int CHANGE_VARIABLE = 13;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_VARIABLE__INCOMING_FLOWS = VARIABLE_ACTIVITY__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_VARIABLE__OUTGOING_FLOWS = VARIABLE_ACTIVITY__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_VARIABLE__LABEL = VARIABLE_ACTIVITY__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_VARIABLE__OWNER_MICROFLOW = VARIABLE_ACTIVITY__OWNER_MICROFLOW;

	/**
	 * The feature id for the '<em><b>Target Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_VARIABLE__TARGET_VARIABLE = VARIABLE_ACTIVITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_VARIABLE__NEW_VALUE = VARIABLE_ACTIVITY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Change Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_VARIABLE_FEATURE_COUNT = VARIABLE_ACTIVITY_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Change Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_VARIABLE_OPERATION_COUNT = VARIABLE_ACTIVITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.ClientActivityImpl <em>Client Activity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.ClientActivityImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getClientActivity()
	 * @generated
	 */
	int CLIENT_ACTIVITY = 14;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLIENT_ACTIVITY__INCOMING_FLOWS = ACTIVITY__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLIENT_ACTIVITY__OUTGOING_FLOWS = ACTIVITY__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLIENT_ACTIVITY__LABEL = ACTIVITY__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLIENT_ACTIVITY__OWNER_MICROFLOW = ACTIVITY__OWNER_MICROFLOW;

	/**
	 * The number of structural features of the '<em>Client Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLIENT_ACTIVITY_FEATURE_COUNT = ACTIVITY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Client Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLIENT_ACTIVITY_OPERATION_COUNT = ACTIVITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.ShowMessageImpl <em>Show Message</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.ShowMessageImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getShowMessage()
	 * @generated
	 */
	int SHOW_MESSAGE = 15;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHOW_MESSAGE__INCOMING_FLOWS = CLIENT_ACTIVITY__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHOW_MESSAGE__OUTGOING_FLOWS = CLIENT_ACTIVITY__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHOW_MESSAGE__LABEL = CLIENT_ACTIVITY__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHOW_MESSAGE__OWNER_MICROFLOW = CLIENT_ACTIVITY__OWNER_MICROFLOW;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHOW_MESSAGE__MESSAGE = CLIENT_ACTIVITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Show Message</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHOW_MESSAGE_FEATURE_COUNT = CLIENT_ACTIVITY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Show Message</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHOW_MESSAGE_OPERATION_COUNT = CLIENT_ACTIVITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.EventImpl <em>Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.EventImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getEvent()
	 * @generated
	 */
	int EVENT = 16;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__INCOMING_FLOWS = MICROFLOW_ELEMENT__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__OUTGOING_FLOWS = MICROFLOW_ELEMENT__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__LABEL = MICROFLOW_ELEMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__OWNER_MICROFLOW = MICROFLOW_ELEMENT__OWNER_MICROFLOW;

	/**
	 * The number of structural features of the '<em>Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FEATURE_COUNT = MICROFLOW_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_OPERATION_COUNT = MICROFLOW_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.StartEventImpl <em>Start Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.StartEventImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getStartEvent()
	 * @generated
	 */
	int START_EVENT = 17;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_EVENT__INCOMING_FLOWS = EVENT__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_EVENT__OUTGOING_FLOWS = EVENT__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_EVENT__LABEL = EVENT__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_EVENT__OWNER_MICROFLOW = EVENT__OWNER_MICROFLOW;

	/**
	 * The number of structural features of the '<em>Start Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_EVENT_FEATURE_COUNT = EVENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Start Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_EVENT_OPERATION_COUNT = EVENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.EndEventImpl <em>End Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.EndEventImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getEndEvent()
	 * @generated
	 */
	int END_EVENT = 18;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_EVENT__INCOMING_FLOWS = EVENT__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_EVENT__OUTGOING_FLOWS = EVENT__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_EVENT__LABEL = EVENT__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_EVENT__OWNER_MICROFLOW = EVENT__OWNER_MICROFLOW;

	/**
	 * The feature id for the '<em><b>Return Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_EVENT__RETURN_VARIABLE = EVENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>End Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_EVENT_FEATURE_COUNT = EVENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>End Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_EVENT_OPERATION_COUNT = EVENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.VariableImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.EntityImpl <em>Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.EntityImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getEntity()
	 * @generated
	 */
	int ENTITY = 20;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__NAME = VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__ATTRIBUTES = VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__REFERENCES = VARIABLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_OPERATION_COUNT = VARIABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.ReferenceImpl <em>Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.ReferenceImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getReference()
	 * @generated
	 */
	int REFERENCE = 21;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Referenced Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE__REFERENCED_ENTITY = 1;

	/**
	 * The number of structural features of the '<em>Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.BasicVariableImpl <em>Basic Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.BasicVariableImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBasicVariable()
	 * @generated
	 */
	int BASIC_VARIABLE = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_VARIABLE__NAME = VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Value Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_VARIABLE__VALUE_OBJECT = VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Basic Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_VARIABLE_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Basic Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_VARIABLE_OPERATION_COUNT = VARIABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.IntegerVariableImpl <em>Integer Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.IntegerVariableImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getIntegerVariable()
	 * @generated
	 */
	int INTEGER_VARIABLE = 23;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VARIABLE__NAME = BASIC_VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Value Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VARIABLE__VALUE_OBJECT = BASIC_VARIABLE__VALUE_OBJECT;

	/**
	 * The number of structural features of the '<em>Integer Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VARIABLE_FEATURE_COUNT = BASIC_VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Integer Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VARIABLE_OPERATION_COUNT = BASIC_VARIABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.BooleanVariableImpl <em>Boolean Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.BooleanVariableImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanVariable()
	 * @generated
	 */
	int BOOLEAN_VARIABLE = 24;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VARIABLE__NAME = BASIC_VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Value Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VARIABLE__VALUE_OBJECT = BASIC_VARIABLE__VALUE_OBJECT;

	/**
	 * The number of structural features of the '<em>Boolean Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VARIABLE_FEATURE_COUNT = BASIC_VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Boolean Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VARIABLE_OPERATION_COUNT = BASIC_VARIABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.StringVariableImpl <em>String Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.StringVariableImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getStringVariable()
	 * @generated
	 */
	int STRING_VARIABLE = 25;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VARIABLE__NAME = BASIC_VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Value Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VARIABLE__VALUE_OBJECT = BASIC_VARIABLE__VALUE_OBJECT;

	/**
	 * The number of structural features of the '<em>String Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VARIABLE_FEATURE_COUNT = BASIC_VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>String Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VARIABLE_OPERATION_COUNT = BASIC_VARIABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.ValueImpl <em>Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.ValueImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getValue()
	 * @generated
	 */
	int VALUE = 26;

	/**
	 * The number of structural features of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.IntegerValueImpl <em>Integer Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.IntegerValueImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getIntegerValue()
	 * @generated
	 */
	int INTEGER_VALUE = 27;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Integer Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.BooleanValueImpl <em>Boolean Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.BooleanValueImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanValue()
	 * @generated
	 */
	int BOOLEAN_VALUE = 28;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Boolean Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.StringValueImpl <em>String Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.StringValueImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getStringValue()
	 * @generated
	 */
	int STRING_VALUE = 29;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>String Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.DecisionImpl <em>Decision</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.DecisionImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getDecision()
	 * @generated
	 */
	int DECISION = 30;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION__INCOMING_FLOWS = MICROFLOW_ELEMENT__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION__OUTGOING_FLOWS = MICROFLOW_ELEMENT__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION__LABEL = MICROFLOW_ELEMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION__OWNER_MICROFLOW = MICROFLOW_ELEMENT__OWNER_MICROFLOW;

	/**
	 * The number of structural features of the '<em>Decision</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_FEATURE_COUNT = MICROFLOW_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Decision</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_OPERATION_COUNT = MICROFLOW_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.ForkDecisionImpl <em>Fork Decision</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.ForkDecisionImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getForkDecision()
	 * @generated
	 */
	int FORK_DECISION = 31;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORK_DECISION__INCOMING_FLOWS = DECISION__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORK_DECISION__OUTGOING_FLOWS = DECISION__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORK_DECISION__LABEL = DECISION__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORK_DECISION__OWNER_MICROFLOW = DECISION__OWNER_MICROFLOW;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORK_DECISION__EXPRESSION = DECISION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Fork Decision</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORK_DECISION_FEATURE_COUNT = DECISION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Fork Decision</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORK_DECISION_OPERATION_COUNT = DECISION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.MergeDecisionImpl <em>Merge Decision</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.MergeDecisionImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getMergeDecision()
	 * @generated
	 */
	int MERGE_DECISION = 32;

	/**
	 * The feature id for the '<em><b>Incoming Flows</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_DECISION__INCOMING_FLOWS = DECISION__INCOMING_FLOWS;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_DECISION__OUTGOING_FLOWS = DECISION__OUTGOING_FLOWS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_DECISION__LABEL = DECISION__LABEL;

	/**
	 * The feature id for the '<em><b>Owner Microflow</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_DECISION__OWNER_MICROFLOW = DECISION__OWNER_MICROFLOW;

	/**
	 * The number of structural features of the '<em>Merge Decision</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_DECISION_FEATURE_COUNT = DECISION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Merge Decision</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_DECISION_OPERATION_COUNT = DECISION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.ExpressionImpl <em>Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.ExpressionImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 33;

	/**
	 * The number of structural features of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.IntegerComparisonExpressionImpl <em>Integer Comparison Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.IntegerComparisonExpressionImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getIntegerComparisonExpression()
	 * @generated
	 */
	int INTEGER_COMPARISON_EXPRESSION = 34;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_COMPARISON_EXPRESSION__OPERATOR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operand1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_COMPARISON_EXPRESSION__OPERAND1 = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operand2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_COMPARISON_EXPRESSION__OPERAND2 = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Integer Comparison Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_COMPARISON_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Integer Comparison Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_COMPARISON_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.StringComparisonExpressionImpl <em>String Comparison Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.StringComparisonExpressionImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getStringComparisonExpression()
	 * @generated
	 */
	int STRING_COMPARISON_EXPRESSION = 35;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_COMPARISON_EXPRESSION__OPERATOR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operand1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_COMPARISON_EXPRESSION__OPERAND1 = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operand2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_COMPARISON_EXPRESSION__OPERAND2 = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>String Comparison Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_COMPARISON_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>String Comparison Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_COMPARISON_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.BooleanExpressionImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanExpression()
	 * @generated
	 */
	int BOOLEAN_EXPRESSION = 36;

	/**
	 * The number of structural features of the '<em>Boolean Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Boolean Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.BooleanBinaryExpressionImpl <em>Boolean Binary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.BooleanBinaryExpressionImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanBinaryExpression()
	 * @generated
	 */
	int BOOLEAN_BINARY_EXPRESSION = 37;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_BINARY_EXPRESSION__OPERATOR = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operand1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_BINARY_EXPRESSION__OPERAND1 = BOOLEAN_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operand2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_BINARY_EXPRESSION__OPERAND2 = BOOLEAN_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Boolean Binary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_BINARY_EXPRESSION_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Boolean Binary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_BINARY_EXPRESSION_OPERATION_COUNT = BOOLEAN_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.impl.BooleanUnaryExpressionImpl <em>Boolean Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.impl.BooleanUnaryExpressionImpl
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanUnaryExpression()
	 * @generated
	 */
	int BOOLEAN_UNARY_EXPRESSION = 38;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_UNARY_EXPRESSION__OPERATOR = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operand</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_UNARY_EXPRESSION__OPERAND = BOOLEAN_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Boolean Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_UNARY_EXPRESSION_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Boolean Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_UNARY_EXPRESSION_OPERATION_COUNT = BOOLEAN_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.BooleanBinaryOperator <em>Boolean Binary Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.BooleanBinaryOperator
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanBinaryOperator()
	 * @generated
	 */
	int BOOLEAN_BINARY_OPERATOR = 39;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.BooleanUnaryOperator <em>Boolean Unary Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.BooleanUnaryOperator
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanUnaryOperator()
	 * @generated
	 */
	int BOOLEAN_UNARY_OPERATOR = 40;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.IntegerComparisonOperator <em>Integer Comparison Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.IntegerComparisonOperator
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getIntegerComparisonOperator()
	 * @generated
	 */
	int INTEGER_COMPARISON_OPERATOR = 41;

	/**
	 * The meta object id for the '{@link org.imt.bpmn.StringComparisonOperator <em>String Comparison Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.imt.bpmn.StringComparisonOperator
	 * @see org.imt.bpmn.impl.bpmnPackageImpl#getStringComparisonOperator()
	 * @generated
	 */
	int STRING_COMPARISON_OPERATOR = 42;


	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.Microflow <em>Microflow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Microflow</em>'.
	 * @see org.imt.bpmn.Microflow
	 * @generated
	 */
	EClass getMicroflow();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.Microflow#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.imt.bpmn.Microflow#getName()
	 * @see #getMicroflow()
	 * @generated
	 */
	EAttribute getMicroflow_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imt.bpmn.Microflow#getOwnedElements <em>Owned Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned Elements</em>'.
	 * @see org.imt.bpmn.Microflow#getOwnedElements()
	 * @see #getMicroflow()
	 * @generated
	 */
	EReference getMicroflow_OwnedElements();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imt.bpmn.Microflow#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.imt.bpmn.Microflow#getParameters()
	 * @see #getMicroflow()
	 * @generated
	 */
	EReference getMicroflow_Parameters();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.Microflow#getCurrentNode <em>Current Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Current Node</em>'.
	 * @see org.imt.bpmn.Microflow#getCurrentNode()
	 * @see #getMicroflow()
	 * @generated
	 */
	EReference getMicroflow_CurrentNode();

	/**
	 * Returns the meta object for the reference list '{@link org.imt.bpmn.Microflow#getValuedVariables <em>Valued Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Valued Variables</em>'.
	 * @see org.imt.bpmn.Microflow#getValuedVariables()
	 * @see #getMicroflow()
	 * @generated
	 */
	EReference getMicroflow_ValuedVariables();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.MicroflowElement <em>Microflow Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Microflow Element</em>'.
	 * @see org.imt.bpmn.MicroflowElement
	 * @generated
	 */
	EClass getMicroflowElement();

	/**
	 * Returns the meta object for the reference list '{@link org.imt.bpmn.MicroflowElement#getIncomingFlows <em>Incoming Flows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Flows</em>'.
	 * @see org.imt.bpmn.MicroflowElement#getIncomingFlows()
	 * @see #getMicroflowElement()
	 * @generated
	 */
	EReference getMicroflowElement_IncomingFlows();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imt.bpmn.MicroflowElement#getOutgoingFlows <em>Outgoing Flows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outgoing Flows</em>'.
	 * @see org.imt.bpmn.MicroflowElement#getOutgoingFlows()
	 * @see #getMicroflowElement()
	 * @generated
	 */
	EReference getMicroflowElement_OutgoingFlows();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.MicroflowElement#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.imt.bpmn.MicroflowElement#getLabel()
	 * @see #getMicroflowElement()
	 * @generated
	 */
	EAttribute getMicroflowElement_Label();

	/**
	 * Returns the meta object for the container reference '{@link org.imt.bpmn.MicroflowElement#getOwnerMicroflow <em>Owner Microflow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner Microflow</em>'.
	 * @see org.imt.bpmn.MicroflowElement#getOwnerMicroflow()
	 * @see #getMicroflowElement()
	 * @generated
	 */
	EReference getMicroflowElement_OwnerMicroflow();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.SequenceFlow <em>Sequence Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sequence Flow</em>'.
	 * @see org.imt.bpmn.SequenceFlow
	 * @generated
	 */
	EClass getSequenceFlow();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.SequenceFlow#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Constraint</em>'.
	 * @see org.imt.bpmn.SequenceFlow#getConstraint()
	 * @see #getSequenceFlow()
	 * @generated
	 */
	EAttribute getSequenceFlow_Constraint();

	/**
	 * Returns the meta object for the container reference '{@link org.imt.bpmn.SequenceFlow#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Source</em>'.
	 * @see org.imt.bpmn.SequenceFlow#getSource()
	 * @see #getSequenceFlow()
	 * @generated
	 */
	EReference getSequenceFlow_Source();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.SequenceFlow#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.imt.bpmn.SequenceFlow#getTarget()
	 * @see #getSequenceFlow()
	 * @generated
	 */
	EReference getSequenceFlow_Target();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.Activity <em>Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Activity</em>'.
	 * @see org.imt.bpmn.Activity
	 * @generated
	 */
	EClass getActivity();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.ObjectActivity <em>Object Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Activity</em>'.
	 * @see org.imt.bpmn.ObjectActivity
	 * @generated
	 */
	EClass getObjectActivity();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.ObjectActivity#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entity</em>'.
	 * @see org.imt.bpmn.ObjectActivity#getEntity()
	 * @see #getObjectActivity()
	 * @generated
	 */
	EReference getObjectActivity_Entity();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.CreateObject <em>Create Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Create Object</em>'.
	 * @see org.imt.bpmn.CreateObject
	 * @generated
	 */
	EClass getCreateObject();

	/**
	 * Returns the meta object for the containment reference '{@link org.imt.bpmn.CreateObject#getNewEntity <em>New Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>New Entity</em>'.
	 * @see org.imt.bpmn.CreateObject#getNewEntity()
	 * @see #getCreateObject()
	 * @generated
	 */
	EReference getCreateObject_NewEntity();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.DeleteObject <em>Delete Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Delete Object</em>'.
	 * @see org.imt.bpmn.DeleteObject
	 * @generated
	 */
	EClass getDeleteObject();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.ChangeObject <em>Change Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Change Object</em>'.
	 * @see org.imt.bpmn.ChangeObject
	 * @generated
	 */
	EClass getChangeObject();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.RetrieveObject <em>Retrieve Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Retrieve Object</em>'.
	 * @see org.imt.bpmn.RetrieveObject
	 * @generated
	 */
	EClass getRetrieveObject();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.RetrieveObject#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Constraint</em>'.
	 * @see org.imt.bpmn.RetrieveObject#getConstraint()
	 * @see #getRetrieveObject()
	 * @generated
	 */
	EAttribute getRetrieveObject_Constraint();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.ActionCallActivity <em>Action Call Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Call Activity</em>'.
	 * @see org.imt.bpmn.ActionCallActivity
	 * @generated
	 */
	EClass getActionCallActivity();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.MicroflowCall <em>Microflow Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Microflow Call</em>'.
	 * @see org.imt.bpmn.MicroflowCall
	 * @generated
	 */
	EClass getMicroflowCall();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.MicroflowCall#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.imt.bpmn.MicroflowCall#getTarget()
	 * @see #getMicroflowCall()
	 * @generated
	 */
	EReference getMicroflowCall_Target();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.VariableActivity <em>Variable Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Activity</em>'.
	 * @see org.imt.bpmn.VariableActivity
	 * @generated
	 */
	EClass getVariableActivity();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.CreateVariable <em>Create Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Create Variable</em>'.
	 * @see org.imt.bpmn.CreateVariable
	 * @generated
	 */
	EClass getCreateVariable();

	/**
	 * Returns the meta object for the containment reference '{@link org.imt.bpmn.CreateVariable#getNewVariable <em>New Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>New Variable</em>'.
	 * @see org.imt.bpmn.CreateVariable#getNewVariable()
	 * @see #getCreateVariable()
	 * @generated
	 */
	EReference getCreateVariable_NewVariable();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.ChangeVariable <em>Change Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Change Variable</em>'.
	 * @see org.imt.bpmn.ChangeVariable
	 * @generated
	 */
	EClass getChangeVariable();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.ChangeVariable#getTargetVariable <em>Target Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Variable</em>'.
	 * @see org.imt.bpmn.ChangeVariable#getTargetVariable()
	 * @see #getChangeVariable()
	 * @generated
	 */
	EReference getChangeVariable_TargetVariable();

	/**
	 * Returns the meta object for the containment reference '{@link org.imt.bpmn.ChangeVariable#getNewValue <em>New Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>New Value</em>'.
	 * @see org.imt.bpmn.ChangeVariable#getNewValue()
	 * @see #getChangeVariable()
	 * @generated
	 */
	EReference getChangeVariable_NewValue();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.ClientActivity <em>Client Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Client Activity</em>'.
	 * @see org.imt.bpmn.ClientActivity
	 * @generated
	 */
	EClass getClientActivity();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.ShowMessage <em>Show Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Show Message</em>'.
	 * @see org.imt.bpmn.ShowMessage
	 * @generated
	 */
	EClass getShowMessage();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.ShowMessage#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.imt.bpmn.ShowMessage#getMessage()
	 * @see #getShowMessage()
	 * @generated
	 */
	EAttribute getShowMessage_Message();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event</em>'.
	 * @see org.imt.bpmn.Event
	 * @generated
	 */
	EClass getEvent();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.StartEvent <em>Start Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Start Event</em>'.
	 * @see org.imt.bpmn.StartEvent
	 * @generated
	 */
	EClass getStartEvent();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.EndEvent <em>End Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>End Event</em>'.
	 * @see org.imt.bpmn.EndEvent
	 * @generated
	 */
	EClass getEndEvent();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.EndEvent#getReturnVariable <em>Return Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Return Variable</em>'.
	 * @see org.imt.bpmn.EndEvent#getReturnVariable()
	 * @see #getEndEvent()
	 * @generated
	 */
	EReference getEndEvent_ReturnVariable();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see org.imt.bpmn.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.Variable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.imt.bpmn.Variable#getName()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Name();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity</em>'.
	 * @see org.imt.bpmn.Entity
	 * @generated
	 */
	EClass getEntity();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imt.bpmn.Entity#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.imt.bpmn.Entity#getAttributes()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_Attributes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.imt.bpmn.Entity#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>References</em>'.
	 * @see org.imt.bpmn.Entity#getReferences()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_References();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.Reference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reference</em>'.
	 * @see org.imt.bpmn.Reference
	 * @generated
	 */
	EClass getReference();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.Reference#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.imt.bpmn.Reference#getName()
	 * @see #getReference()
	 * @generated
	 */
	EAttribute getReference_Name();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.Reference#getReferencedEntity <em>Referenced Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Referenced Entity</em>'.
	 * @see org.imt.bpmn.Reference#getReferencedEntity()
	 * @see #getReference()
	 * @generated
	 */
	EReference getReference_ReferencedEntity();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.BasicVariable <em>Basic Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic Variable</em>'.
	 * @see org.imt.bpmn.BasicVariable
	 * @generated
	 */
	EClass getBasicVariable();

	/**
	 * Returns the meta object for the containment reference '{@link org.imt.bpmn.BasicVariable#getValueObject <em>Value Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value Object</em>'.
	 * @see org.imt.bpmn.BasicVariable#getValueObject()
	 * @see #getBasicVariable()
	 * @generated
	 */
	EReference getBasicVariable_ValueObject();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.IntegerVariable <em>Integer Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Variable</em>'.
	 * @see org.imt.bpmn.IntegerVariable
	 * @generated
	 */
	EClass getIntegerVariable();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.BooleanVariable <em>Boolean Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Variable</em>'.
	 * @see org.imt.bpmn.BooleanVariable
	 * @generated
	 */
	EClass getBooleanVariable();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.StringVariable <em>String Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Variable</em>'.
	 * @see org.imt.bpmn.StringVariable
	 * @generated
	 */
	EClass getStringVariable();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value</em>'.
	 * @see org.imt.bpmn.Value
	 * @generated
	 */
	EClass getValue();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.IntegerValue <em>Integer Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Value</em>'.
	 * @see org.imt.bpmn.IntegerValue
	 * @generated
	 */
	EClass getIntegerValue();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.IntegerValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.imt.bpmn.IntegerValue#getValue()
	 * @see #getIntegerValue()
	 * @generated
	 */
	EAttribute getIntegerValue_Value();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.BooleanValue <em>Boolean Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Value</em>'.
	 * @see org.imt.bpmn.BooleanValue
	 * @generated
	 */
	EClass getBooleanValue();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.BooleanValue#isValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.imt.bpmn.BooleanValue#isValue()
	 * @see #getBooleanValue()
	 * @generated
	 */
	EAttribute getBooleanValue_Value();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.StringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Value</em>'.
	 * @see org.imt.bpmn.StringValue
	 * @generated
	 */
	EClass getStringValue();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.StringValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.imt.bpmn.StringValue#getValue()
	 * @see #getStringValue()
	 * @generated
	 */
	EAttribute getStringValue_Value();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.Decision <em>Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Decision</em>'.
	 * @see org.imt.bpmn.Decision
	 * @generated
	 */
	EClass getDecision();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.ForkDecision <em>Fork Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fork Decision</em>'.
	 * @see org.imt.bpmn.ForkDecision
	 * @generated
	 */
	EClass getForkDecision();

	/**
	 * Returns the meta object for the containment reference '{@link org.imt.bpmn.ForkDecision#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.imt.bpmn.ForkDecision#getExpression()
	 * @see #getForkDecision()
	 * @generated
	 */
	EReference getForkDecision_Expression();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.MergeDecision <em>Merge Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Merge Decision</em>'.
	 * @see org.imt.bpmn.MergeDecision
	 * @generated
	 */
	EClass getMergeDecision();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see org.imt.bpmn.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.IntegerComparisonExpression <em>Integer Comparison Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Comparison Expression</em>'.
	 * @see org.imt.bpmn.IntegerComparisonExpression
	 * @generated
	 */
	EClass getIntegerComparisonExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.IntegerComparisonExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.imt.bpmn.IntegerComparisonExpression#getOperator()
	 * @see #getIntegerComparisonExpression()
	 * @generated
	 */
	EAttribute getIntegerComparisonExpression_Operator();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.IntegerComparisonExpression#getOperand1 <em>Operand1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Operand1</em>'.
	 * @see org.imt.bpmn.IntegerComparisonExpression#getOperand1()
	 * @see #getIntegerComparisonExpression()
	 * @generated
	 */
	EReference getIntegerComparisonExpression_Operand1();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.IntegerComparisonExpression#getOperand2 <em>Operand2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Operand2</em>'.
	 * @see org.imt.bpmn.IntegerComparisonExpression#getOperand2()
	 * @see #getIntegerComparisonExpression()
	 * @generated
	 */
	EReference getIntegerComparisonExpression_Operand2();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.StringComparisonExpression <em>String Comparison Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Comparison Expression</em>'.
	 * @see org.imt.bpmn.StringComparisonExpression
	 * @generated
	 */
	EClass getStringComparisonExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.StringComparisonExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.imt.bpmn.StringComparisonExpression#getOperator()
	 * @see #getStringComparisonExpression()
	 * @generated
	 */
	EAttribute getStringComparisonExpression_Operator();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.StringComparisonExpression#getOperand1 <em>Operand1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Operand1</em>'.
	 * @see org.imt.bpmn.StringComparisonExpression#getOperand1()
	 * @see #getStringComparisonExpression()
	 * @generated
	 */
	EReference getStringComparisonExpression_Operand1();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.StringComparisonExpression#getOperand2 <em>Operand2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Operand2</em>'.
	 * @see org.imt.bpmn.StringComparisonExpression#getOperand2()
	 * @see #getStringComparisonExpression()
	 * @generated
	 */
	EReference getStringComparisonExpression_Operand2();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.BooleanExpression <em>Boolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Expression</em>'.
	 * @see org.imt.bpmn.BooleanExpression
	 * @generated
	 */
	EClass getBooleanExpression();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.BooleanBinaryExpression <em>Boolean Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Binary Expression</em>'.
	 * @see org.imt.bpmn.BooleanBinaryExpression
	 * @generated
	 */
	EClass getBooleanBinaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.BooleanBinaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.imt.bpmn.BooleanBinaryExpression#getOperator()
	 * @see #getBooleanBinaryExpression()
	 * @generated
	 */
	EAttribute getBooleanBinaryExpression_Operator();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.BooleanBinaryExpression#getOperand1 <em>Operand1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Operand1</em>'.
	 * @see org.imt.bpmn.BooleanBinaryExpression#getOperand1()
	 * @see #getBooleanBinaryExpression()
	 * @generated
	 */
	EReference getBooleanBinaryExpression_Operand1();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.BooleanBinaryExpression#getOperand2 <em>Operand2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Operand2</em>'.
	 * @see org.imt.bpmn.BooleanBinaryExpression#getOperand2()
	 * @see #getBooleanBinaryExpression()
	 * @generated
	 */
	EReference getBooleanBinaryExpression_Operand2();

	/**
	 * Returns the meta object for class '{@link org.imt.bpmn.BooleanUnaryExpression <em>Boolean Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Unary Expression</em>'.
	 * @see org.imt.bpmn.BooleanUnaryExpression
	 * @generated
	 */
	EClass getBooleanUnaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.imt.bpmn.BooleanUnaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.imt.bpmn.BooleanUnaryExpression#getOperator()
	 * @see #getBooleanUnaryExpression()
	 * @generated
	 */
	EAttribute getBooleanUnaryExpression_Operator();

	/**
	 * Returns the meta object for the reference '{@link org.imt.bpmn.BooleanUnaryExpression#getOperand <em>Operand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Operand</em>'.
	 * @see org.imt.bpmn.BooleanUnaryExpression#getOperand()
	 * @see #getBooleanUnaryExpression()
	 * @generated
	 */
	EReference getBooleanUnaryExpression_Operand();

	/**
	 * Returns the meta object for enum '{@link org.imt.bpmn.BooleanBinaryOperator <em>Boolean Binary Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Boolean Binary Operator</em>'.
	 * @see org.imt.bpmn.BooleanBinaryOperator
	 * @generated
	 */
	EEnum getBooleanBinaryOperator();

	/**
	 * Returns the meta object for enum '{@link org.imt.bpmn.BooleanUnaryOperator <em>Boolean Unary Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Boolean Unary Operator</em>'.
	 * @see org.imt.bpmn.BooleanUnaryOperator
	 * @generated
	 */
	EEnum getBooleanUnaryOperator();

	/**
	 * Returns the meta object for enum '{@link org.imt.bpmn.IntegerComparisonOperator <em>Integer Comparison Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Integer Comparison Operator</em>'.
	 * @see org.imt.bpmn.IntegerComparisonOperator
	 * @generated
	 */
	EEnum getIntegerComparisonOperator();

	/**
	 * Returns the meta object for enum '{@link org.imt.bpmn.StringComparisonOperator <em>String Comparison Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>String Comparison Operator</em>'.
	 * @see org.imt.bpmn.StringComparisonOperator
	 * @generated
	 */
	EEnum getStringComparisonOperator();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	bpmnFactory getbpmnFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.MicroflowImpl <em>Microflow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.MicroflowImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getMicroflow()
		 * @generated
		 */
		EClass MICROFLOW = eINSTANCE.getMicroflow();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MICROFLOW__NAME = eINSTANCE.getMicroflow_Name();

		/**
		 * The meta object literal for the '<em><b>Owned Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROFLOW__OWNED_ELEMENTS = eINSTANCE.getMicroflow_OwnedElements();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROFLOW__PARAMETERS = eINSTANCE.getMicroflow_Parameters();

		/**
		 * The meta object literal for the '<em><b>Current Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROFLOW__CURRENT_NODE = eINSTANCE.getMicroflow_CurrentNode();

		/**
		 * The meta object literal for the '<em><b>Valued Variables</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROFLOW__VALUED_VARIABLES = eINSTANCE.getMicroflow_ValuedVariables();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.MicroflowElementImpl <em>Microflow Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.MicroflowElementImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getMicroflowElement()
		 * @generated
		 */
		EClass MICROFLOW_ELEMENT = eINSTANCE.getMicroflowElement();

		/**
		 * The meta object literal for the '<em><b>Incoming Flows</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROFLOW_ELEMENT__INCOMING_FLOWS = eINSTANCE.getMicroflowElement_IncomingFlows();

		/**
		 * The meta object literal for the '<em><b>Outgoing Flows</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROFLOW_ELEMENT__OUTGOING_FLOWS = eINSTANCE.getMicroflowElement_OutgoingFlows();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MICROFLOW_ELEMENT__LABEL = eINSTANCE.getMicroflowElement_Label();

		/**
		 * The meta object literal for the '<em><b>Owner Microflow</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROFLOW_ELEMENT__OWNER_MICROFLOW = eINSTANCE.getMicroflowElement_OwnerMicroflow();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.SequenceFlowImpl <em>Sequence Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.SequenceFlowImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getSequenceFlow()
		 * @generated
		 */
		EClass SEQUENCE_FLOW = eINSTANCE.getSequenceFlow();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEQUENCE_FLOW__CONSTRAINT = eINSTANCE.getSequenceFlow_Constraint();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEQUENCE_FLOW__SOURCE = eINSTANCE.getSequenceFlow_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEQUENCE_FLOW__TARGET = eINSTANCE.getSequenceFlow_Target();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.ActivityImpl <em>Activity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.ActivityImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getActivity()
		 * @generated
		 */
		EClass ACTIVITY = eINSTANCE.getActivity();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.ObjectActivityImpl <em>Object Activity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.ObjectActivityImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getObjectActivity()
		 * @generated
		 */
		EClass OBJECT_ACTIVITY = eINSTANCE.getObjectActivity();

		/**
		 * The meta object literal for the '<em><b>Entity</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_ACTIVITY__ENTITY = eINSTANCE.getObjectActivity_Entity();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.CreateObjectImpl <em>Create Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.CreateObjectImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getCreateObject()
		 * @generated
		 */
		EClass CREATE_OBJECT = eINSTANCE.getCreateObject();

		/**
		 * The meta object literal for the '<em><b>New Entity</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CREATE_OBJECT__NEW_ENTITY = eINSTANCE.getCreateObject_NewEntity();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.DeleteObjectImpl <em>Delete Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.DeleteObjectImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getDeleteObject()
		 * @generated
		 */
		EClass DELETE_OBJECT = eINSTANCE.getDeleteObject();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.ChangeObjectImpl <em>Change Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.ChangeObjectImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getChangeObject()
		 * @generated
		 */
		EClass CHANGE_OBJECT = eINSTANCE.getChangeObject();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.RetrieveObjectImpl <em>Retrieve Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.RetrieveObjectImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getRetrieveObject()
		 * @generated
		 */
		EClass RETRIEVE_OBJECT = eINSTANCE.getRetrieveObject();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RETRIEVE_OBJECT__CONSTRAINT = eINSTANCE.getRetrieveObject_Constraint();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.ActionCallActivityImpl <em>Action Call Activity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.ActionCallActivityImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getActionCallActivity()
		 * @generated
		 */
		EClass ACTION_CALL_ACTIVITY = eINSTANCE.getActionCallActivity();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.MicroflowCallImpl <em>Microflow Call</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.MicroflowCallImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getMicroflowCall()
		 * @generated
		 */
		EClass MICROFLOW_CALL = eINSTANCE.getMicroflowCall();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROFLOW_CALL__TARGET = eINSTANCE.getMicroflowCall_Target();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.VariableActivityImpl <em>Variable Activity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.VariableActivityImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getVariableActivity()
		 * @generated
		 */
		EClass VARIABLE_ACTIVITY = eINSTANCE.getVariableActivity();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.CreateVariableImpl <em>Create Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.CreateVariableImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getCreateVariable()
		 * @generated
		 */
		EClass CREATE_VARIABLE = eINSTANCE.getCreateVariable();

		/**
		 * The meta object literal for the '<em><b>New Variable</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CREATE_VARIABLE__NEW_VARIABLE = eINSTANCE.getCreateVariable_NewVariable();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.ChangeVariableImpl <em>Change Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.ChangeVariableImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getChangeVariable()
		 * @generated
		 */
		EClass CHANGE_VARIABLE = eINSTANCE.getChangeVariable();

		/**
		 * The meta object literal for the '<em><b>Target Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_VARIABLE__TARGET_VARIABLE = eINSTANCE.getChangeVariable_TargetVariable();

		/**
		 * The meta object literal for the '<em><b>New Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_VARIABLE__NEW_VALUE = eINSTANCE.getChangeVariable_NewValue();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.ClientActivityImpl <em>Client Activity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.ClientActivityImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getClientActivity()
		 * @generated
		 */
		EClass CLIENT_ACTIVITY = eINSTANCE.getClientActivity();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.ShowMessageImpl <em>Show Message</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.ShowMessageImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getShowMessage()
		 * @generated
		 */
		EClass SHOW_MESSAGE = eINSTANCE.getShowMessage();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHOW_MESSAGE__MESSAGE = eINSTANCE.getShowMessage_Message();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.EventImpl <em>Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.EventImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getEvent()
		 * @generated
		 */
		EClass EVENT = eINSTANCE.getEvent();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.StartEventImpl <em>Start Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.StartEventImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getStartEvent()
		 * @generated
		 */
		EClass START_EVENT = eINSTANCE.getStartEvent();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.EndEventImpl <em>End Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.EndEventImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getEndEvent()
		 * @generated
		 */
		EClass END_EVENT = eINSTANCE.getEndEvent();

		/**
		 * The meta object literal for the '<em><b>Return Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference END_EVENT__RETURN_VARIABLE = eINSTANCE.getEndEvent_ReturnVariable();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.VariableImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__NAME = eINSTANCE.getVariable_Name();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.EntityImpl <em>Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.EntityImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getEntity()
		 * @generated
		 */
		EClass ENTITY = eINSTANCE.getEntity();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY__ATTRIBUTES = eINSTANCE.getEntity_Attributes();

		/**
		 * The meta object literal for the '<em><b>References</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY__REFERENCES = eINSTANCE.getEntity_References();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.ReferenceImpl <em>Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.ReferenceImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getReference()
		 * @generated
		 */
		EClass REFERENCE = eINSTANCE.getReference();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFERENCE__NAME = eINSTANCE.getReference_Name();

		/**
		 * The meta object literal for the '<em><b>Referenced Entity</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENCE__REFERENCED_ENTITY = eINSTANCE.getReference_ReferencedEntity();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.BasicVariableImpl <em>Basic Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.BasicVariableImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBasicVariable()
		 * @generated
		 */
		EClass BASIC_VARIABLE = eINSTANCE.getBasicVariable();

		/**
		 * The meta object literal for the '<em><b>Value Object</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASIC_VARIABLE__VALUE_OBJECT = eINSTANCE.getBasicVariable_ValueObject();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.IntegerVariableImpl <em>Integer Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.IntegerVariableImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getIntegerVariable()
		 * @generated
		 */
		EClass INTEGER_VARIABLE = eINSTANCE.getIntegerVariable();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.BooleanVariableImpl <em>Boolean Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.BooleanVariableImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanVariable()
		 * @generated
		 */
		EClass BOOLEAN_VARIABLE = eINSTANCE.getBooleanVariable();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.StringVariableImpl <em>String Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.StringVariableImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getStringVariable()
		 * @generated
		 */
		EClass STRING_VARIABLE = eINSTANCE.getStringVariable();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.ValueImpl <em>Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.ValueImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getValue()
		 * @generated
		 */
		EClass VALUE = eINSTANCE.getValue();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.IntegerValueImpl <em>Integer Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.IntegerValueImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getIntegerValue()
		 * @generated
		 */
		EClass INTEGER_VALUE = eINSTANCE.getIntegerValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_VALUE__VALUE = eINSTANCE.getIntegerValue_Value();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.BooleanValueImpl <em>Boolean Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.BooleanValueImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanValue()
		 * @generated
		 */
		EClass BOOLEAN_VALUE = eINSTANCE.getBooleanValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_VALUE__VALUE = eINSTANCE.getBooleanValue_Value();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.StringValueImpl <em>String Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.StringValueImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getStringValue()
		 * @generated
		 */
		EClass STRING_VALUE = eINSTANCE.getStringValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_VALUE__VALUE = eINSTANCE.getStringValue_Value();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.DecisionImpl <em>Decision</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.DecisionImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getDecision()
		 * @generated
		 */
		EClass DECISION = eINSTANCE.getDecision();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.ForkDecisionImpl <em>Fork Decision</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.ForkDecisionImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getForkDecision()
		 * @generated
		 */
		EClass FORK_DECISION = eINSTANCE.getForkDecision();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FORK_DECISION__EXPRESSION = eINSTANCE.getForkDecision_Expression();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.MergeDecisionImpl <em>Merge Decision</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.MergeDecisionImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getMergeDecision()
		 * @generated
		 */
		EClass MERGE_DECISION = eINSTANCE.getMergeDecision();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.ExpressionImpl <em>Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.ExpressionImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.IntegerComparisonExpressionImpl <em>Integer Comparison Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.IntegerComparisonExpressionImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getIntegerComparisonExpression()
		 * @generated
		 */
		EClass INTEGER_COMPARISON_EXPRESSION = eINSTANCE.getIntegerComparisonExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_COMPARISON_EXPRESSION__OPERATOR = eINSTANCE.getIntegerComparisonExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Operand1</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTEGER_COMPARISON_EXPRESSION__OPERAND1 = eINSTANCE.getIntegerComparisonExpression_Operand1();

		/**
		 * The meta object literal for the '<em><b>Operand2</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTEGER_COMPARISON_EXPRESSION__OPERAND2 = eINSTANCE.getIntegerComparisonExpression_Operand2();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.StringComparisonExpressionImpl <em>String Comparison Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.StringComparisonExpressionImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getStringComparisonExpression()
		 * @generated
		 */
		EClass STRING_COMPARISON_EXPRESSION = eINSTANCE.getStringComparisonExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_COMPARISON_EXPRESSION__OPERATOR = eINSTANCE.getStringComparisonExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Operand1</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_COMPARISON_EXPRESSION__OPERAND1 = eINSTANCE.getStringComparisonExpression_Operand1();

		/**
		 * The meta object literal for the '<em><b>Operand2</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_COMPARISON_EXPRESSION__OPERAND2 = eINSTANCE.getStringComparisonExpression_Operand2();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.BooleanExpressionImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanExpression()
		 * @generated
		 */
		EClass BOOLEAN_EXPRESSION = eINSTANCE.getBooleanExpression();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.BooleanBinaryExpressionImpl <em>Boolean Binary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.BooleanBinaryExpressionImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanBinaryExpression()
		 * @generated
		 */
		EClass BOOLEAN_BINARY_EXPRESSION = eINSTANCE.getBooleanBinaryExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_BINARY_EXPRESSION__OPERATOR = eINSTANCE.getBooleanBinaryExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Operand1</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOLEAN_BINARY_EXPRESSION__OPERAND1 = eINSTANCE.getBooleanBinaryExpression_Operand1();

		/**
		 * The meta object literal for the '<em><b>Operand2</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOLEAN_BINARY_EXPRESSION__OPERAND2 = eINSTANCE.getBooleanBinaryExpression_Operand2();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.impl.BooleanUnaryExpressionImpl <em>Boolean Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.impl.BooleanUnaryExpressionImpl
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanUnaryExpression()
		 * @generated
		 */
		EClass BOOLEAN_UNARY_EXPRESSION = eINSTANCE.getBooleanUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_UNARY_EXPRESSION__OPERATOR = eINSTANCE.getBooleanUnaryExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Operand</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOLEAN_UNARY_EXPRESSION__OPERAND = eINSTANCE.getBooleanUnaryExpression_Operand();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.BooleanBinaryOperator <em>Boolean Binary Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.BooleanBinaryOperator
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanBinaryOperator()
		 * @generated
		 */
		EEnum BOOLEAN_BINARY_OPERATOR = eINSTANCE.getBooleanBinaryOperator();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.BooleanUnaryOperator <em>Boolean Unary Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.BooleanUnaryOperator
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getBooleanUnaryOperator()
		 * @generated
		 */
		EEnum BOOLEAN_UNARY_OPERATOR = eINSTANCE.getBooleanUnaryOperator();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.IntegerComparisonOperator <em>Integer Comparison Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.IntegerComparisonOperator
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getIntegerComparisonOperator()
		 * @generated
		 */
		EEnum INTEGER_COMPARISON_OPERATOR = eINSTANCE.getIntegerComparisonOperator();

		/**
		 * The meta object literal for the '{@link org.imt.bpmn.StringComparisonOperator <em>String Comparison Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.imt.bpmn.StringComparisonOperator
		 * @see org.imt.bpmn.impl.bpmnPackageImpl#getStringComparisonOperator()
		 * @generated
		 */
		EEnum STRING_COMPARISON_OPERATOR = eINSTANCE.getStringComparisonOperator();

	}

} //bpmnPackage
