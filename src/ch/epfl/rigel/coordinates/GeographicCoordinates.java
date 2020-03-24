package ch.epfl.rigel.coordinates;

import java.util.Locale;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

/**
 * A geographic coordinate
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class GeographicCoordinates extends SphericalCoordinates {
    
    private final static RightOpenInterval LONINTERVAL = RightOpenInterval.symmetric(Angle.TAU);
    private final static ClosedInterval LATINTERVAL = ClosedInterval.symmetric(Angle.TAU/2.0);
    
    /**
     * GeographicCoordinates package-private constructor
     * @param longitude  (double) : gives the longitude of the position
     * @param latitude   (double) : gives the latitude of the position
     */
     GeographicCoordinates(double longitude, double latitude) { super(longitude,latitude);}
        
     /**
      * Public method used to call the private constructor while throwing an exception if not working 
      * @param lonDeg    (double) : gives the longitude value in deg of the position
      * @param latDeg    (double) : gives the latitude value in deg of the position
      * 
      * @return      call the constructor with the entered parameters or throw exception
      */
    public static GeographicCoordinates ofDeg(double lonDeg, double latDeg) {
                if(isValidLonDeg(lonDeg)&&isValidLatDeg(latDeg)) {
            return new GeographicCoordinates(Angle.ofDeg(lonDeg),Angle.ofDeg(latDeg));
            }
        else {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Method validating if the entered value is valid
     * @param lonDeg  (double) : gives the longitude of the position in degrees
     */
    public static boolean isValidLonDeg(double lonDeg) { return LONINTERVAL.contains(Angle.ofDeg(lonDeg));}
        
    /**
     * Method validating if the entered value is valid
     * @param latDeg  (double) : gives the latitude of the position in degrees
     */
    public static boolean isValidLatDeg(double latDeg) { return LATINTERVAL.contains(Angle.ofDeg(latDeg));}
    
    @Override
    public double lon() { return super.lon();}
    
    @Override
    public double lonDeg() { return super.lonDeg();}
    
    @Override
    public double lat() {
        return super.lat();
    }
    
    @Override
    public double latDeg() { return super.latDeg();}
    
    @Override
    public String toString() { return String.format(Locale.ROOT,"(lon=%.4f°, lat=%.4f°)",lonDeg(),latDeg());}
}
