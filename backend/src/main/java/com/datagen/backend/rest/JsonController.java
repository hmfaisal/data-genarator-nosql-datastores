package com.datagen.backend.rest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.datagen.backend.model.Calculation;
import com.datagen.backend.model.FormatWrap;
import com.datagen.backend.service.JsonService;

@RestController
@RequestMapping( value = "/api")
public class JsonController {
	
	@Autowired
    private JsonService jsonService;
	
	@RequestMapping( method = POST, value = "/json/generate" )
    public ResponseEntity<?> jsonGeneration(@RequestBody FormatWrap wrap) throws IOException {
		try{
			this.jsonService.generator(wrap);
			return new ResponseEntity<FormatWrap>(wrap, HttpStatus.OK);
			
		}catch(HttpStatusCodeException exception) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
    }
	
	@RequestMapping( method = POST, value = "/json/calculation" )
    public ResponseEntity<?> jsonCalculation(@RequestBody FormatWrap wrap) throws IOException {
		try{
			Calculation calc = this.jsonService.calculation(wrap);
			return new ResponseEntity<Calculation>(calc, HttpStatus.OK);
			
		}catch(HttpStatusCodeException exception) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
    }

}
