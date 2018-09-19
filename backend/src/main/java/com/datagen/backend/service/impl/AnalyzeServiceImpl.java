package com.datagen.backend.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.helper.ValueGenSeqCount;

import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.service.AnalyzeService;
import com.datagen.backend.sql.helper.ValueOccuranceHelper;

@Service
public class AnalyzeServiceImpl implements AnalyzeService{
		
	public long countVolume(List<Schema> schema,int id,long volume,String choice){
		long result=0;
		LinkedMultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<String, Object>();
		for(Schema s:schema){
			Collection<JsNode> values = s.getValue();
			for(JsNode value : values) {
				int nodeId = value.getId();
				String type = value.getValueType();
				if(id==nodeId && (!type.equals("ARRAY") || !type.equals("OBJECT"))){
					if(choice.equals("SQL")){
						valueMap= ValueOccuranceHelper.calculatedValMap(value.getValueMap());
					}else{
						valueMap = value.getValueMap();
					}
					
					result = count(valueMap,volume);
				}
			}
		}
		return result;
	}
	
	public long count(LinkedMultiValueMap<String, Object> valueMap,long volume){
		long vol = 0;
		for(String type : valueMap.keySet()){
			if(type.equals("BOOLEAN")){
				long tempVol=volume;
				vol+=tempVol;
			}
			if(type.equals("NULL")){
				long tempVol=volume;
				vol+=tempVol;
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
				
				long tempVol = ValueGenSeqCount.sentenceCount(max, texts);
				vol+=tempVol;
			}
			if(type.equals("DATE")){
				Collection<Object> values = valueMap.get(type);
				long tempVol = ValueGenSeqCount.dateGenCount(values);
				vol+=tempVol;
			}
			if(type.equals("ALPHANUMERIC")){
				Collection<Object> values = valueMap.get(type);
				String text = (String)values.iterator().next();
				long tempVol = ValueGenSeqCount.alphaNumCount(text);
				vol+=tempVol;
			}
			if(type.equals("URL")){
				Collection<Object> values = valueMap.get(type);
				String text = (String)values.iterator().next();
				long tempVol = ValueGenSeqCount.urlCount(text);
				vol+=tempVol;
			}
			if(type.equals("EMAIL")){
				Collection<Object> values = valueMap.get(type);
				String text = (String)values.iterator().next();
				long tempVol = ValueGenSeqCount.emailCount(text);
				vol+=tempVol;
			}
			if(type.equals("INTEGER")){
				Collection<Object> values = valueMap.get(type);
				long tempVol = ValueGenSeqCount.intGenCount(values);
				vol+=tempVol;
			}
			if(type.equals("DOUBLE")){
				long tempVol=volume;
				vol+=tempVol;
			}
			if(type.equals("FLOAT")){
				long tempVol=volume;
				vol+=tempVol;
			}
			if(type.equals("LONG")){
				Collection<Object> values = valueMap.get(type);
				long tempVol= ValueGenSeqCount.longGenCount(values);
				vol+=tempVol;
			}
			if(type.equals("SHORT")){
				Collection<Object> values = valueMap.get(type);
				long tempVol= ValueGenSeqCount.shortGenCount(values);
				vol+=tempVol;
			}
		}
		
		return vol;
		
	}

}
