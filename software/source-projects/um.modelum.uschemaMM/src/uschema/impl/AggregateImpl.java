/**
 */
package uschema.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import uschema.Aggregate;
import uschema.SchemaType;
import uschema.UschemaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Aggregate</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uschema.impl.AggregateImpl#getSpecifiedBy <em>Specified By</em>}</li>
 *   <li>{@link uschema.impl.AggregateImpl#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link uschema.impl.AggregateImpl#getLowerBound <em>Lower Bound</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AggregateImpl extends StructuralFeatureImpl implements Aggregate {
	/**
	 * The cached value of the '{@link #getSpecifiedBy() <em>Specified By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedBy()
	 * @generated
	 * @ordered
	 */
	protected SchemaType specifiedBy;

	/**
	 * The default value of the '{@link #getUpperBound() <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperBound()
	 * @generated
	 * @ordered
	 */
	protected static final int UPPER_BOUND_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getUpperBound() <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperBound()
	 * @generated
	 * @ordered
	 */
	protected int upperBound = UPPER_BOUND_EDEFAULT;

	/**
	 * The default value of the '{@link #getLowerBound() <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerBound()
	 * @generated
	 * @ordered
	 */
	protected static final int LOWER_BOUND_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLowerBound() <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerBound()
	 * @generated
	 * @ordered
	 */
	protected int lowerBound = LOWER_BOUND_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AggregateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UschemaPackage.Literals.AGGREGATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SchemaType getSpecifiedBy() {
		if (specifiedBy != null && specifiedBy.eIsProxy()) {
			InternalEObject oldSpecifiedBy = (InternalEObject)specifiedBy;
			specifiedBy = (SchemaType)eResolveProxy(oldSpecifiedBy);
			if (specifiedBy != oldSpecifiedBy) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UschemaPackage.AGGREGATE__SPECIFIED_BY, oldSpecifiedBy, specifiedBy));
			}
		}
		return specifiedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SchemaType basicGetSpecifiedBy() {
		return specifiedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSpecifiedBy(SchemaType newSpecifiedBy, NotificationChain msgs) {
		SchemaType oldSpecifiedBy = specifiedBy;
		specifiedBy = newSpecifiedBy;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UschemaPackage.AGGREGATE__SPECIFIED_BY, oldSpecifiedBy, newSpecifiedBy);
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
	public void setSpecifiedBy(SchemaType newSpecifiedBy) {
		if (newSpecifiedBy != specifiedBy) {
			NotificationChain msgs = null;
			if (specifiedBy != null)
				msgs = ((InternalEObject)specifiedBy).eInverseRemove(this, UschemaPackage.SCHEMA_TYPE__AGGREGATE, SchemaType.class, msgs);
			if (newSpecifiedBy != null)
				msgs = ((InternalEObject)newSpecifiedBy).eInverseAdd(this, UschemaPackage.SCHEMA_TYPE__AGGREGATE, SchemaType.class, msgs);
			msgs = basicSetSpecifiedBy(newSpecifiedBy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UschemaPackage.AGGREGATE__SPECIFIED_BY, newSpecifiedBy, newSpecifiedBy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getUpperBound() {
		return upperBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUpperBound(int newUpperBound) {
		int oldUpperBound = upperBound;
		upperBound = newUpperBound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UschemaPackage.AGGREGATE__UPPER_BOUND, oldUpperBound, upperBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getLowerBound() {
		return lowerBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLowerBound(int newLowerBound) {
		int oldLowerBound = lowerBound;
		lowerBound = newLowerBound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UschemaPackage.AGGREGATE__LOWER_BOUND, oldLowerBound, lowerBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case UschemaPackage.AGGREGATE__SPECIFIED_BY:
				if (specifiedBy != null)
					msgs = ((InternalEObject)specifiedBy).eInverseRemove(this, UschemaPackage.SCHEMA_TYPE__AGGREGATE, SchemaType.class, msgs);
				return basicSetSpecifiedBy((SchemaType)otherEnd, msgs);
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
			case UschemaPackage.AGGREGATE__SPECIFIED_BY:
				return basicSetSpecifiedBy(null, msgs);
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
			case UschemaPackage.AGGREGATE__SPECIFIED_BY:
				if (resolve) return getSpecifiedBy();
				return basicGetSpecifiedBy();
			case UschemaPackage.AGGREGATE__UPPER_BOUND:
				return getUpperBound();
			case UschemaPackage.AGGREGATE__LOWER_BOUND:
				return getLowerBound();
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
			case UschemaPackage.AGGREGATE__SPECIFIED_BY:
				setSpecifiedBy((SchemaType)newValue);
				return;
			case UschemaPackage.AGGREGATE__UPPER_BOUND:
				setUpperBound((Integer)newValue);
				return;
			case UschemaPackage.AGGREGATE__LOWER_BOUND:
				setLowerBound((Integer)newValue);
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
			case UschemaPackage.AGGREGATE__SPECIFIED_BY:
				setSpecifiedBy((SchemaType)null);
				return;
			case UschemaPackage.AGGREGATE__UPPER_BOUND:
				setUpperBound(UPPER_BOUND_EDEFAULT);
				return;
			case UschemaPackage.AGGREGATE__LOWER_BOUND:
				setLowerBound(LOWER_BOUND_EDEFAULT);
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
			case UschemaPackage.AGGREGATE__SPECIFIED_BY:
				return specifiedBy != null;
			case UschemaPackage.AGGREGATE__UPPER_BOUND:
				return upperBound != UPPER_BOUND_EDEFAULT;
			case UschemaPackage.AGGREGATE__LOWER_BOUND:
				return lowerBound != LOWER_BOUND_EDEFAULT;
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
		result.append(" (upperBound: ");
		result.append(upperBound);
		result.append(", lowerBound: ");
		result.append(lowerBound);
		result.append(')');
		return result.toString();
	}

} //AggregateImpl
