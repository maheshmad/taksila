//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.09 at 08:28:21 AM EDT 
//


package com.taksila.veda.model.db.config.v1_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfigId.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ConfigId">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DB_JNDI"/>
 *     &lt;enumeration value="SMTP_HOST_URL"/>
 *     &lt;enumeration value="SMTP_AUTH_ID"/>
 *     &lt;enumeration value="SMTP_AUTH_PSWD"/>
 *     &lt;enumeration value="SMTP_TRANSPORT_PROTOCOL"/>
 *     &lt;enumeration value="SMTP_SOCKET_FACTORY_PORT"/>
 *     &lt;enumeration value="SMTP_SOCKET_FACTORY_CLASS"/>
 *     &lt;enumeration value="SMTP_ENABLE_AUTHENTICATION"/>
 *     &lt;enumeration value="SMTP_ENABLE_START_TLS"/>
 *     &lt;enumeration value="SMTP_PORT"/>
 *     &lt;enumeration value="SMTP_DEBUG"/>
 *     &lt;enumeration value="SMTP_PSWD_RESET_URL_CONTEXT_ROOT"/>
 *     &lt;enumeration value="MQ_HOST_URL"/>
 *     &lt;enumeration value="MQ_AUTH_ID"/>
 *     &lt;enumeration value="MQ_AUTH_PSWD"/>
 *     &lt;enumeration value="GOOGLE_MAPS_API_KEY"/>
 *     &lt;enumeration value="GOOGLE_MAPS_API_BASE_URL"/>
 *     &lt;enumeration value="GOOGLE_MAPS_CLIENT_ID"/>
 *     &lt;enumeration value="GOOGLE_MAPS_API_KEY_SECRET"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConfigId")
@XmlEnum
public enum ConfigId {

    DB_JNDI,
    SMTP_HOST_URL,
    SMTP_AUTH_ID,
    SMTP_AUTH_PSWD,
    SMTP_TRANSPORT_PROTOCOL,
    SMTP_SOCKET_FACTORY_PORT,
    SMTP_SOCKET_FACTORY_CLASS,
    SMTP_ENABLE_AUTHENTICATION,
    SMTP_ENABLE_START_TLS,
    SMTP_PORT,
    SMTP_DEBUG,
    SMTP_PSWD_RESET_URL_CONTEXT_ROOT,
    MQ_HOST_URL,
    MQ_AUTH_ID,
    MQ_AUTH_PSWD,
    GOOGLE_MAPS_API_KEY,
    GOOGLE_MAPS_API_BASE_URL,
    GOOGLE_MAPS_CLIENT_ID,
    GOOGLE_MAPS_API_KEY_SECRET;

    public String value() {
        return name();
    }

    public static ConfigId fromValue(String v) {
        return valueOf(v);
    }

}
