package com.datagen.backend.cql;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.notsql.helper.DistributedCurrentOccuranceHelper;
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
		generator(sb,schema,valueTotal,method,current,currentValue,tempCurrentValue,parent);
		return sb;
	}
	
	public void generator(StringBuilder sb,List<Schema> schema,List<ValueCheck> valueTotal,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,LinkedHashSet<Integer> parent){
		
		for(Schema s:schema){
			int key = s.getKey();
			Collection<JsNode> values = s.getValue();
			
			if(key==0){
				sb.append("CREATE"+" ");
				sb.append("(");
				String keyName = "root";
				createLabel(sb,keyName,key,current);
				sb.append("{");
				nodeGenerator(sb,schema,valueTotal,method,current,currentValue,tempCurrentValue,parent,key,values);
				sb.append("}");
				sb.append(")");
				sb.append("\n");
			}else{
				sb.append("CREATE"+" ");
				sb.append("(");
				JsNode node = DistributedCurrentOccuranceHelper.getNodeByKey(schema, key);
				String keyName = node.getNodeName();
				createLabel(sb,keyName,key,current);
				sb.append("{");
				nodeGenerator(sb,schema,valueTotal,method,current,currentValue,tempCurrentValue,parent,key,values);
				sb.append("}");
				sb.append(")");
				sb.append("\n");
			}
		}
		createRelation(schema,parent,sb,current);
	}
	
	public void nodeGenerator(StringBuilder sb,List<Schema> schema,List<ValueCheck> valueTotal,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,LinkedHashSet<Integer> parent,int key,Collection<JsNode> values){
		
		for(JsNode value : values) {
			int id = value.getId();
			String name = value.getNodeName();
				
			if(!parent.contains(id)){
				createProperty(sb,name);
				boolean nextIn = DistributedCurrentOccuranceHelper.nextButParent(values,id,valueTotal,parent);
				int lastElement = FieldOccuranceHelper.getLastElement(values);
				valueScope(value,id,sb,parent,valueTotal,method,current,currentValue,tempCurrentValue);
				closureScope(lastElement,id,sb,nextIn);
			}
		}
	}
	
	public void createProperty(StringBuilder sb, String name){
		sb.append("`"+name+"`"+":");
	}
	
	public void createLabel(StringBuilder sb, String pName, Integer key,long current){
		sb.append("`"+pName+key+current+"`");
		sb.append(":");
		sb.append("`"+pName+"`");
		sb.append(" ");
	}
	
	public void createRelation(List<Schema> schema, LinkedHashSet<Integer> parent, StringBuilder sb,long current){
		sb.append("CREATE"+"\n");
		int size=parent.size();
		int curSize=size-1;
		for(Schema s:schema){
			int key = s.getKey();
			Collection<JsNode> values = s.getValue();
			for(JsNode value : values) {
				int id = value.getId();
				String pName= value.getParentName();
				String name = value.getNodeName();
				if(parent.contains(id)){
					sb.append("("+"`"+pName+key+current+"`"+")");
					sb.append("-"+"["+":");
					sb.append("RELATES");
					sb.append("]"+"->");
					sb.append("("+"`"+name+id+current+"`"+")");
					if(curSize<=1){
						sb.append("\n");
					}else{
						sb.append(",");
						sb.append("\n");
					}
					curSize--;
				}
			}
		}
	}
	
	public void valueScope(JsNode value, int id, StringBuilder sb, LinkedHashSet<Integer> parent, List<ValueCheck> valueTotal, List<DgenMethod> met, long current, List<CurrentValue> currentValue, List<CurrentValue> tempCurrentValue){
		String type = value.getValueType();
		LinkedMultiValueMap<String, Object> valueMap = value.getValueMap();
		String method = MethodChecker.getMethod(id,met);
		LinkedMultiValueMap<String, Long> currentTotal = FlatCurrentOccuranceHelper.currentMap(id, valueTotal);
		if(type.equals("ARRAY")){
			
		}else if(type.equals("OBJECT")){
			
		}else{
			ValueGenerator.flatValueGenerator(sb,current,currentTotal,valueMap,method,currentValue,tempCurrentValue,id);
		}		
	}
	
	public void closureScope(int lastElement,int id,StringBuilder sb,boolean nextIn){
		if(id != lastElement && nextIn==true){
			sb.append(",");
		}
	}

}
