/**
 */
package documentschema;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Embedded</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link documentschema.Embedded#getAggregates <em>Aggregates</em>}</li>
 *   <li>{@link documentschema.Embedded#isIsMany <em>Is Many</em>}</li>
 * </ul>
 *
 * @see documentschema.DocumentschemaPackage#getEmbedded()
 * @model
 * @generated
 */
public interface Embedded extends Property {
	/**
	 * Returns the value of the '<em><b>Aggregates</b></em>' containment reference list.
	 * The list contents are of type {@link documentschema.Property}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Aggregates</em>' containment reference list.
	 * @see documentschema.DocumentschemaPackage#getEmbedded_Aggregates()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Property> getAggregates();

	/**
	 * Returns the value of the '<em><b>Is Many</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Many</em>' attribute.
	 * @see #setIsMany(boolean)
	 * @see documentschema.DocumentschemaPackage#getEmbedded_IsMany()
	 * @model
	 * @generated
	 */
	boolean isIsMany();

	/**
	 * Sets the value of the '{@link documentschema.Embedded#isIsMany <em>Is Many</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Many</em>' attribute.
	 * @see #isIsMany()
	 * @generated
	 */
	void setIsMany(boolean value);

} // Embedded
