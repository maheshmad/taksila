package com.taksila.veda.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuditComponent 
{
	static Logger logger = LogManager.getLogger(AuditComponent.class.getName());
	@Autowired
	public AuditComponent(@Value("tenantId") String tenantId)
	{
		logger.trace(">>>>>>>>>>>>>>>> Creating AuditComponent bean for tenant "+tenantId);		
	}
	
	public String auditRequest(HttpServletRequest webRequest, String requestBody) 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
