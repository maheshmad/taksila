package com.taksila.veda.email;

import javax.mail.PasswordAuthentication;

import com.taksila.veda.utils.SysAdminConfig;

class SMTPAuthenticator extends javax.mail.Authenticator 
{	   
    public PasswordAuthentication getPasswordAuthentication() 
    {
       String username = SysAdminConfig.SMTP_AUTH_ID;
       String password = SysAdminConfig.SMTP_AUTH_PSWD;
       return new PasswordAuthentication(username, password);
    }
}