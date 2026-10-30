/**
 */
package relationalschema;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link relationalschema.Table#getColumns <em>Columns</em>}</li>
 *   <li>{@link relationalschema.Table#getKeys <em>Keys</em>}</li>
 *   <li>{@link relationalschema.Table#getFks <em>Fks</em>}</li>
 *   <li>{@link relationalschema.Table#getOwner <em>Owner</em>}</li>
 * </ul>
 *
 * @see relationalschema.RelationalschemaPackage#getTable()
 * @model
 * @generated
 */
public interface Table extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Columns</b></em>' containment reference list.
	 * The list contents are of type {@link relationalschema.Column}.
	 * It is bidirectional and its opposite is '{@link relationalschema.Column#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Columns</em>' containment reference list.
	 * @see relationalschema.RelationalschemaPackage#getTable_Columns()
	 * @see relationalschema.Column#getOwner
	 * @model opposite="owner" containment="true"
	 * @generated
	 */
	EList<Column> getColumns();

	/**
	 * Returns the value of the '<em><b>Keys</b></em>' containment reference list.
	 * The list contents are of type {@link relationalschema.Key}.
	 * It is bidirectional and its opposite is '{@link relationalschema.Key#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Keys</em>' containment reference list.
	 * @see relationalschema.RelationalschemaPackage#getTable_Keys()
	 * @see relationalschema.Key#getOwner
	 * @model opposite="owner" containment="true" required="true"
	 * @generated
	 */
	EList<Key> getKeys();

	/**
	 * Returns the value of the '<em><b>Fks</b></em>' containment reference list.
	 * The list contents are of type {@link relationalschema.FKey}.
	 * It is bidirectional and its opposite is '{@link relationalschema.FKey#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fks</em>' containment reference list.
	 * @see relationalschema.RelationalschemaPackage#getTable_Fks()
	 * @see relationalschema.FKey#getOwner
	 * @model opposite="owner" containment="true"
	 * @generated
	 */
	EList<FKey> getFks();

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link relationalschema.RelationalSchema#getTables <em>Tables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(RelationalSchema)
	 * @see relationalschema.RelationalschemaPackage#getTable_Owner()
	 * @see relationalschema.RelationalSchema#getTables
	 * @model opposite="tables" transient="false"
	 * @generated
	 */
	RelationalSchema getOwner();

	/**
	 * Sets the value of the '{@link relationalschema.Table#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(RelationalSchema value);

} // Table
