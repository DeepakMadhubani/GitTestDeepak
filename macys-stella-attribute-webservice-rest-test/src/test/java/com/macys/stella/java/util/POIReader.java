package com.macys.stella.java.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Iterator;

import jxl.CellType;

import com.macys.stella.java.util.ExcelReader;
import com.macys.stella.java.util.HashMapUtils;

/**
 *
 */
public class POIReader  {
	private static final Logger LOGGER = Logger.getLogger(POIReader.class);

	private Workbook currentWorkbook;
	FileInputStream fis;
	HashMap<Integer, List<String>> fileContents  = new HashMap<Integer, List<String>>();
	List<String>sheetRows =new ArrayList<String>();
	public POIReader(File testDataFile) throws IOException {
		if(( testDataFile).exists()) {

			try {
				fis = new FileInputStream(testDataFile);
				if(String.valueOf(testDataFile).endsWith(".xls"))
				{
					currentWorkbook = new HSSFWorkbook(fis);
				}
				else
				{
					currentWorkbook = new XSSFWorkbook(fis);	
				}

			} catch(Exception exception) {
				LOGGER.error("Error in loading the test data file: " + testDataFile, exception);
			}		
		}
		else {
			LOGGER.error("Couldn't find the test data file: " + testDataFile);
		}
	}


	public int getRowNumberWithColumns(String sheetName, HashMap<String, String> colValPair){
		Sheet sheet = currentWorkbook.getSheet(sheetName);
		Row row = getRowOfColumnsValues(sheet, colValPair);
		if(row == null)
			return -1;
		else
			return row.getRowNum();
	}
	public String getTextFromRowOfColumn(String sheetName,String columnToGet, String columnWhoseValuse, String columnValue) {
		Sheet sheet = currentWorkbook.getSheet(sheetName);
		String rowValue = "";
		Row matchedRow =  getRowOfColumnValue(sheet, columnWhoseValuse, columnValue);
		if(matchedRow == null) {
			System.out.println("No desired row found");
			return rowValue;
		}
		int columnNo = getColumn(sheet, columnToGet);

		Cell cell = matchedRow.getCell(columnNo);
		if(cell.getCellType() == Cell.CELL_TYPE_STRING)
			rowValue = cell.getStringCellValue().trim();
		else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
			rowValue = String.valueOf(Math.round(cell.getNumericCellValue()));
		else if(cell.getCellType() == Cell.CELL_TYPE_BLANK)
			rowValue = "";
		return rowValue;
	}

	private Row getRowOfColumnValue(Sheet sheet, String columnName, String columnValue){
		int column = getColumn(sheet, columnName);
		int rows = getActualRowCount(sheet);
		for(int i=0;  i<=rows; i++){
			Row matchedRow = sheet.getRow(i);
			if(matchedRow == null) {
				System.out.println("No desired row found");
				return null;
			}
			Cell cell = matchedRow.getCell(column);
			String cellText = cell.getStringCellValue().trim();
			if(cellText.equalsIgnoreCase(columnValue))
				return matchedRow;
		}
		return null;

	}
	
	public String getTextFromRowOfColumn(String sheetName,String columnToGet,  HashMap<String, String> colValPair) {
		Sheet sheet = currentWorkbook.getSheet(sheetName);
		String rowValue = "";
		Row matchedRow =  getRowOfColumnsValues(sheet, colValPair);
		if(matchedRow == null) {
			System.out.println("No desired row found");
			return null;
		}
		int columnNo = getColumn(sheet, columnToGet);

		Cell cell = matchedRow.getCell(columnNo);
		if(cell.getCellType() == Cell.CELL_TYPE_STRING)
			rowValue = cell.getStringCellValue().trim();
		else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
			rowValue = String.valueOf(Math.round(cell.getNumericCellValue()));
		else if(cell.getCellType() == Cell.CELL_TYPE_BLANK)
			rowValue = "";
		return rowValue;
	}
	private Row getRowOfColumnsValues(Sheet sheet, HashMap<String, String> colValPair){

		int allRows = getActualRowCount(sheet);
		for(int i=0;  i<allRows; i++){
			boolean rowFound = true;
			Row matchedRow = sheet.getRow(i);
			if(matchedRow == null) {
				System.out.println("No desired row found");
				return null;
			}
			for(Entry<String, String> entry : colValPair.entrySet()){
				String colName = entry.getKey();
				String colVal = entry.getValue();
				Cell cell = matchedRow.getCell(getColumn(sheet, colName));
				String cellText = "";
				if(cell.getCellType() == Cell.CELL_TYPE_STRING)
					cellText = cell.getStringCellValue().trim();
				else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
					cellText = String.valueOf(Math.round(cell.getNumericCellValue()));
				else if(cell.getCellType() == Cell.CELL_TYPE_BLANK)
					cellText = "";
				
				if(!cellText.equalsIgnoreCase(colVal)){
					rowFound = false;
					break;
				}	
			}
			if(rowFound)
				return matchedRow;
		}
		return null;

	}
	public int getActualRowCount(Sheet sheet) {
		int notNullCount = 0;
		for(Row row : sheet) {
			for(Cell cell : row) {
				if(cell.getCellType() != Cell.CELL_TYPE_BLANK) {
					if(cell.getCellType() != Cell.CELL_TYPE_STRING || cell.getStringCellValue().length() > 0) {
						notNullCount++;
						break;
					}
				}
			}
		}
		return notNullCount;
	}
	private int getColumn(Sheet sheet, String columnName) {
		Row firstRow = sheet.getRow(0);
		Iterator<Cell> cellIterator = firstRow.cellIterator();
		while(cellIterator.hasNext()) {
			Cell next = cellIterator.next();
			if(StringUtils.equalsIgnoreCase(next.getStringCellValue(), columnName)) {
				return next.getColumnIndex();
			}
		}
		return -1;
	}

	public boolean hasSheet(String sheetName) {
		Sheet sheet = currentWorkbook.getSheet(sheetName);
		if(sheet == null) {
			LOGGER.error("SheetName:" + sheetName + " doesn't exist !!");
			return false;
		}
		return true;
	}




	public HashMap<Integer, List<String>> getRowsByColumnAndValue(int sheetNumber) throws IOException {
		Sheet currentSheet = currentWorkbook.getSheetAt(sheetNumber);
		int rowID=0;


		Iterator<Row> rowIterator = currentSheet.iterator();
		while (rowIterator.hasNext())
		{
			String stringValue;
			double intValue; 
			//Get the row object
			Row row = rowIterator.next();
			rowID++;
			//Every row has columns, get the column iterator and iterate over them
			Iterator<Cell> cellIterator = row.cellIterator();
			sheetRows = new ArrayList<String>();
			while (cellIterator.hasNext())
			{
				//Get the Cell object
				Cell cell = cellIterator.next();

				//check the cell type and process accordingly
				switch(cell.getCellType()){
				case Cell.CELL_TYPE_STRING:
					stringValue = cell.getStringCellValue().trim();
					if ((!stringValue.equals("NA")&&(!stringValue.equals("Column Name"))&&
							(!stringValue.equals("Locale"))&&(!stringValue.equals("Unit of Length"))))                      
						sheetRows.add(stringValue);
					//	System.out.println("Adding :"+stringValue+" value in list");
					break;
				case Cell.CELL_TYPE_NUMERIC:
					intValue =cell.getNumericCellValue();
					if(HSSFDateUtil.isCellDateFormatted(cell))
					{
						Date date = HSSFDateUtil.getJavaDate(intValue);

						String dateFmt = cell.getCellStyle().getDataFormatString();
						//   System.out.println("excel date format:"+dateFmt);
						/* strValue = new SimpleDateFormat(dateFmt).format(date); - won't work as 
					    Java fmt differs from Excel fmt. If Excel date format is mm/dd/yyyy, Java 
					    will always be 00 for date since "m" is minutes of the hour.*/

						//  stringValue = new CellDateFormatter(dateFmt).format(date);

						SimpleDateFormat dateFormat =  new SimpleDateFormat("MM/dd/yyyy");
						stringValue = dateFormat.format(date);
						sheetRows.add(stringValue);
					}
					else
					{
						if(intValue % 1 == 0){
							sheetRows.add(String.valueOf(BigInteger.valueOf((long) cell.getNumericCellValue())));
						}
						else{
							sheetRows.add(String.valueOf(intValue));
						}
					}
					break;
				case Cell.CELL_TYPE_BLANK:
					stringValue = "";
					sheetRows.add(String.valueOf(stringValue));

				}

			} //end of cell iterator
			fileContents.put(rowID, (ArrayList<String>) sheetRows);
		}	
		fis.close();
		return fileContents;
	}	

	public List<List<String>> readAllRowsOfXlsx(int sheetNumber) throws IOException{

		List<List<String>> allDataAsList = new ArrayList<List<String>>();
		Sheet sheet = (Sheet) currentWorkbook.getSheetAt(sheetNumber);

		Iterator<Row> rowIter = sheet.rowIterator();
		while(rowIter.hasNext()){
			Row myRow = (Row) rowIter.next();
			Iterator<Cell> cellIter = myRow.cellIterator();
			List<String> rowAsList  = new ArrayList<String>();
			while(cellIter.hasNext()){
				Cell myCell = (Cell) cellIter.next();
				rowAsList.add(myCell.toString());
			}
			allDataAsList.add(rowAsList);
		}

		return allDataAsList;
	}

	public List<Map<String,String>> getRowsByColumnAndValueinHashMap(String sheetName) throws IOException {
		Sheet currentSheet = currentWorkbook.getSheet(sheetName);
		int rowID=0;

		List<Map<String, String>> finalList = new ArrayList<Map<String,String>>();
		Iterator<Row> rowIterator = currentSheet.iterator();
		int cellMaxCount = -1;
		ArrayList<String> keys = new ArrayList<String>();

		while (rowIterator.hasNext())
		{
			String stringValue;
			double intValue; 
			//Get the row object
			Row row = rowIterator.next();

			rowID++;
			int cellID = 0;
			//Every row has columns, get the column iterator and iterate over them
			Iterator<Cell> cellIterator = row.cellIterator();
			sheetRows = new ArrayList<String>();
			Map<String,String> map = new LinkedHashMap<String,String>();

			while (cellIterator.hasNext())
			{

				//Get the Cell object
				Cell cell = cellIterator.next();

				//check the cell type and process accordingly
				switch(cell.getCellType()){
				case Cell.CELL_TYPE_STRING:
					stringValue = cell.getStringCellValue().trim();
					if(rowID == 1){

						keys.add(stringValue);
						cellID++;

					}else                      
					{//sheetRows.add(stringValue);
						map.put((String) keys.get(cellID), stringValue);
						cellID++;
					}
					//	System.out.println("Adding :"+stringValue+" value in list");

					break;

				case Cell.CELL_TYPE_NUMERIC:
					intValue =cell.getNumericCellValue();
					if(rowID == 1){

						keys.add(String.valueOf(intValue));

					}else 
						if(HSSFDateUtil.isCellDateFormatted(cell))
						{
							Date date = HSSFDateUtil.getJavaDate(intValue);

							String dateFmt = cell.getCellStyle().getDataFormatString();
							//   System.out.println("excel date format:"+dateFmt);
							// strValue = new SimpleDateFormat(dateFmt).format(date); /*- won't work as 
							/* Java fmt differs from Excel fmt. If Excel date format is mm/dd/yyyy, Java 
					    will always be 00 for date since "m" is minutes of the hour.*/

							//  stringValue = new CellDateFormatter(dateFmt).format(date);

							SimpleDateFormat dateFormat =  new SimpleDateFormat("MM/dd/yyyy");
							stringValue = dateFormat.format(date);
							map.put((String) keys.get(cellID), stringValue);
							cellID++;
						}
						else
						{
							if(intValue % 1 == 0){
								//sheetRows.add(String.valueOf(BigInteger.valueOf((long) cell.getNumericCellValue())));
								map.put((String) keys.get(cellID), String.valueOf(BigInteger.valueOf((long) cell.getNumericCellValue())));
								cellID++;
							}
							else{
								sheetRows.add(String.valueOf(intValue));
								map.put((String) keys.get(cellID), String.valueOf(intValue));
								cellID++;
							}
						}
					break; 
				case Cell.CELL_TYPE_BLANK:
					if(rowID == 1)//Save the cellID count for next iterations
					{ 
						cellMaxCount = cellID; 
						break;
					}
					else if(cellID <= cellMaxCount){
						stringValue = "";
						map.put((String) keys.get(cellID), String.valueOf(stringValue));
						cellID++;
					}

					break;


				}
				if(rowID == 1)//Save the cellID count for next iterations
				{ 
					cellMaxCount = cellID; 

				}
				/*//Counter for all other rows
				if(rowID != 1 && cellID<cellMaxCount){
					cellID++;
				}*/

			} //end of cell iterator

			//If you reach the null values in following rows, enter blank in map for corresponding values
			if(!cellIterator.hasNext() && cellID<cellMaxCount){
				while(cellID<cellMaxCount){
					map.put((String)keys.get(cellID),"");
					cellID++;
				}
			}

			//fileContents.put(rowID, (ArrayList<String>) sheetRows);

			if(!map.isEmpty()){
				finalList.add(map);
			}
		}	


		fis.close();
		return finalList;
	}

	public static void main(String args[]){
		File file = new File("C:\\GITCODE\\newrepo\\stella-ui\\stella-functional-tests\\svn_migration\\stella-functional-tests\\src\\test\\resources\\MCOM_ProductCreationMultipleEntries.xls");
		try {
			POIReader reader = new POIReader(file);
			List<Map<String,String>> finalList = reader.getRowsByColumnAndValueinHashMap("Sheet1");
			HashMapUtils.printListOfHashMap(finalList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}



