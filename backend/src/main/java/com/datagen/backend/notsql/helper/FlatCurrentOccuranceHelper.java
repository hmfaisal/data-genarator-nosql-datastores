package com.datagen.backend.notsql.helper;

import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.model.ValueCheck;

public class FlatCurrentOccuranceHelper {
	
	public static LinkedMultiValueMap<String, Long> currentMap(int id,List<ValueCheck> valueTotal){
		LinkedMultiValueMap<String, Long> vMap = null;
		for(ValueCheck value: valueTotal){
			if(id==value.getId()){
				vMap= value.getValueMap();
			}
		}
		return vMap;
	}

}
