/**
 */
package relationalschema;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link relationalschema.Column#getDatatype <em>Datatype</em>}</li>
 *   <li>{@link relationalschema.Column#getSize <em>Size</em>}</li>
 *   <li>{@link relationalschema.Column#isNullable <em>Nullable</em>}</li>
 *   <li>{@link relationalschema.Column#getDefaultvalue <em>Defaultvalue</em>}</li>
 *   <li>{@link relationalschema.Column#getOwner <em>Owner</em>}</li>
 * </ul>
 *
 * @see relationalschema.RelationalschemaPackage#getColumn()
 * @model
 * @generated
 */
public interface Column extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Datatype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Datatype</em>' attribute.
	 * @see #setDatatype(String)
	 * @see relationalschema.RelationalschemaPackage#getColumn_Datatype()
	 * @model required="true"
	 * @generated
	 */
	String getDatatype();

	/**
	 * Sets the value of the '{@link relationalschema.Column#getDatatype <em>Datatype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Datatype</em>' attribute.
	 * @see #getDatatype()
	 * @generated
	 */
	void setDatatype(String value);

	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see #setSize(int)
	 * @see relationalschema.RelationalschemaPackage#getColumn_Size()
	 * @model default="1" required="true"
	 * @generated
	 */
	int getSize();

	/**
	 * Sets the value of the '{@link relationalschema.Column#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' attribute.
	 * @see #getSize()
	 * @generated
	 */
	void setSize(int value);

	/**
	 * Returns the value of the '<em><b>Nullable</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nullable</em>' attribute.
	 * @see #setNullable(boolean)
	 * @see relationalschema.RelationalschemaPackage#getColumn_Nullable()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isNullable();

	/**
	 * Sets the value of the '{@link relationalschema.Column#isNullable <em>Nullable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nullable</em>' attribute.
	 * @see #isNullable()
	 * @generated
	 */
	void setNullable(boolean value);

	/**
	 * Returns the value of the '<em><b>Defaultvalue</b></em>' attribute.
	 * The default value is <code>"NULL"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Defaultvalue</em>' attribute.
	 * @see #setDefaultvalue(String)
	 * @see relationalschema.RelationalschemaPackage#getColumn_Defaultvalue()
	 * @model default="NULL"
	 * @generated
	 */
	String getDefaultvalue();

	/**
	 * Sets the value of the '{@link relationalschema.Column#getDefaultvalue <em>Defaultvalue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defaultvalue</em>' attribute.
	 * @see #getDefaultvalue()
	 * @generated
	 */
	void setDefaultvalue(String value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link relationalschema.Table#getColumns <em>Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(Table)
	 * @see relationalschema.RelationalschemaPackage#getColumn_Owner()
	 * @see relationalschema.Table#getColumns
	 * @model opposite="columns" required="true" transient="false"
	 * @generated
	 */
	Table getOwner();

	/**
	 * Sets the value of the '{@link relationalschema.Column#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(Table value);

} // Column
