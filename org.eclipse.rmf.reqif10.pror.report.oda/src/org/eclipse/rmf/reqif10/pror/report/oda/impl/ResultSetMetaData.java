/*
 *************************************************************************
 * Copyright (c) 2013 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package org.eclipse.rmf.reqif10.pror.report.oda.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.datatools.connectivity.oda.IResultSetMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.SpecElementWithAttributes;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.common.util.ReqIF10Util;
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

/**
 * Implementation class of IResultSetMetaData for an ODA runtime driver. <br>
 * For demo purpose, the auto-generated method stubs have hard-coded
 * implementation that returns a pre-defined set of meta-data and query results.
 * A custom ODA driver is expected to implement own data source specific
 * behavior in its place.
 */
public class ResultSetMetaData implements IResultSetMetaData {
	private String query;
	// ReqIF data
	private ReqIF reqif;
	// ReqIF Header data
	private String[] comment;
	private String creationTime;
	private String identifier;
	private String repositoryId;
	private String reqIFToolId;
	private String reqIFVersion;
	private String sourceToolId;
	private String title;

	// ReqIFToolExtension data
	private String[] columnNames;
	private AdapterFactoryEditingDomain editingDomain;
	private EList<Column> columns;
	private HashMap<String, Integer> columnIdxMap;
	private ResultSetMetaHelper rsetHelper;

	public ResultSetMetaData(Connection connection, String query) {

		this.query = query;
		this.reqif = connection.getReqif();

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

		ProrSpecViewConfiguration config = ConfigurationUtil
				.createSpecViewConfiguration(this.reqif.getCoreContent()
						.getSpecifications().get(0), editingDomain);

		columns = config.getColumns();
		
		
		Specification spec = reqif.getCoreContent().getSpecifications().get(0);
		rsetHelper = new ResultSetMetaHelper(spec.getChildren(), config);
		
		
		
		columnIdxMap = new HashMap<String, Integer>();

		int i = 0;
		for (Column column : columns) {
			columnIdxMap.put(column.getLabel(), i);
			i++;
		}
	}

	public int getColumnCount() throws OdaException {
		return columns.size();
	}

	public String getColumnName(int index) throws OdaException {
		return columns.get(index - 1).getLabel();
	}

	public String getColumnLabel(int index) throws OdaException {
		return getColumnName(index); // default
	}

	public int getColumnType(int index) throws OdaException {
		String currentColumnName = columns.get(index - 1).getLabel();
		Map<String, Integer> columnTypes = rsetHelper.getColumnTypes();
		
		System.out.println(columnTypes.get(currentColumnName));
		return columnTypes.get(currentColumnName);
	}

	public String getColumnTypeName(int index) throws OdaException {
		int nativeTypeCode = getColumnType(index);
		return Driver.getNativeDataTypeName(nativeTypeCode);
	}

	public int getColumnDisplayLength(int index) throws OdaException {
		// TODO replace with data source specific implementation

		// hard-coded for demo purpose
		return 8;
	}

	public int getPrecision(int index) throws OdaException {
		// TODO Auto-generated method stub
		return -1;
	}

	public int getScale(int index) throws OdaException {
		// TODO Auto-generated method stub
		return -1;
	}

	public int isNullable(int index) throws OdaException {
		// TODO Auto-generated method stub
		return IResultSetMetaData.columnNullableUnknown;
	}

}
