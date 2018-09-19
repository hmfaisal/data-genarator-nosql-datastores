package com.datagen.backend.graphson;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.helper.FileWrite;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.GraphsonId;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.notsql.helper.DistributedOccuranceHelper;

public class DistributedExecutor {
	
	static String fileName = "FinalOutput.json";
	
public static void generator(List<Schema> schema,long volume,int singleVolume,String fileLocation,List<DgenMethod> method,String technique){
		
		FileWrite.clean(fileLocation);
		
		if(technique.equals("BYCOUNT")){
			generationByCount(schema,volume,fileLocation,method);
		}else if(technique.equals("BYVOLUME")){
			generationByVolume(schema,volume,fileLocation,method,singleVolume);
		}
	}
	
	public static void generationByVolume(List<Schema> schema,long volume,String fileLocation,List<DgenMethod> method,int tempSize){

		long totalData= Math.round(volume / tempSize);
		long currentSize =0;
		List<ValueCheck> valueTotal= DistributedOccuranceHelper.setTotalCalculation(schema,method,totalData);
		List<ValueCheck> currentTotal= DistributedOccuranceHelper.setCurrentCalculation(schema);
		LinkedMultiValueMap<Integer, Long> maxTotal= DistributedOccuranceHelper.setMaxTotalCalculation(valueTotal);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		GraphsonId graphsonId= Helper.setGraphsonId(maxTotal,schema,valueTotal);
		long startTime = System.currentTimeMillis();
		for (long i=1; i<=totalData; i++) {
			List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
			StringBuilder json= getJsonChunk(schema,valueTotal,currentTotal,maxTotal,method,i,currentValue,tempCurrentValue,graphsonId);
			currentValue = tempCurrentValue;
			String sb = json.toString();
			int size= sb.getBytes().length;
			currentSize=currentSize+size;
			if(currentSize>=volume || i == totalData){
				FileWrite.writer(sb,fileLocation,fileName);
				System.out.println("GRAPHSONDISTRIBUTED_NUMBER"+i);
				break;
			}else{
				FileWrite.writer(sb,fileLocation,fileName);
			}
		}
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("GRAPHSONDISTRIBUTED_TIME"+elapsedTime);
	}
	
	public static void generationByCount(List<Schema> schema,long volume,String fileLocation,List<DgenMethod> method){

		long totalData= volume;
		List<ValueCheck> valueTotal= DistributedOccuranceHelper.setTotalCalculation(schema,method,totalData);
		List<ValueCheck> currentTotal= DistributedOccuranceHelper.setCurrentCalculation(schema);
		LinkedMultiValueMap<Integer, Long> maxTotal= DistributedOccuranceHelper.setMaxTotalCalculation(valueTotal);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		GraphsonId graphsonId= Helper.setGraphsonId(maxTotal,schema,valueTotal);
		for (long i=1; i<=totalData; i++) {
			List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
			StringBuilder json= getJsonChunk(schema,valueTotal,currentTotal,maxTotal,method,i,currentValue,tempCurrentValue,graphsonId);
			currentValue = tempCurrentValue;
			String sb = json.toString();
			FileWrite.writer(sb,fileLocation,fileName);
		}
	}
	
	public static StringBuilder getJsonChunk(List<Schema> schema,List<ValueCheck> valueTotal,List<ValueCheck> currentTotal,LinkedMultiValueMap<Integer, Long> maxTotal,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,GraphsonId graphsonId){
		DistributedGenerator jsonGen = new DistributedGenerator();
		StringBuilder json = jsonGen.init(schema,method,current,valueTotal,currentTotal,maxTotal,currentValue,tempCurrentValue,graphsonId);
        return json;
	}
			

}
