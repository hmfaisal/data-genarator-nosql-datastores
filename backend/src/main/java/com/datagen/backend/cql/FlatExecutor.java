package com.datagen.backend.cql;

import java.util.ArrayList;
import java.util.List;

import com.datagen.backend.helper.FileWrite;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.notsql.helper.FlatOccuranceHelper;

public class FlatExecutor {
	
	static String fileName = "FinalOutput.cypher";
	
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
		List<ValueCheck> valueTotal= FlatOccuranceHelper.setTotalCalculation(schema,method,totalData);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		long startTime = System.currentTimeMillis();
		for (long i=1; i<=totalData; i++) {
			List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
			StringBuilder cql= getCqlChunk(schema,valueTotal,method,i,currentValue,tempCurrentValue);
			currentValue = tempCurrentValue;
			String sb = cql.toString();
			int size= sb.getBytes().length;
			currentSize=currentSize+size;
			if(currentSize>=volume || i == totalData){
				FileWrite.writer(sb,fileLocation,fileName);
				System.out.println("CQLFLAT_NUMBER"+i);
				break;
			}else{
				FileWrite.writer(sb,fileLocation,fileName);
			}
		}
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("CQLFLAT_TIME"+elapsedTime);
	}
	
	public static void generationByCount(List<Schema> schema,long volume,String fileLocation,List<DgenMethod> method){

		long totalData= volume;
		List<ValueCheck> valueTotal= FlatOccuranceHelper.setTotalCalculation(schema,method,totalData);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		for (long i=1; i<=totalData; i++) {
			List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
			StringBuilder cql= getCqlChunk(schema,valueTotal,method,i,currentValue,tempCurrentValue);
			currentValue = tempCurrentValue;
			String sb = cql.toString();
			FileWrite.writer(sb,fileLocation,fileName);
		}
	}
	
	public static StringBuilder getCqlChunk(List<Schema> schema,List<ValueCheck> valueTotal,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue){
		FlatGenerator cqlGen = new FlatGenerator();
		StringBuilder cql = cqlGen.init(schema,valueTotal,method,current,currentValue,tempCurrentValue);
        return cql;
	}
}
