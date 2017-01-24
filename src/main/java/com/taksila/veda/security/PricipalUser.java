package com.taksila.veda.security;

import java.security.Principal;

import com.taksila.veda.model.db.usermgmt.v1_0.User;

public class PricipalUser extends User implements Principal
{

	@Override
	public String getName() 
	{	
		return this.getUserId();
	}
	
}
