//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.09 at 08:22:33 AM EDT 
//


package com.taksila.veda.model.db.event_schedule_mgmt.v1_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EventStatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EventStatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IN_PROGRESS"/>
 *     &lt;enumeration value="NOT_STARTED"/>
 *     &lt;enumeration value="LOCKED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EventStatusType")
@XmlEnum
public enum EventStatusType {

    IN_PROGRESS,
    NOT_STARTED,
    LOCKED;

    public String value() {
        return name();
    }

    public static EventStatusType fromValue(String v) {
        return valueOf(v);
    }

}
