package ch.epfl.rigel.coordinates;

import java.util.Locale;

import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import static ch.epfl.rigel.Preconditions.checkArgument;

/**
 * A geographic coordinate
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class GeographicCoordinates extends SphericalCoordinates {

    //initializing the intervals for the geographic coordinates
    private final static RightOpenInterval LON_INTERVAL = RightOpenInterval.symmetric(Angle.TAU);
    private final static ClosedInterval LAT_INTERVAL = ClosedInterval.symmetric(Angle.TAU / 2.0);

    /**
     * GeographicCoordinates private constructor
     *
     * @param longitude (double) : gives the longitude of the position
     * @param latitude  (double) : gives the latitude of the position
     */
    private GeographicCoordinates(double longitude, double latitude) {
        super(longitude, latitude);
    }

    /**
     * Public method used to return the geographic coordinates while throwing an exception if not working
     *
     * @param lonDeg (double) : gives the longitude value in deg of the position
     * @param latDeg (double) : gives the latitude value in deg of the position
     * @return (GeographicCoordinates) : return the geographic coordinates or throw exception if the coords are out of the intervals
     */
    public static GeographicCoordinates ofDeg(double lonDeg, double latDeg) {
        checkArgument(isValidLonDeg(lonDeg) && isValidLatDeg(latDeg));
        return new GeographicCoordinates(Angle.ofDeg(lonDeg), Angle.ofDeg(latDeg));
    }

    /**
     * Method validating if the entered value is valid
     *
     * @param lonDeg (double) : gives the longitude of the position in degrees
     * @return (boolean) : returns whether the interval contains the longitude
     */
    public static boolean isValidLonDeg(double lonDeg) {
        return LON_INTERVAL
                .contains(Angle.ofDeg(lonDeg));
    }

    /**
     * Method validating if the entered value is valid
     *
     * @param latDeg (double) : gives the latitude of the position in degrees
     * @return (boolean) : returns whether the interval contains the latitude
     */
    public static boolean isValidLatDeg(double latDeg) {
        return LAT_INTERVAL
                .contains(Angle.ofDeg(latDeg));
    }

    /**
     * GeographicCoordinates overrode public method returning the longitude in radians
     *
     * @return (double) : returns the longitude in radians
     */
    @Override
    public double lon() {
        return super.lon();
    }

    /**
     * GeographicCoordinates overrode public method returning the longitude in degrees
     *
     * @return (double) : returns the longitude in degrees
     */
    @Override
    public double lonDeg() {
        return super.lonDeg();
    }

    /**
     * GeographicCoordinates overrode public method returning the latitude in radians
     *
     * @return (double) : returns the latitude in radians
     */
    @Override
    public double lat() {
        return super.lat();
    }

    /**
     * GeographicCoordinates overrode public method returning the latitude in degrees
     *
     * @return (double) : returns the latitude in degrees
     */
    @Override
    public double latDeg() {
        return super.latDeg();
    }

    /**
     * CartesianCoordinates overrode method returning a string with the coordinates
     *
     * @return (String) : the string of coordinates
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(lon=%.4f°, lat=%.4f°)", lonDeg(), latDeg());
    }
}
