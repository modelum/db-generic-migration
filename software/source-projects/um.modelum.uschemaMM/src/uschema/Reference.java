/**
 */
package uschema;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uschema.Reference#getOpposite <em>Opposite</em>}</li>
 *   <li>{@link uschema.Reference#getRefsTo <em>Refs To</em>}</li>
 *   <li>{@link uschema.Reference#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link uschema.Reference#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link uschema.Reference#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link uschema.Reference#getIsFeaturedBy <em>Is Featured By</em>}</li>
 * </ul>
 *
 * @see uschema.UschemaPackage#getReference()
 * @model
 * @generated
 */
public interface Reference extends LogicalFeature {
	/**
	 * Returns the value of the '<em><b>Opposite</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Opposite</em>' reference.
	 * @see #setOpposite(Reference)
	 * @see uschema.UschemaPackage#getReference_Opposite()
	 * @model
	 * @generated
	 */
	Reference getOpposite();

	/**
	 * Sets the value of the '{@link uschema.Reference#getOpposite <em>Opposite</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Opposite</em>' reference.
	 * @see #getOpposite()
	 * @generated
	 */
	void setOpposite(Reference value);

	/**
	 * Returns the value of the '<em><b>Refs To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refs To</em>' reference.
	 * @see #setRefsTo(EntityType)
	 * @see uschema.UschemaPackage#getReference_RefsTo()
	 * @model required="true"
	 * @generated
	 */
	EntityType getRefsTo();

	/**
	 * Sets the value of the '{@link uschema.Reference#getRefsTo <em>Refs To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refs To</em>' reference.
	 * @see #getRefsTo()
	 * @generated
	 */
	void setRefsTo(EntityType value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' reference list.
	 * The list contents are of type {@link uschema.Attribute}.
	 * It is bidirectional and its opposite is '{@link uschema.Attribute#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' reference list.
	 * @see uschema.UschemaPackage#getReference_Attributes()
	 * @see uschema.Attribute#getReferences
	 * @model opposite="references"
	 * @generated
	 */
	EList<Attribute> getAttributes();

	/**
	 * Returns the value of the '<em><b>Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Bound</em>' attribute.
	 * @see #setUpperBound(int)
	 * @see uschema.UschemaPackage#getReference_UpperBound()
	 * @model
	 * @generated
	 */
	int getUpperBound();

	/**
	 * Sets the value of the '{@link uschema.Reference#getUpperBound <em>Upper Bound</em>}' attribute.
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
	 * @see uschema.UschemaPackage#getReference_LowerBound()
	 * @model
	 * @generated
	 */
	int getLowerBound();

	/**
	 * Sets the value of the '{@link uschema.Reference#getLowerBound <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Bound</em>' attribute.
	 * @see #getLowerBound()
	 * @generated
	 */
	void setLowerBound(int value);

	/**
	 * Returns the value of the '<em><b>Is Featured By</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link uschema.RelationshipType#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Featured By</em>' reference.
	 * @see #setIsFeaturedBy(RelationshipType)
	 * @see uschema.UschemaPackage#getReference_IsFeaturedBy()
	 * @see uschema.RelationshipType#getReferences
	 * @model opposite="references"
	 * @generated
	 */
	RelationshipType getIsFeaturedBy();

	/**
	 * Sets the value of the '{@link uschema.Reference#getIsFeaturedBy <em>Is Featured By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Featured By</em>' reference.
	 * @see #getIsFeaturedBy()
	 * @generated
	 */
	void setIsFeaturedBy(RelationshipType value);

} // Reference
