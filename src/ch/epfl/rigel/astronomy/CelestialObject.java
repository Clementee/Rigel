package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.util.Objects;

/**
 * A celestial object
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public abstract class CelestialObject {

    private static String celestialName;
    private static EquatorialCoordinates celestialEquatorialPosition;
    private static float celestialAngularSize;
    private static float celestialMagnitude;

    CelestialObject(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude){

        celestialName = Objects.requireNonNull(name);
        celestialEquatorialPosition = Objects.requireNonNull(equatorialPos);

        if (!(angularSize < 0)) {
            celestialAngularSize = angularSize;
        } else {
            throw new IllegalArgumentException();
        }

        celestialMagnitude = magnitude;
    }

    public String name(){
        return celestialName;
    }

    public double angularSize(){
        return celestialAngularSize;
    }

    public double magnitude(){
        return celestialMagnitude;
    }

    public EquatorialCoordinates equatorialPos(){
        return celestialEquatorialPosition;
    }

    public String info(){
        return name();
    }

    @Override
    public String toString(){
        return info();
    }
}
