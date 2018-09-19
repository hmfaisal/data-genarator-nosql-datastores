package com.datagen.backend.service;

import java.util.List;

import com.datagen.backend.model.Schema;

public interface AnalyzeService {
	
	long countVolume(List<Schema> schema,int id,long volume,String choice);

}
