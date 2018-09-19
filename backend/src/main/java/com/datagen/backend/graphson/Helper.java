package com.datagen.backend.graphson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.model.GraphsonId;
import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.notsql.helper.DistributedCurrentOccuranceHelper;

public class Helper {
	public static Collection<JsNode> getIdAsKey(Collection<JsNode> values,LinkedHashSet<Integer> parent,List<ValueCheck> valueTotal,int block){
		Collection<JsNode> keyVal = new ArrayList<JsNode>();
		for(JsNode value : values) {
			int id = value.getId();
			if(DistributedCurrentOccuranceHelper.isInBlock(valueTotal,id,block)){
				if(parent.contains(id)){
					keyVal.add(value);
				}
			}
		}
		return keyVal;
	}
	
	public static Collection<JsNode> getProperty(Collection<JsNode> values,LinkedHashSet<Integer> parent,List<ValueCheck> valueTotal,int block){
		Collection<JsNode> keyVal = new ArrayList<JsNode>();
		for(JsNode value : values) {
			int id = value.getId();
			if(DistributedCurrentOccuranceHelper.isInBlock(valueTotal,id,block)){
				if(!parent.contains(id)){
					keyVal.add(value);
				}
			}
		}
		return keyVal;
	}
	
	public static LinkedMultiValueMap<Integer, Long> setVertexId(List<Schema> schema,int block,List<ValueCheck> valueTotal,GraphsonId graphsonId){
		LinkedMultiValueMap<Integer, Long> vertexIdMap = new LinkedMultiValueMap<Integer, Long>();
		for(Schema s:schema){
			int key = s.getKey();
			if(key==0 || DistributedCurrentOccuranceHelper.isInBlock(valueTotal,key,block)){
				long vId = graphsonId.getVertexId()+1;
				graphsonId.setVertexId(vId);
				vertexIdMap.add(key, vId);
			}
		}
		return vertexIdMap;
	}
	
	
	public static long getVertex(LinkedMultiValueMap<Integer, Long> vertexIdMap,int key){
		long mappedId = 0;
		if(vertexIdMap.containsKey(key)){
			mappedId = vertexIdMap.getFirst(key);
		}
		return mappedId;
	}
	
	public static long getEdge(LinkedMultiValueMap<Integer, Long> edgeIdMap,int key){
		
		long mappedId = 0;
		if(edgeIdMap.containsKey(key)){
			mappedId = edgeIdMap.getFirst(key);
		}
		return mappedId;
	}
	
	public static GraphsonId setGraphsonId(LinkedMultiValueMap<Integer, Long> maxTotal, List<Schema> schema,List<ValueCheck> valueTotal){
		GraphsonId graphsonId= new GraphsonId();
		graphsonId.setVertexId(0);
		long edge = calculateEdgeStart(maxTotal,schema,valueTotal);
		graphsonId.setEdgeId(edge);
		graphsonId.setPropertyId(-1);
		return graphsonId;
	}
	
	public static long calculateEdgeStart(LinkedMultiValueMap<Integer, Long> maxTotal, List<Schema> schema,List<ValueCheck> valueTotal){
		
		long total = 0;
		for(int block : maxTotal.keySet()){
			long occur = maxTotal.getFirst(block);
			long temp = 0;
			for(Schema s:schema){
				int key = s.getKey();
				if(key==0 || DistributedCurrentOccuranceHelper.isInBlock(valueTotal,key,block)){
					temp++;
				}
			}
			total = total+(temp*occur);
		}
		return total;
	}
	
	
	//Flat Generator
	
	public static Collection<JsNode> getFlatIdAsKey(Collection<JsNode> values,LinkedHashSet<Integer> parent){
		Collection<JsNode> keyVal = new ArrayList<JsNode>();
		for(JsNode value : values) {
			int id = value.getId();
			if(parent.contains(id)){
				keyVal.add(value);
			}
		}
		return keyVal;
	}
	
	public static Collection<JsNode> getFlatProperty(Collection<JsNode> values,LinkedHashSet<Integer> parent){
		Collection<JsNode> keyVal = new ArrayList<JsNode>();
		for(JsNode value : values) {
			int id = value.getId();
			if(!parent.contains(id)){
				keyVal.add(value);
			}
		}
		return keyVal;
	}
	
	public static LinkedMultiValueMap<Integer, Long> setFlatVertexId(List<Schema> schema,GraphsonId graphsonId){
		LinkedMultiValueMap<Integer, Long> vertexIdMap = new LinkedMultiValueMap<Integer, Long>();
		for(Schema s:schema){
			int key = s.getKey();
			long vId = graphsonId.getVertexId()+1;
			graphsonId.setVertexId(vId);
			vertexIdMap.add(key, vId);
		}
		return vertexIdMap;
	}
	
	public static GraphsonId setFlatGraphsonId( List<Schema> schema,long totalData){
		GraphsonId graphsonId= new GraphsonId();
		graphsonId.setVertexId(0);
		long edge = calculateFlatEdgeStart(schema,totalData);
		graphsonId.setEdgeId(edge);
		graphsonId.setPropertyId(-1);
		return graphsonId;
	}
	
	public static long calculateFlatEdgeStart(List<Schema> schema,long totalData){
		return totalData*schema.size();
	}
}
