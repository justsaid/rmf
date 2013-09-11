/**
 */
package org.eclipse.rmf.reqif10.pror.configuration.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.rmf.reqif10.AttributeDefinition;

import org.eclipse.rmf.reqif10.pror.configuration.ConfigurationPackage;
import org.eclipse.rmf.reqif10.pror.configuration.ProrDefaultFilter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pror Default Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.rmf.reqif10.pror.configuration.impl.ProrDefaultFilterImpl#getRegexp <em>Regexp</em>}</li>
 *   <li>{@link org.eclipse.rmf.reqif10.pror.configuration.impl.ProrDefaultFilterImpl#getAttribute <em>Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProrDefaultFilterImpl extends ProrFilterConfigurationImpl implements ProrDefaultFilter {
	/**
	 * The default value of the '{@link #getRegexp() <em>Regexp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegexp()
	 * @generated
	 * @ordered
	 */
	protected static final String REGEXP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRegexp() <em>Regexp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegexp()
	 * @generated
	 * @ordered
	 */
	protected String regexp = REGEXP_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected AttributeDefinition attribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProrDefaultFilterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.PROR_DEFAULT_FILTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRegexp() {
		return regexp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRegexp(String newRegexp) {
		String oldRegexp = regexp;
		regexp = newRegexp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.PROR_DEFAULT_FILTER__REGEXP, oldRegexp, regexp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeDefinition getAttribute() {
		if (attribute != null && attribute.eIsProxy()) {
			InternalEObject oldAttribute = (InternalEObject)attribute;
			attribute = (AttributeDefinition)eResolveProxy(oldAttribute);
			if (attribute != oldAttribute) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConfigurationPackage.PROR_DEFAULT_FILTER__ATTRIBUTE, oldAttribute, attribute));
			}
		}
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeDefinition basicGetAttribute() {
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttribute(AttributeDefinition newAttribute) {
		AttributeDefinition oldAttribute = attribute;
		attribute = newAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.PROR_DEFAULT_FILTER__ATTRIBUTE, oldAttribute, attribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConfigurationPackage.PROR_DEFAULT_FILTER__REGEXP:
				return getRegexp();
			case ConfigurationPackage.PROR_DEFAULT_FILTER__ATTRIBUTE:
				if (resolve) return getAttribute();
				return basicGetAttribute();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ConfigurationPackage.PROR_DEFAULT_FILTER__REGEXP:
				setRegexp((String)newValue);
				return;
			case ConfigurationPackage.PROR_DEFAULT_FILTER__ATTRIBUTE:
				setAttribute((AttributeDefinition)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ConfigurationPackage.PROR_DEFAULT_FILTER__REGEXP:
				setRegexp(REGEXP_EDEFAULT);
				return;
			case ConfigurationPackage.PROR_DEFAULT_FILTER__ATTRIBUTE:
				setAttribute((AttributeDefinition)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ConfigurationPackage.PROR_DEFAULT_FILTER__REGEXP:
				return REGEXP_EDEFAULT == null ? regexp != null : !REGEXP_EDEFAULT.equals(regexp);
			case ConfigurationPackage.PROR_DEFAULT_FILTER__ATTRIBUTE:
				return attribute != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (regexp: ");
		result.append(regexp);
		result.append(')');
		return result.toString();
	}

} //ProrDefaultFilterImpl
