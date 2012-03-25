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
package org.eclipse.rmf.pror.reqif10.tests.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.rmf.reqif10.ReqIf;

public class ReqIfResourceUtils {

	public static ReqIf createReqIfPlatformResorce(IProject project, String fileName,
			String contents, EditingDomain editingDomain)
			throws Exception {

		IFile file = project.getFile(fileName);
		InputStream input = new ByteArrayInputStream(
				contents.getBytes("utf-8"));
		file.create(input, IResource.FORCE, null);

		URI resourceURI = URI.createPlatformResourceURI(file.getFullPath()
				.toOSString(), true);
		Resource resource = editingDomain.getResourceSet().getResource(
				resourceURI, true);
		ReqIf rif = (ReqIf) resource.getContents().get(0);

		return rif;

	}

	public static ReqIf createReqIfPlatformResorce(IProject project, String fileName,
			InputStream inputStream, EditingDomain editingDomain)
			throws Exception {
		return createReqIfPlatformResorce(project, fileName,
				ResourceUtils.inputStreamToString(inputStream), editingDomain);
	}

}