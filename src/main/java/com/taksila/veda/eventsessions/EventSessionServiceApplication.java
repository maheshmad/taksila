package com.taksila.veda.eventsessions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.taksila.veda.db.eventsessions.EventSessionsRepository;
import com.taksila.veda.model.db.event_session.v1_0.EventSession;

@SpringBootApplication
@ComponentScan("com.taksila")
public class EventSessionServiceApplication 
{
	@Autowired
	ApplicationContext applicationContext;
	
	public static void main(String[] args) 
	{
		SpringApplication.run(EventSessionServiceApplication.class, args);
				
	}
		
	@Bean
	public CommandLineRunner addRepo()
	{		
		return (args)->	
		{			
			ClassRoomSessionComponent classRoomSessionComponent = applicationContext.getBean(ClassRoomSessionComponent.class,"demo");
						
			classRoomSessionComponent.test();	
//			EventSession c = new EventSession();
//			c.setUserRecordId("mm3");
//			repository.save(c);			
//			repository.save(c);			
//			repository.save(c);			
//			repository.save(c);			
//			repository.save(c);
//						
//			EventSession d = new EventSession();
//			d.setUserRecordId("mm4");
//			repository2.save(d);			
//			repository2.save(d);			
//			repository2.save(d);			
//			repository2.save(d);			
//			repository2.save(d);
			
//			for (EventSession eventSess: repository.findByUserRecordId("mm1"))
//			{
//				System.out.println("****** event uid = "+eventSess.getEventSessionId());
//			}
			
			
			
			
		};
	}
	
}
