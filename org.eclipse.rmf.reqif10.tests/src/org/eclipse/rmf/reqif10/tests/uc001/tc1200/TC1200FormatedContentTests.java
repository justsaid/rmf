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
package org.eclipse.rmf.reqif10.tests.uc001.tc1200;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import junit.framework.Assert;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.tests.util.AbstractTestCase;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("nls")
public class TC1200FormatedContentTests extends AbstractTestCase {
	static final String TEST_CASE_ID = "TC1200";
	static final String REFERENCE_DATA_FILENAME = getWorkingFileName(getReferenceDataFileName(TEST_CASE_ID, false));
	static final String EXPORT_DATA_FILENAME = getWorkingFileName(getFirstExportFileName(TEST_CASE_ID, false));

	static ReqIF originalReqIF = null;
	static ReqIF loadedReqIF = null;

	@BeforeClass
	public static void setupOnce() throws Exception {
		AbstractTestCase.setupOnce();
		// ___
		originalReqIF = new TC1200FormatedContentModelBuilder().getReqIF();
		saveReqIFFile(originalReqIF, REFERENCE_DATA_FILENAME);
		loadedReqIF = loadReqIFFile(REFERENCE_DATA_FILENAME);

	}

	@Test
	public void testSchemaCompliance() throws Exception {
		validateAgainstSchema(REFERENCE_DATA_FILENAME);
	}

	@Test
	public void testResave() throws IOException {
		try {
			saveReqIFFile(loadedReqIF, EXPORT_DATA_FILENAME);
		} catch (IOException ioe) {
			Assert.assertFalse("We shall be able to save without exception. However the following exception occurred: " + ioe.toString(), true);
		}
	}

	@Test
	public void testNoProxies() throws IOException {
		EcoreUtil.resolveAll(loadedReqIF);
		Map<EObject, Collection<Setting>> map = EcoreUtil.ProxyCrossReferencer.find(loadedReqIF);
		assertEquals(0, map.size());
	}

}
