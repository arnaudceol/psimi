package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.impl.DefaultConfidence;

/**
 * Factory for confidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class ConfidenceFactory {

    public Confidence createConfidence(String typeName, String typeMi, String value){
        return new DefaultConfidence(CvTermFactory.createMICvTerm(typeName, typeMi), value);
    }

    public Confidence createConfidence(String typeName, String value){
        return new DefaultConfidence(CvTermFactory.createMICvTerm(typeName, null), value);
    }

    public Confidence createConfidenceWithUnit(String typeName, String typeMi, String value, String unitname, String unitMi){
        return new DefaultConfidence(CvTermFactory.createMICvTerm(typeName, typeMi), value, CvTermFactory.createMICvTerm(unitname, unitMi));
    }

    public Confidence createConfidenceWithUnit(String typeName, String value, String unitName){
        return new DefaultConfidence(CvTermFactory.createMICvTerm(typeName, null), value, CvTermFactory.createMICvTerm(unitName, null));
    }

    public Confidence createAuthorBasedConfidence(String value){
        return new DefaultConfidence(CvTermFactory.createMICvTerm(Confidence.AUTHOR_BASED_CONFIDENCE, Confidence.AUTHOR_BASED_CONFIDENCE_MI), value);
    }
}