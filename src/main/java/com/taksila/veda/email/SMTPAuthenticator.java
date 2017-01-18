package com.taksila.veda.email;

import javax.mail.PasswordAuthentication;

import com.taksila.veda.config.ConfigComponent;
import com.taksila.veda.model.db.config.v1_0.ConfigId;
import com.taksila.veda.utils.AppEnvConfig;

class SMTPAuthenticator extends javax.mail.Authenticator 
{	   
    public PasswordAuthentication getPasswordAuthentication() 
    {
       String username = AppEnvConfig.SMTP_AUTH_ID;
       String password = AppEnvConfig.SMTP_AUTH_PSWD;
       return new PasswordAuthentication(username, password);
    }
}