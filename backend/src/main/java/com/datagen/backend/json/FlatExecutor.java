package com.datagen.backend.json;

import java.util.ArrayList;
import java.util.List;

import com.datagen.backend.helper.FileWrite;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.notsql.helper.FlatOccuranceHelper;

public class FlatExecutor {
	
	static String fileName = "FinalOutput.json";
	
	public static void generator(List<Schema> schema,long volume,int singleVolume,String fileLocation,List<DgenMethod> method,String technique){
		
		jsonGenInit(fileLocation);
		if(technique.equals("BYCOUNT")){
			generationByCount(schema,volume,fileLocation,method);
		}else if(technique.equals("BYVOLUME")){
			generationByVolume(schema,volume,fileLocation,method,singleVolume);
		}
		
		String jsb = "]";
		FileWrite.writer(jsb,fileLocation,fileName);
	}
	
	public static void generationByVolume(List<Schema> schema,long volume,String fileLocation,List<DgenMethod> method,int tempSize){

		long totalData= Math.round(volume / tempSize);
		long currentSize =0;
		List<ValueCheck> valueTotal= FlatOccuranceHelper.setTotalCalculation(schema,method,totalData);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		long startTime = System.currentTimeMillis();
		for (long i=1; i<=totalData; i++) {
			List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
			StringBuilder json= getJsonChunk(schema,valueTotal,method,i,currentValue,tempCurrentValue);
			currentValue = tempCurrentValue;
			String sb = json.toString();
			int size= sb.getBytes().length;
			currentSize=currentSize+size;
			if(currentSize>=volume || i == totalData){
				FileWrite.writer(sb,fileLocation,fileName);
				System.out.println("JSONFLAT_NUMBER"+i);
				break;
			}else{
				FileWrite.writer(sb+",",fileLocation,fileName);
			}
		}
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("JSONFLAT_TIME"+elapsedTime);
	}
	
	public static void generationByCount(List<Schema> schema,long volume,String fileLocation,List<DgenMethod> method){

		long totalData= volume;
		List<ValueCheck> valueTotal= FlatOccuranceHelper.setTotalCalculation(schema,method,totalData);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		for (long i=1; i<=totalData; i++) {
			List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
			StringBuilder json= getJsonChunk(schema,valueTotal,method,i,currentValue,tempCurrentValue);
			currentValue = tempCurrentValue;
			String sb = json.toString();
			if(i==volume){
				FileWrite.writer(sb,fileLocation,fileName);
				break;
			}else{
				FileWrite.writer(sb+",",fileLocation,fileName);
			}
		}
	}
	
	public static StringBuilder getJsonChunk(List<Schema> schema,List<ValueCheck> valueTotal,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue){
		FlatGenerator jsonGen = new FlatGenerator();
		StringBuilder json = jsonGen.init(schema,valueTotal,method,current,currentValue,tempCurrentValue);
        return json;
	}
	
	public static void jsonGenInit(String location){
		FileWrite.clean(location);
		String jsb = "[";
		FileWrite.writer(jsb,location,fileName);
	}

}
