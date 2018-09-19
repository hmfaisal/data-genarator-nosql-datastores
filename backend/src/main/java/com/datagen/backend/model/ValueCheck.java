package com.datagen.backend.model;

import org.springframework.util.LinkedMultiValueMap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ValueCheck {
	
	private int id;
	LinkedMultiValueMap<Integer, Long> occurance = new LinkedMultiValueMap<Integer, Long>();
	LinkedMultiValueMap<Integer, Integer> loop = new LinkedMultiValueMap<Integer, Integer>();
	LinkedMultiValueMap<String, Long> valueMap = new LinkedMultiValueMap<String, Long>();

}
