package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.SiderealTime;

import java.time.ZonedDateTime;
import java.util.function.Function;

public final class EquatorialToHorizontalConversion implements Function<EquatorialCoordinates, HorizontalCoordinates> {

    
    private double latObserver;
    private double lonObserver;
    private double siderealTime;

    public EquatorialToHorizontalConversion (ZonedDateTime when, GeographicCoordinates where) {
        latObserver = where.lat();
        lonObserver = where.lon();
        siderealTime = SiderealTime.greenwich(when);
    }

    @Override
    public HorizontalCoordinates apply(EquatorialCoordinates equ) {

        final double sinDelta = Math.sin(equ.lat());
        final double sinLat = Math.sin(latObserver);
        final double cosDelta = Math.cos(equ.lat());
        final double cosLat = Math.cos(latObserver);
        final double hourAngle = siderealTime-lonObserver;


        final double heightHoriz = Math.asin(sinDelta*Math.sin(sinLat)+cosDelta*cosLat*Math.cos(hourAngle));
        final double azimuthHoriz = Math.atan2(-cosDelta*cosLat*Math.sin(hourAngle),sinDelta-sinLat*Math.sin(heightHoriz));

        return HorizontalCoordinates.of(azimuthHoriz,heightHoriz);
    }

    @Override
    public final boolean equals(Object object){
        throw new UnsupportedOperationException();
    }

    @Override
    public final int hashCode(){
        throw new UnsupportedOperationException();
    }
}
