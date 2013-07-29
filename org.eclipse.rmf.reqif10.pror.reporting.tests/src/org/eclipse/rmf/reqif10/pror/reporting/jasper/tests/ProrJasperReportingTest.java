package org.eclipse.rmf.reqif10.pror.reporting.jasper.tests;

import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.PositionTypeEnum;
import net.sf.jasperreports.engine.type.ResetTypeEnum;

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
	public void init() throws URISyntaxException {

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
		// ProrToolExtension prorToolExtension =
		// ConfigurationFactory.eINSTANCE.createProrToolExtension();
		specViewConfig = ConfigurationUtil.createSpecViewConfiguration(
				specification, editingDomain);
		// prorToolExtension.getSpecViewConfigurations().add(specViewConfig);
		// ReqIFToolExtensionUtil.addToolExtension(reqif, prorToolExtension);

		contentProvider = new ProrAgileGridContentProvider(specification,
				specViewConfig);

	}

	@Test
	public void firstTest() throws Exception {
		ArrayList<Map<String, ?>> specMapList = new ArrayList<Map<String, ?>>();

		// printRecursive(new StringBuilder(), specViewConfig, "",
		// specification.getChildren(), adapterFactory, specMapList);
		fillRecursive(specification.getChildren(), specViewConfig, specMapList);
		Reporter.makeMapReport(specMapList);
	}

	private static void jasperPrint(EList<SpecHierarchy> children) {
		for (SpecHierarchy child : children) {
			if (child.getObject() != null) {
				SpecObject specObj = child.getObject();

			}
		}
	}

	private static ArrayList<Map<String, ?>> fillRecursive(
			EList<SpecHierarchy> children, ProrSpecViewConfiguration config,
			ArrayList<Map<String, ?>> specMapList) {
		for (SpecHierarchy child : children) {
			if (child.getObject() != null) {
				SpecObject specObj = child.getObject();
				HashMap<String, String> specMap = new HashMap<String, String>();

				for (Column col : config.getColumns()) {

					AttributeValue av = ReqIF10Util.getAttributeValueForLabel(
							specObj, col.getLabel());

					specMap.put(col.getLabel(), getDefaultValue(av));

				}

				specMapList.add(specMap);
			}
			fillRecursive(child.getChildren(), config, specMapList);

		}
		return specMapList;
	}

	private static String getDefaultValue(AttributeValue av) {

		return getDefaultValue(av, "      ");

	}

	private static String getDefaultValue(AttributeValue av, String indent) {
		Object value = av == null ? null : ReqIF10Util.getTheValue(av);
		String textValue;
		if (value == null) {
			textValue = "";
		} else if (value instanceof List<?>) {
			textValue = "";
			for (Iterator<EnumValue> i = ((List<EnumValue>) value).iterator(); i
					.hasNext();) {
				textValue += i.next().getLongName();

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

	private static JasperDesign addStringField(JasperDesign jaspDesign,
			String fieldName) throws JRException {
		JRDesignField field = new JRDesignField();
		field.setName(fieldName);
		field.setValueClass(java.lang.String.class);
		jaspDesign.addField(field);
		return jaspDesign;

	}

	public static JRDesignField createField(String fieldName) {
		JRDesignField field = new JRDesignField();
		field.setName(fieldName);
		field.setValueClass(java.lang.Integer.class);
		return field;
	}

	public static JRDesignLine createLine(int x, int y) {
		JRDesignLine line = new JRDesignLine();
		line.setX(0);
		line.setY(19);
		line.setWidth(515);
		line.setHeight(0);
		return line;
	}

	public static JRDesignTextField createTextField(int x, int y) {
		JRDesignTextField textField = new JRDesignTextField();
		textField.setX(x);
		textField.setY(y);
		textField.setWidth(515);
		textField.setHeight(15);
		textField.setBackcolor(new Color(0xC0, 0xC0, 0xC0));
		textField.setMode(ModeEnum.OPAQUE);
		textField.setHorizontalAlignment(HorizontalAlignEnum.LEFT);

		return textField;
	}

	public static JasperDesign getJasperDesign() throws JRException {

		// JasperDesign
		JasperDesign jasperDesign = new JasperDesign();
		jasperDesign.setName("ReqIF_Report");
		jasperDesign.setPageWidth(595);
		jasperDesign.setPageHeight(842);
		jasperDesign.setColumnWidth(515);
		jasperDesign.setColumnSpacing(0);
		jasperDesign.setLeftMargin(40);
		jasperDesign.setRightMargin(40);
		jasperDesign.setTopMargin(50);
		jasperDesign.setBottomMargin(50);

		// Fonts
		JRDesignStyle normalStyle = new JRDesignStyle();
		normalStyle.setName("Sans_Normal");
		normalStyle.setDefault(true);
		normalStyle.setFontName("Serif");
		normalStyle.setFontSize(12);
		normalStyle.setPdfFontName("Helvetica");
		normalStyle.setPdfEncoding("Cp1252");
		normalStyle.setPdfEmbedded(false);
		jasperDesign.addStyle(normalStyle);

		JRDesignStyle boldStyle = new JRDesignStyle();
		boldStyle.setName("Sans_Bold");
		boldStyle.setFontName("Serif");
		boldStyle.setFontSize(12);
		boldStyle.setBold(true);
		boldStyle.setPdfFontName("Helvetica-Bold");
		boldStyle.setPdfEncoding("Cp1252");
		boldStyle.setPdfEmbedded(false);
		jasperDesign.addStyle(boldStyle);

		JRDesignStyle italicStyle = new JRDesignStyle();
		italicStyle.setName("Sans_Italic");
		italicStyle.setFontName("DejaVu Sans");
		italicStyle.setFontSize(12);
		italicStyle.setItalic(true);
		italicStyle.setPdfFontName("Helvetica-Oblique");
		italicStyle.setPdfEncoding("Cp1252");
		italicStyle.setPdfEmbedded(false);
		jasperDesign.addStyle(italicStyle);

		// Parameters
		JRDesignParameter parameter = new JRDesignParameter();
		parameter.setName("ReportTitle");
		parameter.setValueClass(java.lang.String.class);
		jasperDesign.addParameter(parameter);

		// Query
		JRDesignQuery query = new JRDesignQuery();
		query.setText("");
		jasperDesign.setQuery(query);

		// Fields
		JRDesignField field = createField("Id");
		jasperDesign.addField(field);

		field = createField("FirstName");
		jasperDesign.addField(field);

		field = createField("LastName");
		jasperDesign.addField(field);
		
		field = createField("City");
		jasperDesign.addField(field);
		
		field = createField("Street");
		jasperDesign.addField(field);
		// Bands

		JRDesignBand band = new JRDesignBand();
		band.setHeight(20);
		
		JRDesignTextField textField = createTextField(0, 4);
		
		band.addElement(textField);
		JRDesignLine line = createLine(0, 19);
		band.addElement(line);

		
		band = new JRDesignBand();
		band.setHeight(20);
		line = createLine(0, -1);
		band.addElement(line);

		JRDesignStaticText staticText = new JRDesignStaticText();
		staticText.setX(400);
		staticText.setY(0);
		staticText.setWidth(60);
		staticText.setHeight(15);
		staticText.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
		staticText.setStyle(boldStyle);
		staticText.setText("Count : ");
//		band.addElement(staticText);
		textField = new JRDesignTextField();
		textField.setX(460);
		textField.setY(0);
		textField.setWidth(30);
		textField.setHeight(15);
		textField.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
		textField.setStyle(boldStyle);
//		band.addElement(textField);

		// Title
		band = new JRDesignBand();
		band.setHeight(50);
		line = new JRDesignLine();
		line.setX(0);
		line.setY(0);
		line.setWidth(515);
		line.setHeight(0);
		band.addElement(line);
		textField = new JRDesignTextField();
		textField.setBlankWhenNull(true);
		textField.setX(0);
		textField.setY(10);
		textField.setWidth(515);
		textField.setHeight(30);
		textField.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
		textField.setStyle(normalStyle);
		textField.setFontSize(22);
		band.addElement(textField);
		jasperDesign.setTitle(band);

		// Page header
		band = new JRDesignBand();
		band.setHeight(20);
		JRDesignFrame frame = new JRDesignFrame();
		frame.setX(0);
		frame.setY(5);
		frame.setWidth(515);
		frame.setHeight(15);
		frame.setForecolor(new Color(0x33, 0x33, 0x33));
		frame.setBackcolor(new Color(0x33, 0x33, 0x33));
		frame.setMode(ModeEnum.OPAQUE);
		band.addElement(frame);

		staticText = new JRDesignStaticText();
		staticText.setX(0);
		staticText.setY(0);
		staticText.setWidth(55);
		staticText.setHeight(15);
		staticText.setForecolor(Color.white);
		staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
		staticText.setMode(ModeEnum.OPAQUE);
		staticText.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
		staticText.setStyle(boldStyle);
		staticText.setText("ID");
		frame.addElement(staticText);
		staticText = new JRDesignStaticText();
		staticText.setX(55);
		staticText.setY(0);
		staticText.setWidth(205);
		staticText.setHeight(15);
		staticText.setForecolor(Color.white);
		staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
		staticText.setMode(ModeEnum.OPAQUE);
		staticText.setStyle(boldStyle);
		staticText.setText("Name");
		frame.addElement(staticText);
		staticText = new JRDesignStaticText();
		staticText.setX(260);
		staticText.setY(0);
		staticText.setWidth(255);
		staticText.setHeight(15);
		staticText.setForecolor(Color.white);
		staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
		staticText.setMode(ModeEnum.OPAQUE);
		staticText.setStyle(boldStyle);
		staticText.setText("Street");
		frame.addElement(staticText);
		jasperDesign.setPageHeader(band);

		// Column header
		band = new JRDesignBand();
		jasperDesign.setColumnHeader(band);

		// Detail
		band = new JRDesignBand();
		band.setHeight(20);
		textField = new JRDesignTextField();
		textField.setX(0);
		textField.setY(4);
		textField.setWidth(50);
		textField.setHeight(15);
		textField.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
		textField.setStyle(normalStyle);
		band.addElement(textField);
		textField = new JRDesignTextField();
		textField.setStretchWithOverflow(true);
		textField.setX(55);
		textField.setY(4);
		textField.setWidth(200);
		textField.setHeight(15);
		textField.setPositionType(PositionTypeEnum.FLOAT);
		textField.setStyle(normalStyle);
		band.addElement(textField);
		textField = new JRDesignTextField();
		textField.setStretchWithOverflow(true);
		textField.setX(260);
		textField.setY(4);
		textField.setWidth(255);
		textField.setHeight(15);
		textField.setPositionType(PositionTypeEnum.FLOAT);
		textField.setStyle(normalStyle);
		band.addElement(textField);

		line = new JRDesignLine();
		line.setX(0);
		line.setY(19);
		line.setWidth(515);
		line.setHeight(0);
		line.setForecolor(new Color(0x80, 0x80, 0x80));
		line.setPositionType(PositionTypeEnum.FLOAT);
		band.addElement(line);

		((JRDesignSection) jasperDesign.getDetailSection()).addBand(band);

		// Column footer
		band = new JRDesignBand();
		// jasperDesign.setColumnFooter(band);

		// Page footer
		band = new JRDesignBand();
		// jasperDesign.setPageFooter(band);

		// Summary
		band = new JRDesignBand();
		// jasperDesign.setSummary(band);

		return jasperDesign;
	}

}
