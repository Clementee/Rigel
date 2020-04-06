package ch.epfl.rigel.coordinates;

import java.util.Locale;

import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import static ch.epfl.rigel.Preconditions.checkInInterval;

/**
 * Equatorial coordinates
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class EquatorialCoordinates extends SphericalCoordinates {

    //initializing the intervals for the equatorial coordinates
    private final static RightOpenInterval RA_INTERVAL = RightOpenInterval.of(0, Angle.TAU);
    private final static ClosedInterval DEC_INTERVAL =  ClosedInterval.symmetric(Math.PI);

    /**
     * EquatorialCoordinates package-private constructor
     * 
     * @param rightAscension  (double) : gives the longitude/right ascension of the position
     * @param declination     (double) : gives the latitude/declination of the position
     */
    EquatorialCoordinates(double rightAscension, double declination) { super(rightAscension, declination);}

    /**
     * Public method used to return the equatorial coordinates while throwing an exception if not working
     * 
     * @param ra    (double) : gives the longitude/right ascension value in rad of the position
     * @param dec   (double) : gives the latitude/declination value in rad of the position
     *
     * @return (EquatorialCoordinates) : call the constructor with the entered parameters or throws exception
     */
    public static EquatorialCoordinates of(double ra, double dec) {
        return new EquatorialCoordinates(checkInInterval(RA_INTERVAL , ra),checkInInterval(DEC_INTERVAL , dec));
    }

    /**
     * CartesianCoordinates method returning the right ascension of the coordinates in radians
     *
     * @return (double) : return the right ascension in radians
     */
    public double ra() {
        return super.lon();
    }

    /**
     * CartesianCoordinates method returning the right ascension of the coordinates in degrees
     *
     * @return (double) : return the right ascension in degrees
     */
    public double raDeg() {
        return super.lonDeg();
    }

    /**
     * CartesianCoordinates method returning the right ascension of the coordinates in hours
     *
     * @return (double) : return the right ascension in hours
     */
    public double raHr() {
        double ra = super.lon();
        
        return Angle.toHr(ra);
    }

    /**
     * CartesianCoordinates method returning the declination of the coordinates in radians
     *
     * @return (double) : return the declination in radians
     */
    public double dec() { 
        return super.lat();
    }

    /**
     * CartesianCoordinates method returning the right ascension of the coordinates in degrees
     *
     * @return (double) : return the declination in degrees
     */
    public double decDeg() {
        return super.latDeg();
    }

    /**
     * EquatorialCoordinates overrode method returning a string with the coordinates
     *
     * @return (String) : the string of coordinates
     */
    @Override
    public String toString() { 
        return String.format(Locale.ROOT,"(ra=%.4fh, dec=%.4f°)",raHr(),decDeg()); 
    }
}
