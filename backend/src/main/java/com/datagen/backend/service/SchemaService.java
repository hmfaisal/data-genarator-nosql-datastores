package com.datagen.backend.service;

import java.io.IOException;
import java.util.List;

import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.google.common.collect.Multimap;


public interface SchemaService {
	
	Multimap<Integer, JsNode>  parse(String fileName) throws IOException;
	String read(String fileName) throws IOException;
	List<Schema> getSchema(Multimap<Integer, JsNode> map);
}
