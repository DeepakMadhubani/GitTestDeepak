package com.macys.stella.attribute.dbreader.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.apache.log4j.Logger;

public abstract class DataAccessBase implements DatabaseReader{
	protected static final Logger LOGGER = Logger.getLogger("Database");

	public ResultSet executeQuery(String query) throws SQLException {
		LOGGER.info("Executing query: " + query);
		long then = System.currentTimeMillis();
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.setQueryTimeout(500);
			return statement.executeQuery(query);
		}
		finally {
			LOGGER.info(String.format("Query executed in %s",
					com.macys.stella.java.util.StringUtils.formatTime((System.currentTimeMillis() - then))));
		}
	}
	
	
	public DataReader getResultSet(String queryCommand) throws SQLException {
		ResultSet results = null;
		try{
			results = executeQuery(queryCommand);
			return DataReader.load(results);
		}
		finally{
			tearDown(results);
		}
	}
	
	public String executeQueryReturnRandomResult(String queryCommand) throws SQLException {
		ResultSet resultSet = null;
		int randomIndex=0;
		String[] results = null;
		LOGGER.info("Executing query: " + queryCommand);
		long then = System.currentTimeMillis();
		try {
			resultSet = executeQuery(queryCommand);
			int totalResults=resultSet.getFetchSize();
			randomIndex = new Random().nextInt(totalResults);
			results = new String[totalResults];
			int index = 0;
			while(index < totalResults && resultSet.next()) {
				results[index] = resultSet.getString(1);
				index++;
			}
			LOGGER.info(String.format("Got results: %s",results[randomIndex]));
		}
		catch(SQLException e){
		      e.printStackTrace();
		 }
		finally {
			LOGGER.info(String.format("Query executed in %s",
					com.macys.stella.java.util.StringUtils.formatTime((System.currentTimeMillis() - then))));
			tearDown(resultSet);
		}
		return results[randomIndex];
	}
	public static void tearDown(ResultSet set) throws SQLException {
		if(set == null) {
			return;
		}
		set.getStatement().getConnection().close();
	}
	
	

}
