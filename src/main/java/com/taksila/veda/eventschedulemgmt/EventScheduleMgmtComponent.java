package com.taksila.veda.eventschedulemgmt;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.taksila.veda.classroom.ClassroomComponent;
import com.taksila.veda.db.dao.EventScheduleDAO;
import com.taksila.veda.model.api.base.v1_0.AllowedActionsRequest;
import com.taksila.veda.model.api.base.v1_0.AllowedActionsResponse;
import com.taksila.veda.model.api.base.v1_0.Err;
import com.taksila.veda.model.api.base.v1_0.ErrorInfo;
import com.taksila.veda.model.api.base.v1_0.SearchHitRecord;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.base.v1_0.UserAllowedAction;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.CreateEventScheduleRequest;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.CreateEventScheduleResponse;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.DeleteEventScheduleRequest;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.DeleteEventScheduleResponse;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.GetEventScheduleRequest;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.GetEventScheduleResponse;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.SearchEventScheduleRequest;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.SearchEventScheduleResponse;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.UpdateEventScheduleRequest;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.UpdateEventScheduleResponse;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.UpdateEventScheduleSessionIdRequest;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.UpdateEventScheduleSessionIdResponse;
import com.taksila.veda.model.db.event_schedule_mgmt.v1_0.EventSchedule;
import com.taksila.veda.model.db.event_schedule_mgmt.v1_0.EventStatusType;
import com.taksila.veda.model.db.event_schedule_mgmt.v1_0.EventType;
import com.taksila.veda.utils.CommonUtils;
import com.taksila.veda.utils.ValidationUtils;



@Component
@Scope(value="prototype")
public class EventScheduleMgmtComponent 
{	
	@Autowired
	ApplicationContext applicationContext;
			
	static Logger logger = LogManager.getLogger(EventScheduleMgmtComponent.class.getName());
	private String tenantId;
	
	@Autowired
	public EventScheduleMgmtComponent(@Value("tenantId") String tenantId) 
	{
		logger.trace(">>>>>>>>>>>>>>>> Creating EventScheduleMgmtComponent bean for tenant "+tenantId);		
		this.tenantId = tenantId;		
	}
			
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public SearchEventScheduleResponse searchEventSchedule(SearchEventScheduleRequest req)
	{
		EventScheduleDAO eventScheduleDAO = applicationContext.getBean(EventScheduleDAO.class,tenantId);	
		
		SearchEventScheduleResponse resp = new SearchEventScheduleResponse();
		List<EventSchedule> eventScheduleSearchHits = new ArrayList<EventSchedule>();
		try 
		{
			if (StringUtils.isNotBlank(req.getUserRecordId()))
				eventScheduleSearchHits = eventScheduleDAO.searchEventScheduleByUserid(req.getUserRecordId());
			else
				eventScheduleSearchHits = eventScheduleDAO.searchEventScheduleByClassroomId(req.getClassroomid());
			
			for(EventSchedule event: eventScheduleSearchHits)
			{
				SearchHitRecord rec = new SearchHitRecord();
				/*
				 * map search hits
				 */
				rec.setRecordId(String.valueOf(event.getId()));				
				rec.setRecordTitle(event.getEventTitle());
				rec.setRecordSubtitle(event.getEventDescription()+" Starts at :"+event.getEventStartDate()+" Ends at: "+event.getEventEndDate());					
				
				resp.getHits().add(rec);
			}
			
			resp.setRecordType("EVENTS_SCHEDULE");
			resp.setPage(req.getPage());
			resp.setPageOffset(req.getPageOffset());
			resp.setTotalHits(eventScheduleSearchHits.size());
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
//	public SearchEventScheduleResponse searchEventScheduleByUser(SearchEventScheduleRequest req)
//	{
//		SearchEventScheduleResponse resp = new SearchEventScheduleResponse();
//		try 
//		{
//			List<EventSchedule> eventScheduleSearchHits = eventScheduleDAO.searchEventScheduleByClassroomId(req.getUserRecordId());
//			resp.getEventSchedule().addAll(eventScheduleSearchHits);
//			resp.setStatus(StatusType.SUCCESS);
//			resp.setSuccess(true);
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
	public GetEventScheduleResponse getEventSchedule(GetEventScheduleRequest req)
	{
		EventScheduleDAO eventScheduleDAO = applicationContext.getBean(EventScheduleDAO.class,tenantId);	
		GetEventScheduleResponse resp = new GetEventScheduleResponse();
		try 
		{
			EventSchedule eventSchedule = eventScheduleDAO.getEventScheduleById(req.getId());
			
			if (eventSchedule == null)
			{	
				resp.setMsg("Did not find any records with id = "+req.getId());
			}
			else
			{
//				eventSchedule.getEnrolledStudents().addAll(this.enrollmentDAO.searchStudentsByEventScheduleid(req.getId()));
				resp.setEventSchedule(eventSchedule);
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
	public GetEventScheduleResponse getEventScheduleBySessionId(GetEventScheduleRequest req)
	{
		EventScheduleDAO eventScheduleDAO = applicationContext.getBean(EventScheduleDAO.class,tenantId);	
		GetEventScheduleResponse resp = new GetEventScheduleResponse();
		try 
		{
			EventSchedule eventSchedule = eventScheduleDAO.getEventScheduleBySessionId(req.getEventSessionId());
			
			if (eventSchedule == null)
			{	
				resp.setMsg("Did not find any records with id = "+req.getEventSessionId());
			}
			else
			{
//				eventSchedule.getEnrolledStudents().addAll(this.enrollmentDAO.searchStudentsByEventScheduleid(req.getId()));
				resp.setEventSchedule(eventSchedule);
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
	public EventSchedule getEventScheduleBySessionId(String sessionId)
	{
		EventScheduleDAO eventScheduleDAO = applicationContext.getBean(EventScheduleDAO.class,tenantId);	
		try 
		{
			return eventScheduleDAO.getEventScheduleBySessionId(sessionId);			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param eventScheduleId
	 * @return EventSchedule
	 */
	public EventSchedule getEventSchedule(String eventScheduleId)
	{
		GetEventScheduleRequest req = new GetEventScheduleRequest();
		req.setId(eventScheduleId);
		return this.getEventSchedule(req).getEventSchedule();
	}
	
	
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public CreateEventScheduleResponse createNewEventSchedule(CreateEventScheduleRequest req)
	{
		EventScheduleDAO eventScheduleDAO = applicationContext.getBean(EventScheduleDAO.class,tenantId);	
		CreateEventScheduleResponse resp = new CreateEventScheduleResponse();
		EventSchedule eventScheudle = req.getEventSchedule();
		try 
		{							
			/*
			 * validate
			 */
			ErrorInfo errorInfo = this.validateEventSchedule(eventScheudle);
//			errs.add(this.isValidId(eventScheudle.getId(), true));

			if (errorInfo.getErrors() != null && !errorInfo.getErrors().isEmpty())
			{
				resp.setErrorInfo(errorInfo);
				return resp;
			}
			
			EventSchedule eventSche = eventScheduleDAO.insertEventSchedule(req.getEventSchedule());
			resp.setSuccess(true);			
			if (StringUtils.isNotBlank(eventSche.getId()))
			{
				resp.setEventSchedule(eventSche);
				resp.setStatus(StatusType.SUCCESS);
				resp.setMsg("Successfully created a new event schedule id = "+resp.getEventSchedule().getId());
			}
			else
			{
				resp.setStatus(StatusType.FAILED);
				resp.setMsg("Updates to DB failed , please try again or contact support ");
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
	 * @param req
	 * @return
	 */
	public UpdateEventScheduleResponse updateEventSchedule(UpdateEventScheduleRequest req)
	{
		EventScheduleDAO eventScheduleDAO = applicationContext.getBean(EventScheduleDAO.class,tenantId);	
		applicationContext.getBean(ClassroomComponent.class,tenantId);	
		
		UpdateEventScheduleResponse resp = new UpdateEventScheduleResponse();
		EventSchedule eventScheudle = req.getEventSchedule();
		try 
		{
			/*
			 * validate
			 */
			ErrorInfo errorInfo = this.validateEventSchedule(eventScheudle);
						
			if (errorInfo.getErrors() != null && !errorInfo.getErrors().isEmpty())
			{
				resp.setErrorInfo(errorInfo);
				return resp;
			}
			
			boolean updateSucceded = eventScheduleDAO.updateEventSchedule(req.getEventSchedule());
			if (updateSucceded)
			{
				resp.setStatus(StatusType.SUCCESS);
				resp.setEventSchedule(req.getEventSchedule());
			}
			else
			{
				resp.setStatus(StatusType.FAILED);
				resp.setErrorInfo(CommonUtils.buildErrorInfo("FAILED", "Database was not updated! Please check your input"));
				resp.setMsg("Updates to DB failed , please try again or contact support ");

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
	public UpdateEventScheduleSessionIdResponse updateEventScheduleSessionId(UpdateEventScheduleSessionIdRequest req)
	{
		EventScheduleDAO eventScheduleDAO = applicationContext.getBean(EventScheduleDAO.class,tenantId);	
		applicationContext.getBean(ClassroomComponent.class,tenantId);	
		
		UpdateEventScheduleSessionIdResponse resp = new UpdateEventScheduleSessionIdResponse();
		try 
		{									
			/*
			 * validation
			 */
			if (StringUtils.isBlank(req.getEventScheduleId()) ||
				StringUtils.isBlank(req.getEventSessionId()))
			{
				ErrorInfo errorInfo = new ErrorInfo();
				if (StringUtils.isBlank(req.getEventScheduleId()))
					errorInfo.getErrors().add(CommonUtils.buildErr("eventScheduleId", "This is a mandatory field."));
				
				if (StringUtils.isBlank(req.getEventSessionId()))
					errorInfo.getErrors().add(CommonUtils.buildErr("eventSessionId", "This is a mandatory field."));
				
				resp.setErrorInfo(errorInfo);
				return resp;
			}
			
			/*
			 * update
			 */
			boolean updateSucceded = eventScheduleDAO.updateEventScheduleSession(req.getEventScheduleId(), req.getEventSessionId(), req.getUserRecordId());
			if (updateSucceded)
			{
				resp.setStatus(StatusType.SUCCESS);
				resp.setEventSchedule(eventScheduleDAO.getEventScheduleById(req.getEventScheduleId()));
			}
			else
			{
				resp.setStatus(StatusType.FAILED);
				resp.setErrorInfo(CommonUtils.buildErrorInfo("FAILED", "Database was not updated! Please check your input"));
				resp.setMsg("Updates to DB failed , please try again or contact support ");

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
	public DeleteEventScheduleResponse deleteEventSchedule(DeleteEventScheduleRequest req)
	{
		EventScheduleDAO eventScheduleDAO = applicationContext.getBean(EventScheduleDAO.class,tenantId);	
		applicationContext.getBean(ClassroomComponent.class,tenantId);	
		
		DeleteEventScheduleResponse resp = new DeleteEventScheduleResponse();
		try 
		{
			//TODO validation
			
			boolean updateSucceded = eventScheduleDAO.deleteEventSchedule(req.getId());
			if (updateSucceded)
			{
				resp.setStatus(StatusType.SUCCESS);
			}
			else
			{
				resp.setStatus(StatusType.FAILED);
				resp.setErrorInfo(CommonUtils.buildErrorInfo("FAILED", "Database was not updated! Please check your input"));
				resp.setMsg("Updates to DB failed , please try again or contact support ");
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
	 * @param formParams
	 * @param id
	 * @param updatedByUser
	 * @return
	 */
	public UpdateEventScheduleResponse updateEventSchedule(MultivaluedMap<String, String> formParams, String id, String updatedByUser) 
	{
		EventScheduleDAO eventScheduleDAO = applicationContext.getBean(EventScheduleDAO.class,tenantId);	
		
		UpdateEventScheduleResponse resp = new UpdateEventScheduleResponse();
		try 
		{
			/*
			 * validate
			 */
			EventSchedule currentEventSchedule = eventScheduleDAO.getEventScheduleById(id);
			if (currentEventSchedule == null)
			{
				resp.setErrorInfo(CommonUtils.buildErrorInfo("id", "Event was not found! Please check your input!"));
			}
			else
			{
				this.mapFormFields(formParams, currentEventSchedule);
				UpdateEventScheduleRequest req = new UpdateEventScheduleRequest();
				currentEventSchedule.setUpdatedBy(updatedByUser);
				currentEventSchedule.setLastUpdatedDateTime(CommonUtils.getXMLGregorianCalendarNow());
				req.setEventSchedule(currentEventSchedule);
				resp = this.updateEventSchedule(req);
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
	 * @param formParams
	 * @param eventSchedule
	 */
	public void mapFormFields(MultivaluedMap<String, String> formParams, EventSchedule eventSchedule) 
	{
		applicationContext.getBean(EventScheduleDAO.class,tenantId);	
		applicationContext.getBean(ClassroomComponent.class,tenantId);	
		
		for (String key: formParams.keySet())
		{
			if (StringUtils.equals(key, "id"))
				eventSchedule.setId(formParams.getFirst("id"));
					
			if (StringUtils.equals(key, "classroomid"))
				eventSchedule.setClassroomid(formParams.getFirst("classroomid"));
			
			if (StringUtils.equals(key, "eventDescription"))
				eventSchedule.setEventDescription(formParams.getFirst("eventDescription"));
											
			if (StringUtils.equals(key, "eventEndDate"))
				eventSchedule.setEventEndDate(CommonUtils.getXMLGregorianCalendarFromString(formParams.getFirst("eventEndDate"), "yyyy-MM-dd HH:mm"));
			
			if (StringUtils.equals(key, "eventStartDate"))
				eventSchedule.setEventStartDate(CommonUtils.getXMLGregorianCalendarFromString(formParams.getFirst("eventStartDate"), "yyyy-MM-dd HH:mm"));
					
			if (StringUtils.equals(key, "eventTitle"))
				eventSchedule.setEventTitle(formParams.getFirst("eventTitle"));
						
			if (StringUtils.equals(key, "eventStatus"))
				eventSchedule.setEventStatus(EventStatusType.fromValue(formParams.getFirst("eventStatus")));
			
			if (StringUtils.equals(key, "eventType"))
				eventSchedule.setEventType(EventType.fromValue(formParams.getFirst("eventType")));
			
						
		}
								
	}
	
	
	private ErrorInfo validateEventSchedule(EventSchedule eventScheudle) 
	{
		ErrorInfo errorInfo = new ErrorInfo();
		List<Err> errs = new ArrayList<Err>();
		
		if (eventScheudle == null)
			errs.add(CommonUtils.buildErr("INVALID", "No event schedule found in the request!"));
		
		this.isValidEventDesc(eventScheudle.getEventDescription(), false, errs);
		this.isValidEventEndDate(eventScheudle.getEventEndDate(), true, errs);
		this.isValidEventStartDate(eventScheudle.getEventStartDate(), true, errs);
		this.isValidEventStatus(eventScheudle.getEventStatus(), false, errs);
		this.isValidEventTitle(eventScheudle.getEventTitle(), true, errs);
		this.isValidEventType(eventScheudle.getEventType(), true, errs);
		
		for (Err er: errs)
		{
			if (er != null)
				errorInfo.getErrors().add(er);
		}
		
		return errorInfo;
	}

	
	
	/**
	 * 
	 * @param eventType
	 * @param checkMandatory
	 * @return
	 */
	public boolean isValidEventType(EventType eventType, boolean checkMandatory, List<Err> errors) 
	{
		if (checkMandatory && eventType == null)
		{			
			errors.add(CommonUtils.buildErr("eventType", "is missing, Please provide a valid value"));
			return false;
		}
		
		return true;
//		try
//		{
//			EventType.fromValue(eventType);
//			return null;
//		}
//		catch(Exception ex)
//		{
//			return CommonUtils.buildErr("eventType", eventType+" is not a valid input. Please provide a valid value");
//		}
						
	}
	
	
	public boolean isValidEventStatus(EventStatusType eventStatusType, boolean checkMandatory, List<Err> errors) 
	{
	
		if (checkMandatory && eventStatusType == null)
		{			
			errors.add(CommonUtils.buildErr("eventStatus", "is missing, Please provide a valid value"));
			return false;
		}
		
		return true;
		
//		
//		try
//		{
//			EventStatusType.fromValue(eventStatusType);
//			return null;
//		}
//		catch(Exception ex)
//		{
//			return CommonUtils.buildErr("eventStatus", eventStatusType+" is not a valid input. Please provide a valid value");
//		}
		
	}
	
	
	public boolean isValidEventTitle(String val, boolean checkMandatory, List<Err> errors) 
	{
		if (checkMandatory && StringUtils.isBlank(val)) 
		{			
			errors.add(CommonUtils.buildErr("eventTitle", "is missing, Please provide a valid value"));
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * 
	 * @param val
	 * @param checkMandatory
	 * @return
	 */
	public boolean isValidEventStartDate(XMLGregorianCalendar val, boolean checkMandatory, List<Err> errors) 
	{
		Err err = ValidationUtils.isValidDate("eventStartDate", val, "yyyy-MM-dd", checkMandatory);
		if (err != null)
		{
			if (errors != null)
				errors.add(err);
			return false;
		}
		
		return true;		
	}
	
	public boolean isValidEventEndDate(XMLGregorianCalendar val, boolean checkMandatory, List<Err> errors) 
	{
		Err err = ValidationUtils.isValidDate("eventEndDate", val,"yyyy-MM-dd", checkMandatory);
		if (err != null)
		{
			if (errors != null)
			errors.add(err);
			return false;
		}
		
		return true;
			
	}
	
	public boolean isValidEventDesc(String val, boolean checkMandatory, List<Err> errors) 
	{
		if (checkMandatory && StringUtils.isBlank(val))
		{
			errors.add(CommonUtils.buildErr("eventTitle", "is missing, Please provide a valid value"));
			return false;
		}
		
		return true;
		
	}
	
	public boolean isValidEventRecordId(String val, boolean checkMandatory, List<Err> errors) 
	{
		ClassroomComponent classroomComp = applicationContext.getBean(ClassroomComponent.class,tenantId);	
		
		if (checkMandatory && StringUtils.isBlank(val))
		{
			errors.add(CommonUtils.buildErr("eventTitle", "is missing, Please provide a valid value"));
			return false;
		}
		
		return classroomComp.checkClassroomidExists(val,errors);
		
		
	}
	
	public boolean isValidId(String val, boolean checkMandatory, List<Err> errors) 
	{
		EventScheduleDAO eventScheduleDAO = applicationContext.getBean(EventScheduleDAO.class,tenantId);
		if (errors == null)
			errors = new ArrayList<Err>();
		
		if (checkMandatory && StringUtils.isBlank(val)) 
		{
			errors.add(CommonUtils.buildErr("id", "is missing, Please provide a valid value"));
			return false;
		}
		
		try 
		{
			if (eventScheduleDAO.getEventScheduleById(val) == null)
			{
				errors.add(CommonUtils.buildErr("id", "Did not find any schedule with id = "+val));
				return false;
			}
			else;
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
			errors.add(CommonUtils.buildErr("classroomid", "Could not locate classroom = "+val+" due to db exception, reason :"+e.getMessage()));
			return false;
		}		
	
		return true;
		
	}

	/**
	 * 
	 * @param req
	 * @return
	 */
	public AllowedActionsResponse getAllowedActions(AllowedActionsRequest req) 
	{
		AllowedActionsResponse allowedActions = new AllowedActionsResponse();
		
		allowedActions.getActions().add(checkAllowedAction("EDIT",req.getRecordId()));
		allowedActions.getActions().add(checkAllowedAction("DELETE",req.getRecordId()));
		allowedActions.getActions().add(checkAllowedAction("JOIN",req.getRecordId()));
		
		
		return allowedActions;
	}
	
	/**
	 * 
	 * @param actionType
	 * @param recordId
	 * @return
	 */
	private UserAllowedAction checkAllowedAction(String actionType,String recordId)
	{
		boolean allowed = false;
		
		if ("EDIT".equals(actionType))
		{
			allowed = true;
		}
		else if ("DELETE".equals(actionType))
		{
			allowed = true;
		}		
		else if ("JOIN".equals(actionType))
		{
			allowed = true;
		}
		
		
		if (allowed)
		{
			UserAllowedAction userActionAllowed = new UserAllowedAction();
			userActionAllowed.setType(actionType);
			userActionAllowed.setLink("/eventschedule/"+actionType.toLowerCase()+"/"+recordId);
			return userActionAllowed;

		}			
		
		return null;
		
	}


	public boolean isOwnerOfSchedule(String userid, String eventScheduleId, List<Err> errors) 
	{		
		EventScheduleDAO eventScheduleDAO = applicationContext.getBean(EventScheduleDAO.class,tenantId);	
				
		try 
		{
			EventSchedule sche = eventScheduleDAO.getEventScheduleById(eventScheduleId);
			if (sche == null)
			{
				if (errors !=null)
					errors.add(CommonUtils.buildErr("id", "Did not find any schedule with id = "+eventScheduleId));
					
				return false;
			}
			else;
			
			return this.isOwnerOfSchedule(sche, userid,errors);
			
		} 
		catch (Exception e) 
		{		
			if (errors !=null)
				errors.add(CommonUtils.buildErr("eventScheduleId", "Could not locate event schedule = "+eventScheduleId+" due to db exception! Contact support!"));
		}		
	
		return true;
		
	}
	
	
	public boolean isOwnerOfSchedule(EventSchedule sche, String userid, List<Err> errors) 
	{		
		if (sche == null)
		{
			return false;
		}
		
		if (!StringUtils.equalsIgnoreCase(userid, sche.getUpdatedBy()))
		{
			if (errors !=null)
				errors.add(CommonUtils.buildErr("userid", "User "+userid+" is not the owner of this schedule!"));
			
			return false;
		}
			
	
		return true;
	}
	
	
}
