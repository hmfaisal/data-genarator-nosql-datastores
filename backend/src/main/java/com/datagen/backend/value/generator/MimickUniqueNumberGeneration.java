package com.datagen.backend.value.generator;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.datagen.backend.helper.ValueRangeCalculator;
import com.datagen.backend.helper.CurrentValueHelper;
import com.datagen.backend.model.CurrentValue;

public class MimickUniqueNumberGeneration {
	
	public int intGenerator(Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){
		
		int[] range = ValueRangeCalculator.getIntRange(values);
		int min= range[0];
		int max= range[1];
		String type ="INTEGER";
		
		int randomNum = 0;	
		CurrentValue cValue = new CurrentValue();
		if(currentValue.isEmpty()){
			randomNum = min;
		}else{
			if(CurrentValueHelper.hasElement(currentValue,id,type)){
				int lastValue = CurrentValueHelper.getIntValue(currentValue, id);
				if(lastValue+1<=max){
					randomNum = lastValue+1;
				}
			}else{
				randomNum = min;
			}	
		}
		
		cValue = CurrentValueHelper.setCurrentValue(id, type, randomNum);

		tempCurrentValue.add(cValue);
		return randomNum;
	}
	
	public float floatGenerator(Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){
		
		float[] range = ValueRangeCalculator.getFloatRange(values);
		float min= range[0];
		float max= range[1];
		String type ="FLOAT";
		
		float randomNum = 0;
		CurrentValue cValue = new CurrentValue();
		if(currentValue.isEmpty()){
			randomNum = min;	
		}else{
			if(CurrentValueHelper.hasElement(currentValue,id,type)){
				float lastValue = CurrentValueHelper.getFloatValue(currentValue, id);
				float temp = Math.nextAfter(lastValue, max);
				if(temp<max){
					randomNum = temp;
				}
				
			}else{
				randomNum = min;
			}
		}
		
		cValue = CurrentValueHelper.setCurrentValue(id, type, randomNum);
		tempCurrentValue.add(cValue);
		return randomNum;
	}
	
	public double doubleGenerator(Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){
		
		double[] range = ValueRangeCalculator.getDoubleRange(values);
		double min= range[0];
		double max= range[1];
		String type ="DOUBLE";
		
		double randomNum = 0;
		CurrentValue cValue = new CurrentValue();
		if(currentValue.isEmpty()){
			randomNum = min;
		}else{
			if(CurrentValueHelper.hasElement(currentValue,id,type)){
				double lastValue = CurrentValueHelper.getDoubleValue(currentValue, id);
				double temp = Math.nextAfter(lastValue, max);
				if(temp<max){
					randomNum = temp;
				}
			}else{
				randomNum = min;
			}
		}
		cValue = CurrentValueHelper.setCurrentValue(id, type, randomNum);	
		tempCurrentValue.add(cValue);
		return randomNum;
	}
	
	public long longGenerator(Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){
		
		long[] range = ValueRangeCalculator.getLongRange(values);
		long min= range[0];
		long max= range[1];
		String type ="LONG";
		
		long randomNum = 0;
		CurrentValue cValue = new CurrentValue();
		if(currentValue.isEmpty()){
			randomNum = min;
		}else{
			if(CurrentValueHelper.hasElement(currentValue,id,type)){
				long lastValue = CurrentValueHelper.getLongValue(currentValue, id);
				if(lastValue+1<=max){
					randomNum = lastValue+1;
				}
			}else{
				randomNum = min;
			}
			
		}
		cValue = CurrentValueHelper.setCurrentValue(id, type, randomNum);	
		tempCurrentValue.add(cValue);
		return randomNum;
	}
	
	public short shortGenerator(Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){
		short randomNum = (short)ThreadLocalRandom.current().nextInt(1 << 16);
		return randomNum;
	}
}
