package com.datagen.backend.helper;

import java.util.List;

import com.datagen.backend.model.DgenMethod;

public class MethodChecker {
	
	public static String getMethod(int id,List<DgenMethod> method){
		String met = null;
		for (DgenMethod m:method){
			if(id==m.getId()){
				met = m.getMethod();
			}
		}
		return met;
	}
}
