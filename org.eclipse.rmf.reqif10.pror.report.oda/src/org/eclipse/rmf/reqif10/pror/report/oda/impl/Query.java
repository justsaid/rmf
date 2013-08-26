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
import org.eclipse.datatools.connectivity.oda.IParameterMetaData;
import org.eclipse.datatools.connectivity.oda.IResultSet;
import org.eclipse.datatools.connectivity.oda.IResultSetMetaData;
import org.eclipse.datatools.connectivity.oda.IQuery;
import org.eclipse.datatools.connectivity.oda.OdaException;
import org.eclipse.datatools.connectivity.oda.SortSpec;
import org.eclipse.datatools.connectivity.oda.spec.QuerySpecification;

/**
 * Implementation class of IQuery for an ODA runtime driver.
 * <br>
 * For demo purpose, the auto-generated method stubs have
 * hard-coded implementation that returns a pre-defined set
 * of meta-data and query results.
 * A custom ODA driver is expected to implement own data source specific
 * behavior in its place. 
 */
public class Query implements IQuery
{
	private int m_maxRows;
    private String m_preparedText;
    
    private Connection connection;
    
    public Query(Connection connection) {
    	this.connection = connection;
    	
	}
	
	public void prepare( String queryText ) throws OdaException
	{
        m_preparedText = queryText;
	}
	
	public void setAppContext( Object context ) throws OdaException
	{
	}

	public void close() throws OdaException
	{
		this.connection = null;
        this.m_preparedText = null;
	}

	public IResultSetMetaData getMetaData() throws OdaException
	{
		return new ResultSetMetaData(this.connection, this.m_preparedText);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#executeQuery()
	 */
	public IResultSet executeQuery() throws OdaException
	{
        /* TODO Auto-generated method stub
         * Replace with implementation to return an instance 
         * based on this prepared query.
         */
		IResultSet resultSet = new ResultSet(this.connection);
		resultSet.setMaxRows( getMaxRows() );
		return resultSet;
	}

	public void setProperty( String name, String value ) throws OdaException
	{
		// do nothing; assumes no data set query property
	}

	public void setMaxRows( int max ) throws OdaException
	{
	    m_maxRows = max;
	}

	public int getMaxRows() throws OdaException
	{
		return m_maxRows;
	}

	public void clearInParameters() throws OdaException
	{
	}

	public void setInt( String parameterName, int value ) throws OdaException
	{
	}

	public void setInt( int parameterId, int value ) throws OdaException
	{
	}

	public void setDouble( String parameterName, double value ) throws OdaException
	{
	}

	public void setDouble( int parameterId, double value ) throws OdaException
	{
	}

	public void setBigDecimal( String parameterName, BigDecimal value ) throws OdaException
	{
	}

	public void setBigDecimal( int parameterId, BigDecimal value ) throws OdaException
	{
	}

	public void setString( String parameterName, String value ) throws OdaException
	{
	}

	public void setString( int parameterId, String value ) throws OdaException
	{
	}

	public void setDate( String parameterName, Date value ) throws OdaException
	{
	}

	public void setDate( int parameterId, Date value ) throws OdaException
	{
	}

	public void setTime( String parameterName, Time value ) throws OdaException
	{
	}

	public void setTime( int parameterId, Time value ) throws OdaException
	{
	}

	public void setTimestamp( String parameterName, Timestamp value ) throws OdaException
	{
	}

	public void setTimestamp( int parameterId, Timestamp value ) throws OdaException
	{
	}

    public void setBoolean( String parameterName, boolean value )
            throws OdaException
    {
    }

    public void setBoolean( int parameterId, boolean value )
            throws OdaException
    {
    }

    public void setObject( String parameterName, Object value )
            throws OdaException
    {
    }
    
    public void setObject( int parameterId, Object value ) throws OdaException
    {
    }
    
    public void setNull( String parameterName ) throws OdaException
    {
    }

    public void setNull( int parameterId ) throws OdaException
    {
    }

	public int findInParameter( String parameterName ) throws OdaException
	{
		return 0;
	}

	public IParameterMetaData getParameterMetaData() throws OdaException
	{
        /* TODO Auto-generated method stub
         * Replace with implementation to return an instance 
         * based on this prepared query.
         */
		return new ParameterMetaData();
	}

	public void setSortSpec( SortSpec sortBy ) throws OdaException
	{
		// only applies to sorting, assumes not supported
        throw new UnsupportedOperationException();
	}

	public SortSpec getSortSpec() throws OdaException
	{
		return null;
	}

    public void setSpecification( QuerySpecification querySpec )
            throws OdaException, UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public QuerySpecification getSpecification()
    {
        return null;
    }

    public String getEffectiveQueryText()
    {
        // TODO Auto-generated method stub
        return m_preparedText;
    }

    public void cancel() throws OdaException, UnsupportedOperationException
    {
        // assumes unable to cancel while executing a query
        throw new UnsupportedOperationException();
    }
    
}
