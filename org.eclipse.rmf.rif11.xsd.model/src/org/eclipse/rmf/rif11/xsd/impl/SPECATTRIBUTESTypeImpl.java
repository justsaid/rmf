/*******************************************************************************
 * Copyright (c) 2011 itemis GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nirmal Sasidharan - initial API and implementation
 ******************************************************************************/

package org.eclipse.rmf.rif11.xsd.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.rmf.rif11.xsd.ATTRIBUTEDEFINITIONCOMPLEX;
import org.eclipse.rmf.rif11.xsd.ATTRIBUTEDEFINITIONENUMERATION;
import org.eclipse.rmf.rif11.xsd.ATTRIBUTEDEFINITIONSIMPLE;
import org.eclipse.rmf.rif11.xsd.RifPackage;
import org.eclipse.rmf.rif11.xsd.SPECATTRIBUTESType;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>SPECATTRIBUTES Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.rmf.rif11.xsd.impl.SPECATTRIBUTESTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link org.eclipse.rmf.rif11.xsd.impl.SPECATTRIBUTESTypeImpl#getATTRIBUTEDEFINITIONCOMPLEX <em>ATTRIBUTEDEFINITIONCOMPLEX</em>}</li>
 *   <li>{@link org.eclipse.rmf.rif11.xsd.impl.SPECATTRIBUTESTypeImpl#getATTRIBUTEDEFINITIONENUMERATION <em>ATTRIBUTEDEFINITIONENUMERATION</em>}</li>
 *   <li>{@link org.eclipse.rmf.rif11.xsd.impl.SPECATTRIBUTESTypeImpl#getATTRIBUTEDEFINITIONSIMPLE <em>ATTRIBUTEDEFINITIONSIMPLE</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SPECATTRIBUTESTypeImpl extends EObjectImpl implements SPECATTRIBUTESType {
	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap group;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SPECATTRIBUTESTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RifPackage.Literals.SPECATTRIBUTES_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, RifPackage.SPECATTRIBUTES_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ATTRIBUTEDEFINITIONCOMPLEX> getATTRIBUTEDEFINITIONCOMPLEX() {
		return getGroup().list(RifPackage.Literals.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONCOMPLEX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ATTRIBUTEDEFINITIONENUMERATION> getATTRIBUTEDEFINITIONENUMERATION() {
		return getGroup().list(RifPackage.Literals.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONENUMERATION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ATTRIBUTEDEFINITIONSIMPLE> getATTRIBUTEDEFINITIONSIMPLE() {
		return getGroup().list(RifPackage.Literals.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONSIMPLE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RifPackage.SPECATTRIBUTES_TYPE__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONCOMPLEX:
				return ((InternalEList<?>)getATTRIBUTEDEFINITIONCOMPLEX()).basicRemove(otherEnd, msgs);
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONENUMERATION:
				return ((InternalEList<?>)getATTRIBUTEDEFINITIONENUMERATION()).basicRemove(otherEnd, msgs);
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONSIMPLE:
				return ((InternalEList<?>)getATTRIBUTEDEFINITIONSIMPLE()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RifPackage.SPECATTRIBUTES_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONCOMPLEX:
				return getATTRIBUTEDEFINITIONCOMPLEX();
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONENUMERATION:
				return getATTRIBUTEDEFINITIONENUMERATION();
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONSIMPLE:
				return getATTRIBUTEDEFINITIONSIMPLE();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RifPackage.SPECATTRIBUTES_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONCOMPLEX:
				getATTRIBUTEDEFINITIONCOMPLEX().clear();
				getATTRIBUTEDEFINITIONCOMPLEX().addAll((Collection<? extends ATTRIBUTEDEFINITIONCOMPLEX>)newValue);
				return;
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONENUMERATION:
				getATTRIBUTEDEFINITIONENUMERATION().clear();
				getATTRIBUTEDEFINITIONENUMERATION().addAll((Collection<? extends ATTRIBUTEDEFINITIONENUMERATION>)newValue);
				return;
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONSIMPLE:
				getATTRIBUTEDEFINITIONSIMPLE().clear();
				getATTRIBUTEDEFINITIONSIMPLE().addAll((Collection<? extends ATTRIBUTEDEFINITIONSIMPLE>)newValue);
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
			case RifPackage.SPECATTRIBUTES_TYPE__GROUP:
				getGroup().clear();
				return;
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONCOMPLEX:
				getATTRIBUTEDEFINITIONCOMPLEX().clear();
				return;
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONENUMERATION:
				getATTRIBUTEDEFINITIONENUMERATION().clear();
				return;
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONSIMPLE:
				getATTRIBUTEDEFINITIONSIMPLE().clear();
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
			case RifPackage.SPECATTRIBUTES_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONCOMPLEX:
				return !getATTRIBUTEDEFINITIONCOMPLEX().isEmpty();
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONENUMERATION:
				return !getATTRIBUTEDEFINITIONENUMERATION().isEmpty();
			case RifPackage.SPECATTRIBUTES_TYPE__ATTRIBUTEDEFINITIONSIMPLE:
				return !getATTRIBUTEDEFINITIONSIMPLE().isEmpty();
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
		result.append(" (group: ");
		result.append(group);
		result.append(')');
		return result.toString();
	}

} //SPECATTRIBUTESTypeImpl