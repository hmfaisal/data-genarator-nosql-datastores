package com.datagen.backend.model;

import org.springframework.util.LinkedMultiValueMap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JsNode {
	private int id;
	private int parentid;
	private String parentName;
	private String nodeName;
	private int depth;
	private String valueType;
	LinkedMultiValueMap<Integer, Integer> occurance = new LinkedMultiValueMap<Integer, Integer>();
	LinkedMultiValueMap<String, Integer> loop = new LinkedMultiValueMap<String, Integer>();
	LinkedMultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<String, Object>();
	//Multimap<String, Object> valueMap = ArrayListMultimap.create();
	
	public JsNode(int id, int parentid, String parentName,String nodeName, int depth,String nodeType){
		this.id = id;
		this.parentid = parentid;
		this.nodeName = nodeName;
		this.parentName = parentName;
		this.depth = depth;
		this.valueType = nodeType;
	}
	
	public int getId() {
		return id;
	}
	public int setId(int id) {
		return this.id = id;
	}
	
	public int getParentId() {
		return parentid;
	}
	public int setParentId(int parentid) {
		return this.parentid = parentid;
	}
	
	public String getParentName() {
		return parentName;
	}
	public String setParentName(String parentName) {
		return this.parentName = parentName;
	}
	
	public String getNodeName() {
		return nodeName;
	}
	public String setNodeName(String nodeName) {
		return this.nodeName = nodeName;
	}
	
	public int getDepth() {
		return depth;
	}
	public int setDepth(int depth) {
		return this.depth = depth;
	}
	
	public String getValueType() {
		return valueType;
	}
	public String setValueType(String valueType) {
		return this.valueType = valueType;
	}
}
