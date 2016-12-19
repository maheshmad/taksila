//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.18 at 06:25:47 PM EST 
//


package com.taksila.veda.model.db.event_schedule_mgmt.v1_0;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.taksila.veda.model.db.event_schedule_mgmt.v1_0 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EventTitle_QNAME = new QName("http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", "eventTitle");
    private final static QName _EventEndDate_QNAME = new QName("http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", "eventEndDate");
    private final static QName _EventAttendanceId_QNAME = new QName("http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", "eventAttendanceId");
    private final static QName _EventStatus_QNAME = new QName("http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", "eventStatus");
    private final static QName _EndDate_QNAME = new QName("http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", "endDate");
    private final static QName _EventScheduleId_QNAME = new QName("http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", "eventScheduleId");
    private final static QName _EventDescription_QNAME = new QName("http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", "eventDescription");
    private final static QName _UserRecordId_QNAME = new QName("http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", "userRecordId");
    private final static QName _EventType_QNAME = new QName("http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", "eventType");
    private final static QName _EventStartDate_QNAME = new QName("http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", "eventStartDate");
    private final static QName _StartDate_QNAME = new QName("http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", "startDate");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.taksila.veda.model.db.event_schedule_mgmt.v1_0
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EventAttendance }
     * 
     */
    public EventAttendance createEventAttendance() {
        return new EventAttendance();
    }

    /**
     * Create an instance of {@link EventSchedule }
     * 
     */
    public EventSchedule createEventSchedule() {
        return new EventSchedule();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", name = "eventTitle")
    public JAXBElement<String> createEventTitle(String value) {
        return new JAXBElement<String>(_EventTitle_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", name = "eventEndDate")
    public JAXBElement<XMLGregorianCalendar> createEventEndDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_EventEndDate_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", name = "eventAttendanceId")
    public JAXBElement<String> createEventAttendanceId(String value) {
        return new JAXBElement<String>(_EventAttendanceId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventStatusType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", name = "eventStatus")
    public JAXBElement<EventStatusType> createEventStatus(EventStatusType value) {
        return new JAXBElement<EventStatusType>(_EventStatus_QNAME, EventStatusType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", name = "endDate")
    public JAXBElement<XMLGregorianCalendar> createEndDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_EndDate_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", name = "eventScheduleId")
    public JAXBElement<String> createEventScheduleId(String value) {
        return new JAXBElement<String>(_EventScheduleId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", name = "eventDescription")
    public JAXBElement<String> createEventDescription(String value) {
        return new JAXBElement<String>(_EventDescription_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", name = "userRecordId")
    public JAXBElement<String> createUserRecordId(String value) {
        return new JAXBElement<String>(_UserRecordId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", name = "eventType")
    public JAXBElement<EventType> createEventType(EventType value) {
        return new JAXBElement<EventType>(_EventType_QNAME, EventType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", name = "eventStartDate")
    public JAXBElement<XMLGregorianCalendar> createEventStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_EventStartDate_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/event_schedule_mgmt/v1_0", name = "startDate")
    public JAXBElement<XMLGregorianCalendar> createStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_StartDate_QNAME, XMLGregorianCalendar.class, null, value);
    }

}
