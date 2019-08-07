package com.cw.web.common.sms.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String getJsonStringFromObject(Object obj){
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T>T getObjectFromJsonString(String jsonString, Class<T> valueType){
		try {
			return (T)mapper.readValue(jsonString, valueType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}
}
