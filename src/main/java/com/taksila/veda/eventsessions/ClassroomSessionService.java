package com.taksila.veda.eventsessions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.taksila.servlet.utils.ServletUtils;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.event_session.v1_0.JoinEventSessionRequest;
import com.taksila.veda.model.api.event_session.v1_0.JoinEventSessionResponse;
import com.taksila.veda.model.api.event_session.v1_0.StartEventSessionRequest;
import com.taksila.veda.model.api.event_session.v1_0.StartEventSessionResponse;
import com.taksila.veda.security.SecurityUtils;
import com.taksila.veda.utils.CommonUtils;

@Path("/eventsessions")
public class ClassroomSessionService 
{
	static Logger logger = LogManager.getLogger(ClassroomSessionService.class.getName());	
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	SecurityUtils securityUtils;
			
	/**
	 * 	 
	 */
	@GET	
	@Produces(MediaType.APPLICATION_JSON)	
	@ManagedAsync
	@Path("/start/{eventScheduleid}")
    public void startEventSession(@Context HttpServletRequest request,@Context HttpServletResponse response,
    		@PathParam("eventScheduleid") String eventScheduleid,    	
    		@Context UriInfo uri,	
    		@Suspended final AsyncResponse asyncResp) 
    {    	
		String tenantId = ServletUtils.getSubDomain(uri);
		ClassRoomSessionComponent classRoomSessionComponent = applicationContext.getBean(ClassRoomSessionComponent.class,tenantId);
		
		StartEventSessionResponse startEventSessionResponse = new StartEventSessionResponse();
		
		logger.trace("processing startEventSession ..");
		try 
		{
			String principalUserId = securityUtils.getLoggedInPrincipalUserid(tenantId, request);
									
			StartEventSessionRequest startEventSessionRequest = new StartEventSessionRequest();
			startEventSessionRequest.setUserRecordId(principalUserId);
			startEventSessionRequest.setEventScheduleId(eventScheduleid);
			logger.trace("about to start a session = "+CommonUtils.toJson(startEventSessionRequest)+" tenantid = "+tenantId);
			startEventSessionResponse = classRoomSessionComponent.startEventSession(startEventSessionRequest);
			logger.trace("response from session start = "+CommonUtils.toJson(startEventSessionResponse));
			if (startEventSessionResponse.getErrorInfo() != null && 
				startEventSessionResponse.getErrorInfo().getErrors() != null &&
				startEventSessionResponse.getErrorInfo().getErrors().isEmpty())
			{
				startEventSessionResponse.setSuccess(true);
				startEventSessionResponse.setStatus(StatusType.SUCCESS);
			}
			else
			{
				startEventSessionResponse.setSuccess(false);
				startEventSessionResponse.setStatus(StatusType.INVALID);
			}	
		} 
		catch (Exception ex) 
		{		
			ex.printStackTrace();
			CommonUtils.handleExceptionForResponse(startEventSessionResponse, ex);
		}
		
		asyncResp.resume(Response.ok(startEventSessionResponse).build());

    }
	
	
	/**
	 * 	 
	 */
	@GET	
	@Produces(MediaType.APPLICATION_JSON)	
	@ManagedAsync
	@Path("/join/{eventSessionId}")
    public void joinEventSession(@Context HttpServletRequest request,@Context HttpServletResponse response,
    		@PathParam("eventSessionId") String eventSessionId,    	
    		@Context UriInfo uri,	
    		@Suspended final AsyncResponse asyncResp) 
    {    	
		String tenantId = ServletUtils.getSubDomain(uri);
		ClassRoomSessionComponent classRoomSessionComponent = applicationContext.getBean(ClassRoomSessionComponent.class,tenantId);		
//		UserComponent userComp = applicationContext.getBean(UserComponent.class,tenantId);
//		GetUserResponse getUserResp = userComp.getUser(userid);
//		if(getUserResp == null || getUserResp.getUser() == null)
//		{
//			throw new Exception("SECURITY VIOLATION! - Unauthenticated userid = "+userid);			
//		}
		JoinEventSessionResponse joinEventSessionResponse = new JoinEventSessionResponse();
		
		logger.trace("processing joinEventSession ..");
		try 
		{
			String userid = securityUtils.getLoggedInPrincipalUserid(tenantId, request);			
			JoinEventSessionRequest joinEventSessionRequest = new JoinEventSessionRequest();
			joinEventSessionRequest.setUserRecordId(userid);
			joinEventSessionRequest.setEventSessionId(eventSessionId);
			logger.trace("about to join a session = "+CommonUtils.toJson(joinEventSessionRequest)+" tenantid = "+tenantId);			
			joinEventSessionResponse = classRoomSessionComponent.joinSession(joinEventSessionRequest);
			logger.trace("response from session join = "+CommonUtils.toJson(joinEventSessionResponse));
			if (joinEventSessionResponse.getErrorInfo() != null && 
					joinEventSessionResponse.getErrorInfo().getErrors() != null &&
							joinEventSessionResponse.getErrorInfo().getErrors().isEmpty())
			{
				joinEventSessionResponse.setSuccess(true);
				joinEventSessionResponse.setStatus(StatusType.SUCCESS);				
			}
			else
			{
				joinEventSessionResponse.setSuccess(false);
				joinEventSessionResponse.setStatus(StatusType.INVALID);				
			}	
			
		} 
		catch (Exception ex) 
		{		
			ex.printStackTrace();
			CommonUtils.handleExceptionForResponse(joinEventSessionResponse, ex);
		}
		
		asyncResp.resume(Response.ok(joinEventSessionResponse).build());

    }
	
	
	
	
	
	
	
	

	
}
