//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.09 at 10:21:18 AM EDT 
//


package com.taksila.veda.model.db.usermgmt.v1_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.taksila.veda.model.db.base.v1_0.BaseTable;
import com.taksila.veda.model.db.base.v1_0.UserRole;


/**
 * 
 * 				UserInfo table to store the complete user information						
 * 			
 * 
 * <p>Java class for User complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="User">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.taksila.com/veda/model/db/base/v1_0}BaseTable">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/usermgmt/v1_0}emailId"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/usermgmt/v1_0}firstName"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/usermgmt/v1_0}lastName"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/usermgmt/v1_0}userPswd"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/usermgmt/v1_0}userrole" maxOccurs="unbounded"/>
 *         &lt;element name="contactInfo" type="{http://www.taksila.com/veda/model/db/usermgmt/v1_0}Contact"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User", propOrder = {
    "emailId",
    "firstName",
    "lastName",
    "userPswd",
    "userrole",
    "contactInfo"
})
public class User
    extends BaseTable
{

    @XmlElement(required = true)
    protected String emailId;
    @XmlElement(required = true)
    protected String firstName;
    @XmlElement(required = true)
    protected String lastName;
    @XmlElement(required = true)
    protected String userPswd;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected List<UserRole> userrole;
    @XmlElement(required = true)
    protected Contact contactInfo;

    /**
     * Gets the value of the emailId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * Sets the value of the emailId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailId(String value) {
        this.emailId = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the userPswd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserPswd() {
        return userPswd;
    }

    /**
     * Sets the value of the userPswd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserPswd(String value) {
        this.userPswd = value;
    }

    /**
     * Gets the value of the userrole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userrole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserrole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserRole }
     * 
     * 
     */
    public List<UserRole> getUserrole() {
        if (userrole == null) {
            userrole = new ArrayList<UserRole>();
        }
        return this.userrole;
    }

    /**
     * Gets the value of the contactInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Contact }
     *     
     */
    public Contact getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the value of the contactInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Contact }
     *     
     */
    public void setContactInfo(Contact value) {
        this.contactInfo = value;
    }

}
