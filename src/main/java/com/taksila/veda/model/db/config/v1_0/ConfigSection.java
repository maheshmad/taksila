//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.09 at 08:28:21 AM EDT 
//


package com.taksila.veda.model.db.config.v1_0;

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
 * 				Config section table stores all the config groups.						
 * 			
 * 
 * <p>Java class for ConfigSection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfigSection">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.taksila.com/veda/model/db/base/v1_0}BaseTable">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/config/v1_0}sectionName"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/config/v1_0}viewXtype"/>
 *         &lt;element name="configGroups" type="{http://www.taksila.com/veda/model/db/config/v1_0}ConfigGroup" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/base/v1_0}allowedRoles" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigSection", propOrder = {
    "sectionName",
    "viewXtype",
    "configGroups",
    "allowedRoles"
})
public class ConfigSection
    extends BaseTable
{

    @XmlElement(required = true)
    protected String sectionName;
    @XmlElement(required = true)
    protected String viewXtype;
    @XmlElement(required = true)
    protected List<ConfigGroup> configGroups;
    @XmlElement(namespace = "http://www.taksila.com/veda/model/db/base/v1_0", required = true)
    @XmlSchemaType(name = "string")
    protected List<UserRole> allowedRoles;

    /**
     * Gets the value of the sectionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * Sets the value of the sectionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSectionName(String value) {
        this.sectionName = value;
    }

    /**
     * Gets the value of the viewXtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViewXtype() {
        return viewXtype;
    }

    /**
     * Sets the value of the viewXtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViewXtype(String value) {
        this.viewXtype = value;
    }

    /**
     * Gets the value of the configGroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the configGroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfigGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConfigGroup }
     * 
     * 
     */
    public List<ConfigGroup> getConfigGroups() {
        if (configGroups == null) {
            configGroups = new ArrayList<ConfigGroup>();
        }
        return this.configGroups;
    }

    /**
     * Gets the value of the allowedRoles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allowedRoles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllowedRoles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserRole }
     * 
     * 
     */
    public List<UserRole> getAllowedRoles() {
        if (allowedRoles == null) {
            allowedRoles = new ArrayList<UserRole>();
        }
        return this.allowedRoles;
    }

}
