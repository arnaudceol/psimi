package org.hupo.psi.calimocho.io;

import junit.framework.Assert;
import org.hupo.psi.calimocho.AbstractCalimochoTest;
import org.hupo.psi.calimocho.model.DocumentDefinition;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class DocumentConverterTest extends AbstractCalimochoTest{

    @Test
    public void convert() throws Exception {
        String geneList = "lalagene|3\n" +
                          "lolo|5";

        ByteArrayInputStream bais = new ByteArrayInputStream( geneList.getBytes() );

        final DocumentDefinition docDefinition = super.buildGeneListDefinition();
        final DocumentDefinition anotherDocDefinition = super.buildAnotherGeneListDefinition();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(  );

        DocumentConverter documentConverter = new DocumentConverter(docDefinition, anotherDocDefinition);
        documentConverter.convert( bais, baos );

        String[] lines = baos.toString().split( "\n" );

        Assert.assertEquals( "'taxid:3','gene:lalagene'", lines[0].trim() );
        Assert.assertEquals( "'taxid:5','gene:lolo'", lines[1].trim() );
    }

    @Test
    public void convert2() throws Exception {
        String geneList = "'taxid:3','gene:lalagene'\n" +
                          "'taxid:5','gene:lolo'";

        ByteArrayInputStream bais = new ByteArrayInputStream( geneList.getBytes() );

        final DocumentDefinition docDefinition = super.buildGeneListDefinition();
        final DocumentDefinition anotherDocDefinition = super.buildAnotherGeneListDefinition();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(  );

        DocumentConverter documentConverter = new DocumentConverter(anotherDocDefinition, docDefinition);
        documentConverter.convert( bais, baos );

        String[] lines = baos.toString().split( "\n" );

        Assert.assertEquals( "lalagene|3", lines[0].trim() );
        Assert.assertEquals( "lolo|5", lines[1].trim() );
    }
}
