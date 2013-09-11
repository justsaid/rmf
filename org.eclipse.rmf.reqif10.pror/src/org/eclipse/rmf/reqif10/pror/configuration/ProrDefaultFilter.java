/**
 */
package org.eclipse.rmf.reqif10.pror.configuration;

import org.eclipse.rmf.reqif10.AttributeDefinition;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pror Default Filter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.rmf.reqif10.pror.configuration.ProrDefaultFilter#getRegexp <em>Regexp</em>}</li>
 *   <li>{@link org.eclipse.rmf.reqif10.pror.configuration.ProrDefaultFilter#getAttribute <em>Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.rmf.reqif10.pror.configuration.ConfigurationPackage#getProrDefaultFilter()
 * @model
 * @generated
 */
public interface ProrDefaultFilter extends ProrFilterConfiguration {
	/**
	 * Returns the value of the '<em><b>Regexp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Regexp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Regexp</em>' attribute.
	 * @see #setRegexp(String)
	 * @see org.eclipse.rmf.reqif10.pror.configuration.ConfigurationPackage#getProrDefaultFilter_Regexp()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getRegexp();

	/**
	 * Sets the value of the '{@link org.eclipse.rmf.reqif10.pror.configuration.ProrDefaultFilter#getRegexp <em>Regexp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Regexp</em>' attribute.
	 * @see #getRegexp()
	 * @generated
	 */
	void setRegexp(String value);

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(AttributeDefinition)
	 * @see org.eclipse.rmf.reqif10.pror.configuration.ConfigurationPackage#getProrDefaultFilter_Attribute()
	 * @model
	 * @generated
	 */
	AttributeDefinition getAttribute();

	/**
	 * Sets the value of the '{@link org.eclipse.rmf.reqif10.pror.configuration.ProrDefaultFilter#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(AttributeDefinition value);

} // ProrDefaultFilter
