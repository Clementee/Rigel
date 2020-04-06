package ch.epfl.rigel.coordinates;

import java.util.Locale;
import java.util.function.Function;

import static ch.epfl.rigel.math.Angle.normalizePositive;
import static java.lang.Math.*;

public final class StereographicProjection implements Function<HorizontalCoordinates, CartesianCoordinates> {

    private static double centerLambda;
    private static double radius;
    private static double cosPhy;
    private static double sinPhy;
    
    /**
     * StereographicProjection public constructor initializing some values
     *
     * @param center (EquatorialCoordinates) : gives the equatorial coordinates of the position studied
     */
    public StereographicProjection(HorizontalCoordinates center){
        
        centerLambda = center.lon();
        
        double centerPhy = center.lat();
        
        cosPhy = Math.cos(centerPhy);
        sinPhy = Math.sin(centerPhy);
    }

    /**
     * StereographicPosition public method returning the cartesian coordinates of the center in the projection passing by the point hor
     * 
     * @param hor  (HorizontalCoordinates) : gives the horizontal coordinates of the point we search to convert
     * @return (CartesianCoordinates) : return the coordinates of the center of the projection passing by hor
     */
    public CartesianCoordinates circleCenterForParallel(HorizontalCoordinates hor){
        
        radius = cosPhy / (Math.sin(hor.alt()) + sinPhy);
        
        return CartesianCoordinates.of(0,radius);
    }

    /**
     * StereographicPosition public method returning the radius of the circle of the parallel passing by the point hor
     * 
     * @param parallel  (HorizontalCoordinates) : gives the horizontal coordinates of the parallel we search to convert
     * @return radius (CartesianCoordinates) : return the value of the radius of the circle
     */
    public double circleRadiusForParallel(HorizontalCoordinates parallel){
        
        radius = Math.cos(parallel.alt()) / (Math.sin(parallel.alt()) + sinPhy);
        return radius;
    }

    /**
     * StereographicPosition public method returning the diameter of a sphere centered on the center
     * @param rad  (double) : gives the angular size of the sphere we study
     *
     * @return (double) : return the diameter of the sphere
     */
    public double applyToAngle(double rad){
        return 2 * Math.tan(rad / 4);
    }

    /**
     * StereographicPosition public method returning the cartesian coordinates of the projection
     * 
     * @param azAlt  (HorizontalCoordinates) : gives the horizontal coordinates we search to convert
     * @return (CartesianCoordinates) : return the coordinates of the projection of azAlt
     */
    @Override
    public CartesianCoordinates apply(HorizontalCoordinates azAlt) {

        final double horAzimuth = azAlt.az();
        final double horAltitude = azAlt.alt();
        final double cosLat = Math.cos(horAltitude);
        final double sinLat = Math.sin(horAltitude);
        final double lambdaDelta = horAzimuth-centerLambda;
        final double cosLambdaDelta = Math.cos(lambdaDelta);
        
        final double d = 1 / (1 + sinPhy * sinLat + cosLat * cosPhy * cosLambdaDelta);

        double y = d * (sinLat * cosPhy - cosLat * sinPhy * cosLambdaDelta);
        double x = d * cosLat * Math.sin(lambdaDelta);
        
        return CartesianCoordinates.of(x,y);
    }

    /**
     * StereographicPosition public method returning the horizontal coordinates linked to the cartesian coordinates in parameter
     * 
     * @param xy  (CartesianCoordinates) : gives the cartesian coordinates we search to convert
     * @return (HorizontalCoordinates) : return the horizontal coordinates of the point xy
     */
    public HorizontalCoordinates inverseApply(CartesianCoordinates xy){
        
        double x = xy.x();
        double y = xy.y();

        double rho = sqrt(x * x + y * y);
        double sinC = (2 * rho) / (rho * rho + 1);
        double cosC = (1 - rho * rho) / (rho * rho + 1);

        double lambda = atan2(x * sinC , rho * cosPhy * cosC - y * sinPhy * sinC) + centerLambda;
        double phi = asin(cosC * sinPhy + (y * sinC * cosPhy) / rho);

        return HorizontalCoordinates.of(normalizePositive(lambda), phi);
    }

    /**
     * StereographicProjection overrode method throwing UOE
     *
     * @throws UnsupportedOperationException : throws this exception when the method is called
     */
    @Override
    public final int hashCode(){
        throw new UnsupportedOperationException();
    }

    /**
     * StereographicProjection overrode method throwing UOE
     * 
     * @param obj (Object) : gives the object to compare
     * @throws UnsupportedOperationException : throws this exception when the method is called
     */
    @Override
    public final boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }

    /**
     * StereographicProjection overrode method returning a string with the coordinates of the center
     *
     * @return (String) : the string of coordinates for the center of the projection
     */
    @Override
    public final String toString(){
        return String.format(Locale.ROOT,"The stereographic projection is centered on %f", centerLambda);
    }
}
