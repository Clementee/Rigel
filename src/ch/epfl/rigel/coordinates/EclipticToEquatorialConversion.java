package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.Epoch;
import ch.epfl.rigel.math.Angle;

import java.time.ZonedDateTime;
import java.util.function.Function;

public final class EclipticToEquatorialConversion implements Function<EclipticCoordinates,EquatorialCoordinates> {

    private double equatorialRA;
    private double equatorialDec;
    private double obliquityEcliptic;
    private double nbJulianCycles;


    public EclipticToEquatorialConversion(ZonedDateTime when){
        nbJulianCycles = Epoch.julianCenturiesUntil(when);
        obliquityEcliptic = 0.00181*Math.pow(nbJulianCycles,3)-0.0006*Math.pow(nbJulianCycles,2)-46.815*nbJulianCycles+Angle.ofHr(23)*26*21.45;
    }


    // A modifier
    @Override
    public EquatorialCoordinates apply(EclipticCoordinates eclipticCoordinates){
        equatorialRA = Math.atan2((Math.sin(eclipticCoordinates.lon())*Math.cos(obliquityEcliptic)-Math.tan(eclipticCoordinates.lat())*Math.sin(obliquityEcliptic)),(Math.cos(eclipticCoordinates.lon())));
        equatorialDec = Math.asin((Math.sin(eclipticCoordinates.lat()))*Math.cos(obliquityEcliptic)+Math.cos(eclipticCoordinates.lat())*Math.sin(obliquityEcliptic)*Math.sin(eclipticCoordinates.lon()));
        return new EquatorialCoordinates(equatorialRA,equatorialDec);
    }

    @Override
    public final int hashCode(){
        throw new UnsupportedOperationException();
    }

    public final boolean equals(){
        throw new UnsupportedOperationException();
    }
}
