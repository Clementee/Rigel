package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

import java.util.*;

import static ch.epfl.rigel.math.Angle.*;
import static java.lang.Math.*;

/**
 * A model of Planet
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public enum PlanetModel implements CelestialObjectModel<Planet> {

    MERCURY("Mercure", 0.24085, 75.5671, 77.612, 0.205627,
            0.387098, 7.0051, 48.449, 6.74, -0.42),
    VENUS("Vénus", 0.615207, 272.30044, 131.54, 0.006812,
            0.723329, 3.3947, 76.769, 16.92, -4.40),
    EARTH("Terre", 0.999996, 99.556772, 103.2055, 0.016671,
            0.999985, 0, 0, 0, 0),
    MARS("Mars", 1.880765, 109.09646, 336.217, 0.093348,
            1.523689, 1.8497, 49.632, 9.36, -1.52),
    JUPITER("Jupiter", 11.857911, 337.917132, 14.6633, 0.048907,
            5.20278, 1.3035, 100.595, 196.74, -9.40),
    SATURN("Saturne", 29.310579, 172.398316, 89.567, 0.053853,
            9.51134, 2.4873, 113.752, 165.60, -8.88),
    URANUS("Uranus", 84.039492, 356.135400, 172.884833, 0.046321,
            19.21814, 0.773059, 73.926961, 65.80, -7.19),
    NEPTUNE("Neptune", 165.84539, 326.895127, 23.07, 0.010483,
            30.1985, 1.7673, 131.879, 62.20, -6.87);

    private final String frenchName;

    private final static double DAYS_PER_YEAR = 365.242191;
    private final double Tp, epsilon, varpi, eccentricity, a, i, omega, theta0, v0;

    public static List<PlanetModel> ALL = List.copyOf(Arrays.asList(PlanetModel.values()));

    /**
     * PlanetModel package-private constructor initializing the model of planet with its characteristics
     *
     * @param frenchName   (String) : returns the French name of the planet
     * @param Tp           (double) : returns the tropical year of the planet
     * @param epsilon      (double) : returns the longitude at J2010 in radians
     * @param varpi        (double) : returns the longitude at the perigee in radians
     * @param eccentricity (double) : returns the eccentricity of the orbit of the planet
     * @param a            (double) : returns the half big axis of the orbit
     * @param i            (double) : returns the inclination of the orbit at the ecliptic
     * @param omega        (double) : returns the longitude of the ascending node
     * @param theta0       (double) : returns the angular size of the planet
     * @param v0           (double) : returns the magnitude of the planet
     */
    PlanetModel(String frenchName, double Tp, double epsilon, double varpi, double eccentricity, double a, double i, double omega, double theta0, double v0) {

        this.frenchName = frenchName;
        this.Tp = Tp;
        this.epsilon = ofDeg(epsilon);
        this.varpi = ofDeg(varpi);
        this.eccentricity = eccentricity;
        this.a = a;
        this.i = ofDeg(i);
        this.omega = ofDeg(omega);
        this.theta0 = Angle.ofArcsec(theta0);
        this.v0 = v0;
    }

    /**
     * PlanetModel method at, creating a planet and returning it
     *
     * @param daysSinceJ2010                 (double) : gives the number of days after J2010
     * @param eclipticToEquatorialConversion (EclipticToEquatorialConversion) : gives the equatorial coordinates given by the conversion from ecliptic
     * @return (Planet) : returns the planet
     */
    @Override
    public Planet at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {

        double l = l(daysSinceJ2010,this);
        double r = r(daysSinceJ2010,this);

        double phi = asin(sin(l - omega) * sin(i));
        double rPrim = r * cos(phi);
        double lPrim = atan2((sin(l - omega) * cos(i)), cos(l - omega)) + omega;

        double R = r(daysSinceJ2010,EARTH);
        double L = l(daysSinceJ2010,EARTH);

        double lambda;
        double k = R * sin(lPrim - L);

        if (this.a<1) {
            lambda = PI + L + atan(((rPrim * sin(L - lPrim))) / (R - (rPrim * cos(L - lPrim))));
        } else {
            lambda = lPrim + atan(k / (rPrim - (R * cos(lPrim - L))));
        }

        double beta = atan(((rPrim * tan(phi) * sin(lambda - lPrim)) / k));

        double rho = Math.sqrt(Math.pow(R, 2) + Math.pow(r, 2) - 2 * R * r * Math.cos(l - L) * Math.cos(phi));
        double theta = theta0 / rho;
        double F = (1 + cos(lambda - l)) / 2;
        double m = v0 + 5 * Math.log10(r * rho / Math.sqrt(F));

        EclipticCoordinates planetEclipticCoordinates = EclipticCoordinates.of(normalizePositive(lambda), beta);
        EquatorialCoordinates equatorialCoordinates = eclipticToEquatorialConversion.apply(planetEclipticCoordinates);

        return new Planet(frenchName, equatorialCoordinates, (float) theta, (float) m);
    }

    private double l(double daysSinceJ2010, PlanetModel planet){
        double M = (TAU / DAYS_PER_YEAR) * (daysSinceJ2010 / planet.Tp) + planet.epsilon - planet.varpi;
        double v = M + 2 * planet.eccentricity * sin(M);
        return v + planet.varpi;
    }

    private double r(double daysSinceJ2010, PlanetModel planet){
        double M = (TAU / DAYS_PER_YEAR) * (daysSinceJ2010 / planet.Tp) + planet.epsilon - planet.varpi;
        double v = M + 2 * planet.eccentricity * sin(M);
        return (planet.a * (1 - Math.pow(planet.eccentricity, 2))) / (1 + planet.eccentricity * cos(v));
    }
}
