package com.taksila.veda.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;



public class VedaSecurityContext implements SecurityContext 
{
    private PricipalUser user;
    private String scheme;
 
    public VedaSecurityContext(PricipalUser user, String scheme) 
    {
        this.user = user;
        this.scheme = scheme;
    }
 
    @Override
    public Principal getUserPrincipal() 
    {
    	return this.user;
    }
 
    @Override
    public boolean isUserInRole(String s) 
    {
        if (user.getUserRoles() != null) 
        {
            return user.getUserRoles().contains(s);
        }
        return false;
    }
 
    @Override
    public boolean isSecure() {return "https".equals(this.scheme);}
 
    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
