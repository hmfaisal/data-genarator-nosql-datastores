package com.datagen.backend.xml;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.helper.MethodChecker;
import com.datagen.backend.xml.ValueGenerator;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.notsql.helper.DistributedCurrentOccuranceHelper;
import com.datagen.backend.notsql.helper.FieldOccuranceHelper;

public class DistributedGenerator {
	
	public StringBuilder init(List<Schema> schema,List<DgenMethod> method,long current,List<ValueCheck> valueTotal,List<ValueCheck> currentValueTotal,LinkedMultiValueMap<Integer, Long> maxTotal,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue){

		StringBuilder sb = new StringBuilder();
		LinkedHashSet<Integer> parent = new LinkedHashSet<Integer>();
		for(Schema s:schema){
			parent.add(s.getKey());
		}
		generator(0,schema,method,parent,sb,current,valueTotal,currentValueTotal,maxTotal,currentValue,tempCurrentValue);
		return sb;
	}


	public void generator(int key, List<Schema> schema,  List<DgenMethod> met,LinkedHashSet<Integer> parent,StringBuilder sb,long current,List<ValueCheck> valueTotal,List<ValueCheck> currentValueTotal,LinkedMultiValueMap<Integer, Long> maxTotal,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue){
		
		int block = FieldOccuranceHelper.getBlock(maxTotal,current);
		Collection<JsNode> values = null;
		for(Schema s:schema){
			if(s.getKey()==key){
				values = s.getValue();
			}
		}
		
		for(JsNode value : values) {
			int id = value.getId();
			int parentId = value.getParentId();
			LinkedMultiValueMap<String, Integer> pLoop = value.getLoop();
			if(DistributedCurrentOccuranceHelper.isInBlock(valueTotal,id,block)){
				int loop = FieldOccuranceHelper.getNodeLoop(pLoop,currentValueTotal,id,parentId,block);
				for(int i =1;i<=loop;i++){
					DistributedCurrentOccuranceHelper.setCurrentLoop(currentValueTotal, id, block);
					
					String name = value.getNodeName();
					String type = value.getValueType();
					
					LinkedMultiValueMap<String, Object> valueMap = value.getValueMap();
					String method = MethodChecker.getMethod(id,met);
					sb.append("<"+ name +">");
					if(!parent.contains(id)){
						valueScope(sb,type,current,valueMap,method,currentValue,tempCurrentValue,valueTotal,currentValueTotal,id);
					}else if(parent.contains(id)){
						generator(id,schema,met,parent,sb,current,valueTotal,currentValueTotal,maxTotal,currentValue,tempCurrentValue);
					}
					sb.append("</"+ name +">");
				}
			}
		}
			
	}
	
	public void valueScope(StringBuilder sb,String type,long current,LinkedMultiValueMap<String, Object> valueMap,String method,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,List<ValueCheck> valueTotal,List<ValueCheck> currentTotal,int id)	{
		if(type.equals("ARRAY")){
				
		}else if(type.equals("OBJECT")){
			
		}else{
			ValueGenerator.distributedValueGenerator(sb,current,valueMap,method,currentValue,tempCurrentValue,valueTotal,currentTotal,id);
		}
	}

}
