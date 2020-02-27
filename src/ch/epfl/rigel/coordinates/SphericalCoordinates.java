package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;

/**
 * Spherical coordinates
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
abstract class SphericalCoordinates {
    
    private double longitudeCoords;
    private double latitudeCoords;
    
    /**
     * SphericalCoordinates package-private constructor
     * @param longitude  (double) : gives the longitude of the position
     * @param latitude   (double) : gives the latitude of the position
     */
    SphericalCoordinates(double longitude, double latitude) {
        longitudeCoords = longitude;
        latitudeCoords = latitude;
    }
    
    /**
     * Public method used to return the value of the longitude in radians
     * 
     * @return :    return value of longitude in radians
     */
    double lon() { return longitudeCoords;}
    
    /**
     * Public method used to return the value of the longitude in degrees
     * 
     * @return :    return value of longitude in degrees
     */
    double lonDeg() { return Angle.toDeg(longitudeCoords);}
    
    /**
     * Public method used to return the value of the latitude in radians
     * 
     * @return :    return value of latitude in radians
     */
    double lat() { return latitudeCoords;}
    
    /**
     * Public method used to return the value of the latitude in degrees
     * 
     * @return :    return value of latitude in degrees
     */
    double latDeg() { return Angle.toDeg(latitudeCoords);}
    
    //Method overridden in order to throw an exception when called
    @Override
    public final int hashCode() { throw new UnsupportedOperationException();}
    
    //Method overridden in order to throw an exception when called
    @Override
    public final boolean equals(Object obj) { throw new UnsupportedOperationException();}

}
