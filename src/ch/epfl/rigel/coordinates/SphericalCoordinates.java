package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;

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
    
    double lon() {
        return longitudeCoords;
    }
    
    double lonDeg() {
        return Angle.toDeg(longitudeCoords);
    }
    
    double lat() {
        return latitudeCoords;
    }
    
    double latDeg() {
        System.out.println(latitudeCoords);
        return Angle.toDeg(latitudeCoords);
    }
    
    //Method overridden in order to throw an exception when called
    @Override
    public final int hashCode() {
        throw new UnsupportedOperationException();
    }
    
    //Method overridden in order to throw an exception when called
    @Override
    public final boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }

}
