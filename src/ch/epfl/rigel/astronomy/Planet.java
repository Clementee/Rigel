package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

/**
 * A planet
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class Planet extends CelestialObject{

    /**
     * Planet public constructor returning a celestial object and more precisely a planet
     * 
     * @param name  (String) : gives the name of the planet
     * @param equatorialPos   (EquatorialCoordinates) : gives the equatorial coordinates of the planet
     * @param angularSize (float) : gives the angular size of the planet
     * @param magnitude (float) : gives the magnitude of the planet
     */
    public Planet(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude) {
        
        super(name, equatorialPos, angularSize, magnitude);
    }
}
