package psidev.psi.mi.tab.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.datasource.*;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.tab.PsimiTabIterator;
import psidev.psi.mi.tab.PsimiTabReader;
import psidev.psi.mi.tab.events.ClusteredColumnEvent;
import psidev.psi.mi.tab.events.InvalidFormatEvent;
import psidev.psi.mi.tab.events.MissingCvEvent;
import psidev.psi.mi.tab.model.BinaryInteraction;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * A simple Mitab datasource.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class SimpleMitabDataSource implements StreamingInteractionSource, MitabParserListener{

    private PsimiTabReader reader;
    private Iterator<BinaryInteraction> mitabIterator;
    private File file;
    private InputStream stream;
    private Collection<FileSourceError> errors;

    private Log log = LogFactory.getLog(SimpleMitabDataSource.class);

    public SimpleMitabDataSource(File file){
        this.reader = new PsimiTabReader();
        this.reader.addMitabParserListener(this);
        this.file = file;
        if (file == null){
           throw new IllegalArgumentException("File is mandatory for a MITAb datasource");
        }
        errors = new ArrayList<FileSourceError>();
    }

    public SimpleMitabDataSource(InputStream stream){
        this.reader = new PsimiTabReader();
        this.reader.addMitabParserListener(this);
        this.stream = stream;
        if (stream == null){
            throw new IllegalArgumentException("InputStream is mandatory for a MITAb datasource");
        }
        errors = new ArrayList<FileSourceError>();
    }

    public Iterator<? extends InteractionEvidence> getInteractionEvidencesIterator() {
        if (mitabIterator == null){
            open();
        }
        return mitabIterator;
    }

    public Iterator<? extends ModelledInteraction> getModelledInteractionsIterator() {
        return null;
    }

    public Iterator<? extends CooperativeInteraction> getCooperativeInteractionsIterator() {
        return null;
    }

    public Iterator<? extends AllostericInteraction> getAllostericInteractionsIterator() {
        return null;
    }

    public Iterator<? extends Interaction> getInteractionsIterator() {
        return getInteractionEvidencesIterator();
    }

    public void initialiseContext(Map<String, Object> stringObjectMap) {
        // do nothing
    }

    public Collection<FileSourceError> getDataSourceErrors() {
        return errors;
    }

    public void open() {
        if (file != null){
            try {
                this.mitabIterator = this.reader.iterate(file);
            } catch (IOException e) {
                log.error("Impossible to parse current file");
                this.mitabIterator = null;

                FileSourceError error = new FileSourceError(e.getCause().toString(), e.getMessage(), new DefaultFileSourceContext());
                this.errors.add(error);
            }
        }
        else {
            try {
                this.mitabIterator = this.reader.iterate(stream);
            } catch (IOException e) {
                log.error("Impossible to parse current InputStream");
                this.mitabIterator = null;

                FileSourceError error = new FileSourceError(e.getCause().toString(), e.getMessage(), new DefaultFileSourceContext());
                this.errors.add(error);            }
        }
    }

    public void close() {
        if (mitabIterator != null){
            ((PsimiTabIterator) mitabIterator).closeStreamReader();
            mitabIterator = null;
        }
    }

    public void fireOnInvalidFormat(InvalidFormatEvent event){
        FileSourceError error = new FileSourceError(FileParsingErrorType.invalid_syntax.toString(), event.getMessage(), event);
        this.errors.add(error);
    }

    public void fireOnClusteredColumnEvent(ClusteredColumnEvent event) {
        FileSourceError error = new FileSourceError(event.getErrorType().toString(), event.getMessage(), event);
        this.errors.add(error);
    }

    public void fireOnMissingCvEvent(MissingCvEvent event) {
        FileSourceError error = new FileSourceError(event.getErrorType().toString(), event.getMessage(), event);;
        this.errors.add(error);
    }

}