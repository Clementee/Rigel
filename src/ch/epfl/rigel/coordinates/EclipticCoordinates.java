package ch.epfl.rigel.coordinates;

import java.util.Locale;

import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

public final class EclipticCoordinates extends SphericalCoordinates {
   
    private final static RightOpenInterval LONINTERVAL = RightOpenInterval.symmetric(360);
    private final static ClosedInterval LATINTERVAL = ClosedInterval.symmetric(180);
    
    /**
     * EclipticCoordinates package-private constructor
     * @param longitude  (double) : gives the longitude of the position
     * @param latitude   (double) : gives the latitude of the position
     */
    EclipticCoordinates(double longitude, double latitude) {
        super(longitude, latitude);
    }
    
    /**
     * Public method used to call the private constructor while throwing an exception if not working 
     * @param lon    (double) : gives the longitudinal value in rad of the position
     * @param lat    (double) : gives the latitude value in rad of the position
     * 
     * @return      call the constructor with the entered parameters or throw exception
     */
    public static EclipticCoordinates of(double lon, double lat) {
        
        if(LONINTERVAL.contains(Angle.toDeg(lon)) && LATINTERVAL.contains(Angle.toDeg(lat))) {
            return new EclipticCoordinates(lon,lat);
        }
        else {
            throw new IllegalArgumentException();
        }
    }
    
    @Override
    public double lon() {
        return super.lon();
    }
    
    @Override
    public double lonDeg() {
        return super.lonDeg();
    }
    
    @Override
    public double lat() {
        return super.lat();
    }
    
    @Override
    public double latDeg() {
        return super.latDeg();
    }
    
    @Override
    public String toString() {
        return String.format(Locale.ROOT,"(λ=%.4f°, β=%.4f°)",lonDeg(),latDeg());
    }

}
