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
import com.datagen.backend.service.GraphsonService;

@RestController
@RequestMapping( value = "/api")
public class GraphsonController {
	
	@Autowired
    private GraphsonService graphsonService;
	
	@RequestMapping( method = POST, value = "/graphson/generate" )
    public ResponseEntity<?> graphsonGeneration(@RequestBody FormatWrap wrap) throws IOException {
		try{
			this.graphsonService.generator(wrap);
			return new ResponseEntity<FormatWrap>(wrap, HttpStatus.OK);
			
		}catch(HttpStatusCodeException exception) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
    }
	
	@RequestMapping( method = POST, value = "/graphson/calculation" )
    public ResponseEntity<?> graphsonCalculation(@RequestBody FormatWrap wrap) throws IOException {
		try{
			Calculation calc = this.graphsonService.calculation(wrap);
			return new ResponseEntity<Calculation>(calc, HttpStatus.OK);
			
		}catch(HttpStatusCodeException exception) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
    }

}
