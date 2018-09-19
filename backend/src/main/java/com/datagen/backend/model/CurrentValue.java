package com.datagen.backend.model;

import org.springframework.util.LinkedMultiValueMap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrentValue {
	
	private int id;
	LinkedMultiValueMap<String, Object> currentVal = new LinkedMultiValueMap<String, Object>();
	
}
