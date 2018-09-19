package com.datagen.backend.helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextChecker {
	
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy/MM/dd][dd/MM/yyyy][dd-MM-yyyy][MM-dd-yyyy][MM/dd/yyyy][yyyy-MM-dd'T'HH:mm:ss]");
	
	public static boolean alphanumericChecker(String text){
		
		if (text.matches(".*[A-Za-z].*") && text.matches(".*[0-9].*") && text.matches("[A-Za-z0-9]*")) {
		    return true;
		} else {
		    return false;
		}	
	}
	
	public static boolean urlChecker(String text){
		
		Pattern REGEX = Pattern.compile("(?i)^(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,}))\\.?)(?::\\d{2,5})?(?:[/?#]\\S*)?$");
		Matcher matcher = REGEX.matcher(text);
	    if (matcher.find()) {
	       return true;
	    }else{
	    	return false;
	    }
	}
	
	public static boolean emailChecker(String text){
		
		Pattern REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = REGEX.matcher(text);
	    if (matcher.find()) {
	       return true;
	    }else{
	    	return false;
	    }
	}
	
	public static long dateToLong(String text){
		
		LocalDate date = LocalDate.parse(text, DATE_TIME_FORMAT);
		long finalTime = date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
		//long finalTime = formatDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		return finalTime;
	}
}
