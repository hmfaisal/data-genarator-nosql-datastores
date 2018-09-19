package com.datagen.backend.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;

public class DateTimeChecker {
	
	
	//private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy/MM/dd][dd/MM/yyyy][dd-MM-yyyy][MM-dd-yyyy][MM/dd/yyyy][yyyy-MM-dd HH:mm:ss][yyyy-MM-dd HH:mm][yyyy-MM-dd HH][yyyy/MM/dd HH:mm:ss][yyyy/MM/dd HH:mm][yyyy/MM/dd HH]");
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy/MM/dd][dd/MM/yyyy][dd-MM-yyyy][MM-dd-yyyy][MM/dd/yyyy][yyyy-MM-dd'T'HH:mm:ss]");
	
	/*
	private static final String[] formats = { 
            "yyyy-MM-dd'T'HH:mm:ss'Z'",   "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss",      "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss", 
            "MM/dd/yyyy HH:mm:ss",        "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'", 
            "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS", 
            "MM/dd/yyyy'T'HH:mm:ssZ",     "MM/dd/yyyy'T'HH:mm:ss", 
            "yyyy:MM:dd HH:mm:ss",        "yyyyMMdd", 
            "yyyy/MM/dd HH:mm:ss",
            "yyyy-MM-dd", 				  "yyyy/MM/dd", 
            "dd/MM/yyyy", 				  "dd-MM-yyyy", 
            "MM-dd-yyyy", 				  "MM/dd/yyyy", 
            };
            */
	/*
	public static boolean dateChecker(String text) {
		boolean bool = false;
		if (text != null) {
            for (String parse : formats) {
                SimpleDateFormat sdf = new SimpleDateFormat(parse);
                try {
                    sdf.parse(text);
                    bool= true;
                } catch (ParseException e) {

                }
            }
        }
		return bool;
	}
	*/
	
	public static boolean dateChecker(String stringFromTxt) {
		try {
		    DATE_TIME_FORMAT.parse(stringFromTxt);
		    return true;
		} catch (DateTimeParseException dtpe) {
		    return false;
		}
	}
	
	/*
	public static boolean dateChecker(String text){
		
		String[] patterns = {"yyyy-MM-dd", "yyyy/MM/dd", "dd/MM/yyyy", "dd-MM-yyyy", "MM-dd-yyyy", "MM/dd/yyyy", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH"};
		boolean check = Arrays.asList(patterns).stream()
		        .anyMatch(pattern -> {
		            try {
		                LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
		                return true;
		            } catch (Exception e) {
		                return false;
		            }
		        });
		return check;	
	}
	*/

}
