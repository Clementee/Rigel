package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import java.time.ZonedDateTime;
import java.util.function.Function;
import static ch.epfl.rigel.astronomy.Epoch.J2000;

/**
 * A conversion from an ecliptic to an equatorial coordinate
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class EclipticToEquatorialConversion implements Function<EclipticCoordinates,EquatorialCoordinates> {

    private static double cosObliquity;
    private static double sinObliquity;
    private final static Polynomial epsilonPoly = Polynomial.of(0.00181, -0.0006,-46.815);

    /**
     * EclipticToEquatorialConversion public constructor, initializing few values
     * @param when  (ZonedDateTime) : gives the zoned date time used to settle the conversion
     */
    public EclipticToEquatorialConversion(ZonedDateTime when){
        double nbJulianCycles = J2000.julianCenturiesUntil(when);
        double obliquityEcliptic = epsilonPoly.at(nbJulianCycles)+Angle.toHr(Angle.ofDeg(23))*3600*26*60*21.45;
        cosObliquity = Math.cos(obliquityEcliptic);
    }

    /**
     * Method calculating the values to return the equatorial coordinates related to the ecliptic entered in parameters
     * @param ecl  (EclipticCoordinates) : gives the ecliptic coordinates that we search to convert
     * @return equ (EquatorialCoordinates) : return the equatorial coordinates related to ecl
     */
    @Override
    public EquatorialCoordinates apply(EclipticCoordinates ecl){

        double latitudeEcliptic = ecl.lat();
        double longitudeEcliptic = ecl.lon();
        double equatorialRA = Math.atan2((Math.sin(longitudeEcliptic)*cosObliquity - Math.tan(latitudeEcliptic) * sinObliquity), longitudeEcliptic);
        double equatorialDec = Math.asin(((Math.sin(latitudeEcliptic)) * cosObliquity) + (Math.cos(latitudeEcliptic) * sinObliquity * Math.sin(longitudeEcliptic)));
        return new EquatorialCoordinates(equatorialRA, equatorialDec);
    }

    @Override
    public final int hashCode(){ throw new UnsupportedOperationException();}

    @Override
    public final boolean equals(Object object){ throw new UnsupportedOperationException();}
    
}
