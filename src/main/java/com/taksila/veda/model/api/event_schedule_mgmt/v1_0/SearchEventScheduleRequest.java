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
import com.taksila.veda.model.api.base.v1_0.SearchRequest;


/**
 * 
 * 				This represents the api structure of the SearchEventScheduleRequest				
 * 			
 * 
 * <p>Java class for SearchEventScheduleRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchEventScheduleRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.taksila.com/veda/model/api/base/v1_0}SearchRequest">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/classroom/v1_0}classroomid"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchEventScheduleRequest", propOrder = {
    "classroomid"
})
public class SearchEventScheduleRequest
    extends SearchRequest
{

    @XmlElement(namespace = "http://www.taksila.com/veda/model/db/classroom/v1_0", required = true)
    protected String classroomid;

    /**
     * Gets the value of the classroomid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassroomid() {
        return classroomid;
    }

    /**
     * Sets the value of the classroomid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassroomid(String value) {
        this.classroomid = value;
    }

}
