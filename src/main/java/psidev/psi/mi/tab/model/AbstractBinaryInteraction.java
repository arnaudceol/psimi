/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.ParameterUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.clone.ExperimentCloner;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Representation of a binary interaction as in the MITAB25 format.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public abstract class AbstractBinaryInteraction<T extends Interactor> extends DefaultInteractionEvidence implements BinaryInteraction<T> {

	private static final long serialVersionUID = 5851048278405255668L;

    /**
     * Sets up a logger for that class.
     */
    Log log = LogFactory.getLog(AbstractBinaryInteraction.class);

	///////////////////////
	// Instance variable

	/**
	 *  MITAB 2.5
	 */

	/**
	 * Interactor A.
	 */
	protected T interactorA;

	/**
	 * Interactor B.
	 */
	protected T interactorB;

    private MitabExperiment mitabExperiment;

    /**
     * Types of the interaction.
     */
    private List<CrossReference> interactionTypes
            = new InteractionTypesList();

    /**
     * Associated confidence value for that interaction.
     */
    private List<Confidence> confidenceValues
            = new InteractionMitabConfidenceList();

    /**
     * Identifiers of the interaction that provided the data.
     */
    private List<CrossReference> interactionAcs
            = new InteractionMitabIdentifiersList();

	/**
	 *  MITAB 2.6
	 */

	/**
	 * Model used to convert n-ary interactions into binary.
	 */
	private List<CrossReference> complexExpansion
			= new ArrayList<CrossReference>();

	/**
	 * Cross references associated to the interaction.
	 */
	private List<CrossReference> interactionXrefs
			= new InteractionMitabXrefsList();

	/**
	 * Annotations for the interaction.
	 */
	private List<Annotation> interactionAnnotations
			= new InteractionMitabAnnotationList();

    /**
     * Parameters for the interaction.
     */
    private List<Parameter> interactionParameters
            = new InteractionMitabParametersList();

    /**
     * Checksum for interaction.
     */
    private List<Checksum> interactionChecksums
            = new InteractionMitabChecksumList();

    /**
     * Update Date
     */
    private List<Date> updateDate
            = new MitabUpdateDateList();

    /**
     * Creation Date
     */
    private List<Date> creationDate
            = new MitabCreationDateList();


	/**
	 * MITAB 2.7
	 */

	///////////////////////
	// Constructors
	public AbstractBinaryInteraction() {
        super();
        setExperimentAndAddInteractionEvidence(new MitabExperiment());
	}

	public AbstractBinaryInteraction(T interactor) {
        super();
		setInteractorA(interactor);
        setExperimentAndAddInteractionEvidence(new MitabExperiment());

    }

	public AbstractBinaryInteraction(T interactorA, T interactorB) {
        super();
		setInteractorA(interactorA);
		setInteractorB(interactorB);
        setExperimentAndAddInteractionEvidence(new MitabExperiment());
    }

    @Override
    protected void initializeConfidences(){
        this.confidences = new BinaryInteractionConfidenceList();
    }

    @Override
    protected void initializeIdentifiers(){
        this.identifiers = new BinaryInteractionIdentifiersList();
    }

    @Override
    protected void initializeXrefs() {
        this.xrefs = new BinaryInteractionXrefList();
    }

    @Override
    protected void initializeAnnotations() {
        this.annotations = new BinaryInteractionAnnotationList();
    }

    @Override
    protected void initializeParameters(){
        this.parameters = new BinaryInteractionParametersList();
    }

    @Override
    protected void initializeChecksum(){
        this.checksums = new BinaryInteractionChecksumList();
    }

    public void flip() {
		T interactorA = getInteractorA();
		T interactorB = getInteractorB();

		setInteractorA(interactorB);
		setInteractorB(interactorA);
	}

	///////////////////////////
	// Getters & Setters

	/**
	 * {@inheritDoc}
	 */
	public T getInteractorA() {
		return interactorA;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setInteractorA(T interactorA) {
        T currentB = interactorB;
        participants.clear();
        this.interactorA = interactorA;
        this.interactorB = currentB;
        participants.add(interactorA);
        participants.add(currentB);
	}

	/**
	 * {@inheritDoc}
	 */
	public T getInteractorB() {
		return interactorB;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setInteractorB(T interactorB) {
        T currentA = interactorA;

        participants.clear();

        this.interactorA = currentA;
        this.interactorB = interactorB;

        participants.add(currentA);
        participants.add(interactorB);
	}

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getInteractionTypes() {
        return interactionTypes;
    }

    /**
     * {@inheritDoc}
     */
    public void setInteractionTypes(List<CrossReference> interactionTypes) {
        this.interactionTypes.clear();
        if (interactionTypes != null) {
            this.interactionTypes.addAll(interactionTypes);
        }
    }


    /**
     * {@inheritDoc}
     */
    public List<Confidence> getConfidenceValues() {
        return confidenceValues;
    }

    /**
     * {@inheritDoc}
     */
    public void setConfidenceValues(List<Confidence> confidenceValues) {
        this.confidenceValues.clear();
        if (confidenceValues != null) {
            this.confidenceValues.addAll(confidenceValues);
        }
    }

	/**
	 * {@inheritDoc}
	 */
	public List<CrossReference> getSourceDatabases() {
		return mitabExperiment.getMitabPublication().getSourceDatabases();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSourceDatabases(List<CrossReference> sourceDatabases) {
        mitabExperiment.getMitabPublication().setSourceDatabases(sourceDatabases);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CrossReference> getInteractionAcs() {
		return interactionAcs;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setInteractionAcs(List<CrossReference> interactionAcs) {
        this.interactionAcs.clear();
        if (interactionAcs != null) {
            this.interactionAcs.addAll(interactionAcs);
        }
	}

	/**
	 * MITAB 2.6
	 */

	/**
	 * {@inheritDoc}
	 */
	public List<CrossReference> getComplexExpansion() {
		return complexExpansion;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setComplexExpansion(List<CrossReference> complexExpansion) {
		this.complexExpansion = complexExpansion;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CrossReference> getMitabXrefs() {
		return interactionXrefs;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMitabXrefs(List<CrossReference> xrefs) {
        this.interactionXrefs.clear();
        if (xrefs != null) {
            this.interactionXrefs.addAll(xrefs);
        }
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Annotation> getMitabAnnotations() {
		return interactionAnnotations;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMitabAnnotations(List<Annotation> interactionAnnotations) {
        this.interactionAnnotations.clear();
        if (interactionAnnotations != null) {
            this.interactionAnnotations.addAll(interactionAnnotations);
        }
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Parameter> getMitabParameters() {
		return interactionParameters;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMitabParameters(List<Parameter> parameters) {
        this.interactionParameters.clear();
        if (parameters != null) {
            this.interactionParameters.addAll(parameters);
        }
	}

    /**
     * {@inheritDoc}
     */
    public List<Checksum> getInteractionChecksums() {
        return interactionChecksums;
    }

    /**
     * {@inheritDoc}
     */
    public void setInteractionChecksums(List<Checksum> interactionChecksums) {
        this.interactionChecksums.clear();
        if (interactionChecksums != null) {
            this.interactionChecksums.addAll(interactionChecksums);
        }
    }

	/**
	 * {@inheritDoc}
	 */
	public List<Date> getUpdateDate() {
		return updateDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setUpdateDate(List<Date> updateDate) {
		this.updateDate = updateDate;
	}

    /**
     * {@inheritDoc}
     */
    public List<Date> getCreationDate() {
        return creationDate;
    }

    /**
     * {@inheritDoc}
     */
    public void setCreationDate(List<Date> creationDate) {
        this.creationDate = creationDate;
    }

    // experiment

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getDetectionMethods() {
        return mitabExperiment.getDetectionMethods();
    }

    /**
     * {@inheritDoc}
     */
    public void setDetectionMethods(List<CrossReference> detectionMethods) {
        mitabExperiment.setDetectionMethods(detectionMethods);
    }

    /**
     * {@inheritDoc}
     */
    public Organism getHostOrganism() {
        return mitabExperiment.getHostOrganism();
    }

    /**
     * {@inheritDoc}
     */
    public void setHostOrganism(Organism hostOrganism) {
        mitabExperiment.setHostOrganism(hostOrganism);
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasHostOrganism() {
        return mitabExperiment.hasHostOrganism();
    }

    // publication

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getPublications() {
        return mitabExperiment.getMitabPublication().getPublications();
    }

    /**
     * {@inheritDoc}
     */
    public void setPublications(List<CrossReference> publications) {
        mitabExperiment.getMitabPublication().setPublications(publications);
    }

    /**
     * {@inheritDoc}
     */
    public List<Author> getAuthors() {
        return mitabExperiment.getMitabPublication().getMitabAuthors();
    }

    /**
     * {@inheritDoc}
     */
    public void setAuthors(List<Author> authors) {
        mitabExperiment.getMitabPublication().setMitabAuthors(authors);
    }

	/**
	 * {@inheritDoc}
	 */
	public boolean isNegativeInteraction() {
		return isNegative;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setNegativeInteraction(Boolean negativeInteraction) {
        if (negativeInteraction == null){
           this.isNegative = false;
        }
        else {
            this.isNegative = negativeInteraction;
        }
	}

    protected void resetInteractionTypeNameFromMiReferences(){
        if (!interactionTypes.isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(interactionTypes), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                type.setShortName(name);
                type.setFullName(name);
            }
        }
    }

    protected void resetInteractionTypeNameFromFirstReferences(){
        if (!interactionTypes.isEmpty()){
            Iterator<CrossReference> methodsIterator = interactionTypes.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            type.setShortName(name != null ? name : "unknown");
            type.setFullName(name != null ? name : "unknown");
        }
    }

    protected void processAddedChecksumEvent(psidev.psi.mi.jami.model.Checksum added){
        if (rigid == null && ChecksumUtils.doesChecksumHaveMethod(added, psidev.psi.mi.jami.model.Checksum.RIGID_MI, psidev.psi.mi.jami.model.Checksum.RIGID)){
            rigid = added;
        }
    }

    protected void processRemovedChecksumEvent(psidev.psi.mi.jami.model.Checksum removed){
        if (rigid != null && rigid.getValue().equals(removed.getValue())
                && ChecksumUtils.doesChecksumHaveMethod(removed, psidev.psi.mi.jami.model.Checksum.RIGID_MI, psidev.psi.mi.jami.model.Checksum.RIGID)){
            rigid = ChecksumUtils.collectFirstChecksumWithMethod(checksums, psidev.psi.mi.jami.model.Checksum.RIGID_MI, psidev.psi.mi.jami.model.Checksum.RIGID);
        }
    }

    protected void clearPropertiesLinkedToChecksum(){
        rigid = null;
    }

	/////////////////////////
	// Object's override

    @Override
    public void setExperiment(psidev.psi.mi.jami.model.Experiment experiment) {
        if (experiment == null){
            super.setExperiment(null);
            this.mitabExperiment = null;
        }
        else if (experiment instanceof MitabExperiment){
            super.setExperiment(experiment);
            this.mitabExperiment = (MitabExperiment) experiment;
        }
        else {
            MitabExperiment convertedExperiment = new MitabExperiment();

            ExperimentCloner.copyAndOverrideExperimentProperties(experiment, convertedExperiment);
            mitabExperiment = convertedExperiment;
            super.setExperiment(experiment);
        }
    }

    @Override
    public void setExperimentAndAddInteractionEvidence(Experiment experiment) {
        if (experiment == null){
            super.setExperiment(null);
            this.mitabExperiment = null;
        }
        else if (experiment instanceof MitabExperiment){
            super.setExperiment(experiment);
            this.mitabExperiment = (MitabExperiment) experiment;
            experiment.getInteractions().add(this);
        }
        else {
            MitabExperiment convertedExperiment = new MitabExperiment();

            ExperimentCloner.copyAndOverrideExperimentProperties(experiment, convertedExperiment);
            mitabExperiment = convertedExperiment;
            super.setExperiment(experiment);
            experiment.getInteractions().add(this);
        }
    }

    @Override
    public void setCreatedDate(Date created) {
        ((MitabCreationDateList)creationDate).clearOnly();
        super.setCreatedDate(created);
        creationDate.add(created);
    }

    @Override
    public void setUpdatedDate(Date updated) {
        ((MitabUpdateDateList)updateDate).clearOnly();
        super.setUpdatedDate(updated);
        updateDate.add(updated);
    }

    @Override
    public void setType(CvTerm type) {
        super.setType(type);
        processNewInteractionTypesList(type);
    }

    private void processNewInteractionTypesList(CvTerm type) {
        ((InteractionTypesList)interactionTypes).clearOnly();
        if (type.getMIIdentifier() != null){
            ((InteractionTypesList)interactionTypes).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, type.getMIIdentifier(), type.getFullName() != null ? type.getFullName() : type.getShortName()));
        }
        else{
            if (!type.getIdentifiers().isEmpty()){
                Xref ref = type.getIdentifiers().iterator().next();
                ((InteractionTypesList)interactionTypes).addOnly(new CrossReferenceImpl(ref.getDatabase().getShortName(), ref.getId(), type.getFullName() != null ? type.getFullName() : type.getShortName()));
            }
            else {
                ((InteractionTypesList)interactionTypes).addOnly(new CrossReferenceImpl("unknown", "-", type.getFullName() != null ? type.getFullName() : type.getShortName()));
            }
        }
    }

    //We need update the toString, equals and hash ?

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("{interactorA=").append(interactorA);
		sb.append(", interactorB=").append(interactorB);
		sb.append(", detectionMethods=").append(getDetectionMethods());
		sb.append(", interactionTypes=").append(interactionTypes);
		sb.append(", authors=").append(getAuthors());
		sb.append(", publications=").append(getPublications());
		sb.append(", confidenceValues=").append(confidenceValues);
		sb.append(", sourceDatabases=").append(getSourceDatabases());
		sb.append(", interactionAcs=").append(interactionAcs);
		sb.append(", complexExpansion=").append(complexExpansion);
		sb.append(", xrefs=").append(interactionXrefs);
		sb.append(", annotations=").append(interactionAnnotations);
		sb.append(", hostOrganism=").append(hasHostOrganism() ? getHostOrganism() : "-");
		sb.append(", parameters=").append(interactionParameters);
		sb.append(", creationDate=").append(creationDate);
		sb.append(", updateDate=").append(updateDate);
		sb.append(", checksums=").append(interactionChecksums);
		sb.append(", negative=").append(isNegative);
		sb.append('}');
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		//TODO When two interactions are equals?

		AbstractBinaryInteraction that = (AbstractBinaryInteraction) o;


		if (getDetectionMethods() != null ? !getDetectionMethods().equals(that.getDetectionMethods()) : that.getDetectionMethods() != null) {
			return false;
		}
		if (interactionTypes != null ? !interactionTypes.equals(that.interactionTypes) : that.interactionTypes != null) {
			return false;
		}


		// TODO update to (A == A' && B == B') || (B == A' && A == B')
		//interactorA or interactorB can be null
		if (interactorA == null) {
			if (that.interactorA != null && that.interactorB != null) {
				return false;
			} else if (that.interactorA == null) {
				//We check interactor B, This shouldn't be null (because then both interactor should be null)
				if (!interactorB.equals(that.interactorB)) {
					return false;
				}

			} else if (that.interactorB == null) {
				if (!interactorB.equals(that.interactorA)) {
					return false;
				}
			}
		} else if (interactorB == null) {
			if (that.interactorB != null && that.interactorA != null) {
				return false;
			} else if (that.interactorB == null) {
				//We check interactor A, This shouldn't be null (because then both interactor should be null)
				if (!interactorA.equals(that.interactorA)) {
					return false;
				}
			} else if ((interactorB == null && that.interactorA == null)) {
				if (!interactorA.equals(that.interactorB)) {
					return false;
				}
			}
		} else if (interactorA != null && interactorB != null) {
			boolean part1 = (interactorA.equals(that.interactorA) && interactorB.equals(that.interactorB));
			boolean part2 = (interactorB.equals(that.interactorA) && interactorA.equals(that.interactorB));

			if (!(part1 || part2)) {
				return false;
			}
		}


		if (getPublications() != null ? !CollectionUtils.isEqualCollection(getPublications(), that.getPublications()) : that.getPublications() != null) {
			return false;
		}

		if (confidenceValues != null ? !CollectionUtils.isEqualCollection(confidenceValues, that.confidenceValues) : that.confidenceValues != null) {
			return false;
		}

		if (getSourceDatabases() != null ? !CollectionUtils.isEqualCollection(getSourceDatabases(), that.getSourceDatabases()) : that.getSourceDatabases() != null) {
			return false;
		}

		if (interactionAcs != null ? !CollectionUtils.isEqualCollection(interactionAcs, that.interactionAcs) : that.interactionAcs != null) {
			return false;
		}

		if (getAuthors() != null ? !CollectionUtils.isEqualCollection(getAuthors(), that.getAuthors()) : that.getAuthors() != null) {
			return false;
		}

		if (complexExpansion != null ? !CollectionUtils.isEqualCollection(complexExpansion, that.complexExpansion) : that.complexExpansion != null) {
			return false;
		}

		if (interactionXrefs != null ? !CollectionUtils.isEqualCollection(interactionXrefs, that.interactionXrefs) : that.interactionXrefs != null) {
			return false;
		}

		if (interactionAnnotations != null ? !CollectionUtils.isEqualCollection(interactionAnnotations, that.interactionAnnotations) : that.interactionAnnotations != null) {
			return false;
		}

		if (getHostOrganism() != null ? !getHostOrganism().equals(that.getHostOrganism()) : that.getHostOrganism() != null) {
			return false;
		}

		if (interactionParameters != null ? !CollectionUtils.isEqualCollection(interactionParameters, that.interactionParameters) : that.interactionParameters != null) {
			return false;
		}

		if (creationDate != null ? !CollectionUtils.isEqualCollection(creationDate, that.creationDate) : that.creationDate != null) {
			return false;
		}

		if (updateDate != null ? !CollectionUtils.isEqualCollection(updateDate, that.updateDate) : that.updateDate != null) {
			return false;
		}

		if (interactionChecksums != null ? !CollectionUtils.isEqualCollection(interactionChecksums, that.interactionChecksums) : that.interactionChecksums != null) {
			return false;
		}

		return isNegative == that.isNegative;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result;

		// Note: we want to reflect that (A == A' && B == B') || (B == A' && A == B')

		result = interactorA.hashCode() * interactorB.hashCode();
		result = 31 * result + (getDetectionMethods() != null ? getDetectionMethods().hashCode() : 0);
		result = 31 * result + (interactionTypes != null ? interactionTypes.hashCode() : 0);
		result = 31 * result + (getPublications() != null ? getPublications().hashCode() : 0);
		result = 31 * result + (confidenceValues != null ? confidenceValues.hashCode() : 0);
		result = 31 * result + (getSourceDatabases() != null ? getSourceDatabases().hashCode() : 0);
		result = 31 * result + (interactionAcs != null ? interactionAcs.hashCode() : 0);
		result = 31 * result + (getAuthors() != null ? getAuthors().hashCode() : 0);
		result = 31 * result + (getSourceDatabases() != null ? getSourceDatabases().hashCode() : 0);
		result = 31 * result + (complexExpansion != null ? complexExpansion.hashCode() : 0);
		result = 31 * result + (interactionXrefs != null ? interactionXrefs.hashCode() : 0);
		result = 31 * result + (interactionAnnotations != null ? interactionAnnotations.hashCode() : 0);
		result = 31 * result + (getHostOrganism() != null ? getHostOrganism().hashCode() : 0);
		result = 31 * result + (interactionParameters != null ? interactionParameters.hashCode() : 0);
		result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
		result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
		result = 31 * result + (interactionChecksums != null ? interactionChecksums.hashCode() : 0);


		return result;
	}

    protected class InteractionTypesList extends AbstractListHavingPoperties<CrossReference> {
        public InteractionTypesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            if (type == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                type = new DefaultCvTerm(name, name, added);
            }
            else {
                type.getXrefs().add(added);
                // reset shortname
                if (type.getMIIdentifier() != null && type.getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        type.setShortName(name);
                    }
                    else {
                        resetInteractionTypeNameFromMiReferences();
                        if (type.getShortName().equals("unknown")){
                            resetInteractionTypeNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (type != null){
                type.getXrefs().remove(removed);

                if (removed.getText() != null && type.getShortName().equals(removed.getText())){
                    if (type.getMIIdentifier() != null){
                        resetInteractionTypeNameFromMiReferences();
                        if (type.getShortName().equals("unknown")){
                            resetInteractionTypeNameFromFirstReferences();
                        }
                    }
                    else {
                        resetInteractionTypeNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                type = null;
            }
        }

        @Override
        protected void clearProperties() {
            type = null;
        }
    }

    protected class BinaryInteractionConfidenceList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Confidence> {
        public BinaryInteractionConfidenceList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Confidence added) {
            if (added instanceof Confidence){
                ((InteractionMitabConfidenceList)confidenceValues).addOnly((Confidence) added);
            }
            else {
                ((InteractionMitabConfidenceList)confidenceValues).addOnly(new ConfidenceImpl(added.getType().getShortName(), added.getValue(), added.getUnit() != null ? added.getUnit().getShortName() : null));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Confidence removed) {
            if (removed instanceof Confidence){
                ((InteractionMitabConfidenceList)confidenceValues).removeOnly(removed);
            }
            else {
                ((InteractionMitabConfidenceList)confidenceValues).removeOnly(new ConfidenceImpl(removed.getType().getShortName(), removed.getValue(), removed.getUnit() != null ? removed.getUnit().getShortName() : null));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all confidences
            ((InteractionMitabConfidenceList)confidenceValues).clearOnly();
        }
    }

    protected class InteractionMitabConfidenceList extends AbstractListHavingPoperties<Confidence> {
        public InteractionMitabConfidenceList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Confidence added) {

            // we added a confidence, needs to add it in confidences
            ((BinaryInteractionConfidenceList)confidences).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Confidence removed) {

            // we removed a Confidence, needs to remove it in confidences
            ((BinaryInteractionConfidenceList)confidences).removeOnly(removed);

        }

        @Override
        protected void clearProperties() {
            // clear all confidences
            ((BinaryInteractionConfidenceList)confidences).clearOnly();
        }
    }

    protected class BinaryInteractionIdentifiersList extends AbstractListHavingPoperties<Xref> {
        public BinaryInteractionIdentifiersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {
            if (added instanceof CrossReference){
                ((InteractionMitabIdentifiersList)interactionAcs).addOnly((CrossReference) added);
            }
            else {
                ((InteractionMitabIdentifiersList)interactionAcs).addOnly(new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier()!= null ? added.getQualifier().getShortName() : null));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {
            if (removed instanceof CrossReference){
                ((InteractionMitabIdentifiersList)interactionAcs).removeOnly(removed);
            }
            else {
                ((InteractionMitabIdentifiersList)interactionAcs).removeOnly(new CrossReferenceImpl(removed.getDatabase().getShortName(), removed.getId(), removed.getQualifier()!= null ? removed.getQualifier().getShortName() : null));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all confidences
            ((InteractionMitabIdentifiersList)interactionAcs).clearOnly();
        }
    }

    protected class InteractionMitabIdentifiersList extends AbstractListHavingPoperties<CrossReference> {
        public InteractionMitabIdentifiersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            // imex
            if (added.getDatabase().getShortName().toLowerCase().trim().equals(Xref.IMEX) && imexId == null){
                assignImexId(imexId.getId());
            }
            else {
                ((BinaryInteractionIdentifiersList)identifiers).addOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            // imex
            if (removed.getDatabase().getShortName().toLowerCase().trim().equals(Xref.IMEX) && imexId != null && imexId.equals(removed.getId())){
                xrefs.remove(removed);
            }
            else {
                ((BinaryInteractionIdentifiersList)identifiers).removeOnly(removed);
            }
        }

        @Override
        protected void clearProperties() {

            // remove imex id from xrefs
            if (imexId != null){
                xrefs.remove(imexId);
            }

            // clear all confidences
            ((BinaryInteractionIdentifiersList)identifiers).clearOnly();
        }
    }

    private class BinaryInteractionXrefList extends AbstractListHavingPoperties<Xref> {
        public BinaryInteractionXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {

            // the added identifier is imex and the current imex is not set
            if (imexId == null && XrefUtils.isXrefFromDatabase(added, Xref.IMEX_MI, Xref.IMEX)){
                // the added xref is imex-primary
                if (XrefUtils.doesXrefHaveQualifier(added, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                    if (added instanceof CrossReference){
                        imexId = added;
                        ((InteractionMitabIdentifiersList) interactionAcs).addOnly((CrossReference)imexId);
                    }
                    else {
                        CrossReference imex = new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier().getShortName());
                        imexId = imex;
                        ((InteractionMitabIdentifiersList) interactionAcs).addOnly((CrossReference)imexId);
                    }
                }
            }
            else{
                if (added instanceof CrossReference){
                    ((InteractionMitabXrefsList)interactionXrefs).addOnly((CrossReference) added);
                }
                else {
                    ((InteractionMitabXrefsList)interactionXrefs).addOnly(new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier()!= null ? added.getQualifier().getShortName() : null));
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            // the removed identifier is pubmed
            if (imexId == removed){
                ((InteractionMitabIdentifiersList) interactionAcs).removeOnly(imexId);
                imexId = null;
            }
            else {
                if (removed instanceof CrossReference){
                    ((InteractionMitabXrefsList)interactionXrefs).removeOnly(removed);
                }
                else {
                    ((InteractionMitabXrefsList)interactionXrefs).removeOnly(new CrossReferenceImpl(removed.getDatabase().getShortName(), removed.getId(), removed.getQualifier()!= null ? removed.getQualifier().getShortName() : null));
                }
            }
        }

        @Override
        protected void clearProperties() {
            ((InteractionMitabIdentifiersList) interactionAcs).removeOnly(imexId);
            ((InteractionMitabXrefsList)interactionXrefs).clearOnly();
            imexId = null;
        }
    }

    private class InteractionMitabXrefsList extends AbstractListHavingPoperties<CrossReference> {
        public InteractionMitabXrefsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            ((BinaryInteractionXrefList)xrefs).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            ((BinaryInteractionXrefList)xrefs).removeOnly(removed);

        }

        @Override
        protected void clearProperties() {
            // clear all xrefs excepted imex
            ((BinaryInteractionXrefList)xrefs).retainAllOnly(interactionAcs);
        }
    }

    protected class BinaryInteractionAnnotationList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Annotation> {
        public BinaryInteractionAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            String shortTopic = added.getTopic().getShortName();

            // we have a complex expansion added as a annotation
            if (psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION.equals(shortTopic)){
                CrossReference matchingXref = null;
                if (added.getValue() == null){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION);
                }
                else if (psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION.equalsIgnoreCase(added.getValue().trim())){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION);
                }
                else if (psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION.equalsIgnoreCase(added.getValue().trim())){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION);
                }
                else if (psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION.equalsIgnoreCase(added.getValue().trim())){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION);
                }
                else {
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION_MI, added.getValue());
                }
                ((ComplexExpansionList)complexExpansion).addOnly(matchingXref);
            }
            else if (psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION.equalsIgnoreCase(shortTopic)){
                CrossReference matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION);
                ((ComplexExpansionList)complexExpansion).addOnly(matchingXref);
            }
            else if (psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION.equalsIgnoreCase(shortTopic)){
                CrossReference matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION);
                ((ComplexExpansionList)complexExpansion).addOnly(matchingXref);
            }
            else if (psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION.equalsIgnoreCase(shortTopic)){
                CrossReference matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION);
                ((ComplexExpansionList)complexExpansion).addOnly(matchingXref);
            }
            // we have a simple xref
            else {
                if (added instanceof Annotation){
                    ((InteractionMitabAnnotationList)interactionAnnotations).addOnly((Annotation) added);
                }
                else {
                    ((InteractionMitabAnnotationList)interactionAnnotations).addOnly(new AnnotationImpl(added.getTopic().getShortName(), added.getValue()));
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            String shortTopic = removed.getTopic().getShortName();

            // we have a complex expansion added as a annotation
            if (psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION.equals(shortTopic)){
                CrossReference matchingXref = null;
                if (removed.getValue() == null){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION);
                }
                else if (psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION.equalsIgnoreCase(removed.getValue().trim())){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION);
                }
                else if (psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION.equalsIgnoreCase(removed.getValue().trim())){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION);
                }
                else if (psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION.equalsIgnoreCase(removed.getValue().trim())){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION);
                }
                else {
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION_MI, removed.getValue());
                }
                ((ComplexExpansionList)complexExpansion).removeOnly(matchingXref);
            }
            else if (psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION.equalsIgnoreCase(shortTopic)){
                CrossReference matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION);
                ((ComplexExpansionList)complexExpansion).removeOnly(matchingXref);
            }
            else if (psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION.equalsIgnoreCase(shortTopic)){
                CrossReference matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION);
                ((ComplexExpansionList)complexExpansion).removeOnly(matchingXref);
            }
            else if (psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION.equalsIgnoreCase(shortTopic)){
                CrossReference matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION);
                ((ComplexExpansionList)complexExpansion).removeOnly(matchingXref);
            }
            // remove normal annotation
            else {
                if (removed instanceof Annotation){
                    ((InteractionMitabAnnotationList)interactionAnnotations).removeOnly(removed);
                }
                else {
                    ((InteractionMitabAnnotationList)interactionAnnotations).removeOnly(new AnnotationImpl(removed.getTopic().getShortName(), removed.getValue()));
                }
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractionMitabAnnotationList)interactionAnnotations).clearOnly();
            ((ComplexExpansionList)complexExpansion).clearOnly();
        }
    }

    protected class ComplexExpansionList extends AbstractListHavingPoperties<CrossReference> {
        public ComplexExpansionList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {
            ((BinaryInteractionAnnotationList)annotations).addOnly(
                    new DefaultAnnotation(CvTermFactory.createMICvTerm(Annotation.COMPLEX_EXPANSION, Annotation.COMPLEX_EXPANSION_MI), added.getText() != null ? added.getText() : added.getId()));
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {
            ((BinaryInteractionAnnotationList)annotations).removeOnly(
                    new DefaultAnnotation(CvTermFactory.createMICvTerm(Annotation.COMPLEX_EXPANSION, Annotation.COMPLEX_EXPANSION_MI), removed.getText() != null ? removed.getText() : removed.getId()));
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((BinaryInteractionAnnotationList)annotations).retainAllOnly(interactionAnnotations);
        }
    }

    protected class InteractionMitabAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public InteractionMitabAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Annotation added) {

            // we added a annotation, needs to add it in annotations
            ((BinaryInteractionAnnotationList)annotations).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Annotation removed) {

            // we removed a annotation, needs to remove it in annotations
            ((BinaryInteractionAnnotationList)annotations).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((BinaryInteractionAnnotationList)annotations).clearOnly();
        }
    }

    protected class BinaryInteractionParametersList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Parameter> {
        public BinaryInteractionParametersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Parameter added) {
            if (added instanceof Parameter){
                ((InteractionMitabParametersList)interactionParameters).addOnly((Parameter) added);
            }
            else {
                try {
                    ((InteractionMitabParametersList)interactionParameters).addOnly(new ParameterImpl(added.getType().getShortName(), ParameterUtils.getParameterValueAsString(added)));
                } catch (IllegalParameterException e) {
                    removeOnly(added);
                    log.error("Impossible to add this parameter because not well formatted", e);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Parameter removed) {
            if (removed instanceof Parameter){
                ((InteractionMitabParametersList)interactionParameters).removeOnly(removed);
            }
            else {
                try {
                    ((InteractionMitabParametersList)interactionParameters).removeOnly(new ParameterImpl(removed.getType().getShortName(), ParameterUtils.getParameterValueAsString(removed)));
                } catch (IllegalParameterException e) {
                    log.error("Impossible to remove entirely this parameter because not well formatted", e);
                }
            }
        }

        @Override
        protected void clearProperties() {
            ((InteractionMitabParametersList)interactionParameters).clearOnly();
        }
    }

    protected class InteractionMitabParametersList extends AbstractListHavingPoperties<Parameter> {
        public InteractionMitabParametersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Parameter added) {

            ((BinaryInteractionParametersList)parameters).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Parameter removed) {

            ((BinaryInteractionParametersList)parameters).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            ((BinaryInteractionParametersList)parameters).clearOnly();
        }
    }

    protected class BinaryInteractionChecksumList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Checksum> {
        public BinaryInteractionChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Checksum added) {
            Checksum modified = null;
            if (added instanceof Checksum){
                modified = (Checksum) added;
                ((InteractionMitabChecksumList)interactionChecksums).addOnly(modified);
            }
            else {
                modified = new ChecksumImpl(added.getMethod().getShortName(), added.getValue());
                ((InteractionMitabChecksumList)interactionChecksums).addOnly(modified);
            }

            processAddedChecksumEvent(modified);
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Checksum removed) {
            Checksum modified = null;
            if (removed instanceof Checksum){
                modified = (Checksum) removed;
                ((InteractionMitabChecksumList)interactionChecksums).removeOnly(modified);
            }
            else {
                modified = new ChecksumImpl(removed.getMethod().getShortName(), removed.getValue());
                ((InteractionMitabChecksumList)interactionChecksums).removeOnly(modified);
            }

            processRemovedChecksumEvent(modified);
        }

        @Override
        protected void clearProperties() {
            // clear all checksums
            ((InteractionMitabChecksumList)interactionChecksums).clearOnly();
            clearPropertiesLinkedToChecksum();
        }
    }

    protected class InteractionMitabChecksumList extends AbstractListHavingPoperties<Checksum> {
        public InteractionMitabChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Checksum added) {

            // we added a checksum, needs to add it in checksums
            ((BinaryInteractionChecksumList)checksums).addOnly(added);
            processRemovedChecksumEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Checksum removed) {

            // we removed a checksum, needs to remove it in checksums
            ((BinaryInteractionChecksumList)checksums).removeOnly(removed);
            processRemovedChecksumEvent(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all checksums
            ((BinaryInteractionChecksumList)checksums).clearOnly();
            clearPropertiesLinkedToChecksum();
        }
    }

    protected class MitabCreationDateList extends AbstractListHavingPoperties<Date> {
        public MitabCreationDateList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Date added) {

            if (createdDate == null){
                createdDate = added;
            }
        }

        @Override
        protected void processRemovedObjectEvent(Date removed) {

            if (createdDate != null && createdDate == removed && !isEmpty()){
                createdDate = creationDate.iterator().next();
            }

            if (isEmpty()){
                createdDate = null;
            }
        }

        @Override
        protected void clearProperties() {
            createdDate = null;
        }
    }

    protected class MitabUpdateDateList extends AbstractListHavingPoperties<Date> {
        public MitabUpdateDateList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Date added) {

            if (updatedDate == null){
                updatedDate = added;
            }
        }

        @Override
        protected void processRemovedObjectEvent(Date removed) {

            if (updatedDate != null && updatedDate == removed && !isEmpty()){
                updatedDate = updateDate.iterator().next();
            }

            if (isEmpty()){
                updatedDate = null;
            }
        }

        @Override
        protected void clearProperties() {
            updatedDate = null;
        }
    }
}