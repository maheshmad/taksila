package com.taksila.veda.email;

import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.taksila.veda.config.ConfigComponent;
import com.taksila.veda.model.db.config.v1_0.ConfigId;
import com.taksila.veda.utils.CommonUtils;

@Component
@Scope(value="prototype")
public class EmailUtils
{	
	static Logger logger = LogManager.getLogger(EmailUtils.class.getName());	
	Map<ConfigId, String> configsMap = null;	
	String tenantId;
	public String userid;
	public String cred;
	
	@Autowired
	ApplicationContext applicationContext;
	

	@Autowired
	public EmailUtils(@Value("tenantId") String tenantId) 
	{
		logger.trace(">>>>>>>>>>>>>>>> Creating EmailUtils bean for tenant "+tenantId);		
		this.tenantId = tenantId;	
	}
	
	class SMTPAuthenticator extends javax.mail.Authenticator 
	{	   
	    public PasswordAuthentication getPasswordAuthentication() 
	    {	       
	       return new PasswordAuthentication(userid, cred);
	    }
	}
	
	public void sendSupportMail(String to,  String subject, String msg,String cc) throws Exception
	{
		ConfigComponent configComp = applicationContext.getBean(ConfigComponent.class,this.tenantId);	
		this.sendMail(to, configComp.getConfig(ConfigId.SUPPORT_EMAIL_ID), subject, msg, cc);
	}
	
	public void sendMail(String to, String from, String subject, String msg,String cc) throws Exception
	{		   
		   ConfigComponent configComp = applicationContext.getBean(ConfigComponent.class,this.tenantId);	
		   
		   Properties props = new Properties();
	       props.put("mail.transport.protocol", configComp.getConfig(ConfigId.SMTP_TRANSPORT_PROTOCOL));
	       props.put("mail.smtp.host", configComp.getConfig(ConfigId.SMTP_HOST_URL));
	       props.put("mail.smtp.socketFactory.port", configComp.getConfig(ConfigId.SMTP_SOCKET_FACTORY_PORT));  
	       props.put("mail.smtp.socketFactory.class",  configComp.getConfig(ConfigId.SMTP_SOCKET_FACTORY_CLASS));  
	       props.put("mail.smtp.auth", configComp.getConfig(ConfigId.SMTP_ENABLE_AUTHENTICATION));  
	       props.put("mail.smtp.starttls.enable", configComp.getConfig(ConfigId.SMTP_ENABLE_START_TLS));
	       props.put("mail.smtp.port", configComp.getConfig(ConfigId.SMTP_PORT)); 
	       props.put("mail.smtp.localhost", "mail.altaireworks.com");
	       props.setProperty("mail.smtp.ssl.trust", "*");
	       props.put("mail.smtp.ssl.protocols", "TLSv1.2");            


	       logger.trace(CommonUtils.toJson(props));
	       this.userid =  configComp.getConfig(ConfigId.SMTP_AUTH_ID);
	       this.cred =  configComp.getConfig(ConfigId.SMTP_AUTH_PSWD);
	       
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
		   transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		   transport.close();
		
	}
   
}
