package org.eclipse.rmf.reqif10.pror.report.oda.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.rmf.reqif10.AttributeDefinition;
import org.eclipse.rmf.reqif10.AttributeDefinitionBoolean;
import org.eclipse.rmf.reqif10.AttributeDefinitionDate;
import org.eclipse.rmf.reqif10.AttributeDefinitionEnumeration;
import org.eclipse.rmf.reqif10.AttributeDefinitionInteger;
import org.eclipse.rmf.reqif10.AttributeDefinitionReal;
import org.eclipse.rmf.reqif10.AttributeDefinitionString;
import org.eclipse.rmf.reqif10.AttributeDefinitionXHTML;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.EnumValue;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.XhtmlContent;
import org.eclipse.rmf.reqif10.common.util.ReqIF10Util;
import org.eclipse.rmf.reqif10.common.util.ReqIF10XhtmlUtil;
import org.eclipse.rmf.reqif10.pror.configuration.Column;
import org.eclipse.rmf.reqif10.pror.configuration.ProrSpecViewConfiguration;
import org.eclipse.rmf.reqif10.pror.util.ProrXhtmlSimplifiedHelper;

public class ResultSetMetaHelper {

	private Map<String, Integer> columnTypes;
	private List<String[]> matrix;

	public Map<String, Integer> getColumnTypes() {
		return columnTypes;
	}
	public List<String[]> getMatrix() {
		return matrix;
	}
	
	public ResultSetMetaHelper(EList<SpecHierarchy> children,
			ProrSpecViewConfiguration config) {
		columnTypes = new HashMap<String, Integer>();
		List<String[]> m = new ArrayList<String[]>();
		matrix = fillMatrix(children, config, m);
	}

	private List<String[]> fillMatrix(EList<SpecHierarchy> children,
			ProrSpecViewConfiguration config, List<String[]> matrix) {
		fillRecursiv(children, config, matrix, 0, 0);
		return matrix;
	}

	private void fillRecursiv(EList<SpecHierarchy> children,
			ProrSpecViewConfiguration config, List<String[]> matrix, int rowId,
			int indent) {

		Iterator<SpecHierarchy> it = children.iterator();

		//
		// List<Integer> columnTypes = new ArrayList<Integer>();
		//

		while (it.hasNext()) {
			SpecHierarchy specH = it.next();
			SpecObject specObj = specH.getObject();

			matrix.add(new String[config.getColumns().size()]);

			if (specObj != null) {

				int columnId = 0;
				for (Column column : config.getColumns()) {
					AttributeValue av = ReqIF10Util.getAttributeValueForLabel(
							specObj, column.getLabel());

					matrix.get(rowId)[columnId] = multiplyString(" --", indent)
							+ getDefaultValue(av);
					columnId++;

					// //// filling columntypes

					int columnType = -1;
					AttributeValue value = ReqIF10Util
							.getAttributeValueForLabel(specObj,
									column.getLabel());

					if(value != null){
					AttributeDefinition attributeDefinition = ReqIF10Util
							.getAttributeDefinition(value);
					if (attributeDefinition == null)
						columnType = -1;
					else if (attributeDefinition instanceof AttributeDefinitionBoolean)
						columnType = java.sql.Types.BOOLEAN;
					else if (attributeDefinition instanceof AttributeDefinitionDate)
						columnType = java.sql.Types.DATE;
					else if (attributeDefinition instanceof AttributeDefinitionInteger)
						columnType = java.sql.Types.INTEGER;
					else if (attributeDefinition instanceof AttributeDefinitionReal)
						columnType = java.sql.Types.REAL;
					else if (attributeDefinition instanceof AttributeDefinitionString)
						columnType = java.sql.Types.CHAR;
					else if (attributeDefinition instanceof AttributeDefinitionXHTML)
						columnType = java.sql.Types.OTHER;
					else if (attributeDefinition instanceof AttributeDefinitionEnumeration)
						columnType = java.sql.Types.OTHER;
					else
						throw new IllegalArgumentException(
								"Type not supported: " + attributeDefinition); //$NON-NLS-1$
					}
					if (columnType != -1)
						if (!columnTypes.containsKey(column.getLabel()))
							columnTypes.put(column.getLabel(), columnType);

					// //
					System.out.println(column.getLabel());
				}
			}
			rowId++;
			fillRecursiv(specH.getChildren(), config, matrix, rowId, indent + 1);
		}

	}

	private String multiplyString(String str, int times) {
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
