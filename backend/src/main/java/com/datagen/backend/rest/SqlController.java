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
import com.datagen.backend.service.SqlService;

@RestController
@RequestMapping( value = "/api")
public class SqlController {
	
	@Autowired
    private SqlService sqlService;
	
	@RequestMapping( method = POST, value = "/sql/generate" )
    public ResponseEntity<?> sqlGeneration(@RequestBody FormatWrap wrap) throws IOException {
		try{
			this.sqlService.generator(wrap);
			return new ResponseEntity<FormatWrap>(wrap, HttpStatus.OK);
			
		}catch(HttpStatusCodeException exception) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
    }
	
	@RequestMapping( method = POST, value = "/sql/calculation" )
    public ResponseEntity<?> sqlCalculation(@RequestBody FormatWrap wrap) throws IOException {
		try{
			Calculation calc = this.sqlService.calculation(wrap);
			return new ResponseEntity<Calculation>(calc, HttpStatus.OK);
			
		}catch(HttpStatusCodeException exception) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
    }

}
