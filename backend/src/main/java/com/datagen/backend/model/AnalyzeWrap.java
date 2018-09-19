package com.datagen.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnalyzeWrap {
	
	private List<Schema> schema;
	private String choice;
	private long volume;
	private int id;

}
