/*
 *************************************************************************
 * Copyright (c) 2013 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package org.eclipse.rmf.reqif10.pror.report.oda.impl;

import org.eclipse.datatools.connectivity.oda.IParameterMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;

/**
 * Implementation class of IParameterMetaData for an ODA runtime driver.
 * <br>
 * For demo purpose, the auto-generated method stubs have
 * hard-coded implementation that returns a pre-defined set
 * of meta-data and query results.
 * A custom ODA driver is expected to implement own data source specific
 * behavior in its place. 
 */
public class ParameterMetaData implements IParameterMetaData 
{
	public int getParameterCount() throws OdaException 
	{
        return 1;
	}

	public int getParameterMode( int param ) throws OdaException 
	{
		return IParameterMetaData.parameterModeIn;
	}

    public String getParameterName( int param ) throws OdaException
    {
        return null;    // name is not available
    }

	public int getParameterType( int param ) throws OdaException 
	{
        return java.sql.Types.CHAR;   // as defined in data set extension manifest
	}

	public String getParameterTypeName( int param ) throws OdaException 
	{
        int nativeTypeCode = getParameterType( param );
        return Driver.getNativeDataTypeName( nativeTypeCode );
	}

	public int getPrecision( int param ) throws OdaException 
	{
		return -1;
	}

	public int getScale( int param ) throws OdaException 
	{
		return -1;
	}

	public int isNullable( int param ) throws OdaException 
	{
		return IParameterMetaData.parameterNullableUnknown;
	}

}
