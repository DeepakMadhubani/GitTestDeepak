package com.macys.stella.attribute.dbreader.impl;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.macys.stella.attribute.dbreader.impl.DataReader;

import org.apache.log4j.Logger;

public class DataAccessClient extends DataAccessBase {
	private String dbDriver;
	private String dbUrl;
	private String userName;
	private String password;
	protected final Logger log = Logger.getLogger(getClass().getSimpleName());
	
	public DataAccessClient(String dbUrl, String userName, String password) throws SQLException {
       this.dbUrl = dbUrl;
       this.userName = userName;
       this.password = password;
       log.debug(String.format("Initialized DB connection %s using credentials %s/%s.", dbUrl, userName, password));
    }

	public DataAccessClient(String dbDriver, String dbUrl, String userName, String passWord) throws SQLException {
		try {
			this.dbDriver = dbDriver;
		    this.dbUrl = dbUrl;
		    this.userName = userName;
		    this.password = passWord;
		    Class.forName(dbDriver);
		    } catch (ClassNotFoundException e) {
			  throw new SQLException("Database Access Client Failed with message = " + e.getMessage());
		      }
	}

	/** Factory method that allows this class to clone itself */
	public DataAccessBase createInstance() throws SQLException {
		return new DataAccessClient(dbDriver, dbUrl, userName, password);
	}

	/** Get database connection */
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbUrl, userName, password);
	}
	public Connection getConnection(String dbUrl,String userName,String passWord) throws SQLException {
		log.info("DB URL: "+ dbUrl);
		log.info("user Name:" + userName);
		log.info("passWord: "+passWord); 
		return DriverManager.getConnection(dbUrl, userName, password);
	
		
	}
		 public DataReader getDataReaderforPrice(String queryCommand) throws SQLException {
		  ResultSet results = null;
		  try {
		   results = executeQuery(queryCommand);
		   return DataReader.load(results);
		  }
		  finally {
		   tearDown(results);
		  }
	}

		public int executeUpdate(String updateCommand) throws SQLException {
			// TODO Auto-generated method stub
			return 0;
		}

		public DataReader getDataReader(String queryCommand)
				throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		public DataReader getResultSet(String queryCommand) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
}
