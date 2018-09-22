package com.datagen.backend.parser;

import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.model.JsNode;
import com.google.common.collect.Multimap;


public class JsonTreeHelper {
	
	public static void addNode(int id, int parentID, String par, String nodeName, int depth, String nodeT,List<JsNode> NODE,Multimap<Integer, JsNode> map,LinkedHashSet<Integer> parent){
		JsNode field = new JsNode(id,parentID,par,nodeName,depth,nodeT);
    	NODE.add(field);
    	map.put(parentID, field);
    	parent.add(parentID);
	}
	
	
    public static int checkNode(String parent, String node, int depth,List<JsNode> NODE){
    	if(!NODE.isEmpty()){
        	for (int i = 0; i < NODE.size(); i++) {
        		String c = NODE.get(i).getNodeName();
        		String p = NODE.get(i).getParentName();
        		int dep = NODE.get(i).getDepth();
        		
        		if(node==c && parent==p && depth==dep){
        			int node_id = NODE.get(i).getId();
        			return node_id;
        		}
        	}	
        }
		return 0;
    }
    
    public static JsNode getNode(int id,List<JsNode> NODE){
    	if(!NODE.isEmpty()){
    		for(JsNode node : NODE){
    			int i = node.getId();
    			if(i==id){
    				return node;
    			}
    		}
    	}
    	return null;
    }
    
    public static int getParentId(String parent,List<JsNode> NODE){
    	int parentID = 0;
    	if(!NODE.isEmpty()){
        	for (int i = NODE.size()-1; i >= 0; i--) {
        		int pID = NODE.get(i).getId();
        		String pName = NODE.get(i).getNodeName();
        		if(parent==pName){
        			parentID = pID;
        			return parentID;
        		}
        	}	
        } 	
    	return parentID;
    }
    
    public static void occuranceSetter(JsNode n,int iter){
    	LinkedMultiValueMap<Integer, Integer> occurance = n.getOccurance();
    	if(occurance.isEmpty()){
    		occurance.set(iter, 1);
    	}else{
			if(occurance.containsKey(iter)){
				int cur= occurance.getFirst(iter);
				int newCur = cur+1;
    			occurance.set(iter, newCur);
			}else{
				occurance.set(iter, 1);
			}
    	}
		n.setOccurance(occurance);
    }

	public static void loopSetter(JsNode n,int iter,List<JsNode> NODE){
		int id = n.getId();
		int parentId=n.getParentId();
    	LinkedMultiValueMap<String, Integer> loop = n.getLoop();
		int parentCurrent = 1;
		if(parentId==0){

		}else{
			JsNode p = JsonTreeHelper.getNode(parentId, NODE);

			LinkedMultiValueMap<Integer, Integer> occurance = p.getOccurance();
			if(occurance.isEmpty()){
				parentCurrent =1;
			}else{
				if(occurance.containsKey(iter)){
					int cur= occurance.getFirst(iter);
					parentCurrent = cur;
				}
			}
		}
		
		String l = "O"+iter+"P"+parentCurrent;
    	if(loop.isEmpty()){
    		loop.set("O"+iter+"P"+parentCurrent, 1);
    	}else{
			if(loop.containsKey(l)){
					int cur= loop.getFirst(l);
					int newCur = cur+1;
    				loop.set("O"+iter+"P"+parentCurrent, newCur);
			}else{
				loop.set("O"+iter+"P"+parentCurrent, 1);
			}
    	}
		n.setLoop(loop);
    }
    

}
