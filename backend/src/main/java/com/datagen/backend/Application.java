package com.datagen.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

@Configuration
@SpringBootApplication
public class Application extends SpringBootServletInitializer{
	
	@Bean
    ObjectMapper customizeJacksonConfiguration() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new GuavaModule());
        return om;
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
