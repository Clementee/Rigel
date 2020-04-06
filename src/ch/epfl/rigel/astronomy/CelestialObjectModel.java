package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;

/**
 * Model of celestial object
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public interface CelestialObjectModel <O> {

    /**
     * Abstract method calculating the position of the object using a model
     * @param daysSinceJ2010 (double) : the number of days since the first day of 2010
     * @param eclipticToEquatorialConversion (EclipticToEquatorialConversion): the coordinates converter
     * @return (O) : the modelled object
     */
    public abstract O at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion);
    
}
