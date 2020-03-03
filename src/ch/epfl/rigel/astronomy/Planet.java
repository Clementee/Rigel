package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

/**
 * A planet
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class Planet extends CelestialObject{

    public Planet(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude) {
        super(name, equatorialPos, angularSize, magnitude);
    }
}
