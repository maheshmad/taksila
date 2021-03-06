package com.taksila.veda.usermgmt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.taksila.veda.db.dao.UserImagesDAO;
import com.taksila.veda.db.dao.UsersDAO;
import com.taksila.veda.email.EmailUtils;
import com.taksila.veda.eventschedulemgmt.EventScheduleMgmtComponent;
import com.taksila.veda.model.api.base.v1_0.AllowedActionsRequest;
import com.taksila.veda.model.api.base.v1_0.AllowedActionsResponse;
import com.taksila.veda.model.api.base.v1_0.SearchHitRecord;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.usermgmt.v1_0.CreateNewUserRequest;
import com.taksila.veda.model.api.usermgmt.v1_0.CreateNewUserResponse;
import com.taksila.veda.model.api.usermgmt.v1_0.DeleteUserRequest;
import com.taksila.veda.model.api.usermgmt.v1_0.DeleteUserResponse;
import com.taksila.veda.model.api.usermgmt.v1_0.GetUserRequest;
import com.taksila.veda.model.api.usermgmt.v1_0.GetUserResponse;
import com.taksila.veda.model.api.usermgmt.v1_0.SearchUserRequest;
import com.taksila.veda.model.api.usermgmt.v1_0.SearchUserResponse;
import com.taksila.veda.model.api.usermgmt.v1_0.UpdateUserRequest;
import com.taksila.veda.model.api.usermgmt.v1_0.UpdateUserResponse;
import com.taksila.veda.model.db.usermgmt.v1_0.User;
import com.taksila.veda.model.db.usermgmt.v1_0.UserImageInfo;
import com.taksila.veda.model.db.usermgmt.v1_0.UserImageType;
import com.taksila.veda.utils.CommonUtils;
import com.taksila.veda.utils.SysAdminConfig;

@Component
@Scope(value="prototype")
public class UserComponent
{	
	@Autowired
	ApplicationContext applicationContext;
	
	static Logger logger = LogManager.getLogger(UserComponent.class.getName());	

	private String tenantId;
	
	@Autowired
	public UserComponent(@Value("tenantId") String tenantId) 
	{
		logger.trace(">>>>>>>>>>>>>>>> Creating UserComponent bean for tenant = "+tenantId);
		this.tenantId = tenantId;
		
	}
	/**
	 * 
	 * @param req
	 * @return
	 */
	public SearchUserResponse searchUsers(SearchUserRequest req)
	{
		SearchUserResponse resp = new SearchUserResponse();
		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);	
		try 
		{
			List<User> userSearchHits = usersDAO.searchUsers(req.getQuery());
			
			for(User user: userSearchHits)
			{
				SearchHitRecord rec = new SearchHitRecord();
				/*
				 * map search hits
				 */
				rec.setRecordId(String.valueOf(user.getId()));
				rec.setRecordTitle(user.getFirstName());
				rec.setRecordSubtitle(user.getLastName());				
				
				resp.getHits().add(rec);
			}
			
			resp.setRecordType("USERS");
			resp.setPage(req.getPage());
			resp.setPageOffset(req.getPageOffset());
			resp.setTotalHits(userSearchHits.size());

		} 
		catch (Exception e) 
		{			
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;
	}
	
	
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public GetUserResponse getUser(int id)
	{
		GetUserResponse resp = new GetUserResponse();
		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);	
		try 
		{
			User user = usersDAO.getUserByUserRecordId(id);
			
			if (user == null)
			{	
				resp.setMsg("Did not find any records with id = "+id);
			}
			else
			{
				resp.setUser(user);
				List<UserImageInfo> hits = this.getUserImageInfoAll(user.getUserId(), UserImageType.PROFILE_IMAGE);
				if (hits.size() > 0)
					resp.setProfileImageInfo(hits.get(0));				
				/*
				 * get other images
				 */				
				resp.getSocialImagesList().addAll(this.getUserImageInfoAll(user.getUserId(), UserImageType.SOCIAL_IMAGE));
			}
			
			logger.trace("++++++++  exiting getUser component ");

		} 
		catch (Exception e) 
		{
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public GetUserResponse getUser(String userid)
	{
		GetUserResponse resp = new GetUserResponse();
		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);	
		try 
		{
			User user = usersDAO.getUserByUserId(userid);
			
			if (user == null)
			{	
				resp.setMsg("Did not find any records with userid = "+userid);
			}
			else
			{
				resp.setUser(user);										
				List<UserImageInfo> hits = this.getUserImageInfoAll(userid, UserImageType.PROFILE_IMAGE);
				if (hits.size() > 0)
					resp.setProfileImageInfo(hits.get(0));				
				/*
				 * get other images
				 */				
				resp.getSocialImagesList().addAll(this.getUserImageInfoAll(userid, UserImageType.SOCIAL_IMAGE));
				
			}
			
			logger.trace("++++++++  exiting getUser component ");

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;
	}
	
	
//	/**
//	 * 
//	 * @param req
//	 * @return
//	 */
//	public GetUserResponse getUserByName(GetUserRequest req)
//	{
//		GetUserResponse resp = new GetUserResponse();
//		try 
//		{
//			User user = userDAO.getUserByName(req.getName());
//			
//			if (user == null)
//			{	
//				resp.setMsg("Did not find any records with id = "+req.getId());
//			}
//			else
//			{
//				resp.setUser(user);
//			}
//			
//			logger.trace("++++++++  exiting getUser component ");
//
//		} 
//		catch (Exception e) 
//		{
//			CommonUtils.handleExceptionForResponse(resp, e);
//		}
//		return resp;
//	}
	
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public CreateNewUserResponse createNewUser(CreateNewUserRequest req)
	{
		CreateNewUserResponse resp = new CreateNewUserResponse();
		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);	
		try 
		{
			/*
			 * generate temporary password
			 */
			String temppassword = RandomStringUtils.random(8, true, true);
			req.getNewUser().setUserPswd(CommonUtils.getSecureHash(temppassword));
			User user = usersDAO.insertUser(req.getNewUser());			
			resp.setUser(user);
			resp.setStatus(StatusType.SUCCESS);			
			resp.setSuccess(true);
			
			/*
			 * send invitation email 
			 */
			boolean emailSent = this.sendInvitationEmail(user, temppassword);	
			resp.setMsg("New user : "+user.getFirstName()+" "+user.getLastName()+" successfully added, record id = "+user.getId()+"<br /> Email sent = "+emailSent);
			
			logger.trace("********  exiting user component createNewUser ");
		} 
		catch (Exception e) 
		{
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;
		
	}
	
	/**
	 * 
	 * @param formParams
	 * @return
	 */
	public CreateNewUserResponse createNewUser(MultivaluedMap<String, String> formParams)
	{				
		User user = new User();
		CreateNewUserRequest operReq = new CreateNewUserRequest();
		
		mapFormValuesToUser(formParams, user); /* bind the form values to new user object */		
		operReq.setNewUser(user);
		
		return this.createNewUser(operReq); 		
		
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public UpdateUserResponse updateUser(UpdateUserRequest req)
	{		
		UpdateUserResponse resp = new UpdateUserResponse();
		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);	
		try 
		{
			//TODO validation
			logger.trace("about to add user to DB = "+CommonUtils.toJson(req.getUser()));
			boolean updateSucceded = usersDAO.updateUser(req.getUser());
			if (updateSucceded)
			{
				resp.setStatus(StatusType.SUCCESS);
				resp.setUser(req.getUser());
			}
			else
			{
				resp.setStatus(StatusType.FAILED);
				resp.setErrorInfo(CommonUtils.buildErrorInfo("FAILED", "Database was not updated! Please check your input"));
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
	 * @param formParams
	 * @return
	 */
	public UpdateUserResponse updateUser(String id,MultivaluedMap<String, String> formParams)
	{						
		UpdateUserResponse operResp = new UpdateUserResponse();
		try	
		{
			logger.trace("About to update user record = "+id+ " for fields "+CommonUtils.toJson(formParams));			
			/*
			 * first fetch the original user record 
			 */
			GetUserRequest req = new GetUserRequest();
			req.setId(id);			
			GetUserResponse userResp = this.getUser(Integer.parseInt(id));
			
			/*
			 * check if the record exists before it can be updated
			 */
			if (userResp == null || userResp.getUser() == null)
			{						
				operResp.setStatus(StatusType.FAILED);
				operResp.setErrorInfo(CommonUtils.buildErrorInfo("id", "Could not located record id = "+id+"! Update was cancelled"));
				operResp.setMsg("Could not located record id = "+id+"! Update was cancelled");
			}
			else
			{																
				User user = userResp.getUser();
				mapFormValuesToUser(formParams, user);						
				UpdateUserRequest updateUserReq = new UpdateUserRequest();
				updateUserReq.setUser(user);
				operResp = this.updateUser(updateUserReq);						
			}											
			
			operResp.setSuccess(true);
		}
		catch(Exception ex)
		{
			CommonUtils.handleExceptionForResponse(operResp, ex);
		}
		
		return operResp; 		
		
	}
	
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public UpdateUserResponse updateUserImage(String userId,InputStream userContentImageIs, String imageType, double scale)
	{
		logger.trace("++++++++  ABOUT to insert image into Users id= "+userId);
		UpdateUserResponse resp = new UpdateUserResponse();
		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);	
		try 
		{
			//TODO validation
			
			boolean updateSucceded = usersDAO.updateUserImage(userId, userContentImageIs, imageType,scale);
			if (updateSucceded)
			{
				resp.setStatus(StatusType.SUCCESS);	
				resp.setSuccess(true);
			}
			else
			{
				resp.setSuccess(false);
				resp.setStatus(StatusType.FAILED);
				resp.setErrorInfo(CommonUtils.buildErrorInfo("FAILED", "Image content was not updated! Please check your data"));				
			}
			
		} 
		catch (Exception e) 
		{
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;
		
	}
	
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	public ByteArrayOutputStream getUserImage(int userId,double scale) throws Exception
	{
		logger.trace("++++++++  ABOUT to getUserImage image = "+userId);	
		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);	
			//TODO validation			
		return usersDAO.readUserImage(userId, scale);					
		
	}
	
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public DeleteUserResponse deleteUser(DeleteUserRequest req)
	{
		DeleteUserResponse resp = new DeleteUserResponse();
		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);	
		try 
		{
			//TODO validation
			
			boolean updateSucceded = usersDAO.deleteUser(req.getId());
			if (updateSucceded)
			{
				resp.setStatus(StatusType.SUCCESS);
			}
			else
			{
				resp.setStatus(StatusType.FAILED);
				resp.setErrorInfo(CommonUtils.buildErrorInfo("FAILED", "Database was not updated! Please check your input"));
			}
			
		} 
		catch (Exception e) 
		{
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;
		
	}
	
	/**
	 * 
	 * @param imageid
	 * @param scale
	 * @return
	 * @throws Exception
	 */
	public ByteArrayOutputStream getImage(String imageid, double scale) throws Exception 
	{
		UserImagesDAO userImagesDAO = applicationContext.getBean(UserImagesDAO.class,tenantId);	
		return userImagesDAO.readUserImageContent(imageid,scale);
	}
	
	
	
	
	/**
	 * This function is used by both create new user and update user
	 * 
	 * @param formParams
	 * @param user
	 */
	private void mapFormValuesToUser(MultivaluedMap<String, String> formParams, User user)
	{		
		
		/*
		 * update only the fields that have being sent in the 
		 * form
		 */
		logger.trace(CommonUtils.toJson(formParams));
		for (String key: formParams.keySet())
		{
			if (StringUtils.equals(key, "id"))
				user.setId(formParams.getFirst("id"));
			
			if (StringUtils.equals(key, "userId"))
				user.setUserId(formParams.getFirst("userId"));
			
			if (StringUtils.equals(key, "emailId"))
				user.setEmailId(formParams.getFirst("emailId"));
			
			if (StringUtils.equals(key, "userRoles"))
			{
				logger.trace("user roles from ui = "+formParams.getFirst("userRoles"));
				user.getUserRoles().clear();
				user.getUserRoles().addAll(CommonUtils.convertStringToUserRoles(formParams.getFirst("userRoles")));
			}
			
			if (StringUtils.equals(key, "firstName"))
				user.setFirstName(formParams.getFirst("firstName"));
			
			if (StringUtils.equals(key, "middleName"))
				user.setMiddleName(formParams.getFirst("middleName"));
			
			if (StringUtils.equals(key, "lastName"))
				user.setLastName(formParams.getFirst("lastName"));
									
			if (StringUtils.equals(key, "addressLine1"))
				user.setAddressLine1(formParams.getFirst("addressLine1"));
			
			if (StringUtils.equals(key, "addressLine2"))
				user.setAddressLine2(formParams.getFirst("addressLine2"));
			
			if (StringUtils.equals(key, "city"))
				user.setCity(formParams.getFirst("city"));
			
			if (StringUtils.equals(key, "state"))
				user.setState(formParams.getFirst("state"));
			
			if (StringUtils.equals(key,"postalcode"))
				user.setPostalcode(formParams.getFirst("postalcode"));
			
			if (StringUtils.equals(key,"country"))
				user.setCountry(formParams.getFirst("country"));
			
			if (StringUtils.equals(key, "cellphone"))
				user.setCellphone(formParams.getFirst("cellphone"));
			
			if (StringUtils.equals(key, "okToText"))
				user.setOkToText(Boolean.getBoolean(formParams.getFirst("okToText")));
			
			if (StringUtils.equals(key, "landlinephone"))
				user.setLandlinephone(formParams.getFirst("landlinephone"));
			
			if (StringUtils.equals(key, "officephone"))
				user.setOfficephone(formParams.getFirst("officephone"));
			
			if (StringUtils.equals(key, "officephoneExt"))
				user.setOfficephoneExt(formParams.getFirst("officephoneExt"));
			
			if (StringUtils.equals(key, "updatedBy"))
				user.setUpdatedBy(formParams.getFirst("updatedBy"));
			
			if (StringUtils.equals(key, "accountDisabled"))
				user.setOkToText(Boolean.getBoolean(formParams.getFirst("accountDisabled")));
			
			if (StringUtils.equals(key, "accountDeleted"))
				user.setOkToText(Boolean.getBoolean(formParams.getFirst("accountDeleted")));
		}
		
		
		
	}
	
	/*
	 * send invitation
	 */
	private boolean sendInvitationEmail(User user,String tempPassword)
	{
        EmailUtils emailUtils = applicationContext.getBean(EmailUtils.class,this.tenantId);	

		try
		{
			String invitationUrl = SysAdminConfig.GENERAL_DOMAIN_ROOT;
			String msg = "Hello "+user.getFirstName()+", <br /><br /><br />"
					+ "Welcome to Veda. <br />"
					+ "Please click below to activate and set up your account.<br /><br />"
					+ "<span style = \"padding-left:16%\">"
					+ invitationUrl
					+ "</span><br/><br/><br/>"
					+ "Your credentials are below.<br /><br />"
					+ "<div style = \"padding-left:20%\">"
					+ "User Id : "
					+ user.getUserId()
					+ "<br/>Temporary Password : "
					+ tempPassword  
					+ "<br/></div>"
					+ "You will be required to change your password on your first login.<br/><br/>"
					+ "If you have any questions about your account, please feel free to reach us at support@altaireworks or call us on +1xxxxxx.<br/><br/>"
					+ "<address>"
					+ "<br/>";
			
			emailUtils.sendMail(user.getEmailId(), "support@altaireworks", "Welcome", msg, null);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
			
		
		return true;
		
	}
	
	/**
	 * 
	 * @param user
	 * @param fileId
	 * @return
	 * @throws Exception 
	 */
	public UserImageInfo processUserImageFile(String userid,String fileId) throws Exception 
	{		
		UserImagesDAO userImagesDAO = applicationContext.getBean(UserImagesDAO.class,tenantId);	
		GetUserResponse userResp = this.getUser(userid);		
		if (userResp == null || userResp.getUser() == null)
			throw new Exception("Invalid user");
		
		User user = userResp.getUser();
		/*
		 * search if the image already exists.
		 * If not create a new entry
		 */
		UserImageInfo searchUserImage = new UserImageInfo();
		searchUserImage.setUserImageType(UserImageType.PROFILE_IMAGE);
		searchUserImage.setUserId(user.getUserId());
		
		List<UserImageInfo> userImages = userImagesDAO.search(searchUserImage);		
		UserImageInfo userImageInfo = null;
		if (userImages != null && !userImages.isEmpty())
		{
			userImageInfo = userImages.get(0);
		}
		else
		{
			userImageInfo = new UserImageInfo();
			userImageInfo.setImageid(fileId);
			userImageInfo.setUserId(user.getUserId());			
		}
		
		/*
		 * save the original file to db 
		 */		      
		userImageInfo.setUserImageType(UserImageType.PROFILE_IMAGE);				
		File imageFile = new File(getUserTempFilePath(user.getUserId()) +"\\"+ fileId);
		InputStream imgis =  new FileInputStream(imageFile);
		InputStream imgisthumb =  new FileInputStream(imageFile);
		UserImageInfo uimgInfo = userImagesDAO.insertUserImageInfo(userImageInfo, imgis, imgisthumb);
		
		FileUtils.deleteQuietly(imageFile);
		
		return uimgInfo;
	}
	
	public UserImageInfo getUserImageInfo(String imageid) throws Exception 
	{		
		UserImagesDAO userImagesDAO = applicationContext.getBean(UserImagesDAO.class,tenantId);	
		return userImagesDAO.getUserImageInfoById(imageid);
	}
	
	public List<UserImageInfo> getUserImageInfoAll(String userid, UserImageType imgType) throws Exception 
	{				
		UserImagesDAO userImagesDAO = applicationContext.getBean(UserImagesDAO.class,tenantId);	
		UserImageInfo searchUserImage = new UserImageInfo();
		searchUserImage.setUserId(userid);
		searchUserImage.setUserImageType(imgType);
		List<UserImageInfo> hits = userImagesDAO.search(searchUserImage);
		return hits;
	}
	
	
	
	
	
	/*
	 * get temp file path
	 */
	public String getUserTempFilePath(String userid)
	{
		String basePath = SysAdminConfig.TEMP_FILE_PATH;
		basePath = StringUtils.removeEnd(basePath, "\\");
		String dirPath = SysAdminConfig.TEMP_FILE_PATH+"\\users\\"+userid+"\\";
		boolean dirExits = new File(dirPath).mkdirs();
		return dirPath;  
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public AllowedActionsResponse getAllowedActions(AllowedActionsRequest req) 
	{
		AllowedActionsResponse allowedActionsResp = new AllowedActionsResponse();
		
		switch (req.getRecordType())
		{
			case EVENTSCHEDULE:
				EventScheduleMgmtComponent evenScheMgmtComp = new EventScheduleMgmtComponent(this.tenantId);
				allowedActionsResp = evenScheMgmtComp.getAllowedActions(req);
			default:				
				allowedActionsResp.setErrorInfo(CommonUtils.buildErrorInfo("RECORD TYPE", "Unsupported Record Type"));
		}
		
		
		return allowedActionsResp;
	}
	
//	@Override
//	public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException 
//	{		
//		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);	
//		try 
//		{
//			User user = usersDAO.getUserByUserId(username);
//			
//			if (user == null)
//			{	
//				return null;
//			}
//			else
//			{				
//				return new JwtUser(Long.parseLong(user.getId()),
//															user.getUserId(),
//															user.getFirstName(),
//															user.getLastName(),
//															user.getUserPswd(),
//															user.getEmailId(),
//															null,
//															true,
//															null);
//				
//				
//			}
//			
//			
//
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
	
	
}
