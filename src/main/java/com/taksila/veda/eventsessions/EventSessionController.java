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
import com.taksila.veda.model.api.event_session.v1_0.StartEventSessionRequest;
import com.taksila.veda.model.api.event_session.v1_0.StartEventSessionResponse;
import com.taksila.veda.socket.services.SocketEvent;
import com.taksila.veda.socket.services.SocketEvent.SocketEventType;
import com.taksila.veda.utils.CommonUtils;



@Controller
public class EventSessionController 
{
	@Autowired
	ApplicationContext applicationContext;	
	
	static Logger logger = LogManager.getLogger(ServletUtils.class.getName());
	
	@MessageMapping("/topic/sessionmessages/{eventSessionid}")	
	@SendTo("/topic/sessionmessages/{eventSessionid}")
	public SocketEvent messageHandler(StompHeaderAccessor accessor,SimpMessageHeaderAccessor headerAccessor, SocketEvent eventMsg)
	{		
		System.out.println("socket event recived ..."+CommonUtils.toJson(eventMsg)+" , full header = "+CommonUtils.toJson(accessor));
		String tenantId = ServletUtils.getSubDomain(accessor.getSessionAttributes());
		ClassRoomSessionComponent classRoomSessionComponent = applicationContext.getBean(ClassRoomSessionComponent.class,tenantId);
//		Principal principal = headerAccessor.getUser();
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
					break;
				case CONNECT_SUCCESS:
					break;
				case DATA:
					break;
				case DISCONNECTED:
					break;
				case ERROR:
					break;
				case ACTION_SESSION_START:
					StartEventSessionRequest startEventSessionRequest = new StartEventSessionRequest();
//					startEventSessionRequest.setEventScheduleId(eventSessionid);
//					startEventSessionRequest.setUserRecordId(value);
					StartEventSessionResponse startEventSessionResponse = classRoomSessionComponent.startEventSession(startEventSessionRequest);
					if (startEventSessionResponse != null &&
						startEventSessionResponse.getErrorInfo() != null && 
						startEventSessionResponse.getErrorInfo().getErrors().isEmpty())	
					{
						eventMsgReply.setType(SocketEventType.ACTION_SUCCESS);
					}
					else
						eventMsgReply.setMsg(startEventSessionResponse.getErrorInfo().getErrors().get(0).getErrorMsg());
						
					break;
				default:
					break;
				
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
	
	@MessageMapping("/topic/start/session/{eventScheduleId}")	
	@SendTo("/topic/general_app_message_topic")
	public SocketEvent startSessionMessageHandler(StompHeaderAccessor accessor,SimpMessageHeaderAccessor headerAccessor, @DestinationVariable String eventScheduleId, SocketEvent eventMsg)
	{				
		System.out.println("socket event recived ..."+CommonUtils.toJson(eventMsg)+ " event schedule id = "+eventScheduleId+" , full header = "+CommonUtils.toJson(accessor));
		
		String tenantId = ServletUtils.getSubDomain(accessor.getSessionAttributes());
		ClassRoomSessionComponent classRoomSessionComponent = applicationContext.getBean(ClassRoomSessionComponent.class,tenantId);
//		System.out.println("socket event headers in  startSessionMessageHandler..."+CommonUtils.toJson(headerAccessor));
//		Principal principal = headerAccessor.getUser();
		/*
		 * all event drive socket events needs to reply back to acknowledge 
		 * the message and include the response
		 * 
		 */
		SocketEvent eventMsgReply = new SocketEvent(); 
		eventMsgReply.setType(SocketEventType.ACTION_FAILED);		
		
		try
		{
			
			StartEventSessionRequest startEventSessionRequest = new StartEventSessionRequest();
			startEventSessionRequest.setUserRecordId(headerAccessor.getUser().getName());
			startEventSessionRequest.setEventScheduleId(eventScheduleId);
//					startEventSessionRequest.setUserRecordId(value);
			logger.trace("about to start a session = "+CommonUtils.toJson(startEventSessionRequest)+" tenantid = "+tenantId);

			StartEventSessionResponse startEventSessionResponse = classRoomSessionComponent.startEventSession(startEventSessionRequest);
			logger.trace("response from session start = "+CommonUtils.toJson(startEventSessionResponse));
			if (startEventSessionResponse != null &&
				startEventSessionResponse.getErrorInfo() != null && 
				startEventSessionResponse.getErrorInfo().getErrors().isEmpty())	
			{
				eventMsgReply.setType(SocketEventType.ACTION_SUCCESS);
				eventMsgReply.setMsg("Event started at "+startEventSessionResponse.getEventSession().getJoiningDateTime());
			}
			else
				eventMsgReply.setMsg(startEventSessionResponse.getErrorInfo().getErrors().get(0).getErrorMsg());
												
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			eventMsgReply.setMsg(ex.getMessage());
		}
		
		System.out.println("Sending back reply"+CommonUtils.toJson(eventMsgReply));
		
		return eventMsgReply;		
	}
	
	
	
}
