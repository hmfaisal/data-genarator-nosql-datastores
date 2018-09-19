package com.datagen.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GraphsonId {
	private long vertexId;
	private long propertyId;
	private long edgeId;
}
