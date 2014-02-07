package psidev.psi.mi.jami.enricher.util;

import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.listener.*;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.annotation.DefaultAnnotationComparator;
import psidev.psi.mi.jami.utils.comparator.checksum.DefaultChecksumComparator;
import psidev.psi.mi.jami.utils.comparator.confidence.DefaultConfidenceComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.DefaultParameterComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.Collection;
import java.util.Iterator;

/**
 * Utilities for enrichers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 10/07/13
 */
public class EnricherUtils {

    /* Characters to be used for new rows, new columns, blank cells */
    public static final String NEW_LINE = "\n";
    public static final String NEW_EVENT = "\t";
    public static final String BLANK_SPACE = "-";

    /**
     * Takes two lists of Xrefs and produces a list of those to add and those to remove.
     *
     * It will add in toEnrichXrefs all xref from fetchedXrefs that are not there. It will also remove extra identifiers from toEnrichXrefs
     * if remove boolean is true.
     *
     *
     * @param termToEnrich     The object to enrich
     * @param fetchedXrefs      The new xrefs to be added.
     * @param remove: if true, we remove xrefs that are not in enriched list
     * @param isIdentifier if true compare identifiers, otherwise xrefs
     */
    public static <T extends Object> void mergeXrefs(T termToEnrich, Collection<Xref> toEnrichXrefs, Collection<Xref> fetchedXrefs , boolean remove,
                              boolean isIdentifier, XrefsChangeListener<T> xrefListener, IdentifiersChangeListener<T> identifierListener){

        Iterator<Xref> refIterator = toEnrichXrefs.iterator();
        // remove xrefs in toEnrichXrefs that are not in fetchedXrefs
        if (remove){
            while(refIterator.hasNext()){
                Xref ref = refIterator.next();
                boolean containsRef = false;
                for (Xref ref2 : fetchedXrefs){
                    // identical xrefs
                    if (DefaultXrefComparator.areEquals(ref, ref2)){
                        containsRef = true;
                        break;
                    }
                }
                // remove xref not in second list
                if (!containsRef){
                    refIterator.remove();
                    if (isIdentifier){
                        if (identifierListener != null){
                            identifierListener.onRemovedIdentifier(termToEnrich, ref);
                        }
                    }
                    else{
                        if (xrefListener != null){
                            xrefListener.onRemovedXref(termToEnrich, ref);
                        }
                    }
                }
            }
        }

        // add xrefs from fetchedXrefs that are not in toEnrichXref
        refIterator = fetchedXrefs.iterator();
        while(refIterator.hasNext()){
            Xref ref = refIterator.next();
            boolean containsRef = false;
            for (Xref ref2 : toEnrichXrefs){
                // when we allow to removexrefs, we compare qualifiers as well
                if (remove){
                    // identical xrefs
                    if (DefaultXrefComparator.areEquals(ref, ref2)){
                        containsRef = true;
                        break;
                    }
                }
                // when we don't allow to remove xrefs, we compare only database+identifier to avoid dupicating xrefs with same db/id but different qualifiers.
                // it would be too confusing for the suer
                else{
                    // identical identifier
                    if (DefaultExternalIdentifierComparator.areEquals(ref, ref2)){
                        containsRef = true;
                        break;
                    }
                }
            }
            // add missing xref not in second list
            if (!containsRef){
                toEnrichXrefs.add(ref);
                if (isIdentifier){
                    if (identifierListener != null){
                        identifierListener.onAddedIdentifier(termToEnrich, ref);
                    }
                }
                else{
                    if (xrefListener != null){
                        xrefListener.onAddedXref(termToEnrich, ref);
                    }
                }
            }
        }
    }

    public static <T extends Object> void mergeAliases(T termToEnrich, Collection<Alias> toEnrichAliases, Collection<Alias> fetchedAliases, boolean remove, AliasesChangeListener<T> aliasListener){
        Iterator<Alias> aliasIterator = toEnrichAliases.iterator();
        // remove aliases in toEnrichAliases that are not in fetchedAliases
        if (remove){
            while(aliasIterator.hasNext()){
                Alias alias = aliasIterator.next();
                boolean containsAlias = false;
                for (Alias alias2 : fetchedAliases){
                    // identical aliases
                    if (DefaultAliasComparator.areEquals(alias, alias2)){
                        containsAlias = true;
                        break;
                    }
                }
                // remove alias not in second list
                if (!containsAlias){
                    aliasIterator.remove();
                    if (aliasListener != null){
                        aliasListener.onRemovedAlias(termToEnrich, alias);
                    }
                }
            }
        }

        // add xrefs from fetchedXrefs that are not in toEnrichXref
        aliasIterator = fetchedAliases.iterator();
        while(aliasIterator.hasNext()){
            Alias alias = aliasIterator.next();
            boolean containsAlias = false;
            for (Alias alias2 : toEnrichAliases){
                // identical aliases
                if (DefaultAliasComparator.areEquals(alias, alias2)){
                    containsAlias = true;
                    break;
                }
            }
            // add missing xref not in second list
            if (!containsAlias){
                toEnrichAliases.add(alias);
                if (aliasListener != null){
                    aliasListener.onAddedAlias(termToEnrich, alias);
                }
            }
        }
    }

    public static <T extends Object> void mergeChecksums(T termToEnrich, Collection<Checksum> toEnrichChecksums, Collection<Checksum> fetchedCehcksum, boolean remove, ChecksumsChangeListener<T> aliasListener){
        Iterator<Checksum> checksumIterator = toEnrichChecksums.iterator();
        // remove aliases in toEnrichAliases that are not in fetchedAliases
        if (remove){
            while(checksumIterator.hasNext()){
                Checksum checksum = checksumIterator.next();
                boolean containsChecksum = false;
                for (Checksum checksum2 : fetchedCehcksum){
                    // identical checksum
                    if (DefaultChecksumComparator.areEquals(checksum, checksum2)){
                        containsChecksum = true;
                        break;
                    }
                }
                // remove alias not in second list
                if (!containsChecksum){
                    checksumIterator.remove();
                    if (aliasListener != null){
                        aliasListener.onRemovedChecksum(termToEnrich, checksum);
                    }
                }
            }
        }

        // add xrefs from fetchedXrefs that are not in toEnrichXref
        checksumIterator = fetchedCehcksum.iterator();
        while(checksumIterator.hasNext()){
            Checksum checksum = checksumIterator.next();
            boolean containsChecksum = false;
            for (Checksum checksum2 : toEnrichChecksums){
                // identical aliases
                if (DefaultChecksumComparator.areEquals(checksum, checksum2)){
                    containsChecksum = true;
                    break;
                }
            }
            // add missing xref not in second list
            if (!containsChecksum){
                toEnrichChecksums.add(checksum);
                if (aliasListener != null){
                    aliasListener.onAddedChecksum(termToEnrich, checksum);
                }
            }
        }
    }

    public static <T extends Object> void mergeAnnotations(T termToEnrich, Collection<Annotation> toEnrichAnnotations, Collection<Annotation> fetchedAnnotations, boolean remove, AnnotationsChangeListener<T> annotationListener){
        Iterator<Annotation> annotIterator = toEnrichAnnotations.iterator();
        // remove aliases in toEnrichAliases that are not in fetchedAliases
        if (remove){
            while(annotIterator.hasNext()){
                Annotation annot = annotIterator.next();

                boolean containsAnnotation = false;
                for (Annotation annot2 : fetchedAnnotations){
                    // identical annot
                    if (DefaultAnnotationComparator.areEquals(annot, annot2)){
                        containsAnnotation = true;
                        break;
                    }
                }
                // remove alias not in second list
                if (!containsAnnotation){
                    annotIterator.remove();
                    if (annotationListener != null){
                        annotationListener.onRemovedAnnotation(termToEnrich, annot);
                    }
                }
            }
        }

        // add annotations from fetchedAnnotatioms that are not in toEnrichAnnotations
        annotIterator = fetchedAnnotations.iterator();
        while(annotIterator.hasNext()){
            Annotation annot = annotIterator.next();
            boolean containsChecksum = false;
            for (Annotation annot2 : toEnrichAnnotations){
                // identical annot
                if (DefaultAnnotationComparator.areEquals(annot, annot2)){
                    containsChecksum = true;
                    break;
                }
            }
            // add missing xref not in second list
            if (!containsChecksum){
                toEnrichAnnotations.add(annot);
                if (annotationListener != null){
                    annotationListener.onAddedAnnotation(termToEnrich, annot);
                }
            }
        }
    }

    public static <T extends Object> void mergeConfidences(T termToEnrich, Collection<Confidence> toEnrichConfidences, Collection<Confidence> fetchedConfidences, boolean remove, ConfidencesChangeListener<T> confListener){
        Iterator<Confidence> confIterator = toEnrichConfidences.iterator();
        // remove confidences in toEnrichConfidences that are not in fetchedConfidences
        if (remove){
            while(confIterator.hasNext()){
                Confidence conf = confIterator.next();

                boolean containsConf = false;
                for (Confidence conf2 : fetchedConfidences){
                    // identical confidence
                    if (DefaultConfidenceComparator.areEquals(conf, conf2)){
                        containsConf = true;
                        break;
                    }
                }
                // remove confidence not in second list
                if (!containsConf){
                    confIterator.remove();
                    if (confListener != null){
                        confListener.onRemovedConfidence(termToEnrich, conf);
                    }
                }
            }
        }

        // add confidences from fetchedConfidences that are not in toEnrichConfidences
        confIterator = fetchedConfidences.iterator();
        while(confIterator.hasNext()){
            Confidence conf = confIterator.next();
            boolean containsConf = false;
            for (Confidence conf2 : toEnrichConfidences){
                // identical confidence
                if (DefaultConfidenceComparator.areEquals(conf, conf2)){
                    containsConf = true;
                    break;
                }
            }
            // add missing confidence not in second list
            if (!containsConf){
                toEnrichConfidences.add(conf);
                if (confListener != null){
                    confListener.onAddedConfidence(termToEnrich, conf);
                }
            }
        }
    }

    public static <T extends Object> void mergeParameters(T termToEnrich, Collection<Parameter> toEnrichParameters, Collection<Parameter> fetchedParameters, boolean remove, ParametersChangeListener<T> paramListener){
        Iterator<Parameter> paramIterator = toEnrichParameters.iterator();
        // remove parameters in toEnrichParameters that are not in fetchedParameters
        if (remove){
            while(paramIterator.hasNext()){
                Parameter param = paramIterator.next();

                boolean containsParam = false;
                for (Parameter param2 : fetchedParameters){
                    // identical parameter
                    if (DefaultParameterComparator.areEquals(param, param2)){
                        containsParam = true;
                        break;
                    }
                }
                // remove parameter not in second list
                if (!containsParam){
                    paramIterator.remove();
                    if (paramListener != null){
                        paramListener.onRemovedParameter(termToEnrich, param);
                    }
                }
            }
        }

        // add parameters from fetchedParameters that are not in toEnrichParameters
        paramIterator = fetchedParameters.iterator();
        while(paramIterator.hasNext()){
            Parameter param = paramIterator.next();
            boolean containsParam = false;
            for (Parameter param2 : toEnrichParameters){
                // identical parameter
                if (DefaultParameterComparator.areEquals(param, param2)){
                    containsParam = true;
                    break;
                }
            }
            // add missing parameter not in second list
            if (!containsParam){
                toEnrichParameters.add(param);
                if (paramListener != null){
                    paramListener.onAddedParameter(termToEnrich, param);
                }
            }
        }
    }

    public static <P extends Participant,F extends Feature> void mergeParticipants(Interaction termToEnrich, Collection<P> toEnrichParticipants, Collection<P> fetchedParticipants, boolean remove, InteractionChangeListener interactionListener,
                                                                 ParticipantEnricher<P,F> participantEnricher) throws EnricherException {
        Iterator<P> partIterator = toEnrichParticipants.iterator();
        // remove participants in toEnrichParticipants that are not in fetchedParticipants
        if (remove){
            while(partIterator.hasNext()){
                P part = partIterator.next();

                boolean containsPart = false;
                for (P part2 : fetchedParticipants){
                    // identical participant
                    if (part == part2){
                        containsPart = true;
                        break;
                    }
                }
                // remove participant not in second list
                if (!containsPart){
                    partIterator.remove();
                    part.setInteraction(null);
                    if (interactionListener != null){
                        interactionListener.onRemovedParticipant(termToEnrich, part);
                    }
                }
            }
        }

        // add confidences from fetchedConfidences that are not in toEnrichConfidences
        partIterator = fetchedParticipants.iterator();
        while(partIterator.hasNext()){
            P part = partIterator.next();
            boolean containsPart = false;
            for (P part2 : toEnrichParticipants){
                // identical participants
                if (part == part2){
                    containsPart = true;
                    if (participantEnricher != null){
                        participantEnricher.enrich(part2, part);
                    }
                    break;
                }
            }
            // add missing confidence not in second list
            if (!containsPart){
                termToEnrich.addParticipant(part);
                if (interactionListener != null){
                    interactionListener.onAddedParticipant(termToEnrich, part);
                }
            }
        }
    }
}
