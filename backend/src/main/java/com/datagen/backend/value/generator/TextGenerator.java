package com.datagen.backend.value.generator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.text.RandomStringGenerator;

public class TextGenerator {
	
	public static String sentenceGenerator(int min, int max, ArrayList<String> texts){
		
		String finalText="";
		for(int i=0; i<max;i++){
			Random random = new Random();
			int index = random.nextInt(texts.size());
			finalText=finalText+texts.get(index) + " ";
		}
		return finalText.trim();	
	}
	
	public static String randomTextGenerator(int min, int max){
		
		RandomStringGenerator generator = new RandomStringGenerator.Builder()
			     .withinRange('a', 'z').build();
		String finalText="";
		
		for(int i=0; i<max;i++){
			String value = generator.generate(6);
			finalText=finalText+value + " ";
		}
		
		return finalText.trim();
	}
	
	public static String alphanumericGenerator(String text){
		
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		int length = text.length();
	    StringBuilder builder = new StringBuilder(length);

	    for (int i = 0; i < length; i++) {
	        builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
	    }

	    return builder.toString();
	}
	
	public static String urlGenerator(String text) {
		URL url = null;
		try {
			url = new URL(text);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String body = alphanumericGenerator( url.getHost());
		String result= "http://"+body+".com";
		return result;
	}
	
	public static String emailGenerator(String text){
		String name   = text.substring(0, text.lastIndexOf("@"));
		String domain = text.substring(text.lastIndexOf("@") +1);
		String body = alphanumericGenerator(name);
		String result= body+"@"+domain;
		return result;
	}

}
