package com.datagen.backend.value.generator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.math.BigInteger;

import com.datagen.backend.model.TextReturnValue;

public class TextGeneratorSequence {
	
	static String[] ALPHABET = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
	static boolean going;
	
	
	public TextReturnValue alphaNumGenerator(String text,String lastLoop,TextReturnValue resObj){
		long count = text.length();
		going = true;
		return possibleAlphaNum(count,"",lastLoop,"",resObj);
	}
	
	public TextReturnValue possibleAlphaNum(long maxLength, String curr,String lastLoop, String currLoop,TextReturnValue resObj) {
		int i = 0;
		while(i < ALPHABET.length && going){
			if(curr.length() == maxLength) {
				long cl = Long.parseLong(currLoop);
				long ll = Long.parseLong(lastLoop);
				if(cl>ll){
	        		resObj.setLoop(currLoop);
	            	resObj.setResult(curr);
	            	going = false;
				}
			}else{
					String oldCurr = curr;
	        		String oldCurrLoop = currLoop;
	                curr += ALPHABET[i];
	                currLoop+=i;
	                possibleAlphaNum(maxLength,curr,lastLoop,currLoop,resObj);
	                curr = oldCurr;
	                currLoop=oldCurrLoop;
			}	
			i++;
		}
		return resObj;
    }
	
	public TextReturnValue urlGenerator(String text,String lastLoop,TextReturnValue resObj) {
		URL url = null;
		try {
			url = new URL(text);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextReturnValue bodyObj = alphaNumGenerator( url.getHost(),lastLoop,resObj);
		String body = bodyObj.getResult();
		String result= "http://"+body+".com";
		bodyObj.setResult(result);
		return bodyObj;
	}
	
	public TextReturnValue emailGenerator(String text,String lastLoop,TextReturnValue resObj){
		String name   = text.substring(0, text.lastIndexOf("@"));
		String domain = text.substring(text.lastIndexOf("@") +1);
		TextReturnValue bodyObj = alphaNumGenerator(name,lastLoop,resObj);
		String body = bodyObj.getResult();
		String result= body+"@"+domain;
		bodyObj.setResult(result);
		return bodyObj;
	}
	
	public TextReturnValue sentenceGenerator(int min, int max, ArrayList<String> texts,String lastLoop,TextReturnValue resObj){
		
		int word =0;
		going = true;
		return possibleSentence(word,max,"",lastLoop,"",texts,resObj);
	}
	
	public TextReturnValue possibleSentence(int word,int maxLength, String curr,String lastLoop, String currLoop,ArrayList<String> texts,TextReturnValue resObj) {

		int i = 0;
		int size =0;
		if(texts.size()>Integer.MAX_VALUE){
			size = Integer.MAX_VALUE;
		}else{
			size = texts.size();
		}
		while(i < size && going){
			if(word== maxLength) {
				//int cl = Integer.parseInt(currLoop);
				//int ll = Integer.parseInt(lastLoop);
				BigInteger cl=new BigInteger(currLoop);
				BigInteger ll=new BigInteger(lastLoop);
      			int res;
      			res = cl.compareTo(ll);
				if(res==1){
	        		resObj.setLoop(currLoop);
	            	resObj.setResult(curr);
	            	going = false;
				}
			}
			else{
					String oldCurr = curr;
	        		String oldCurrLoop = currLoop;
	        		int oldWord= word;
	        		word++;
	                curr += texts.get(i)+" ";
	                currLoop+=i;
	                possibleSentence(word,maxLength,curr,lastLoop,currLoop,texts,resObj);
	                word=oldWord;
	                curr = oldCurr;
	                currLoop=oldCurrLoop;
			}
				
			i++;
		}
		return resObj;
    }

}
