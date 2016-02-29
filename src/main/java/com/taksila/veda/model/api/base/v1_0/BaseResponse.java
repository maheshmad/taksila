//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.29 at 03:53:26 PM EST 
//


package com.taksila.veda.model.api.base.v1_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.taksila.veda.model.api.security.v1_0.EmailPasswordResetResponse;
import com.taksila.veda.model.api.security.v1_0.GenerateVerificationTokenResponse;
import com.taksila.veda.model.api.security.v1_0.ResetPasswordResponse;


/**
 * <p>Java class for BaseResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/api/base/v1_0}status"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/api/base/v1_0}errorInfo"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/api/base/v1_0}status"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/api/base/v1_0}msg"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseResponse", propOrder = {
    "content"
})
@XmlSeeAlso({
    ResetPasswordResponse.class,
    GenerateVerificationTokenResponse.class,
    EmailPasswordResetResponse.class
})
public class BaseResponse {

    @XmlElementRefs({
        @XmlElementRef(name = "msg", namespace = "http://www.taksila.com/veda/model/api/base/v1_0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "status", namespace = "http://www.taksila.com/veda/model/api/base/v1_0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "success", namespace = "http://www.taksila.com/veda/model/api/base/v1_0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "errorInfo", namespace = "http://www.taksila.com/veda/model/api/base/v1_0", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> content;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "Status" is used by two different parts of a schema. See: 
     * line 32 of file:/C:/Users/Nxxxx/git/taksila/src/main/resources/xsd/veda_base_model_v1.0.xsd
     * line 30 of file:/C:/Users/Nxxxx/git/taksila/src/main/resources/xsd/veda_base_model_v1.0.xsd
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * {@link JAXBElement }{@code <}{@link ErrorInfo }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getContent() {
        if (content == null) {
            content = new ArrayList<JAXBElement<?>>();
        }
        return this.content;
    }

}
