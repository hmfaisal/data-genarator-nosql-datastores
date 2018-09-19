package com.datagen.backend.model;

import java.util.Collection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Schema {
	
	private int key;
	private Collection<JsNode> value;

}
