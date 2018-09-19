package com.datagen.backend.notsql.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;

public class DistributedCurrentOccuranceHelper {
	
	public static String currentMap(int id,List<ValueCheck> valueTotal, List<ValueCheck> currentValue, long current){
		
		boolean run =true;
		LinkedMultiValueMap<String, Long> vTotalMap = getTotalMap(valueTotal,id);
		LinkedMultiValueMap<String, Long> vCurrentMap = getCurrentMap(currentValue,id);
		
		for(String vType : vTotalMap.keySet()){
			long vtTotal= vTotalMap.getFirst(vType);
			long cTotal = currentTotal(vCurrentMap,vType);
			if(cTotal<vtTotal && run){
				run = false;
				return vType;
			}	
		}
		
		return null;
	}
	
	public static LinkedMultiValueMap<String, Long> getTotalMap(List<ValueCheck> valueTotal,int id){
		for(ValueCheck value: valueTotal){
			if(value.getId()==id){
				return value.getValueMap();
			}
		}
		return null;
	}
	
	public static LinkedMultiValueMap<String, Long> getCurrentMap(List<ValueCheck> currentValue,int id){
		for(ValueCheck value: currentValue){
			if(value.getId()==id){
				return value.getValueMap();
			}
		}
		return null;
	}
	
	public static long currentTotal(LinkedMultiValueMap<String, Long> vCurrentMap, String vType){
		
		for(String cType : vCurrentMap.keySet()){
			if(cType.equals(vType)){
				return vCurrentMap.getFirst(cType);
			}
		}
		return 0;
	}
	
	
	public static void setCurrentValue(List<ValueCheck> currentTotal, int id, String type){
		for(ValueCheck value: currentTotal){
			if(id==value.getId()){
				LinkedMultiValueMap<String, Long> vMap= value.getValueMap();
				for(String vType : vMap.keySet()){
					if(vType.equals(type)){
						long total= vMap.getFirst(vType);
						LinkedMultiValueMap<String, Long> newVMap= new LinkedMultiValueMap <String, Long>();
						newVMap.add(vType, total+1);
						value.setValueMap(newVMap);
					}
				}
				
			}
		}
	}
	
	public static void setCurrentOccurance(List<ValueCheck> currentTotal, int id,int block){
		for(ValueCheck value: currentTotal){
			if(id==value.getId()){
				LinkedMultiValueMap<Integer, Long> occur= value.getOccurance();
				if(occur.containsKey(block)){
					long occurance = occur.getFirst(block);
					LinkedMultiValueMap<Integer, Long> newOccur= new LinkedMultiValueMap <Integer, Long>();
					newOccur.add(block,occurance+1);
					value.setOccurance(newOccur);
				}
			}
		}
	}
	
	public static void setCurrentLoop(List<ValueCheck> currentTotal, int id,int block){
		for(ValueCheck value: currentTotal){
			if(id==value.getId()){
				LinkedMultiValueMap<Integer, Integer> occur= value.getLoop();
				if(occur.containsKey(block)){
					int occurance = occur.getFirst(block);
					LinkedMultiValueMap<Integer, Integer> newLoop= new LinkedMultiValueMap <Integer, Integer>();
					newLoop.add(block,occurance+1);
					value.setLoop(newLoop);
				}
			}
		}
	}
	
	public static int getCurrentLoop(List<ValueCheck> currentTotal, int id,int block){
		int loop =0;
		for(ValueCheck value: currentTotal){
			if(id==value.getId()){
				LinkedMultiValueMap<Integer, Integer> occur= value.getLoop();
				if(occur.containsKey(block)){
					loop = occur.getFirst(block);
				}
			}
		}
		return loop;
	}
	
	public static void clearCurrentLoop(List<ValueCheck> currentValue){
		for(ValueCheck cv: currentValue){
			LinkedMultiValueMap<Integer, Integer> loop= cv.getLoop();
			LinkedMultiValueMap<Integer, Integer> newLoop= new LinkedMultiValueMap <Integer, Integer>();
			for(int l : loop.keySet()){
				newLoop.add(l, 0);
				cv.setLoop(newLoop);
			}
		}
	}
	
	public static boolean isInBlock(List<ValueCheck> valueTotal,int id,int block){
		
		boolean bool = false;
		for(ValueCheck value: valueTotal){
			if(id==value.getId()){
				LinkedMultiValueMap<Integer, Long> occur= value.getOccurance();
				if(occur.containsKey(block)){
					bool= true;
				}
			}
		}
		
		return bool;
	}
	
	public static boolean isInLoop(List<ValueCheck> valueTotal,int id,int block,int current){
		boolean bool = false;
		for(ValueCheck value: valueTotal){
			if(id==value.getId()){
				LinkedMultiValueMap<Integer, Integer> loop= value.getLoop();
				if(loop.containsKey(block)){
					int l = loop.getFirst(block);
					if(current<=l){
						bool= true;
					}
				}
			}
		}
		
		return bool;
	}
	
	public static boolean nextInBlock(Collection<JsNode> values,int id,List<ValueCheck> valueTotal,int block){
		boolean bool = false;
		int nextId = id;
		ArrayList<Integer> aList = new ArrayList<Integer>();
		for(JsNode value : values) {
			aList.add(value.getId());
		}
		
		for(int i = 0 ; i < aList.size();i++)
		{
		    if((aList.get(i)==nextId) && (i+1<aList.size())){
		    	nextId = aList.get(i+1);
		    	bool=DistributedCurrentOccuranceHelper.isInBlock(valueTotal,nextId,block);
		    	if(bool==true){
		    		break;
		    	}
		    }
		}
		return bool;
	}
	
	public static boolean nextButParentInBlock(Collection<JsNode> values,int id,List<ValueCheck> valueTotal,LinkedHashSet<Integer> parent,int block){
		boolean bool = false;
		int nextId = id;
		ArrayList<Integer> aList = new ArrayList<Integer>();
		for(JsNode value : values) {
			int nId = value.getId();
			if(!parent.contains(nId)){
				aList.add(nId);
			}
		}
		
		for(int i = 0 ; i < aList.size();i++)
		{
		    if((aList.get(i)==nextId) && (i+1<aList.size())){
		    	nextId = aList.get(i+1);
		    	bool=DistributedCurrentOccuranceHelper.isInBlock(valueTotal,nextId,block);
		    	if(bool==true){
		    		break;
		    	}
		    }
		}
		return bool;
	}
	
	public static boolean nextButParent(Collection<JsNode> values,int id,List<ValueCheck> valueTotal,LinkedHashSet<Integer> parent){
		boolean bool = false;
		int nextId = id;
		ArrayList<Integer> aList = new ArrayList<Integer>();
		for(JsNode value : values) {
			int nId = value.getId();
			if(!parent.contains(nId)){
				aList.add(nId);
			}
		}
		
		for(int i = 0 ; i < aList.size();i++)
		{
		    if((aList.get(i)==nextId) && (i+1<aList.size())){
		    	int prevId= nextId;
		    	nextId = aList.get(i+1);
		    	if(prevId!=nextId){
		    		bool = true;
		    		break;
		    	}
		    }
		}
		return bool;
	}
	
	public static boolean nextParentInBlock(int id,List<ValueCheck> valueTotal,LinkedHashSet<Integer> parent,int block){
		boolean bool = false;
		int nextId = id;
		ArrayList<Integer> aList = new ArrayList<Integer>();
		Iterator<Integer> itr = parent.iterator();
		while(itr.hasNext()){
            aList.add(itr.next());
        }
		
		for(int i = 0 ; i < aList.size();i++){
		    if((aList.get(i)==nextId) && (i+1<aList.size())){
		    	nextId = aList.get(i+1);
		    	bool=DistributedCurrentOccuranceHelper.isInBlock(valueTotal,nextId,block);
		    	if(bool==true){
		    		break;
		    	}
		    }
		}
		return bool;
	}
	
	public static boolean rootNextInBlock(Collection<JsNode> values,List<ValueCheck> valueTotal,LinkedHashSet<Integer> parent,int block){
		boolean bool = false;
		for(JsNode value : values) {
			int id = value.getId();
			if(!parent.contains(id)){
				bool = isInBlock(valueTotal,id,block);
				if(bool==true){
					break;
		    	}
			}
		}
		return bool;
	}
	
	public static JsNode getNodeByKey(List<Schema> schema,int key){
		JsNode node = null;
		for(Schema s:schema){
			Collection<JsNode> values = s.getValue();
			for(JsNode value : values) {
				if(value.getId()==key){
					node = value;
				}
			}
		}
		return node;
	}

}
