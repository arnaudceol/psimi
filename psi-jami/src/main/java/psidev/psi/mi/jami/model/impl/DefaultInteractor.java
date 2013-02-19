package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorBaseComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Default implementation for Interactor
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultInteractor implements Interactor, Serializable {

    protected String shortName;
    protected String fullName;
    protected Collection<Xref> identifiers;
    protected Collection<Checksum> checksums;
    protected Collection<Xref> xrefs;
    protected Collection<Annotation> annotations;
    protected Collection<Alias> aliases;
    protected Organism organism;
    protected CvTerm type;

    public DefaultInteractor(String name, CvTerm type){
        if (name == null || (name != null && name.length() == 0)){
            throw new IllegalArgumentException("The short name cannot be null or empty.");
        }
        this.shortName = name;
        if (type == null){
            throw new IllegalArgumentException("The interactor type cannot be null.");
        }
        this.type = type;

        initializeChecksums();
        initializeXrefs();
        initializeAnnotations();
        initializeAliases();
        initializeIdentifiers();
    }

    public DefaultInteractor(String name, String fullName, CvTerm type){
        this(name, type);
        this.fullName = fullName;
    }

    public DefaultInteractor(String name, CvTerm type, Organism organism){
        this(name, type);
        this.organism = organism;
    }

    public DefaultInteractor(String name, String fullName, CvTerm type, Organism organism){
        this(name, fullName, type);
        this.organism = organism;
    }

    public DefaultInteractor(String name, CvTerm type, Xref uniqueId){
        this(name, type);
        this.identifiers.add(uniqueId);
    }

    public DefaultInteractor(String name, String fullName, CvTerm type, Xref uniqueId){
        this(name, fullName, type);
        this.identifiers.add(uniqueId);
    }

    public DefaultInteractor(String name, CvTerm type, Organism organism, Xref uniqueId){
        this(name, type, organism);
        this.identifiers.add(uniqueId);
    }

    public DefaultInteractor(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId){
        this(name, fullName, type, organism);
        this.identifiers.add(uniqueId);
    }

    protected void initializeAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initializeXrefs(){
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initializeAliases(){
        this.aliases = new ArrayList<Alias>();
    }

    protected void initializeIdentifiers(){
        this.identifiers = new ArrayList<Xref>();
    }

    protected void initializeChecksums(){
        this.checksums = new ArrayList<Checksum>();
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String name) {
        if (name == null || (name != null && name.length() == 0)){
             throw new IllegalArgumentException("The short name cannot be null or empty.");
        }
        this.shortName = name;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public Collection<Xref> getIdentifiers() {
        return this.identifiers;
    }

    public Collection<Checksum> getChecksums() {
        return this.checksums;
    }

    public Collection<Xref> getXrefs() {
        return this.xrefs;
    }

    public Collection<Annotation> getAnnotations() {
        return this.annotations;
    }

    public Collection<Alias> getAliases() {
        return this.aliases;
    }

    public Organism getOrganism() {
        return this.organism;
    }

    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    public CvTerm getType() {
        return this.type;
    }

    public void setType(CvTerm type) {
        if (type == null){
            throw new IllegalArgumentException("The interactor type cannot be null.");
        }
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Interactor)){
            return false;
        }

        // use UnambiguousExactInteractor comparator for equals
        return UnambiguousExactInteractorComparator.areEquals(this, (Interactor) o);
    }

    @Override
    public String toString() {
        return shortName + (organism != null ? ", " + organism.toString() : "");
    }

    @Override
    public int hashCode() {
        // use UnambiguousExactInteractorBase comparator for hashcode to avoid instance of calls. It is possible that
        // the method equals will return false and the hashcode will be the same but it is not a big issue
        return UnambiguousExactInteractorBaseComparator.hashCode(this);
    }
}