package com.datagen.backend.notsql.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.helper.MethodChecker;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;

public class DistributedOccuranceHelper {
	
	public static List<ValueCheck> setTotalCalculation(List<Schema> schema,List<DgenMethod> met,long dataTotal){
		
		List<ValueCheck> valueTotal= new ArrayList<ValueCheck>();
		int actualTotal= FieldOccuranceHelper.actualTotalOccurance(schema);
		LinkedHashSet<Integer> parent = new LinkedHashSet<Integer>();
		for(Schema s:schema){
			parent.add(s.getKey());
		}
		
		for(Schema s:schema){
			Collection<JsNode> values = s.getValue();
			for(JsNode value : values) {
				int id = value.getId();
				int parentId = value.getParentId();
				String name = value.getNodeName();
				LinkedMultiValueMap<String, Object> valMap = value.getValueMap();
				LinkedMultiValueMap<Integer, Integer> fieldOccurance = value.getOccurance();
				String method = MethodChecker.getMethod(id,met);
				
				ValueCheck vCheck = new ValueCheck();
				vCheck.setId(id);
				
				/*
				if(parent.contains(id)){
					LinkedMultiValueMap<Integer, Integer> loop= FieldOccuranceHelper.getParentCalculatedLoop(schema,id,fieldOccurance);
					vCheck.setLoop(loop);
				}else{
					//Integer l = new Integer(1);
					LinkedMultiValueMap<Integer, Integer> loop= new LinkedMultiValueMap <Integer, Integer>();
					for(int l : fieldOccurance.keySet()){
						loop.add(l, 1);
					}
					vCheck.setLoop(loop);
				}
				*/
				
				LinkedMultiValueMap<Integer, Integer> loop= FieldOccuranceHelper.getCalculatedLoop(schema,id,parentId,fieldOccurance);
				//LinkedMultiValueMap<Integer, Integer> finalLoop= FieldOccuranceHelper.getParentCalculatedLoop(schema,id,parentId,fieldOccurance);
				//vCheck.setLoop(loop);
				
				//LinkedMultiValueMap<Integer, Integer> loop= fieldOccurance;
				vCheck.setLoop(loop);
				

				LinkedMultiValueMap<Integer, Long> occurance = FieldOccuranceHelper.getCalculatedOcccurance(fieldOccurance,actualTotal,dataTotal);
				vCheck.setOccurance(occurance);
				
				//int nodeOccur = fieldOccurance.keySet().size();
				//long calculateNodeTotal = FieldOccuranceHelper.occuranceCalculate(nodeOccur,actualTotal,dataTotal);
				LinkedMultiValueMap <String, Long> valueMap = valueCalculate(valMap,method,actualTotal,dataTotal);
				vCheck.setValueMap(valueMap);
				
				valueTotal.add(vCheck);
			}
		}
		return valueTotal;
	}
	
	public static LinkedMultiValueMap <String, Long> valueCalculate(LinkedMultiValueMap<String, Object> valueMap,String method,long dataTotal, long occurance){
		if(method.equals("MIMICKUNIQUE")){
			return UniqueValueOccuranceHelper.uniqueCalculation(valueMap,occurance);
		}else{
			return NonUniqueValueOccuranceHelper.nonUniqueCalculation(valueMap,occurance);
		}
	}
	
	public static List<ValueCheck> setCurrentCalculation(List<Schema> schema){
		
		List<ValueCheck> currentTotal= new ArrayList<ValueCheck>();
		
		for(Schema s:schema){
			Collection<JsNode> values = s.getValue();
			for(JsNode value : values) {
				
				int id = value.getId();
				LinkedMultiValueMap<String, Object> valMap = value.getValueMap();
				LinkedMultiValueMap<Integer, Integer> fieldOccurance= value.getOccurance();
				
				ValueCheck vCheck = new ValueCheck();
				vCheck.setId(id);
				
				LinkedMultiValueMap<Integer, Integer> loop= new LinkedMultiValueMap <Integer, Integer>();
				for(int l : fieldOccurance.keySet()){
					loop.add(l, 0);
				}
				vCheck.setLoop(loop);
				
				long total = 0;
				LinkedMultiValueMap<Integer, Long> occurance= new LinkedMultiValueMap <Integer, Long>();
				for(int occur : fieldOccurance.keySet()){
					occurance.add(occur, total);
				}
				vCheck.setOccurance(occurance);
				
				LinkedMultiValueMap <String, Long> valueMap= new LinkedMultiValueMap <String, Long>();
				long currentToal= 0;
				for(String key : valMap.keySet()){
					valueMap.add(key, currentToal);
				}
				vCheck.setValueMap(valueMap);
				
				currentTotal.add(vCheck);
			}
		}
		return currentTotal;
	}
	
	
	public static LinkedMultiValueMap<Integer, Long> setMaxTotalCalculation(List<ValueCheck> valueTotal){
		
		LinkedMultiValueMap<Integer, Long> occurance= new LinkedMultiValueMap <Integer, Long>();
		
		for(ValueCheck value: valueTotal){
			LinkedMultiValueMap<Integer, Long> fieldOccurance = value.getOccurance();
			for(int key : fieldOccurance.keySet()){
				long occur = FieldOccuranceHelper.getMaxForEveryOccur(key,valueTotal);
				if(FieldOccuranceHelper.isNotMaxInserted(occurance,key,occur)){
					occurance.add(key, occur);
				}
			}
		}
		
		return occurance;
	}
		
}
