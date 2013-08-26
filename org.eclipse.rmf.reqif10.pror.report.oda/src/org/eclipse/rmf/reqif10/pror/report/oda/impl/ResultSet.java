/*
 *************************************************************************
 * Copyright (c) 2013 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package org.eclipse.rmf.reqif10.pror.report.oda.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.pror.configuration.Column;
import org.eclipse.rmf.reqif10.pror.configuration.ProrSpecViewConfiguration;
import org.eclipse.rmf.reqif10.pror.configuration.util.ConfigurationAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.headline.util.HeadlineAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.id.util.IdAdapterFactory;
import org.eclipse.rmf.reqif10.pror.presentation.linewrap.util.LinewrapAdapterFactory;
import org.eclipse.rmf.reqif10.pror.provider.ReqIF10ItemProviderAdapterFactory;
import org.eclipse.rmf.reqif10.pror.report.oda.impl.test.OdaUtilTest;
import org.eclipse.rmf.reqif10.pror.util.ConfigurationUtil;
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

	public void initiateMatrix() {

		OdaUtilTest util = new OdaUtilTest();
		List<String[]> m = new ArrayList<String[]>();
		matrix = util.fillMatrix(m);
	}

	public boolean next() throws OdaException
	{
        
        int maxRows = matrix.size();
        
        if( currentRowId < maxRows )
        {
            currentRowId++;
            if (matrix.get(currentRowId) != null)
            return true;
            else
            	return false;
        }
        
        return false;        
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#close()
	 */
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
		return Integer.parseInt(getString(index));
	}

	public int getInt(String columnName) throws OdaException {
		return getInt(getString(columnName));
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
