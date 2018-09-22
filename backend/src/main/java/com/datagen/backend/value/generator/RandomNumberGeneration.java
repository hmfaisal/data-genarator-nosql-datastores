package com.datagen.backend.value.generator;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGeneration {
	
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
		if(min<=0){
			min = 1;
		}
		if (min == max) {
			max = max+1;
		}
		int size = Integer.toString(max).length();
		int range = (int) Math.pow(10, size - 1);
		if(range<=0){
			range = 1;
		}
	    return ThreadLocalRandom.current().nextInt();
	}
	
	public float floatGenerator(Collection<Object> values){
		float value = ThreadLocalRandom.current().nextFloat();
		return value;
	}
	
	public double doubleGenerator(Collection<Object> values){
		double value = ThreadLocalRandom.current().nextDouble();
		return value;
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
		int size = Long.toString(max).length();
		long range = (long) Math.pow(10, size - 1);
		if(min<=0){
			min = 1;
		}
		if(range<0){
			range = 1;
		}
	    return ThreadLocalRandom.current().nextLong();
	}
	
	public short shortGenerator(Collection<Object> values){
		short value = (short)ThreadLocalRandom.current().nextInt(1 << 16);
		return value;
	}
}
