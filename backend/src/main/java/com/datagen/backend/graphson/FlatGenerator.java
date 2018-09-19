package com.datagen.backend.graphson;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.notsql.helper.DistributedCurrentOccuranceHelper;
import com.datagen.backend.notsql.helper.FlatCurrentOccuranceHelper;
import com.datagen.backend.notsql.helper.ValueGenerator;
import com.datagen.backend.helper.MethodChecker;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.GraphsonId;
import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;

public class FlatGenerator {
	
public StringBuilder init(List<Schema> schema,List<ValueCheck> valueTotal,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,GraphsonId graphsonId){
		
		StringBuilder sb = new StringBuilder();
		LinkedHashSet<Integer> parent = new LinkedHashSet<Integer>();
		for(Schema s:schema){
			parent.add(s.getKey());
		}
		LinkedMultiValueMap<Integer, Long> vertexIdMap = Helper.setFlatVertexId(schema, graphsonId);
		LinkedMultiValueMap<Integer, Long> edgeIdMap = new LinkedMultiValueMap<Integer, Long>();
		generator(sb,schema,valueTotal,method,current,currentValue,tempCurrentValue,parent,graphsonId,vertexIdMap,edgeIdMap);
		return sb;
	}
	
	public void generator(StringBuilder sb,List<Schema> schema,List<ValueCheck> valueTotal,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,LinkedHashSet<Integer> parent,GraphsonId graphsonId,LinkedMultiValueMap<Integer, Long> vertexIdMap,LinkedMultiValueMap<Integer, Long> edgeIdMap){
		
		for(Schema s:schema){
			int key = s.getKey();
			Collection<JsNode> values = s.getValue();
			
			if(key==0){
				sb.append("{");
				String keyName = "root";
				createId(sb,key,vertexIdMap);
				createLabel(sb,keyName);
				InEdgeGenerator(sb,schema,key,vertexIdMap,edgeIdMap);
				outEdgeGenerator(sb,values,parent,graphsonId, vertexIdMap, edgeIdMap);
				propertyGenerator(sb,valueTotal,method,current,currentValue,tempCurrentValue,parent,key,values,graphsonId);
				sb.append("}");
				sb.append("\n");
			}else{
				sb.append("{");
				JsNode node = DistributedCurrentOccuranceHelper.getNodeByKey(schema, key);
				String keyName = node.getNodeName();
				createId(sb,key,vertexIdMap);
				createLabel(sb,keyName);
				InEdgeGenerator(sb,schema,key,vertexIdMap,edgeIdMap);
				outEdgeGenerator(sb,values,parent, graphsonId, vertexIdMap, edgeIdMap);
				propertyGenerator(sb,valueTotal,method,current,currentValue,tempCurrentValue,parent,key,values,graphsonId);
				sb.append("}");
				sb.append("\n");
			}
		}
		sb.deleteCharAt( sb.length() - 1 );
	}
	
	public void propertyGenerator(StringBuilder sb,List<ValueCheck> valueTotal,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,LinkedHashSet<Integer> parent,int key,Collection<JsNode> values,GraphsonId graphsonId){
		
		Collection<JsNode> property = Helper.getFlatProperty(values,parent);
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
				graphsonId.setPropertyId(propId);
				
				sb.append("\""+name+"\":");
				sb.append("[{");
				sb.append("\"id\":");
				sb.append(propId);
				sb.append(",");
				sb.append("\"value\":");
				
				valueScope(value,id,sb,parent,valueTotal,method,current,currentValue,tempCurrentValue);
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
	
	public void outEdgeGenerator(StringBuilder sb,Collection<JsNode> values,LinkedHashSet<Integer> parent,GraphsonId graphsonId,LinkedMultiValueMap<Integer, Long> vertexIdMap,LinkedMultiValueMap<Integer, Long> edgeIdMap){
		
		Collection<JsNode> outEdge = Helper.getFlatIdAsKey(values,parent);
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

}
