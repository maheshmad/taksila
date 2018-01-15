package com.taksila.veda.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.springframework.stereotype.Component;

import com.taksila.veda.utils.SysAdminConfig;

@Component
public class EmailUtils
{	
	static Logger logger = LogManager.getLogger(EmailUtils.class.getName());	
	
	public void sendMail(String tenantId, String to, String from, String subject, String msg,String cc) throws Exception
	{		   
		   Properties props = new Properties();
	       props.put("mail.transport.protocol", SysAdminConfig.SMTP_TRANSPORT_PROTOCOL);
	       props.put("mail.smtp.host", SysAdminConfig.SMTP_HOST_URL);
	       props.put("mail.smtp.socketFactory.port", SysAdminConfig.SMTP_SOCKET_FACTORY_PORT);  
	       props.put("mail.smtp.socketFactory.class",  SysAdminConfig.SMTP_SOCKET_FACTORY_CLASS);  
	       props.put("mail.smtp.auth", SysAdminConfig.SMTP_ENABLE_AUTHENTICATION);  
	       props.put("mail.smtp.starttls.enable", SysAdminConfig.SMTP_ENABLE_START_TLS);
	       props.put("mail.smtp.port", SysAdminConfig.SMTP_PORT); 
	              
		   Authenticator auth = new SMTPAuthenticator();
		   Session mailSession = Session.getInstance(props, auth);
		   // uncomment for debugging infos to stdout
		   mailSession.setDebug(true);		   
		   Transport transport = mailSession.getTransport("smtp");

		   MimeMessage message = new MimeMessage(mailSession);
		   message.setContent(msg, "text/html; charset=utf-8");
//		   message.setSubject(ESAPI.validator().getValidInput("Email Subject", subject, "AlphaNumericWithSpaces", 256, false));
		   message.setSubject(subject);
		   message.setFrom(new InternetAddress(ESAPI.validator().getValidInput("From Email", from, "Email", 255, false)));
		   message.addRecipient(Message.RecipientType.TO,   new InternetAddress(ESAPI.validator().getValidInput("User Email", to, "Email", 255, false)));
		   
		   if (StringUtils.isNotBlank(cc))
			   message.addRecipient(Message.RecipientType.CC, new InternetAddress(ESAPI.validator().getValidInput("User Email", cc, "Email", 255, false)));

		   logger.trace("sending email \n"+msg);
		   transport.connect();		   
		   transport.sendMessage(message,      message.getRecipients(Message.RecipientType.TO));
		   transport.close();
		
	}
   
}
