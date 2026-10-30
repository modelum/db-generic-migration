/**
 */
package uschema;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Key</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uschema.Key#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link uschema.Key#isIsID <em>Is ID</em>}</li>
 * </ul>
 *
 * @see uschema.UschemaPackage#getKey()
 * @model
 * @generated
 */
public interface Key extends LogicalFeature {
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' reference list.
	 * The list contents are of type {@link uschema.Attribute}.
	 * It is bidirectional and its opposite is '{@link uschema.Attribute#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' reference list.
	 * @see uschema.UschemaPackage#getKey_Attributes()
	 * @see uschema.Attribute#getKey
	 * @model opposite="key"
	 * @generated
	 */
	EList<Attribute> getAttributes();

	/**
	 * Returns the value of the '<em><b>Is ID</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is ID</em>' attribute.
	 * @see #setIsID(boolean)
	 * @see uschema.UschemaPackage#getKey_IsID()
	 * @model default="true" required="true"
	 * @generated
	 */
	boolean isIsID();

	/**
	 * Sets the value of the '{@link uschema.Key#isIsID <em>Is ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is ID</em>' attribute.
	 * @see #isIsID()
	 * @generated
	 */
	void setIsID(boolean value);

} // Key
