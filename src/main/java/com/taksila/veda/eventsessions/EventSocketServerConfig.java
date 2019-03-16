package com.taksila.veda.eventsessions;

import java.security.Principal;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.taksila.servlet.utils.ServletUtils;
import com.taksila.veda.security.JwtTokenUtil;
import com.taksila.veda.utils.CommonUtils;

@Configuration
@EnableWebSocketMessageBroker
public class EventSocketServerConfig implements WebSocketMessageBrokerConfigurer	   
{
	@Autowired
	private JwtTokenUtil jwtTokenUtil; 
	
	static Logger logger = LogManager.getLogger(ServletUtils.class.getName());
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
	@Override
	 public void configureClientInboundChannel(ChannelRegistration registration) 
	 {
	    		
		registration.interceptors(new ChannelInterceptor() 
	    {
	        @Override
	        public Message<?> preSend(Message<?> message, MessageChannel channel) 
	        {	        	
	        	StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
	        	if (accessor.getUser() == null)
	        	{
		            if (StompCommand.CONNECT.equals(accessor.getCommand())) 
		            {		            	
		            	String jwtToken = accessor.getFirstNativeHeader(tokenHeader);
		            	Principal user = new Principal() 
		            	{						
							@Override
							public String getName() 
							{								
								return jwtTokenUtil.getUsernameFromToken(jwtToken);
							}
						};
		                accessor.setUser(user);
		                accessor.setHost("http://demo.localhost/xe1");
		                
		                System.out.println("socket event headers during a connect ..."+CommonUtils.toJson(accessor)+" , for user = "+user.getName());
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
		registry.addEndpoint("/veda/veda-eventsession-wsocket").setAllowedOrigins("*").
							withSockJS().
							setSessionCookieNeeded(true).
							setInterceptors(httpSessionIdHandshakeInterceptor());			
	}
	
	@Override
    public void configureMessageBroker(MessageBrokerRegistry config) 
	{
		CommonUtils.logEyeCatchingMessage("****** configuring Message broker....",false);
		config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/veda");
    }

	public class HttpSessionIdHandshakeInterceptor implements HandshakeInterceptor {

        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception 
        {
            logger.trace("beforeHandshake");
            if (request instanceof ServletServerHttpRequest) 
            {
                ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                attributes.put("host", servletRequest.getURI());
                logger.trace("***** "+servletRequest.getURI()+"************");
            }
            return true;
        }

        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                Exception ex) 
        {
            logger.trace("afterHandshake");
        }

    }

     @Bean
     public HttpSessionIdHandshakeInterceptor httpSessionIdHandshakeInterceptor() {
      return new HttpSessionIdHandshakeInterceptor();
     }
	
	
}
