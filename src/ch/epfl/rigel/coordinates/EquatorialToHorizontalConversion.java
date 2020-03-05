package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.SiderealTime;
import ch.epfl.rigel.math.Angle;

import java.time.ZonedDateTime;
import java.util.function.Function;


/**
 * A conversion from an equatorial to a horizontal coordinate
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class EquatorialToHorizontalConversion implements Function<EquatorialCoordinates, HorizontalCoordinates> {

    private final double siderealTime;
    private final double cosPhy;
    private final double sinPhy;

    /**
     * EquatorialToHorizontalConversion public constructor, initializing few values
     * @param when  (ZonedDateTime) : gives the zoned date time used to settle the conversion
     * @param where (GeographicCoordinates) : gives the localisation of the observer
     */
    public EquatorialToHorizontalConversion (ZonedDateTime when, GeographicCoordinates where) {
        double latObserver = where.lat();
        siderealTime = SiderealTime.local(when,where);
        cosPhy = Math.cos(latObserver);
        sinPhy = Math.sin(latObserver);
    }

    /**
     * Method calculating the values to return the horizontal coordinates related to the equatorial entered in parameters
     * @param equ  (EquatorialCoordinates) : gives the equatorial coordinates that we search to convert
     * @return hor (HorizontalCoordinates) : return the horizontal coordinates related to equ
     */
    @Override
    public HorizontalCoordinates apply(EquatorialCoordinates equ) {

        double sinDelta = Math.sin(equ.dec());
        double cosDelta = Math.cos(equ.dec());
        double hourAngle = siderealTime-equ.ra();
        double heightHoriz = Math.asin(sinDelta*sinPhy+cosDelta*cosPhy*Math.cos(hourAngle));
        final double azimuthHoriz = Math.atan2(-cosDelta*cosPhy*Math.sin(hourAngle),sinDelta-sinPhy*Math.sin(heightHoriz));
        return HorizontalCoordinates.of(Angle.normalizePositive(azimuthHoriz),heightHoriz);
    }

    @Override
    public final boolean equals(Object object){ throw new UnsupportedOperationException();}

    @Override
    public final int hashCode(){ throw new UnsupportedOperationException();}
}
