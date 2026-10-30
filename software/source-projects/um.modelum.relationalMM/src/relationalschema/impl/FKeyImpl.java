/**
 */
package relationalschema.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import relationalschema.Column;
import relationalschema.FKey;
import relationalschema.Key;
import relationalschema.ReferentialAction;
import relationalschema.RelationalschemaPackage;
import relationalschema.Table;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>FKey</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link relationalschema.impl.FKeyImpl#getColumns <em>Columns</em>}</li>
 *   <li>{@link relationalschema.impl.FKeyImpl#getRefsTo <em>Refs To</em>}</li>
 *   <li>{@link relationalschema.impl.FKeyImpl#getOnDelete <em>On Delete</em>}</li>
 *   <li>{@link relationalschema.impl.FKeyImpl#getOnUpdate <em>On Update</em>}</li>
 *   <li>{@link relationalschema.impl.FKeyImpl#getConstraintname <em>Constraintname</em>}</li>
 *   <li>{@link relationalschema.impl.FKeyImpl#getOwner <em>Owner</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FKeyImpl extends MinimalEObjectImpl.Container implements FKey {
	/**
	 * The cached value of the '{@link #getColumns() <em>Columns</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<Column> columns;

	/**
	 * The cached value of the '{@link #getRefsTo() <em>Refs To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefsTo()
	 * @generated
	 * @ordered
	 */
	protected Key refsTo;

	/**
	 * The default value of the '{@link #getOnDelete() <em>On Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOnDelete()
	 * @generated
	 * @ordered
	 */
	protected static final ReferentialAction ON_DELETE_EDEFAULT = ReferentialAction.NO_ACTION;

	/**
	 * The cached value of the '{@link #getOnDelete() <em>On Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOnDelete()
	 * @generated
	 * @ordered
	 */
	protected ReferentialAction onDelete = ON_DELETE_EDEFAULT;

	/**
	 * The default value of the '{@link #getOnUpdate() <em>On Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOnUpdate()
	 * @generated
	 * @ordered
	 */
	protected static final ReferentialAction ON_UPDATE_EDEFAULT = ReferentialAction.CASCADE;

	/**
	 * The cached value of the '{@link #getOnUpdate() <em>On Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOnUpdate()
	 * @generated
	 * @ordered
	 */
	protected ReferentialAction onUpdate = ON_UPDATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getConstraintname() <em>Constraintname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraintname()
	 * @generated
	 * @ordered
	 */
	protected static final String CONSTRAINTNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConstraintname() <em>Constraintname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraintname()
	 * @generated
	 * @ordered
	 */
	protected String constraintname = CONSTRAINTNAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FKeyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RelationalschemaPackage.Literals.FKEY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Column> getColumns() {
		if (columns == null) {
			columns = new EObjectResolvingEList<Column>(Column.class, this, RelationalschemaPackage.FKEY__COLUMNS);
		}
		return columns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Key getRefsTo() {
		if (refsTo != null && refsTo.eIsProxy()) {
			InternalEObject oldRefsTo = (InternalEObject)refsTo;
			refsTo = (Key)eResolveProxy(oldRefsTo);
			if (refsTo != oldRefsTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RelationalschemaPackage.FKEY__REFS_TO, oldRefsTo, refsTo));
			}
		}
		return refsTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Key basicGetRefsTo() {
		return refsTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRefsTo(Key newRefsTo) {
		Key oldRefsTo = refsTo;
		refsTo = newRefsTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RelationalschemaPackage.FKEY__REFS_TO, oldRefsTo, refsTo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ReferentialAction getOnDelete() {
		return onDelete;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOnDelete(ReferentialAction newOnDelete) {
		ReferentialAction oldOnDelete = onDelete;
		onDelete = newOnDelete == null ? ON_DELETE_EDEFAULT : newOnDelete;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RelationalschemaPackage.FKEY__ON_DELETE, oldOnDelete, onDelete));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ReferentialAction getOnUpdate() {
		return onUpdate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOnUpdate(ReferentialAction newOnUpdate) {
		ReferentialAction oldOnUpdate = onUpdate;
		onUpdate = newOnUpdate == null ? ON_UPDATE_EDEFAULT : newOnUpdate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RelationalschemaPackage.FKEY__ON_UPDATE, oldOnUpdate, onUpdate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getConstraintname() {
		return constraintname;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConstraintname(String newConstraintname) {
		String oldConstraintname = constraintname;
		constraintname = newConstraintname;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RelationalschemaPackage.FKEY__CONSTRAINTNAME, oldConstraintname, constraintname));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Table getOwner() {
		if (eContainerFeatureID() != RelationalschemaPackage.FKEY__OWNER) return null;
		return (Table)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwner(Table newOwner, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwner, RelationalschemaPackage.FKEY__OWNER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOwner(Table newOwner) {
		if (newOwner != eInternalContainer() || (eContainerFeatureID() != RelationalschemaPackage.FKEY__OWNER && newOwner != null)) {
			if (EcoreUtil.isAncestor(this, newOwner))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwner != null)
				msgs = ((InternalEObject)newOwner).eInverseAdd(this, RelationalschemaPackage.TABLE__FKS, Table.class, msgs);
			msgs = basicSetOwner(newOwner, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RelationalschemaPackage.FKEY__OWNER, newOwner, newOwner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RelationalschemaPackage.FKEY__OWNER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOwner((Table)otherEnd, msgs);
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
			case RelationalschemaPackage.FKEY__OWNER:
				return basicSetOwner(null, msgs);
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
			case RelationalschemaPackage.FKEY__OWNER:
				return eInternalContainer().eInverseRemove(this, RelationalschemaPackage.TABLE__FKS, Table.class, msgs);
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
			case RelationalschemaPackage.FKEY__COLUMNS:
				return getColumns();
			case RelationalschemaPackage.FKEY__REFS_TO:
				if (resolve) return getRefsTo();
				return basicGetRefsTo();
			case RelationalschemaPackage.FKEY__ON_DELETE:
				return getOnDelete();
			case RelationalschemaPackage.FKEY__ON_UPDATE:
				return getOnUpdate();
			case RelationalschemaPackage.FKEY__CONSTRAINTNAME:
				return getConstraintname();
			case RelationalschemaPackage.FKEY__OWNER:
				return getOwner();
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
			case RelationalschemaPackage.FKEY__COLUMNS:
				getColumns().clear();
				getColumns().addAll((Collection<? extends Column>)newValue);
				return;
			case RelationalschemaPackage.FKEY__REFS_TO:
				setRefsTo((Key)newValue);
				return;
			case RelationalschemaPackage.FKEY__ON_DELETE:
				setOnDelete((ReferentialAction)newValue);
				return;
			case RelationalschemaPackage.FKEY__ON_UPDATE:
				setOnUpdate((ReferentialAction)newValue);
				return;
			case RelationalschemaPackage.FKEY__CONSTRAINTNAME:
				setConstraintname((String)newValue);
				return;
			case RelationalschemaPackage.FKEY__OWNER:
				setOwner((Table)newValue);
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
			case RelationalschemaPackage.FKEY__COLUMNS:
				getColumns().clear();
				return;
			case RelationalschemaPackage.FKEY__REFS_TO:
				setRefsTo((Key)null);
				return;
			case RelationalschemaPackage.FKEY__ON_DELETE:
				setOnDelete(ON_DELETE_EDEFAULT);
				return;
			case RelationalschemaPackage.FKEY__ON_UPDATE:
				setOnUpdate(ON_UPDATE_EDEFAULT);
				return;
			case RelationalschemaPackage.FKEY__CONSTRAINTNAME:
				setConstraintname(CONSTRAINTNAME_EDEFAULT);
				return;
			case RelationalschemaPackage.FKEY__OWNER:
				setOwner((Table)null);
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
			case RelationalschemaPackage.FKEY__COLUMNS:
				return columns != null && !columns.isEmpty();
			case RelationalschemaPackage.FKEY__REFS_TO:
				return refsTo != null;
			case RelationalschemaPackage.FKEY__ON_DELETE:
				return onDelete != ON_DELETE_EDEFAULT;
			case RelationalschemaPackage.FKEY__ON_UPDATE:
				return onUpdate != ON_UPDATE_EDEFAULT;
			case RelationalschemaPackage.FKEY__CONSTRAINTNAME:
				return CONSTRAINTNAME_EDEFAULT == null ? constraintname != null : !CONSTRAINTNAME_EDEFAULT.equals(constraintname);
			case RelationalschemaPackage.FKEY__OWNER:
				return getOwner() != null;
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
		result.append(" (onDelete: ");
		result.append(onDelete);
		result.append(", onUpdate: ");
		result.append(onUpdate);
		result.append(", constraintname: ");
		result.append(constraintname);
		result.append(')');
		return result.toString();
	}

} //FKeyImpl
