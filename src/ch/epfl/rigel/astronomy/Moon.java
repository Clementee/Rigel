package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

import java.util.Locale;

import static ch.epfl.rigel.Preconditions.checkInInterval;

/**
 * The moon
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class Moon extends CelestialObject {

    private float moonPhase;
    
    private final static String MOON_NAME = "Lune";

        /**
         * Moon package-private constructor returning a celestial object and more precisely the moon except if the phase isn't in the interval
         * 
         * @param equatorialPos   (EquatorialCoordinates) : gives the equatorial coordinates of the planet
         * @param angularSize     (float) : gives the angular size of the planet
         * @param magnitude       (float) : gives the magnitude of the planet
         * @param phase           (float) : gives the phase
         */
        public Moon(EquatorialCoordinates equatorialPos, float angularSize, float magnitude, float phase) {

        super(MOON_NAME, equatorialPos, angularSize, magnitude);

        checkInInterval( ClosedInterval.of(0,1) , phase);
        
        moonPhase = phase*100;
    }

    /**
     * Overrode method info returning the name of the moon under a set form
     * 
     * @return (String) : return the phrase with the name of the moon
     */
    @Override
    public String info(){
            
        return MOON_NAME + " (" +String.format( Locale.ROOT ,"%.1f", moonPhase )+ "%)";
    }
}
