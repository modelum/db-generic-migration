/**
 */
package relationalschema;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>FKey</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link relationalschema.FKey#getColumns <em>Columns</em>}</li>
 *   <li>{@link relationalschema.FKey#getRefsTo <em>Refs To</em>}</li>
 *   <li>{@link relationalschema.FKey#getOnDelete <em>On Delete</em>}</li>
 *   <li>{@link relationalschema.FKey#getOnUpdate <em>On Update</em>}</li>
 *   <li>{@link relationalschema.FKey#getConstraintname <em>Constraintname</em>}</li>
 *   <li>{@link relationalschema.FKey#getOwner <em>Owner</em>}</li>
 * </ul>
 *
 * @see relationalschema.RelationalschemaPackage#getFKey()
 * @model
 * @generated
 */
public interface FKey extends EObject {
	/**
	 * Returns the value of the '<em><b>Columns</b></em>' reference list.
	 * The list contents are of type {@link relationalschema.Column}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Columns</em>' reference list.
	 * @see relationalschema.RelationalschemaPackage#getFKey_Columns()
	 * @model required="true"
	 * @generated
	 */
	EList<Column> getColumns();

	/**
	 * Returns the value of the '<em><b>Refs To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refs To</em>' reference.
	 * @see #setRefsTo(Key)
	 * @see relationalschema.RelationalschemaPackage#getFKey_RefsTo()
	 * @model required="true"
	 * @generated
	 */
	Key getRefsTo();

	/**
	 * Sets the value of the '{@link relationalschema.FKey#getRefsTo <em>Refs To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refs To</em>' reference.
	 * @see #getRefsTo()
	 * @generated
	 */
	void setRefsTo(Key value);

	/**
	 * Returns the value of the '<em><b>On Delete</b></em>' attribute.
	 * The default value is <code>"NO_ACTION"</code>.
	 * The literals are from the enumeration {@link relationalschema.ReferentialAction}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>On Delete</em>' attribute.
	 * @see relationalschema.ReferentialAction
	 * @see #setOnDelete(ReferentialAction)
	 * @see relationalschema.RelationalschemaPackage#getFKey_OnDelete()
	 * @model default="NO_ACTION" required="true" ordered="false"
	 * @generated
	 */
	ReferentialAction getOnDelete();

	/**
	 * Sets the value of the '{@link relationalschema.FKey#getOnDelete <em>On Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>On Delete</em>' attribute.
	 * @see relationalschema.ReferentialAction
	 * @see #getOnDelete()
	 * @generated
	 */
	void setOnDelete(ReferentialAction value);

	/**
	 * Returns the value of the '<em><b>On Update</b></em>' attribute.
	 * The default value is <code>"CASCADE"</code>.
	 * The literals are from the enumeration {@link relationalschema.ReferentialAction}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>On Update</em>' attribute.
	 * @see relationalschema.ReferentialAction
	 * @see #setOnUpdate(ReferentialAction)
	 * @see relationalschema.RelationalschemaPackage#getFKey_OnUpdate()
	 * @model default="CASCADE" required="true" ordered="false"
	 * @generated
	 */
	ReferentialAction getOnUpdate();

	/**
	 * Sets the value of the '{@link relationalschema.FKey#getOnUpdate <em>On Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>On Update</em>' attribute.
	 * @see relationalschema.ReferentialAction
	 * @see #getOnUpdate()
	 * @generated
	 */
	void setOnUpdate(ReferentialAction value);

	/**
	 * Returns the value of the '<em><b>Constraintname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraintname</em>' attribute.
	 * @see #setConstraintname(String)
	 * @see relationalschema.RelationalschemaPackage#getFKey_Constraintname()
	 * @model
	 * @generated
	 */
	String getConstraintname();

	/**
	 * Sets the value of the '{@link relationalschema.FKey#getConstraintname <em>Constraintname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraintname</em>' attribute.
	 * @see #getConstraintname()
	 * @generated
	 */
	void setConstraintname(String value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link relationalschema.Table#getFks <em>Fks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(Table)
	 * @see relationalschema.RelationalschemaPackage#getFKey_Owner()
	 * @see relationalschema.Table#getFks
	 * @model opposite="fks" required="true" transient="false"
	 * @generated
	 */
	Table getOwner();

	/**
	 * Sets the value of the '{@link relationalschema.FKey#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(Table value);

} // FKey
