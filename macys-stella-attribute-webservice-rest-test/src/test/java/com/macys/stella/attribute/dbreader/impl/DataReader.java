package com.macys.stella.attribute.dbreader.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DataReader {
	
	private static final Logger log = Logger.getLogger("DataReader");

	/**
	 * Reference to the columns read by the result set, loaded from result set meta data.
	 */
	private String[] columns = new String[0];
	/**
	 * Reference to the rows of data read by the result set.
	 */
	private DataRow[] rows = new DataRow[0];
	
	public int getColumnCount() {
		return columns.length;
	}
	/**
	 * Returns the number of Rows in this {@code DataReader} object. <br>
	 * 
	 * @return the number of Rows
	 */
	public int getRowCount() {
		return rows.length;
	}
	public int findColumn(String columnLabel) {
		for(int i = 0; i < columns.length; i++) {
			if(columns[i].equalsIgnoreCase(columnLabel)) {
				return i + 1;
			}
		}
		return 0;
	}
	public static DataReader load(ResultSet resultSet) throws SQLException {
		DataReader reader = new DataReader();
		ResultSetMetaData metaData = resultSet.getMetaData();

		// Fill the columns array with the values from the result set's meta data.
		reader.columns = new String[metaData.getColumnCount()];
		for(int i = 0; i < metaData.getColumnCount(); i++) {
			reader.columns[i] = metaData.getColumnName(i + 1);
		}

		// Get the total number of rows in the result set, instantiate the rows field with this value
		resultSet.last();
		reader.rows = new DataRow[resultSet.getRow()];
		resultSet.beforeFirst();

		// Fill each DataRow with the records for each column in the rows.
		while(resultSet.next()) {
			Object[] records = new Object[metaData.getColumnCount()];
			for(int i = 0; i < metaData.getColumnCount(); i++) {
				records[i] = resultSet.getObject(i + 1);
			}
			reader.rows[resultSet.getRow() - 1] = new DataRow(reader, records);
		}
		log.debug(String.format("Loaded %s row(s) of %s column(s) into memory", reader.rows.length, reader.columns.length));
		return reader;
	}
}
