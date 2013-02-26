package psidev.psi.mi.jami.model;

/**
 * A feature range indicates the positions of a feature in the interactor sequence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Range {

    public static final String POSITION_SEPARATOR = "-";
    public static final String N_TERMINAL_POSITION_SYMBOL = "n";
    public static final String C_TERMINAL_POSITION_SYMBOL = "c";
    public static final String UNDETERMINED_POSITION_SYMBOL = "?";
    public static final String FUZZY_POSITION_SYMBOL = "..";
    public static final String GREATER_THAN_POSITION_SYMBOL = ">";
    public static final String LESS_THAN_POSITION_SYMBOL = "<";

    /**
     * The start position of the feature range in the interactor sequence
     * It cannot be null
     * @return the start position
     */
    public Position getStart();

    /**
     * The end position of the feature range in the interactor sequence
     * It cannot be null
     * @return the end position
     */
    public Position getEnd();

    /**
     * Set the positions of the feature range in the interactor sequence
     * @param start : start position
     * @param end : end position
     * @throws IllegalArgumentException if
     * - start or end is null
     * - start > end
     */
    public void setPositions(Position start, Position end);

    /**
     * Link boolean to know if two amino acids/nucleic acids are linked in the feature range (ex: disulfure bridges).
     * @return true if two amino acids/nucleic acids are linked together (does not form a linear feature).
     */
    public boolean isLink();
}