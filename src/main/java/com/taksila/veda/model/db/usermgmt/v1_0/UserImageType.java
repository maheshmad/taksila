//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.16 at 11:52:48 AM EDT 
//


package com.taksila.veda.model.db.usermgmt.v1_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserImageType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UserImageType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PROFILE_IMAGE"/>
 *     &lt;enumeration value="SOCIAL_IMAGE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UserImageType")
@XmlEnum
public enum UserImageType {

    PROFILE_IMAGE,
    SOCIAL_IMAGE;

    public String value() {
        return name();
    }

    public static UserImageType fromValue(String v) {
        return valueOf(v);
    }

}
