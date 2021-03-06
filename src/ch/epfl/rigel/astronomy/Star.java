package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import static ch.epfl.rigel.Preconditions.checkArgument;
import static ch.epfl.rigel.Preconditions.checkInInterval;

/**
 * A star
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class Star extends CelestialObject {

    private final int starNumber;
    private final float starColorInd;
    private final static ClosedInterval STAR_COLOR_INTERVAL = ClosedInterval.of(-0.5,5.5);
    private final String bayer;

    /**
     * Star public constructor returning a celestial object and initializing some values
     *
     * @param hipparcosId   (int) : gives the number of the star
     * @param name          (String) : gives the name of the celestial object
     * @param equatorialPos (EquatorialCoordinates) : gives the equatorial coordinates of the planet
     * @param magnitude     (float) : gives the magnitude of the planet
     * @param colorIndex    (float) : gives the index of the color of the star
     */
    public Star(int hipparcosId, String name, EquatorialCoordinates equatorialPos, float magnitude, float colorIndex, String bayer) {

        super(name, equatorialPos, 0, magnitude);

        checkInInterval(STAR_COLOR_INTERVAL, colorIndex);
        checkArgument(hipparcosId >= 0);

        starNumber = hipparcosId;
        starColorInd = colorIndex;
        this.bayer = bayer;
    }

    /**
     * Public method bayer returning the bayer string linked to the star
     * 
     * @return bayer (String) : the bayer of the star
     */
    public String bayer(){
        return bayer;
    }

    /**
     * Star method returning the hipparcos number of the star
     *
     * @return starNumber (int) : return the hipparcos number linked to the star
     */
    public int hipparcosId() {
        return starNumber;
    }

    /**
     * Star method returning the temperature linked to the color of the star
     *
     * @return colorTemperature (int) : return the temperature of the color of the star in kelvins
     */
    public int colorTemperature() {
        return (int) Math.floor((4600 * ((1 / (0.92 * starColorInd + 1.7)) + (1 / (0.92 * starColorInd + 0.62)))));
    }
}
