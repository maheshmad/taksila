package com.taksila.veda.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taksila.servlet.utils.ServletUtils;
import com.taksila.veda.model.api.base.v1_0.BaseResponse;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.security.v1_0.ResetPasswordResponse;
import com.taksila.veda.model.api.security.v1_0.UserLoginResponse;
import com.taksila.veda.utils.CommonUtils;

@Component
@Path("/auth")
public class UserAuthService 
{
	static Logger logger = LogManager.getLogger(UserAuthService.class.getName());
	public static final String USER_AUTH_SESSION_COOKIE_NAME = "x-auth-cookie";
	public static final String USER_AUTH_SESSION_ATTR = "user-auth-info";
	Client client = ClientBuilder.newClient();   
	
	@Autowired
	ApplicationContext applicationContext;
		
	/*
	 * Login using local LDAP without oauth
	 */
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ManagedAsync
    public void post(@FormParam("user") String userid, @Context HttpServletRequest request,@Context HttpServletResponse response,
    		@Context UriInfo uri,@FormParam("pwd") String password,@Suspended final AsyncResponse asyncResp) 
    {    	
		UserLoginResponse loginResp = new UserLoginResponse();
		try 
		{
			logger.trace("About to authenticate ");
			String tenantId = ServletUtils.getSubDomain(uri.getBaseUri());
			JwtEndClientDevice jwtEndClientDevice = new JwtEndClientDevice(request);
			/*
			 * get user info
			 */			
			UserAuthComponent userAuthComponent = applicationContext.getBean(UserAuthComponent.class,tenantId);
			loginResp = userAuthComponent.authenticate(userid,password,jwtEndClientDevice);
			logger.trace(CommonUtils.toJson(loginResp));
			loginResp.setSuccess(true);
			if (loginResp.getErrorInfo() != null)
			{			
				asyncResp.resume(Response.status(403).entity(loginResp).build());
			}
			else
			{								
				NewCookie cookie = new NewCookie(USER_AUTH_SESSION_COOKIE_NAME, loginResp.getJwtToken(),"/veda", "", "authorization cookie", 1000000, false);
				asyncResp.resume(Response.ok(loginResp).cookie(cookie).build());
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			loginResp.setStatus(StatusType.EXCEPTION);
			loginResp.setErrorInfo(CommonUtils.buildErrorInfo(e));
			loginResp.setMsg(e.getMessage());			
		}
		
		loginResp.setSuccess(true);
		asyncResp.resume(Response.ok(loginResp).build());
		        
    }
	
	/*
	 * Login using local LDAP without oauth
	 */
	@POST
	@Path("/updatepassword")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ManagedAsync
    public void changePassword(	@FormParam("newpswd") String newpassword,
    		@FormParam("newpswdconfirm") String confirmpassword,
    		@FormParam("pswdChgAuthToken") String authSessionToken,
    		@Context HttpServletRequest request,
    		@Context HttpServletResponse response,
    		@Context UriInfo uri,    		
    		@Suspended final AsyncResponse asyncResp) 
    {    	
		ResetPasswordResponse chgPswdResp = new ResetPasswordResponse();
		try 
		{
			logger.trace("About to authenticate ");			
			String tenantId = ServletUtils.getSubDomain(uri.getBaseUri());				
			chgPswdResp.setSuccess(true);
			/*
			 * get user info
			 */			
			UserAuthComponent userAuthComponent = applicationContext.getBean(UserAuthComponent.class,tenantId);
			chgPswdResp = userAuthComponent.changePassword(authSessionToken, newpassword, confirmpassword);
			logger.trace(CommonUtils.toJson(chgPswdResp));	
			chgPswdResp.setSuccess(true);
			asyncResp.resume(Response.ok(chgPswdResp).build());
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			CommonUtils.handleExceptionForResponse(chgPswdResp, e);		
		}
		
		chgPswdResp.setSuccess(true);
		asyncResp.resume(Response.ok(chgPswdResp).build());
		        
    }
	
	/*
	 * Login using local LDAP without oauth
	 */
	@POST
	@Path("/forgotpassword")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ManagedAsync
    public void resetPassword(	@FormParam("email") String emailid,    		
    		@Context HttpServletRequest request,
    		@Context HttpServletResponse response,
    		@Context UriInfo uri,    		
    		@Suspended final AsyncResponse asyncResp) 
    {    	
		ResetPasswordResponse resetPswdResp = new ResetPasswordResponse();
		try 
		{
			logger.trace("About to reset the password for user = "+emailid);			
			String tenantId = ServletUtils.getSubDomain(uri.getBaseUri());				
			resetPswdResp.setSuccess(true);
			/*
			 * get user info
			 */			
			UserAuthComponent userAuthComponent = applicationContext.getBean(UserAuthComponent.class,tenantId);
			resetPswdResp = userAuthComponent.emailPasswordResetLink(emailid);
//			logger.trace(CommonUtils.toJson(resetPswdResp));	
			resetPswdResp.setSuccess(true);			
			asyncResp.resume(Response.ok(resetPswdResp).build());
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			CommonUtils.handleExceptionForResponse(resetPswdResp, e);		
		}
		
		resetPswdResp.setSuccess(true);
		asyncResp.resume(Response.ok(resetPswdResp).build());
		        
    }
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	@Path("/user")
	public void getLoggedInUserInfo(@Context HttpServletRequest request, @Context UriInfo uri,	@Context HttpServletResponse resp,
			@CookieParam(USER_AUTH_SESSION_COOKIE_NAME) Cookie authCookie,@Suspended final AsyncResponse asyncResp)
	{    				
		
		if (authCookie == null)
			asyncResp.resume(Response.status(403).type("text/plain").entity("No auth cookie found! Please make sure the cookie feature is enabled").build());
		
		String jwtCookie = authCookie.getValue();				
		String tenantId = ServletUtils.getSubDomain(uri.getBaseUri());				
		/*
		 * get user info
		 */
		UserAuthComponent userAuthComponent = applicationContext.getBean(UserAuthComponent.class,tenantId);
		UserLoginResponse userInfoResp = userAuthComponent.getLoggedInUser(jwtCookie);		
		
		if (userInfoResp.getErrorInfo() != null || userInfoResp.getSessionInfo() == null)
		{			
			userInfoResp.setStatus(StatusType.INVALID);
			userInfoResp.setSuccess(false);
			asyncResp.resume(Response.status(403).entity(userInfoResp).build());
		}
		else
		{			
			request.getSession(true);
			userInfoResp.setStatus(StatusType.SUCCESS);
			userInfoResp.setSuccess(true);
			asyncResp.resume(Response.ok(userInfoResp).build());
		}		
		
	}
	
	
	@GET
	@Path("/user/profileimage")
	@Produces("image/*")
	public Response getUserImage(@Context HttpServletRequest request,@Context UriInfo uri,
			@Context HttpServletResponse resp,@CookieParam(USER_AUTH_SESSION_COOKIE_NAME) Cookie authCookie,@Suspended final AsyncResponse asyncResp)
	{    						
		logger.trace("Inside getUserImage service!!!!!");
		String tenantId = ServletUtils.getSubDomain(uri.getBaseUri());		
		byte[] userphoto = CommonUtils.readImageFile("defaultprofileimage-128.png");
			
		return Response.ok(userphoto).build();
				
	}
				
	/*
	 * Logoff user
	 */
	@POST
	@Path("/logoff")
	@Produces(MediaType.APPLICATION_JSON)	
    public void post(@Context HttpServletRequest request,@CookieParam(USER_AUTH_SESSION_COOKIE_NAME) Cookie authCookie,@Context UriInfo uri,@Suspended final AsyncResponse asyncResp) 
    {    			
        logger.trace("received logout request for session");
        BaseResponse resp = new BaseResponse();
        resp.setSuccess(true);
    	try
		{        	    		    		    				  	    		
    		if (authCookie != null)
    		{	    		
	    		String authSessionId = authCookie.getValue();				
	    		String tenantId = ServletUtils.getSubDomain(uri.getBaseUri());				
	    		/*
	    		 * get user info
	    		 */
	    		UserAuthComponent userAuthComponent = applicationContext.getBean(UserAuthComponent.class,tenantId);  		
	    		boolean logoutResp = userAuthComponent.logout(authSessionId);
	    		if (logoutResp)
	    		{
	    			resp.setStatus(StatusType.SUCCESS);
	    			resp.setMsg("Successfully logged out session = "+authSessionId);	
	    		}
	    		else
	    			resp.setStatus(StatusType.FAILED);
	    		
    		}
    		else
    		{
    			resp.setStatus(StatusType.INVALID);
    			resp.setMsg("No authorization cookie found! Operation is invalid");	       			
    		}
	    		
		}    	
		catch (Exception e)
		{
			e.printStackTrace();
			resp.setStatus(StatusType.EXCEPTION);
			resp.setMsg("No authorization cookie found! Operation is invalid");			
		}
		    	
		asyncResp.resume(Response.ok(resp).build());
        
    }
	

	
	
}
