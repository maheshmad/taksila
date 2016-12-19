//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.18 at 06:25:47 PM EST 
//


package com.taksila.veda.model.api.event_schedule_mgmt.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.taksila.veda.model.api.base.v1_0.BaseRequest;
import com.taksila.veda.model.db.event_schedule_mgmt.v1_0.EventAttendance;


/**
 * 
 * 				This represents the api structure of the UpdateEventAttendanceRequest				
 * 			
 * 
 * <p>Java class for UpdateEventAttendanceRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateEventAttendanceRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.taksila.com/veda/model/api/base/v1_0}BaseRequest">
 *       &lt;sequence>
 *         &lt;element name="attendance" type="{http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0}EventAttendance"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateEventAttendanceRequest", propOrder = {
    "attendance"
})
public class UpdateEventAttendanceRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected EventAttendance attendance;

    /**
     * Gets the value of the attendance property.
     * 
     * @return
     *     possible object is
     *     {@link EventAttendance }
     *     
     */
    public EventAttendance getAttendance() {
        return attendance;
    }

    /**
     * Sets the value of the attendance property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventAttendance }
     *     
     */
    public void setAttendance(EventAttendance value) {
        this.attendance = value;
    }

}
