package com.datagen.backend.sql.helper;

import java.util.Collection;

import org.springframework.util.LinkedMultiValueMap;

public class TypeChecker {
	
	public static String typeCaster( String type,LinkedMultiValueMap<String, Object> valueMap){
		
		String valueType ="";
		
		if(!type.equals("ARRAY") || !type.equals("OBJECT")){
			for(String vType : valueMap.keySet()){
				if(vType.equals("BOOLEAN")){
					valueType= boolChecker(valueType);
				}
				if(vType.equals("NULL")){
					valueType= nullChecker(valueType);
				}
				if(vType.equals("WORDS")){
					Collection<Object> values = valueMap.get(vType);
					valueType= textChecker(valueType,values);
				}
				if(vType.equals("ALPHANUMERIC")||vType.equals("URL")||vType.equals("EMAIL")){
					valueType= varcharChecker(valueType);
				}
				if(vType.equals("DATE")){
					valueType= dateChecker(valueType);
				}
				if(vType.equals("INTEGER")){
					valueType= intChecker(valueType);
				}
				if(vType.equals("DOUBLE")){
					valueType= doubleChecker(valueType);
				}
				if(vType.equals("FLOAT")){
					valueType= floatChecker(valueType);
				}
				if(vType.equals("LONG")){
					valueType= longChecker(valueType);
				}
				if(vType.equals("SHORT")){
					valueType= shortChecker(valueType);
				}
			}
		}
		
		return valueType;	
	}
	
	public static String boolChecker(String valueType){
		if(valueType.isEmpty()){
			valueType="BOOLEAN";
		}
		else if(valueType.equals("TEXT")){
			
		}else{
			valueType="BOOLEAN";
		}
		return valueType;
	}
	
	public static String nullChecker(String valueType){
		if(valueType.isEmpty()){
			valueType="VARCHAR";
		}
		else if(valueType.equals("TEXT")||valueType.equals("BOOLEAN")||valueType.contains("VARCHAR")){
			
		}else{
			valueType="VARCHAR";
		}
		return valueType;
	}
	
	public static String textChecker(String valueType, Collection<Object> values){
		int min = 0;
		int max = 0;
		for(Object value:values){
			String[] word = ((String) value).split("\\W+");
			int temp_min = word.length;
			int temp_max = word.length;
			if(temp_min<min){
				min = temp_min;
			}else if(temp_max>max){
				max= temp_max;
			}
		}
		if(max<8){
			if(valueType.isEmpty()){
				valueType="VARCHAR(255)";
			}
			else if(valueType.equals("TEXT")){
				
			}else{
				valueType="VARCHAR(255)";
			}
		}else{
			if(valueType.isEmpty()){
				valueType="TEXT";
			}
			else if(valueType.equals("TEXT")){
				
			}else{
				valueType="TEXT";
			}
		}
		
		return valueType;
	}
	
	public static String varcharChecker(String valueType){
		if(valueType.isEmpty()){
			valueType="VARCHAR(255)";
		}
		else if(valueType.equals("TEXT")||valueType.equals("BOOLEAN")){
			
		}else{
			valueType="VARCHAR(255)";
		}
		return valueType;
	}
	
	public static String dateChecker(String valueType){
		if(valueType.isEmpty()){
			valueType="DATETIME";
		}
		else if(valueType.equals("TEXT")||valueType.equals("BOOLEAN")||valueType.contains("VARCHAR")){
			
		}else{
			valueType="DATETIME";
		}
		return valueType;
	}
	
	public static String intChecker(String valueType){
		if(valueType.isEmpty()){
			valueType="INTEGER";
		}
		else if(valueType.equals("TEXT")||valueType.equals("BOOLEAN")||valueType.contains("VARCHAR")||valueType.equals("DATETIME")||valueType.equals("LONG")||valueType.equals("DOUBLE")||valueType.equals("FLOAT")){
			
		}else{
			valueType="INTEGER";
		}
		return valueType;
	}
	
	public static String shortChecker(String valueType){
		if(valueType.isEmpty()){
			valueType="INTEGER";
		}
		else if(valueType.equals("TEXT")||valueType.equals("BOOLEAN")||valueType.contains("VARCHAR")||valueType.equals("DATETIME")||valueType.equals("LONG")||valueType.equals("DOUBLE")||valueType.equals("FLOAT")||valueType.equals("INTEGER")){
			
		}else{
			valueType="INTEGER";
		}
		return valueType;
	}
	
	public static String longChecker(String valueType){
		if(valueType.isEmpty()){
			valueType="LONG";
		}
		else if(valueType.equals("TEXT")||valueType.equals("BOOLEAN")||valueType.contains("VARCHAR")||valueType.equals("DATETIME")||valueType.equals("DOUBLE")){
			
		}else{
			valueType="LONG";
		}
		return valueType;
	}
	
	public static String doubleChecker(String valueType){
		if(valueType.isEmpty()){
			valueType="DOUBLE";
		}
		else if(valueType.equals("TEXT")||valueType.equals("BOOLEAN")||valueType.contains("VARCHAR")||valueType.equals("DATETIME")){
			
		}else{
			valueType="DOUBLE";
		}
		return valueType;
	}
	
	public static String floatChecker(String valueType){
		if(valueType.isEmpty()){
			valueType="FLOAT";
		}
		else if(valueType.equals("TEXT")||valueType.equals("BOOLEAN")||valueType.contains("VARCHAR")||valueType.equals("DATETIME")||valueType.equals("DOUBLE")||valueType.equals("LONG")){
			
		}else{
			valueType="FLOAT";
		}
		return valueType;
	}

}
