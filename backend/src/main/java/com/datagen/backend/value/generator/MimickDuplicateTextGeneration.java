package com.datagen.backend.value.generator;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import com.datagen.backend.helper.TextChecker;
import com.datagen.backend.helper.ValueRangeCalculator;

public class MimickDuplicateTextGeneration {
	
	public String wordsGenerator(Collection<Object> values) {

		ArrayList<String> texts = new ArrayList<String>();
		int min = 0;
		int max = 0;
		for(Object value:values){
			String[] word = ((String) value).split("[^a-zA-Z0-9']+");
			int temp_min = word.length;
			int temp_max = word.length;
			for(String s:word){
				texts.add(s);
			}
			
			if(temp_min<min){
				min = temp_min;
			}else if(temp_max>max){
				max= temp_max;
			}
		}
		if(max>Integer.MAX_VALUE){
			max = Integer.MAX_VALUE;
		}
		String finalText = TextGenerator.sentenceGenerator(min, max, texts);
		return finalText;
	}
	
	public String alphaNumGenerator(Collection<Object> values){
		String text = (String)values.iterator().next();
		String finalText = TextGenerator.alphanumericGenerator(text);
		return finalText;
	}
	
	public String urlGenerator(Collection<Object> values) {
		String text = (String)values.iterator().next();
		String finalText = TextGenerator.urlGenerator(text);
		return finalText;
	}
	
	public String emailGenerator(Collection<Object> values){
		String text = (String)values.iterator().next();
		String finalText = TextGenerator.emailGenerator(text);
		return finalText;
	}
	
	public String dateGenerator(Collection<Object> values){
		Collection<Object> dates = new ArrayList<Object>();
		for(Object value:values){
			String temp = (String) value;
			long date = TextChecker.dateToLong(temp);
			dates.add(date);
		}
		
		long[] range = ValueRangeCalculator.getLongRange(dates);
		long min= range[0];
		long max= range[1];
		long randomNum = ThreadLocalRandom.current().nextLong(min, max + 1);
		LocalDate date = Instant.ofEpochSecond(randomNum).atZone(ZoneId.systemDefault()).toLocalDate();
		return date.toString();
	}

}
