package com.taksila.veda.eventsessions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.taksila.veda.db.dao.EventScheduleDAO;
import com.taksila.veda.db.dao.EventScheduleRepositoryInterface;
import com.taksila.veda.db.eventsessions.EventSessionsRepository;
import com.taksila.veda.eventschedulemgmt.EventScheduleMgmtComponent;
import com.taksila.veda.eventschedulemgmt.EventScheduleMgmtService;
import com.taksila.veda.model.api.base.v1_0.ErrorInfo;
import com.taksila.veda.model.api.event_session.v1_0.StartEventSessionRequest;
import com.taksila.veda.model.api.event_session.v1_0.StartEventSessionResponse;
import com.taksila.veda.model.db.event_session.v1_0.EventSession;
import com.taksila.veda.socket.services.SocketEvent;
import com.taksila.veda.socket.services.SocketEvent.SocketEventType;
import com.taksila.veda.utils.CommonUtils;



@Component
@Scope(value="prototype")
@Lazy(value = true)
public class EventSessionsComponent 
{	
	
	@Autowired
	ApplicationContext applicationContext;	
	String tenantId;
	
	@Autowired
	public EventSessionsComponent(@Value("tenantId") String tenantId)
	{
		this.tenantId = tenantId;
	}
	
	/**
	 * 
	 * @param startEventSessionRequest
	 * @return
	 */
//	public StartEventSessionResponse startEventSession(StartEventSessionRequest startEventSessionRequest) 
//	{
//		
//		
//		
//	}
		
	
	
	
}
