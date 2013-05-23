package psidev.psi.mi.jami.bridges.uniprot;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;
import uk.ac.ebi.kraken.uuw.services.remoting.RemoteDataAccessException;
import uk.ac.ebi.kraken.uuw.services.remoting.*;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryRetrievalService;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 13:09
 */
public class UniprotBridge {
    private EntryRetrievalService entryRetrievalService;

    public UniprotBridge() throws BridgeFailedException {
        try{
            entryRetrievalService = UniProtJAPI.factory.getEntryRetrievalService();
        }catch(RemoteDataAccessException e) {
            throw new BridgeFailedException();
        }
    }

    public UniProtEntry fetchEntryByID(String ID)
            throws BridgeFailedException {

        //try{
            UniProtEntry entry = (UniProtEntry) entryRetrievalService.getUniProtEntry(ID);

            if(entry == null) {
                return null;
            }
            return entry;
        /*}catch (RemoteDataAccessException e){
            throw new BridgeFailedException(e);
        } */
    }

    public UniProtEntry fetchEntriesByIDs(String[] IDs)
            throws BridgeFailedException {
        /*
        try{
            UniProtEntry entry = (UniProtEntry) entryRetrievalService.getUniProtEntry(ID);

            if(entry == null) {
                return null;
            }
            return entry;
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException(e);
        } */
        return null;
    }
}