/**
 */
package uschema;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Aggregate</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uschema.Aggregate#getSpecifiedBy <em>Specified By</em>}</li>
 *   <li>{@link uschema.Aggregate#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link uschema.Aggregate#getLowerBound <em>Lower Bound</em>}</li>
 * </ul>
 *
 * @see uschema.UschemaPackage#getAggregate()
 * @model
 * @generated
 */
public interface Aggregate extends StructuralFeature {
	/**
	 * Returns the value of the '<em><b>Specified By</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link uschema.SchemaType#getAggregate <em>Aggregate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified By</em>' reference.
	 * @see #setSpecifiedBy(SchemaType)
	 * @see uschema.UschemaPackage#getAggregate_SpecifiedBy()
	 * @see uschema.SchemaType#getAggregate
	 * @model opposite="aggregate" required="true"
	 * @generated
	 */
	SchemaType getSpecifiedBy();

	/**
	 * Sets the value of the '{@link uschema.Aggregate#getSpecifiedBy <em>Specified By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified By</em>' reference.
	 * @see #getSpecifiedBy()
	 * @generated
	 */
	void setSpecifiedBy(SchemaType value);

	/**
	 * Returns the value of the '<em><b>Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Bound</em>' attribute.
	 * @see #setUpperBound(int)
	 * @see uschema.UschemaPackage#getAggregate_UpperBound()
	 * @model
	 * @generated
	 */
	int getUpperBound();

	/**
	 * Sets the value of the '{@link uschema.Aggregate#getUpperBound <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Bound</em>' attribute.
	 * @see #getUpperBound()
	 * @generated
	 */
	void setUpperBound(int value);

	/**
	 * Returns the value of the '<em><b>Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower Bound</em>' attribute.
	 * @see #setLowerBound(int)
	 * @see uschema.UschemaPackage#getAggregate_LowerBound()
	 * @model
	 * @generated
	 */
	int getLowerBound();

	/**
	 * Sets the value of the '{@link uschema.Aggregate#getLowerBound <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Bound</em>' attribute.
	 * @see #getLowerBound()
	 * @generated
	 */
	void setLowerBound(int value);

} // Aggregate
