/**
 */
package org.imt.minijava.xminiJava.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.imt.minijava.xminiJava.Call;
import org.imt.minijava.xminiJava.Context;
import org.imt.minijava.xminiJava.Frame;
import org.imt.minijava.xminiJava.ObjectInstance;
import org.imt.minijava.xminiJava.Value;
import org.imt.minijava.xminiJava.XminiJavaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Frame</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.imt.minijava.xminiJava.impl.FrameImpl#getCall <em>Call</em>}</li>
 *   <li>{@link org.imt.minijava.xminiJava.impl.FrameImpl#getInstance <em>Instance</em>}</li>
 *   <li>{@link org.imt.minijava.xminiJava.impl.FrameImpl#getChildFrame <em>Child Frame</em>}</li>
 *   <li>{@link org.imt.minijava.xminiJava.impl.FrameImpl#getParentFrame <em>Parent Frame</em>}</li>
 *   <li>{@link org.imt.minijava.xminiJava.impl.FrameImpl#getRootContext <em>Root Context</em>}</li>
 *   <li>{@link org.imt.minijava.xminiJava.impl.FrameImpl#getReturnValue <em>Return Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FrameImpl extends MinimalEObjectImpl.Container implements Frame {
	/**
	 * The cached value of the '{@link #getCall() <em>Call</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCall()
	 * @generated
	 * @ordered
	 */
	protected Call call;

	/**
	 * The cached value of the '{@link #getInstance() <em>Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstance()
	 * @generated
	 * @ordered
	 */
	protected ObjectInstance instance;

	/**
	 * The cached value of the '{@link #getChildFrame() <em>Child Frame</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildFrame()
	 * @generated
	 * @ordered
	 */
	protected Frame childFrame;

	/**
	 * The cached value of the '{@link #getRootContext() <em>Root Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootContext()
	 * @generated
	 * @ordered
	 */
	protected Context rootContext;

	/**
	 * The cached value of the '{@link #getReturnValue() <em>Return Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnValue()
	 * @generated
	 * @ordered
	 */
	protected Value returnValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FrameImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return XminiJavaPackage.Literals.FRAME;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Call getCall() {
		return call;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCall(Call newCall, NotificationChain msgs) {
		Call oldCall = call;
		call = newCall;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XminiJavaPackage.FRAME__CALL, oldCall, newCall);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCall(Call newCall) {
		if (newCall != call) {
			NotificationChain msgs = null;
			if (call != null)
				msgs = ((InternalEObject)call).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XminiJavaPackage.FRAME__CALL, null, msgs);
			if (newCall != null)
				msgs = ((InternalEObject)newCall).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XminiJavaPackage.FRAME__CALL, null, msgs);
			msgs = basicSetCall(newCall, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XminiJavaPackage.FRAME__CALL, newCall, newCall));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectInstance getInstance() {
		if (instance != null && instance.eIsProxy()) {
			InternalEObject oldInstance = (InternalEObject)instance;
			instance = (ObjectInstance)eResolveProxy(oldInstance);
			if (instance != oldInstance) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, XminiJavaPackage.FRAME__INSTANCE, oldInstance, instance));
			}
		}
		return instance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectInstance basicGetInstance() {
		return instance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstance(ObjectInstance newInstance) {
		ObjectInstance oldInstance = instance;
		instance = newInstance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XminiJavaPackage.FRAME__INSTANCE, oldInstance, instance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Frame getChildFrame() {
		return childFrame;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChildFrame(Frame newChildFrame, NotificationChain msgs) {
		Frame oldChildFrame = childFrame;
		childFrame = newChildFrame;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XminiJavaPackage.FRAME__CHILD_FRAME, oldChildFrame, newChildFrame);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChildFrame(Frame newChildFrame) {
		if (newChildFrame != childFrame) {
			NotificationChain msgs = null;
			if (childFrame != null)
				msgs = ((InternalEObject)childFrame).eInverseRemove(this, XminiJavaPackage.FRAME__PARENT_FRAME, Frame.class, msgs);
			if (newChildFrame != null)
				msgs = ((InternalEObject)newChildFrame).eInverseAdd(this, XminiJavaPackage.FRAME__PARENT_FRAME, Frame.class, msgs);
			msgs = basicSetChildFrame(newChildFrame, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XminiJavaPackage.FRAME__CHILD_FRAME, newChildFrame, newChildFrame));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Frame getParentFrame() {
		if (eContainerFeatureID() != XminiJavaPackage.FRAME__PARENT_FRAME) return null;
		return (Frame)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentFrame(Frame newParentFrame, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParentFrame, XminiJavaPackage.FRAME__PARENT_FRAME, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentFrame(Frame newParentFrame) {
		if (newParentFrame != eInternalContainer() || (eContainerFeatureID() != XminiJavaPackage.FRAME__PARENT_FRAME && newParentFrame != null)) {
			if (EcoreUtil.isAncestor(this, newParentFrame))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentFrame != null)
				msgs = ((InternalEObject)newParentFrame).eInverseAdd(this, XminiJavaPackage.FRAME__CHILD_FRAME, Frame.class, msgs);
			msgs = basicSetParentFrame(newParentFrame, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XminiJavaPackage.FRAME__PARENT_FRAME, newParentFrame, newParentFrame));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Context getRootContext() {
		return rootContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRootContext(Context newRootContext, NotificationChain msgs) {
		Context oldRootContext = rootContext;
		rootContext = newRootContext;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XminiJavaPackage.FRAME__ROOT_CONTEXT, oldRootContext, newRootContext);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootContext(Context newRootContext) {
		if (newRootContext != rootContext) {
			NotificationChain msgs = null;
			if (rootContext != null)
				msgs = ((InternalEObject)rootContext).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XminiJavaPackage.FRAME__ROOT_CONTEXT, null, msgs);
			if (newRootContext != null)
				msgs = ((InternalEObject)newRootContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XminiJavaPackage.FRAME__ROOT_CONTEXT, null, msgs);
			msgs = basicSetRootContext(newRootContext, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XminiJavaPackage.FRAME__ROOT_CONTEXT, newRootContext, newRootContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value getReturnValue() {
		return returnValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReturnValue(Value newReturnValue, NotificationChain msgs) {
		Value oldReturnValue = returnValue;
		returnValue = newReturnValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XminiJavaPackage.FRAME__RETURN_VALUE, oldReturnValue, newReturnValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnValue(Value newReturnValue) {
		if (newReturnValue != returnValue) {
			NotificationChain msgs = null;
			if (returnValue != null)
				msgs = ((InternalEObject)returnValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XminiJavaPackage.FRAME__RETURN_VALUE, null, msgs);
			if (newReturnValue != null)
				msgs = ((InternalEObject)newReturnValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XminiJavaPackage.FRAME__RETURN_VALUE, null, msgs);
			msgs = basicSetReturnValue(newReturnValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XminiJavaPackage.FRAME__RETURN_VALUE, newReturnValue, newReturnValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case XminiJavaPackage.FRAME__CHILD_FRAME:
				if (childFrame != null)
					msgs = ((InternalEObject)childFrame).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XminiJavaPackage.FRAME__CHILD_FRAME, null, msgs);
				return basicSetChildFrame((Frame)otherEnd, msgs);
			case XminiJavaPackage.FRAME__PARENT_FRAME:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParentFrame((Frame)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case XminiJavaPackage.FRAME__CALL:
				return basicSetCall(null, msgs);
			case XminiJavaPackage.FRAME__CHILD_FRAME:
				return basicSetChildFrame(null, msgs);
			case XminiJavaPackage.FRAME__PARENT_FRAME:
				return basicSetParentFrame(null, msgs);
			case XminiJavaPackage.FRAME__ROOT_CONTEXT:
				return basicSetRootContext(null, msgs);
			case XminiJavaPackage.FRAME__RETURN_VALUE:
				return basicSetReturnValue(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case XminiJavaPackage.FRAME__PARENT_FRAME:
				return eInternalContainer().eInverseRemove(this, XminiJavaPackage.FRAME__CHILD_FRAME, Frame.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case XminiJavaPackage.FRAME__CALL:
				return getCall();
			case XminiJavaPackage.FRAME__INSTANCE:
				if (resolve) return getInstance();
				return basicGetInstance();
			case XminiJavaPackage.FRAME__CHILD_FRAME:
				return getChildFrame();
			case XminiJavaPackage.FRAME__PARENT_FRAME:
				return getParentFrame();
			case XminiJavaPackage.FRAME__ROOT_CONTEXT:
				return getRootContext();
			case XminiJavaPackage.FRAME__RETURN_VALUE:
				return getReturnValue();
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
			case XminiJavaPackage.FRAME__CALL:
				setCall((Call)newValue);
				return;
			case XminiJavaPackage.FRAME__INSTANCE:
				setInstance((ObjectInstance)newValue);
				return;
			case XminiJavaPackage.FRAME__CHILD_FRAME:
				setChildFrame((Frame)newValue);
				return;
			case XminiJavaPackage.FRAME__PARENT_FRAME:
				setParentFrame((Frame)newValue);
				return;
			case XminiJavaPackage.FRAME__ROOT_CONTEXT:
				setRootContext((Context)newValue);
				return;
			case XminiJavaPackage.FRAME__RETURN_VALUE:
				setReturnValue((Value)newValue);
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
			case XminiJavaPackage.FRAME__CALL:
				setCall((Call)null);
				return;
			case XminiJavaPackage.FRAME__INSTANCE:
				setInstance((ObjectInstance)null);
				return;
			case XminiJavaPackage.FRAME__CHILD_FRAME:
				setChildFrame((Frame)null);
				return;
			case XminiJavaPackage.FRAME__PARENT_FRAME:
				setParentFrame((Frame)null);
				return;
			case XminiJavaPackage.FRAME__ROOT_CONTEXT:
				setRootContext((Context)null);
				return;
			case XminiJavaPackage.FRAME__RETURN_VALUE:
				setReturnValue((Value)null);
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
			case XminiJavaPackage.FRAME__CALL:
				return call != null;
			case XminiJavaPackage.FRAME__INSTANCE:
				return instance != null;
			case XminiJavaPackage.FRAME__CHILD_FRAME:
				return childFrame != null;
			case XminiJavaPackage.FRAME__PARENT_FRAME:
				return getParentFrame() != null;
			case XminiJavaPackage.FRAME__ROOT_CONTEXT:
				return rootContext != null;
			case XminiJavaPackage.FRAME__RETURN_VALUE:
				return returnValue != null;
		}
		return super.eIsSet(featureID);
	}

} //FrameImpl
