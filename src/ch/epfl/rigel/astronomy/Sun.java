package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.util.Objects;

/**
 * The sun
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class Sun extends CelestialObject {

    private final double meanAn;
    private final EclipticCoordinates eclipticPosition;

    /**
     * Sun public constructor returning a celestial object and more precisely the sun except if the parameters are not working
     *
     * @param eclipticPos   (EclipticCoordinates) : gives the ecliptic coordinates of the sun
     * @param equatorialPos (EquatorialCoordinates) : gives the equatorial coordinates of the planet
     * @param angularSize   (float) : gives the angular size of the planet
     * @param meanAnomaly   (float) : gives the anomaly
     */
    public Sun(EclipticCoordinates eclipticPos, EquatorialCoordinates equatorialPos, float angularSize, float meanAnomaly) {

        super("Soleil", equatorialPos, angularSize, -26.7f);

        Objects.requireNonNull(eclipticPos);

        eclipticPosition = eclipticPos;
        meanAn = meanAnomaly;
    }

    /**
     * Public method returning the ecliptic position of the sun
     *
     * @return eclipticPositionAn (EclipticCoordinates) : return the ecliptic coordinates of the sun
     */
    public EclipticCoordinates eclipticPos() {
        return eclipticPosition;
    }

    /**
     * Public method returning the anomaly
     *
     * @return meanAn (double) : return the anomaly
     */
    public double meanAnomaly() {
        return meanAn;
    }
}
