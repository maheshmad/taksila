package com.taksila.veda.eventsessions;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.taksila.veda.db.eventsessions.EventSessionsRepository;
import com.taksila.veda.eventschedulemgmt.EventScheduleMgmtComponent;
import com.taksila.veda.model.api.base.v1_0.Err;
import com.taksila.veda.model.api.base.v1_0.ErrorInfo;
import com.taksila.veda.model.api.event_schedule_mgmt.v1_0.UpdateEventScheduleRequest;
import com.taksila.veda.model.api.event_session.v1_0.StartEventSessionRequest;
import com.taksila.veda.model.api.event_session.v1_0.StartEventSessionResponse;
import com.taksila.veda.model.db.event_schedule_mgmt.v1_0.EventSchedule;
import com.taksila.veda.model.db.event_session.v1_0.EventSession;
import com.taksila.veda.socket.services.SocketEvent;
import com.taksila.veda.utils.CommonUtils;



@Component
@Scope(value="prototype")
public class ClassRoomSessionComponent 
{	
	static Logger logger = LogManager.getLogger(ClassRoomSessionComponent.class.getName());

	@Autowired
	ApplicationContext applicationContext;	
	String tenantId;
	
	public ClassRoomSessionComponent(@Value("tenantId") String tenantId)
	{
		this.tenantId = tenantId;
		logger.trace("building ClassRoomSessionComponent for tenantid = "+tenantId);
	}
	
//	public SocketEvent broadcastJoinMessage(String eventSessionId,SocketEvent eventMsg)
//	{		
//		SocketEvent eventMsgReply = new SocketEvent(); 
//		eventMsgReply.setType(SocketEventType.DATA);
//		eventMsgReply.setMsg("this is a classroom message for eventSessionId = "+eventSessionId);		
//		
//		return eventMsgReply;
//		
//	}
	
	public void test()
	{
		EventSessionsRepository repository = applicationContext.getBean(EventSessionsRepository.class,tenantId);
		EventSessionsRepository repository2 = applicationContext.getBean(EventSessionsRepository.class,tenantId);
		
		EventSession a = new EventSession();
		a.setUserRecordId("mm1");
		EventSession b = new EventSession();
		b.setUserRecordId("mm2");
		
		try {
			repository.save(a);			
			repository2.save(b);
			repository.save(a);			
			repository2.save(b);
			repository.save(a);	
			repository2.save(b);
			repository.save(a);	
			repository2.save(b);
			repository.save(a);
			repository2.save(b);
			repository.save(a);			
			repository2.save(b);
			repository.save(a);			
			repository2.save(b);
			repository.save(a);	
			repository2.save(b);
			repository.save(a);	
			repository2.save(b);
			repository.save(a);
			repository2.save(b);
			repository.save(a);			
			repository2.save(b);
			repository.save(a);			
			repository2.save(b);
			repository.save(a);	
			repository2.save(b);
			repository.save(a);	
			repository2.save(b);
			repository.save(a);
			repository2.save(b);
			repository.save(a);			
			repository2.save(b);
			repository.save(a);			
			repository2.save(b);
			repository.save(a);	
			repository2.save(b);
			repository.save(a);	
			repository2.save(b);
			repository.save(a);
			repository2.save(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//	public void sendMessage()
//	{
//		String eventid1 = "A12345";
//		String eventid2 = "B3434343";
//		
//		SocketEvent eventMsgReply1 = new SocketEvent(); 
//		eventMsgReply1.setType(SocketEventType.DATA);
//		eventMsgReply1.setMsg("this is a classroom message for eventSessionId = "+eventid1);	
//		
//		SocketEvent eventMsgReply2 = new SocketEvent(); 
//		eventMsgReply2.setType(SocketEventType.DATA);
//		eventMsgReply2.setMsg("this is a classroom message for eventSessionId = "+eventid2);	
//		
//		System.out.println("*** sending event message 1");
//		template.convertAndSend("/topic/sessionmessages/"+eventid1, eventMsgReply1);
//		System.out.println("*** sending event message 2");
//		template.convertAndSend("/topic/sessionmessages/"+eventid2, eventMsgReply2);
//	}
	
	/**
	 * 
	 * @param startEventSessionRequest
	 * @return
	 */
	public StartEventSessionResponse startEventSession(StartEventSessionRequest startEventSessionRequest) 
	{
		logger.trace("inside startEventSession!!!");
		EventSessionsRepository eventSessionsRepository = applicationContext.getBean(EventSessionsRepository.class,this.tenantId);
		EventScheduleMgmtComponent eventScheduleMgmtComponent = applicationContext.getBean(EventScheduleMgmtComponent.class,this.tenantId);
				
		StartEventSessionResponse startEventSessionResponse = new StartEventSessionResponse();
		String evtScheId = startEventSessionRequest.getEventScheduleId();
		String userRecid = startEventSessionRequest.getUserRecordId();
		
		SocketEvent eventMsg = new SocketEvent(); 		
		eventMsg.setId(startEventSessionRequest.getEventScheduleId());;		
		/*
		 * save the session event
		 */
		try 
		{			
			/*
			 * Perform validations			
			 *   
			 */
			startEventSessionResponse.setErrorInfo(new ErrorInfo());						
			List<Err> errors = startEventSessionResponse.getErrorInfo().getErrors();
			/*
			 * check is its a valid scheduled event
			 */
			EventSchedule eventSchedule = eventScheduleMgmtComponent.getEventSchedule(evtScheId);
			if (eventSchedule == null)
				errors.add(CommonUtils.buildErr("eventScheduleId", "Event schedule id = "+evtScheId+" is not found in db!"));
			/*
			 * check if the userid is the owner of the schedule id
			 */			
			eventScheduleMgmtComponent.isOwnerOfSchedule(eventSchedule, userRecid, errors);
			
			/*
			 * check if validations passed
			 */
			if (startEventSessionResponse.getErrorInfo().getErrors() != null &&
					!startEventSessionResponse.getErrorInfo().getErrors().isEmpty())
			{
				return startEventSessionResponse;
			}
			
			/*
			 * since validations passed, now 
			 * save the event session in db and update it in eventschedule 
			 */
			logger.trace("validations passed inside startEventSession!!!");

			String eventSessionId = this.generateEventSessionId(evtScheId, userRecid);
			EventSession newEventSession = new EventSession();
			newEventSession.setUserRecordId(userRecid);	
			newEventSession.setEventSessionId(eventSessionId);
			Boolean saveResult = eventSessionsRepository.save(newEventSession);
			if (saveResult)
			{
				startEventSessionResponse.setEventSession(newEventSession);
				/*
				 * update the schedule db
				 */
				eventSchedule.setEventSessionId(eventSessionId);			
				UpdateEventScheduleRequest updateEventScheduleReq = new UpdateEventScheduleRequest();
				updateEventScheduleReq.setEventSchedule(eventSchedule);
				updateEventScheduleReq.setUserRecordId(userRecid);
				eventScheduleMgmtComponent.updateEventSchedule(updateEventScheduleReq);
			}
			else
				startEventSessionResponse.setErrorInfo(CommonUtils.buildErrorInfo("error", "Error while updating the database! Please retry later!"));
			
//			/*
//			 * send a general app broadcast message to all users
//			 */
//			eventMsg.setType(SocketEventType.ACTION_SESSION_START);
//			eventMsg.setMsg("this is a classroom message for schedule id = "+startEventSessionRequest.getEventScheduleId());
//			template.convertAndSend(MESSAGETOPIC.TOPIC_GENERAL_APP_MESSAGE_TOPIC.value(),eventMsg);
			
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
			startEventSessionResponse.setErrorInfo(CommonUtils.buildErrorInfo(e));			
		}		
		
		
		return startEventSessionResponse;
		
		
	}
	
	
	public String generateEventSessionId(String eventScheduleId, String userid)
	{		
		return eventScheduleId+userid;		
	}
	
	
	
}
