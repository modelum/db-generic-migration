/**
 */
package documentschema;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Primitive Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link documentschema.PrimitiveType#getDatatype <em>Datatype</em>}</li>
 * </ul>
 *
 * @see documentschema.DocumentschemaPackage#getPrimitiveType()
 * @model
 * @generated
 */
public interface PrimitiveType extends Type {
	/**
	 * Returns the value of the '<em><b>Datatype</b></em>' attribute.
	 * The literals are from the enumeration {@link documentschema.DataType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Datatype</em>' attribute.
	 * @see documentschema.DataType
	 * @see #setDatatype(DataType)
	 * @see documentschema.DocumentschemaPackage#getPrimitiveType_Datatype()
	 * @model
	 * @generated
	 */
	DataType getDatatype();

	/**
	 * Sets the value of the '{@link documentschema.PrimitiveType#getDatatype <em>Datatype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Datatype</em>' attribute.
	 * @see documentschema.DataType
	 * @see #getDatatype()
	 * @generated
	 */
	void setDatatype(DataType value);

} // PrimitiveType
