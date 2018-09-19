package com.datagen.backend.sql.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;

public class FlatOccuranceHelper {
	
	public static List<ValueCheck> setTotalCalculation(List<Schema> schema,long dataTotal){
		List<ValueCheck> valueTotal= new ArrayList<ValueCheck>();
		for(Schema s:schema){
			Collection<JsNode> values = s.getValue();
			for(JsNode value : values) {
				int id = value.getId();
				String type = value.getValueType();
				if(!type.equals("ARRAY") || !type.equals("OBJECT")){
					ValueCheck vCheck = new ValueCheck();
					vCheck.setId(id);
					LinkedMultiValueMap <String, Long> valueMap = valueCalculate(value.getValueMap(),dataTotal);
					vCheck.setValueMap(valueMap);
					valueTotal.add(vCheck);
				}
			}
		}
		return valueTotal;
	}
	
	public static LinkedMultiValueMap <String, Long> valueCalculate(LinkedMultiValueMap<String, Object> valueMap,long dataTotal){
		LinkedMultiValueMap <String, Long> calculatedVMap= new LinkedMultiValueMap <String, Long>();
		for(String key : valueMap.keySet()){
			calculatedVMap.add(key, dataTotal);
		}
		return calculatedVMap;
	}

}
