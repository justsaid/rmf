package org.eclipse.rmf.reqif10.pror.report.oda.impl.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.EnumValue;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.XhtmlContent;
import org.eclipse.rmf.reqif10.common.util.ReqIF10Util;
import org.eclipse.rmf.reqif10.common.util.ReqIF10XhtmlUtil;
import org.eclipse.rmf.reqif10.pror.configuration.Column;
import org.eclipse.rmf.reqif10.pror.configuration.ProrSpecViewConfiguration;
import org.eclipse.rmf.reqif10.pror.configuration.util.ConfigurationAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.headline.util.HeadlineAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.id.util.IdAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.linewrap.util.LinewrapAdapterFactory;
import org.eclipse.rmf.reqif10.pror.provider.ReqIF10ItemProviderAdapterFactory;
import org.eclipse.rmf.reqif10.pror.report.oda.impl.Connection;
import org.eclipse.rmf.reqif10.pror.util.ConfigurationUtil;
import org.eclipse.rmf.reqif10.pror.util.ProrXhtmlSimplifiedHelper;
import org.eclipse.rmf.serialization.ReqIFResourceSetImpl;
import org.junit.Before;
import org.junit.Test;

public class OdaUtilTest {

	private Specification specification;
	private EList<Specification> specifications;
	private AdapterFactoryEditingDomain editingDomain;
	ComposedAdapterFactory adapterFactory;

	
	public ComposedAdapterFactory getAdapterFactory() {
		return adapterFactory;
	}
	public AdapterFactoryEditingDomain getEditingDomain() {
		return editingDomain;
	}
	
	@Before
	public void init() throws FileNotFoundException, IOException {

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

		BasicCommandStack commandStack = new BasicCommandStack();
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory,
				commandStack, new ReqIFResourceSetImpl());

		// iterate reqif dump folder

		File f = new File("reqif/BasicReqs.reqif");
		System.out.println("===> ReqIF Dump folder exists? ===> " + f.exists());

		// File[] files = f.listFiles();
		// f = files[0];

		if (f.exists()) {

			ReqIF reqif = Connection.loadReqIFData(f);

			if (reqif != null) {

				specifications = reqif.getCoreContent().getSpecifications();

				// for testing purpose we take only the first specification
				specification = specifications.get(0);
			}

		}
	}

	public void printSpecHierarchy(Specification spec,
			ProrSpecViewConfiguration config) {
		Iterator<SpecHierarchy> it = spec.getChildren().iterator();

		while (it.hasNext()) {
			SpecHierarchy specH = it.next();
			SpecObject specObj = specH.getObject();
			if (specObj != null) {

				for (Column column : config.getColumns()) {

					AttributeValue av = ReqIF10Util.getAttributeValueForLabel(
							specObj, column.getLabel());

					System.out.println(getDefaultValue(av));

				}
			}
		}
	}

	public List<String[]> fillMatrix(List<String[]> matrix) {

		ProrSpecViewConfiguration config = ConfigurationUtil
				.createSpecViewConfiguration(specification, editingDomain);

		fillRecursiv(specification.getChildren(), config, matrix, 0, 0);
		return matrix;
	}

	public void fillRecursiv(EList<SpecHierarchy> children,
			ProrSpecViewConfiguration config, List<String[]> matrix, int rowId, int indent) {

		Iterator<SpecHierarchy> it = children.iterator();

		while (it.hasNext()) {
			SpecHierarchy specH = it.next();
			SpecObject specObj = specH.getObject();
			if (specObj != null) {
				
				matrix.add(new String[config.getColumns().size()]);
				
				int columnId = 0;
				for (Column column : config.getColumns()) {
					AttributeValue av = ReqIF10Util.getAttributeValueForLabel(
							specObj, column.getLabel());

					matrix.get(rowId)[columnId] = multiplyString("    ", indent) + getDefaultValue(av);
					columnId++;
					
					System.out.println( column.getLabel());
				}
			}
			rowId++;
			fillRecursiv(specH.getChildren(), config, matrix, rowId, indent + 1);
		}

	}

	@Test
	public void fillMatrixTest() {

		ProrSpecViewConfiguration config = ConfigurationUtil
				.createSpecViewConfiguration(specification, editingDomain);

		int columnSize = config.getColumns().size();
		System.out.println("Column size: " + columnSize);
		List<String[]> matrix = new ArrayList<String[]>();

		printMatrix(fillMatrix(matrix));

	}

	public void printMatrix(List<String[]> matrix) {
		for (int i = 0; i < matrix.size(); i++)
			for (int j = 0; j < matrix.get(i).length; j++) {
				System.out.println(i + " " + j + " " + matrix.get(i)[j]);
			}
	}
	
	
	private String multiplyString(String str, int times)
	{
		StringBuilder strb = new StringBuilder();
		for (int i = 0; i < times; i++) {
			strb.append(str);
		}
		return strb.toString();
	}
	private static String getDefaultValue(AttributeValue av) {
		Object value = av == null ? null : ReqIF10Util.getTheValue(av);
		String textValue;
		if (value == null) {
			textValue = "";
		} else if (value instanceof List<?>) {
			textValue = "";
			for (Iterator<?> i = ((List<?>) value).iterator(); i.hasNext();) {
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
}
