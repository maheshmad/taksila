//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.16 at 11:52:48 AM EDT 
//


package com.taksila.veda.model.api.usermgmt.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.taksila.veda.model.api.base.v1_0.BaseResponse;
import com.taksila.veda.model.db.usermgmt.v1_0.UserImageInfo;


/**
 * 
 * 				This represents the api structure of the UploadUserImageResponse response 				
 * 			
 * 
 * <p>Java class for UploadUserImageResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UploadUserImageResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.taksila.com/veda/model/api/base/v1_0}BaseResponse">
 *       &lt;sequence>
 *         &lt;element name="userImageInfo" type="{http://www.taksila.com/veda/model/db/usermgmt/v1_0}UserImageInfo"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UploadUserImageResponse", propOrder = {
    "userImageInfo"
})
public class UploadUserImageResponse
    extends BaseResponse
{

    @XmlElement(required = true)
    protected UserImageInfo userImageInfo;

    /**
     * Gets the value of the userImageInfo property.
     * 
     * @return
     *     possible object is
     *     {@link UserImageInfo }
     *     
     */
    public UserImageInfo getUserImageInfo() {
        return userImageInfo;
    }

    /**
     * Sets the value of the userImageInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserImageInfo }
     *     
     */
    public void setUserImageInfo(UserImageInfo value) {
        this.userImageInfo = value;
    }

}
