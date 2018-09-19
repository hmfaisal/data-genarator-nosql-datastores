package com.datagen.backend.service;

import com.datagen.backend.model.Calculation;
import com.datagen.backend.model.FormatWrap;

public interface SqlService {
	
	Calculation calculation(FormatWrap wrap);
	void generator(FormatWrap wrap);

}
