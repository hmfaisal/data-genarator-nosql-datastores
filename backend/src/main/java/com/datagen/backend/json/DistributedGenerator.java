package com.datagen.backend.json;

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
import com.datagen.backend.notsql.helper.DistributedCurrentOccuranceHelper;
import com.datagen.backend.notsql.helper.FieldOccuranceHelper;
import com.datagen.backend.notsql.helper.ValueGenerator;

public class DistributedGenerator {
	
	public StringBuilder init(List<Schema> schema,List<DgenMethod> method,long current,List<ValueCheck> valueTotal,List<ValueCheck> currentValueTotal,LinkedMultiValueMap<Integer, Long> maxTotal,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue){

		StringBuilder sb = new StringBuilder();
		LinkedHashSet<Integer> parent = new LinkedHashSet<Integer>();
		for(Schema s:schema){
			parent.add(s.getKey());
		}
		DistributedCurrentOccuranceHelper.clearCurrentLoop(currentValueTotal);
		int block = FieldOccuranceHelper.getBlock(maxTotal,current);
		sb.append("{");
		generator(0,schema,method,parent,sb,current,valueTotal,currentValueTotal,block,currentValue,tempCurrentValue);
		sb.append("}");
		return sb;
	}
	
	public void generator(int key, List<Schema> schema,  List<DgenMethod> met,LinkedHashSet<Integer> parent,StringBuilder sb,long current,List<ValueCheck> valueTotal,List<ValueCheck> currentValueTotal,int block,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue){
		/*
		String temp = sb.toString();
		String lastChar = temp.substring(temp.length() - 1); 
		if(lastChar.equals(",")){
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("}");
		}else{
			sb.append("}");
		}
		*/
		
		
		Collection<JsNode> values = null;
		for(Schema s:schema){
			if(s.getKey()==key){
				values = s.getValue();
			}
		}
		for(JsNode value : values) {
			int id = value.getId();
			if(DistributedCurrentOccuranceHelper.isInBlock(valueTotal,id,block)){
				int loop = FieldOccuranceHelper.getLoop(valueTotal,id,block);
				for(int i =1;i<=loop;i++){
					DistributedCurrentOccuranceHelper.setCurrentLoop(currentValueTotal, id, block);
					if(i>1){
						sb.append("{");
					}
					String name = value.getNodeName();
					String type = value.getValueType();
					LinkedMultiValueMap<String, Object> valueMap = value.getValueMap();
					String method = MethodChecker.getMethod(id,met);

					sb.append("\""+ name +"\"" + ":");
					if(!parent.contains(id)){
						valueScope(sb,type,current,valueMap,method,currentValue,tempCurrentValue,valueTotal,currentValueTotal,id);
					}else if(parent.contains(id)){
						openBracketScope(type,sb);
						generator(id,schema,met,parent,sb,current,valueTotal,currentValueTotal,block,currentValue,tempCurrentValue);
					}
					
					closeBracketScope(key, i, loop, id,values, sb, schema, valueTotal,block);
				}
				
			}
		}
	}
	
	public void valueScope(StringBuilder sb,String type,long current,LinkedMultiValueMap<String, Object> valueMap,String method,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,List<ValueCheck> valueTotal,List<ValueCheck> currentTotal,int id)	{
		if(type.equals("ARRAY")){
				
		}else if(type.equals("OBJECT")){
			
		}else if(type.equals("ARRAY_VALUE")){
			sb.append("[");
			ValueGenerator.distributedValueGenerator(sb,current,valueMap,method,currentValue,tempCurrentValue,valueTotal,currentTotal,id);
			sb.append("]");
		}else{
			ValueGenerator.distributedValueGenerator(sb,current,valueMap,method,currentValue,tempCurrentValue,valueTotal,currentTotal,id);
		}
	}
	
	public static void openBracketScope(String type, StringBuilder sb){
		if(type.equals("ARRAY")){
			sb.append("[{");
		}
		else{
			sb.append("{");
		}
	}
	
	public static void closeBracketScope(int key,int i,int loop, int id, Collection<JsNode> values,StringBuilder sb, List<Schema> schema,List<ValueCheck> valueTotal,int block){
		String ptype = checkEndType(key,schema);
		int lastElement = FieldOccuranceHelper.getLastElement(values);
		boolean nextInBlock = DistributedCurrentOccuranceHelper.nextInBlock(values,id,valueTotal,block);
		if(ptype!=null){
			if(ptype.equals("ARRAY")){
				if(i<loop){
					sb.append("},");
				}else if(id == lastElement || nextInBlock==false){
					sb.append("}]");
				}else if(id != lastElement && nextInBlock==true){
					sb.append(",");
				}
				
			}else if(ptype.equals("OBJECT")){
				if(id == lastElement || nextInBlock==false){
					sb.append("}");
				}else if(id != lastElement && nextInBlock==true){
					sb.append(",");
				}
			}
		}else{
			if(nextInBlock==true){
				sb.append(",");
			}
		}
	}
	
	public static String checkEndType(int key, List<Schema> schema){
		String ptype = null;
		for(Schema s:schema){
			Collection<JsNode> values = s.getValue();
			for(JsNode value : values) {
    			int pid = value.getId();
    			if(pid==key){
    				ptype = value.getValueType();
    			}
    		}
		}
		return ptype;
	}

}

