/**
 */
package relationalschema;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Relational Schema</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link relationalschema.RelationalSchema#getVersion <em>Version</em>}</li>
 *   <li>{@link relationalschema.RelationalSchema#getTables <em>Tables</em>}</li>
 *   <li>{@link relationalschema.RelationalSchema#getLocation <em>Location</em>}</li>
 * </ul>
 *
 * @see relationalschema.RelationalschemaPackage#getRelationalSchema()
 * @model
 * @generated
 */
public interface RelationalSchema extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(int)
	 * @see relationalschema.RelationalschemaPackage#getRelationalSchema_Version()
	 * @model
	 * @generated
	 */
	int getVersion();

	/**
	 * Sets the value of the '{@link relationalschema.RelationalSchema#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(int value);

	/**
	 * Returns the value of the '<em><b>Tables</b></em>' containment reference list.
	 * The list contents are of type {@link relationalschema.Table}.
	 * It is bidirectional and its opposite is '{@link relationalschema.Table#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tables</em>' containment reference list.
	 * @see relationalschema.RelationalschemaPackage#getRelationalSchema_Tables()
	 * @see relationalschema.Table#getOwner
	 * @model opposite="owner" containment="true"
	 * @generated
	 */
	EList<Table> getTables();

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(String)
	 * @see relationalschema.RelationalschemaPackage#getRelationalSchema_Location()
	 * @model
	 * @generated
	 */
	String getLocation();

	/**
	 * Sets the value of the '{@link relationalschema.RelationalSchema#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(String value);

} // RelationalSchema
