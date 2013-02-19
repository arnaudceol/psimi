package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * An interaction that is not directly supported by experimental evidence but is based on homology statements, modelling, etc...
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/12/12</pre>
 */

public interface ModelledInteraction extends Interaction<ModelledParticipant>{

    /**
     * Experiments that have been used to predict this modelled interaction.
     * The collection cannot be null. If the modelled interaction does not have experimental interactions attached to it, the method should return an empty set
     * @return the collection of experimental evidences
     */
    public Collection<Experiment> getExperiments();

    /**
     * The source which reported this modelled interaction. It can be an organization, institute, ...
     * It can be null if the source is unknown or not relevant.
     * Ex: IntAct, MINT, DIP, ...
     * @return the source
     */
    public Source getSource();

    /**
     * Sets the source reporting the interaction.
     * @param source: source for this interaction
     */
    public void setSource(Source source);

    /**
     * This method will add the participant and set the interaction of the new participant to this current interaction
     * @param part
     * @return true if participant is added to the list of participants
     */
    public boolean  addModelledParticipant(ModelledParticipant part);

    /**
     * This method will remove the participant and set the interaction of the new participant to null
     * @param part
     * @return true if participant is removed from the list of participants
     */
    public boolean removeModelledParticipant(ModelledParticipant part);

    /**
     * This method will add all the participant and set the interaction of the new participant to this current interaction
     * @param participants
     * @return true if participant are added to the list of participants
     */
    public boolean  addAllModelledParticipants(Collection<? extends ModelledParticipant> participants);

    /**
     * This method will remove the participant and set the interaction of the removed participant to null.
     * @param participants
     * @return true if participant are removed from the list of participants
     */
    public boolean removeAllModelledParticipants(Collection<? extends ModelledParticipant> participants);
}