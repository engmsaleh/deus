//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.09.12 at 02:57:31 PM CEST 
//


package it.unipr.ce.dsg.deus.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for engine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="engine">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="logger" type="{http://dsg.ce.unipr.it/software/deus/schema/automator}logger" minOccurs="0"/>
 *         &lt;element name="processes" type="{http://dsg.ce.unipr.it/software/deus/schema/automator}references"/>
 *       &lt;/sequence>
 *       &lt;attribute name="maxvt" use="required" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="seed" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="keyspacesize" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "engine", propOrder = {
    "logger",
    "processes"
})
public class Engine {

    protected Logger logger;
    @XmlElement(required = true)
    protected References processes;
    @XmlAttribute(required = true)
    protected float maxvt;
    @XmlAttribute(required = true)
    protected int seed;
    @XmlAttribute
    protected Integer keyspacesize;

    /**
     * Gets the value of the logger property.
     * 
     * @return
     *     possible object is
     *     {@link Logger }
     *     
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Sets the value of the logger property.
     * 
     * @param value
     *     allowed object is
     *     {@link Logger }
     *     
     */
    public void setLogger(Logger value) {
        this.logger = value;
    }

    /**
     * Gets the value of the processes property.
     * 
     * @return
     *     possible object is
     *     {@link References }
     *     
     */
    public References getProcesses() {
        return processes;
    }

    /**
     * Sets the value of the processes property.
     * 
     * @param value
     *     allowed object is
     *     {@link References }
     *     
     */
    public void setProcesses(References value) {
        this.processes = value;
    }

    /**
     * Gets the value of the maxvt property.
     * 
     */
    public float getMaxvt() {
        return maxvt;
    }

    /**
     * Sets the value of the maxvt property.
     * 
     */
    public void setMaxvt(float value) {
        this.maxvt = value;
    }

    /**
     * Gets the value of the seed property.
     * 
     */
    public int getSeed() {
        return seed;
    }

    /**
     * Sets the value of the seed property.
     * 
     */
    public void setSeed(int value) {
        this.seed = value;
    }

    /**
     * Gets the value of the keyspacesize property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKeyspacesize() {
        return keyspacesize;
    }

    /**
     * Sets the value of the keyspacesize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKeyspacesize(Integer value) {
        this.keyspacesize = value;
    }

}
