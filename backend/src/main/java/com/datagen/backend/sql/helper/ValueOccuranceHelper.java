package com.datagen.backend.sql.helper;

import org.springframework.util.LinkedMultiValueMap;

public class ValueOccuranceHelper {
	
	/*precedence
	 * WORDS
	 * BOOLEAN
	 * EMAIL
	 * ALPHANUMERIC
	 * URL
	 * DATE
	 * DOUBLE
	 * LONG
	 * FLOAT
	 * INTEGER
	 * SHORT
	 */
	public static LinkedMultiValueMap <String, Object> calculatedValMap(LinkedMultiValueMap<String, Object> valueMap){
		
		LinkedMultiValueMap <String, Object> calculatedVMap= new LinkedMultiValueMap <String, Object>();
		String type = "";
		
		for(String key : valueMap.keySet()){
			if(key.equals("BOOLEAN")){
				if(type.isEmpty()){
					type="BOOLEAN";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
				else if(type.equals("WORDS")){
					
				}else{
					type="BOOLEAN";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
			}
			if(key.equals("NULL")){
				if(type.isEmpty()){
					type="NULL";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
				else if(type.equals("WORDS")||type.equals("ALPHANUMERIC")||type.equals("URL")||type.equals("EMAIL")||type.equals("BOOLEAN")){
					
				}else{
					type="NULL";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
			}
			if(key.equals("WORDS")){
				if(type.isEmpty()){
					type="WORDS";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}else{
					type="WORDS";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
			}
			if(key.equals("ALPHANUMERIC")){
				if(type.isEmpty()){
					type="ALPHANUMERIC";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
				else if(type.equals("WORDS")||type.equals("EMAIL")||type.equals("BOOLEAN")){
					
				}else{
					type="ALPHANUMERIC";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
			}
			if(key.equals("URL")){
				if(type.isEmpty()){
					type="URL";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
				else if(type.equals("WORDS")||type.equals("EMAIL")||type.equals("ALPHANUMERIC")||type.equals("BOOLEAN")){
					
				}else{
					type="URL";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
			}
			if(key.equals("EMAIL")){
				if(type.isEmpty()){
					type="EMAIL";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
				else if(type.equals("WORDS")||type.equals("BOOLEAN")){
					
				}else{
					type="EMAIL";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
			}
			if(key.equals("DATE")){
				if(type.isEmpty()){
					type="DATE";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
				else if(type.equals("WORDS")||type.equals("EMAIL")||type.equals("ALPHANUMERIC")||type.equals("URL")||type.equals("BOOLEAN")){
					
				}else{
					type="DATE";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
			}
			if(key.equals("INTEGER")){
				if(type.isEmpty()){
					type="INTEGER";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
				else if(type.equals("WORDS")||type.equals("EMAIL")||type.equals("ALPHANUMERIC")||type.equals("URL")||type.equals("BOOLEAN")||type.equals("DATE")||type.equals("DOUBLE")||type.equals("FLOAT")||type.equals("LONG")){
					
				}else{
					type="INTEGER";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
			}
			if(key.equals("DOUBLE")){
				if(type.isEmpty()){
					type="DOUBLE";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
				else if(type.equals("WORDS")||type.equals("EMAIL")||type.equals("ALPHANUMERIC")||type.equals("URL")||type.equals("BOOLEAN")||type.equals("DATE")){
					
				}else{
					type="DOUBLE";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
			}
			if(key.equals("FLOAT")){
				if(type.isEmpty()){
					type="FLOAT";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
				else if(type.equals("WORDS")||type.equals("EMAIL")||type.equals("ALPHANUMERIC")||type.equals("URL")||type.equals("BOOLEAN")||type.equals("DATE")||type.equals("DOUBLE")||type.equals("LONG")){
					
				}else{
					type="FLOAT";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
			}
			if(key.equals("LONG")){
				if(type.isEmpty()){
					type="LONG";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
				else if(type.equals("WORDS")||type.equals("EMAIL")||type.equals("ALPHANUMERIC")||type.equals("URL")||type.equals("BOOLEAN")||type.equals("DATE")||type.equals("DOUBLE")){
					
				}else{
					type="LONG";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
			}
			if(key.equals("SHORT")){
				if(type.isEmpty()){
					type="SHORT";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
				else if(type.equals("WORDS")||type.equals("EMAIL")||type.equals("ALPHANUMERIC")||type.equals("URL")||type.equals("BOOLEAN")||type.equals("DATE")||type.equals("DOUBLE")||type.equals("FLOAT")||type.equals("LONG")||type.equals("INTEGER")){
					
				}else{
					type="SHORT";
					LinkedMultiValueMap <String, Object> tempVMap= new LinkedMultiValueMap <String, Object>();
					tempVMap.add(key, valueMap.getFirst(key));
					calculatedVMap= tempVMap;
				}
			}
		}
		return calculatedVMap;
	}

}
