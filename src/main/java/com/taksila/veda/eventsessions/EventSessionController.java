package com.taksila.veda.eventsessions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.taksila.servlet.utils.ServletUtils;
import com.taksila.veda.socket.services.SocketEvent;
import com.taksila.veda.socket.services.SocketEvent.SocketEventType;
import com.taksila.veda.utils.CommonUtils;



@Controller
public class EventSessionController 
{
	@Autowired
	ApplicationContext applicationContext;	
	
	static Logger logger = LogManager.getLogger(ServletUtils.class.getName());
	
	@MessageMapping("/topic/sessionmessages/{eventSessionId}")	
	@SendTo("/topic/sessionmessages/{eventSessionId}")
	public SocketEvent messageHandler(StompHeaderAccessor accessor,SimpMessageHeaderAccessor headerAccessor, @DestinationVariable String eventSessionId, SocketEvent eventMsg)
	{		
		System.out.println("socket event recived ..."+CommonUtils.toJson(eventMsg)+" , full header = "+CommonUtils.toJson(accessor));
		String tenantId = ServletUtils.getSubDomain(accessor.getSessionAttributes());
		String userId = headerAccessor.getUser().getName();
		/*
		 * all event drive socket events needs to reply back to acknowledge 
		 * the message and include the response
		 * 
		 */
		SocketEvent eventMsgReply = new SocketEvent(); 
		eventMsgReply.setType(SocketEventType.ACTION_FAILED);		
		
		try
		{
			switch (eventMsg.getType())
			{
				case ACTION:
					return this.handleAction(tenantId,userId,eventSessionId,eventMsg);
				case CONNECT_SUCCESS:
					break;
				case DATA:
					break;
				case DISCONNECTED:
					break;
				case ERROR:
					break;					
				default:
					return this.handleAction(tenantId,userId,eventSessionId,eventMsg);	
				
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			eventMsgReply.setMsg(ex.getMessage());
		}
						
		return eventMsgReply;		
	}
	
	/**
	 * 
	 * @param headerAccessor
	 * @param eventScheduleId
	 * @param eventMsg
	 * @return
	 */	
	private SocketEvent handleAction(String tenantId, String userid, String eventScheduleId, SocketEvent eventMsg)
	{				
		System.out.println("socket event recived ..."+CommonUtils.toJson(eventMsg)+ " event schedule id = "+eventScheduleId);
		
		/*
		 * all event drive socket events needs to reply back to acknowledge 
		 * the message and include the response
		 * 
		 */
		eventMsg.setFrom(userid);
		
		return eventMsg;
	}
	
	
	
//	/**
//	 * 
//	 * @param headerAccessor
//	 * @param eventScheduleId
//	 * @param eventMsg
//	 * @return
//	 */	
//	private SocketEvent startSessionMessageHandler(String tenantId, String userid, String eventScheduleId, SocketEvent eventMsg)
//	{				
//		System.out.println("socket event recived ..."+CommonUtils.toJson(eventMsg)+ " event schedule id = "+eventScheduleId);
//		
//		ClassRoomSessionComponent classRoomSessionComponent = applicationContext.getBean(ClassRoomSessionComponent.class,tenantId);
//		/*
//		 * all event drive socket events needs to reply back to acknowledge 
//		 * the message and include the response
//		 * 
//		 */
//		SocketEvent eventMsgReply = new SocketEvent(); 
//		eventMsgReply.setType(SocketEventType.ACTION_FAILED);		
//		
//		try
//		{
//			
//			StartEventSessionRequest startEventSessionRequest = new StartEventSessionRequest();
//			startEventSessionRequest.setUserRecordId(userid);
//			startEventSessionRequest.setEventScheduleId(eventScheduleId);
//			logger.trace("about to start a session = "+CommonUtils.toJson(startEventSessionRequest)+" tenantid = "+tenantId);
//
//			StartEventSessionResponse startEventSessionResponse = classRoomSessionComponent.startEventSession(startEventSessionRequest);
//			logger.trace("response from session start = "+CommonUtils.toJson(startEventSessionResponse));
//			if (startEventSessionResponse != null &&
//				startEventSessionResponse.getErrorInfo() != null && 
//				startEventSessionResponse.getErrorInfo().getErrors().isEmpty())	
//			{
//				eventMsgReply.setType(SocketEventType.ACTION_SUCCESS);
//				eventMsgReply.setMsg("Event started at "+startEventSessionResponse.getEventSession().getJoiningDateTime());
//			}
//			else
//				eventMsgReply.setMsg(startEventSessionResponse.getErrorInfo().getErrors().get(0).getErrorMsg());
//												
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//			eventMsgReply.setMsg(ex.getMessage());
//		}
//		
//		System.out.println("Sending back reply"+CommonUtils.toJson(eventMsgReply));
//		
//		return eventMsgReply;		
//	}
//	
//	
//	/**
//	 * 
//	 * @param headerAccessor
//	 * @param eventScheduleId
//	 * @param eventMsg
//	 * @return
//	 * @throws Exception 
//	 */
//	private SocketEvent joinSessionMessageHandler(String tenantId, String userid, String eventSessionId, SocketEvent eventMsg) throws Exception
//	{				
//		System.out.println("socket event recived ..."+CommonUtils.toJson(eventMsg)+ " event session id = "+eventSessionId);	
//		ClassRoomSessionComponent classRoomSessionComponent = applicationContext.getBean(ClassRoomSessionComponent.class,tenantId);
//		UserComponent userComp = applicationContext.getBean(UserComponent.class,tenantId);
//		GetUserResponse getUserResp = userComp.getUser(userid);
//		if(getUserResp == null || getUserResp.getUser() == null)
//		{
//			throw new Exception("SECURITY VIOLATION! - Unauthenticated userid = "+userid);			
//		}
//		
//		String userRecordId = getUserResp.getUser().getId();
//		/*
//		 * all event drive socket events needs to reply back to acknowledge 
//		 * the message and include the response
//		 * 
//		 */
//		SocketEvent eventMsgReply = new SocketEvent(); 
//		eventMsgReply.setType(SocketEventType.ACTION_FAILED);		
//		
//		try
//		{			
//			JoinEventSessionRequest joinEventSessionRequest = new JoinEventSessionRequest();
//			joinEventSessionRequest.setUserRecordId(userRecordId);
//			joinEventSessionRequest.setEventSessionId(eventSessionId);
//			logger.trace("about to join a session = "+CommonUtils.toJson(joinEventSessionRequest)+" tenantid = "+tenantId);
//
//			JoinEventSessionResponse joinEventSessionResponse = classRoomSessionComponent.joinSession(joinEventSessionRequest);
//			logger.trace("response from session start = "+CommonUtils.toJson(joinEventSessionResponse));
//			if (joinEventSessionResponse != null &&
//				joinEventSessionResponse.getErrorInfo() != null && 
//				joinEventSessionResponse.getErrorInfo().getErrors().isEmpty())	
//			{
//				eventMsgReply.setType(SocketEventType.ACTION_SUCCESS);
//				eventMsgReply.setMsg("Event started at "+joinEventSessionResponse.getEventSession().getJoiningDateTime());
//			}
//			else
//				eventMsgReply.setMsg(joinEventSessionResponse.getErrorInfo().getErrors().get(0).getErrorMsg());
//												
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//			eventMsgReply.setMsg(ex.getMessage());
//		}
//		
//		System.out.println("Sending back reply"+CommonUtils.toJson(eventMsgReply));
//		
//		return eventMsgReply;		
//	}
	
	
	
}
