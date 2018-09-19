package com.datagen.backend.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.service.SchemaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Multimap;
import com.datagen.backend.parser.JsonTreeWalker;

@Service
public class SchemaServiceImpl implements SchemaService{
	
	public Multimap<Integer, JsNode> parse(String fileName) throws IOException{
		String json = this.read(fileName);
		return JsonTreeWalker.parseTree(json);
	}
	
	public String read(String fileName) throws IOException{
		String content = new String(Files.readAllBytes(Paths.get(fileName)));
		return content;
	}
	
	public List<Schema> getSchema(Multimap<Integer, JsNode> map){
		List<Schema> result = new ArrayList<>();
		
		for(Integer key : map.keySet()) {
			Schema data = new Schema();
    		data.setKey(key);
    		Collection<JsNode> values = map.get(key);
    		data.setValue(values);
    		result.add(data);
    	}
		return result;
	}

}
