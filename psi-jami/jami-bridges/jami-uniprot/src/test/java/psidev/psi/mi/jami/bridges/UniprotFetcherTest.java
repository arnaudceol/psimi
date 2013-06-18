package psidev.psi.mi.jami.bridges;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.uniprot.UniprotFetcher;
import psidev.psi.mi.jami.model.Protein;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 29/05/13
 * Time: 16:16
 */
public class UniprotFetcherTest {

    private UniprotFetcher fetcher;

    @Before
    public void initialiseFetcher() {
        fetcher = new UniprotFetcher();
    }

    //--------------MASTER



    //--------------ISOFORM

    /**
     * For a set of isoform identifiers,
     * Check that the regular expression catches them,
     * Check that they return a single protein.
     * Check that the protein has only expected fields.
     *
     * @throws BadSearchTermException
     * @throws BadResultException
     * @throws BridgeFailedException
     */
    @Test
    public void test_isoform_returned_identifier()
            throws BadSearchTermException, BadResultException, BridgeFailedException {

        String[] identifiers = {"Q6ZRI6-3", "P13055-2"};

        for(String identifier : identifiers){
            assertTrue(fetcher.UNIPROT_MASTER_REGEX.matcher(identifier).find());
            assertTrue(fetcher.UNIPROT_ISOFORM_REGEX.matcher(identifier).find());

            Collection<Protein> proteins = fetcher.getProteinsByIdentifier(identifier);
            for(Protein protein : proteins){
                assertEquals(identifier, protein.getShortName());
                assertNotNull(protein.getOrganism());
                assertNotNull(protein.getSequence());
                assertTrue(protein.getXrefs().size() == 1);
                assertNull(protein.getRogid());
            }
        }
    }

    //--------------FEATURE CHAIN

    /**
     * For a set of feature chain identifiers,
     * Check that the regular expression catches them,
     * check that they return a single protein.
     *
     * @throws BadSearchTermException
     * @throws BadResultException
     * @throws BridgeFailedException
     */
    @Test
    public void test_feature_chain_search_regular_expression()
            throws BadSearchTermException, BadResultException, BridgeFailedException {

        String[] identifiers = {
                "PRO_0000030311",
                "P19838-PRO_0000030311",
                "PRO_0000021413",
                "PRO_0000021416",
                "PRO_0000021449"};

        for(String identifier : identifiers){
            assertTrue(fetcher.UNIPROT_PRO_REGEX.matcher(identifier).find());
            assertEquals(1, fetcher.getProteinsByIdentifier(identifier).size());
        }
    }

    /**
     * Testing a fringe case where the sub sequence continues to the end.
     * For the chosen identifier,
     * Check one protein is returned,
     * check that the sequence it returns is of the correct length
     * check that the sequence is returns is correct.
     *
     * @throws BadSearchTermException
     * @throws BadResultException
     * @throws BridgeFailedException
     */
    @Test
    public void test_feature_chain_search_has_correct_sequence_for_truncation_to_end() throws BadSearchTermException, BadResultException, BridgeFailedException {
        Collection<Protein> proteins;

        //Sequence and length were independently verified at:
        //http://www.uniprot.org/uniprot/P15515
        proteins = fetcher.getProteinsByIdentifier("PRO_0000021416");
        //Fringe case - the end is at the maximum length
        assertTrue(proteins.size() == 1);

        for(Protein protein : proteins){
            assertEquals(38, protein.getSequence().length());
            assertEquals("DSHEKRHHGYRRKFHEKHHSHREFPFYGDYGSNYLYDN",
                    protein.getSequence());
        }
    }

    /**
     * Testing a fringe case where the sub sequence begins on the first position.
     * For the chosen identifier,
     * Check one protein is returned,
     * check that the sequence it returns is of the correct length
     * check that the sequence is returns is correct.
     *
     * @throws BadSearchTermException
     * @throws BadResultException
     * @throws BridgeFailedException
     */
    @Test
    public void test_feature_chain_search_has_correct_sequence_for_truncation_from_start()
            throws BadSearchTermException, BadResultException, BridgeFailedException {

        Collection<Protein> proteins;

        //Sequence and length were independently verified at:
        //http://www.uniprot.org/uniprot/Q9TQY7
        proteins = fetcher.getProteinsByIdentifier("PRO_0000015868");
        //Fringe case - the beginning is the first position
        assertTrue(proteins.size() == 1);

        for(Protein protein : proteins){
            assertEquals(30, protein.getSequence().length());
            assertEquals("FPNQHLCGSHLVEALYLVCGEKGFYYIPRM",
                    protein.getSequence());
        }
    }
}