package com.taksila.veda.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class TenantConfigManager
{
	private static Logger logger = LogManager.getLogger(TenantConfigManager.class.getName());
	
	@Autowired
	ApplicationContext applicationContext;
	String tenantId;
	
	@Autowired
	public TenantConfigManager(@Value("tenantId")  String tenantId) 
	{
		logger.trace(">>>>>>>>>>>>>>>> Creating TenantConfigManager bean for tenant "+tenantId);		
		this.tenantId = tenantId;
	}
	
	private Map<String, ConfigComponent> configMap = new HashMap<String,ConfigComponent>();
		
	public ConfigComponent getTenantConfig()
	{
		if (configMap.get(this.tenantId) == null)
		{			
			ConfigComponent configComp = applicationContext.getBean(ConfigComponent.class,tenantId);
			configMap.put(tenantId, configComp);
		}
		
		return configMap.get(tenantId);
	}
	
	
}
