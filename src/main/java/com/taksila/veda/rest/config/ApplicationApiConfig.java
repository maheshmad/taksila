package com.taksila.veda.rest.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.taksila.veda.classroom.ClassroomService;
import com.taksila.veda.classroom.EnrollmentService;
import com.taksila.veda.config.ConfigService;
import com.taksila.veda.course.ChapterService;
import com.taksila.veda.course.CourseService;
import com.taksila.veda.course.TopicService;
import com.taksila.veda.course.slides.SlideService;
import com.taksila.veda.eventschedulemgmt.EventScheduleMgmtService;
import com.taksila.veda.security.UserAuthService;
import com.taksila.veda.usermgmt.UserService;
/**
 * The @ApplicationPath annotation is used to define the URL mapping for the application. 
 * The path specified by @ApplicationPath is the base URI for all resource URIs specified by @Path annotations in the resource class. 
 * You may only apply @ApplicationPath to a subclass of javax.ws.rs.core.Application.
 * 
 * @author      Mahesh M
 * @version     %I%, %G%
 * @since       1.0
 */
@Configuration
@ApplicationPath("api")
public class ApplicationApiConfig extends ResourceConfig
{
	public ApplicationApiConfig()
	{
		System.out.println("Inside veda application api config !!!!!!!!!!!!!");
		register(ConfigService.class);
		register(UserService.class);
		register(ClassroomService.class);
		register(EnrollmentService.class);
		register(ChapterService.class);
		register(CourseService.class);
		register(TopicService.class);
		register(SlideService.class);
		register(EventScheduleMgmtService.class);
		register(UserAuthService.class);
		register(ClassroomService.class);
		
		packages("com.taksila.veda");
	}
	
}
