package psidev.psi.mi.jami.enricher.impl.featureevidence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.FeatureEvidenceFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEvidenceEnricher;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.featureevidence.listener.FeatureEvidenceEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.PositionUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
import uk.ac.ebi.intact.commons.util.DiffUtils;
import uk.ac.ebi.intact.commons.util.diff.Diff;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:55
 */
public abstract  class AbstractFeatureEvidenceEnricher
        implements FeatureEvidenceEnricher{

    protected static final Logger log = LoggerFactory.getLogger(AbstractFeatureEvidenceEnricher.class.getName());

    private FeatureEvidenceFetcher featureEvidenceFetcher;
    protected FeatureEvidenceEnricherListener listener;

    protected CvTermEnricher cvTermEnricher;

    public boolean enrichFeatureEvidence(FeatureEvidence featureEvidenceToEnrich, String sequenceOld, String sequenceNew) throws BridgeFailedException, MissingServiceException, BadToEnrichFormException, BadSearchTermException {

        if(featureEvidenceToEnrich ==  null) {
            if(listener != null) listener.onFeatureEvidenceEnriched(featureEvidenceToEnrich,
                    "Failed. Attempted to enrich null featureEvidence");
            return false;
        }

        if(cvTermEnricher != null){
            cvTermEnricher.enrichCvTerm(featureEvidenceToEnrich.getType());
            for(CvTerm cvTerm : featureEvidenceToEnrich.getDetectionMethods()){
                cvTermEnricher.enrichCvTerm(cvTerm);
            }
        }

        if(sequenceOld == null){
            if(listener != null) listener.onFeatureEvidenceEnriched(featureEvidenceToEnrich,
                    "Failed. The original sequence is null which invalidates all features.");
            return false;
        }

        //boolean allValid = true;
        for(Range range : featureEvidenceToEnrich.getRanges()) {

            if( range.getStart().isPositionUndetermined()
                    || range.getEnd().isPositionUndetermined()){
                //Entry already considered invalid;
            }
            else if( PositionUtils.areRangePositionsValid(
                        range.getStart().getStart(), range.getEnd().getEnd())
                    && PositionUtils.areRangePositionsOutOfBounds(
                        range.getStart().getStart(), range.getEnd().getEnd(),
                        sequenceOld.length())) {
                //VALID
            }
            else {
                if(listener != null) ;//listener. note that the feature has invalid range
                range.getStart().getStatus().getAnnotations().add(
                        AnnotationUtils.createCaution(
                                "Invalid range: "+
                                range.getStart().getStart() + "," +
                                range.getEnd().getEnd()));  //todo
                //range.getStart().PositionUtils.createUndeterminedPosition());
                //allValid = false;
            }
        }
        /*if( ! allValid){

            if(listener != null) listener.onFeatureEvidenceEnriched(featureEvidenceToEnrich,
                    "Failed. Feature has invalid ranges.");
            return false;
        } */


        List<Diff> sequenceChanges  = DiffUtils.diff(sequenceOld , sequenceNew);

        if(sequenceChanges != null
                && ! sequenceChanges.isEmpty()){
            for(Range range : featureEvidenceToEnrich.getRanges()) {
                if( ! range.getStart().isPositionUndetermined()
                    && ! range.getEnd().isPositionUndetermined()){
                    // Try and remap the valid sequences.
                    String oldValue = sequenceOld.substring(
                            (int)range.getStart().getStart() , (int)range.getEnd().getEnd());

                    range.setPositions(
                            PositionUtils.createCertainPosition(
                                    DiffUtils.calculateIndexShift(sequenceChanges ,(int)range.getStart().getStart())),
                            PositionUtils.createCertainPosition(
                                    DiffUtils.calculateIndexShift(sequenceChanges ,(int)range.getEnd().getEnd())));

                    String newValue = sequenceNew.substring(
                            (int) range.getStart().getStart(), (int) range.getEnd().getEnd());

                    if( ! oldValue.equalsIgnoreCase(newValue)){ //TODO
                        log.warn("Sub sequence: " + oldValue +
                        " is not equal to new sub sequence: " + newValue);
                    }
                }

            }

        }

        return true;
    }

    protected abstract boolean processFeatureEvidence(FeatureEvidence featureEvidenceToEnrich);

    public void setFeatureEvidenceFetcher(FeatureEvidenceFetcher featureEvidenceFetcher) {
        this.featureEvidenceFetcher = featureEvidenceFetcher;
    }

    public FeatureEvidenceFetcher getFeatureEvidenceFetcher() {
        //Todo lazy load
        return featureEvidenceFetcher;
    }

    public void setFeatureEvidenceEnricherListener(FeatureEvidenceEnricherListener featureEvidenceEnricherListener) {
        this.listener = featureEvidenceEnricherListener;
    }

    public FeatureEvidenceEnricherListener getFeatureEvidenceEnricherListener() {
        return listener;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    public CvTermEnricher getCvTermEnricher(){
        return cvTermEnricher;
    }
}