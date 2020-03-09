package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.RightOpenInterval;

/**
 * A star
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class Star extends CelestialObject {

    private final int starNumber;
    private final float starColorInd;

    /**
     * Star public constructor returning a celestial object and initializing some values
     *
     * @param hipparcosId   (int) : gives the number of the star
     * @param name          (String) : gives the name of the celestial object
     * @param equatorialPos (EquatorialCoordinates) : gives the equatorial coordinates of the planet
     * @param magnitude     (float) : gives the magnitude of the planet
     * @param colorIndex    (float) : gives the index of the color of the star
     */
    public Star(int hipparcosId, String name, EquatorialCoordinates equatorialPos, float magnitude, float colorIndex) {
        
        super(name,equatorialPos,0,magnitude);

        if(RightOpenInterval.of(-0.5,5.5).contains(colorIndex)&&hipparcosId>=0){
            starNumber = hipparcosId;
            starColorInd = colorIndex;
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public int hipparcosId(){
        return starNumber;
    }

    public int colorTemperature(){
        return (int) Math.floor((4600*((1 / (0.92 * starColorInd + 1.7)) + (1 / (0.92 * starColorInd + 0.62)))));
    }
}
