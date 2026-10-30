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

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import relationalschema.Column;
import relationalschema.FKey;
import relationalschema.Key;
import relationalschema.RelationalSchema;
import relationalschema.RelationalschemaPackage;
import relationalschema.Table;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link relationalschema.impl.TableImpl#getColumns <em>Columns</em>}</li>
 *   <li>{@link relationalschema.impl.TableImpl#getKeys <em>Keys</em>}</li>
 *   <li>{@link relationalschema.impl.TableImpl#getFks <em>Fks</em>}</li>
 *   <li>{@link relationalschema.impl.TableImpl#getOwner <em>Owner</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TableImpl extends NamedElementImpl implements Table {
	/**
	 * The cached value of the '{@link #getColumns() <em>Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<Column> columns;

	/**
	 * The cached value of the '{@link #getKeys() <em>Keys</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeys()
	 * @generated
	 * @ordered
	 */
	protected EList<Key> keys;

	/**
	 * The cached value of the '{@link #getFks() <em>Fks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFks()
	 * @generated
	 * @ordered
	 */
	protected EList<FKey> fks;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RelationalschemaPackage.Literals.TABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Column> getColumns() {
		if (columns == null) {
			columns = new EObjectContainmentWithInverseEList<Column>(Column.class, this, RelationalschemaPackage.TABLE__COLUMNS, RelationalschemaPackage.COLUMN__OWNER);
		}
		return columns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Key> getKeys() {
		if (keys == null) {
			keys = new EObjectContainmentWithInverseEList<Key>(Key.class, this, RelationalschemaPackage.TABLE__KEYS, RelationalschemaPackage.KEY__OWNER);
		}
		return keys;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FKey> getFks() {
		if (fks == null) {
			fks = new EObjectContainmentWithInverseEList<FKey>(FKey.class, this, RelationalschemaPackage.TABLE__FKS, RelationalschemaPackage.FKEY__OWNER);
		}
		return fks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RelationalSchema getOwner() {
		if (eContainerFeatureID() != RelationalschemaPackage.TABLE__OWNER) return null;
		return (RelationalSchema)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwner(RelationalSchema newOwner, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwner, RelationalschemaPackage.TABLE__OWNER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOwner(RelationalSchema newOwner) {
		if (newOwner != eInternalContainer() || (eContainerFeatureID() != RelationalschemaPackage.TABLE__OWNER && newOwner != null)) {
			if (EcoreUtil.isAncestor(this, newOwner))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwner != null)
				msgs = ((InternalEObject)newOwner).eInverseAdd(this, RelationalschemaPackage.RELATIONAL_SCHEMA__TABLES, RelationalSchema.class, msgs);
			msgs = basicSetOwner(newOwner, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RelationalschemaPackage.TABLE__OWNER, newOwner, newOwner));
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
			case RelationalschemaPackage.TABLE__COLUMNS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getColumns()).basicAdd(otherEnd, msgs);
			case RelationalschemaPackage.TABLE__KEYS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getKeys()).basicAdd(otherEnd, msgs);
			case RelationalschemaPackage.TABLE__FKS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getFks()).basicAdd(otherEnd, msgs);
			case RelationalschemaPackage.TABLE__OWNER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOwner((RelationalSchema)otherEnd, msgs);
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
			case RelationalschemaPackage.TABLE__COLUMNS:
				return ((InternalEList<?>)getColumns()).basicRemove(otherEnd, msgs);
			case RelationalschemaPackage.TABLE__KEYS:
				return ((InternalEList<?>)getKeys()).basicRemove(otherEnd, msgs);
			case RelationalschemaPackage.TABLE__FKS:
				return ((InternalEList<?>)getFks()).basicRemove(otherEnd, msgs);
			case RelationalschemaPackage.TABLE__OWNER:
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
			case RelationalschemaPackage.TABLE__OWNER:
				return eInternalContainer().eInverseRemove(this, RelationalschemaPackage.RELATIONAL_SCHEMA__TABLES, RelationalSchema.class, msgs);
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
			case RelationalschemaPackage.TABLE__COLUMNS:
				return getColumns();
			case RelationalschemaPackage.TABLE__KEYS:
				return getKeys();
			case RelationalschemaPackage.TABLE__FKS:
				return getFks();
			case RelationalschemaPackage.TABLE__OWNER:
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
			case RelationalschemaPackage.TABLE__COLUMNS:
				getColumns().clear();
				getColumns().addAll((Collection<? extends Column>)newValue);
				return;
			case RelationalschemaPackage.TABLE__KEYS:
				getKeys().clear();
				getKeys().addAll((Collection<? extends Key>)newValue);
				return;
			case RelationalschemaPackage.TABLE__FKS:
				getFks().clear();
				getFks().addAll((Collection<? extends FKey>)newValue);
				return;
			case RelationalschemaPackage.TABLE__OWNER:
				setOwner((RelationalSchema)newValue);
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
			case RelationalschemaPackage.TABLE__COLUMNS:
				getColumns().clear();
				return;
			case RelationalschemaPackage.TABLE__KEYS:
				getKeys().clear();
				return;
			case RelationalschemaPackage.TABLE__FKS:
				getFks().clear();
				return;
			case RelationalschemaPackage.TABLE__OWNER:
				setOwner((RelationalSchema)null);
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
			case RelationalschemaPackage.TABLE__COLUMNS:
				return columns != null && !columns.isEmpty();
			case RelationalschemaPackage.TABLE__KEYS:
				return keys != null && !keys.isEmpty();
			case RelationalschemaPackage.TABLE__FKS:
				return fks != null && !fks.isEmpty();
			case RelationalschemaPackage.TABLE__OWNER:
				return getOwner() != null;
		}
		return super.eIsSet(featureID);
	}

} //TableImpl
