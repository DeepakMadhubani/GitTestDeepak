package com.macys.stella.java.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Abstract class for reading data from excel
 */
public abstract class ExcelReader implements Reader {
	public abstract String getData(int sheetNum, int column, int row);

	public abstract String getData(String sheetName, int column, int row);

	public abstract int getRowCount(String sheetName);

	public abstract int getRowCount(int sheetNumber);

	public abstract String getData(int sheetNum, String columnName, int row);

	public abstract String getData(String sheetName, String columnName, int row);

	public abstract boolean hasSheet(String sheetName);

	public abstract List<Integer> getRowsByColumnAndValue(String sheetName, String columnName, String columnValue);

	public abstract boolean checkIfCellHighlightedinGrey(int sheetNo, String colName, int row);

	public abstract HashMap<Integer,List<String>> getRowsByColumnAndValue(int sheetNumber,int rows,int columns);
		// TODO Auto-generated method stub
		
}
