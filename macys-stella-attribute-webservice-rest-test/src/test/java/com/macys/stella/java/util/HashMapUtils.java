/* (c) 2010, Macys, Inc. All Rights Reserved */
package com.macys.stella.java.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

import com.macys.stella.java.util.POIReader;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class HashMapUtils {

	/**
	 * Standard log4j logger.
	 */
	protected final static Logger log = Logger.getLogger(HashMapUtils.class);

	
	/**
	 * @param subHashMapList
	 * @param superHashMapList
	 * @return
	 */
	public static Boolean checkSubSetHashMap(
			List<Map<String, String>> subHashMapList,
			List<Map<String, String>> superHashMapList) {

		Boolean flag = false;

		for (Map excelmap : subHashMapList) {
			for (Map dbMap : superHashMapList) {
				if (excelmap.equals(dbMap)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				log.info("HashMap not found :");
				printHashMap(excelmap);
				return flag;
			}
		}

		return flag;

	}

	/**
	 * @param HashMap
	 *            prints the HashMap passed as reference
	 */
	public static void printHashMap(Map<String, String> hashMap) {
		log.info("");
		if (hashMap == null) {

			log.info("Hashmap is null");
			
		} else {

			for (Map.Entry<String, String> entry : hashMap.entrySet()) {
				System.out.print(" Key: " + entry.getKey() + ". Value: "
						+ entry.getValue());
			}
			log.info("");
		}
		log.info("\n");
	}

	/**
	 * @param HashMapList
	 *            Prints the HashMapList
	 */
	public static void printListOfHashMap(
			List<Map<String, String>> hashMapList) {
		int counter = 0;
		for (Map<String, String> hashMap : hashMapList) {
			log.info("List number :" + ++counter);
			printHashMap(hashMap);
		}

	}

	/**
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static Boolean compareHashMapKeySet(Map<String, String> map1,
			Map<String, String> map2) {

		return map1.keySet().equals(map2.keySet());

	}

	/**
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static Boolean compareHashMapListKeySet(
			List<Map<String, String>> list1,
			List<Map<String, String>> list2) {
		Boolean flag = false;
		for (Map<String, String> map1 : list1) {
			for (Map<String, String> map2 : list2) {
				flag = compareHashMapKeySet(map1, map2);
				if (!flag) {
					return flag;
				}
			}
		}
		return flag;

	}

	/**
	 * @param keyValues
	 * @return
	 */
	public static Map<String, String> createEmptyHashMapWithGivenKeySet(
			String[] keyValues) {
		Map<String, String> emptyMap = new HashMap<String, String>();

		for (int i = 0; i < keyValues.length; i++) {
			emptyMap.put(keyValues[i], "");
		}
		return emptyMap;
	}

	/**
	 * @param path
	 * @param column_names
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	public static List<Map<String, String>> readExcelToHashMapList(
			String path, String[] column_names) throws BiffException,
			IOException {

		// BufferedReader reader = new BufferedReader(new UnicodeReader(new
		// FileInputStream(fileName)));
		/*
		 * Workbook workbook = Workbook.getWorkbook(new File(path)); Sheet sheet
		 * = workbook.getSheet(0);
		 * 
		 * List<HashMap<String, String>> myMap = new
		 * ArrayList<HashMap<String,String>>();
		 * 
		 * for(int row = 1; row <sheet.getRows();row ++) { StringBuffer
		 * tempPrint = new StringBuffer();
		 * log.info("ROw number : "+row); HashMap<String,String> map =
		 * new HashMap<String,String>(); //Fetching keys from the Excel sheet
		 * for(int column = 0;column < sheet.getColumns(); column++) { String
		 * key = column_names[column]; String value = sheet.getCell(column,
		 * row).getContents(). ;
		 * //log.info("Key :"+key+" value :"+value);
		 * map.put(key.trim(),value.trim());
		 * tempPrint.append(value).append(","); } log.info(tempPrint);
		 * 
		 * myMap.add(map); } return myMap;
		 */

		// Reading using POI

		POIReader poiReader = new POIReader(new File(path));
		HashMap<Integer, List<String>> rowValuesOfSheet = poiReader
				.getRowsByColumnAndValue(0);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		if (rowValuesOfSheet.size() <= 1) {
			log.info("No values in XLS \n Creating an empty hashmap");
			Map<String, String> map = HashMapUtils.createEmptyHashMapWithGivenKeySet(column_names);
			mapList.add(map);
			
		} else {

			for (int i = 2; i <= rowValuesOfSheet.size(); i++) {
				List<String> tempList = rowValuesOfSheet.get(i);
				//log.info(tempList.toString());

				HashMap<String, String> map = new HashMap<String, String>();

				int column_count = 0;
				for (String values : tempList) {
					if (values == null) {
						map.put(column_names[column_count], "");
					} else {

						map.put(column_names[column_count], values);

					}

					//log.info("Key :" + column_names[column_count]+ " value :" + values);
					column_count++;

				}
				mapList.add(map);
			}
		}

		return mapList;
	}

	/**
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static Boolean compareHashMapListSize(
			List<Map<String, String>> list1,
			List<Map<String, String>> list2) {
		return (list1.size() == list2.size());
	}

	

	public static List<String> readExcelFileHeaders(String path) throws BiffException,IOException {

		// Reading using POI

		List<String> headers = new ArrayList<String>();
		POIReader poiReader = new POIReader(new File(path));
		HashMap<Integer, List<String>> rowValuesOfSheet = poiReader
				.getRowsByColumnAndValue(0);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		if (rowValuesOfSheet.size() < 1) {
			log.info("No values in XLS");
			return null ;
			
		} else {

			//for (int i = 1; i <= ; i++) {
			headers = rowValuesOfSheet.get(1);
				
				for(String value : headers){
					log.info(value);
				}

		}

		return headers;
	}
	
	public static Boolean ifMapFoundInList(List<Map<String, String>> list, Map<String,String> map){
		Boolean found = false;
		for(Map<String,String> value : list){
			if(value.equals(map)){
				found = true;
				break;
			}
				
		}
		if(!found){
			Log.info("Map mismatched with List Data");
		}
		return found;
	}
	
	
	public static void main (String args[]){
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("1", "abc");
		map.put("2", "def");
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		list.add(map);
		
		Map<String,String> map2 = new HashMap<String,String>();
		map2.put("1", "def");
		map2.put("2", "abc");
		
		log.info(ifMapFoundInList(list,map2).toString());
		
	}

}
