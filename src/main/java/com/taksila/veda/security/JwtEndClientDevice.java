package com.taksila.veda.security;

import javax.servlet.http.HttpServletRequest;

import com.taksila.servlet.utils.ServletUtils;
import com.taksila.veda.utils.UAgentInfo;

public class JwtEndClientDevice
{
	String ipAddress;
	Platform platForm;
	String userAgent;
	
	public enum Platform
	{
		TABLET,
		BROWSER,
		MOBILE
	}
	
	public JwtEndClientDevice(HttpServletRequest request)
	{
		UAgentInfo detector = new UAgentInfo(request.getHeader("User-Agent"), request.getHeader("Accept"));
		
		this.ipAddress = ServletUtils.getClientIpAddr(request);		
		if (detector.isTierIphone)
			this.platForm = Platform.MOBILE;
		if (detector.getIsTierTablet())
			this.platForm = Platform.TABLET;
		if (detector.detectWebkit())
			this.platForm = Platform.BROWSER;
	}
	
}
