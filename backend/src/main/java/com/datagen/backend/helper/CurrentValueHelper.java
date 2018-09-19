package com.datagen.backend.helper;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.model.CurrentValue;

public class CurrentValueHelper {
	
	public static CurrentValue setCurrentValue(int id,String type,Object value){
		CurrentValue cVal = new CurrentValue();
		cVal.setId(id);
		LinkedMultiValueMap <String, Object> newCVal= new LinkedMultiValueMap <String, Object>();
		newCVal.add(type, value);
		cVal.setCurrentVal(newCVal);
		return cVal;
	}
	
	public static boolean hasElement(List<CurrentValue> currentValue,int id,String type){
		
		for(CurrentValue cv:currentValue){
			int key = cv.getId();
			if(key==id){
				LinkedMultiValueMap<String, Object> currentVal = cv.getCurrentVal();
				for(String vType : currentVal.keySet()){
					if(vType.equals(type)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static int getIntValue(List<CurrentValue> currentValue,int id){
		int result = 0;
		for(CurrentValue cv:currentValue){
			int key = cv.getId();
			if(key==id){
				LinkedMultiValueMap<String, Object> currentVal= cv.getCurrentVal();
				for(String val : currentVal.keySet()){
					if(val.equals("INTEGER")){
						Collection<Object> values = currentVal.get(val);
						for(Object value:values){
							result = (int) value;
						}
					}
				}
			}
		}
		return result;
	}
	
	public static double getDoubleValue(List<CurrentValue> currentValue,int id){
		double result = 0;
		for(CurrentValue cv:currentValue){
			int key = cv.getId();
			if(key==id){
				LinkedMultiValueMap<String, Object> currentVal= cv.getCurrentVal();
				for(String val : currentVal.keySet()){
					if(val.equals("DOUBLE")){
						Collection<Object> values = currentVal.get(val);
						for(Object value:values){
							result = (double) value;
						}
					}
				}
			}
		}
		return result;
	}
	
	public static float getFloatValue(List<CurrentValue> currentValue,int id){
		float result = 0;
		for(CurrentValue cv:currentValue){
			int key = cv.getId();
			if(key==id){
				LinkedMultiValueMap<String, Object> currentVal= cv.getCurrentVal();
				for(String val : currentVal.keySet()){
					if(val.equals("FLOAT")){
						Collection<Object> values = currentVal.get(val);
						for(Object value:values){
							result = (float) value;
						}
					}
				}
			}
		}
		return result;
	}
	
	public static long getLongValue(List<CurrentValue> currentValue,int id){
		long result = 0;
		for(CurrentValue cv:currentValue){
			int key = cv.getId();
			if(key==id){
				LinkedMultiValueMap<String, Object> currentVal= cv.getCurrentVal();
				for(String val : currentVal.keySet()){
					if(val.equals("LONG")){
						Collection<Object> values = currentVal.get(val);
						for(Object value:values){
							result = (long) value;
						}
					}
				}
			}
		}
		return result;
	}
	
	public static LocalDate getDateValue(List<CurrentValue> currentValue,int id){
		LocalDate result = null ;
		for(CurrentValue cv:currentValue){
			int key = cv.getId();
			if(key==id){
				LinkedMultiValueMap<String, Object> currentVal= cv.getCurrentVal();
				for(String val : currentVal.keySet()){
					if(val.equals("DATE")){
						Collection<Object> values = currentVal.get(val);
						for(Object value:values){
							result = (LocalDate) value;
						}
					}
				}
			}
		}
		return result;
	}
	
	public static short getShortValue(List<CurrentValue> currentValue,int id){
		short result = 0;
		for(CurrentValue cv:currentValue){
			int key = cv.getId();
			if(key==id){
				LinkedMultiValueMap<String, Object> currentVal= cv.getCurrentVal();
				for(String val : currentVal.keySet()){
					if(val.equals("SHORT")){
						Collection<Object> values = currentVal.get(val);
						for(Object value:values){
							result = (short) value;
						}
					}
				}
			}
		}
		return result;
	}
	
	public static String getTextValue(List<CurrentValue> currentValue,int id){
		String result = "";
		for(CurrentValue cv:currentValue){
			int key = cv.getId();
			if(key==id){
				LinkedMultiValueMap<String, Object> currentVal= cv.getCurrentVal();
				for(String val : currentVal.keySet()){
					if(val.equals("WORDS")||val.equals("ALPHANUMERIC")||val.equals("URL")||val.equals("EMAIL")){
						Collection<Object> values = currentVal.get(val);
						for(Object value:values){
							result = (String) value;
						}
					}
				}
			}
		}
		return result;
	}

}
