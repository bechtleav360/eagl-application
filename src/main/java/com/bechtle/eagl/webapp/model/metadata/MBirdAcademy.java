//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2021.12.16 um 04:58:56 PM CET 
//


package com.bechtle.eagl.webapp.model.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MBirdAcademy complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MBirdAcademy"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{https://www.mathplan.de/moses/xsd/default}MNamedEntity"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kurzname" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MBirdAcademy", namespace = "https://www.mathplan.de/moses/xsd/default", propOrder = {
    "kurzname"
})
public class MBirdAcademy
    extends MNamedEntity
{

    @XmlElement(namespace = "https://www.mathplan.de/moses/xsd/default", required = true)
    protected String kurzname;

    /**
     * Ruft den Wert der kurzname-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKurzname() {
        return kurzname;
    }

    /**
     * Legt den Wert der kurzname-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKurzname(String value) {
        this.kurzname = value;
    }

}