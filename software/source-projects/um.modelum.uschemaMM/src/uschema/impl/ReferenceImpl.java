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

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import uschema.Attribute;
import uschema.EntityType;
import uschema.Reference;
import uschema.RelationshipType;
import uschema.UschemaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uschema.impl.ReferenceImpl#getOpposite <em>Opposite</em>}</li>
 *   <li>{@link uschema.impl.ReferenceImpl#getRefsTo <em>Refs To</em>}</li>
 *   <li>{@link uschema.impl.ReferenceImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link uschema.impl.ReferenceImpl#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link uschema.impl.ReferenceImpl#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link uschema.impl.ReferenceImpl#getIsFeaturedBy <em>Is Featured By</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferenceImpl extends LogicalFeatureImpl implements Reference {
	/**
	 * The cached value of the '{@link #getOpposite() <em>Opposite</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOpposite()
	 * @generated
	 * @ordered
	 */
	protected Reference opposite;

	/**
	 * The cached value of the '{@link #getRefsTo() <em>Refs To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefsTo()
	 * @generated
	 * @ordered
	 */
	protected EntityType refsTo;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<Attribute> attributes;

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
	 * The cached value of the '{@link #getIsFeaturedBy() <em>Is Featured By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsFeaturedBy()
	 * @generated
	 * @ordered
	 */
	protected RelationshipType isFeaturedBy;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UschemaPackage.Literals.REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Reference getOpposite() {
		if (opposite != null && opposite.eIsProxy()) {
			InternalEObject oldOpposite = (InternalEObject)opposite;
			opposite = (Reference)eResolveProxy(oldOpposite);
			if (opposite != oldOpposite) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UschemaPackage.REFERENCE__OPPOSITE, oldOpposite, opposite));
			}
		}
		return opposite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Reference basicGetOpposite() {
		return opposite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOpposite(Reference newOpposite) {
		Reference oldOpposite = opposite;
		opposite = newOpposite;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UschemaPackage.REFERENCE__OPPOSITE, oldOpposite, opposite));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EntityType getRefsTo() {
		if (refsTo != null && refsTo.eIsProxy()) {
			InternalEObject oldRefsTo = (InternalEObject)refsTo;
			refsTo = (EntityType)eResolveProxy(oldRefsTo);
			if (refsTo != oldRefsTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UschemaPackage.REFERENCE__REFS_TO, oldRefsTo, refsTo));
			}
		}
		return refsTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityType basicGetRefsTo() {
		return refsTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRefsTo(EntityType newRefsTo) {
		EntityType oldRefsTo = refsTo;
		refsTo = newRefsTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UschemaPackage.REFERENCE__REFS_TO, oldRefsTo, refsTo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectWithInverseResolvingEList.ManyInverse<Attribute>(Attribute.class, this, UschemaPackage.REFERENCE__ATTRIBUTES, UschemaPackage.ATTRIBUTE__REFERENCES);
		}
		return attributes;
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
			eNotify(new ENotificationImpl(this, Notification.SET, UschemaPackage.REFERENCE__UPPER_BOUND, oldUpperBound, upperBound));
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
			eNotify(new ENotificationImpl(this, Notification.SET, UschemaPackage.REFERENCE__LOWER_BOUND, oldLowerBound, lowerBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RelationshipType getIsFeaturedBy() {
		if (isFeaturedBy != null && isFeaturedBy.eIsProxy()) {
			InternalEObject oldIsFeaturedBy = (InternalEObject)isFeaturedBy;
			isFeaturedBy = (RelationshipType)eResolveProxy(oldIsFeaturedBy);
			if (isFeaturedBy != oldIsFeaturedBy) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UschemaPackage.REFERENCE__IS_FEATURED_BY, oldIsFeaturedBy, isFeaturedBy));
			}
		}
		return isFeaturedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationshipType basicGetIsFeaturedBy() {
		return isFeaturedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIsFeaturedBy(RelationshipType newIsFeaturedBy, NotificationChain msgs) {
		RelationshipType oldIsFeaturedBy = isFeaturedBy;
		isFeaturedBy = newIsFeaturedBy;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UschemaPackage.REFERENCE__IS_FEATURED_BY, oldIsFeaturedBy, newIsFeaturedBy);
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
	public void setIsFeaturedBy(RelationshipType newIsFeaturedBy) {
		if (newIsFeaturedBy != isFeaturedBy) {
			NotificationChain msgs = null;
			if (isFeaturedBy != null)
				msgs = ((InternalEObject)isFeaturedBy).eInverseRemove(this, UschemaPackage.RELATIONSHIP_TYPE__REFERENCES, RelationshipType.class, msgs);
			if (newIsFeaturedBy != null)
				msgs = ((InternalEObject)newIsFeaturedBy).eInverseAdd(this, UschemaPackage.RELATIONSHIP_TYPE__REFERENCES, RelationshipType.class, msgs);
			msgs = basicSetIsFeaturedBy(newIsFeaturedBy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UschemaPackage.REFERENCE__IS_FEATURED_BY, newIsFeaturedBy, newIsFeaturedBy));
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
			case UschemaPackage.REFERENCE__ATTRIBUTES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAttributes()).basicAdd(otherEnd, msgs);
			case UschemaPackage.REFERENCE__IS_FEATURED_BY:
				if (isFeaturedBy != null)
					msgs = ((InternalEObject)isFeaturedBy).eInverseRemove(this, UschemaPackage.RELATIONSHIP_TYPE__REFERENCES, RelationshipType.class, msgs);
				return basicSetIsFeaturedBy((RelationshipType)otherEnd, msgs);
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
			case UschemaPackage.REFERENCE__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
			case UschemaPackage.REFERENCE__IS_FEATURED_BY:
				return basicSetIsFeaturedBy(null, msgs);
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
			case UschemaPackage.REFERENCE__OPPOSITE:
				if (resolve) return getOpposite();
				return basicGetOpposite();
			case UschemaPackage.REFERENCE__REFS_TO:
				if (resolve) return getRefsTo();
				return basicGetRefsTo();
			case UschemaPackage.REFERENCE__ATTRIBUTES:
				return getAttributes();
			case UschemaPackage.REFERENCE__UPPER_BOUND:
				return getUpperBound();
			case UschemaPackage.REFERENCE__LOWER_BOUND:
				return getLowerBound();
			case UschemaPackage.REFERENCE__IS_FEATURED_BY:
				if (resolve) return getIsFeaturedBy();
				return basicGetIsFeaturedBy();
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
			case UschemaPackage.REFERENCE__OPPOSITE:
				setOpposite((Reference)newValue);
				return;
			case UschemaPackage.REFERENCE__REFS_TO:
				setRefsTo((EntityType)newValue);
				return;
			case UschemaPackage.REFERENCE__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends Attribute>)newValue);
				return;
			case UschemaPackage.REFERENCE__UPPER_BOUND:
				setUpperBound((Integer)newValue);
				return;
			case UschemaPackage.REFERENCE__LOWER_BOUND:
				setLowerBound((Integer)newValue);
				return;
			case UschemaPackage.REFERENCE__IS_FEATURED_BY:
				setIsFeaturedBy((RelationshipType)newValue);
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
			case UschemaPackage.REFERENCE__OPPOSITE:
				setOpposite((Reference)null);
				return;
			case UschemaPackage.REFERENCE__REFS_TO:
				setRefsTo((EntityType)null);
				return;
			case UschemaPackage.REFERENCE__ATTRIBUTES:
				getAttributes().clear();
				return;
			case UschemaPackage.REFERENCE__UPPER_BOUND:
				setUpperBound(UPPER_BOUND_EDEFAULT);
				return;
			case UschemaPackage.REFERENCE__LOWER_BOUND:
				setLowerBound(LOWER_BOUND_EDEFAULT);
				return;
			case UschemaPackage.REFERENCE__IS_FEATURED_BY:
				setIsFeaturedBy((RelationshipType)null);
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
			case UschemaPackage.REFERENCE__OPPOSITE:
				return opposite != null;
			case UschemaPackage.REFERENCE__REFS_TO:
				return refsTo != null;
			case UschemaPackage.REFERENCE__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case UschemaPackage.REFERENCE__UPPER_BOUND:
				return upperBound != UPPER_BOUND_EDEFAULT;
			case UschemaPackage.REFERENCE__LOWER_BOUND:
				return lowerBound != LOWER_BOUND_EDEFAULT;
			case UschemaPackage.REFERENCE__IS_FEATURED_BY:
				return isFeaturedBy != null;
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

} //ReferenceImpl
