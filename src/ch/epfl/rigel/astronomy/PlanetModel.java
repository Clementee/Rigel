package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.w3c.dom.html.HTMLAreaElement;

import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashSet;
import java.util.Set;

import static ch.epfl.rigel.math.Angle.*;
import static java.lang.Math.*;

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
    URANUS("Uranus", 84.039492, 271.063148, 172.884833, 0.046321,
            19.21814, 0.773059, 73.926961, 65.80, -7.19),
    NEPTUNE("Neptune", 165.84539, 326.895127, 23.07, 0.010483,
            30.1985, 1.7673, 131.879, 62.20, -6.87);

    private String frenchName;
    private final double DAYS_PER_YEAR = 365.242191;
    private double Tp, epsilon, varpi, eccentricity, a, i, omega, theta0, v0;
    private Set<PlanetModel> innerPlanetSet = createInnerPlanet();

    private PlanetModel(String frenchName, double Tp, double epsilon, double varpi, double eccentricity, double a, double i, double omega, double theta0, double v0){
        this.frenchName=frenchName;
        this.Tp=Tp;
        this.epsilon=ofDeg(epsilon);
        this.varpi=ofDeg(varpi);
        this.eccentricity=eccentricity;
        this.a=a;
        this.i=ofDeg(i);
        this.omega=ofDeg(omega);
        this.theta0=theta0*(PI/(180*3600));
        this.v0=v0;
    }
    @Override
    public Planet at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        double M = TAU/DAYS_PER_YEAR*daysSinceJ2010/Tp+epsilon-varpi;
        double v = M + 2* eccentricity*sin(M);
        double r = (a*(1-eccentricity*eccentricity))/(1+eccentricity*cos(v));
        double l = v + varpi;
        double phi = asin(sin(l-omega)*sin(i));
        double rPrim = r*cos(phi);
        double lPrim = atan((sin(l-omega)*cos(i))/cos(l-omega))+omega;

        double MEarth = TAU/DAYS_PER_YEAR * daysSinceJ2010/EARTH.Tp+EARTH.epsilon-EARTH.varpi;
        double vEARTH = MEarth + 2 * EARTH.eccentricity * sin(MEarth);
        double R = (EARTH.a*(1-EARTH.eccentricity*EARTH.eccentricity)/(1+EARTH.eccentricity*cos(vEARTH)));
        double L = vEARTH + EARTH.varpi;

        double lambda=0;
        double k= R * sin(lPrim - L);

        if(innerPlanetSet.contains(this)){
            lambda = (TAU / 2) + atan(((rPrim * sin(L - lPrim)) / (R - (rPrim * cos(L - lPrim)))));
        }else{
            lambda  = lPrim + atan(k / (rPrim - (R * cos(lPrim - L))));
        }
        System.out.println(lambda);
        double beta = atan(((rPrim * tan(phi) * sin(lambda - lPrim)) / k));

        double rho = sqrt((R * R) + (r * r) + (2 * R * r * cos(l - L) * cos(phi)));
        double theta = theta0/rho;
        System.out.println("theta0 = " + theta0);
        double F = (1 + cos(lambda - l)) / 2;
        System.out.println(v0);
        double m = v0 + 5 * (log(r*rho/sqrt(F)))/log(10);

        EclipticCoordinates planetEclipticCoordinates= EclipticCoordinates.of(lambda, beta);
        EquatorialCoordinates equatorialCoordinates = eclipticToEquatorialConversion.apply(planetEclipticCoordinates);
        System.out.println(m);
    return new Planet(frenchName, equatorialCoordinates, (float)theta, (float)m);
    }

    private Set<PlanetModel> createInnerPlanet(){
        Set<PlanetModel> set = new HashSet<>();
        set.add(MERCURY);
        set.add(VENUS);
        return set;
    }
}