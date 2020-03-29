package ch.epfl.rigel.coordinates;

import java.util.Locale;

import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import static ch.epfl.rigel.Preconditions.checkInInterval;

/**
 * An equatorial coordinate
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class EquatorialCoordinates extends SphericalCoordinates {

    private final static RightOpenInterval RAINTERVAL = RightOpenInterval.of(0, Angle.TAU);
    private final static ClosedInterval DECINTERVAL =  ClosedInterval.symmetric(Math.PI);

    /**
     * EquatorialCoordinates package-private constructor
     * @param rightAscension  (double) : gives the longitude/right ascension of the position
     * @param declination     (double) : gives the latitude/declination of the position
     */
    EquatorialCoordinates(double rightAscension, double declination) { super(rightAscension, declination);}

    /**
     * Public method used to call the private constructor while throwing an exception if not working
     * @param ra    (double) : gives the longitude/right ascension value in rad of the position
     * @param dec   (double) : gives the latitude/declination value in rad of the position
     *
     * @return      call the constructor with the entered parameters or throw exception
     */
    public static EquatorialCoordinates of(double ra, double dec) {
        return new EquatorialCoordinates(checkInInterval(RAINTERVAL,ra),checkInInterval(DECINTERVAL,dec));
    }

    public double ra() { return super.lon();}

    public double raDeg() { return super.lonDeg();}

    public double raHr() {
        double ra = super.lon();
        return Angle.toHr(ra);
    }

    public double dec() { return super.lat();}

    public double decDeg() { return super.latDeg();}

    @Override
    public String toString() { return String.format(Locale.ROOT,"(ra=%.4fh, dec=%.4f°)",raHr(),decDeg()); }
}
