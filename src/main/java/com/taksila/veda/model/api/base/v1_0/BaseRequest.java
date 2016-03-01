//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.29 at 07:36:10 PM EST 
//


package com.taksila.veda.model.api.base.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.taksila.veda.model.api.security.v1_0.GetUserInfoRequest;
import com.taksila.veda.model.api.security.v1_0.ResetPasswordRequest;
import com.taksila.veda.model.api.security.v1_0.SearchUsersRequest;


/**
 * <p>Java class for BaseRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.taksila.com/veda/model/api/base/v1_0}txnId"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseRequest", propOrder = {
    "txnId"
})
@XmlSeeAlso({
    ResetPasswordRequest.class,
    SearchUsersRequest.class,
    GetUserInfoRequest.class
})
public class BaseRequest {

    @XmlElement(required = true)
    protected String txnId;

    /**
     * Gets the value of the txnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxnId() {
        return txnId;
    }

    /**
     * Sets the value of the txnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxnId(String value) {
        this.txnId = value;
    }

}
