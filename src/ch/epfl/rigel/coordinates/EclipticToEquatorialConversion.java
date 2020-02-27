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

    private double cosObliquity;

    public EclipticToEquatorialConversion(ZonedDateTime when){
        double nbJulianCycles = J2000.julianCenturiesUntil(when);
        double obliquityEcliptic = ((0.00181 * Math.pow(nbJulianCycles, 3)) - (0.0006 * Math.pow(nbJulianCycles, 2)) - (46.815 * nbJulianCycles)) + (Angle.toHr(Angle.ofDeg(23)) * 26 * 21.45);
        cosObliquity = Math.cos(obliquityEcliptic);
    }

    @Override
    public EquatorialCoordinates apply(EclipticCoordinates ecl){

        double latitudeEcliptic = ecl.lat();
        double longitudeEcliptic = ecl.lon();
        double equatorialRA = Math.atan2((Math.sin(longitudeEcliptic)*cosObliquity - Math.tan(latitudeEcliptic) * cosObliquity), (longitudeEcliptic));
        double equatorialDec = Math.asin((Math.sin(latitudeEcliptic))*cosObliquity + Math.cos(latitudeEcliptic) * cosObliquity * Math.sin(longitudeEcliptic));
        return new EquatorialCoordinates(equatorialRA, equatorialDec);
    }

    @Override
    public final int hashCode(){ throw new UnsupportedOperationException();}

    @Override
    public final boolean equals(Object object){ throw new UnsupportedOperationException();}
}
