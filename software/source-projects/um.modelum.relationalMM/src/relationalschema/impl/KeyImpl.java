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
import relationalschema.Key;
import relationalschema.RelationalschemaPackage;
import relationalschema.Table;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Key</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link relationalschema.impl.KeyImpl#getColumns <em>Columns</em>}</li>
 *   <li>{@link relationalschema.impl.KeyImpl#getConstraintname <em>Constraintname</em>}</li>
 *   <li>{@link relationalschema.impl.KeyImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link relationalschema.impl.KeyImpl#isIsPK <em>Is PK</em>}</li>
 * </ul>
 *
 * @generated
 */
public class KeyImpl extends MinimalEObjectImpl.Container implements Key {
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
	 * The default value of the '{@link #isIsPK() <em>Is PK</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsPK()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_PK_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isIsPK() <em>Is PK</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsPK()
	 * @generated
	 * @ordered
	 */
	protected boolean isPK = IS_PK_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KeyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RelationalschemaPackage.Literals.KEY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Column> getColumns() {
		if (columns == null) {
			columns = new EObjectResolvingEList<Column>(Column.class, this, RelationalschemaPackage.KEY__COLUMNS);
		}
		return columns;
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
			eNotify(new ENotificationImpl(this, Notification.SET, RelationalschemaPackage.KEY__CONSTRAINTNAME, oldConstraintname, constraintname));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Table getOwner() {
		if (eContainerFeatureID() != RelationalschemaPackage.KEY__OWNER) return null;
		return (Table)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwner(Table newOwner, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwner, RelationalschemaPackage.KEY__OWNER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOwner(Table newOwner) {
		if (newOwner != eInternalContainer() || (eContainerFeatureID() != RelationalschemaPackage.KEY__OWNER && newOwner != null)) {
			if (EcoreUtil.isAncestor(this, newOwner))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwner != null)
				msgs = ((InternalEObject)newOwner).eInverseAdd(this, RelationalschemaPackage.TABLE__KEYS, Table.class, msgs);
			msgs = basicSetOwner(newOwner, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RelationalschemaPackage.KEY__OWNER, newOwner, newOwner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsPK() {
		return isPK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsPK(boolean newIsPK) {
		boolean oldIsPK = isPK;
		isPK = newIsPK;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RelationalschemaPackage.KEY__IS_PK, oldIsPK, isPK));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RelationalschemaPackage.KEY__OWNER:
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
			case RelationalschemaPackage.KEY__OWNER:
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
			case RelationalschemaPackage.KEY__OWNER:
				return eInternalContainer().eInverseRemove(this, RelationalschemaPackage.TABLE__KEYS, Table.class, msgs);
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
			case RelationalschemaPackage.KEY__COLUMNS:
				return getColumns();
			case RelationalschemaPackage.KEY__CONSTRAINTNAME:
				return getConstraintname();
			case RelationalschemaPackage.KEY__OWNER:
				return getOwner();
			case RelationalschemaPackage.KEY__IS_PK:
				return isIsPK();
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
			case RelationalschemaPackage.KEY__COLUMNS:
				getColumns().clear();
				getColumns().addAll((Collection<? extends Column>)newValue);
				return;
			case RelationalschemaPackage.KEY__CONSTRAINTNAME:
				setConstraintname((String)newValue);
				return;
			case RelationalschemaPackage.KEY__OWNER:
				setOwner((Table)newValue);
				return;
			case RelationalschemaPackage.KEY__IS_PK:
				setIsPK((Boolean)newValue);
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
			case RelationalschemaPackage.KEY__COLUMNS:
				getColumns().clear();
				return;
			case RelationalschemaPackage.KEY__CONSTRAINTNAME:
				setConstraintname(CONSTRAINTNAME_EDEFAULT);
				return;
			case RelationalschemaPackage.KEY__OWNER:
				setOwner((Table)null);
				return;
			case RelationalschemaPackage.KEY__IS_PK:
				setIsPK(IS_PK_EDEFAULT);
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
			case RelationalschemaPackage.KEY__COLUMNS:
				return columns != null && !columns.isEmpty();
			case RelationalschemaPackage.KEY__CONSTRAINTNAME:
				return CONSTRAINTNAME_EDEFAULT == null ? constraintname != null : !CONSTRAINTNAME_EDEFAULT.equals(constraintname);
			case RelationalschemaPackage.KEY__OWNER:
				return getOwner() != null;
			case RelationalschemaPackage.KEY__IS_PK:
				return isPK != IS_PK_EDEFAULT;
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
		result.append(" (constraintname: ");
		result.append(constraintname);
		result.append(", isPK: ");
		result.append(isPK);
		result.append(')');
		return result.toString();
	}

} //KeyImpl
