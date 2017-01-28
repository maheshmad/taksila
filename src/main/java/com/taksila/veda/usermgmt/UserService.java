package com.taksila.veda.usermgmt;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

import org.apache.commons.codec.binary.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taksila.servlet.utils.ServletUtils;
import com.taksila.veda.eventschedulemgmt.EventScheduleMgmtComponent;
import com.taksila.veda.model.api.base.v1_0.AllowedActionsRequest;
import com.taksila.veda.model.api.base.v1_0.AllowedActionsResponse;
import com.taksila.veda.model.api.base.v1_0.RecordType;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.usermgmt.v1_0.CreateNewUserResponse;
import com.taksila.veda.model.api.usermgmt.v1_0.DeleteUserRequest;
import com.taksila.veda.model.api.usermgmt.v1_0.DeleteUserResponse;
import com.taksila.veda.model.api.usermgmt.v1_0.GetUserResponse;
import com.taksila.veda.model.api.usermgmt.v1_0.SearchUserRequest;
import com.taksila.veda.model.api.usermgmt.v1_0.SearchUserResponse;
import com.taksila.veda.model.api.usermgmt.v1_0.UpdateUserResponse;
import com.taksila.veda.utils.CommonUtils;

@Component
@Path("/user")
public class UserService 
{
	static Logger logger = LogManager.getLogger(UserService.class.getName());	
	Executor executor;
	
	@Autowired
	ApplicationContext applicationContext;
	
   public UserService() 
   {
      executor = Executors.newSingleThreadExecutor();
   }
	
	
		
	/**
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param title
	 * @param subtitle
	 * @param description
	 * @param uri
	 * @param asyncResp
	 */
	@POST	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ManagedAsync
    public void createNewUser(@Context HttpServletRequest request,@Context HttpServletResponse response,
    		@Context final UriInfo uri,	
    		@Suspended final AsyncResponse asyncResp) 
    {    	
		
		executor.execute(new Runnable() 
		{
			public void run() 
			{	
				String tenantId = ServletUtils.getSubDomain(uri);				
				CreateNewUserResponse operResp = new CreateNewUserResponse();
				try 
				{					
					MultivaluedMap<String, String> formParams= CommonUtils.getMultivaluedMap(request.getParameterMap());
					UserComponent userComp = applicationContext.getBean(UserComponent.class,tenantId);						
					operResp = userComp.createNewUser(formParams); 
					if (operResp.getErrorInfo() == null)
						operResp.setSuccess(true);
					else
						operResp.setSuccess(false);
				} 
				catch (Exception ex) 
				{		
					CommonUtils.handleExceptionForResponse(operResp, ex);
					operResp.setSuccess(false);
				}
				
				asyncResp.resume(Response.ok(operResp).build());
			}
		});
		
		logger.trace("********  exiting createNewUser service ");


    }
	
	/**
	 * 
	 * @param request
	 * @param uri
	 * @param userid
	 * @param resp
	 * @param asyncResp
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	@Path("/{id}")
	public void getUser(@Context HttpServletRequest request, @Context final UriInfo uri,		
			@PathParam("id") final int id,
			@Context HttpServletResponse resp,
			@Suspended final AsyncResponse asyncResp)
	{    				
		
		executor.execute(new Runnable() 
		{
			public void run() 
			{	
				GetUserResponse operResp = new GetUserResponse();
				try 
				{
					String tenantId = ServletUtils.getSubDomain(uri);
					UserComponent userComp = applicationContext.getBean(UserComponent.class,tenantId);						
					operResp = userComp.getUser(id); 			
					operResp.setSuccess(true);
				} 
				catch (Exception ex) 
				{		
					ex.printStackTrace();
					CommonUtils.handleExceptionForResponse(operResp, ex);
				}
				
				asyncResp.resume(Response.ok(operResp).build());
			}
		});
		
		
		logger.trace("********  exiting getUser service ");
		
	}
	
			
	/**
	 * 
	 * @param request
	 * @param uri
	 * @param userid
	 * @param resp
	 * @param asyncResp
	 */
	@GET
	@Produces("image/*")
	@ManagedAsync
	@Path("/image/{scale}/{imageid}")
	public void getUserImage(@Context HttpServletRequest request, 
			@Context final UriInfo uri,		
			@PathParam("imageid") final String imageid,
			@PathParam("scale") final String scale,
			@Context HttpServletResponse resp,
			@Suspended final AsyncResponse asyncResp)
	{    				
		
		executor.execute(new Runnable() 
		{
			public void run() 
			{					
				ByteArrayOutputStream operResp = null;
				try 
				{										
					String tenantId = ServletUtils.getSubDomain(uri);
					UserComponent userComp = applicationContext.getBean(UserComponent.class,tenantId);						
					if (StringUtils.equals(scale, "thumb"))
						operResp = userComp.getImage(imageid, 0.5);
					else
						operResp = userComp.getImage(imageid, 1);
				} 
				catch (Exception ex) 
				{		
					ex.printStackTrace();
				}
				
				if (operResp != null)
				{
					logger.trace("found image ....sending it back ");
					asyncResp.resume(Response.ok(operResp.toByteArray()).build());
				}
				else				
					Response.ok(CommonUtils.readImageFile("defaultprofileimage-128.png")).build();
				
			}
		});
		
		
		logger.trace("********  exiting getUserImage service ");
		
	}
	
	
	/**
	 * 
	 * @param request
	 * @param uri
	 * @param name
	 * @param userid
	 * @param title
	 * @param subtitle
	 * @param description
	 * @param resp
	 * @param asyncResp
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ManagedAsync
	@Path("/{id}")
	public void updateUser(@Context HttpServletRequest request, @Context final UriInfo uri,				
			@PathParam("id") final String id, 
			@Context HttpServletResponse resp,@Suspended final AsyncResponse asyncResp)
	{    				
		MultivaluedMap<String, String> formParams= CommonUtils.getMultivaluedMap(request.getParameterMap());	
		executor.execute(new Runnable() 
		{
			public void run() 
			{	
				logger.trace("about to update the user id = "+id);
				UpdateUserResponse operResp = new UpdateUserResponse();
				String tenantId = ServletUtils.getSubDomain(uri);
				UserComponent userComp = applicationContext.getBean(UserComponent.class,tenantId);						
				
				try 
				{											
					operResp = userComp.updateUser(id, formParams); 			
					operResp.setSuccess(true);
				} 
				catch (Exception ex) 
				{		
					ex.printStackTrace();
					CommonUtils.handleExceptionForResponse(operResp, ex);
				}
				
				logger.trace("************ exiting updateUser() in service");
				asyncResp.resume(Response.ok(operResp).build());
			}
		});
		
				
	}
	
	/**
	 * 
	 * @param request
	 * @param uri
	 * @param userid
	 * @param resp
	 * @param asyncResp
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ManagedAsync
	@Path("/{userid}")
	public void deleteUser(@Context HttpServletRequest request, 
			@Context final UriInfo uri,	
			@PathParam("userid") final String userid,			
			@Context HttpServletResponse resp,
			@Suspended final AsyncResponse asyncResp)
	{    				
		executor.execute(new Runnable() 
		{
			public void run() 
			{		
				DeleteUserResponse operResp = new DeleteUserResponse();
				try
				{
					logger.trace("About to delete user record = "+userid);						
					
					String tenantId = ServletUtils.getSubDomain(uri);
					UserComponent userComp = applicationContext.getBean(UserComponent.class,tenantId);						
					DeleteUserRequest req = new DeleteUserRequest();
					req.setId(userid);
					operResp = userComp.deleteUser(req);
					operResp.setSuccess(true);
				}
				catch(Exception ex)
				{
					CommonUtils.handleExceptionForResponse(operResp, ex);
				}
				
				asyncResp.resume(Response.ok(operResp).build());
			}
		});
		
		logger.trace("************ exiting deleteUser() in service");
		
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
	public void searchUsers(@Context HttpServletRequest request, 
			@Context final UriInfo uri,
			@Context HttpServletResponse resp,
			@QueryParam("q") final String name,
			@QueryParam("page") final String page,
			@QueryParam("start") final String start, 
			@Suspended final AsyncResponse asyncResp)
	{    				
		
		logger.trace("inside search query = "+name);
		executor.execute(new Runnable() 
		{
			public void run() 
			{	
				SearchUserResponse searchResp = new SearchUserResponse();		
				SearchUserRequest req = new SearchUserRequest();
				try 
				{					
					req.setPage(page == null?1:Integer.valueOf(page));
					req.setPageOffset(start == null ? 1: Integer.valueOf(start));		
					req.setQuery(name == null ? "": name);
					req.setRecordType("USERS");
					
					String tenantId = ServletUtils.getSubDomain(uri);
					UserComponent userComp = applicationContext.getBean(UserComponent.class,tenantId);						
					searchResp = userComp.searchUsers(req);
					searchResp.setStatus(StatusType.SUCCESS);;
				} 
				catch (Exception e) 
				{								
					CommonUtils.handleExceptionForResponse(searchResp, e);
				}
				
				searchResp.setSuccess(true);
				asyncResp.resume(Response.ok(searchResp).build());
			}
		});
		
		logger.trace("************ exiting searchUser() in service");
		
		
	}
	
	
	/**
	 * 	
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	@Path("/allowedactions/{eventRecordType}/{eventRecordid}")
	public void getActions(@Context HttpServletRequest request, 
			@PathParam("eventRecordid") String eventRecordid,
			@PathParam("eventRecordType") String eventRecordType,
			@Context UriInfo uri,@Context HttpServletResponse resp,			 
			@Suspended final AsyncResponse asyncResp)
	{    						
		logger.trace("inside user allowed actions service");		
		String tenantId = ServletUtils.getSubDomain(uri);
		EventScheduleMgmtComponent eventScheduleComp = applicationContext.getBean(EventScheduleMgmtComponent.class,tenantId);

		AllowedActionsResponse allowedActionsResp = new AllowedActionsResponse();
		
		try 
		{			
			AllowedActionsRequest req = new AllowedActionsRequest();
			req.setRecordId(eventRecordid);
			req.setRecordType(RecordType.fromValue(eventRecordType.toUpperCase()));
			allowedActionsResp = eventScheduleComp.getAllowedActions(req);		
			allowedActionsResp.setSuccess(true);						
		} 
		catch (Exception e) 
		{			
			e.printStackTrace();
			CommonUtils.handleExceptionForResponse(allowedActionsResp, e);
		}
				
		asyncResp.resume(Response.ok(allowedActionsResp).build());
		
	}
	
	
	

	
}
