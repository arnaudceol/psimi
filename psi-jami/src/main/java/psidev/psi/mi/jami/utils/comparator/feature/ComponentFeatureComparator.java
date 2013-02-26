package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.ComponentFeature;

import java.util.Comparator;

/**
 * Basic ComponentFeature comparator.
 * It will use a FeatureBaseComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of a component feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class ComponentFeatureComparator implements Comparator<ComponentFeature> {

    protected FeatureBaseComparator featureComparator;

    /**
     * Creates a new ComponentFeatureComparator.
     * @param featureComparator : feature comparator required for comparing basic feature properties
     */
    public ComponentFeatureComparator(FeatureBaseComparator featureComparator){
        if (featureComparator == null){
            throw new IllegalArgumentException("The Feature comparator is required to compare general feature properties. It cannot be null");
        }
        this.featureComparator = featureComparator;
    }

    public FeatureBaseComparator getFeatureComparator() {
        return featureComparator;
    }

    /**
     * It will use a FeatureBaseComparator to compare basic properties of a component feature.
     *
     * This comparator will ignore all the other properties of a component component feature.
     *
     * @param biologicalFeature1
     * @param biologicalFeature2
     * @return
     */
    public int compare(ComponentFeature biologicalFeature1, ComponentFeature biologicalFeature2) {
        return featureComparator.compare(biologicalFeature1, biologicalFeature2);
    }
}