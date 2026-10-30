/**
 */
package uschema;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>PSet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uschema.PSet#getElementType <em>Element Type</em>}</li>
 * </ul>
 *
 * @see uschema.UschemaPackage#getPSet()
 * @model
 * @generated
 */
public interface PSet extends DataType {
	/**
	 * Returns the value of the '<em><b>Element Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Type</em>' containment reference.
	 * @see #setElementType(DataType)
	 * @see uschema.UschemaPackage#getPSet_ElementType()
	 * @model containment="true" required="true"
	 * @generated
	 */
	DataType getElementType();

	/**
	 * Sets the value of the '{@link uschema.PSet#getElementType <em>Element Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element Type</em>' containment reference.
	 * @see #getElementType()
	 * @generated
	 */
	void setElementType(DataType value);

} // PSet
