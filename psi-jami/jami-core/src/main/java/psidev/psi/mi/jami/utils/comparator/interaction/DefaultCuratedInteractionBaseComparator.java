package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;

/**
 * Default comparator for curated interactions.
 *
 * It will first compare the basic properties of an interaction using DefaultInteractionBaseComparator.
 * Then it will compare the created dates (null created dates always come after)
 * Finally it will compare the updated date (null updated date always come after)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultCuratedInteractionBaseComparator extends CuratedInteractionBaseComparator {

    private static DefaultCuratedInteractionBaseComparator defaultCuratedInteractionBaseComparator;

    public DefaultCuratedInteractionBaseComparator() {
        super(new DefaultInteractionBaseComparator());
    }

    @Override
    public DefaultInteractionBaseComparator getInteractionBaseComparator() {
        return (DefaultInteractionBaseComparator) super.getInteractionBaseComparator();
    }

    @Override
    /**
     * It will first compare the basic properties of an interaction using DefaultInteractionBaseComparator.
     * Then it will compare the created dates (null created dates always come after)
     * Finally it will compare the updated date (null updated date always come after)
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultCuratedInteractionBaseComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (defaultCuratedInteractionBaseComparator == null){
            defaultCuratedInteractionBaseComparator = new DefaultCuratedInteractionBaseComparator();
        }

        return defaultCuratedInteractionBaseComparator.compare(interaction1, interaction2) == 0;
    }
}