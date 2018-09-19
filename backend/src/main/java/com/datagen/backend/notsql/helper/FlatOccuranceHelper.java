package com.datagen.backend.notsql.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.helper.MethodChecker;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;

public class FlatOccuranceHelper {
	
	public static List<ValueCheck> setTotalCalculation(List<Schema> schema,List<DgenMethod> met,long dataTotal){
		List<ValueCheck> valueTotal= new ArrayList<ValueCheck>();
		for(Schema s:schema){
			Collection<JsNode> values = s.getValue();
			for(JsNode value : values) {
				int id = value.getId();
				String type = value.getValueType();
				String method = MethodChecker.getMethod(id,met);
				if(!type.equals("ARRAY") || !type.equals("OBJECT")){
					ValueCheck vCheck = new ValueCheck();
					vCheck.setId(id);
					LinkedMultiValueMap <String, Long> valueMap = valueCalculate(value.getValueMap(),method,dataTotal);
					vCheck.setValueMap(valueMap);
					valueTotal.add(vCheck);
				}
			}
		}
		return valueTotal;
	}
	
	public static LinkedMultiValueMap <String, Long> valueCalculate(LinkedMultiValueMap<String, Object> valueMap,String method,long dataTotal){
		if(method.equals("MIMICKUNIQUE")){
			return UniqueValueOccuranceHelper.uniqueCalculation(valueMap,dataTotal);
		}else{
			return NonUniqueValueOccuranceHelper.nonUniqueCalculation(valueMap,dataTotal);
		}
	}

}
