package com.taksila;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.taksila.veda.db.eventsessions.EventSessionsRepository;

@SpringBootApplication
@ComponentScan("com.taksila")
@ServletComponentScan
public class VedaSpringApplication extends SpringBootServletInitializer
{
	public static void main(String[] args) 
	{
		new VedaSpringApplication().configure(new SpringApplicationBuilder((VedaSpringApplication.class))).run(args);
//		SpringApplication.run(VedaSpringApplication.class, args);
				
	}		
	
	
	@Bean
	public CommandLineRunner addRepo(EventSessionsRepository repository)
	{
		
		return (args)->	
		{
//			eventSessionsComponent.save(new EventSessionsEntity("mm1"));
//			eventSessionsComponent.save(new EventSessionsEntity("mm1"));
//			eventSessionsComponent.save(new EventSessionsEntity("mm1"));
//			eventSessionsComponent.save(new EventSessionsEntity("mm1"));
//			eventSessionsComponent.save(new EventSessionsEntity("mm1"));
//			
//			for (EventSessionsEntity eventSess: repository.findByUserRecordId("mm1"))
//			{
//				System.out.println("****** event uid = "+eventSess.getEventSessionId());
//			}
			
		};
		
		
		
	}
	
}
