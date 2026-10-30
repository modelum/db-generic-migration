/**
 */
package uschema;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Schema Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uschema.SchemaType#getName <em>Name</em>}</li>
 *   <li>{@link uschema.SchemaType#getParents <em>Parents</em>}</li>
 *   <li>{@link uschema.SchemaType#getFeatures <em>Features</em>}</li>
 *   <li>{@link uschema.SchemaType#getAggregate <em>Aggregate</em>}</li>
 * </ul>
 *
 * @see uschema.UschemaPackage#getSchemaType()
 * @model abstract="true"
 * @generated
 */
public interface SchemaType extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see uschema.UschemaPackage#getSchemaType_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link uschema.SchemaType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Parents</b></em>' reference list.
	 * The list contents are of type {@link uschema.SchemaType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parents</em>' reference list.
	 * @see uschema.UschemaPackage#getSchemaType_Parents()
	 * @model
	 * @generated
	 */
	EList<SchemaType> getParents();

	/**
	 * Returns the value of the '<em><b>Features</b></em>' containment reference list.
	 * The list contents are of type {@link uschema.Feature}.
	 * It is bidirectional and its opposite is '{@link uschema.Feature#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Features</em>' containment reference list.
	 * @see uschema.UschemaPackage#getSchemaType_Features()
	 * @see uschema.Feature#getOwner
	 * @model opposite="owner" containment="true"
	 * @generated
	 */
	EList<Feature> getFeatures();

	/**
	 * Returns the value of the '<em><b>Aggregate</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link uschema.Aggregate#getSpecifiedBy <em>Specified By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Aggregate</em>' reference.
	 * @see #setAggregate(Aggregate)
	 * @see uschema.UschemaPackage#getSchemaType_Aggregate()
	 * @see uschema.Aggregate#getSpecifiedBy
	 * @model opposite="specifiedBy" required="true"
	 * @generated
	 */
	Aggregate getAggregate();

	/**
	 * Sets the value of the '{@link uschema.SchemaType#getAggregate <em>Aggregate</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Aggregate</em>' reference.
	 * @see #getAggregate()
	 * @generated
	 */
	void setAggregate(Aggregate value);

} // SchemaType
