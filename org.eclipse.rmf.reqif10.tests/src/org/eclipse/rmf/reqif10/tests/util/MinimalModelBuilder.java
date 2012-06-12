/**
 * Copyright (c) 2012 itemis AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Mark Broerkens - initial API and implementation
 * 
 */
package org.eclipse.rmf.reqif10.tests.util;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIF10Factory;

public class MinimalModelBuilder {
	private ReqIF reqIF;

	public MinimalModelBuilder() throws Exception {
		super();
	}

	public final void createReqIF() throws Exception {
		reqIF = ReqIF10Factory.eINSTANCE.createReqIF();

		// create header
		createReqIFHeader();

		createReqIFCoreContents();

		// create types
		createDatatypes();
		createSpecObjectTypes();
		createSpecificationTypes();
		createSpecRelationTypes();
		createSpecRelationGroupTypes();

		// create objects
		createSpecObjects();
		createSpecifications();
		createSpecRelations();
		createSpecRelationGroups();

		// create tool extensions
		createToolExtensions();
	}

	public void createReqIFHeader() throws Exception {
	}

	public void createReqIFCoreContents() throws Exception {
	}

	public void createDatatypes() throws Exception {
	}

	public void createSpecObjectTypes() throws Exception {
	}

	public void createSpecificationTypes() throws Exception {
	}

	public void createSpecRelationGroups() throws Exception {
	}

	public void createSpecRelationGroupTypes() throws Exception {
	}

	public void createSpecifications() throws Exception {
	}

	public void createSpecObjects() throws Exception {
	}

	public void createSpecRelations() throws Exception {
	}

	public void createSpecRelationTypes() throws Exception {
	}

	public void createToolExtensions() throws Exception {
	}

	public XMLGregorianCalendar getCurrentDate() throws DatatypeConfigurationException {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		XMLGregorianCalendar xmlGregoriaCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		return xmlGregoriaCalendar;
	}

	public XMLGregorianCalendar toDate(String date) throws DatatypeConfigurationException {
		XMLGregorianCalendar xmlGregoriaCalendar = (XMLGregorianCalendar) EcoreUtil.createFromString(XMLTypePackage.eINSTANCE.getDateTime(), date);
		return xmlGregoriaCalendar;
	}

	public ReqIF getReqIF() throws Exception {
		if (null == reqIF) {
			createReqIF();
		}
		return reqIF;
	}

}
