package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Gene;

import java.util.Comparator;

/**
 * Basic genes comparator.
 * It will first use InteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, It will look at ensembl identifier if both are set. If the ensembl identifiers are not both set, it will look at the
 * ensemblGenome identifiers. If at least one ensemblGemome identifiers is not set, it will look at the entrez/gene id. If at least one entrez/gene id is not set, it will look at the refseq identifiers.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class GeneComparator implements Comparator<Gene> {

    protected InteractorBaseComparator interactorComparator;

    public GeneComparator(InteractorBaseComparator interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare genes. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
    }

    /**
     * It will first use InteractorBaseComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, It will look at ensembl identifier if both are set. If the ensembl identifiers are not both set, it will look at the
     * ensemblGenome identifiers. If at least one ensemblGemome identifiers is not set, it will look at the entrez/gene id. If at least one entrez/gene id is not set, it will look at the refseq identifiers.
     *
     * @param gene1
     * @param gene2
     * @return
     */
    public int compare(Gene gene1, Gene gene2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (gene1 == null && gene2 == null){
            return EQUAL;
        }
        else if (gene1 == null){
            return AFTER;
        }
        else if (gene2 == null){
            return BEFORE;
        }
        else {
            // First compares the interactor properties
            int comp = interactorComparator.compare(gene1, gene2);
            if (comp != 0){
                return comp;
            }

            // first compares ensembl identifiers
            String ensembl1 = gene1.getEnsembl();
            String ensembl2 = gene2.getEnsembl();

            if (ensembl1 != null && ensembl2 != null){
                return ensembl1.compareTo(ensembl2);
            }

            // compares ensemblGenomes identifier if at least one ensembl identifier is not set
            String ensemblGenome1 = gene1.getEnsembleGenome();
            String ensemblGenome2 = gene2.getEnsembleGenome();

            if (ensemblGenome1 != null && ensemblGenome2 != null){
                return ensemblGenome1.compareTo(ensemblGenome2);
            }

            // compares entrez/gene Id if at least one ensemblGenomes identifier is not set
            String geneId1 = gene1.getEntrezGeneId();
            String geneId2 = gene2.getEntrezGeneId();

            if (geneId1 != null && geneId2 != null){
                return geneId1.compareTo(geneId2);
            }

            // compares refseq identifier if at least one reseq identifier is not set
            String refseq1 = gene1.getRefseq();
            String refseq2 = gene2.getRefseq();

            if (refseq1 != null && refseq2 != null){
                return refseq1.compareTo(refseq2);
            }

            return comp;
        }
    }

    public InteractorBaseComparator getInteractorComparator() {
        return interactorComparator;
    }
}