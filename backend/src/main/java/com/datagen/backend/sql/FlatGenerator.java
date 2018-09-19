package com.datagen.backend.sql;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.helper.MethodChecker;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.sql.helper.FlatCurrentOccuranceHelper;
import com.datagen.backend.sql.helper.ValueGenerator;
import com.datagen.backend.sql.helper.ValueOccuranceHelper;
import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class FlatGenerator {
	
	public Multimap<Integer, Object> init(List<Schema> schema, int rootId, Multimap<Integer, Integer> childId, List<DgenMethod> method, List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue, List<ValueCheck> valueTotal,long current){
		Multimap<Integer, Object> childVal = LinkedListMultimap.create();
		LinkedHashSet<Integer> parent = new LinkedHashSet<Integer>();
		for(Schema s:schema){
			parent.add(s.getKey());
		}
		generator(0,schema,parent,childId,rootId,childVal,method,currentValue,tempCurrentValue,valueTotal,current);
		return childVal;
	}
	
	public void generator(int key, List<Schema> schema, LinkedHashSet<Integer> parent, Multimap<Integer, Integer> childId, int rootId,Multimap<Integer, Object> childVal, List<DgenMethod> met, List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,List<ValueCheck> valueTotal,long current){
		
		Collection<JsNode> values = null;
		for(Schema s:schema){
			if(s.getKey()==key){
				values = s.getValue();
			}
		}
		
		JsNode lastElement = Iterables.getLast(values, null);
		for(JsNode value : values) {
			int id = value.getId();
			String type = value.getValueType();
			LinkedMultiValueMap<String, Object> vMap = value.getValueMap();
			LinkedMultiValueMap<String, Object> valueMap = ValueOccuranceHelper.calculatedValMap(vMap);
			String method = MethodChecker.getMethod(id,met);
			LinkedMultiValueMap<String, Long> currentTotal = FlatCurrentOccuranceHelper.currentMap(id, valueTotal);
			
			if(parent.contains(id)){
				generator(id,schema,parent,childId,rootId,childVal,met,currentValue,tempCurrentValue,valueTotal,current);
			}
			if(value == lastElement){
				closureScope(id,type,valueMap,key,schema,method,currentValue,tempCurrentValue,childId,rootId,childVal,current,currentTotal);
			}
			else{
				otherScope(id,type,valueMap,key,schema,method,currentValue,tempCurrentValue,childVal,current,currentTotal);
			}

		}
	}
	
	public void closureScope(int id, String type, LinkedMultiValueMap<String, Object> valueMap, int key, List<Schema> schema, String method, List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,Multimap<Integer, Integer> childId, int rootId,Multimap<Integer, Object> childVal, long current, LinkedMultiValueMap<String, Long> currentTotal){
		if(!type.equals("ARRAY") || !type.equals("OBJECT")){
			ValueGenerator.flatValueGenerator(childVal,current,currentTotal,valueMap,method,currentValue,tempCurrentValue,id,key);
		}
		int currentId =1;
		endScope(currentId,key,schema,childId,rootId,childVal);
	}
	
	public void otherScope(int id, String type, LinkedMultiValueMap<String, Object> valueMap, int key, List<Schema> schema, String method, List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue, Multimap<Integer, Object> childVal, long current, LinkedMultiValueMap<String, Long> currentTotal){
		if(!type.equals("ARRAY") || !type.equals("OBJECT")){
			ValueGenerator.flatValueGenerator(childVal,current,currentTotal,valueMap,method,currentValue,tempCurrentValue,id,key);
		}
	}
	
	public void endScope(int currentId, int key, List<Schema> schema, Multimap<Integer, Integer> childId, int rootId,Multimap<Integer, Object> childVal){
		Collection<Integer> cId = childId.get(key);
		if(cId.isEmpty()){
			currentId = 1;
		}else{
			for(Integer ci : cId) {
				currentId= ci+1;
				childId.remove(key, ci);
				break;
			}
		}
		if (key==0){
			childVal.put(key,rootId);
			rootId++;
		}
		for(Schema s:schema) {
			int k = s.getKey();
			Collection<JsNode> values = s.getValue();
			for(JsNode value : values) {
				int pid = value.getId();
				if (pid==key){
					childVal.put(k,currentId);
					childVal.put(key,currentId);
					childId.put(key,currentId);
				}
			}
		}
	}


}
