/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.impl.DefaultPosition;
import psidev.psi.mi.jami.utils.PositionUtils;
import psidev.psi.mi.jami.utils.clone.CvTermCloner;

/**
 * <p>Java class for positionType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="positionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="position" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Position extends DefaultPosition{

    ///////////////////////////
    // Constructors

    public Position() {
        super(new RangeStatus(), 0);
        this.isPositionUndetermined = true;
    }

    public Position( long position ) {
        super(new RangeStatus(), position);
        this.isPositionUndetermined = true;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the position property.
     *
     * @return the position
     */
    public long getPosition() {
        return start;
    }

    /**
     * Sets the value of the position property.
     *
     * @param position the position
     */
    public void setPosition( long position ) {
        if ( position < 0 ) {
            throw new IllegalArgumentException( "position cannot be negative." );
        }
        this.start = position;
        this.end = position;
    }

    public void setStatus(RangeStatus status){
        if (status != null){
            if (this.status == null){
                this.status = new RangeStatus();
                CvTermCloner.copyAndOverrideCvTermProperties(status, this.status);
            }
            isPositionUndetermined = (PositionUtils.isUndetermined(this) || PositionUtils.isCTerminalRange(this) || PositionUtils.isNTerminalRange(this));
        }
        else {
            this.status = new RangeStatus();
            this.isPositionUndetermined = true;
        }
    }

    /////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Position" );
        sb.append( "{position=" ).append( start );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        final Position position1 = ( Position ) o;

        if ( start != position1.start ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return ( int ) ( start ^ ( start >>> 32 ) );
    }
}