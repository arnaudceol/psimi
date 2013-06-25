package psidev.psi.mi.jami.tab.io.writer.extended;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.model.InteractionEvidence;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Mitab 2.6 writer for interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class Mitab26InteractionEvidenceWriter extends Mitab25InteractionEvidenceWriter {

    public Mitab26InteractionEvidenceWriter() {
        super();
    }

    public Mitab26InteractionEvidenceWriter(File file) throws IOException {
        super(file);
    }

    public Mitab26InteractionEvidenceWriter(OutputStream output) {
        super(output);
    }

    public Mitab26InteractionEvidenceWriter(Writer writer) {
        super(writer);
    }

    public Mitab26InteractionEvidenceWriter(OutputStream output, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) {
        super(output, expansionMethod);
    }

    public Mitab26InteractionEvidenceWriter(File file, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public Mitab26InteractionEvidenceWriter(Writer writer, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) {
        super(writer, expansionMethod);
    }

    @Override
    protected void initialiseWriter(Writer writer){
        setBinaryWriter(new Mitab26BinaryEvidenceWriter(writer));
    }

    @Override
    protected void initialiseOutputStream(OutputStream output) {
        setBinaryWriter(new Mitab26BinaryEvidenceWriter(output));
    }

    @Override
    protected void initialiseFile(File file) throws IOException {
        setBinaryWriter(new Mitab26BinaryEvidenceWriter(file));
    }
}