package com.datagen.backend.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.datagen.backend.helper.FileWrite;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.sql.helper.FlatOccuranceHelper;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class FlatExecutor {
	
	static String fileName = "FinalOutput.sql";
	
	public static void generator(List<Schema> schema,long volume,int singleVolume,String fileLocation,List<DgenMethod> method,String technique){
		
		FileWrite.clean(fileLocation);
		Multimap<Integer, String> columnName = LinkedListMultimap.create();
		
		//table+column writer
		StringBuilder tsb= columnCreate(schema,columnName);
		String table = tsb.toString();
		FileWrite.writer(table,fileLocation,fileName);
		//end table+colum writer
		
		if(technique.equals("BYCOUNT")){
			generationByCount(schema,volume,fileLocation,method,columnName);
		}else if(technique.equals("BYVOLUME")){
			generationByVolume(schema,volume,fileLocation,method,singleVolume,columnName);
		}
	}
	
	public static void generationByVolume(List<Schema> schema,long volume,String fileLocation,List<DgenMethod> method,int tempSize,Multimap<Integer, String> columnName){
		
		Map<Integer, Collection<String>> column = columnName.asMap();
		long totalData= Math.round(volume / tempSize);
		//value generator
		int rootId = 1;
		Multimap<Integer, Integer> childId = LinkedListMultimap.create();
		//List<CurrentValue> valMap = ValueChecker.getValue(schema);
		List<ValueCheck> valueTotal= FlatOccuranceHelper.setTotalCalculation(schema,totalData);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		
		long currentSize =0;
		long startTime = System.currentTimeMillis();
		for (long i=1; i<=totalData; i++) {
			List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
			FlatGenerator sqlGen = new FlatGenerator();
			Multimap<Integer, Object> childVal = sqlGen.init(schema,rootId,childId,method,currentValue,tempCurrentValue,valueTotal,i);
			currentValue = tempCurrentValue;
			//row writer
			StringBuilder sql= rowCreate(schema,childVal,column);
			String sb = sql.toString();
			int size= sb.getBytes().length;
			currentSize=currentSize+size;
			if(currentSize>=volume || i == totalData){
				FileWrite.writer(sb,fileLocation,fileName);
				System.out.println("SQLFLAT_NUMBER"+i);
				break;
			}else{
				FileWrite.writer(sb,fileLocation,fileName);
			}
			//end row writer
		}
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("SQLFLAT_TIME"+elapsedTime);
		//end value generator
	}
	
	public static void generationByCount(List<Schema> schema,long volume,String fileLocation,List<DgenMethod> method,Multimap<Integer, String> columnName){
		
		Map<Integer, Collection<String>> column = columnName.asMap();
		long totalData= volume;
		//value generator
		int rootId = 1;
		Multimap<Integer, Integer> childId = LinkedListMultimap.create();
		//List<CurrentValue> valMap = ValueChecker.getValue(schema);
		List<ValueCheck> valueTotal= FlatOccuranceHelper.setTotalCalculation(schema,totalData);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		for (long i=1; i<=totalData; i++) {
			List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
			FlatGenerator sqlGen = new FlatGenerator();
			Multimap<Integer, Object> childVal = sqlGen.init(schema,rootId,childId,method,currentValue,tempCurrentValue,valueTotal,i);
			currentValue = tempCurrentValue;
			//row writer
			StringBuilder sql= rowCreate(schema,childVal,column);
			String sb = sql.toString();
			FileWrite.writer(sb,fileLocation,fileName);
			//end row writer
		}
	}
	
	public static StringBuilder columnCreate(List<Schema> schema,Multimap<Integer, String> columnName){
		SqlSyntaxBuilder sqlSyntaxBuilder = new SqlSyntaxBuilder();
		return sqlSyntaxBuilder.tableBuilder(schema,columnName);
	}
	
	public static StringBuilder rowCreate(List<Schema> schema,Multimap<Integer, Object> childVal,Map<Integer, Collection<String>> column){
		SqlSyntaxBuilder sqlSyntaxBuilder = new SqlSyntaxBuilder();
		return sqlSyntaxBuilder.rowBuilder(schema,childVal,column);
	}

}
