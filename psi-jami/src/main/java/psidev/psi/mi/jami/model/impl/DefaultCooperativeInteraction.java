package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CooperativeInteraction;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.utils.comparator.interaction.UnambiguousExactCooperativeInteractionComparator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Default implementation for CooperativeInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultCooperativeInteraction extends DefaultModelledInteraction implements CooperativeInteraction {

    protected CvTerm cooperativeMechanism;
    protected CvTerm effectOutCome;
    protected CvTerm response;
    protected Collection<ModelledInteraction> affectedInteractions;

    public DefaultCooperativeInteraction(CvTerm cooperativeMechanism, CvTerm effectOutcome, CvTerm response) {
        super();
        this.affectedInteractions = new ArrayList<ModelledInteraction>();

        if (cooperativeMechanism == null){
           throw new IllegalArgumentException("The cooperative mechanism is required and cannot be null.");
        }
        this.cooperativeMechanism = cooperativeMechanism;
        if (effectOutcome == null){
            throw new IllegalArgumentException("The effect outcome is required and cannot be null.");
        }
        this.effectOutCome = effectOutcome;
        if (response == null){
            throw new IllegalArgumentException("The response is required and cannot be null.");
        }
        this.response = response;
    }

    public DefaultCooperativeInteraction(String shortName, CvTerm cooperativeMechanism, CvTerm effectOutcome, CvTerm response) {
        super(shortName);
        this.affectedInteractions = new ArrayList<ModelledInteraction>();
        if (cooperativeMechanism == null){
            throw new IllegalArgumentException("The cooperative mechanism is required and cannot be null.");
        }
        this.cooperativeMechanism = cooperativeMechanism;
        if (effectOutcome == null){
            throw new IllegalArgumentException("The effect outcome is required and cannot be null.");
        }
        this.effectOutCome = effectOutcome;
        if (response == null){
            throw new IllegalArgumentException("The response is required and cannot be null.");
        }
        this.response = response;
    }

    public DefaultCooperativeInteraction(String shortName, Source source, CvTerm cooperativeMechanism, CvTerm effectOutcome, CvTerm response) {
        super(shortName, source);
        this.affectedInteractions = new ArrayList<ModelledInteraction>();
        if (cooperativeMechanism == null){
            throw new IllegalArgumentException("The cooperative mechanism is required and cannot be null.");
        }
        this.cooperativeMechanism = cooperativeMechanism;
        if (effectOutcome == null){
            throw new IllegalArgumentException("The effect outcome is required and cannot be null.");
        }
        this.effectOutCome = effectOutcome;
        if (response == null){
            throw new IllegalArgumentException("The response is required and cannot be null.");
        }
        this.response = response;
    }

    public DefaultCooperativeInteraction(String shortName, CvTerm type, CvTerm cooperativeMechanism, CvTerm effectOutcome, CvTerm response) {
        super(shortName, type);
        this.affectedInteractions = new ArrayList<ModelledInteraction>();
        if (cooperativeMechanism == null){
            throw new IllegalArgumentException("The cooperative mechanism is required and cannot be null.");
        }
        this.cooperativeMechanism = cooperativeMechanism;
        if (effectOutcome == null){
            throw new IllegalArgumentException("The effect outcome is required and cannot be null.");
        }
        this.effectOutCome = effectOutcome;
        if (response == null){
            throw new IllegalArgumentException("The response is required and cannot be null.");
        }
        this.response = response;
    }

    public DefaultCooperativeInteraction(String shortName, Source source, CvTerm type, CvTerm cooperativeMechanism, CvTerm effectOutcome, CvTerm response) {
        super(shortName, source, type);
        this.affectedInteractions = new ArrayList<ModelledInteraction>();
        if (cooperativeMechanism == null){
            throw new IllegalArgumentException("The cooperative mechanism is required and cannot be null.");
        }
        this.cooperativeMechanism = cooperativeMechanism;
        if (effectOutcome == null){
            throw new IllegalArgumentException("The effect outcome is required and cannot be null.");
        }
        this.effectOutCome = effectOutcome;
        if (response == null){
            throw new IllegalArgumentException("The response is required and cannot be null.");
        }
        this.response = response;
    }

    public CvTerm getCooperativeMechanism() {
        return this.cooperativeMechanism;
    }

    public void setCooperativeMechanism(CvTerm mechanism) {
        if (cooperativeMechanism == null){
            throw new IllegalArgumentException("The cooperative mechanism is required and cannot be null.");
        }
        this.cooperativeMechanism = mechanism;
    }

    public CvTerm getEffectOutCome() {
        return this.effectOutCome;
    }

    public void setEffectOutCome(CvTerm effect) {
        if (effect == null){
            throw new IllegalArgumentException("The effect outcome is required and cannot be null.");
        }
        this.effectOutCome = effect;
    }

    public CvTerm getResponse() {
        return this.response;
    }

    public void setResponse(CvTerm response) {
        if (response == null){
            throw new IllegalArgumentException("The response is required and cannot be null.");
        }
        this.response = response;
    }

    public Collection<ModelledInteraction> getAffectedInteractions() {
        return this.affectedInteractions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof CooperativeInteraction)){
            return false;
        }

        // use UnambiguousExactCooperativeInteraction comparator for equals
        return UnambiguousExactCooperativeInteractionComparator.areEquals(this, (CooperativeInteraction) o);
    }

    @Override
    public String toString() {
        return super.toString()+", mechanism: " + cooperativeMechanism.toString() + ", effect outcome: " + effectOutCome.toString() + ", response: " + response.toString();
    }
}