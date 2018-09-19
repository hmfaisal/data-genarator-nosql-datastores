package com.datagen.backend.value.generator;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.datagen.backend.helper.ValueRangeCalculator;
import com.datagen.backend.helper.CurrentValueHelper;
import com.datagen.backend.helper.TextChecker;
import com.datagen.backend.model.CurrentValue;
import com.datagen.backend.model.TextReturnValue;

public class MimickUniqueTextGeneration {
	
	public String wordsGenerator(Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){
		
		ArrayList<String> texts = new ArrayList<String>();
		int min = 0;
		int max = 0;
		for(Object value:values){
			String[] word = ((String) value).split("[^a-zA-Z0-9']+");
			int temp_min = word.length;
			int temp_max = word.length;
			for(String s:word){
				if(!texts.contains(s)){
					texts.add(s);
				}
			}
			if(min==0 && max==0){
				min = temp_min;
				max= temp_max;
			}
			else if(temp_min<min){
				min = temp_min;
			}else if(temp_max>max){
				max= temp_max;
			}
		}

		if(max>Integer.MAX_VALUE){
			max = Integer.MAX_VALUE;
		}
		
		String type ="WORDS";
		CurrentValue cValue = new CurrentValue();
		TextGeneratorSequence tGen = new TextGeneratorSequence();
		TextReturnValue resObj = new TextReturnValue();
		String lastLoop = "-1";
		
		if(currentValue.isEmpty()){
			lastLoop = "-1";
		}else{
			if(CurrentValueHelper.hasElement(currentValue,id,type)){
				lastLoop = CurrentValueHelper.getTextValue(currentValue, id);
			}else{
				lastLoop = "-1";
			}
		}
		
		TextReturnValue resObject = tGen.sentenceGenerator(min, max, texts,lastLoop,resObj);
		String result = resObject.getResult().trim();
		String loop = resObject.getLoop();
		cValue = CurrentValueHelper.setCurrentValue(id, type, loop);
		tempCurrentValue.add(cValue);
		return result;
	}
	
	public String dateGenerator(Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){
		
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
		String type ="DATE";
		
		LocalDate target = null;
		CurrentValue cValue = new CurrentValue();
		if(currentValue.isEmpty()){
			target = minDate;
		}else{
			if(CurrentValueHelper.hasElement(currentValue,id,type)){
				LocalDate lastDate = CurrentValueHelper.getDateValue(currentValue, id);
				if(lastDate.isBefore(maxDate)||lastDate.isEqual(maxDate)){
					target = lastDate.plusDays(1);
				}
			}else{
				target = minDate;
			}
			
		}
		cValue = CurrentValueHelper.setCurrentValue(id, type, target);	
		tempCurrentValue.add(cValue);
		//DateTimeGenerator tGen = new DateTimeGenerator();
		//String finalText = tGen.uniqueDateGenerator(target);
		return target.toString();
	}
	
	public String alphaNumGenerator(Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id) {
		
		String type ="ALPHANUMERIC";
		String text = (String)values.iterator().next();
		CurrentValue cValue = new CurrentValue();
		TextGeneratorSequence tGen = new TextGeneratorSequence();
		TextReturnValue resObj = new TextReturnValue();
		String lastLoop = "-1";
		
		if(currentValue.isEmpty()){
			lastLoop = "-1";
		}else{
			if(CurrentValueHelper.hasElement(currentValue,id,type)){
				lastLoop = CurrentValueHelper.getTextValue(currentValue, id);
			}else{
				lastLoop = "-1";
			}
		}
		
		TextReturnValue resObject = tGen.alphaNumGenerator(text,lastLoop,resObj);
		String result = resObject.getResult();
		String loop = resObject.getLoop();
		cValue = CurrentValueHelper.setCurrentValue(id, type, loop);
		tempCurrentValue.add(cValue);
		return result;
	}
	
	public String urlGenerator(Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id){
		
		String type ="URL";
		String text = (String)values.iterator().next();
		CurrentValue cValue = new CurrentValue();
		String lastLoop = "-1";
		if(currentValue.isEmpty()){
			lastLoop = "-1";
		}else{
			if(CurrentValueHelper.hasElement(currentValue,id,type)){
				lastLoop = CurrentValueHelper.getTextValue(currentValue, id);
			}else{
				lastLoop = "-1";
			}
		}
		TextGeneratorSequence tGen = new TextGeneratorSequence();
		TextReturnValue resObj = new TextReturnValue();
		TextReturnValue resObject = tGen.urlGenerator(text,lastLoop,resObj);
		String result = resObject.getResult();
		String index = resObject.getLoop();
		cValue = CurrentValueHelper.setCurrentValue(id, type, index);
		tempCurrentValue.add(cValue);
		return result;
	}
	
	public String emailGenerator(Collection<Object> values,List<CurrentValue> currentValue,List<CurrentValue> tempCurrentValue,int id) {
		String type ="EMAIL";
		String text = (String)values.iterator().next();
		CurrentValue cValue = new CurrentValue();
		String lastLoop = "-1";
		
		if(currentValue.isEmpty()){
			lastLoop = "-1";
		}else{
			if(CurrentValueHelper.hasElement(currentValue,id,type)){
				lastLoop = CurrentValueHelper.getTextValue(currentValue, id);
			}else{
				lastLoop = "-1";
			}
		}
		TextGeneratorSequence tGen = new TextGeneratorSequence();
		TextReturnValue resObj = new TextReturnValue();
		TextReturnValue resObject= tGen.emailGenerator(text,lastLoop,resObj);
		String result = resObject.getResult();
		String index = resObject.getLoop();
		cValue = CurrentValueHelper.setCurrentValue(id, type, index);
		tempCurrentValue.add(cValue);
		return result;
	}
}
