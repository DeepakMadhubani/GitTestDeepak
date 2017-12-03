package com.macys.stella.attribute.webservice.rest.util;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	
	public <T> String getJsonPayloadFromObject(T object) {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonPayLoad = null;
		try {
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			jsonPayLoad = objectMapper.writeValueAsString(object);
			System.out.println("Json Payload:");
			System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		return jsonPayLoad;
	}
	
	
	public <T> T getObjectFromJsonResponse(String jsonString,Class<T> obj)
	{
		T object = null;
		ObjectMapper objectMapper = new ObjectMapper();
			try {
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
				object=objectMapper.readValue(jsonString, obj);
				System.out.println("Json Response:");
				System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
			} catch (JsonParseException e) {	
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return object;
	
		}
	
	
	public<T> List<T> getListOfObjectfromJsonResponse(Class<T> MyClass, String response)
	{

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		TypeReference<List<T>> mapType = new TypeReference<List<T>>() {};
		//List<T> myObjects = mapper.readValue(response, new TypeReference<List<T>>(){});
		List<T> myObjects = null;
		try {
			myObjects = objectMapper.readValue(response,mapType);
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return myObjects;
}
	
}
