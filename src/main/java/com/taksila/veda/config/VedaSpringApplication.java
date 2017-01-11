package com.taksila.veda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.taksila")
public class VedaSpringApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(VedaSpringApplication.class, args);				
	}
		
	
//	@Autowired
//	public ClassRoomSessionComponent classRoomSessionComponent;
//	
//	@Bean
//	public CommandLineRunner addRepo(EventSessionsRepository repository)
//	{
//		
//		return (args)->	
//		{
////			eventSessionsComponent.save(new EventSessionsEntity("mm1"));
////			eventSessionsComponent.save(new EventSessionsEntity("mm1"));
////			eventSessionsComponent.save(new EventSessionsEntity("mm1"));
////			eventSessionsComponent.save(new EventSessionsEntity("mm1"));
////			eventSessionsComponent.save(new EventSessionsEntity("mm1"));
////			
////			for (EventSessionsEntity eventSess: repository.findByUserRecordId("mm1"))
////			{
////				System.out.println("****** event uid = "+eventSess.getEventSessionId());
////			}
//			
//		};
		
	
	
}
