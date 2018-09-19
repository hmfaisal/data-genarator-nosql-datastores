package com.datagen.backend.xml;

import java.util.ArrayList;
import java.util.List;

import com.datagen.backend.helper.FileWrite;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.Schema;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.notsql.helper.FlatOccuranceHelper;

public class FlatExecutor {
	
static String fileName = "FinalOutput.xml";
	
	public static void generator(List<Schema> schema,long volume,int singleVolume,String fileLocation,List<DgenMethod> method,String technique){
		
		xmlGenInit(fileLocation);
		
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
			StringBuilder xml= getXmlChunk(schema,valueTotal,method,i,currentValue,tempCurrentValue);
			currentValue = tempCurrentValue;
			String sb = xml.toString();
			int size= sb.getBytes().length;
			currentSize=currentSize+size;
			if(currentSize>=volume || i == totalData){
				FileWrite.writer(sb,fileLocation,fileName);
				System.out.println("XMLFLAT_NUMBER"+i);
				break;
			}else{
				FileWrite.writer(sb+",",fileLocation,fileName);
			}
		}
		String xmlObjClose = "</root>";
		FileWrite.writer(xmlObjClose,fileLocation,fileName);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("XMLLAT_TIME"+elapsedTime);
	}
	
	public static void generationByCount(List<Schema> schema,long volume,String fileLocation,List<DgenMethod> method){

		long totalData= volume;
		List<ValueCheck> valueTotal= FlatOccuranceHelper.setTotalCalculation(schema,method,totalData);
		List<CurrentValue> currentValue= new ArrayList<CurrentValue>();
		for (long i=1; i<=totalData; i++) {
			List<CurrentValue> tempCurrentValue= new ArrayList<CurrentValue>();
			StringBuilder xml= getXmlChunk(schema,valueTotal,method,i,currentValue,tempCurrentValue);
			currentValue = tempCurrentValue;
			String sb = xml.toString();
			if(i==volume){
				FileWrite.writer(sb,fileLocation,fileName);
				break;
			}else{
				FileWrite.writer(sb+",",fileLocation,fileName);
			}
		}
		String xmlObjClose = "</root>";
		FileWrite.writer(xmlObjClose,fileLocation,fileName);
	}
	
	public static StringBuilder getXmlChunk(List<Schema> schema,List<ValueCheck> valueTotal,List<DgenMethod> method,long current,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue){
		FlatGenerator xmlGen = new FlatGenerator();
		StringBuilder xml = xmlGen.init(schema,valueTotal,method,current,currentValue,tempCurrentValue);
        return xml;
	}
	
	public static void xmlGenInit(String location){
		FileWrite.clean(location);
		String xmlOpen = "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<root>";
		FileWrite.writer(xmlOpen,location,fileName);
	}

}
