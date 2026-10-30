/**
 */
package uschema.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import uschema.Aggregate;
import uschema.Feature;
import uschema.SchemaType;
import uschema.UschemaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Schema Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uschema.impl.SchemaTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link uschema.impl.SchemaTypeImpl#getParents <em>Parents</em>}</li>
 *   <li>{@link uschema.impl.SchemaTypeImpl#getFeatures <em>Features</em>}</li>
 *   <li>{@link uschema.impl.SchemaTypeImpl#getAggregate <em>Aggregate</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SchemaTypeImpl extends MinimalEObjectImpl.Container implements SchemaType {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParents() <em>Parents</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParents()
	 * @generated
	 * @ordered
	 */
	protected EList<SchemaType> parents;

	/**
	 * The cached value of the '{@link #getFeatures() <em>Features</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatures()
	 * @generated
	 * @ordered
	 */
	protected EList<Feature> features;

	/**
	 * The cached value of the '{@link #getAggregate() <em>Aggregate</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAggregate()
	 * @generated
	 * @ordered
	 */
	protected Aggregate aggregate;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SchemaTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UschemaPackage.Literals.SCHEMA_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UschemaPackage.SCHEMA_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SchemaType> getParents() {
		if (parents == null) {
			parents = new EObjectResolvingEList<SchemaType>(SchemaType.class, this, UschemaPackage.SCHEMA_TYPE__PARENTS);
		}
		return parents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Feature> getFeatures() {
		if (features == null) {
			features = new EObjectContainmentWithInverseEList<Feature>(Feature.class, this, UschemaPackage.SCHEMA_TYPE__FEATURES, UschemaPackage.FEATURE__OWNER);
		}
		return features;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Aggregate getAggregate() {
		if (aggregate != null && aggregate.eIsProxy()) {
			InternalEObject oldAggregate = (InternalEObject)aggregate;
			aggregate = (Aggregate)eResolveProxy(oldAggregate);
			if (aggregate != oldAggregate) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UschemaPackage.SCHEMA_TYPE__AGGREGATE, oldAggregate, aggregate));
			}
		}
		return aggregate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Aggregate basicGetAggregate() {
		return aggregate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAggregate(Aggregate newAggregate, NotificationChain msgs) {
		Aggregate oldAggregate = aggregate;
		aggregate = newAggregate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UschemaPackage.SCHEMA_TYPE__AGGREGATE, oldAggregate, newAggregate);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAggregate(Aggregate newAggregate) {
		if (newAggregate != aggregate) {
			NotificationChain msgs = null;
			if (aggregate != null)
				msgs = ((InternalEObject)aggregate).eInverseRemove(this, UschemaPackage.AGGREGATE__SPECIFIED_BY, Aggregate.class, msgs);
			if (newAggregate != null)
				msgs = ((InternalEObject)newAggregate).eInverseAdd(this, UschemaPackage.AGGREGATE__SPECIFIED_BY, Aggregate.class, msgs);
			msgs = basicSetAggregate(newAggregate, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UschemaPackage.SCHEMA_TYPE__AGGREGATE, newAggregate, newAggregate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case UschemaPackage.SCHEMA_TYPE__FEATURES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getFeatures()).basicAdd(otherEnd, msgs);
			case UschemaPackage.SCHEMA_TYPE__AGGREGATE:
				if (aggregate != null)
					msgs = ((InternalEObject)aggregate).eInverseRemove(this, UschemaPackage.AGGREGATE__SPECIFIED_BY, Aggregate.class, msgs);
				return basicSetAggregate((Aggregate)otherEnd, msgs);
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
			case UschemaPackage.SCHEMA_TYPE__FEATURES:
				return ((InternalEList<?>)getFeatures()).basicRemove(otherEnd, msgs);
			case UschemaPackage.SCHEMA_TYPE__AGGREGATE:
				return basicSetAggregate(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UschemaPackage.SCHEMA_TYPE__NAME:
				return getName();
			case UschemaPackage.SCHEMA_TYPE__PARENTS:
				return getParents();
			case UschemaPackage.SCHEMA_TYPE__FEATURES:
				return getFeatures();
			case UschemaPackage.SCHEMA_TYPE__AGGREGATE:
				if (resolve) return getAggregate();
				return basicGetAggregate();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case UschemaPackage.SCHEMA_TYPE__NAME:
				setName((String)newValue);
				return;
			case UschemaPackage.SCHEMA_TYPE__PARENTS:
				getParents().clear();
				getParents().addAll((Collection<? extends SchemaType>)newValue);
				return;
			case UschemaPackage.SCHEMA_TYPE__FEATURES:
				getFeatures().clear();
				getFeatures().addAll((Collection<? extends Feature>)newValue);
				return;
			case UschemaPackage.SCHEMA_TYPE__AGGREGATE:
				setAggregate((Aggregate)newValue);
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
			case UschemaPackage.SCHEMA_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UschemaPackage.SCHEMA_TYPE__PARENTS:
				getParents().clear();
				return;
			case UschemaPackage.SCHEMA_TYPE__FEATURES:
				getFeatures().clear();
				return;
			case UschemaPackage.SCHEMA_TYPE__AGGREGATE:
				setAggregate((Aggregate)null);
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
			case UschemaPackage.SCHEMA_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UschemaPackage.SCHEMA_TYPE__PARENTS:
				return parents != null && !parents.isEmpty();
			case UschemaPackage.SCHEMA_TYPE__FEATURES:
				return features != null && !features.isEmpty();
			case UschemaPackage.SCHEMA_TYPE__AGGREGATE:
				return aggregate != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //SchemaTypeImpl
