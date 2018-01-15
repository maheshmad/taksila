package com.taksila.veda.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class SysAdminConfig  
{
    @Autowired
    private Environment env;
 
//	public static String SMTP_HOST_URL="localhost";
//	public static String SMTP_AUTH_ID="support@localhost";
//	public static String SMTP_AUTH_PSWD="support@123";
//	public static String SMTP_TRANSPORT_PROTOCOL="smtp";
//	public static String SMTP_SOCKET_FACTORY_PORT="587";
//	public static String SMTP_SOCKET_FACTORY_CLASS="javax.net.ssl.SSLSocketFactory";
//	public static String SMTP_ENABLE_AUTHENTICATION="true";
//	public static String SMTP_ENABLE_START_TLS="true";
//	public static String SMTP_PORT="587";
//	public static String SMTP_DEBUG="true";
	
	public static String SMTP_HOST_URL="smtp.gmail.com";
	public static String SMTP_AUTH_ID="contact.taxila@gmail.com";
	public static String SMTP_AUTH_PSWD="xrWQIo0wwc";
	public static String SMTP_TRANSPORT_PROTOCOL="smtp";
	public static String SMTP_SOCKET_FACTORY_PORT="587";
	public static String SMTP_SOCKET_FACTORY_CLASS="javax.net.ssl.SSLSocketFactory";
	public static String SMTP_ENABLE_AUTHENTICATION="true";
	public static String SMTP_ENABLE_START_TLS="true";
	public static String SMTP_PORT="587";
	public static String SMTP_DEBUG="true";
	
	public static String SMTP_PSWD_RESET_URL_CONTEXT_ROOT="";

	public static String MQ_HOST_URL="";
	public static String MQ_AUTH_ID="";
	public static String MQ_AUTH_PSWD="";

	public static String GOOGLE_MAPS_API_KEY="";
	public static String GOOGLE_MAPS_API_BASE_URL="";
	public static String GOOGLE_MAPS_CLIENT_ID="";
	public static String GOOGLE_MAPS_API_KEY_SECRET="";

	public static String GENERAL_DOMAIN_ROOT="demo.localhost/xe1";
	public static String TEMP_FILE_PATH="C:\\files\\upload\\";
    
}
