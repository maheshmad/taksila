package com.taksila.veda.security;

import java.util.UUID;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.taksila.veda.db.dao.UserSessionDAO;
import com.taksila.veda.db.dao.UsersDAO;
import com.taksila.veda.email.EmailUtils;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.security.v1_0.ResetPasswordResponse;
import com.taksila.veda.model.api.security.v1_0.UserLoginResponse;
import com.taksila.veda.model.db.security.v1_0.UserSession;
import com.taksila.veda.model.db.usermgmt.v1_0.User;
import com.taksila.veda.utils.CommonUtils;
import com.taksila.veda.utils.SysAdminConfig;

@Component
@Scope(value="prototype")
public class UserAuthComponent  
{	
	@Autowired
	ApplicationContext applicationContext;
		
	@Autowired
	JwtTokenUtil jwtTokenUtil;
			
	private String dateFormat = "MM/dd/yyyy HH:mm:ss z";
	static Logger logger = LogManager.getLogger(UserAuthComponent.class.getName());
	
	private String tenantId;
	
	@Autowired
	public UserAuthComponent(@Value("tenantId") String tenantId) 
	{
		logger.trace(">>>>>>>>>>>>>>>> Creating UserAuthComponent bean for tenant "+tenantId);		
		this.tenantId = tenantId;		
	}
	
	public UserLoginResponse getLoggedInUser(String sessionid)
	{
		UserSessionDAO userSessionDAO = applicationContext.getBean(UserSessionDAO.class,tenantId);	
		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);	

		UserLoginResponse resp = new UserLoginResponse();
		try 
		{
			UserSession userSession = userSessionDAO.getValidSession(sessionid);
			if (userSession != null)
			{
				User userInfo = usersDAO.getUserByUserId(userSession.getUserId());
				resp.setUserInfo(userInfo);
				resp.setSessionInfo(userSession);
			}			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;
	}
	
	/**
	 * 
	 * @param userId
	 * @param authTokenId
	 * @param newPassword
	 * @param confirmpassword
	 * @return
	 */
	public ResetPasswordResponse changePassword(String authTokenId,String newPassword, String confirmpassword)
	{
		UserSessionDAO userSessionDAO = applicationContext.getBean(UserSessionDAO.class,tenantId);	
		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);	

		ResetPasswordResponse resetResponse = new ResetPasswordResponse();
		try 
		{
			UserSession userSession = userSessionDAO.getValidSession(authTokenId);
			if(userSession == null) 
			{
				resetResponse.setStatus(StatusType.FAILED);
				resetResponse.setMsg("Un-authorized attempt to update the password. Please check your input!");								
			} 
			else 
			{
				if (!StringUtils.equals(newPassword, confirmpassword))
				{
					resetResponse.setStatus(StatusType.FAILED);
					resetResponse.setMsg("Passwords does not match. Please check your input!");			
				}
				else
				{
					if (usersDAO.updatePassword(userSession.getUserId(), CommonUtils.getSecureHash(newPassword),false))
					{
						userSessionDAO.invalidateUserSession(userSession.getId());
						resetResponse.setStatus(StatusType.SUCCESS);
						resetResponse.setMsg("<span style='color:green'>Password successfully changed! Please login with your new password</span>");							
					}
					else
					{
						resetResponse.setStatus(StatusType.FAILED);
						resetResponse.setMsg("Attempt to change password failed. Please try again later or call support!");		
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			CommonUtils.handleExceptionForResponse(resetResponse, e);
		}
		return resetResponse;
	}
	
	
	/**
	 * 
	 * @param sessionid
	 * @param userId
	 * @return
	 */
	public boolean logout(String sessionid)
	{
		try 
		{
			UserSessionDAO userSessionDAO = applicationContext.getBean(UserSessionDAO.class,tenantId);	
			userSessionDAO.invalidateUserSession(sessionid);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 
	 * @param userid
	 * @param pwd
	 * @param sessionid
	 * @return
	 */
	public UserLoginResponse authenticate(String userid, String pwd, JwtEndClientDevice device) 
	{
		UserSessionDAO userSessionDAO = applicationContext.getBean(UserSessionDAO.class,tenantId);
		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);	
		
		UserSession session = this.buildUserSession(userid, device);
		UserLoginResponse loginResp = new UserLoginResponse();
		try 
		{
			logger.trace("About to validate user = "+userid+" pwd = "+pwd);
			User user = usersDAO.authenticate(userid, pwd);
						
			if (user != null)
			{													
				userSessionDAO.insertSession(session);
				
				loginResp.setUserInfo(user);				
				loginResp.setStatus(StatusType.SUCCESS);				
				loginResp.setSessionInfo(session);
				
				loginResp.setJwtToken(jwtTokenUtil.generateToken(session, device));
			}
			else
			{
				loginResp.setStatus(StatusType.FAILED);
				loginResp.setMsg("Login failed, please check your userid / password");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			CommonUtils.handleExceptionForResponse(loginResp, e);
		}
		
		return loginResp;
	}
	
	/**
	 * 
	 * @param emailid
	 * @return
	 */
	public ResetPasswordResponse emailPasswordResetLink(String emailid) 
	{
		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);	

		ResetPasswordResponse resetResponse = new ResetPasswordResponse();
		try 
		{
			User user = usersDAO.getUserByEmailId(emailid);
			if(user == null) 
			{
				resetResponse.setStatus(StatusType.FAILED);
				resetResponse.setMsg("We could not locate your account");								
												
			} 
			else 
			{
				
				String randomString = RandomStringUtils.random(8, true, true);
				String tempPassword = CommonUtils.getSecureHash(randomString);
				usersDAO.updatePassword(user.getUserId(), tempPassword,true);
				/*
				 * send invitation email 
				 */
				this.sendPasswordResetEmail(user, randomString);	
				resetResponse.setMsg("Your temporary password has being emailed");								
				resetResponse.setStatus(StatusType.SUCCESS);
						
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			CommonUtils.handleExceptionForResponse(resetResponse, e);
		}
		return resetResponse;
		
	}
	
	
	/*
	 * send invitation
	 */
	private void sendPasswordResetEmail(User user,String tempPassword) throws Exception
	{
        EmailUtils emailUtils = applicationContext.getBean(EmailUtils.class,this.tenantId);	

		String invitationUrl = SysAdminConfig.GENERAL_DOMAIN_ROOT;
		String msg = "Hello "+user.getFirstName()+", <br /><br /><br />"
				+ "Your password request has being processed. <br />"
				+ "Please click below to login with your temporary password.<br /><br />"
				+ "	<span style = \"padding-left:16%\">"
				+ 		invitationUrl
				+ "	</span><br/><br/><br/>"
				+ "Your credentials are below.<br /><br />"
				+ "<div style = \"padding-left:20%\">"
				+ "		User Id : "
				+ 			user.getUserId()
				+ "		<br/>Temporary Password : "
				+ 			tempPassword  
				+ "		<br/>"
				+ "</div>"
				+ "You will be required to change your password on your first login.<br/><br/>"
				+ "If you have any questions about your account, please feel free to reach us at support@altiareworks.com or call us on +1-309-533-2918.<br/><br/>"
				+ "<br/>"
				+ "Thanks,"
				+ "Support Team "
				+ "www.altaireworks.com";
		
		emailUtils.sendSupportMail(user.getEmailId(), "Password Reset", msg, null);
		
		
	}
	
	
	/*
	 * 
	 */
	private UserSession buildUserSession(String userId, JwtEndClientDevice device)
	{
		UserSession userSession = new UserSession();		
		
		userSession.setId(UUID.randomUUID().toString());
		userSession.setClient(device.platForm.name());
		userSession.setIpAddr(device.ipAddress);
		userSession.setUserId(userId);
		userSession.setExpiresOn(CommonUtils.getXMLGregorianCalendarNow());
		
		return userSession;
	}
	
}
