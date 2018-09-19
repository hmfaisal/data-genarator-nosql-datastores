package com.datagen.backend.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.service.SchemaService;
import com.google.common.collect.Multimap;


@RestController
@RequestMapping( value = "/api")
public class SchemaController {
	
	@Autowired
    private SchemaService schemaService;
	
	@RequestMapping( method = GET, value = "/schema/parse" )
    public ResponseEntity<?> parse(@RequestParam("file")String file) throws IOException {
		try{
			Multimap<Integer, JsNode>  schema= this.schemaService.parse(file);
			List<Schema> result=this.schemaService.getSchema(schema);
			return new ResponseEntity<List<Schema>>(result, HttpStatus.OK);
			
		}catch(HttpStatusCodeException exception) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
    }
	

}
