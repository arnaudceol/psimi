package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;

/**
 * Default feature comparator.
 * It will look first at the feature types using a DefaultCvTermComparator. If the feature types are the same, it will look at the
 * feature identifiers using DefaultExternalIdentifierComparator. If the feature identifiers are the same, it will look at
 * the ranges using DefaultRangeComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class DefaultFeatureBaseComparator extends FeatureBaseComparator {

    private static DefaultFeatureBaseComparator defaultFeatureComparator;

    /**
     * Creates a new DefaultFeatureBaseComparator. It will use a DefaultCvTermComparator to compare feature types and range status,
     * a DefaultExternalIdentifierComparator to compare identifiers and a DefaultRangeComparator to compare ranges
     */
    public DefaultFeatureBaseComparator() {
        super(new DefaultCvTermComparator(), new DefaultExternalIdentifierComparator());
    }

    @Override
    /**
     * It will look first at the feature types using a DefaultCvTermComparator. If the feature types are the same, it will look at the
     * feature identifiers using DefaultExternalIdentifierComparator. If the feature identifiers are the same, it will look at
     * the ranges using DefaultRangeComparator.
     */
    public int compare(Feature feature1, Feature feature2) {
        return super.compare(feature1, feature2);
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) this.cvTermComparator;
    }

    @Override
    public DefaultExternalIdentifierComparator getIdentifierComparator() {
        return (DefaultExternalIdentifierComparator) this.identifierComparator;
    }

    /**
     * Use DefaultFeatureBaseComparator to know if two features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two features are equal
     */
    public static boolean areEquals(Feature feature1, Feature feature2){
        if (defaultFeatureComparator == null){
            defaultFeatureComparator = new DefaultFeatureBaseComparator();
        }

        return defaultFeatureComparator.compare(feature1, feature2) == 0;
    }
}