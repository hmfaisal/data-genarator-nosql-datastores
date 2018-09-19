package com.datagen.backend.value.generator;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class MimickDuplicateNumberGeneration {
	
	public int intGenerator(Collection<Object> values){
		int min=Integer.valueOf(values.iterator().next().toString());
		int max = Integer.valueOf(values.iterator().next().toString());
		for(Object value:values){
			int temp_min = Integer.valueOf(value.toString());
			int temp_max = Integer.valueOf(value.toString());
			if(temp_min<min){
				min = temp_min;
			}else if(temp_max>max){
				max= temp_max;
			}
		}
		if (min == max) {
			max = max+1;
		}
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
	public float floatGenerator(Collection<Object> values){
		float min=Float.valueOf(values.iterator().next().toString());
		float max = Float.valueOf(values.iterator().next().toString());
		for(Object value:values){
			float temp_min = Float.valueOf(value.toString());
			float temp_max = Float.valueOf(value.toString());
			if(temp_min<min){
				min = temp_min;
			}else if(temp_max>max){
				max= temp_max;
			}
		}
		if (min == max) {
			max = max+1;
		}
		float randomNum = ThreadLocalRandom.current().nextFloat() * (max - min) + min;
		return randomNum;
	}
	
	public double doubleGenerator(Collection<Object> values){
		double min=Double.valueOf(values.iterator().next().toString());
		double max = Double.valueOf(values.iterator().next().toString());
		for(Object value:values){
			double temp_min = Double.valueOf(value.toString());
			double temp_max = Double.valueOf(value.toString());
			if(temp_min<min){
				min = temp_min;
			}else if(temp_max>max){
				max= temp_max;
			}
		}
		if (min == max) {
			max = max+1;
		}
		double randomNum = ThreadLocalRandom.current().nextDouble(min, max+ 1);
		return randomNum;
	}
	
	public long longGenerator(Collection<Object> values){
		long min=Long.valueOf(values.iterator().next().toString());
		long max = Long.valueOf(values.iterator().next().toString());
		for(Object value:values){
			long temp_min = Long.valueOf(value.toString());
			long temp_max = Long.valueOf(value.toString());
			if(temp_min<min){
				min = temp_min;
			}else if(temp_max>max){
				max= temp_max;
			}
		}
		if (min == max) {
			max = max+1;
		}
		long randomNum = ThreadLocalRandom.current().nextLong(min, max + 1);
		return randomNum;
	}
	
	public short shortGenerator(Collection<Object> values){
		short randomNum = (short)ThreadLocalRandom.current().nextInt(1 << 16);
		return randomNum;
	}
	
	
}
