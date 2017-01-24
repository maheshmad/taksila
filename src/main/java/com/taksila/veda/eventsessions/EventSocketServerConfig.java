package com.taksila.veda.eventsessions;

import java.security.Principal;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.taksila.veda.utils.CommonUtils;

@Configuration
@EnableWebSocketMessageBroker
public class EventSocketServerConfig extends AbstractWebSocketMessageBrokerConfigurer  
{
	 @Override
	 public void configureClientInboundChannel(ChannelRegistration registration) 
	 {
	    registration.setInterceptors(new ChannelInterceptorAdapter() 
	    {
	        @Override
	        public Message<?> preSend(Message<?> message, MessageChannel channel) 
	        {
	        	StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
	        	if (accessor.getUser() == null)
	        	{
		            if (StompCommand.CONNECT.equals(accessor.getCommand())) 
		            {
		            	System.out.println("socket event headers during a connect ..."+CommonUtils.toJson(accessor));
		            	String sessionId = accessor.getFirstNativeHeader("userSessionId");
		            	Principal user = new Principal() 
		            	{						
							@Override
							public String getName() 
							{								
								return "mm3";
							}
						};
		                accessor.setUser(user);
		            }
	        	}
	        	else
	        		System.out.println("****** found user principal = " +accessor.getUser().getName());

	            return message;
	        }
	    });
	  }
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) 
	{
		CommonUtils.logEyeCatchingMessage("****** registering stomp endpoints....",false);
		registry.addEndpoint("/veda-eventsession-wsocket").setAllowedOrigins("*").withSockJS().setSessionCookieNeeded(true);		
	}
	
	@Override
    public void configureMessageBroker(MessageBrokerRegistry config) 
	{
		CommonUtils.logEyeCatchingMessage("****** configuring Message broker....",false);
		config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/veda");
    }

	
}
