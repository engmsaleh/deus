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
 * <p>Java class for engine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="engine">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="seed" type="{http://dsg.ce.unipr.it/software/deus/schema/deusAutomator}seed" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="startVT" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="endVT" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="stepVT" type="{http://www.w3.org/2001/XMLSchema}float" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "engine", propOrder = {
    "seed"
})
public class Engine {

    protected Seed seed;
    @XmlAttribute
    protected Float startVT;
    @XmlAttribute
    protected Float endVT;
    @XmlAttribute
    protected Float stepVT;

    /**
     * Gets the value of the seed property.
     * 
     * @return
     *     possible object is
     *     {@link Seed }
     *     
     */
    public Seed getSeed() {
        return seed;
    }

    /**
     * Sets the value of the seed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Seed }
     *     
     */
    public void setSeed(Seed value) {
        this.seed = value;
    }

    /**
     * Gets the value of the startVT property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getStartVT() {
        return startVT;
    }

    /**
     * Sets the value of the startVT property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setStartVT(Float value) {
        this.startVT = value;
    }

    /**
     * Gets the value of the endVT property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getEndVT() {
        return endVT;
    }

    /**
     * Sets the value of the endVT property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setEndVT(Float value) {
        this.endVT = value;
    }

    /**
     * Gets the value of the stepVT property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getStepVT() {
        return stepVT;
    }

    /**
     * Sets the value of the stepVT property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setStepVT(Float value) {
        this.stepVT = value;
    }

}
