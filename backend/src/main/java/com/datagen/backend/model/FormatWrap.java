package com.datagen.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FormatWrap {
	
	private List<Schema> schema;
	private int singleVolume;
	private long volume;
	private PathConfig config;
	private List<DgenMethod> methods;
	private String technique;
	private String format;
}
