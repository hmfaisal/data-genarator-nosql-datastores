package com.datagen.backend.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.datagen.backend.model.JsNode;
import com.datagen.backend.parser.JsonTreeHelper;

public class JsonTreeWalker {
	
	static int ID;
	static int ITER;

    public static Multimap<Integer, JsNode> parseTree(String json) throws IOException {
    	
        ObjectMapper m = new ObjectMapper();
        
        List<JsNode> NODE = new ArrayList<JsNode>();
    	Multimap<Integer, JsNode> MAP = LinkedListMultimap.create();
    	LinkedHashSet<Integer> PARENT = new LinkedHashSet<Integer>();
    	ID = 1;
    	ITER = 0;
    	
        JsonNode rootNode = m.readTree(json);
        JsonNode mainNode = rootNode;
        walker(rootNode, mainNode, 0, "root",NODE,MAP,PARENT,1);

        return MAP;
    }
    
    private static void walker(JsonNode parentNode, JsonNode mainNode, int depth, String parent,List<JsNode> NODE,Multimap<Integer, JsNode> MAP,LinkedHashSet<Integer> PARENT,int pID) {
    	
        if (parentNode.isArray()) {
            Iterator<JsonNode> iter = parentNode.elements();
            while (iter.hasNext()) {
                JsonNode node = iter.next();
                if (node.isObject() || node.isArray()) {
                	if(parentNode.equals(mainNode)){
                		ITER++;
                	}
                	walker(node, mainNode, depth+1, parent,NODE,MAP,PARENT,pID);
                } 
                if (!node.isObject()) {
                	JsonValueHelper.arrayValueSetter(NODE,pID,node);
                }
            }
        }
        if (parentNode.isObject()) {
            Iterator<String> iter = parentNode.fieldNames();
            int parentID = JsonTreeHelper.getParentId(parent,NODE);

            while (iter.hasNext()) {
                String nodeName = iter.next();
                JsonNode node = parentNode.path(nodeName);
                String nodeType= node.getNodeType().toString();
                //String valueN = node.toString();

                int check = JsonTreeHelper.checkNode(parent,nodeName,depth,NODE);
                
                if (check==0){
                	JsonTreeHelper.addNode(ID,parentID,parent,nodeName,depth,nodeType,NODE,MAP,PARENT);
                	JsNode n = JsonTreeHelper.getNode(ID, NODE);
                	JsonTreeHelper.occuranceSetter(n,ITER);
                    JsonTreeHelper.loopSetter(n,ITER,NODE);
                	JsonValueHelper.valueSetter(n,node);
                	int tempID= ID;
                	ID++;
                	if (node.isObject() || node.isArray()) {
                		walker(node, mainNode, depth+1, nodeName,NODE,MAP,PARENT,tempID);
                	}
                	
                }else if(check>0){
                	JsNode n = JsonTreeHelper.getNode(check, NODE);
                	JsonTreeHelper.occuranceSetter(n,ITER);
                    JsonTreeHelper.loopSetter(n,ITER,NODE);
                	JsonValueHelper.valueSetter(n,node);
                	if (node.isObject() || node.isArray()) {
                		walker(node, mainNode, depth+1, nodeName,NODE,MAP,PARENT,check);
                	}
                }
                
            }
        }
    }

}

