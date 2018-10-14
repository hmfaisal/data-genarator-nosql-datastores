package com.datagen.backend.cql;

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
		int block = FieldOccuranceHelper.getBlock(maxTotal,current);
		generator(sb,schema,valueTotal,currentValueTotal,block,method,current,currentValue,tempCurrentValue,parent);
		return sb;
	}
	
	public void generator(StringBuilder sb,List<Schema> schema,List<ValueCheck> valueTotal,List<ValueCheck> currentValueTotal,int block,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,LinkedHashSet<Integer> parent){
		
		for(Schema s:schema){
			int key = s.getKey();
			Collection<JsNode> values = s.getValue();
			
			if(key==0){
				sb.append("CREATE"+" ");
				sb.append("(");
				String keyName = "root";
				createLabel(sb,keyName,key,current);
				boolean hasNext= DistributedCurrentOccuranceHelper.rootNextInBlock(values,valueTotal,parent,block);
				
				if(hasNext==true){
					sb.append("{");
					nodeGenerator(sb,schema,valueTotal,currentValueTotal,method,current,currentValue,tempCurrentValue,parent,key,block,values);
					sb.append("}");
					sb.append(")");
					sb.append("\n");
				}else{
					sb.append(")");
					sb.append("\n");
				}
			}else{
				if(DistributedCurrentOccuranceHelper.isInBlock(valueTotal,key,block)){
					sb.append("CREATE"+" ");
					sb.append("(");
					JsNode node = DistributedCurrentOccuranceHelper.getNodeByKey(schema, key);
					String keyName = node.getNodeName();
					createLabel(sb,keyName,key,current);
					sb.append("{");
					nodeGenerator(sb,schema,valueTotal,currentValueTotal,method,current,currentValue,tempCurrentValue,parent,key,block,values);
					sb.append("}");
					sb.append(")");
					sb.append("\n");
				}
			}
		}
		createRelation(schema,parent,sb,valueTotal,block,current);
	}
	
	public void nodeGenerator(StringBuilder sb,List<Schema> schema,List<ValueCheck> valueTotal,List<ValueCheck> currentValueTotal,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,LinkedHashSet<Integer> parent,int key,int block,Collection<JsNode> values){
		
		for(JsNode value : values) {
			int id = value.getId();
			String name = value.getNodeName();
			int parentId = value.getParentId();
			LinkedMultiValueMap<String, Integer> pLoop = value.getLoop();
			if(DistributedCurrentOccuranceHelper.isInBlock(valueTotal,id,block)){
				
				if(!parent.contains(id)){
					createProperty(sb,name);
					int loop = FieldOccuranceHelper.getLoop(valueTotal,id,block);
					//int loop = FieldOccuranceHelper.getNodeLoop(pLoop,currentValueTotal,id,parentId,block);
					if(loop>1){
						sb.append("[");
					}
					for(int i =1;i<=loop;i++){
						DistributedCurrentOccuranceHelper.setCurrentLoop(currentValueTotal, id, block);
						boolean nextInBlock = DistributedCurrentOccuranceHelper.nextButParentInBlock(values,id,valueTotal,parent,block);
						int lastElement = FieldOccuranceHelper.getLastElement(values);
						valueScope(value,id,sb,parent,valueTotal,currentValueTotal,method,current,currentValue,tempCurrentValue);
						closureScope(loop,i,lastElement,nextInBlock,id,sb);
					}
				}
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
	
	public void createRelation(List<Schema> schema, LinkedHashSet<Integer> parent, StringBuilder sb,List<ValueCheck> valueTotal,int block,long current){
		boolean hasChild = hasChild(schema,parent);
		if (hasChild){
			sb.append("CREATE"+"\n");
		}
		for(Schema s:schema){
			int key = s.getKey();
			Collection<JsNode> values = s.getValue();
			for(JsNode value : values) {
				int id = value.getId();
				if(parent.contains(id) && (DistributedCurrentOccuranceHelper.isInBlock(valueTotal,id,block))){
					String pName= value.getParentName();
					String name = value.getNodeName();
					boolean hasNext= DistributedCurrentOccuranceHelper.nextParentInBlock(id,valueTotal,parent,block);
					sb.append("("+"`"+pName+key+current+"`"+")");
					sb.append("-"+"["+":");
					sb.append("RELATES");
					sb.append("]"+"->");
					sb.append("("+"`"+name+id+current+"`"+")");
					if(hasNext==true){
						sb.append(",");
					}
					sb.append("\n");
				}
			}
		}
	}

	public boolean hasChild(List<Schema> schema,LinkedHashSet<Integer> parent){
		for(Schema s:schema){
			int key = s.getKey();
			Collection<JsNode> values = s.getValue();
			for(JsNode value : values) {
				int id = value.getId();
				if(parent.contains(id)){
					return true;
				}
			}
		}
		return false;
	}
	
	public void valueScope(JsNode value, int id, StringBuilder sb, LinkedHashSet<Integer> parent, List<ValueCheck> valueTotal,List<ValueCheck> currentTotal, List<DgenMethod> met, long current, List<CurrentValue> currentValue, List<CurrentValue> tempCurrentValue){
		String type = value.getValueType();
		LinkedMultiValueMap<String, Object> valueMap = value.getValueMap();
		String method = MethodChecker.getMethod(id,met);
		if(type.equals("ARRAY")){
			
		}else if(type.equals("OBJECT")){
			
		}else{
			ValueGenerator.distributedValueGenerator(sb,current,valueMap,method,currentValue,tempCurrentValue,valueTotal,currentTotal,id);
		}
	}
	
	public void closureScope(int loop, int i,int lastElement,boolean nextInBlock,int id,StringBuilder sb){
		if(loop>1){
			if(i==loop){
				if(nextInBlock==true){
					sb.append("],");
				}else{
					sb.append("]");
				}
			}else if(i!=loop){
				sb.append(",");
			}
		}else{
			if(id != lastElement && nextInBlock==true){
				sb.append(",");
			}
		}
	}
}
