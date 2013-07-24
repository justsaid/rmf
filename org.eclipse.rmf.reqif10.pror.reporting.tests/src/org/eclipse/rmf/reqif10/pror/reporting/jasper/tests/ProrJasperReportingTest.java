package org.eclipse.rmf.reqif10.pror.reporting.jasper.tests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.DatatypeDefinition;
import org.eclipse.rmf.reqif10.EnumValue;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.XhtmlContent;
import org.eclipse.rmf.reqif10.common.util.ReqIF10Util;
import org.eclipse.rmf.reqif10.common.util.ReqIF10XhtmlUtil;
import org.eclipse.rmf.reqif10.pror.configuration.Column;
import org.eclipse.rmf.reqif10.pror.configuration.ProrPresentationConfiguration;
import org.eclipse.rmf.reqif10.pror.configuration.ProrSpecViewConfiguration;
import org.eclipse.rmf.reqif10.pror.configuration.util.ConfigurationAdapterFactory;
import org.eclipse.rmf.reqif10.pror.editor.agilegrid.ProrAgileGridContentProvider;
import org.eclipse.rmf.reqif10.pror.editor.presentation.service.IProrCellRenderer;
import org.eclipse.rmf.reqif10.pror.editor.presentation.service.PresentationEditorInterface;
import org.eclipse.rmf.reqif10.pror.presentation.headline.util.HeadlineAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.id.util.IdAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.linewrap.util.LinewrapAdapterFactory;
import org.eclipse.rmf.reqif10.pror.provider.ReqIF10ItemProviderAdapterFactory;
import org.eclipse.rmf.reqif10.pror.testframework.AbstractItemProviderTest;
import org.eclipse.rmf.reqif10.pror.util.ConfigurationUtil;
import org.eclipse.rmf.reqif10.pror.util.ProrUtil;
import org.eclipse.rmf.reqif10.pror.util.ProrXhtmlSimplifiedHelper;
import org.junit.Before;
import org.junit.Test;

public class ProrJasperReportingTest extends AbstractItemProviderTest {
	protected ProrAgileGridContentProvider contentProvider;
	protected Specification specification;
	protected ProrSpecViewConfiguration specViewConfig;
	protected ReqIF reqif;
	protected SpecObject specObject;
	protected SpecHierarchy specHierarchy;

	@Before
	public void init() throws URISyntaxException{
		
		adapterFactory = new ComposedAdapterFactory(
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

		
		reqif = this.getTestReqif("Integration-Release-Checklist.reqif");
		specification = reqif.getCoreContent().getSpecifications().get(0);
		specObject = reqif.getCoreContent().getSpecObjects().get(0);
		specHierarchy = specification.getChildren().get(0);
		
		
		// Build up the data structures that hold specViewConfig
//		ProrToolExtension prorToolExtension = ConfigurationFactory.eINSTANCE.createProrToolExtension();
		specViewConfig = ConfigurationUtil.createSpecViewConfiguration(
		specification, editingDomain);
//		prorToolExtension.getSpecViewConfigurations().add(specViewConfig);
//		ReqIFToolExtensionUtil.addToolExtension(reqif, prorToolExtension);
		
		contentProvider = new ProrAgileGridContentProvider(specification, specViewConfig);
		
		

	}

	@Test
	public void firstTest() throws Exception {
		ArrayList<Map<String, ?>> specMapList = new ArrayList<Map<String, ?>>();

		printRecursive(new StringBuilder(), specViewConfig, "",
				specification.getChildren(), adapterFactory, specMapList);
		Reporter.makeMapReport(specMapList);
	}

	private static void jasperPrint(EList<SpecHierarchy> children) {
		for (SpecHierarchy child : children) {
			if (child.getObject() != null) {
				SpecObject specObj = child.getObject();

			}
		}
	}

	private static void printRecursive(StringBuilder html,
			ProrSpecViewConfiguration config, String indent,
			EList<SpecHierarchy> children, AdapterFactory adapterFactory,
			ArrayList<Map<String, ?>> specMapList) {

		for (SpecHierarchy child : children) {
			if (child.getObject() != null) {

				SpecObject specObject = child.getObject();
				boolean first = true;
				html.append("<tr>");
				HashMap<String, String> specMap = new HashMap<String, String>();
				for (Column col : config.getColumns()) {
					html.append("<td valign='top'>");

					// Handle indenting
					if (first) {
//						html.append("<div style='margin-left: " + (indent * 20)
//								+ "px;'>");
						
//						indent = indent + "    ";

					}
					System.out.println("column");
					AttributeValue av = ReqIF10Util.getAttributeValueForLabel(
							specObject, col.getLabel());

					System.out.println("----Column Label: " + col.getLabel());
					System.out.println("----Content     : "
							+ getDefaultValue(av, indent));
					specMap.put(col.getLabel(), getDefaultValue(av, indent));

					DatatypeDefinition dd = ReqIF10Util
							.getDatatypeDefinition(av);

					// null
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
								html.append(content);

							} else {

								html.append(getDefaultValue(av, indent));
							}
						}

					} else {
						html.append(getDefaultValue(av, indent));

					}

					if (first) {
						first = false;
						html.append("</div>");
					}
					html.append("</td>");
				}

				specMapList.add(specMap);
				html.append("</tr>\n");
			}
			printRecursive(html, config, indent + "      ", child.getChildren(),
					adapterFactory, specMapList);
		}
	}

	private static String getDefaultValue(AttributeValue av, String indent) {
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
				System.out.println("xhtmlString" + xhtmlString);
				xhtmlString = xhtmlString.replace("<xhtml:", "<");
				xhtmlString = xhtmlString.replace("</xhtml:", "</");
				textValue = xhtmlString;
			} catch (IOException e) {
			}
		} else {
			textValue = value.toString();
		}

		return indent + textValue;
	}

}
