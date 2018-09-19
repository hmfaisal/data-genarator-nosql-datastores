package com.datagen.backend.graphson;

import java.util.ArrayList;
import java.util.List;

import com.datagen.backend.helper.FileWrite;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.GraphsonId;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.notsql.helper.FlatOccuranceHelper;

public class FlatExecutor {
	
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
		List<ValueCheck> valueTotal= FlatOccuranceHelper.setTotalCalculation(schema,method,totalData);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		GraphsonId graphsonId= Helper.setFlatGraphsonId(schema,totalData);
		long currentSize =0;
		long startTime = System.currentTimeMillis();
		for (long i=1; i<=totalData; i++) {
			List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
			StringBuilder json= getJsonChunk(schema,valueTotal,method,i,currentValue,tempCurrentValue,graphsonId);
			currentValue = tempCurrentValue;
			String sb = json.toString();
			int size= sb.getBytes().length;
			currentSize=currentSize+size;
			if(currentSize>=volume || i == totalData){
				FileWrite.writer(sb,fileLocation,fileName);
				System.out.println("GRAPHSONFLAT_NUMBER"+i);
				break;
			}else{
				FileWrite.writer(sb,fileLocation,fileName);
			}
		}
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("GRAPHSONFLAT_TIME"+elapsedTime);
	}
	
	public static void generationByCount(List<Schema> schema,long volume,String fileLocation,List<DgenMethod> method){

		long totalData= volume;
		List<ValueCheck> valueTotal= FlatOccuranceHelper.setTotalCalculation(schema,method,totalData);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		GraphsonId graphsonId= Helper.setFlatGraphsonId(schema,totalData);
		for (long i=1; i<=totalData; i++) {
			List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
			StringBuilder json= getJsonChunk(schema,valueTotal,method,i,currentValue,tempCurrentValue,graphsonId);
			currentValue = tempCurrentValue;
			String sb = json.toString();
			FileWrite.writer(sb,fileLocation,fileName);
		}
	}
	
	public static StringBuilder getJsonChunk(List<Schema> schema,List<ValueCheck> valueTotal,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,GraphsonId graphsonId){
		FlatGenerator jsonGen = new FlatGenerator();
		StringBuilder json = jsonGen.init(schema,valueTotal,method,current,currentValue,tempCurrentValue,graphsonId);
        return json;
	}

}
