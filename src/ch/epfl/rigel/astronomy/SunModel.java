package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

import static ch.epfl.rigel.math.Angle.*;
import static java.lang.Math.*;

/**
 * Model of sun
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public enum SunModel implements CelestialObjectModel<Sun> {
    
    SUN;

    private final static double LONSUN2010 = Angle.ofDeg(279.557208);
    private final static double LONSUNPERIGEE = Angle.ofDeg(283.1124);
    private final static double J2010SunEarthExcentricity = 0.016705;
    private final static double THETA0 = ofDeg(0.533128);

    /**
     * SunModel method at, creating a sun and returning it
     *
     * @param daysSinceJ2010 (double) : gives the number of days after J2010
     * @param eclipticToEquatorialConversion   (EclipticToEquatorialConversion) : gives the equatorial coordinates given by the conversion from ecliptic
     * @return sunAt    (Sun) : returns the sun
     */
    @Override
    public Sun at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        double M0 = TAU/365.242191 * daysSinceJ2010 +LONSUN2010 - LONSUNPERIGEE;
        double v0 = M0 + 2 * J2010SunEarthExcentricity *sin(M0);
        double lambda0 = v0 + LONSUNPERIGEE;
        double phi0 = 0;
        double sunAngularSize = THETA0 * ((1 + J2010SunEarthExcentricity * cos(v0)) / (1 - J2010SunEarthExcentricity));
        EclipticCoordinates sunEclipticCoordinates = EclipticCoordinates.of(Angle.normalizePositive(lambda0), Angle.normalizePositive(phi0));
        EquatorialCoordinates sunEquatorialCoordinates = eclipticToEquatorialConversion.apply(sunEclipticCoordinates);
        return new Sun(sunEclipticCoordinates, sunEquatorialCoordinates, (float) sunAngularSize, (float) M0);
    }
}
