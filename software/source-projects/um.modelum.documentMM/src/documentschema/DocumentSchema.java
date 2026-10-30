/**
 */
package documentschema;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Schema</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link documentschema.DocumentSchema#getName <em>Name</em>}</li>
 *   <li>{@link documentschema.DocumentSchema#getDocuments <em>Documents</em>}</li>
 *   <li>{@link documentschema.DocumentSchema#getTypes <em>Types</em>}</li>
 * </ul>
 *
 * @see documentschema.DocumentschemaPackage#getDocumentSchema()
 * @model
 * @generated
 */
public interface DocumentSchema extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see documentschema.DocumentschemaPackage#getDocumentSchema_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link documentschema.DocumentSchema#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Documents</b></em>' containment reference list.
	 * The list contents are of type {@link documentschema.DocumentType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Documents</em>' containment reference list.
	 * @see documentschema.DocumentschemaPackage#getDocumentSchema_Documents()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<DocumentType> getDocuments();

	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link documentschema.Type}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see documentschema.DocumentschemaPackage#getDocumentSchema_Types()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Type> getTypes();

} // DocumentSchema
