//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.17 at 05:55:12 PM EDT 
//


package com.taksila.veda.model.api.base.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SearchHitRecord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchHitRecord">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.taksila.com/veda/model/api/base/v1_0}recordId"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/api/base/v1_0}recordTitle"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/api/base/v1_0}recordSubtitle"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchHitRecord", propOrder = {
    "recordId",
    "recordTitle",
    "recordSubtitle"
})
public class SearchHitRecord {

    @XmlElement(required = true)
    protected String recordId;
    @XmlElement(required = true)
    protected String recordTitle;
    @XmlElement(required = true)
    protected String recordSubtitle;

    /**
     * Gets the value of the recordId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * Sets the value of the recordId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordId(String value) {
        this.recordId = value;
    }

    /**
     * Gets the value of the recordTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordTitle() {
        return recordTitle;
    }

    /**
     * Sets the value of the recordTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordTitle(String value) {
        this.recordTitle = value;
    }

    /**
     * Gets the value of the recordSubtitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordSubtitle() {
        return recordSubtitle;
    }

    /**
     * Sets the value of the recordSubtitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordSubtitle(String value) {
        this.recordSubtitle = value;
    }

}
