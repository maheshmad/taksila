//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.09 at 10:21:18 AM EDT 
//


package com.taksila.veda.model.api.base.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.taksila.veda.model.api.usermgmt.v1_0.SearchUserRequest;


/**
 * 
 * 				SearchResponse is a generic class for getting search results of 
 * 				any type of records				
 * 			
 * 
 * <p>Java class for SearchRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.taksila.com/veda/model/api/base/v1_0}BaseRequest">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.taksila.com/veda/model/api/base/v1_0}recordType"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/api/base/v1_0}page"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/api/base/v1_0}pageOffset"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/api/base/v1_0}query"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchRequest", propOrder = {
    "recordType",
    "page",
    "pageOffset",
    "query"
})
@XmlSeeAlso({
    SearchUserRequest.class
})
public class SearchRequest
    extends BaseRequest
{

    @XmlElement(required = true)
    protected String recordType;
    protected int page;
    protected int pageOffset;
    @XmlElement(required = true)
    protected String query;

    /**
     * Gets the value of the recordType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordType() {
        return recordType;
    }

    /**
     * Sets the value of the recordType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordType(String value) {
        this.recordType = value;
    }

    /**
     * Gets the value of the page property.
     * 
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the value of the page property.
     * 
     */
    public void setPage(int value) {
        this.page = value;
    }

    /**
     * Gets the value of the pageOffset property.
     * 
     */
    public int getPageOffset() {
        return pageOffset;
    }

    /**
     * Sets the value of the pageOffset property.
     * 
     */
    public void setPageOffset(int value) {
        this.pageOffset = value;
    }

    /**
     * Gets the value of the query property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the value of the query property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuery(String value) {
        this.query = value;
    }

}
