package com.datagen.backend.helper;

import java.util.Collection;

public class ValueRangeCalculator {
	
	public static int[] getIntRange(Collection<Object> values){
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
		int[] result = new int[] {min,max};
		return result;
	}
	
	public static short[] getShortRange(Collection<Object> values){
		short min=(short) values.iterator().next();
		short max = (short) values.iterator().next();
		for(Object value:values){
			short temp_min = (short) value;
			short temp_max = (short) value;
			if(temp_min<min){
				min = temp_min;
			}else if(temp_max>max){
				max= temp_max;
			}
		}
		if (min == max) {
			max = (short) (max+1);
		}
		short[] result = new short[] {min,max};
		return result;
	}
	
	public static double[] getDoubleRange(Collection<Object> values){
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
		double[] result = new double[] {min,max};
		return result;
	}
	
	public static float[] getFloatRange(Collection<Object> values){
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
		float[] result = new float[] {min,max};
		return result;
	}
	
	public static long[] getLongRange(Collection<Object> values){
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
		long[] result = new long[] {min,max};
		return result;
	}
	
}
