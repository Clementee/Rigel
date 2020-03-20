package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import static ch.epfl.rigel.math.Angle.TAU;
import static ch.epfl.rigel.math.Angle.ofDeg;
import static java.lang.Math.*;

/**
 * Model of sun
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public enum SunModel implements CelestialObjectModel<Sun> {
    SUN;

    private final static EclipticCoordinates J2010SunCoordinates = EclipticCoordinates.of(ofDeg(279.557208), ofDeg(283.112438));
    private final static double J2010SunEarthExcentricity = 0.016705;
    private final static double THETA0 = ofDeg(0.533128);
    private static double M0;
    private static double lambda0;

    @Override
    public Sun at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        M0 = TAU/365.242191 * daysSinceJ2010 + J2010SunCoordinates.lon() - J2010SunCoordinates.lat();
        double v0 = M0 + 2 * J2010SunEarthExcentricity *sin(M0);
        lambda0 = v0 + J2010SunCoordinates.lat();
        double phi0 = 0;
        double sunAngularSize = THETA0 * (1 + J2010SunEarthExcentricity * cos(v0)) / (1 - J2010SunEarthExcentricity);
        EclipticCoordinates sunEclipticCoordinates = EclipticCoordinates.of(lambda0, phi0);
        EquatorialCoordinates sunEquatorialCoordinates = eclipticToEquatorialConversion.apply(sunEclipticCoordinates);
        return new Sun(sunEclipticCoordinates, sunEquatorialCoordinates, (float) sunAngularSize, (float) M0);
    }
}
