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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.DatatypeDefinition;
import org.eclipse.rmf.reqif10.EnumValue;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIF10Package;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.SpecRelation;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.XhtmlContent;
import org.eclipse.rmf.reqif10.common.util.ReqIF10Util;
import org.eclipse.rmf.reqif10.common.util.ReqIF10XhtmlUtil;
import org.eclipse.rmf.reqif10.pror.configuration.Column;
import org.eclipse.rmf.reqif10.pror.configuration.ProrPresentationConfiguration;
import org.eclipse.rmf.reqif10.pror.configuration.ProrSpecViewConfiguration;
import org.eclipse.rmf.reqif10.pror.configuration.util.ConfigurationAdapterFactory;
import org.eclipse.rmf.reqif10.pror.editor.presentation.service.IProrCellRenderer;
import org.eclipse.rmf.reqif10.pror.editor.presentation.service.PresentationEditorInterface;
import org.eclipse.rmf.reqif10.pror.presentation.headline.util.HeadlineAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.id.util.IdAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.linewrap.util.LinewrapAdapterFactory;
import org.eclipse.rmf.reqif10.pror.provider.ReqIF10ItemProviderAdapterFactory;
import org.eclipse.rmf.reqif10.pror.util.ConfigurationUtil;
import org.eclipse.rmf.reqif10.pror.util.ProrUtil;
import org.eclipse.rmf.reqif10.pror.util.ProrXhtmlSimplifiedHelper;
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
							
							ProrSpecViewConfiguration config = ConfigurationUtil
									.createSpecViewConfiguration(spec, editingDomain);
							List<SpecObjectJSON> specification = new ArrayList<SpecObjectJSON>();
							createJsonObjRecursive(specification, config, null, spec.getChildren(), adapterFactory);
							
//							createJSON(spec, fileName);
							createSimpleJSON(specification);
//							output.write(createHtmlHeader);
//							output.close();

						}

					}

			}

		}

	}
	private static void createSimpleJSON(List<SpecObjectJSON> specification) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//		List<SpecObjectJSON> specification = new ArrayList<SpecObjectJSON>(); 
//		specification.add(SpecObjectJSON.createSpecObject("1", "-1", SpecObjectJSON.createDefaultAttributes()));
//		specification.add(SpecObjectJSON.createSpecObject("2", "1", SpecObjectJSON.createDefaultAttributes()));
//		specification.add(SpecObjectJSON.createSpecObject("3", "1", SpecObjectJSON.createDefaultAttributes()));
//		specification.add(SpecObjectJSON.createSpecObject("4", "1", SpecObjectJSON.createDefaultAttributes()));
//		specification.add(SpecObjectJSON.createSpecObject("5", "2", SpecObjectJSON.createDefaultAttributes()));
//		specification.add(SpecObjectJSON.createSpecObject("6", "2", SpecObjectJSON.createDefaultAttributes()));
		
		
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

	
	private static String getDefaultValue(AttributeValue av) {
		Object value = av == null ? null : ReqIF10Util.getTheValue(av);
		String textValue;
		if (value == null) {
			textValue = "";
		} else if (value instanceof List<?>) {
			textValue = "";
			for (Iterator<?> i = ((List<?>) ((List<?>) value)).iterator(); i
					.hasNext();) {
				EnumValue enumValue = (EnumValue) i.next();
				textValue += enumValue.getLongName();
				if (i.hasNext()) {
					textValue += ", ";
				}
			}
		} else if (value instanceof XhtmlContent) {
			textValue = ProrXhtmlSimplifiedHelper
					.xhtmlToSimplifiedString((XhtmlContent) value);
			try {
				String xhtmlString = ReqIF10XhtmlUtil
						.getXhtmlString((XhtmlContent) value);
				xhtmlString = xhtmlString.replace("<xhtml:", "<");
				xhtmlString = xhtmlString.replace("</xhtml:", "</");
				textValue = xhtmlString;
			} catch (IOException e) {
			}
		} else {
			textValue = value.toString();
		}
		return textValue;
	}

	private static void createJsonObjRecursive(List<SpecObjectJSON> specification,
			ProrSpecViewConfiguration config, String parentId,
			EList<SpecHierarchy> children,
			AdapterFactory adapterFactory) {
		String newParentId = null;
		for (SpecHierarchy child : children) {
			if (child.getObject() != null) {
				SpecObject specObject = child.getObject();
				String specId = specObject.getIdentifier();
				newParentId = specId;
				
				boolean first = true;
//				html.append("<tr>");
				// Handle indenting
//				if (first) {
//					html.append("<div style='margin-left: " + (indent * 20)
//							+ "px;'>");
//				}
				List<SpecAttributeJSON> attributes = new ArrayList<SpecAttributeJSON>();
				for (Column col : config.getColumns()) {
					SpecAttributeJSON attr = null;

					AttributeValue av = ReqIF10Util.getAttributeValueForLabel(
							specObject, col.getLabel());
					DatatypeDefinition dd = ReqIF10Util
							.getDatatypeDefinition(av);
					ProrPresentationConfiguration configuration = ConfigurationUtil
							.getPresentationConfiguration(dd);

					Object itemProvider = ProrUtil.getItemProvider(
							adapterFactory, configuration);

					if (itemProvider instanceof PresentationEditorInterface) {
						PresentationEditorInterface presentationEditor = (PresentationEditorInterface) itemProvider;
						IProrCellRenderer renderer = presentationEditor
								.getCellRenderer(av);
						if (renderer != null) {
							String content = renderer.doDrawHtmlContent(av);
							if (content != null) {
//								html.append(content);
							} else {
//								html.append(getDefaultValue(av));
							}
						}

					} else {
//						html.append(getDefaultValue(av));
						attr = new SpecAttributeJSON(col.getLabel(), getDefaultValue(av));
						
					}

//					if (first) {
//						first = false;
////						html.append("</div>");
//					}
//					html.append("</td>");
					attributes.add(attr);
				}
//				html.append("</tr>\n");
				SpecObjectJSON specObj = SpecObjectJSON.createSpecObject(specId, parentId, attributes);
				specification.add(specObj);
			}
			

			createJsonObjRecursive(specification, config, newParentId, child.getChildren(),
					adapterFactory);
		}
	}

	
	private static void createReqsLinks(ReqIF reqif){
		EList<SpecRelation> specRelations = reqif.getCoreContent().getSpecRelations();
		for(SpecRelation rel : specRelations)
		{
			rel.getSource().getIdentifier();
			rel.getTarget();
			rel.getIdentifier();
			
		}
	}
	
	
}
