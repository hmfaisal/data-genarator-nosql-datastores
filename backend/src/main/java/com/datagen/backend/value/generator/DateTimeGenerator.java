package com.datagen.backend.value.generator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

public class DateTimeGenerator {
	
	//private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy/MM/dd][dd/MM/yyyy][dd-MM-yyyy][MM-dd-yyyy][MM/dd/yyyy][yyyy-MM-dd HH:mm:ss][yyyy-MM-dd HH:mm][yyyy-MM-dd HH][yyyy/MM/dd HH:mm:ss][yyyy/MM/dd HH:mm][yyyy/MM/dd HH]");
	//private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy/MM/dd][dd/MM/yyyy][dd-MM-yyyy][MM-dd-yyyy][MM/dd/yyyy][yyyy-MM-dd'T'HH:mm:ss]");
	
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
	
	public static String duplicateDateGenerator(String text){
		LocalDate date = LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 70))));
		
		/*
		System.out.println(DATE_TIME_FORMAT.parse(text).toString());
		LocalDate ld = LocalDate.parse(text, DATE_TIME_FORMAT);
    	String result= ld.format(DATE_TIME_FORMAT).toString();
		*/
		/*
		String result = null;
		if (text != null) {
            for (String parse : formats) {
                SimpleDateFormat sdf = new SimpleDateFormat(parse);
                try {
                    sdf.parse(text);
                    System.out.println("Printing the value of " + parse);
                    result = parse;
                } catch (ParseException e) {

                }
            }
        }
        */
	    return date.toString();
	}
}
