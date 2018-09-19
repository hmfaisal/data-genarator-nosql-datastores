package com.datagen.backend.cql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.helper.FileWrite;
import com.datagen.backend.model.Calculation;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.notsql.helper.DistributedOccuranceHelper;
import com.datagen.backend.notsql.helper.FlatOccuranceHelper;

public class Calculator {
	
	static String fileName = "FinalOutput.cypher";
	
	public static Calculation calculation(List<Schema> schema,long volume,String fileLocation,List<DgenMethod> method,String format,String technique){
		
		Calculation calc = new Calculation();
		
		//set time
		long startTime = System.currentTimeMillis();
		FileWrite.clean(fileLocation);
		String sb = null;
		if(format.equals("FLAT")){
			sb = getCqlFlat(schema,method);
		}else if(format.equals("ORIGINAL")){
			sb = getCqlOriginal(schema,method);
		}
		FileWrite.writer(sb,fileLocation,fileName);
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
	
	public static String getCqlOriginal(List<Schema> schema,List<DgenMethod> method){
		List<ValueCheck> valueTotal= DistributedOccuranceHelper.setTotalCalculation(schema,method,1);
		List<ValueCheck> currentTotal= DistributedOccuranceHelper.setCurrentCalculation(schema);
		LinkedMultiValueMap<Integer, Long> maxTotal= DistributedOccuranceHelper.setMaxTotalCalculation(valueTotal);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
		StringBuilder tempCql= DistributedExecutor.getCqlChunk(schema,valueTotal,currentTotal,maxTotal,method,1,currentValue,tempCurrentValue);
		String sb = tempCql.toString();
		return sb;
	}
	
	public static String getCqlFlat(List<Schema> schema,List<DgenMethod> method){
		List<ValueCheck> valueTotal= FlatOccuranceHelper.setTotalCalculation(schema,method,1);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
		StringBuilder tempCql= FlatExecutor.getCqlChunk(schema,valueTotal,method,1,currentValue,tempCurrentValue);
		String sb = tempCql.toString();
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
