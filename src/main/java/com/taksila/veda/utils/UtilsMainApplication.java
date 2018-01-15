package com.taksila.veda.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.taksila")
public class UtilsMainApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(UtilsMainApplication.class, args);
				
	}		
	
}
