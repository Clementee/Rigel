package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.Epoch;
import ch.epfl.rigel.math.Angle;

import java.time.ZonedDateTime;
import java.util.function.Function;

public final class EclipticToEquatorialConversion implements Function<EclipticCoordinates,EquatorialCoordinates> {

    private double cosObliquity;
    
    public EclipticToEquatorialConversion(ZonedDateTime when){
        double nbJulianCycles = Epoch.julianCenturiesUntil(when);
        double obliquityEcliptic = ((0.00181 * Math.pow(nbJulianCycles, 3)) - (0.0006 * Math.pow(nbJulianCycles, 2)) - (46.815 * nbJulianCycles)) + (Angle.toHr(Angle.ofDeg(23)) * 26 * 21.45);
        cosObliquity = Math.cos(obliquityEcliptic);
    }

    @Override
    public EquatorialCoordinates apply(EclipticCoordinates eclipticCoordinates){

        double latitudeEcliptic = eclipticCoordinates.lat();
        double longitudeEcliptic = eclipticCoordinates.lon();
        double equatorialRA = Math.atan2((Math.sin(longitudeEcliptic) * cosObliquity - Math.tan(latitudeEcliptic) * cosObliquity), (longitudeEcliptic));
        double equatorialDec = Math.asin((Math.sin(latitudeEcliptic)) * cosObliquity + Math.cos(latitudeEcliptic) * cosObliquity * Math.sin(longitudeEcliptic));
        return new EquatorialCoordinates(equatorialRA, equatorialDec);
    }

    @Override
    public final int hashCode(){
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean equals(Object object){
        throw new UnsupportedOperationException();
    }
}
