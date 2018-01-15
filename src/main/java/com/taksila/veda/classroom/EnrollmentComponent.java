package com.taksila.veda.classroom;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.taksila.veda.db.dao.ClassroomDAO;
import com.taksila.veda.db.dao.EnrollmentDAO;
import com.taksila.veda.db.dao.UsersDAO;
import com.taksila.veda.model.api.base.v1_0.ErrorInfo;
import com.taksila.veda.model.api.base.v1_0.SearchHitRecord;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.classroom.v1_0.Classroom;
import com.taksila.veda.model.api.classroom.v1_0.CreateEnrollmentRequest;
import com.taksila.veda.model.api.classroom.v1_0.CreateEnrollmentResponse;
import com.taksila.veda.model.api.classroom.v1_0.DeleteEnrollmentRequest;
import com.taksila.veda.model.api.classroom.v1_0.DeleteEnrollmentResponse;
import com.taksila.veda.model.api.classroom.v1_0.Enrollment;
import com.taksila.veda.model.api.classroom.v1_0.GetEnrollmentRequest;
import com.taksila.veda.model.api.classroom.v1_0.GetEnrollmentResponse;
import com.taksila.veda.model.api.classroom.v1_0.SearchEnrollmentRequest;
import com.taksila.veda.model.api.classroom.v1_0.SearchEnrollmentResponse;
import com.taksila.veda.model.api.classroom.v1_0.UpdateEnrollmentRequest;
import com.taksila.veda.model.api.classroom.v1_0.UpdateEnrollmentResponse;
import com.taksila.veda.model.db.classroom.v1_0.EnrollmentStatusType;
import com.taksila.veda.model.db.usermgmt.v1_0.User;
import com.taksila.veda.utils.CommonUtils;



@Component
@Scope(value="prototype")
public class EnrollmentComponent 
{	
	@Autowired
	ApplicationContext applicationContext;
	
	private String tenantId;
	static Logger logger = LogManager.getLogger(EnrollmentComponent.class.getName());
	
	@Autowired
	public EnrollmentComponent(@Value("tenantId") String tenantId) 
	{
		logger.trace(">>>>>>>>>>>>>>>> Creating bean for tenant "+tenantId);
		this.tenantId = tenantId;	
	}
			
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public SearchEnrollmentResponse searchEnrollmentByClassroom(SearchEnrollmentRequest req)
	{
		EnrollmentDAO enrollmentDAO = applicationContext.getBean(EnrollmentDAO.class,tenantId);	
				
		SearchEnrollmentResponse resp = new SearchEnrollmentResponse();
		try 
		{
			List<Enrollment> enrollmentSearchHits = enrollmentDAO.searchEnrollmentsByClassroomId(req.getQuery());
			
			for(Enrollment enrollment: enrollmentSearchHits)
			{
				SearchHitRecord rec = new SearchHitRecord();
				String subtitle = "";					
				subtitle += "Status: "+(enrollment.getEnrollStatus() != null ? enrollment.getEnrollStatus().value() : "N/A");
				/*
				 * map search hits
				 */
				rec.setRecordId(String.valueOf(enrollment.getId()));				
				if (enrollment.getStudent() != null)
				{
					User student = enrollment.getStudent();					
					subtitle += ", Email: "+(student.getEmailId() != null ? student.getEmailId() : "N/A");
					subtitle += ", Cellphone: "+(student.getCellphone() != null ? student.getCellphone() : "N/A");					
											
					rec.setRecordTitle(student.getLastName()+", "+student.getFirstName());
					
				}
				rec.setRecordSubtitle(subtitle);				
				resp.getHits().add(rec);
			}
			
			resp.setRecordType("ENROLLMENT");
			resp.setPage(req.getPage());
			resp.setPageOffset(req.getPageOffset());
			resp.setTotalHits(enrollmentSearchHits.size());

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
	public SearchEnrollmentResponse searchEnrollmentByUserRecordid(SearchEnrollmentRequest req)
	{
		EnrollmentDAO enrollmentDAO = applicationContext.getBean(EnrollmentDAO.class,tenantId);	
		
		
		SearchEnrollmentResponse resp = new SearchEnrollmentResponse();
		try 
		{
			List<Enrollment> enrollmentSearchHits = enrollmentDAO.searchEnrollmentsByUserRecordId(req.getQuery());
			
			for(Enrollment enrollment: enrollmentSearchHits)
			{
				SearchHitRecord rec = new SearchHitRecord();
				String subtitle = "";					
				subtitle += "Status: "+(enrollment.getEnrollStatus() != null ? enrollment.getEnrollStatus().value() : "N/A");
				/*
				 * map search hits
				 */
				rec.setRecordId(String.valueOf(enrollment.getId()));				
				if (enrollment.getClassroom() != null)
				{
					Classroom classroom = (Classroom) enrollment.getClassroom();					
//					subtitle += ", Email: "+(student.getEmailId() != null ? student.getEmailId() : "N/A");
//					subtitle += ", Cellphone: "+(student.getCellphone() != null ? student.getCellphone() : "N/A");					//											
					rec.setRecordTitle(classroom.getTitle());
					rec.setRecordSubtitle(classroom.getTitle());
//					rec.setRecordId(classroom.getId());
					
				}
				rec.setRecordSubtitle(subtitle);				
				resp.getHits().add(rec);
			}
			
			resp.setRecordType("ENROLLMENT");
			resp.setPage(req.getPage());
			resp.setPageOffset(req.getPageOffset());
			resp.setTotalHits(enrollmentSearchHits.size());

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
	public GetEnrollmentResponse getEnrollment(GetEnrollmentRequest req)
	{
		EnrollmentDAO enrollmentDAO = applicationContext.getBean(EnrollmentDAO.class,tenantId);	
	
		
		GetEnrollmentResponse resp = new GetEnrollmentResponse();
		try 
		{
			Enrollment enrollment = enrollmentDAO.getEnrollmentById(req.getId());
			
			if (enrollment == null)
			{	
				resp.setMsg("Did not find any records with id = "+req.getId());
			}
			else
			{
				resp.setEnrollment(enrollment);
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
	 */
	public CreateEnrollmentResponse createNewEnrollment(CreateEnrollmentRequest req)
	{
		EnrollmentDAO enrollmentDAO = applicationContext.getBean(EnrollmentDAO.class,tenantId);	
	
		CreateEnrollmentResponse resp = new CreateEnrollmentResponse();
		Enrollment enroll = req.getEnrollment();
		
		logger.trace(" creating a new  enrollment "+CommonUtils.toJson(req));
		try 
		{							
			/*
			 * validate
			 */
			resp.setErrorInfo(this.validate(enroll));
						
			/*
			 * check response
			 */
			if (resp.getErrorInfo() == null)
			{										
				/*
				 * generate enrollment id 
				 */
				req.getEnrollment().setId(this.generateEnrollmentId(enroll.getUserRecordId(), enroll.getClassroomid()));
				
				Enrollment enrolled = enrollmentDAO.insertEnrollment(req.getEnrollment());	
				if (enrolled != null)
				{
					resp.setStatus(StatusType.SUCCESS);
					resp.setMsg("Successfully enrolled for user = "+req.getEnrollment().getUserRecordId()+" in classroom "+req.getEnrollment().getClassroomid());
					resp.setEnrollment(enrolled);
				}
				else
				{
					resp.setStatus(StatusType.FAILED);
					resp.setErrorInfo(CommonUtils.buildErrorInfo("FAILED", "Database was not updated! Please check your input"));
				}
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
	 */
	public UpdateEnrollmentResponse updateEnrollment(UpdateEnrollmentRequest req)
	{
		EnrollmentDAO enrollmentDAO = applicationContext.getBean(EnrollmentDAO.class,tenantId);	
		
		UpdateEnrollmentResponse resp = new UpdateEnrollmentResponse();
		Enrollment enroll = req.getEnrollment();
		try 
		{
			/*
			 * validate
			 */
			resp.setErrorInfo(this.validate(enroll));
						
			/*
			 * check response
			 */
			if (resp.getErrorInfo() == null)
			{		
				boolean updateSucceded = enrollmentDAO.updateEnrollment(req.getEnrollment());
				if (updateSucceded)
				{
					resp.setStatus(StatusType.SUCCESS);
					resp.setEnrollment(enrollmentDAO.getEnrollmentById(req.getEnrollment().getId()));
					resp.setMsg("Successfully updated enrollment for user = "+req.getEnrollment().getUserRecordId());
				}
				else
				{
					resp.setStatus(StatusType.FAILED);
					resp.setErrorInfo(CommonUtils.buildErrorInfo("FAILED", "Database was not updated! Please check your input"));
				}
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
	 */
	public DeleteEnrollmentResponse deleteEnrollment(DeleteEnrollmentRequest req)
	{
		EnrollmentDAO enrollmentDAO = applicationContext.getBean(EnrollmentDAO.class,tenantId);	
		
		DeleteEnrollmentResponse resp = new DeleteEnrollmentResponse();
		try 
		{
			//TODO validation
			
			boolean updateSucceded = enrollmentDAO.deleteEnrollment(req.getId());
			if (updateSucceded)
			{
				resp.setStatus(StatusType.SUCCESS);
				resp.setMsg("User successfully un-enrolled from the class");
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
	 * @param resp
	 * @param enroll
	 * @throws Exception 
	 */
	
	private ErrorInfo validate(Enrollment enroll) throws Exception
	{
		ClassroomDAO classroomDAO = applicationContext.getBean(ClassroomDAO.class,tenantId);	
		UsersDAO usersDAO = applicationContext.getBean(UsersDAO.class,tenantId);
		
		ErrorInfo errorInfo = new ErrorInfo();
		if (StringUtils.isBlank(enroll.getUserRecordId()))
			CommonUtils.buildErrorInfo(errorInfo, "userRecordId", "Please provide a valid user record id");
		else
		{
			User user = usersDAO.getUserByUserRecordId(Integer.parseInt(enroll.getUserRecordId()));			
			if (user == null)
				CommonUtils.buildErrorInfo(errorInfo, "userId", "Student not found user id = "+enroll.getUserRecordId());
		}
			
		
		if (StringUtils.isBlank(enroll.getClassroomid()))
			CommonUtils.buildErrorInfo(errorInfo, "classroomid", "Please provide a valid classroom id");
		else
		{
			Classroom classroom = classroomDAO.getClassroomById(enroll.getClassroomid());				
			if (classroom == null)
				CommonUtils.buildErrorInfo(errorInfo, "classroomid", "Classroom not found classroom id = "+enroll.getClassroomid());
		}
		
		if (errorInfo.getErrors() == null || errorInfo.getErrors().isEmpty())
			return null;
		else
			return errorInfo;
					
	}
	
	private String generateEnrollmentId(String userid, String classroomid)
	{
		return 	CommonUtils.getSecureHash("user:"+userid+"-class:"+classroomid);

	}


	public void mapFormFields(MultivaluedMap<String, String> formParams, Enrollment enrollment) 
	{
		
		logger.trace("form params size = "+formParams.size());
		for (String key: formParams.keySet())
		{
			if (StringUtils.equals(key, "id"))
				enrollment.setId(formParams.getFirst("id"));
			
			if (StringUtils.equals(key, "userRecordId"))
				enrollment.setUserRecordId(formParams.getFirst("userRecordId"));
			
			if (StringUtils.equals(key, "classroomid"))
				enrollment.setClassroomid(formParams.getFirst("classroomid"));
			
			if (StringUtils.equals(key, "verifiedBy"))						
				enrollment.setVerifiedBy(formParams.getFirst("verifiedBy"));
			
			if (StringUtils.equals(key, "enrolledOn"))						
				enrollment.setEnrolledOn(CommonUtils.getXMLGregorianCalendarFromString(formParams.getFirst("enrolledOn"), "yyyy-MM-dd"));
			
			if (StringUtils.equals(key, "enrollStatus"))	
			{
				String val = formParams.getFirst("enrollStatus");
				enrollment.setEnrollStatus(EnrollmentStatusType.fromValue(val));
			}
			
						
		}
								
	}
	
	
	
}
