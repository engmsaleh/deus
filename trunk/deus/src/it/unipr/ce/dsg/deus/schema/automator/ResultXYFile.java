//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.01.08 at 05:21:35 PM CET 
//


package it.unipr.ce.dsg.deus.schema.automator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resultXYFile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resultXYFile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="fileName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="axisX" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="axisY" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultXYFile")
public class ResultXYFile {

    @XmlAttribute(required = true)
    protected String fileName;
    @XmlAttribute(required = true)
    protected String axisX;
    @XmlAttribute(required = true)
    protected String axisY;

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the axisX property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAxisX() {
        return axisX;
    }

    /**
     * Sets the value of the axisX property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAxisX(String value) {
        this.axisX = value;
    }

    /**
     * Gets the value of the axisY property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAxisY() {
        return axisY;
    }

    /**
     * Sets the value of the axisY property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAxisY(String value) {
        this.axisY = value;
    }

}
