package com.taksila.servlet.utils;

import java.net.URI;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ServletUtils 
{
	static Logger logger = LogManager.getLogger(ServletUtils.class.getName());

	public static String getClientIpAddr(HttpServletRequest request) 
	 {  
	        String ip = request.getHeader("X-Forwarded-For");  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("WL-Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_CLIENT_IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getRemoteAddr();  
	        }  
	        return ip;  
	 }
	 
	public static String getFormParamString(String parmname, javax.ws.rs.core.MultivaluedMap<String, String> formParams) 
	{
		if (formParams == null || formParams.isEmpty() || !formParams.containsKey(parmname))
			return "";
		
		if (formParams.get(parmname) == null || formParams.get(parmname).isEmpty())
			return "";
		else			
			return formParams.get(parmname).get(0);
	}
	

	
	public static String getSubDomain(UriInfo uriInfo)
	 {	      
		 return getSubDomain(uriInfo.getRequestUri());
	      
	 }
	

	
	 public static String getSubDomain(URI url)
	 {	      
		 return getSubDomain(url.toString());
	      
	 }
	 

	 
	 
	 
	 public static String getSubDomain(String url)
	 {	      
//		 logger.trace(" getSubDomain = "+url);  
        String host = StringUtils.removeStartIgnoreCase( url,"https://");
        host = StringUtils.removeStartIgnoreCase(host,"http://");
                        
//        logger.trace(" request host for split = "+host);
        if (host == null)
        	return null;            
        String[] p = host.split("\\.");       
//        logger.trace(" host variable split by period len = "+p.length);       
        String tenantDomain = null;
        for(int i=0;i<p.length;i++)
        {
	        if ("www".equals(p[i]) ||  /* ignore www */
	        	"dev".equals(p[i]) ||   /* ignore dev / stag / test region domain route */
	        	"test".equals(p[i]) ||	        	
	        	"stag".equals(p[i]))
	        	continue;
	        else if ("127".equals(p[i]) && StringUtils.contains(host,"127.0.0.1"))
	        {
	        	tenantDomain = "cloud"; /* to facilitate development on localhost */
	        	break;
	        }	
	        else
	        {
	        	tenantDomain = p[i];
	        	break;
	        }
        }
        
//        logger.trace(" Domain = "+tenantDomain);  
        
        return tenantDomain;
	      
	 }
	 
	 
	 public static String getDomainUrl(String url)
	 {	      
		 if(StringUtils.isNotBlank(url)) {
			 return url.substring(0, url.indexOf("/", url.indexOf("//") + 3));
		 }
	      
		 return "";
	 }
	 
	
	 /**
	  * gets cookie from servlet request
	  * 
	  * @param cookiename
	  * @param request
	  * @return
	  */
	 public static String getCookie(String cookiename, HttpServletRequest request)
	 {		 		 
		 if (request.getCookies() == null)
			 return "";
		 
		 for (Cookie cookie: request.getCookies())
		 {
			if (StringUtils.equals(cookie.getName(),cookiename))
			{
				return cookie.getValue();
			}
		 }
		 		 		 
		 return "";
	 }
	 
	 /*
	  * get file name from http
	  */
	 public static String getFileName(final Part part) 
	 {
	        final String partHeader = part.getHeader("content-disposition");
	        logger.trace( "Part Header = {0}", partHeader);
	        for (String content : part.getHeader("content-disposition").split(";")) 
	        {
	            if (content.trim().startsWith("filename")) 
	            {
	                String name = content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
	                name = name.replaceAll(" ", "_"); 
	                return name;
	            }
	        }
	        return null;
	 }
}
