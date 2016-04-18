//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.17 at 05:55:12 PM EDT 
//


package com.taksila.veda.model.db.course.v1_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.taksila.veda.model.api.course.v1_0.Topic;
import com.taksila.veda.model.db.base.v1_0.BaseTable;


/**
 * 
 * 				Topic inside a chapter								
 * 			
 * 
 * <p>Java class for TopicTable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TopicTable">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.taksila.com/veda/model/db/base/v1_0}BaseTable">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}name"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}chapterid"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}title"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}subTitle"/>
 *         &lt;element ref="{http://www.taksila.com/veda/model/db/course/v1_0}description"/>
 *         &lt;element name="slides" type="{http://www.taksila.com/veda/model/db/course/v1_0}SlideTable" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TopicTable", propOrder = {
    "name",
    "chapterid",
    "title",
    "subTitle",
    "description",
    "slides"
})
@XmlSeeAlso({
    Topic.class
})
public class TopicTable
    extends BaseTable
{

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String chapterid;
    @XmlElement(required = true)
    protected String title;
    @XmlElement(required = true)
    protected String subTitle;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected List<SlideTable> slides;

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
     * Gets the value of the chapterid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChapterid() {
        return chapterid;
    }

    /**
     * Sets the value of the chapterid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChapterid(String value) {
        this.chapterid = value;
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
     * Gets the value of the slides property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the slides property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSlides().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SlideTable }
     * 
     * 
     */
    public List<SlideTable> getSlides() {
        if (slides == null) {
            slides = new ArrayList<SlideTable>();
        }
        return this.slides;
    }

}
