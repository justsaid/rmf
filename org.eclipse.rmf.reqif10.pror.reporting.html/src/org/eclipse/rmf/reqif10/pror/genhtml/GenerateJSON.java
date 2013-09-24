/*******************************************************************************
 * Copyright (c) 2011 Formal Mind GmbH and University of Dusseldorf.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Lukas Ladenberger - initial API and implementation
 ******************************************************************************/
package org.eclipse.rmf.reqif10.pror.genhtml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIF10Package;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.pror.configuration.util.ConfigurationAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.headline.util.HeadlineAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.id.util.IdAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.linewrap.util.LinewrapAdapterFactory;
import org.eclipse.rmf.reqif10.pror.provider.ReqIF10ItemProviderAdapterFactory;
import org.eclipse.rmf.serialization.ReqIFResourceFactoryImpl;
import org.eclipse.rmf.serialization.ReqIFResourceImpl;
import org.eclipse.rmf.serialization.ReqIFResourceSetImpl;
import org.eclipselabs.emfjson.EMFJs;
import org.eclipselabs.emfjson.resource.JsResourceFactoryImpl;

/**
 * Standalone-executable class that is used to non-interactively generate HTML
 * from .reqif files. This is used, for instance, to generate viewable
 * specifications on the Eclipse Jenkins build system.
 * 
 * @author ladenberger
 * @author jastram
 * 
 */
public class GenerateJSON {

	public static void main(String[] args) throws FileNotFoundException,
			IOException {

		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory
				.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ConfigurationAdapterFactory());
		adapterFactory
				.addAdapterFactory(new ReqIF10ItemProviderAdapterFactory());
		// FIXME (mj) I would prefer not to generate these - does it work
		// without?
		// adapterFactory.addAdapterFactory(new
		// XhtmlItemProviderAdapterFactory());
		adapterFactory
				.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

		adapterFactory.addAdapterFactory(new HeadlineAdapterFactory());
		adapterFactory.addAdapterFactory(new LinewrapAdapterFactory());
		adapterFactory.addAdapterFactory(new IdAdapterFactory());
		
		BasicCommandStack commandStack = new BasicCommandStack();
		AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(adapterFactory,
				commandStack, new ReqIFResourceSetImpl());
		
		// iterate reqif dump folder
		File f = new File("dump/reqif");

		System.out.println("===> ReqIF Dump folder exists? ===> "
				+ f.exists());
		
		if (f.exists()) {

			File[] fileArray = f.listFiles();

			for (File file : fileArray) {

				if (file.getName().endsWith(".reqif")) {

					System.out.println("===> Loading ReqIF file " + file.getName());
					
					ReqIF reqif = loadData(file);

					if (reqif != null) {
						
						Specification spec = reqif.getCoreContent().getSpecifications().get(0);

							String fileName = file.getName();
//							int mid = fileName.lastIndexOf(".");
//							String fname = fileName.substring(0, mid);
//
//							String createHtmlHeader = ProrEditorUtil
//									.createHtmlContent(spec, editingDomain,
//											adapterFactory);
//							System.out
//									.println("===> Generate HTML file for Loading ReqIF file "
//											+ file.getName());
//
//							String htmlFileName = fname + "_"
//									+ spec.getIdentifier() + ".html";
//
//							File htmlFile = new File("dump/html/"
//									+ htmlFileName);
//							Writer output = new BufferedWriter(new FileWriter(
//									htmlFile));
							createJSON(spec, fileName);
							createSimpleJSON();
//							output.write(createHtmlHeader);
//							output.close();

						}

					}

			}

		}

	}
	private static void createSimpleJSON() throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		List<SpecObjectJSON> specification = new ArrayList<SpecObjectJSON>(); 
		specification.add(SpecObjectJSON.createSpecObject("1", "-1", SpecObjectJSON.createDefaultAttributes()));
		specification.add(SpecObjectJSON.createSpecObject("2", "1", SpecObjectJSON.createDefaultAttributes()));
		specification.add(SpecObjectJSON.createSpecObject("3", "1", SpecObjectJSON.createDefaultAttributes()));
		specification.add(SpecObjectJSON.createSpecObject("4", "1", SpecObjectJSON.createDefaultAttributes()));
		specification.add(SpecObjectJSON.createSpecObject("5", "2", SpecObjectJSON.createDefaultAttributes()));
		specification.add(SpecObjectJSON.createSpecObject("6", "2", SpecObjectJSON.createDefaultAttributes()));
		
		
		String json = ow.writeValueAsString(specification);
		
		System.out.println(json);
		
			
	}
	
	private static void createJSON(Specification spec, String outputName) throws IOException{
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("json", new JsResourceFactoryImpl());
		
		
		Resource resource = resourceSet.createResource(URI.createURI("dump/" + outputName + "-model.json"));
		Map<String, Object> options = new HashMap<String, Object>();
		options.put(EMFJs.OPTION_INDENT_OUTPUT, true);
		options.put(EMFJs.OPTION_SERIALIZE_TYPE, true);
		options.put(EMFJs.OPTION_URL_PARAMETERS, true);
		options.put(EMFJs.OPTION_PROXY_ATTRIBUTES, true);
		options.put(EMFJs.OPTION_SERIALIZE_REF_TYPE, true);
		
		resource.getContents().add(spec);
		resource.save(options);
	}
	
	public static ReqIF loadData(File file) throws FileNotFoundException,
			IOException {
		
		ReqIFResourceSetImpl resourceSet = new ReqIFResourceSetImpl();

		Map<String, Object> extensionToFactoryMap = resourceSet
				.getResourceFactoryRegistry().getExtensionToFactoryMap();

		extensionToFactoryMap.put("reqif", new ReqIFResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(ReqIF10Package.eNS_URI,
				ReqIF10Package.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.FALSE);

		URI fileURI = URI.createFileURI(file.getPath());

		ReqIFResourceImpl resource = (ReqIFResourceImpl) resourceSet
				.createResource(fileURI);
		
		resource.load(extensionToFactoryMap);


		return (ReqIF) resource.getContents().get(0);
		
	}

}
