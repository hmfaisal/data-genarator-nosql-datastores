package com.datagen.backend.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.datagen.backend.helper.FileWrite;
import com.datagen.backend.model.Calculation;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.sql.helper.FlatOccuranceHelper;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class Calculator {
	
	static String fileName = "FinalOutput.sql";
	
	public static Calculation calculation(List<Schema> schema,long volume,String fileLocation,List<DgenMethod> method,String technique){
		
		Calculation calc = new Calculation();
		
		Multimap<Integer, String> columnName = LinkedListMultimap.create();
		StringBuilder tsb= columnCreate(schema,columnName);
		FileWrite.clean(fileLocation);
		
		long startTime = System.currentTimeMillis();
		String sb = getSqlFlat(schema,method,columnName);
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
	
	public static String getSqlFlat(List<Schema> schema,List<DgenMethod> method,Multimap<Integer, String> columnName){
		
		Map<Integer, Collection<String>> column = columnName.asMap();
		int rootId = 1;
		Multimap<Integer, Integer> childId = LinkedListMultimap.create();
		List<ValueCheck> valueTotal= FlatOccuranceHelper.setTotalCalculation(schema,1);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
		
		FlatGenerator sqlGen = new FlatGenerator();
		Multimap<Integer, Object> childVal = sqlGen.init(schema,rootId,childId,method,currentValue,tempCurrentValue,valueTotal,1);
		StringBuilder sql= rowCreate(schema,childVal,column);
		String sb = sql.toString();
		return sb;
	}
	
	public static StringBuilder columnCreate(List<Schema> schema,Multimap<Integer, String> columnName){
		SqlSyntaxBuilder sqlSyntaxBuilder = new SqlSyntaxBuilder();
		return sqlSyntaxBuilder.tableBuilder(schema,columnName);
	}
	
	public static StringBuilder rowCreate(List<Schema> schema,Multimap<Integer, Object> childVal,Map<Integer, Collection<String>> column){
		SqlSyntaxBuilder sqlSyntaxBuilder = new SqlSyntaxBuilder();
		return sqlSyntaxBuilder.rowBuilder(schema,childVal,column);
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
