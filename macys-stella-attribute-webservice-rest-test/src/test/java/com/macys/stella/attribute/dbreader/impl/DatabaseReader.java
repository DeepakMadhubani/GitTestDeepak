package com.macys.stella.attribute.dbreader.impl;


import java.sql.Connection;
import java.sql.SQLException;

import com.macys.stella.attribute.dbreader.impl.DataAccessBase;

/**
 * Abstract class for reading data from Database
 */
public interface DatabaseReader {
	public DataAccessBase createInstance() throws SQLException;

	public Connection getConnection() throws SQLException;
	
	public Connection getConnection(String dbUrl, String userName, String passWord) throws SQLException;

	public int executeUpdate(String updateCommand) throws SQLException;

	public DataReader getDataReader(String queryCommand) throws SQLException;
	
	public DataReader getResultSet(String queryCommand) throws SQLException;
}

