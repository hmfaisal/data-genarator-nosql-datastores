package com.datagen.backend.value.generator;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.Random;

public class RandomTextGeneration {
	
	public String wordsGenerator(Collection<Object> values) {

		int min = 0;
		int max = 0;
		for(Object value:values){
			String[] word = ((String) value).split("\\s+");
			int temp_min = word.length;
			int temp_max = word.length;

			if(temp_min<min){
				min = temp_min;
			}else if(temp_max>max){
				max= temp_max;
			}
		}
		if(max>Integer.MAX_VALUE){
			max = Integer.MAX_VALUE;
		}
		String finalText = TextGenerator.randomTextGenerator(min, max);
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
		LocalDate date = LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 70))));
		return date.toString();
	}

}
