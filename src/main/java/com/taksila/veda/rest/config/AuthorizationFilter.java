package com.taksila.veda.rest.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.taksila.servlet.utils.ServletUtils;
import com.taksila.veda.model.db.base.v1_0.UserRole;
import com.taksila.veda.security.JwtTokenUtil;
import com.taksila.veda.security.PricipalUser;
import com.taksila.veda.security.UserAuthService;
import com.taksila.veda.security.VedaSecurityContext;
import com.taksila.veda.utils.CommonUtils;

/**
 * @author Mahesh 
 *
 */
@Provider
@PreMatching
@Priority(4)
@Component
public class AuthorizationFilter implements ContainerRequestFilter
{	
	private static Logger logger = LogManager.getLogger(AuthorizationFilter.class.getName());
	
	private static List<String> skipAuthenticationList = new ArrayList<String>();
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	static
	{
		skipAuthenticationList.add("/login");
		skipAuthenticationList.add("/logout");
		skipAuthenticationList.add("/auth/forgotpassword");
	}
	
	
	@Context
	HttpServletRequest webRequest;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException 
	{		
		/*
		 * check if the request thread authentication should be skipped
		 */
		if (skipAuthentication(webRequest.getRequestURI()))
		{
			logger.trace("Authentication not required for URI = "+webRequest.getRequestURI());
			return;
		}
		else;
		/*
		 * first get the auth token from header, if its null 
		 * check the token passed as a cookie.
		 */
		String jwtToken = requestContext.getHeaders().getFirst(this.tokenHeader);	
		if (jwtToken == null)					
			jwtToken = ServletUtils.getCookie(UserAuthService.USER_AUTH_SESSION_COOKIE_NAME, webRequest);
		
		logger.trace("in AuthorizationFilter.....token = "+jwtToken);
		String requestApi = webRequest.getRequestURI();
		requestApi = requestApi.substring(webRequest.getContextPath().length());
		String method = requestContext.getMethod();
		logger.trace("REQUEST API:::"+requestApi+" Request METHOD::"+method);									
		
		if(jwtToken == null || StringUtils.isBlank(jwtToken)) 
		{
			 throw new WebApplicationException(Status.FORBIDDEN); //ERROR CODE : 403
		}
				
		/*
		 * check authentication
		 */
		boolean accessDenied = true;
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		List<UserRole> roles = jwtTokenUtil.getRolesFromToken(jwtToken);
		logger.info("checking authentication for user for token ....found username = " + username+ " user roles : "+CommonUtils.toJson(roles));
		
		if (username != null)
		{
			accessDenied = false;
			PricipalUser principal = new PricipalUser();
			principal.setUserId(username);
			principal.getUserRoles().addAll(roles);
			SecurityContext sc = new VedaSecurityContext(principal, "");			
			requestContext.setSecurityContext(sc);
			webRequest.setAttribute("principaluser", principal);
		}
						
		if(accessDenied) 
		{
			logger.trace("Could not get user information from the token"+jwtToken);
			throw new WebApplicationException(Status.FORBIDDEN); //ERROR CODE : 403
		}
		
	}
	
	/**
	 * 
	 * @param requestdURL
	 * @return
	 */
	private static boolean skipAuthentication(String requestdURL) 
	{ 
		boolean skipAuthentication = false;
		logger.trace("check the in the skip list "+requestdURL);
		if(CommonUtils.isNotEmpty(requestdURL)) 
		{
			for(int counter = 0; counter < skipAuthenticationList.size(); counter ++) 
			{
				if(requestdURL.contains(skipAuthenticationList.get(counter)))
				{
					skipAuthentication = true;
					break;
				}
			}
		}
		
		return skipAuthentication;
	}

}
