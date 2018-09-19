package com.datagen.backend.xml;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.notsql.helper.FlatCurrentOccuranceHelper;
import com.datagen.backend.xml.ValueGenerator;
import com.datagen.backend.helper.MethodChecker;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;

public class FlatGenerator {
	
	public StringBuilder init(List<Schema> schema,List<ValueCheck> valueTotal,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue){

		StringBuilder sb = new StringBuilder();
		LinkedHashSet<Integer> parent = new LinkedHashSet<Integer>();
		for(Schema s:schema){
			parent.add(s.getKey());
		}
		generator(0,schema,valueTotal,method,parent,sb,current,currentValue,tempCurrentValue);
		return sb;
	}


	public void generator(int key, List<Schema> schema, List<ValueCheck> valueTotal, List<DgenMethod> met,LinkedHashSet<Integer> parent,StringBuilder sb,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue){
		
		Collection<JsNode> values = null;
		for(Schema s:schema){
			if(s.getKey()==key){
				values = s.getValue();
			}
		}
		
		for(JsNode value : values) {
			int id = value.getId();
			String name = value.getNodeName();
			String type = value.getValueType();
			
			LinkedMultiValueMap<String, Object> valueMap = value.getValueMap();
			String method = MethodChecker.getMethod(id,met);
			LinkedMultiValueMap<String, Long> currentTotal = FlatCurrentOccuranceHelper.currentMap(id, valueTotal);
			
			sb.append("<"+ name +">");
			if(!parent.contains(id)){
				valueScope(sb,type,current,currentTotal, valueMap,method,currentValue,tempCurrentValue,id);
			}else if(parent.contains(id)){
				generator(id,schema,valueTotal,met,parent,sb,current,currentValue,tempCurrentValue);
			}
			sb.append("</"+ name +">");
		}
	}
	
	public void valueScope(StringBuilder sb,String type,long current,LinkedMultiValueMap<String, Long> currentTotal,LinkedMultiValueMap<String, Object> valueMap,String method,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id)	{
		if(type.equals("ARRAY")){
				
		}else if(type.equals("OBJECT")){
			
		}else{
			ValueGenerator.flatValueGenerator(sb,current,currentTotal,valueMap,method,currentValue,tempCurrentValue,id);
		}
	}

}
