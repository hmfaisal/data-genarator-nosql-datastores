package com.datagen.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DgenMethod {
	private int id;
	private String method;
	private long total;
}
