package com.taksila.veda.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.taksila.servlet.utils.ServletUtils;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.config.v1_0.GetConfigurationRequest;
import com.taksila.veda.model.api.config.v1_0.GetConfigurationResponse;
import com.taksila.veda.model.api.config.v1_0.UpdateConfigRequest;
import com.taksila.veda.model.api.config.v1_0.UpdateConfigResponse;
import com.taksila.veda.model.db.config.v1_0.Config;
import com.taksila.veda.model.db.config.v1_0.ConfigGroup;
import com.taksila.veda.model.db.config.v1_0.ConfigId;
import com.taksila.veda.model.db.config.v1_0.ConfigSection;
import com.taksila.veda.utils.CommonUtils;

@Component
@Path("/config")
@Lazy(value = true)
public class ConfigService 
{
	static Logger logger = LogManager.getLogger(ConfigService.class.getName());	
	Executor executor;	
	
	@Autowired
	ApplicationContext applicationContext;
	
	public ConfigService() 
	{
		 executor = Executors.newSingleThreadExecutor();
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param uri
	 * @param formParams
	 * @param asyncResp
	 */
	@POST	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ManagedAsync
    public void post(@Context HttpServletRequest request,@Context HttpServletResponse response,    		
    		@Context final UriInfo uri,
    		@Suspended final AsyncResponse asyncResp) 
    {    	
		executor.execute( new Runnable() 
		{
			public void run() 
			{								
				UpdateConfigResponse updateResp = new UpdateConfigResponse();
				
				try 
				{					
					MultivaluedMap<String, String> formParams= ServletUtils.getMultivaluedMap(request.getParameterMap());
					logger.trace("Inside /api/config POST" );
					String tenantId = ServletUtils.getSubDomain(uri);
					ConfigComponent configComp = applicationContext.getBean(ConfigComponent.class,tenantId);			
					List<Config> configs = new ArrayList<Config>();
					/*
					 * collect all form params
					 * 
					 * Validate
					 */
					for (String key: formParams.keySet())
					{
						try
						{
							ConfigId enumConfigKey = ConfigId.valueOf(key);							
							
							Config config = new Config();
							String v = formParams.getFirst(key); /*  we are not expecting config values to have multiple values */
							config.setConfigValue(v);
							config.setId(enumConfigKey.name());
							configs.add(config);
						}
						catch(IllegalArgumentException ill)
						{
							/* ignore this */
						}
						catch(Exception ex)
						{
							/*
							 * continue on error to check for all issues
							 */
							updateResp.setStatus(StatusType.INVALID);;
							updateResp.setErrorInfo(CommonUtils.buildErrorInfo(key, "Is not a valid configuration key"));
							updateResp.setMsg("Invalid configuration key found! Please check your input: "+ex.getMessage());
						}
								
					}
															
					/*
					 * Invoke the service, if there were no errors
					 */
					if (updateResp.getErrorInfo() == null)
					{
						UpdateConfigRequest updateRequest = new UpdateConfigRequest();
						updateRequest.getConfigs().addAll(configs);			
						updateResp = configComp.updateConfig(updateRequest);
					}
				} 
				catch (Exception ex) 
				{		
					CommonUtils.handleExceptionForResponse(updateResp, ex);
				}
				
				updateResp.setSuccess(true); /* this is the service success,  should be true for all form submissions, even when there are errors */				
				asyncResp.resume(Response.ok(updateResp).build());
			}
		});

    }
	
	/**
	 * 
	 * @param request
	 * @param uri
	 * @param resp
	 * @param asyncResp
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync	
	public void getConfigInfo(@Context HttpServletRequest request, @Context final UriInfo uri,					
			@Context HttpServletResponse resp,@Suspended final AsyncResponse asyncResp)
	{    				
		logger.trace("Inside getConfigInfo");
		executor.execute( new Runnable() 
		{
			public void run() 
			{			
				String tenantId = ServletUtils.getSubDomain(uri);
				ConfigComponent configComp = applicationContext.getBean(ConfigComponent.class,tenantId);
				GetConfigurationResponse configResp = null;
				try 
				{
					GetConfigurationRequest req = new GetConfigurationRequest();
					req.setForRole(null);
					configResp = configComp.getConfigSection(req);
					
				} 
				catch (Exception ex) 
				{		
					ex.printStackTrace();
					CommonUtils.handleExceptionForResponse(configResp, ex);
				}
				
				asyncResp.resume(Response.ok(configResp).build());
			}
		});
		
	}
	
	
	/**
	 * 
	 * @param request
	 * @param uri
	 * @param resp
	 * @param asyncResp
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	@Path("/keyval")
	public void getConfigKeyValue(@Context HttpServletRequest request, @Context final UriInfo uri,					
			@Context HttpServletResponse resp,@Suspended final AsyncResponse asyncResp)
	{    				
		executor.execute( new Runnable() 
		{
			public void run() 
			{			
				Map<String,String> configVals = new HashMap<String,String>();
				String tenantId = ServletUtils.getSubDomain(uri);
				ConfigComponent configComp = applicationContext.getBean(ConfigComponent.class,tenantId);
				GetConfigurationResponse configResp = null;
				String keyvaljson = "{";
				try 
				{
					
					GetConfigurationRequest req = new GetConfigurationRequest();
					req.setForRole(null);
					configResp = configComp.getConfigSection(req);
					
					for (ConfigSection sections: configResp.getSections())
					{
						for (ConfigGroup group: sections.getConfigGroups())
						{
							for (Config config: group.getConfigs())
							{
								configVals.put(config.getId(),config.getConfigValue());
								keyvaljson += config.getId()+":'"+config.getConfigValue()+"',";
							}
						}
					}
					
					keyvaljson = StringUtils.removeEnd(keyvaljson, ",")+"}";
					
				} 
				catch (Exception ex) 
				{		
					ex.printStackTrace();
					CommonUtils.handleExceptionForResponse(configResp, ex);
				}
				
				asyncResp.resume(Response.ok(keyvaljson).build());
			}
		});
		
	}
	
//	/**
//	 * 
//	 * @param request
//	 * @param uri
//	 * @param name
//	 * @param courseid
//	 * @param title
//	 * @param subtitle
//	 * @param description
//	 * @param resp
//	 * @param asyncResp
//	 */
//	@PUT
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@ManagedAsync
//	@Path("/{courseid}")
//	public void updateCourse(@Context HttpServletRequest request, @Context UriInfo uri,	
//			@FormParam("name") String name,
//			@PathParam("courseid") String courseid,
//    		@FormParam("title") String title,     		
//    		@FormParam("subtitle") String subtitle,
//    		@FormParam("description") String description,
//			@Context HttpServletResponse resp,@Suspended final AsyncResponse asyncResp)
//	{    				
//		UpdateCourseResponse operResp = null;
//		try
//		{
//			logger.trace("About to update course record = "+courseid);
//			
//			Course course = new Course();
//			course.setId(courseid);
//			course.setName(name);
//			course.setSubTitle(subtitle);
//			course.setTitle(title);
//			course.setDescription(description);
//			
//			UpdateCourseRequest req = new UpdateCourseRequest();
//			req.setCourse(course);
//			
//			String schoolId = ServletUtils.getSubDomain(uri);
//			CourseComponent courseComp = new CourseComponent(schoolId);
//			operResp = courseComp.updateCourse(req);
//			operResp.setSuccess(true);
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//			CommonUtils.handleExceptionForResponse(operResp, ex);
//		}
//		
//		asyncResp.resume(Response.ok(operResp).build());
//		
//	}
//	
//	/**
//	 * 
//	 * @param request
//	 * @param uri
//	 * @param courseid
//	 * @param resp
//	 * @param asyncResp
//	 */
//	@DELETE
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@ManagedAsync
//	@Path("/{courseid}")
//	public void updateCourse(@Context HttpServletRequest request, @Context UriInfo uri,	@PathParam("courseid") String courseid,			
//			@Context HttpServletResponse resp,@Suspended final AsyncResponse asyncResp)
//	{    				
//		DeleteCourseResponse operResp = new DeleteCourseResponse();
//		try
//		{
//			logger.trace("About to delete course record = "+courseid);						
//			
//			String schoolId = ServletUtils.getSubDomain(uri);
//			CourseComponent courseComp = new CourseComponent(schoolId);
//			DeleteCourseRequest req = new DeleteCourseRequest();
//			req.setId(Integer.valueOf(courseid));
//			operResp = courseComp.deleteCourse(req);
//			operResp.setSuccess(true);
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//			CommonUtils.handleExceptionForResponse(operResp, ex);
//		}
//		
//		asyncResp.resume(Response.ok(operResp).build());
//		
//	}
//	
//	/**
//	 * 
//	 * @param request
//	 * @param uri
//	 * @param resp
//	 * @param name
//	 * @param page
//	 * @param start
//	 * @param asyncResp
//	 */
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@ManagedAsync
//	@Path("/search")
//	public void searchCourses(@Context HttpServletRequest request, @Context UriInfo uri,@Context HttpServletResponse resp,
//			@QueryParam("q") String name,@QueryParam("page") String page,@QueryParam("start") String start, @Suspended final AsyncResponse asyncResp)
//	{    				
//		
//		logger.trace("inside search query = "+name);
//		
//		SearchCourseResponse searchResp = new SearchCourseResponse();		
//		SearchCourseRequest req = new SearchCourseRequest();
//		try 
//		{
//			req.setPage(Integer.valueOf(page));
//			req.setPageOffset(Integer.valueOf(start));		
//			req.setQuery(name);
//			req.setRecordType("COURSE");
//			
//			String schoolId = ServletUtils.getSubDomain(uri);
//			CourseComponent courseComp = new CourseComponent(schoolId);
//			searchResp = courseComp.searchCourses(req);
//		
//		} 
//		catch (Exception e) 
//		{			
//			e.printStackTrace();
//		}
//		
//		searchResp.setSuccess(true);
//		asyncResp.resume(Response.ok(searchResp).build());
//		
//		
//	}
//	
	
	
	

	
}
