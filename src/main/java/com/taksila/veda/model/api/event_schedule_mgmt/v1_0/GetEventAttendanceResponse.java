//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.18 at 06:25:47 PM EST 
//


package com.taksila.veda.model.api.event_schedule_mgmt.v1_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.taksila.veda.model.api.base.v1_0.BaseResponse;
import com.taksila.veda.model.db.event_schedule_mgmt.v1_0.EventAttendance;


/**
 * 
 * 				This represents the api structure of the GetEventAttendanceResponse response 				
 * 			
 * 
 * <p>Java class for GetEventAttendanceResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetEventAttendanceResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.taksila.com/veda/model/api/base/v1_0}BaseResponse">
 *       &lt;sequence>
 *         &lt;element name="attendance" type="{http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0}EventAttendance" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetEventAttendanceResponse", propOrder = {
    "attendance"
})
public class GetEventAttendanceResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected List<EventAttendance> attendance;

    /**
     * Gets the value of the attendance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attendance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttendance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EventAttendance }
     * 
     * 
     */
    public List<EventAttendance> getAttendance() {
        if (attendance == null) {
            attendance = new ArrayList<EventAttendance>();
        }
        return this.attendance;
    }

}
