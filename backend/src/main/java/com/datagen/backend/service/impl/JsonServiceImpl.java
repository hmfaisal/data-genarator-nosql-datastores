package com.datagen.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datagen.backend.json.Calculator;
import com.datagen.backend.json.DistributedExecutor;
import com.datagen.backend.json.FlatExecutor;
import com.datagen.backend.model.Calculation;
import com.datagen.backend.model.DgenMethod;
import com.datagen.backend.model.FormatWrap;
import com.datagen.backend.model.Schema;
import com.datagen.backend.service.JsonService;

@Service
public class JsonServiceImpl implements JsonService {
	
	public Calculation calculation(FormatWrap wrap){
		
		List<Schema> schema = wrap.getSchema();
		long volume = wrap.getVolume();
		String fileLocation = wrap.getConfig().getFileLocation();
		List<DgenMethod> method = wrap.getMethods();
		String format = wrap.getFormat();
		String technique = wrap.getTechnique();
		
		return Calculator.calculation(schema,volume,fileLocation,method,format,technique);
		
	}
	
	public void generator(FormatWrap wrap){
		
		List<Schema> schema = wrap.getSchema();
		long volume = wrap.getVolume();
		int singleVolume = wrap.getSingleVolume();
		String fileLocation = wrap.getConfig().getFileLocation();
		List<DgenMethod> method = wrap.getMethods();
		String format = wrap.getFormat();
		String technique = wrap.getTechnique();
		
		if(format.equals("FLAT")){
			FlatExecutor.generator(schema,volume,singleVolume,fileLocation,method,technique);
		}else if(format.equals("ORIGINAL")){
			DistributedExecutor.generator(schema,volume,singleVolume,fileLocation,method,technique);
		}
		
	}

}
