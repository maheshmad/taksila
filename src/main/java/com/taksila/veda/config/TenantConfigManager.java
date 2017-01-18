package com.taksila.veda.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TenantConfigManager
{
	@Autowired
	ApplicationContext applicationContext;
	
	private Map<String, ConfigComponent> configMap = new HashMap<String,ConfigComponent>();
		
	public ConfigComponent getTenantConfig(String tenantId)
	{
		if (configMap.get(tenantId) == null)
		{			
			ConfigComponent configComp = applicationContext.getBean(ConfigComponent.class,tenantId);
			configMap.put(tenantId, configComp);
		}
		
		return configMap.get(tenantId);
	}
	
	
}
