package com.datagen.backend.notsql.helper;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.ValueCheck;
import com.datagen.backend.value.generator.MimickDuplicateNumberGeneration;
import com.datagen.backend.value.generator.MimickDuplicateTextGeneration;
import com.datagen.backend.value.generator.MimickUniqueNumberGeneration;
import com.datagen.backend.value.generator.MimickUniqueTextGeneration;
import com.datagen.backend.value.generator.RandomNumberGeneration;
import com.datagen.backend.value.generator.RandomTextGeneration;

public class ValueGenerator {

	public static void flatValueGenerator(StringBuilder sb,long current,LinkedMultiValueMap<String, Long> currentTotal, LinkedMultiValueMap<String, Object> valueMap, String method,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){

		boolean run =true;
		long total=0 ;
		for(String vType : currentTotal.keySet()){
			long cTotal= currentTotal.getFirst(vType);
			total=total+cTotal;
			if(current<=total && run){
				runGenerator(sb,vType,valueMap,method,currentValue,tempCurrentValue,id);
				run = false;
			}
		}
	}

	public static void distributedValueGenerator(StringBuilder sb, long current,LinkedMultiValueMap<String, Object> valueMap, String method,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,List<ValueCheck> valueTotal,List<ValueCheck> currentTotal,int id){

		LinkedMultiValueMap<String, Long> curTotal = FlatCurrentOccuranceHelper.currentMap(id, valueTotal);
		boolean run =true;
		long total=0 ;
		for(String vType : curTotal.keySet()){
			long cTotal= curTotal.getFirst(vType);
			total=total+cTotal;
			if(current<=total && run){
				runGenerator(sb,vType,valueMap,method,currentValue,tempCurrentValue,id);
				run = false;
			}
		}

		/*
		String type = DistributedCurrentOccuranceHelper.currentMap(id, valueTotal,currentTotal,current);

		if(type!=null){
			DistributedCurrentOccuranceHelper.setCurrentValue(currentTotal,id,type);
			runGenerator(sb,type,valueMap,method,currentValue,tempCurrentValue,id);
		}
		*/

	}

	public static void runGenerator(StringBuilder sb, String type,LinkedMultiValueMap<String, Object> valueMap, String method,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){

		if(type.equals("BOOLEAN")){
			boolean val = boolBuilder();
			sb.append(String.valueOf(val));
		}
		if(type.equals("NULL")){
			sb.append("null");
		}
		if(type.equals("WORDS")){
			for(String val : valueMap.keySet()){
				if(val==type){
					Collection<Object> values = valueMap.get(val);
					String value = wordsBuilder(method,values,currentValue,tempCurrentValue,id);
					sb.append('"'+value+'"');
				}
			}
		}
		if(type.equals("DATE")){
			for(String val : valueMap.keySet()){
				if(val==type){
					Collection<Object> values = valueMap.get(val);
					String value = dateBuilder(method,values,currentValue,tempCurrentValue,id);
					sb.append('"'+value+'"');
				}
			}
		}
		if(type.equals("ALPHANUMERIC")){
			for(String val : valueMap.keySet()){
				if(val==type){
					Collection<Object> values = valueMap.get(val);
					String value = alphaNumericBuilder(method,values,currentValue,tempCurrentValue,id);
					sb.append('"'+value+'"');
				}
			}
		}
		if(type.equals("URL")){
			for(String val : valueMap.keySet()){
				if(val==type){
					Collection<Object> values = valueMap.get(val);
					String value = urlBuilder(method,values,currentValue,tempCurrentValue,id);
					sb.append('"'+value+'"');
				}
			}
		}
		if(type.equals("EMAIL")){
			for(String val : valueMap.keySet()){
				if(val==type){
					Collection<Object> values = valueMap.get(val);
					String value = emailBuilder(method,values,currentValue,tempCurrentValue,id);
					sb.append('"'+value+'"');
				}
			}
		}
		if(type.equals("INTEGER")){
			for(String val : valueMap.keySet()){
				if(val==type){
					Collection<Object> values = valueMap.get(val);
					int value = intBuilder(method,values,currentValue,tempCurrentValue,id);
					sb.append(String.valueOf(value));
				}
			}
		}
		if(type.equals("DOUBLE")){
			for(String val : valueMap.keySet()){
				if(val==type){
					Collection<Object> values = valueMap.get(val);
					double value = doubleBuilder(method,values,currentValue,tempCurrentValue,id);
					sb.append(String.valueOf(value));
				}
			}
		}
		if(type.equals("FLOAT")){
			for(String val : valueMap.keySet()){
				if(val==type){
					Collection<Object> values = valueMap.get(val);
					float value = floatBuilder(method,values,currentValue,tempCurrentValue,id);
					sb.append(String.valueOf(value));
				}
			}
		}
		if(type.equals("LONG")){
			for(String val : valueMap.keySet()){
				if(val==type){
					Collection<Object> values = valueMap.get(val);
					long value = longBuilder(method,values,currentValue,tempCurrentValue,id);
					sb.append(String.valueOf(value));
				}
			}
		}
		if(type.equals("SHORT")){
			for(String val : valueMap.keySet()){
				if(val==type){
					Collection<Object> values = valueMap.get(val);
					short value = shortBuilder(method,values,currentValue,tempCurrentValue,id);
					sb.append(String.valueOf(value));
				}
			}
		}

	}

	public static boolean boolBuilder(){
		Random random = new Random();
		return random.nextBoolean();
	}

	public static String alphaNumericBuilder(String method,Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id) {
		String value = "";
		if(method.equals("MIMICKUNIQUE")){
			MimickUniqueTextGeneration gen = new MimickUniqueTextGeneration();
			value = gen.alphaNumGenerator(values,currentValue,tempCurrentValue,id);
		}else if(method.equals("MIMICKDUPLICATE")){
			MimickDuplicateTextGeneration gen = new MimickDuplicateTextGeneration();
			value = gen.alphaNumGenerator(values);
		}else if(method.equals("RANDOM")){
			RandomTextGeneration gen = new RandomTextGeneration();
			value = gen.alphaNumGenerator(values);
		}
		return value;
	}

	public static String urlBuilder(String method,Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id) {
		String value = "";
		if(method.equals("MIMICKUNIQUE")){
			MimickUniqueTextGeneration gen = new MimickUniqueTextGeneration();
			value = gen.urlGenerator(values,currentValue,tempCurrentValue,id);
		}else if(method.equals("MIMICKDUPLICATE")){
			MimickDuplicateTextGeneration gen = new MimickDuplicateTextGeneration();
			value = gen.urlGenerator(values);
		}else if(method.equals("RANDOM")){
			RandomTextGeneration gen = new RandomTextGeneration();
			value = gen.urlGenerator(values);
		}
		return value;
	}

	public static String emailBuilder(String method,Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id) {
		String value = "";
		if(method.equals("MIMICKUNIQUE")){
			MimickUniqueTextGeneration gen = new MimickUniqueTextGeneration();
			value = gen.emailGenerator(values,currentValue,tempCurrentValue,id);
		}else if(method.equals("MIMICKDUPLICATE")){
			MimickDuplicateTextGeneration gen = new MimickDuplicateTextGeneration();
			value = gen.emailGenerator(values);
		}else if(method.equals("RANDOM")){
			RandomTextGeneration gen = new RandomTextGeneration();
			value = gen.emailGenerator(values);
		}
		return value;
	}

	public static String dateBuilder(String method,Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id) {
		String value = "";
		if(method.equals("MIMICKUNIQUE")){
			MimickUniqueTextGeneration gen = new MimickUniqueTextGeneration();
			value = gen.dateGenerator(values,currentValue,tempCurrentValue,id);
		}else if(method.equals("MIMICKDUPLICATE")){
			MimickDuplicateTextGeneration gen = new MimickDuplicateTextGeneration();
			value = gen.dateGenerator(values);
		}else if(method.equals("RANDOM")){
			RandomTextGeneration gen = new RandomTextGeneration();
			value = gen.dateGenerator(values);
		}
		return value;
	}

	public static String wordsBuilder(String method,Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id) {
		String value = "";
		if(method.equals("MIMICKUNIQUE")){
			MimickUniqueTextGeneration gen = new MimickUniqueTextGeneration();
			value = gen.wordsGenerator(values,currentValue,tempCurrentValue,id);
		}else if(method.equals("MIMICKDUPLICATE")){
			MimickDuplicateTextGeneration gen = new MimickDuplicateTextGeneration();
			value = gen.wordsGenerator(values);
		}else if(method.equals("RANDOM")){
			RandomTextGeneration gen = new RandomTextGeneration();
			value = gen.wordsGenerator(values);
		}
		return value;
	}

	public static int intBuilder(String method,Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){
		int value = 0;
		if(method.equals("MIMICKUNIQUE")){
			MimickUniqueNumberGeneration gen = new MimickUniqueNumberGeneration();
			value = gen.intGenerator(values,currentValue,tempCurrentValue,id);
		}else if(method.equals("MIMICKDUPLICATE")){
			MimickDuplicateNumberGeneration gen = new MimickDuplicateNumberGeneration();
			value = gen.intGenerator(values);
		}else if(method.equals("RANDOM")){
			RandomNumberGeneration gen = new RandomNumberGeneration();
			value = gen.intGenerator(values);
		}
		return value;
	}

	public static float floatBuilder(String method,Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){
		float value = 0;
		if(method.equals("MIMICKUNIQUE")){
			MimickUniqueNumberGeneration gen = new MimickUniqueNumberGeneration();
			value = gen.floatGenerator(values,currentValue,tempCurrentValue,id);
		}else if(method.equals("MIMICKDUPLICATE")){
			MimickDuplicateNumberGeneration gen = new MimickDuplicateNumberGeneration();
			value = gen.floatGenerator(values);
		}else if(method.equals("RANDOM")){
			RandomNumberGeneration gen = new RandomNumberGeneration();
			value = gen.floatGenerator(values);
		}
		return value;
	}

	public static double doubleBuilder(String method,Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){
		double value = 0;
		if(method.equals("MIMICKUNIQUE")){
			MimickUniqueNumberGeneration gen = new MimickUniqueNumberGeneration();
			value = gen.doubleGenerator(values,currentValue,tempCurrentValue,id);
		}else if(method.equals("MIMICKDUPLICATE")){
			MimickDuplicateNumberGeneration gen = new MimickDuplicateNumberGeneration();
			value = gen.doubleGenerator(values);
		}else if(method.equals("RANDOM")){
			RandomNumberGeneration gen = new RandomNumberGeneration();
			value = gen.doubleGenerator(values);
		}
		return value;
	}

	public static long longBuilder(String method,Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){
		long value = 0;
		if(method.equals("MIMICKUNIQUE")){
			MimickUniqueNumberGeneration gen = new MimickUniqueNumberGeneration();
			value = gen.longGenerator(values,currentValue,tempCurrentValue,id);
		}else if(method.equals("MIMICKDUPLICATE")){
			MimickDuplicateNumberGeneration gen = new MimickDuplicateNumberGeneration();
			value = gen.longGenerator(values);
		}else if(method.equals("RANDOM")){
			RandomNumberGeneration gen = new RandomNumberGeneration();
			value = gen.longGenerator(values);
		}
		return value;
	}

	public static short shortBuilder(String method,Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){
		short value = 0;
		if(method.equals("MIMICKUNIQUE")){
			MimickUniqueNumberGeneration gen = new MimickUniqueNumberGeneration();
			value = gen.shortGenerator(values,currentValue,tempCurrentValue,id);
		}else if(method.equals("MIMICKDUPLICATE")){
			MimickDuplicateNumberGeneration gen = new MimickDuplicateNumberGeneration();
			value = gen.shortGenerator(values);
		}else if(method.equals("RANDOM")){
			RandomNumberGeneration gen = new RandomNumberGeneration();
			value = gen.shortGenerator(values);
		}
		return value;
	}
}
