package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import java.util.Objects;

import static ch.epfl.rigel.Preconditions.checkArgument;

/**
 * A celestial object
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public abstract class CelestialObject {

    private String celestialName;
    
    private EquatorialCoordinates celestialEquatorialPosition;
    
    private float celestialAngularSize;
    private float celestialMagnitude;

    /**
     * CelestialObject package-private constructor returning a celestial object and initializing some values
     *
     * @param name            (String) : gives the name of the celestial object
     * @param equatorialPos   (EquatorialCoordinates) : gives the equatorial coordinates of the planet
     * @param angularSize     (float) : gives the angular size of the planet
     * @param magnitude       (float) : gives the magnitude of the planet
     */
    public CelestialObject(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude){
        
        celestialName = Objects.requireNonNull(name);
        celestialEquatorialPosition = Objects.requireNonNull(equatorialPos);
        
        checkArgument(angularSize>=0);
        
        celestialAngularSize = angularSize;
        celestialMagnitude = magnitude;
    }

    /**
     * Method name returning the name of the celestial object
     *
     * @return celestialName (String) : return the name of the celestial object
     */
    public String name(){
        
        return celestialName;
    }

    /**
     * Method angularSize returning the angular size of the celestial object
     *
     * @return celestialAngularSize (double) : return the angular size of the celestial object
     */
    public double angularSize(){
        
        return celestialAngularSize;
    }

    /**
     * Method magnitude returning the magnitude of the celestial object
     * @return celestialMagnitude (double) : return the magnitude of the celestial object
     */
    public double magnitude(){
        
        return celestialMagnitude;
    }

    /**
     * Method equatorialPos returning the equatorial coordinates of the celestial object
     *
     * @return celestialEquatorialPosition (EquatorialCoordinates) : return the equatorial position of the celestial object
     */
    public EquatorialCoordinates equatorialPos() {
        
        return celestialEquatorialPosition;
    }

    /**
     * Method info returning the name of the celestial object
     *
     * @return (String) : return the name of the celestial object
     */
    public String info(){
        
        return name();
    }

    /**
     * Overrode method toString returning the name of the celestial object calling the method info()
     *
     * @return (String) : return the name of the celestial object
     */
    @Override
    public String toString(){
        
        return info();
    }
}
