package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

import java.util.Locale;


/**
 * The moon
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class Moon extends CelestialObject {

    private static float moonPhase;
    private final static String moonName = "Lune";

        /**
         * Moon package-private constructor returning a celestial object and more precisely the moon except if the phase isn't in the interval
         * @param equatorialPos   (EquatorialCoordinates) : gives the equatorial coordinates of the planet
         * @param angularSize     (float) : gives the angular size of the planet
         * @param magnitude       (float) : gives the magnitude of the planet
         * @param phase           (float) : gives the phase 
         */
    public Moon(EquatorialCoordinates equatorialPos, float angularSize, float magnitude, float phase) {

        super(moonName, equatorialPos, angularSize, magnitude);

        if (!(ClosedInterval.of(0, 1).contains(phase))) {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Moon method returning a string with the informations around the moon
     *
     * @return stringMoon (String) : returining the name of the moon with its phase
     */
    @Override
    public String info(){
        return moonName + " (" +String.format(Locale.ROOT,"(%.1f)",moonPhase*100)+ "%)";
    }
}
