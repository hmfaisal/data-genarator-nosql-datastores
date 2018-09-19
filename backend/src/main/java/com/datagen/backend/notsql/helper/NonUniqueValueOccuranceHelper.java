package com.datagen.backend.notsql.helper;

import java.util.Collection;

import org.springframework.util.LinkedMultiValueMap;

public class NonUniqueValueOccuranceHelper {
	
	public static LinkedMultiValueMap <String, Long> nonUniqueCalculation(LinkedMultiValueMap<String, Object> valueMap,double actualTotal){
		LinkedMultiValueMap <String, Long> calculatedVMap= new LinkedMultiValueMap <String, Long>();
		int actual = valueActualTotal(valueMap);
		for(String key : valueMap.keySet()){
			Collection<Object> values = valueMap.get(key);
			int tempTotal=values.size();
			long currentToal= valueCalculation(actual,tempTotal,actualTotal);
			calculatedVMap.add(key, currentToal);
		}
		return calculatedVMap;
	}
	
	public static long valueCalculation(int actual, int temp, double dataTotal){
		return (long) Math.ceil((temp*dataTotal)/actual);
	}
	
	public static int valueActualTotal(LinkedMultiValueMap<String, Object> valueMap){
		int total =0;
		for(String key : valueMap.keySet()){
			Collection<Object> values = valueMap.get(key);
			total=total+ values.size();
		}
		return total;
	}

}
