package com.datagen.backend.notsql.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;

public class FieldOccuranceHelper {
	
	//Insert ValueTotal from Executor
	public static LinkedMultiValueMap<Integer, Long> getCalculatedOcccurance(LinkedMultiValueMap<Integer, Integer> fieldOccurance,int actualTotal, long dataTotal){
		int nodeOccur = fieldOccurance.keySet().size();
		//System.out.println("NodeOccur"+nodeOccur);
		long calculateNodeOccur = occuranceCalculate(nodeOccur,actualTotal,dataTotal);
		//System.out.println("calculateNodeOccur"+calculateNodeOccur);
		long calculatedEveryNodeOccur= (long) Math.ceil(calculateNodeOccur/nodeOccur);
		//System.out.println("calculatedEveryNodeOccur"+calculatedEveryNodeOccur);
		LinkedMultiValueMap<Integer, Long> newOccurance= new LinkedMultiValueMap <Integer, Long>();
		for(int occur : fieldOccurance.keySet()){
			newOccurance.add(occur, calculatedEveryNodeOccur);
		}
		return newOccurance;
	}
	
	public static long occuranceCalculate(int fieldOccurance,int actualTotal, long dataTotal){
		return (long) Math.ceil((fieldOccurance*dataTotal)/actualTotal);
	}
	
	public static int actualTotalOccurance(List<Schema> schema){
		int actual = 0;
		for(Schema s:schema){
			if(s.getKey()==0){
				Collection<JsNode> values = s.getValue();
				for(JsNode value : values) {
					LinkedMultiValueMap<Integer, Integer> occurance = value.getOccurance();
					int occur = occurance.keySet().size();
					if(occur>actual){
						actual = occur;
					}
				}
			}
		}
		return actual;	
	}
	//End Insert ValueTotal from Executor
	
	//Insert MaxTotal from Executor
	public static boolean isNotMaxInserted(LinkedMultiValueMap<Integer, Long> occurance,int key,long occur){
		boolean bool = false;
		if(occurance.isEmpty()){
			bool= true;
		}else if(occurance.containsKey(key)){
			long lastOccur = occurance.getFirst(key);
			if(occur>lastOccur){
				bool= true;
			}
		}else if(!occurance.containsKey(key)){
			bool = true;
		}
		return bool;
	}
	
	public static long getMaxForEveryOccur(int key,List<ValueCheck> valueTotal){
		long total = 0;
		for(ValueCheck value: valueTotal){
			LinkedMultiValueMap<Integer, Long> fieldOccurance = value.getOccurance();
			if(fieldOccurance.containsKey(key)){
				long temp = fieldOccurance.getFirst(key);
				if(temp>total){
					total=temp;
				}
			}
		}
		return total;
	}
	//End Insert MaxTotal from Executor
	
	
	//Loop
	public static LinkedMultiValueMap<Integer, Integer> getCalculatedLoop(List<Schema> schema, int id,int parentId,LinkedMultiValueMap<Integer, Integer> fieldOccurance){
		LinkedMultiValueMap<Integer, Integer> newLoop= new LinkedMultiValueMap <Integer, Integer>();
		for(int k : fieldOccurance.keySet()){
			int parentOccur = getParentOccur(schema,parentId,k);
			int tempChildOccur = fieldOccurance.getFirst(k);
			int childOccur = (int) Math.ceil(tempChildOccur/parentOccur);
			newLoop.add(k, childOccur);
		}
		return newLoop;
	}
	
	public static int getParentOccur(List<Schema> schema,int parentId,int block){
		int parentOccur = 1;
		for(Schema s:schema){
			//if(s.getKey()==parentId){
				Collection<JsNode> values = s.getValue();
				for(JsNode value : values) {
					int id = value.getId();
					if(id == parentId){
						LinkedMultiValueMap<Integer, Integer> occurance = value.getOccurance();
						if(occurance.containsKey(block)){
							int tempOccur = occurance.getFirst(block);
							if(tempOccur>=parentOccur){
								parentOccur = tempOccur;
							}
						}
					}
				}
			//}
		}
		return parentOccur;
	}

	public static LinkedMultiValueMap<Integer, Integer> getParentCalculatedLoop(List<Schema> schema, int id,LinkedMultiValueMap<Integer, Integer> fieldOccurance){
		LinkedMultiValueMap<Integer, Integer> newLoop= new LinkedMultiValueMap <Integer, Integer>();
		for(int k : fieldOccurance.keySet()){
			int childOccur = getChildOccur(schema,id,k);
			int tempParentOccur = fieldOccurance.getFirst(k);
			int occur = (int) Math.ceil(childOccur/tempParentOccur);
			newLoop.add(k, occur);
		}
		return newLoop;
	}

	public static int getChildOccur(List<Schema> schema,int id,int block){
		int childOccur = 1;
		for(Schema s:schema){
			if(s.getKey()==id){
				Collection<JsNode> values = s.getValue();
				for(JsNode value : values) {
					int childId = value.getId();
					LinkedMultiValueMap<Integer, Integer> occurance = value.getOccurance();
					if(occurance.containsKey(block)){
						int tempOccur = occurance.getFirst(block);
						if(tempOccur>=childOccur){
							childOccur = tempOccur;
						}
					}
				}
			}
		}
		return childOccur;
	}

	public static int getLoop(List<ValueCheck> valueTotal, int id, int block){
		int occurance = 0;
		for(ValueCheck cv: valueTotal){
			if(cv.getId()==id){
				LinkedMultiValueMap<Integer, Integer> occur= cv.getLoop();
				if(occur.containsKey(block)){
					occurance = occur.getFirst(block);
				}
			}
		}
		return occurance;
	}
	//End Loop
	
	public static int getBlock(LinkedMultiValueMap<Integer, Long> maxTotal,long current){
		int block = 0;
		long tempOccur = 0;
		for(int key : maxTotal.keySet()){
			long occur = maxTotal.getFirst(key);
			tempOccur = tempOccur+occur;
			if(current<=tempOccur){
				block = key;
				break;
			}
		}
		return block;
	}
	
	//unused
	public static int getFirstElement(Collection<JsNode> values){
		return values.iterator().next().getId();
	}
	
	public static int getLastElement(Collection<JsNode> values){
		int last =0;
		for(JsNode value : values) {
			last = value.getId();
		}
		return last;
	}
	
	public static int getNextElement(Collection<JsNode> values,int id){
		
		int next = 0;
		ArrayList<Integer> aList = new ArrayList<Integer>();
		for(JsNode value : values) {
			aList.add(value.getId());
		}

		for(int i = 0 ; i < aList.size();i++)
		{
		    if((aList.get(i)==id) && (i+1<aList.size())){
		    	next = aList.get(i+1);
		    }
		}
		return next;
		
	}
	
	public static int getPreviousElement(Collection<JsNode> values,int id){
		
		int next = 0;
		ArrayList<Integer> aList = new ArrayList<Integer>();
		for(JsNode value : values) {
			aList.add(value.getId());
		}
		int indexOfSelectedId = aList.indexOf(id);
		if(indexOfSelectedId < 0){
		    return next; 
		}
		for(int i = 0; i < indexOfSelectedId ; i++)
		{
		    next = aList.get(i);
		}
		return next;
	}
	//unused

}
