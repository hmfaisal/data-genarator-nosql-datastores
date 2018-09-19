package com.datagen.backend.parser;

import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.helper.DateTimeChecker;
import com.datagen.backend.helper.TextChecker;
import com.datagen.backend.model.JsNode;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonValueHelper {
	
	public static void valueSetter(JsNode n, JsonNode node){

		if (node.isBoolean())
		{
			boolValueSetter(n,node);
		}
		if (node.isDouble())
		{
			doubleValueSetter(n,node);
		}
		if (node.isFloat())
		{
			floatValueSetter(n,node);
		}
		if (node.isInt())
		{
			intValueSetter(n,node);
		}
		if (node.isLong())
		{
			longValueSetter(n,node);
		}
		if (node.isShort())
		{
			shortValueSetter(n,node);
		}
		if (node.isNull())
		{
			nullValueSetter(n,node);
		}
		if (node.isTextual())
		{
			textValueSetter(n,node);
		}
	}
	
	public static void boolValueSetter(JsNode n, JsonNode node){
		String type = "BOOLEAN";
		boolean value = node.booleanValue();
		LinkedMultiValueMap<String, Object> valueMap = n.getValueMap();
		valueMap.add(type, value);	
		n.setValueMap(valueMap);
	}
	
	public static void doubleValueSetter(JsNode n, JsonNode node){
		String type = "DOUBLE";
		double value = node.doubleValue();
		LinkedMultiValueMap<String, Object> valueMap = n.getValueMap();
		valueMap.add(type, value);
		n.setValueMap(valueMap);
	}
	
	public static void floatValueSetter(JsNode n, JsonNode node){
		String type = "FLOAT";
		float value = node.floatValue();
		LinkedMultiValueMap<String, Object> valueMap = n.getValueMap();
		valueMap.add(type, value);	
		n.setValueMap(valueMap);
	}
	
	public static void intValueSetter(JsNode n, JsonNode node){
		String type = "INTEGER";
		int value = node.intValue();
		LinkedMultiValueMap<String, Object> valueMap = n.getValueMap();
		valueMap.add(type, value);	
		n.setValueMap(valueMap);
	}
	
	public static void longValueSetter(JsNode n, JsonNode node){
		String type = "LONG";
		long value =node.longValue();
		LinkedMultiValueMap<String, Object> valueMap = n.getValueMap();
		valueMap.add(type, value);	
		n.setValueMap(valueMap);
	}
	
	public static void shortValueSetter(JsNode n, JsonNode node){
		String type = "SHORT";
		short value = node.shortValue();
		LinkedMultiValueMap<String, Object> valueMap = n.getValueMap();
		valueMap.add(type, value);		
		n.setValueMap(valueMap);
	}
	
	public static void nullValueSetter(JsNode n, JsonNode node){
		String type = "NULL";
		String value = null;
		LinkedMultiValueMap<String, Object> valueMap = n.getValueMap();
		valueMap.add(type, value);		
		n.setValueMap(valueMap);
	}
	
	public static void textValueSetter(JsNode n, JsonNode node){
		String type = null;
		String value = node.textValue();
		if(TextChecker.alphanumericChecker(value)){
			type = "ALPHANUMERIC";
		}//else if(DateTimeChecker.dateChecker(value)){
			//type = "DATE";
		//}
		else if(TextChecker.urlChecker(value)){
			type = "URL";
		}else if(TextChecker.emailChecker(value)){
			type = "EMAIL";
		}else{
			type = "WORDS";
		}
		LinkedMultiValueMap<String, Object> valueMap = n.getValueMap();
		valueMap.add(type, value);	
		n.setValueMap(valueMap);
	}
	
	public static void arrayValueSetter(List<JsNode> NODE, int pID,JsonNode node){
    	if(!NODE.isEmpty()){
    		for(JsNode n : NODE){
    			int id = n.getId();
    			if(id==pID){
    				String type="ARRAY_VALUE";
    				n.setValueType(type);
    				valueSetter(n,node);
    			}
    		}
    	}
	}

}
