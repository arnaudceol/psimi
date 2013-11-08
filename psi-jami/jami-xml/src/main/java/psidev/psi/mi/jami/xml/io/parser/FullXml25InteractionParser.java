package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.BasicEntrySet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * Full Parser generating basic interaction objects and ignore experimental details.
 *
 * It will load the all entrySet so is consuming a lot of memory in case of large files but is very performant for small files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class FullXml25InteractionParser extends AbstractFullPsiXml25Parser<Interaction<? extends Participant>>{
    public FullXml25InteractionParser(File file) throws XMLStreamException, JAXBException {
        super(file);
    }

    public FullXml25InteractionParser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(inputStream);
    }

    public FullXml25InteractionParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(url);
    }

    public FullXml25InteractionParser(Reader reader) throws XMLStreamException, JAXBException {
        super(reader);
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(BasicEntrySet.class);
        return ctx.createUnmarshaller();
    }
}