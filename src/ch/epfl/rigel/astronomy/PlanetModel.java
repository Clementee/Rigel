package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static ch.epfl.rigel.math.Angle.TAU;
import static ch.epfl.rigel.math.Angle.ofDeg;
import static java.lang.Math.*;

/**
 * Model of planet
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
    URANUS("Uranus", 84.039492, 271.063148, 172.884833, 0.046321,
            19.21814, 0.773059, 73.926961, 65.80, -7.19),
    NEPTUNE("Neptune", 165.84539, 326.895127, 23.07, 0.010483,
            30.1985, 1.7673, 131.879, 62.20, -6.87);

    public static List<PlanetModel> ALL = List.of(values());
    private static Set<PlanetModel> outerPlanet = createOuterPlanetSet();
    private static Set<PlanetModel> innerPlanet = createInnerPlanetSet();

    private String frenchName;
    private double Tp, epsilon, varpi,eccentricity, a, i, omega, theta0, v0;

    /**
     * PlanetModel private constructor initializing a planet with parameters
     * @param frenchNameC     (String) : gives the french name of the planet
     * @param TpC             (double) : gives the tropical year of the planet
     * @param epsilonC        (double) : gives the longitude at J2010
     * @param varpiC          (double) : gives the longitude at the perigee
     * @param eccentricityC   (double) : gives the eccentricity of the orbit of the planet
     * @param aC              (double) : gives the semi big axis of the orbit of the planet
     * @param iC              (double) : gives the inclination of the orbit at the ecliptic
     * @param omegaC          (double) : gives the longitude of the ascending node
     * @param theta0C         (double) : gives the angular size of the planet
     * @param v0C             (double) : gives the magnitude of the planet
     */
    private PlanetModel(String frenchNameC, double TpC, double epsilonC, double varpiC, double eccentricityC, double aC , double iC, double omegaC, double theta0C, double v0C){
        frenchName = frenchNameC;
        Tp = TpC;
        epsilon = ofDeg(epsilonC);
        varpi = ofDeg(varpiC);
        eccentricity = eccentricityC;
        a = aC;
        i = ofDeg(iC);
        omega = ofDeg(omegaC);
        theta0 = ofDeg(theta0C);
        v0 = v0C;
    }

    @Override
    public Planet at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        double M = TAU/365.242191 * daysSinceJ2010 / Tp + epsilon - varpi;
        double v = M  + 2 * eccentricity * sin(M);
        double r = (a*(1-eccentricity*eccentricity))/(1+eccentricity*Math.cos(v));
        double l = v  + varpi;
        double phi = Math.asin(sin(l - omega)* sin(i));
        double rPrim = r * cos(phi);
        double lPrim = Math.atan2(sin(l-omega)*cos(i), cos(l-omega))+omega;
        double MEarth = TAU/365.242191 * daysSinceJ2010 / EARTH.Tp + EARTH.epsilon - EARTH.varpi;
        double vEarth = M  + 2 * EARTH.eccentricity * sin(MEarth);
        double rEarth = (EARTH.a*(1-EARTH.eccentricity*EARTH.eccentricity))/(1+EARTH.eccentricity*Math.cos(vEarth));
        double lEarth = vEarth + EARTH.varpi;
        double lambda;
        double k = rEarth*sin(lPrim-lEarth);

        if(innerPlanet.contains(this)){
            lambda = PI + lEarth + Math.atan2(rPrim*sin(lEarth-l), rEarth - rPrim*cos(lEarth-l));
        }
        else{
            lambda = lPrim+Math.atan2(k, rPrim-rEarth*cos(lPrim-lEarth));
        }
        double beta = Math.atan((rPrim * tan(phi)*sin(lambda-lPrim))/k);
        double rho = sqrt(rEarth*rEarth+r*r-2*rEarth*r*cos(l-lEarth)*cos(phi));
        double theta = theta0/rho;

        double F = (1+cos(lambda-l))/2;
        double m = v0+5 * (Math.log(r * rho /sqrt(F)))/log(10);

        EclipticCoordinates eclipticCoords = EclipticCoordinates.of(lambda, beta);
        EquatorialCoordinates equatorialCoords = eclipticToEquatorialConversion.apply(eclipticCoords);
        return new Planet(this.frenchName, equatorialCoords,(float) theta, (float) m);
    }


    /**
     * Method createOuterPlanetSet returning the set of planets beyond Earth
     * @return set (Set<PlanetModel>) : return the set of planets beyond Earth
     */
    private static Set<PlanetModel> createOuterPlanetSet() {
        Set<PlanetModel> set = new HashSet<>();
        set.add(MARS);
        set.add(JUPITER);
        set.add(SATURN);
        set.add(URANUS);
        set.add(NEPTUNE);
        return set;
    }


    /**
     * Method createOuterPlanetSet returning the set of planets closer from the Sun
     * @return set (Set<PlanetModel>) : return the set of planets closer from the sun
     */
    private static Set<PlanetModel> createInnerPlanetSet(){
        Set<PlanetModel> set = new HashSet<>();
        set.add(MERCURY);
        set.add(VENUS);
        return set;
    }
}