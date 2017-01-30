package com.taksila.veda.eventschedulemgmt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.taksila.servlet.utils.ServletUtils;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.CreateEventScheduleRequest;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.CreateEventScheduleResponse;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.DeleteEventScheduleRequest;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.DeleteEventScheduleResponse;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.GetEventScheduleRequest;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.GetEventScheduleResponse;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.SearchEventScheduleRequest;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.SearchEventScheduleResponse;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.UpdateEventScheduleResponse;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.UpdateEventScheduleSessionIdRequest;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.UpdateEventScheduleSessionIdResponse;
import com.taksila.veda.model.db.event_schedule_mgmt.v1_0.EventSchedule;
import com.taksila.veda.security.SecurityUtils;
import com.taksila.veda.utils.CommonUtils;

@Path("/eventschedule")
public class EventScheduleMgmtService 
{
	static Logger logger = LogManager.getLogger(EventScheduleMgmtService.class.getName());	
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	SecurityUtils securityUtils;
	/**
	 * 	 
	 */
	@POST	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ManagedAsync
    public void post(@Context HttpServletRequest request,@Context HttpServletResponse response,
    		@Context UriInfo uri,	
    		@Suspended final AsyncResponse asyncResp) 
    {    	
		String tenantId = ServletUtils.getSubDomain(uri);
		EventScheduleMgmtComponent eventScheduleComp = applicationContext.getBean(EventScheduleMgmtComponent.class,tenantId);
		CreateEventScheduleResponse operResp = new CreateEventScheduleResponse();
		
		logger.trace("processing eventSchedule creation..");
		try 
		{
			MultivaluedMap<String, String> formParams= ServletUtils.getMultivaluedMap(request.getParameterMap());
			String principalUserId = securityUtils.getLoggedInPrincipalUserid(tenantId, request);
			
			EventSchedule eventSchedule = new EventSchedule();
			eventScheduleComp.mapFormFields(formParams, eventSchedule);
			eventSchedule.setUpdatedBy(principalUserId);
			
			CreateEventScheduleRequest req = new CreateEventScheduleRequest();
			req.setEventSchedule(eventSchedule);
			
			operResp = eventScheduleComp.createNewEventSchedule(req); 			
			operResp.setSuccess(true);
		} 
		catch (Exception ex) 
		{		
			ex.printStackTrace();
			CommonUtils.handleExceptionForResponse(operResp, ex);
		}
		
		asyncResp.resume(Response.ok(operResp).build());

    }
	
	
	/**
	 * 	 
	 */
//	@POST	
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@ManagedAsync
//	@Path("{eventScheduleid}/session/{eventSessionId}")
//    public void updateSessionId(@Context HttpServletRequest request,@Context HttpServletResponse response,
//    		@PathParam("eventScheduleid") String eventScheduleid,
//    		@PathParam("eventSessionId") String eventSessionId,
//    		@Context UriInfo uri,	
//    		@Suspended final AsyncResponse asyncResp) 
//    {    	
//		String tenantId = ServletUtils.getSubDomain(uri);
//		EventScheduleMgmtComponent eventScheduleComp = applicationContext.getBean(EventScheduleMgmtComponent.class,tenantId);
//		UpdateEventScheduleSessionIdResponse operResp = new UpdateEventScheduleSessionIdResponse();
//		
//		logger.trace("processing updateSessionId ..");
//		try 
//		{
//			String principalUserId = securityUtils.getLoggedInPrincipalUserid(tenantId, request);
//									
//			UpdateEventScheduleSessionIdRequest req = new UpdateEventScheduleSessionIdRequest();
//			req.setUserRecordId(principalUserId);
//			req.setEventScheduleId(eventScheduleid);
//			req.setEventSessionId(eventSessionId);
//			
//			operResp = eventScheduleComp.updateEventScheduleSessionId(req); 
//			operResp.setStatus(StatusType.SUCCESS);
//			operResp.setSuccess(true);
//		} 
//		catch (Exception ex) 
//		{		
//			ex.printStackTrace();
//			CommonUtils.handleExceptionForResponse(operResp, ex);
//		}
//		
//		asyncResp.resume(Response.ok(operResp).build());
//
//    }
	
	
	
	
	/**
	 * 
	 * @param request
	 * @param uri
	 * @param eventScheduleid
	 * @param resp
	 * @param asyncResp
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	@Path("/{id}")
	public void getLoggedInUserInfo(@Context HttpServletRequest request, @Context UriInfo uri,		
			@PathParam("id") String eventScheduleid,
			@Context HttpServletResponse resp,@Suspended final AsyncResponse asyncResp)
	{    				
		
		GetEventScheduleResponse operResp = new GetEventScheduleResponse();
		try 
		{
			GetEventScheduleRequest req = new GetEventScheduleRequest();
			req.setId(eventScheduleid);;
			
			String tenantId = ServletUtils.getSubDomain(uri);
			EventScheduleMgmtComponent eventScheduleComp = applicationContext.getBean(EventScheduleMgmtComponent.class,tenantId);
			operResp = eventScheduleComp.getEventSchedule(req); 			
			operResp.setSuccess(true);
		} 
		catch (Exception ex) 
		{		
			ex.printStackTrace();
			CommonUtils.handleExceptionForResponse(operResp, ex);
		}
		
		asyncResp.resume(Response.ok(operResp).build());
		
	}
		
	
	/**
	 * 
	 * @param request
	 * @param uri
	 * @param eventScheduleid
	 * @param formParams
	 * @param resp
	 * @param asyncResp
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ManagedAsync
	@Path("/{id}")
	public void put(@Context HttpServletRequest request, @Context UriInfo uri,	
			@PathParam("id") String eventScheduleid,
			@Context HttpServletResponse resp,@Suspended final AsyncResponse asyncResp)
	{    				
		String tenantId = ServletUtils.getSubDomain(uri);
		EventScheduleMgmtComponent eventScheduleComp = applicationContext.getBean(EventScheduleMgmtComponent.class,tenantId);
		UpdateEventScheduleResponse operResp = new UpdateEventScheduleResponse();				
		try
		{
			logger.trace("About to update eventSchedule record = "+eventScheduleid);
			String principalUserId = securityUtils.getLoggedInPrincipalUserid(tenantId, request);
			MultivaluedMap<String, String> formParams= ServletUtils.getMultivaluedMap(request.getParameterMap());									
			operResp = eventScheduleComp.updateEventSchedule(formParams,eventScheduleid,principalUserId);
			operResp.setSuccess(true);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			CommonUtils.handleExceptionForResponse(operResp, ex);
		}
		
		asyncResp.resume(Response.ok(operResp).build());
		
	}
	
	/**
	 * 
	 * @param request
	 * @param uri
	 * @param eventScheduleid
	 * @param resp
	 * @param asyncResp
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ManagedAsync
	@Path("/{id}")
	public void deleteEventSchedule(@Context HttpServletRequest request, 
			@Context UriInfo uri,	
			@PathParam("id") String eventScheduleid,			
			@Context HttpServletResponse resp,
			@Suspended final AsyncResponse asyncResp)
	{    				
		DeleteEventScheduleResponse operResp = new DeleteEventScheduleResponse();
		try
		{
			logger.trace("About to delete eventSchedule record = "+eventScheduleid);						
			
			String tenantId = ServletUtils.getSubDomain(uri);
			EventScheduleMgmtComponent eventScheduleComp = applicationContext.getBean(EventScheduleMgmtComponent.class,tenantId);
			DeleteEventScheduleRequest req = new DeleteEventScheduleRequest();
			req.setId(eventScheduleid);
			operResp = eventScheduleComp.deleteEventSchedule(req);
			operResp.setSuccess(true);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			CommonUtils.handleExceptionForResponse(operResp, ex);
		}
		
		asyncResp.resume(Response.ok(operResp).build());
		
	}
	
	/**
	 * 
	 * @param request
	 * @param uri
	 * @param resp
	 * @param name
	 * @param page
	 * @param start
	 * @param asyncResp
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	@Path("/search")
	public void searchEventSchedules(@Context HttpServletRequest request, @Context UriInfo uri,@Context HttpServletResponse resp,
			@QueryParam("eventRecordid") String eventRecordid,
			@QueryParam("classroomid") String classroomid,
			@QueryParam("urecid") String urecid,
			@QueryParam("page") String page,
			@QueryParam("start") String start, 
			@Suspended final AsyncResponse asyncResp)
	{    				
		
		logger.trace("inside event eventSchedule query = "+eventRecordid+" for user id = "+urecid);
		
		SearchEventScheduleResponse searchResp = new SearchEventScheduleResponse();		
		SearchEventScheduleRequest req = new SearchEventScheduleRequest();
		String tenantId = ServletUtils.getSubDomain(uri);
		EventScheduleMgmtComponent eventScheduleComp = applicationContext.getBean(EventScheduleMgmtComponent.class,tenantId);
		
		try 
		{
			if (StringUtils.isNotBlank(page))			
				req.setPage(Integer.valueOf(page));
			if (StringUtils.isNotBlank(start))		
				req.setPageOffset(Integer.valueOf(start));
						
			req.setRecordType("EVENTS-SCHEDULE");
			
			req.setClassroomid(classroomid);
			req.setUserRecordId(urecid);
			searchResp = eventScheduleComp.searchEventSchedule(req);
			
						
		} 
		catch (Exception e) 
		{			
			e.printStackTrace();
		}
		
		searchResp.setSuccess(true);
		asyncResp.resume(Response.ok(searchResp).build());
		
		
	}
	
	
	
	
	
	
	

	
}
