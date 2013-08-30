/*
 *************************************************************************
 * Copyright (c) 2013 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package org.eclipse.rmf.reqif10.pror.report.oda.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.datatools.connectivity.oda.IBlob;
import org.eclipse.datatools.connectivity.oda.IClob;
import org.eclipse.datatools.connectivity.oda.IResultSet;
import org.eclipse.datatools.connectivity.oda.IResultSetMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.rmf.reqif10.AttributeDefinition;
import org.eclipse.rmf.reqif10.AttributeDefinitionBoolean;
import org.eclipse.rmf.reqif10.AttributeDefinitionDate;
import org.eclipse.rmf.reqif10.AttributeDefinitionEnumeration;
import org.eclipse.rmf.reqif10.AttributeDefinitionInteger;
import org.eclipse.rmf.reqif10.AttributeDefinitionReal;
import org.eclipse.rmf.reqif10.AttributeDefinitionString;
import org.eclipse.rmf.reqif10.AttributeDefinitionXHTML;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.AttributeValueBoolean;
import org.eclipse.rmf.reqif10.AttributeValueDate;
import org.eclipse.rmf.reqif10.AttributeValueEnumeration;
import org.eclipse.rmf.reqif10.AttributeValueInteger;
import org.eclipse.rmf.reqif10.AttributeValueReal;
import org.eclipse.rmf.reqif10.AttributeValueString;
import org.eclipse.rmf.reqif10.AttributeValueXHTML;
import org.eclipse.rmf.reqif10.EnumValue;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIF10Factory;
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
import org.eclipse.rmf.reqif10.pror.report.oda.impl.test.OdaUtilTest;
import org.eclipse.rmf.reqif10.pror.util.ConfigurationUtil;
import org.eclipse.rmf.reqif10.pror.util.ProrXhtmlSimplifiedHelper;
import org.eclipse.rmf.serialization.ReqIFResourceSetImpl;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.util.ULocale;

/**
 * Implementation class of IResultSet for an ODA runtime driver. <br>
 * For demo purpose, the auto-generated method stubs have hard-coded
 * implementation that returns a pre-defined set of meta-data and query results.
 * A custom ODA driver is expected to implement own data source specific
 * behavior in its place.
 */
public class ResultSet implements IResultSet {
	private int maxRows;
	private int currentRowId;
	private static final int ROW_INITIAL_VALUE = -1;

	private Connection connection;
	private String query;
	private ReqIF reqif;
	private EList<Specification> specifications;
	
	private static ULocale JRE_DEFAULT_LOCALE = ULocale.getDefault();

	private Specification specification;

	private List<String[]> matrix;
	private AdapterFactoryEditingDomain editingDomain;

	
	public ResultSet(Connection connection) {
		currentRowId = ROW_INITIAL_VALUE;
		this.connection = connection;
		this.reqif = connection.getReqif();
		this.specifications = reqif.getCoreContent().getSpecifications();
		// at first we'll only consider the first spec of an ReqIF file
		this.specification = specifications.get(0);
		
		
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
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory,
				commandStack, new ReqIFResourceSetImpl());
		
		initiateMatrix();
		
	}

	public IResultSetMetaData getMetaData() throws OdaException {
		return new ResultSetMetaData(this.connection, this.query);
	}

	public void setMaxRows(int max) throws OdaException {
		this.maxRows = max;
	}

	/**
	 * Returns the maximum number of rows that can be fetched from this result
	 * set.
	 * 
	 * @return the maximum number of rows to fetch.
	 */
	protected int getMaxRows() {
		// get Max SpecEntry length
		return maxRows;
	}

	private void initiateMatrix() {

		List<String[]> m = new ArrayList<String[]>();
		matrix = fillMatrix(m);
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
			
			
			matrix.add(new String[config.getColumns().size()]);

			if (specObj != null) {
				
				
				int columnId = 0;
				for (Column column : config.getColumns()) {
					AttributeValue av = ReqIF10Util.getAttributeValueForLabel(
							specObj, column.getLabel());

					matrix.get(rowId)[columnId] = multiplyString("", indent) + getDefaultValue(av);
					columnId++;
					
				}
			}
			rowId++;
			fillRecursiv(specH.getChildren(), config, matrix, rowId, indent + 1);
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
	
	
	public boolean next() throws OdaException
	{
        
        int maxRows = matrix.size();
        
        if( currentRowId < maxRows - 1 )
        {
            currentRowId++;
            if (matrix.get(currentRowId) != null)
            return true;
            else
            	return false;
        }
        
        return false;        
	}

	public void close() throws OdaException {
		currentRowId = 0; // reset row counter
	}

	public int getRow() throws OdaException {
		return currentRowId;
	}

	public String getString(int index) throws OdaException {
		return matrix.get(currentRowId)[index - 1];
	}

	public String getString(String columnName) throws OdaException {
		return getString(findColumn(columnName));
	}

	public int getInt(int index) throws OdaException {
		String entry = matrix.get(currentRowId)[index - 1];
		return Integer.parseInt(entry);
	}

	public int getInt(String columnName) throws OdaException {
		return Integer.parseInt(getString(columnName));
	}

	public double getDouble(int index) throws OdaException {
		throw new UnsupportedOperationException();
	}

	public double getDouble(String columnName) throws OdaException {
		return getDouble(findColumn(columnName));
	}

	public BigDecimal getBigDecimal(int index) throws OdaException {
		throw new UnsupportedOperationException();
	}

	public BigDecimal getBigDecimal(String columnName) throws OdaException {
		return getBigDecimal(findColumn(columnName));
	}

	public Date getDate(int index) throws OdaException {
		throw new UnsupportedOperationException();
	}

	public Date getDate(String columnName) throws OdaException {
		return getDate(findColumn(columnName));
	}
	public Time getTime(int index) throws OdaException {
		throw new UnsupportedOperationException();
	}

	public Time getTime(String columnName) throws OdaException {
		return getTime(findColumn(columnName));
	}

	public Timestamp getTimestamp(int index) throws OdaException {
		throw new UnsupportedOperationException();
	}
	public Timestamp getTimestamp(String columnName) throws OdaException {
		return getTimestamp(findColumn(columnName));
	}

	public IBlob getBlob(int index) throws OdaException {
		throw new UnsupportedOperationException();
	}

	public IBlob getBlob(String columnName) throws OdaException {
		return getBlob(findColumn(columnName));
	}

	public IClob getClob(int index) throws OdaException {
		throw new UnsupportedOperationException();
	}

	public IClob getClob(String columnName) throws OdaException {
		return getClob(findColumn(columnName));
	}

	public boolean getBoolean(int index) throws OdaException {
		throw new UnsupportedOperationException();
	}

	public boolean getBoolean(String columnName) throws OdaException {
		return getBoolean(findColumn(columnName));
	}

	public Object getObject(int index) throws OdaException {
		throw new UnsupportedOperationException();
	}

	public Object getObject(String columnName) throws OdaException {
		return getObject(findColumn(columnName));
	}

	public boolean wasNull() throws OdaException {
		return false;
	}

	@Override
	public int findColumn(String columnName) throws OdaException {
		ProrSpecViewConfiguration config = ConfigurationUtil
				.createSpecViewConfiguration(specification, editingDomain);

		EList<Column> columns = config.getColumns();
		Set<String> columnSet = new HashSet<String>();
		Map<String,Integer> columnIdxMap = new HashMap<String, Integer>();
		
		int idx = 0;
		for(Column col : columns){
			columnSet.add(col.getLabel());
			columnIdxMap.put(col.getLabel(), idx);
			idx++;
		}
		
		
//		int columnId = 1; // dummy column id
//		if (columnName == null || columnName.length() == 0)
//			return columnId;
		if(columnSet.contains(columnName))
		{
			return columnIdxMap.get(columnName);
		}
//		String lastChar = columnName.substring(columnName.length() - 1, 1);
//		try {
//			columnId = Integer.parseInt(lastChar);
//		} catch (NumberFormatException e) {
//			// ignore, use dummy column id
//		}
		return -1;
	}

}
