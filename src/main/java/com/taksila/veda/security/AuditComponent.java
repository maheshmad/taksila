package com.taksila.veda.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;

public class AuditComponent 
{
	public AuditComponent(@Value("tenantId") String tenantId)
	{
		
	}
	
	public String auditRequest(HttpServletRequest webRequest, String requestBody) 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
