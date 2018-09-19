package com.datagen.backend.graphson;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.helper.MethodChecker;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.GraphsonId;
import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.notsql.helper.DistributedCurrentOccuranceHelper;
import com.datagen.backend.notsql.helper.FieldOccuranceHelper;
import com.datagen.backend.notsql.helper.ValueGenerator;

public class DistributedGenerator {
	
public StringBuilder init(List<Schema> schema,List<DgenMethod> method,long current,List<ValueCheck> valueTotal,List<ValueCheck> currentValueTotal,LinkedMultiValueMap<Integer, Long> maxTotal,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,GraphsonId graphsonId){
		
		StringBuilder sb = new StringBuilder();
		LinkedHashSet<Integer> parent = new LinkedHashSet<Integer>();
		for(Schema s:schema){
			parent.add(s.getKey());
		}
		
		int block = FieldOccuranceHelper.getBlock(maxTotal,current);
		LinkedMultiValueMap<Integer, Long> vertexIdMap = Helper.setVertexId(schema, block, valueTotal, graphsonId);
		LinkedMultiValueMap<Integer, Long> edgeIdMap = new LinkedMultiValueMap<Integer, Long>();
			
		generator(sb,schema,valueTotal,currentValueTotal,method,current,block,currentValue,tempCurrentValue,parent,graphsonId,vertexIdMap,edgeIdMap);
		return sb;
	}
	
	public void generator(StringBuilder sb,List<Schema> schema,List<ValueCheck> valueTotal,List<ValueCheck> currentValueTotal,List<DgenMethod> method,long current,int block,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,LinkedHashSet<Integer> parent,GraphsonId graphsonId,LinkedMultiValueMap<Integer, Long> vertexIdMap,LinkedMultiValueMap<Integer, Long> edgeIdMap){
		
		for(Schema s:schema){
			int key = s.getKey();
			Collection<JsNode> values = s.getValue();
			
			if(key==0){
				boolean hasNext= DistributedCurrentOccuranceHelper.rootNextInBlock(values,valueTotal,parent,block);
				sb.append("{");
				String keyName = "root";
				createId(sb,key,vertexIdMap);
				createLabel(sb,keyName);
				InEdgeGenerator(sb,schema,key,vertexIdMap,edgeIdMap);
				outEdgeGenerator(sb,values,valueTotal,parent,block, graphsonId, vertexIdMap, edgeIdMap);
				if(hasNext==true){
					propertyGenerator(sb,valueTotal,currentValueTotal,method,current,currentValue,tempCurrentValue,parent,key,block,values,graphsonId);
				}
				sb.append("}");
				sb.append("\n");
			}else{
				if(DistributedCurrentOccuranceHelper.isInBlock(valueTotal,key,block)){
					sb.append("{");
					JsNode node = DistributedCurrentOccuranceHelper.getNodeByKey(schema, key);
					String keyName = node.getNodeName();
					createId(sb,key,vertexIdMap);
					createLabel(sb,keyName);
					InEdgeGenerator(sb,schema,key,vertexIdMap,edgeIdMap);
					outEdgeGenerator(sb,values,valueTotal,parent,block, graphsonId, vertexIdMap, edgeIdMap);
					propertyGenerator(sb,valueTotal,currentValueTotal,method,current,currentValue,tempCurrentValue,parent,key,block,values,graphsonId);
					sb.append("}");
					sb.append("\n");
				}
			}
		}
		sb.deleteCharAt( sb.length() - 1 );
	}
	
	public void propertyGenerator(StringBuilder sb,List<ValueCheck> valueTotal,List<ValueCheck> currentValueTotal,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,LinkedHashSet<Integer> parent,int key,int block,Collection<JsNode> values,GraphsonId graphsonId){
		
		Collection<JsNode> property = Helper.getProperty(values,parent,valueTotal,block);
		int total = property.size();
		int counter = 1;
		if(property!=null){
			sb.append(",");
			sb.append("\"properties\":");
			sb.append("{");
			for(JsNode value : property) {
				int id = value.getId();
				String name = value.getNodeName();
				long propId = graphsonId.getPropertyId()+1;
				int loop = FieldOccuranceHelper.getLoop(valueTotal,id,block);
				graphsonId.setPropertyId(propId);
				
				sb.append("\""+name+"\":");
				sb.append("[{");
				sb.append("\"id\":");
				sb.append(propId);
				sb.append(",");
				sb.append("\"value\":");
				
				if(loop>1){
					sb.append("[");
				}
				for(int i =1;i<=loop;i++){
					DistributedCurrentOccuranceHelper.setCurrentLoop(currentValueTotal, id, block);
					
					valueScope(value,id,sb,parent,valueTotal,currentValueTotal,method,current,currentValue,tempCurrentValue);
					if(loop>1){
						if(i==loop){
							sb.append("]");
						}else {
							sb.append(",");
						}
					}
				}
				sb.append("}]");
				if(counter<total){
					sb.append(",");
					counter++;
				}
			}
			
			sb.append("}");
		}
	}
	
	public void InEdgeGenerator(StringBuilder sb,List<Schema> schema,int key,LinkedMultiValueMap<Integer, Long> vertexIdMap,LinkedMultiValueMap<Integer, Long> edgeIdMap){
		
		if(edgeIdMap.containsKey(key)){
			sb.append(",");
			sb.append("\"inE\":");
			sb.append("{");
			sb.append("\"knows\":");
			sb.append("[");
			JsNode node = DistributedCurrentOccuranceHelper.getNodeByKey(schema, key);
			int pId = node.getParentId();
			long vId = Helper.getVertex(vertexIdMap, pId);
			long eId = Helper.getEdge(edgeIdMap, key);
			createInEdge(sb,vId,eId);
			sb.append("]");
			sb.append("}");
		}
	}
	
	public void outEdgeGenerator(StringBuilder sb,Collection<JsNode> values,List<ValueCheck> valueTotal,LinkedHashSet<Integer> parent,int block,GraphsonId graphsonId,LinkedMultiValueMap<Integer, Long> vertexIdMap,LinkedMultiValueMap<Integer, Long> edgeIdMap){
		
		Collection<JsNode> outEdge = Helper.getIdAsKey(values,parent,valueTotal,block);
		int total = outEdge.size();
		int counter = 1;
		if(outEdge.isEmpty()){
			
		}else{
			sb.append(",");
			sb.append("\"outE\":");
			sb.append("{");
			sb.append("\"knows\":");
			sb.append("[");
			for(JsNode value : outEdge) {
				int id = value.getId();
				long vId = Helper.getVertex(vertexIdMap, id);
				long eId = Helper.getEdge(edgeIdMap, id);
				if(eId==0){
					eId = graphsonId.getEdgeId()+1;
					graphsonId.setEdgeId(eId);
					edgeIdMap.add(id, eId);
				}else{
					graphsonId.setEdgeId(eId);
					edgeIdMap.add(id, eId);
				}
				createOutEdge(sb,vId,eId);
				if(counter<total){
					sb.append(",");
					counter++;
				}
			}
			sb.append("]");
			sb.append("}");
		}
	}
	
	public void createId(StringBuilder sb, int key, LinkedMultiValueMap<Integer, Long> vertexIdMap){
		long vId = Helper.getVertex(vertexIdMap,key);
		sb.append("\"id\":");
		sb.append(vId);
	}
	
	public void createLabel(StringBuilder sb, String pName){
		sb.append(",");
		sb.append("\"label\":");
		sb.append("\""+pName+"\"");
	}
	
	public void createInEdge(StringBuilder sb, long vId, long eId){
		sb.append("{");
		sb.append("\"id\":");
		sb.append(eId);
		sb.append(",");
		sb.append("\"outV\":");
		sb.append(vId);
		sb.append("}");
	}
	
	public void createOutEdge(StringBuilder sb, long vId, long eId){
		sb.append("{");
		sb.append("\"id\":");
		sb.append(eId);
		sb.append(",");
		sb.append("\"inV\":");
		sb.append(vId);
		sb.append("}");
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

}

