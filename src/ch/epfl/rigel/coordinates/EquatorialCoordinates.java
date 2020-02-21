package ch.epfl.rigel.coordinates;

import java.util.Locale;

import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

public final class EquatorialCoordinates extends SphericalCoordinates {

    
    private final static RightOpenInterval RAINTERVAL = RightOpenInterval.of(0, 360);
    private final static ClosedInterval DECINTERVAL =  ClosedInterval.symmetric(180);
    
    /**
     * EquatorialCoordinates package-private constructor
     * @param rightAscension  (double) : gives the longitude/right ascension of the position
     * @param declination     (double) : gives the latitude/declination of the position
     */
    EquatorialCoordinates(double rightAscension, double declination) {
        super(rightAscension, declination);
    }
    
    /**
     * Public method used to call the private constructor while throwing an exception if not working 
     * @param ra    (double) : gives the longitude/right ascension value in rad of the position
     * @param dec   (double) : gives the latitude/declination value in rad of the position
     * 
     * @return      call the constructor with the entered parameters or throw exception
     */
    public static EquatorialCoordinates of(double ra, double dec) {
        
        if(DECINTERVAL.contains(Angle.toDeg(dec)) && RAINTERVAL.contains(Angle.toDeg(ra))) {
            return new EquatorialCoordinates(ra,dec);
        }
        else {
            throw new IllegalArgumentException();
        }
    }
    
    public double ra() {
        return super.lon();
    }
    
    public double raDeg() {
        return super.lonDeg();
    }
    
    public double raHr() {
        double ra = super.lon();
        return Angle.toHr(ra);
    }
    
    public double dec() {
        return super.lat();
    }
    
    public double decDeg() {
        return super.latDeg();
    }
    
    @Override
    public String toString() {
        return String.format(Locale.ROOT,"(ra=%.4fh, dec=%.4f°)",raHr(),decDeg());
    }
}
