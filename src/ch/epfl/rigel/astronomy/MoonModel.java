package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import static ch.epfl.rigel.astronomy.SunModel.SUN;
import static ch.epfl.rigel.math.Angle.normalizePositive;
import static ch.epfl.rigel.math.Angle.ofDeg;
import static java.lang.Math.*;


/**
 * A model of moon
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public enum MoonModel implements CelestialObjectModel<Moon> {

    MOON;

    private final static double l0 = ofDeg(91.929336);
    private final static double P0 = ofDeg(130.143076);
    private final static double N0 = ofDeg(291.682547);
    private final static double i = ofDeg(5.145396);
    private final static double e = 0.0549;
    private final static double THETA0 = ofDeg(0.5181);

    private static double SUN_M;
    private static double SUN_LAMBDA;

    private final static double lCste = ofDeg(13.1763966);
    private final static double MmCste = ofDeg(0.1114041);
    private final static double EvCste = ofDeg(1.2739);
    private final static double AeCste = ofDeg(0.1858);
    private final static double A3Cste = ofDeg(0.37);
    private final static double EcCste = ofDeg(6.2886);
    private final static double A4Cste = ofDeg(0.214);
    private final static double VCste = ofDeg(0.6583);

    private final static double NCste = ofDeg(0.0529539);
    private final static double NPrimCste = ofDeg(0.16);

    private static double lPrimPrim;
    private static double MmPrim;
    private static double Ec;


    /**
     * MoonModel method at, creating a moon and returning it
     *
     * @param daysSinceJ2010                 (double) : gives the number of days after J2010
     * @param eclipticToEquatorialConversion (EclipticToEquatorialConversion) : gives the equatorial coordinates given by the conversion from ecliptic
     * @return (Moon) : returns the moon
     */
    @Override
    public Moon at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {

        SUN_LAMBDA = SUN
                .at(daysSinceJ2010, eclipticToEquatorialConversion)
                .eclipticPos()
                .lon();

        SUN_M = SUN
                .at(daysSinceJ2010, eclipticToEquatorialConversion)
                .meanAnomaly();

        lPrimPrim = MoonLongitudinalOrbit(daysSinceJ2010);

        EclipticCoordinates moonEclipticCoords = MoonEclipticCoordinates(daysSinceJ2010);
        EquatorialCoordinates moonEquatorialPos = eclipticToEquatorialConversion.apply(moonEclipticCoords);

        float F = (float) MoonPhase();
        float angularSize = (float) MoonAngularSize();

        return new Moon(moonEquatorialPos, angularSize, 0, F);
    }

    /**
     * MoonModel method MoonLongitudinalOrbit, returning the longitudinal orbit
     *
     * @param daysSinceJ2010 (double) : gives the number of days after J2010
     * @return moonOrbit     (double) : returns the orbit
     */
    private double MoonLongitudinalOrbit(double daysSinceJ2010) {

        double l = lCste * daysSinceJ2010 + l0;
        double Mm = l - MmCste * daysSinceJ2010 - P0;
        double Ev = EvCste * sin(2 * (l - SUN_LAMBDA) - Mm);
        double Ae = AeCste * sin(SUN_M);
        double A3 = A3Cste * sin(SUN_M);
        MmPrim = Mm + Ev - Ae - A3;
        Ec = EcCste * sin(MmPrim);
        double A4 = A4Cste * sin(2 * MmPrim);
        double lPrim = l + Ev + Ec - Ae + A4;
        double V = VCste * sin(2 * (lPrim - SUN_LAMBDA));
        return lPrim + V;
    }

    /**
     * MoonModel method moonEclipticCoordinates, returning the ecliptic coordinates of the moon
     *
     * @param daysSinceJ2010 (double) : gives the number of days after J2010
     * @return moonEcliptic    (EclipticCoordinates) : returns the ecliptic coordinates
     */
    private EclipticCoordinates MoonEclipticCoordinates(double daysSinceJ2010) {

        double N = N0 - NCste * daysSinceJ2010;
        double NPrim = N - NPrimCste * sin(SUN_M);
        double LAMBDAm = atan2((sin(lPrimPrim - NPrim) * cos(i)), (cos(lPrimPrim - NPrim))) + NPrim;
        double BETAm = asin(sin(lPrimPrim - NPrim) * sin(i));

        return EclipticCoordinates
                .of(normalizePositive(LAMBDAm), BETAm);
    }

    /**
     * MoonModel method MoonPhase, returning the phase
     *
     * @return moonPhase    (double) : returns the phase
     */
    private double MoonPhase() {
        return (1 - cos(lPrimPrim - SUN_LAMBDA)) / 2;
    }

    /**
     * MoonModel method MoonAngularSize, returning the angular size
     *
     * @return moonAngularSize (double) : returns the angular size
     */
    private double MoonAngularSize() {

        double rho = (1 - e * e) / (1 + e * cos(MmPrim + Ec));

        return THETA0 / rho;
    }
}
