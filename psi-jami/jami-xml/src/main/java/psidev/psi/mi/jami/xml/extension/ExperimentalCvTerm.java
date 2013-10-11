//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.20 at 10:58:57 AM BST 
//


package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.AbstractExperimentRef;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/**
 * This element is controlled by the PSI-MI controlled vocabulary
 *                 "experimentalPreparation", root term id MI:0346.
 *             
 * 
 * <p>Java class for experimentalPreparation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * The JAXB binding is designed to be read-only and is not designed for writing
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "experimentalPreparation", propOrder = {
    "JAXBExperimentRefList"
})
public class ExperimentalCvTerm
    extends XmlCvTerm
{
    private Collection<Experiment> experiments;

    public ExperimentalCvTerm() {
    }

    public ExperimentalCvTerm(String shortName, String miIdentifier) {
        super(shortName, miIdentifier);
    }

    public ExperimentalCvTerm(String shortName) {
        super(shortName);
    }

    public ExperimentalCvTerm(String shortName, String fullName, String miIdentifier) {
        super(shortName, fullName, miIdentifier);
    }

    public ExperimentalCvTerm(String shortName, Xref ontologyId) {
        super(shortName, ontologyId);
    }

    public ExperimentalCvTerm(String shortName, String fullName, Xref ontologyId) {
        super(shortName, fullName, ontologyId);
    }

    public Collection<Experiment> getExperiments() {
        if (experiments == null){
            experiments = new ArrayList<Experiment>();
        }
        return experiments;
    }

    /**
     * Gets the value of the experimentRefList property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    @XmlElementWrapper(name="experimentRefList")
    @XmlElement(name="experimentRef", required = true)
    public ArrayList<Integer> getJAXBExperimentRefList() {
        if (experiments == null || experiments.isEmpty()){
            return null;
        }
        ArrayList<Integer> references = new ArrayList<Integer>(experiments.size());
        for (Experiment exp : experiments){
            if (exp instanceof XmlExperiment){
                references.add(((XmlExperiment) exp).getId());
            }
        }
        if (references.isEmpty()){
            return null;
        }
        return references;
    }

    /**
     * Sets the value of the experimentRefList property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBExperimentRefList(ArrayList<Integer> value) {
        if (value != null){
            for (Integer val : value){
                getExperiments().add(new AbstractExperimentRef(val) {
                    public void resolve(Map<Integer, Object> parsedObjects) {
                        if (parsedObjects.containsKey(this.ref)){
                            Object obj = parsedObjects.get(this.ref);
                            if (obj instanceof Experiment){
                                experiments.remove(this);
                                experiments.add((Experiment)obj);
                            }
                            // TODO exception or syntax error if nothing?
                        }
                    }
                });
            }
        }
    }
}
