//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.30 at 08:40:04 AM EDT 
//


package com.taksila.veda.model.db.course.v1_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.taksila.veda.model.api.course.v1_0.Slide;
import com.taksila.veda.model.db.base.v1_0.BaseTable;


/**
 * 
 * 				Slide is a snippet of information, multiple slides will
 * 				compose a topic								
 * 			
 * 
 * <p>Java class for SlideTable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SlideTable">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.taksila.com/veda/model/db/base/v1_0}BaseTable">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}topicid"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}name"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}title"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}subTitle"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}description"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}textContent"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}tags" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}notes" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SlideTable", propOrder = {
    "topicid",
    "name",
    "title",
    "subTitle",
    "description",
    "textContent",
    "tags",
    "notes"
})
@XmlSeeAlso({
    Slide.class
})
public class SlideTable
    extends BaseTable
{

    protected int topicid;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String title;
    @XmlElement(required = true)
    protected String subTitle;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String textContent;
    @XmlElement(required = true)
    protected List<String> tags;
    @XmlElement(required = true)
    protected List<NoteTable> notes;

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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the subTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * Sets the value of the subTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubTitle(String value) {
        this.subTitle = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the textContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextContent() {
        return textContent;
    }

    /**
     * Sets the value of the textContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextContent(String value) {
        this.textContent = value;
    }

    /**
     * Gets the value of the tags property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tags property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTags().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTags() {
        if (tags == null) {
            tags = new ArrayList<String>();
        }
        return this.tags;
    }

    /**
     * Gets the value of the notes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NoteTable }
     * 
     * 
     */
    public List<NoteTable> getNotes() {
        if (notes == null) {
            notes = new ArrayList<NoteTable>();
        }
        return this.notes;
    }

}
