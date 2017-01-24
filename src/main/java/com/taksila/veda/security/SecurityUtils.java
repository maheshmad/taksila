package com.taksila.veda.security;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taksila.veda.model.db.usermgmt.v1_0.User;

@Component
public class SecurityUtils 
{
	static Logger logger = LogManager.getLogger(SecurityUtils.class);
	
	@Autowired
	ApplicationContext applicationContext;
	
	
	public User getLoggedInPrincipal(String tenantId, String sessionid) throws Exception
	{
		if (sessionid == null)
		{
//			logger.trace("user = "+requestContext.getSecurityContext().getUserPrincipal().getName());
			UserAuthComponent userAuth = applicationContext.getBean(UserAuthComponent.class,tenantId);
			if (userAuth.getLoggedInUser(sessionid) != null)
				return userAuth.getLoggedInUser(sessionid).getUserInfo();
			else
				return null;
			
//			return requestContext.getSecurityContext().getUserPrincipal();
		}
		else
		{
			logger.trace("***** User session not found ***********");
			throw new Exception("User not logged in");
		}
			
	}
	
//	public User getLoggedInPrincipal(String tenantId, HttpServletRequest request) throws Exception
//	{
//		String usersessionid = ServletUtils.getCookie(UserAuthService.USER_AUTH_SESSION_COOKIE_NAME, request);
//		return getLoggedInPrincipal(tenantId, usersessionid);
//			
//	}
	
	public String getLoggedInPrincipalUserid(String tenantId, HttpServletRequest request) throws Exception
	{
//		return requestContext.getSecurityContext().getUserPrincipal().getName();
//		User user = getLoggedInPrincipal(tenantId, request);
//		if (user != null)
//		{
//			System.out.println("***** Logged in userid found = "+user.getUserId()+" *************");
//			return user.getUserId();
//		}
//		else
		Principal p = (Principal) request.getAttribute("principaluser");
		logger.trace("***** User found from servlet context = "+p.getName());
		return p.getName();

		
//			return "";
			
	}
	
}
