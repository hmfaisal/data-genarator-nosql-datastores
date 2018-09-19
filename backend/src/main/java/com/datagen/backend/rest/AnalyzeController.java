package com.datagen.backend.rest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.datagen.backend.model.AnalyzeWrap;
import com.datagen.backend.model.Schema;
import com.datagen.backend.service.AnalyzeService;

@RestController
@RequestMapping( value = "/api")
public class AnalyzeController {
	
	@Autowired
    private AnalyzeService analyzeService;
	
	@RequestMapping( method = POST, value = "/analyze/volume/count" )
	public ResponseEntity<?> getVolumeCount( @RequestBody AnalyzeWrap wrap) {
		try{
			List<Schema> schema = wrap.getSchema();
			int id = wrap.getId();
			long volume = wrap.getVolume();
			String choice = wrap.getChoice();
			long result= this.analyzeService.countVolume( schema,id,volume,choice );
			return new ResponseEntity<Long>(result, HttpStatus.OK);
		}catch(HttpStatusCodeException exception) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

}
