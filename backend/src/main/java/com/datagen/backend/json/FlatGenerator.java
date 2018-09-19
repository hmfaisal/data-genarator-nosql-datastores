package com.datagen.backend.json;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.notsql.helper.FieldOccuranceHelper;
import com.datagen.backend.notsql.helper.FlatCurrentOccuranceHelper;
import com.datagen.backend.notsql.helper.ValueGenerator;
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
		sb.append("{");
		generator(0,schema,valueTotal,method,parent,sb,current,currentValue,tempCurrentValue);
		sb.append("}");
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
			
			sb.append("\""+ name +"\"" + ":");
			if(!parent.contains(id)){
				valueScope(sb,type,current,currentTotal,valueMap,method,currentValue,tempCurrentValue,id);
			}else if(parent.contains(id)){
				openBracketScope(type,sb);
				generator(id,schema,valueTotal,met,parent,sb,current,currentValue,tempCurrentValue);
			}
			closeBracketScope(key,id,values, sb, schema);

		}
	}

	public void valueScope(StringBuilder sb,String type,long current,LinkedMultiValueMap<String, Long> currentTotal,LinkedMultiValueMap<String, Object> valueMap,String method,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id)	{
		if(type.equals("ARRAY")){

		}else if(type.equals("OBJECT")){
			
		}else if(type.equals("ARRAY_VALUE")){
			sb.append("[");
			ValueGenerator.flatValueGenerator(sb,current,currentTotal,valueMap,method,currentValue,tempCurrentValue,id);
			sb.append("]");
		}else{
			ValueGenerator.flatValueGenerator(sb,current,currentTotal,valueMap,method,currentValue,tempCurrentValue,id);
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
	
	public static void closeBracketScope(int key, int id, Collection<JsNode> values,StringBuilder sb, List<Schema> schema){
		String ptype = checkEndType(key,schema);
		int lastElement = FieldOccuranceHelper.getLastElement(values);
		if(ptype!=null){
			if(ptype.equals("ARRAY")){
				if(id == lastElement ){
					sb.append("}]");
				}else if(id != lastElement){
					sb.append(",");
				}
				
			}else if(ptype.equals("OBJECT")){
				if(id == lastElement){
					sb.append("}");
				}else if(id != lastElement){
					sb.append(",");
				}
			}
		}else{
			if(id != lastElement){
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
