//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.05.19 at 03:38:08 PM CEST 
//


package it.unipr.ce.dsg.deus.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.unipr.ce.dsg.deus.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Automator_QNAME = new QName("http://dsg.ce.unipr.it/software/deus/schema/automator", "automator");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.unipr.ce.dsg.deus.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Params }
     * 
     */
    public Params createParams() {
        return new Params();
    }

    /**
     * Create an instance of {@link References }
     * 
     */
    public References createReferences() {
        return new References();
    }

    /**
     * Create an instance of {@link Node }
     * 
     */
    public Node createNode() {
        return new Node();
    }

    /**
     * Create an instance of {@link Automator }
     * 
     */
    public Automator createAutomator() {
        return new Automator();
    }

    /**
     * Create an instance of {@link Reference }
     * 
     */
    public Reference createReference() {
        return new Reference();
    }

    /**
     * Create an instance of {@link Logger }
     * 
     */
    public Logger createLogger() {
        return new Logger();
    }

    /**
     * Create an instance of {@link Process }
     * 
     */
    public Process createProcess() {
        return new Process();
    }

    /**
     * Create an instance of {@link Engine }
     * 
     */
    public Engine createEngine() {
        return new Engine();
    }

    /**
     * Create an instance of {@link Event }
     * 
     */
    public Event createEvent() {
        return new Event();
    }

    /**
     * Create an instance of {@link Param }
     * 
     */
    public Param createParam() {
        return new Param();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Automator }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dsg.ce.unipr.it/software/deus/schema/automator", name = "automator")
    public JAXBElement<Automator> createAutomator(Automator value) {
        return new JAXBElement<Automator>(_Automator_QNAME, Automator.class, null, value);
    }

}
