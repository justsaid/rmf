/*
 *************************************************************************
 * Copyright (c) 2013 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package org.eclipse.rmf.reqif10.pror.report.oda.impl;

import org.eclipse.datatools.connectivity.oda.IResultSetMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;
import org.eclipse.rmf.reqif10.ReqIF;

/**
 * Implementation class of IResultSetMetaData for an ODA runtime driver.
 * <br>
 * For demo purpose, the auto-generated method stubs have
 * hard-coded implementation that returns a pre-defined set
 * of meta-data and query results.
 * A custom ODA driver is expected to implement own data source specific
 * behavior in its place. 
 */
public class ResultSetMetaData implements IResultSetMetaData
{
	private Connection connection;
	private String query;
	//ReqIF data
	private ReqIF reqif;
	//ReqIF Header data
	private String[] comment;
	private String creationTime;
	private String identifier;
	private String repositoryId;
	private String reqIFToolId;
	private String reqIFVersion;
	private String sourceToolId;
	private String title;
	
	//ReqIFToolExtension data
	private String[] columnNames;
	
	public ResultSetMetaData(Connection connection, String query){
	
		this.connection = connection;
		this.query = query;
		
		
		this.reqif = connection.getReqif();
	}
    
	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnCount()
	 */
	public int getColumnCount() throws OdaException
	{

        return 2;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnName(int)
	 */
	public String getColumnName( int index ) throws OdaException
	{
        // TODO replace with data source specific implementation

        // hard-coded for demo purpose
        return "Column" + index;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnLabel(int)
	 */
	public String getColumnLabel( int index ) throws OdaException
	{
		return getColumnName( index );		// default
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnType(int)
	 */
	public int getColumnType( int index ) throws OdaException
	{
        // TODO replace with data source specific implementation

        // hard-coded for demo purpose
        if( index == 1 )
            return java.sql.Types.INTEGER;   // as defined in data set extension manifest
        return java.sql.Types.CHAR;          // as defined in data set extension manifest
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnTypeName(int)
	 */
	public String getColumnTypeName( int index ) throws OdaException
	{
        int nativeTypeCode = getColumnType( index );
        return Driver.getNativeDataTypeName( nativeTypeCode );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnDisplayLength(int)
	 */
	public int getColumnDisplayLength( int index ) throws OdaException
	{
        // TODO replace with data source specific implementation

        // hard-coded for demo purpose
		return 8;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getPrecision(int)
	 */
	public int getPrecision( int index ) throws OdaException
	{
        // TODO Auto-generated method stub
		return -1;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getScale(int)
	 */
	public int getScale( int index ) throws OdaException
	{
        // TODO Auto-generated method stub
		return -1;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#isNullable(int)
	 */
	public int isNullable( int index ) throws OdaException
	{
        // TODO Auto-generated method stub
		return IResultSetMetaData.columnNullableUnknown;
	}
    
}
