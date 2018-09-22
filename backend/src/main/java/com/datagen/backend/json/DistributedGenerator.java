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
		jgenerator(0,schema,method,parent,sb,current,valueTotal,currentValueTotal,block,currentValue,tempCurrentValue);
		sb.append("}");
		return sb;
	}

	public void jgenerator(int key, List<Schema> schema,  List<DgenMethod> met,LinkedHashSet<Integer> parent,StringBuilder sb,long current,List<ValueCheck> valueTotal,List<ValueCheck> currentValueTotal,int block,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue){
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
			String name = value.getNodeName();
			String type = value.getValueType();
			LinkedMultiValueMap<String, Object> valueMap = value.getValueMap();
			String method = MethodChecker.getMethod(id,met);
			if(DistributedCurrentOccuranceHelper.isInBlock(valueTotal,id,block)){
				if(!parent.contains(id)){
					DistributedCurrentOccuranceHelper.setCurrentLoop(currentValueTotal, id, block);
					sb.append("\""+ name +"\"" + ":");
					valueScope(sb,type,current,valueMap,method,currentValue,tempCurrentValue,valueTotal,currentValueTotal,id);
					boolean nextInBlock = DistributedCurrentOccuranceHelper.nextInBlock(values,id,valueTotal,block);
					int lastElement = FieldOccuranceHelper.getLastElement(values);
					if(id != lastElement && nextInBlock==true){
						sb.append(",");
					}
				}
				else if(parent.contains(id)){
					sb.append("\""+ name +"\"" + ":");
					openBracketScope(type,sb);
					int loop = FieldOccuranceHelper.getNodeJsonLoop(pLoop,currentValueTotal,id,block,parent,schema);
					for(int i =1;i<=loop;i++){
						DistributedCurrentOccuranceHelper.setCurrentLoop(currentValueTotal, id, block);
						if(i>1){
							sb.append("{");
						}
						jgenerator(id,schema,met,parent,sb,current,valueTotal,currentValueTotal,block,currentValue,tempCurrentValue);
						//closeBracketScope(key, i, loop, id,values, sb, schema, valueTotal,block);
						int lastElement = FieldOccuranceHelper.getLastElement(values);
						boolean nextInBlock = DistributedCurrentOccuranceHelper.nextInBlock(values,id,valueTotal,block);
						if(type.equals("ARRAY")){
							if(i<loop){
								sb.append("},");
							}else if(i==loop && (id != lastElement && nextInBlock==true)){
								sb.append("}],");
							}else if(i==loop && (id == lastElement || nextInBlock==false)){
								sb.append("}]");
							}
						}else if(type.equals("OBJECT")){
							if(i==loop && (id != lastElement && nextInBlock==true)){
								sb.append("},");
							}else if(i==loop && (id == lastElement || nextInBlock==false)){
								sb.append("}");
							}
						}

					}
				}
			}
			
		}
	}
	/*
	public void generator(int key, List<Schema> schema,  List<DgenMethod> met,LinkedHashSet<Integer> parent,StringBuilder sb,long current,List<ValueCheck> valueTotal,List<ValueCheck> currentValueTotal,int block,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue){

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
			String name = value.getNodeName();
			String type = value.getValueType();
			LinkedMultiValueMap<String, Object> valueMap = value.getValueMap();
			String method = MethodChecker.getMethod(id,met);
			if(DistributedCurrentOccuranceHelper.isInBlock(valueTotal,id,block)){
				//int loop = FieldOccuranceHelper.getLoop(valueTotal,id,block);
				//int loop = FieldOccuranceHelper.getNodeLoop(pLoop,currentValueTotal,id,parentId,block);
				int loop = 1;
				if(parent.contains(id)){
					loop = FieldOccuranceHelper.getNodeJsonLoop(pLoop,currentValueTotal,id,parentId,block,parent,schema);
				}
				for(int i =1;i<=loop;i++){
					DistributedCurrentOccuranceHelper.setCurrentLoop(currentValueTotal, id, block);
					if(i>1){
						sb.append("{");
					}
					if(!parent.contains(id)){
						sb.append("\""+ name +"\"" + ":");
						valueScope(sb,type,current,valueMap,method,currentValue,tempCurrentValue,valueTotal,currentValueTotal,id);
					}else if(parent.contains(id)){
						if(i==1){
							sb.append("\""+ name +"\"" + ":");
							openBracketScope(type,sb);
						}
						generator(id,schema,met,parent,sb,current,valueTotal,currentValueTotal,block,currentValue,tempCurrentValue);
					}
					
					closeBracketScope(key, i, loop, id,values, sb, schema, valueTotal,block);
				}
				
			}
		}
	}
	*/
	
	
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
	/*
	public static void closeBracketScope(int key,int i,int loop, int id, Collection<JsNode> values,StringBuilder sb, List<Schema> schema,List<ValueCheck> valueTotal,int block){
		String ptype = checkEndType(key,schema);
		int lastElement = FieldOccuranceHelper.getLastElement(values);
		boolean nextInBlock = DistributedCurrentOccuranceHelper.nextInBlock(values,id,valueTotal,block);
		if(ptype!=null){
			if(ptype.equals("ARRAY")){
				if(i<loop){
					sb.append("},");
				}else if(nextInBlock==false){
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
	*/

}

