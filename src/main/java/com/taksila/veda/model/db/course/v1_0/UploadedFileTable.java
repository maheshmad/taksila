//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.30 at 08:40:04 AM EDT 
//


package com.taksila.veda.model.db.course.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.taksila.veda.model.api.course.v1_0.UploadedFile;
import com.taksila.veda.model.db.base.v1_0.BaseTable;


/**
 * 
 * 				Uploaded files table. This table stores all the files uploaded by the user in its original form. This will
 * 				also be used to track the processing jobs.
 * 			
 * 
 * <p>Java class for UploadedFileTable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UploadedFileTable">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.taksila.com/veda/model/db/base/v1_0}BaseTable">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}topicid"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}filename"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}fileType"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}fileProcessingCode"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UploadedFileTable", propOrder = {
    "topicid",
    "filename",
    "fileType",
    "fileProcessingCode"
})
@XmlSeeAlso({
    UploadedFile.class
})
public class UploadedFileTable
    extends BaseTable
{

    protected int topicid;
    @XmlElement(required = true)
    protected String filename;
    @XmlElement(required = true)
    protected String fileType;
    @XmlElement(required = true)
    protected String fileProcessingCode;

    /**
     * Gets the value of the topicid property.
     * 
     */
    public int getTopicid() {
        return topicid;
    }

    /**
     * Sets the value of the topicid property.
     * 
     */
    public void setTopicid(int value) {
        this.topicid = value;
    }

    /**
     * Gets the value of the filename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the value of the filename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilename(String value) {
        this.filename = value;
    }

    /**
     * Gets the value of the fileType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Sets the value of the fileType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileType(String value) {
        this.fileType = value;
    }

    /**
     * Gets the value of the fileProcessingCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileProcessingCode() {
        return fileProcessingCode;
    }

    /**
     * Sets the value of the fileProcessingCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileProcessingCode(String value) {
        this.fileProcessingCode = value;
    }

}
