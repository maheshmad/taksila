package com.taksila.veda.config;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.taksila.veda.db.dao.ConfigDAO;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.config.v1_0.GetConfigurationRequest;
import com.taksila.veda.model.api.config.v1_0.GetConfigurationResponse;
import com.taksila.veda.model.api.config.v1_0.UpdateConfigRequest;
import com.taksila.veda.model.api.config.v1_0.UpdateConfigResponse;
import com.taksila.veda.model.db.config.v1_0.ConfigId;
import com.taksila.veda.utils.CommonUtils;

@Component
@Lazy(value = true)
public class ConfigComponent 
{	
	private static Logger logger = LogManager.getLogger(ConfigComponent.class.getName());
	Map<ConfigId, String> configsMap = null;	
	String tenantId;
	
	@Autowired
	ApplicationContext applicationContext;
	
	ConfigDAO configDAO;
		
	public ConfigComponent(String tenantId) 
	{
		logger.trace("About to load configuration from database for tenantid = "+tenantId);		
		this.tenantId = tenantId;
	}
	
	public String getConfig(ConfigId key)
	{
		if (configsMap == null || configsMap.isEmpty())
		{
			loadConfigs(true);
			logger.trace("About to load configuration from database");			
		}
					
		if (configsMap.get(key) == null)
			CommonUtils.logEyeCatchingMessage("Config KEY = "+key.toString()+" Not found, please check your config file", true);	
		else
		{
			logger.trace("Accessing config = "+key.name()+ " = "+configsMap.get(key).trim());
			return configsMap.get(key).trim();
		}

		return null;
	}

	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public GetConfigurationResponse getConfigSection(GetConfigurationRequest request)
	{
		GetConfigurationResponse resp = new GetConfigurationResponse();
		resp.setForRole(request.getForRole());
		
		ConfigDAO configDAO = applicationContext.getBean(ConfigDAO.class,request);
		
		try 
		{
			resp.getSections().addAll(configDAO.getConfigSectionsByRole(request.getForRole()));
			resp.setSuccess(true);
		} 
		catch (Exception e) 
		{			
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;

	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public UpdateConfigResponse updateConfig(UpdateConfigRequest request)
	{
		UpdateConfigResponse resp = new UpdateConfigResponse();
//		ConfigDAO configDAO = new ConfigDAO();
		
		try 
		{
			boolean success = configDAO.updateConfigs(request.getConfigs());
			resp.setMsg("Configuration updated !!");
			if (success)			
				resp.setStatus(StatusType.SUCCESS);							
			else
			{	
				resp.setStatus(StatusType.FAILED);
				resp.setMsg("Some or All of config updates failed! Please try again or check your inputs");
			}
		} 
		catch (Exception e) 
		{			
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;
	}
	
	
	private synchronized void loadConfigs(boolean isLatestUpdateRequired)
	{
		try
		{
//			ConfigDAO configDAO = new ConfigDAO();
			ConfigDAO configDAO = applicationContext.getBean(ConfigDAO.class,tenantId);
			configsMap = configDAO.getConfigsByRole(null);			
		}		 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getUserTempFilePath(String type, String additionalFolderId) 
	{
		String basePath = getConfig(ConfigId.TEMP_FILE_PATH);
		basePath = StringUtils.removeEnd(basePath, "\\");
		String dirPath = getConfig(ConfigId.TEMP_FILE_PATH)+"\\"+type+"\\";
		if (StringUtils.isNotBlank(additionalFolderId))
			dirPath = dirPath + additionalFolderId+"\\";
		boolean dirExits = new File(dirPath).mkdirs();
		return dirPath;  
			
	}
	
}
