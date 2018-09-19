package com.datagen.backend.helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;

public class ValueGenSeqCount {
	
	static String[] ALPHABET = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
	
	public static int intGenCount(Collection<Object> values){
		
		int[] range = ValueRangeCalculator.getIntRange(values);
		int min= range[0];
		int max= range[1];
		
		return (max-min)+1;
	}
	
	public static short shortGenCount(Collection<Object> values){
		
		short[] range = ValueRangeCalculator.getShortRange(values);
		short min= range[0];
		short max= range[1];
		
		return (short) ((max-min)+1);
	}
	
	public static long longGenCount(Collection<Object> values){
		
		long[] range = ValueRangeCalculator.getLongRange(values);
		long min= range[0];
		long max= range[1];
		
		return (max-min)+1; 
	}
	
	public static long dateGenCount(Collection<Object> values){
		
		Collection<Object> dates = new ArrayList<Object>();
		for(Object value:values){
			String temp = (String) value;
			long date = TextChecker.dateToLong(temp);
			dates.add(date);
		}
		
		long[] range = ValueRangeCalculator.getLongRange(dates);
		long min= range[0];
		long max= range[1];
		
		LocalDate minDate = Instant.ofEpochSecond(min).atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate maxDate = Instant.ofEpochSecond(max).atZone(ZoneId.systemDefault()).toLocalDate();
		long daysBetween = ChronoUnit.DAYS.between(minDate, maxDate);
		
		return daysBetween; 
	}
	
	public static long alphaNumCount(String text){
		int letters = ALPHABET.length;
		int count = text.length();
		long combinations = (int) Math.pow(letters, count);
		return combinations;
	}
	
	public static long urlCount(String text){
		URL url = null;
		try {
			url = new URL(text);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return alphaNumCount(url.getHost());
	}
	
	public static long emailCount(String text){
		String name   = text.substring(0, text.lastIndexOf("@"));
		return alphaNumCount(name);
	}
	
	public static long sentenceCount(int max, ArrayList<String> texts){
		int total = texts.size();
		long combinations =  (int) Math.pow(total, max);
		if(combinations>Integer.MAX_VALUE){
			combinations = Integer.MAX_VALUE;
		}
		System.out.println(combinations);
		return combinations;
	}
	
	//no use
	public static long floatGenCount(Collection<Object> values){
		float[] range = ValueRangeCalculator.getFloatRange(values);
		float min= range[0];
		float max= range[1];
		float i =min;
		long counter =0;
		while(i<max){
			float temp = Math.nextAfter(min, max);
			counter++;
			i=temp;
		}
		return counter;
	}
	
	//no use
	public static long doubleGenCount(Collection<Object> values){
		
		double[] range = ValueRangeCalculator.getDoubleRange(values);
		double min= range[0];
		double max= range[1];
		long counter =0;
		while(min<max){
			double temp = Math.nextAfter(min, max);
			counter++;
			min=temp;
		}
		return counter;
	}

}
