/*******************************************************************************
 * Copyright (c) 2011 Formal Mind GmbH and University of Dusseldorf.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Michael Jastram - initial API and implementation
 ******************************************************************************/

package org.eclipse.rmf.pror.reqif10.configuration;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pror Presentation Configurations</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.rmf.pror.reqif10.configuration.ProrPresentationConfigurations#getPresentationConfigurations <em>Presentation Configurations</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.rmf.pror.reqif10.configuration.ConfigPackage#getProrPresentationConfigurations()
 * @model
 * @generated
 */
public interface ProrPresentationConfigurations extends EObject {
	/**
	 * Returns the value of the '<em><b>Presentation Configurations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.rmf.pror.reqif10.configuration.ProrPresentationConfiguration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Presentation Configurations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Presentation Configurations</em>' containment reference list.
	 * @see org.eclipse.rmf.pror.reqif10.configuration.ConfigPackage#getProrPresentationConfigurations_PresentationConfigurations()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProrPresentationConfiguration> getPresentationConfigurations();

} // ProrPresentationConfigurations