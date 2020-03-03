package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.SiderealTime;
import java.time.ZonedDateTime;
import java.util.function.Function;


/**
 * A conversion from an equatorial to a horizontal coordinate
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class EquatorialToHorizontalConversion implements Function<EquatorialCoordinates, HorizontalCoordinates> {

    private double latObserver;
    private double siderealTime;

    /**
     * EquatorialToHorizontalConversion public constructor, initializing few values
     * @param when  (ZonedDateTime) : gives the zoned date time used to settle the conversion
     * @param where (GeographicCoordinates) : gives the localisation of the observer
     */
    public EquatorialToHorizontalConversion (ZonedDateTime when, GeographicCoordinates where) {
        latObserver = where.lat();
        double lonObserver = where.lon();
        siderealTime = SiderealTime.greenwich(when)+lonObserver;
    }

    /**
     * Method calculating the values to return the horizontal coordinates related to the equatorial entered in parameters
     * @param equ  (EquatorialCoordinates) : gives the equatorial coordinates that we search to convert
     * @return hor (HorizontalCoordinates) : return the horizontal coordinates related to equ
     */
    @Override
    public HorizontalCoordinates apply(EquatorialCoordinates equ) {

        double sinDelta = Math.sin(equ.lat());
        double sinLat = Math.sin(latObserver);
        double cosDelta = Math.cos(equ.lat());
        double cosLat = Math.cos(latObserver);
        double hourAngle = siderealTime-equ.lat();
        double heightHoriz = Math.asin(sinDelta*sinLat+cosDelta*cosLat*Math.cos(hourAngle));
        System.out.println(heightHoriz);
        final double azimuthHoriz = Math.atan2(-cosDelta*cosLat*Math.sin(hourAngle),sinDelta-sinLat*Math.sin(heightHoriz));
        System.out.println(azimuthHoriz);
        return HorizontalCoordinates.of(azimuthHoriz,heightHoriz);
    }


    @Override
    public final boolean equals(Object object){ throw new UnsupportedOperationException();}

    @Override
    public final int hashCode(){ throw new UnsupportedOperationException();}
}
