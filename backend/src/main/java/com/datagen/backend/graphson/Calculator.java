package com.datagen.backend.graphson;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.helper.FileWrite;
import com.datagen.backend.model.Calculation;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.GraphsonId;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.notsql.helper.DistributedOccuranceHelper;
import com.datagen.backend.notsql.helper.FlatOccuranceHelper;

public class Calculator {
	
	static String fileName = "FinalOutput.json";
	
	public static Calculation calculation(List<Schema> schema,long volume,String fileLocation,List<DgenMethod> method,String format,String technique){
		
		Calculation calc = new Calculation();
		//set time
		long startTime = System.currentTimeMillis();
		
		String sb = null;
		if(format.equals("FLAT")){
			sb = getJsonFlat(schema,method);
		}else if(format.equals("ORIGINAL")){
			sb = getJsonOriginal(schema,method);
		}
		
		FileWrite.writer(sb,fileLocation,fileName);
		String jsb = "]";
		FileWrite.writer(jsb,fileLocation,fileName);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		calc.setTime(elapsedTime);
		
		//set single object size
		int tempSize= sb.getBytes().length;
		calc.setSingleVolume(tempSize);
		
		//set total volume
		if(technique.equals("BYCOUNT")){
			long vol= volumeByCount(method,tempSize,volume);
			calc.setVolume(vol);
		}else if(technique.equals("BYVOLUME")){
			long vol= volumeByVolume(method,tempSize,volume);
			calc.setVolume(vol);
		}
		return calc;
	}
	
	public static long volumeByCount(List<DgenMethod> method,int tempSize,long volume){
		long tempTotal = getVolume(method);
		long vol =0;
		if(tempTotal>0 && tempTotal<=volume){
			vol = tempTotal;
		}else{
			vol = volume;
		}
		return vol;
	}
	public static long volumeByVolume(List<DgenMethod> method,int tempSize,long volume){
		long tempTotal = getVolume(method);
		long vol =0;
		long finalTempTotal = tempTotal*tempSize;
		if(finalTempTotal>0 && finalTempTotal<=volume){
			vol = finalTempTotal;
		}else{
			vol = volume;
		}
		return vol;
	}
	
	public static String getJsonOriginal(List<Schema> schema,List<DgenMethod> method){
		List<ValueCheck> valueTotal= DistributedOccuranceHelper.setTotalCalculation(schema,method,1);
		List<ValueCheck> currentTotal= DistributedOccuranceHelper.setCurrentCalculation(schema);
		LinkedMultiValueMap<Integer, Long> maxTotal= DistributedOccuranceHelper.setMaxTotalCalculation(valueTotal);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
		GraphsonId graphsonId= Helper.setGraphsonId(maxTotal,schema,valueTotal);
		StringBuilder tempJson= DistributedExecutor.getJsonChunk(schema,valueTotal,currentTotal,maxTotal,method,1,currentValue,tempCurrentValue,graphsonId);
		String sb = tempJson.toString();
		return sb;
	}
	
	public static String getJsonFlat(List<Schema> schema,List<DgenMethod> method){
		List<ValueCheck> valueTotal= FlatOccuranceHelper.setTotalCalculation(schema,method,1);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
		GraphsonId graphsonId= Helper.setFlatGraphsonId(schema,1);
		StringBuilder tempJson= FlatExecutor.getJsonChunk(schema,valueTotal,method,1,currentValue,tempCurrentValue,graphsonId);
		String sb = tempJson.toString();
		return sb;
	}
	
	public static long getVolume(List<DgenMethod> method){
		long tempTotal = 0;
		for (DgenMethod m:method){
			if(m.getMethod().equals("MIMICKUNIQUE")){
				long t = m.getTotal();
				if(tempTotal==0 ){
					tempTotal = t;
				}
				else if(t<tempTotal){
					tempTotal = t;
				}
			}
		}
		return tempTotal;
	}

}
