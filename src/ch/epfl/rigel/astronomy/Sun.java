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
    private final static String sunName = "Soleil";
    private static float sunPhase;
    private final static float magnitude = -26.7f;
    private EclipticCoordinates eclipticPos;
    private float meanAnomaly;

    /**
     * Sun package-private constructor returning a celestial object and more precisely the sun except if the parameters are not working
     *
     * @param eclipticPos   (EclipticCoordinates) : gives the ecliptic coordinates of the sun
     * @param equatorialPos (EquatorialCoordinates) : gives the equatorial coordinates of the planet
     * @param angularSize   (float) : gives the angular size of the planet
     * @param meanAnomaly   (float) : gives the anomaly
     */
    public Sun(EclipticCoordinates eclipticPos, EquatorialCoordinates equatorialPos, float angularSize, float meanAnomaly) {
        super(sunName, equatorialPos, angularSize, magnitude);
        this.meanAnomaly = meanAnomaly;
        this.eclipticPos = Objects.requireNonNull(eclipticPos);

        if (eclipticPos.lon() == 0 & eclipticPos.lat() == 0) {
            throw new NullPointerException();
        }
    }

    /**
     * Sun method returning the ecliptic position of the sun
     *
     * @return eclipticPos  (EclipticCoordinates) : returning the ecliptic position of the sun
     */
    public EclipticCoordinates eclipticPos() {
        return eclipticPos;
    }

    /**
     * Sun method returning its anomaly
     *
     * @return meanAnomaly  (double) : returning the anomaly of the sun
     */
    public double meanAnomaly() {
        return meanAnomaly;
    }
}

