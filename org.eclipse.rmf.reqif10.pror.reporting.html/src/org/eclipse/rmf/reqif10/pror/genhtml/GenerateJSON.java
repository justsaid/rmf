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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
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
import org.eclipse.rmf.reqif10.pror.genexcel.ExcelReport;
import org.eclipse.rmf.reqif10.pror.genexcel.FormatType;
import org.eclipse.rmf.reqif10.pror.genexcel.ExcelReportColumn;
import org.eclipse.rmf.reqif10.pror.presentation.headline.provider.HeadlineItemProviderAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.id.util.IdAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.linewrap.provider.LinewrapItemProviderAdapterFactory;
import org.eclipse.rmf.reqif10.pror.provider.ReqIF10ItemProviderAdapterFactory;
import org.eclipse.rmf.reqif10.pror.util.ConfigurationUtil;
import org.eclipse.rmf.reqif10.pror.util.ProrUtil;
import org.eclipse.rmf.reqif10.pror.util.ProrXhtmlSimplifiedHelper;
import org.eclipse.rmf.serialization.ReqIFResourceFactoryImpl;
import org.eclipse.rmf.serialization.ReqIFResourceImpl;
import org.eclipse.rmf.serialization.ReqIFResourceSetImpl;

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

	private static boolean excelReport;
	
	public static HSSFWorkbook getWorkbook(String fileName){
		HSSFWorkbook workbook = null;
		try {
		    FileInputStream file = new FileInputStream(new File(fileName));
		 
		    workbook = new HSSFWorkbook(file);
		 
		    file.close();
		     
		    FileOutputStream outFile = new FileOutputStream(new File(fileName+"_updated.xls"));
		    workbook.write(outFile);
		    outFile.close();
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return workbook;
	}
	public static ExcelReport createExcelLinksReport(List<SpecRelationJSON> links, String fileName, ExcelReport report) {

		try {
			ExcelReportColumn[] columns = new ExcelReportColumn[] {
					new ExcelReportColumn("id", "Object ID", FormatType.TEXT),
					new ExcelReportColumn("label", "Label", FormatType.TEXT),
					new ExcelReportColumn("target", "target ID", FormatType.TEXT),
					new ExcelReportColumn("source", "source ID", FormatType.TEXT),
					};
			

			report.addSheet(links, columns, "SpecRelations"  );

			columns[0].setColor(HSSFColor.GREEN.index);


		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return report;
	}
	public static void createExcelReport(List<SpecObjectJSON> specObjects, String fileName, ExcelReport report) {
		List<SpecAttributeJSON> attributes = specObjects.get(0).getAttributes();
		List<String> attributeNames = new ArrayList<String>();
		for (SpecAttributeJSON attr : attributes) {
			attributeNames.add(attr.getAttrName());
		}

		try {
			int idx = 0;
			ExcelReportColumn[] columns = new ExcelReportColumn[attributeNames.size() + 3];
			
			for (String attrName : attributeNames) {
				columns[idx] = new ExcelReportColumn("attributes["+idx+"].attrValue", attrName,
						FormatType.TEXT);
				idx++;
			}
			columns[idx] = new ExcelReportColumn("objectId", "Object ID", FormatType.TEXT);
			columns[idx+1] = new ExcelReportColumn("hierarchyId", "Hierarchy ID", FormatType.TEXT);
			columns[idx+2] = new ExcelReportColumn("parentId", "Parent ID", FormatType.TEXT);
			

			report.addSheet(specObjects, columns, fileName  );

			OutputStream output = new FileOutputStream("dump/"+fileName+".xls");

			report.write(output);
			output.close();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {

		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory
				.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ConfigurationAdapterFactory());
		adapterFactory
				.addAdapterFactory(new ReqIF10ItemProviderAdapterFactory());
		adapterFactory
				.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

		adapterFactory
				.addAdapterFactory(new HeadlineItemProviderAdapterFactory());
		adapterFactory
				.addAdapterFactory(new LinewrapItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new IdAdapterFactory());

		BasicCommandStack commandStack = new BasicCommandStack();
		AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(
				adapterFactory, commandStack, new ReqIFResourceSetImpl());
		editingDomain.getResourceSet().getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put("reqif", new ReqIFResourceFactoryImpl());

		// iterate reqif dump folder
		File f = new File("dump/reqif");

		System.out.println("===> ReqIF Dump folder exists? ===> " + f.exists());

		if (f.exists()) {

			File[] fileArray = f.listFiles();

			for (File file : fileArray) {

				if (file.getName().endsWith(".reqif")) {

					System.out.println("===> Loading ReqIF file "
							+ file.getName());

					ReqIF reqif = loadData(file);

					if (reqif != null) {

						Specification spec = reqif.getCoreContent()
								.getSpecifications().get(0);

						String fileName = file.getName();

						
						
						ProrSpecViewConfiguration config = ConfigurationUtil
								.createSpecViewConfiguration(spec,
										editingDomain);

						excelReport = true;
						List<SpecObjectJSON> specObjects = new ArrayList<SpecObjectJSON>();
						createJsonObjRecursive(specObjects, config, "",
								spec.getChildren(), adapterFactory);

						List<SpecRelationJSON> specRelations = createReqsLinks(
								reqif, config, adapterFactory);

						ExcelReport report = new ExcelReport();
						createExcelLinksReport(specRelations, fileName, report);
						//create excel report
//						Bean2Excel report = new Bean2Excel(getWorkbook("dump/BasicReqs.reqif.xls"));
						createExcelReport(specObjects,fileName, report);
						
						
						SpecificationJSON jsonSpec = new SpecificationJSON();
						jsonSpec.setSpecification(specObjects);
						jsonSpec.setRelations(specRelations);

						ObjectWriter ow = new ObjectMapper().writer()
								.withDefaultPrettyPrinter();
						// String jsonOut = ow.writeValueAsString(jsonSpec);
						// System.out.println(jsonOut);

						ow.writeValue(new File(fileName + ".json"), jsonSpec);
						// createJSON(spec, fileName);
						// createSimpleJSON(specification);
						// output.write(createHtmlHeader);
						// output.close();
					}
				}
			}
		}
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

	private static void createJsonObjRecursive(
			List<SpecObjectJSON> specification,
			ProrSpecViewConfiguration config, String parentId,
			EList<SpecHierarchy> children, AdapterFactory adapterFactory) {
		String newParentId = null;
		for (SpecHierarchy specH : children) {
			if (specH.getObject() != null) {
				SpecObject specObject = specH.getObject();
				String specObjId = specObject.getIdentifier();
				// String specId = specObject.getIdentifier();
				String specHierarchyId = specH.getIdentifier();
				newParentId = specHierarchyId;

				// html.append("<tr>");
				// Handle indenting
				// if (first) {
				// html.append("<div style='margin-left: " + (indent * 20)
				// + "px;'>");
				// }
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

//					ReqIF10Util.getSpecType(ad);
					
					if (itemProvider instanceof PresentationEditorInterface) {
						PresentationEditorInterface presentationEditor = (PresentationEditorInterface) itemProvider;
						IProrCellRenderer renderer = presentationEditor
								.getCellRenderer(av);
						if (renderer != null) {
							String content = renderer.doDrawHtmlContent(av);
							if (content != null && excelReport == false) {
								attr = new SpecAttributeJSON(col.getLabel(),
										content, FormatType.TEXT);
//								attr = new SpecAttributeJSON(col.getLabel(),
//										getDefaultValue(av), FormatType.TEXT);
								
							} else {
								attr = new SpecAttributeJSON(col.getLabel(),
										getDefaultValue(av), FormatType.TEXT);
							}
						}

					} else {
						attr = new SpecAttributeJSON(col.getLabel(),
								getDefaultValue(av), FormatType.TEXT);
					}

					attributes.add(attr);
				}

				SpecObjectJSON specObj = new SpecObjectJSON();
				specObj.setObjectId(specObjId);
				specObj.setHierarchyId(specHierarchyId);
				specObj.setParentId(parentId);
				specObj.setAttributes(attributes);

				specification.add(specObj);
			}

			createJsonObjRecursive(specification, config, newParentId,
					specH.getChildren(), adapterFactory);
		}
	}

	private static List<SpecRelationJSON> createReqsLinks(ReqIF reqif,
			ProrSpecViewConfiguration config, AdapterFactory adapterFactory) {
		EList<SpecRelation> specRelations = reqif.getCoreContent()
				.getSpecRelations();

		List<SpecRelationJSON> jsonSpecRelations = new ArrayList<SpecRelationJSON>();
		for (SpecRelation rel : specRelations) {

			SpecRelationJSON specRelJSON = new SpecRelationJSON();
			specRelJSON.setId(rel.getIdentifier());
			specRelJSON.setTarget(rel.getTarget().getIdentifier());
			specRelJSON.setSource(rel.getSource().getIdentifier());
			String lab = ConfigurationUtil.getSpecElementLabel(rel.getTarget(),
					adapterFactory);
			specRelJSON.setLabel(lab);

			List<SpecAttributeJSON> attributes = new ArrayList<SpecAttributeJSON>();
			for (Column col : config.getColumns()) {
				SpecAttributeJSON attr = null;
				AttributeValue av = ReqIF10Util.getAttributeValueForLabel(rel,
						col.getLabel());

				DatatypeDefinition dd = ReqIF10Util.getDatatypeDefinition(av);
				ProrPresentationConfiguration configuration = ConfigurationUtil
						.getPresentationConfiguration(dd);

				Object itemProvider = ProrUtil.getItemProvider(adapterFactory,
						configuration);

				if (itemProvider instanceof PresentationEditorInterface) {
					PresentationEditorInterface presentationEditor = (PresentationEditorInterface) itemProvider;
					IProrCellRenderer renderer = presentationEditor
							.getCellRenderer(av);
					if (renderer != null && excelReport==false) {
						String content = renderer.doDrawHtmlContent(av);
						if (content != null) {
							attr = new SpecAttributeJSON(col.getLabel(),
									content, FormatType.TEXT);
						} else {
							attr = new SpecAttributeJSON(col.getLabel(),
									getDefaultValue(av), FormatType.TEXT);
						}
					}

				} else {
					attr = new SpecAttributeJSON(col.getLabel(),
							getDefaultValue(av), FormatType.TEXT);
				}
				attributes.add(attr);
			}
			specRelJSON.setAttributes(attributes);
			jsonSpecRelations.add(specRelJSON);
		}
		return jsonSpecRelations;
	}

}
