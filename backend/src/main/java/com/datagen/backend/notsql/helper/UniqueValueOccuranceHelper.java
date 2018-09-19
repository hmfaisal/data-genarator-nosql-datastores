package com.datagen.backend.notsql.helper;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.helper.ValueGenSeqCount;

public class UniqueValueOccuranceHelper {
	
	public static LinkedMultiValueMap <String, Long> uniqueCalculation(LinkedMultiValueMap<String, Object> valueMap,long dataTotal){
		
		LinkedMultiValueMap <String, Long> calculatedVMap= new LinkedMultiValueMap <String, Long>();
		for(String type : valueMap.keySet()){
			if(type.equals("BOOLEAN")){
				long currentToal=dataTotal;
				calculatedVMap.add(type, currentToal);
			}
			if(type.equals("NULL")){
				long currentToal=dataTotal;
				calculatedVMap.add(type, currentToal);
			}
			if(type.equals("WORDS")){
				Collection<Object> values = valueMap.get(type);
				ArrayList<String> texts = new ArrayList<String>();
				int min = 0;
				int max = 0;
				for(Object value:values){
					String[] word = ((String) value).split("\\W+");
					int temp_min = word.length;
					int temp_max = word.length;
					for(String s:word){
						if(!texts.contains(s)){
							texts.add(s);
						}
					}
					
					if(temp_min<min){
						min = temp_min;
					}else if(temp_max>max){
						max= temp_max;
					}
				}
				
				long currentToal = ValueGenSeqCount.sentenceCount(max, texts);
				calculatedVMap.add(type, currentToal);
			}
			if(type.equals("DATE")){
				Collection<Object> values = valueMap.get(type);
				long currentToal = ValueGenSeqCount.dateGenCount(values);
				calculatedVMap.add(type, currentToal);
			}
			if(type.equals("ALPHANUMERIC")){
				Collection<Object> values = valueMap.get(type);
				String text = (String)values.iterator().next();
				long currentToal = ValueGenSeqCount.alphaNumCount(text);
				calculatedVMap.add(type, currentToal);
			}
			if(type.equals("URL")){
				Collection<Object> values = valueMap.get(type);
				String text = (String)values.iterator().next();
				long currentToal = ValueGenSeqCount.urlCount(text);
				calculatedVMap.add(type, currentToal);
			}
			if(type.equals("EMAIL")){
				Collection<Object> values = valueMap.get(type);
				String text = (String)values.iterator().next();
				long currentToal = ValueGenSeqCount.emailCount(text);
				calculatedVMap.add(type, currentToal);
			}
			if(type.equals("INTEGER")){
				Collection<Object> values = valueMap.get(type);
				long currentToal = ValueGenSeqCount.intGenCount(values);
				calculatedVMap.add(type, currentToal);
			}
			if(type.equals("DOUBLE")){
				long currentToal=dataTotal;
				calculatedVMap.add(type, currentToal);
			}
			if(type.equals("FLOAT")){
				long currentToal=dataTotal;
				calculatedVMap.add(type, currentToal);
			}
			if(type.equals("LONG")){
				Collection<Object> values = valueMap.get(type);
				long currentToal= ValueGenSeqCount.longGenCount(values);
				calculatedVMap.add(type, currentToal);
			}
			if(type.equals("SHORT")){
				Collection<Object> values = valueMap.get(type);
				long currentToal= ValueGenSeqCount.shortGenCount(values);
				calculatedVMap.add(type, currentToal);
			}
		}
		return calculatedVMap;
	}

}
