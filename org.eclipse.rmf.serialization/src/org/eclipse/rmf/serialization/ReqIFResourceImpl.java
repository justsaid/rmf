/**
 * Copyright (c) 2012 itemis AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Mark Br�rkens - initial API and implementation
 */
package org.eclipse.rmf.serialization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.xerces.impl.Constants;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLSave;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.rmf.internal.serialization.ReqIFXMLHelperImpl;
import org.eclipse.rmf.internal.serialization.ReqIFXMLLoadImpl;
import org.eclipse.rmf.internal.serialization.ReqIFXMLSaveImpl;

public class ReqIFResourceImpl extends XMLResourceImpl {

	public ReqIFResourceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReqIFResourceImpl(URI uri) {
		super(uri);		
		// set encoding to UTF-8
		getDefaultSaveOptions().put(XMLResource.OPTION_ENCODING, "UTF-8");
		// get XML names and attribute/value information from extended metadata
		getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		// make sure to write the <?xml version="1.0" encoding="UTF-8"?> header
		getDefaultSaveOptions().put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);
		
		// get XML names and attribute/value information from extended metadata
		getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		// TODO: why do we need this setting?
		getDefaultLoadOptions().put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);
		// Use deprecated methods - the default is true. To improve deserialization performance turn this option to false.
		getDefaultLoadOptions().put(XMLResource.OPTION_USE_DEPRECATED_METHODS, Boolean.FALSE);
		
		getDefaultLoadOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
		
		getDefaultSaveOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);

		getDefaultSaveOptions().put(XMLResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.FALSE);

		// Performance enhancement
		getDefaultLoadOptions().put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
		
		
		// Retrieve application-defined XMLReader features (see http://xerces.apache.org/xerces2-j/features.html for
		// available features and their details)
		Map<String, Boolean> parserFeatures = new HashMap<String, Boolean>();

		// Retrieve application-defined XMLReader properties (see http://xerces.apache.org/xerces2-j/properties.html
		// for available properties and their details)
		Map<String, Object> parserProperties = new HashMap<String, Object>();

		// Perform namespace processing (prefixes will be stripped off element and attribute names and replaced with the
		// corresponding namespace URIs) but do not report attributes used for namespace declarations, and do not report
		// original prefixed names
		parserFeatures.put(Constants.SAX_FEATURE_PREFIX + Constants.NAMESPACES_FEATURE, true);
		parserFeatures.put(Constants.SAX_FEATURE_PREFIX + Constants.NAMESPACE_PREFIXES_FEATURE, false);

		
		getDefaultLoadOptions().put(XMLResource.OPTION_PARSER_FEATURES, parserFeatures);
		getDefaultLoadOptions().put(XMLResource.OPTION_PARSER_PROPERTIES, parserProperties);
		
	}

	@Override
	protected XMLHelper createXMLHelper() {
		return new ReqIFXMLHelperImpl(this);
	}

	@Override
	protected XMLLoad createXMLLoad() {
		return new ReqIFXMLLoadImpl(createXMLHelper());
	}
	
	@Override
	protected XMLSave createXMLSave() {
		return new ReqIFXMLSaveImpl(createXMLHelper());
	}
	
	@Override
	public void save(Map<?, ?> options) throws IOException {
		super.save(options);
	}

	@Override
	public EObject getEObject(String uriFragment) {
		return getEObjectByID(uriFragment);
	}
	/*
	@Override
	
	public EObject getEObject(String uriFragment) {
		if (getContents().size() > 0) {
			return AutosarURIFactory.getEObject(getContents().get(0), uriFragment);
		}
		return null;
	}
*/


}