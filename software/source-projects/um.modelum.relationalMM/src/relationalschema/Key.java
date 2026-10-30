/**
 */
package relationalschema;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Key</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link relationalschema.Key#getColumns <em>Columns</em>}</li>
 *   <li>{@link relationalschema.Key#getConstraintname <em>Constraintname</em>}</li>
 *   <li>{@link relationalschema.Key#getOwner <em>Owner</em>}</li>
 *   <li>{@link relationalschema.Key#isIsPK <em>Is PK</em>}</li>
 * </ul>
 *
 * @see relationalschema.RelationalschemaPackage#getKey()
 * @model
 * @generated
 */
public interface Key extends EObject {
	/**
	 * Returns the value of the '<em><b>Columns</b></em>' reference list.
	 * The list contents are of type {@link relationalschema.Column}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Columns</em>' reference list.
	 * @see relationalschema.RelationalschemaPackage#getKey_Columns()
	 * @model required="true"
	 * @generated
	 */
	EList<Column> getColumns();

	/**
	 * Returns the value of the '<em><b>Constraintname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraintname</em>' attribute.
	 * @see #setConstraintname(String)
	 * @see relationalschema.RelationalschemaPackage#getKey_Constraintname()
	 * @model
	 * @generated
	 */
	String getConstraintname();

	/**
	 * Sets the value of the '{@link relationalschema.Key#getConstraintname <em>Constraintname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraintname</em>' attribute.
	 * @see #getConstraintname()
	 * @generated
	 */
	void setConstraintname(String value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link relationalschema.Table#getKeys <em>Keys</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(Table)
	 * @see relationalschema.RelationalschemaPackage#getKey_Owner()
	 * @see relationalschema.Table#getKeys
	 * @model opposite="keys" required="true" transient="false"
	 * @generated
	 */
	Table getOwner();

	/**
	 * Sets the value of the '{@link relationalschema.Key#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(Table value);

	/**
	 * Returns the value of the '<em><b>Is PK</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is PK</em>' attribute.
	 * @see #setIsPK(boolean)
	 * @see relationalschema.RelationalschemaPackage#getKey_IsPK()
	 * @model default="true" required="true"
	 * @generated
	 */
	boolean isIsPK();

	/**
	 * Sets the value of the '{@link relationalschema.Key#isIsPK <em>Is PK</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is PK</em>' attribute.
	 * @see #isIsPK()
	 * @generated
	 */
	void setIsPK(boolean value);

} // Key
