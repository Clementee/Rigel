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

    public final double siderealTime;
    private final double cosPhi;
    private final double sinPhi;

    /**
     * EquatorialToHorizontalConversion public constructor, initializing few values
     *
     * @param when  (ZonedDateTime) : gives the zoned date time used to settle the conversion
     * @param where (GeographicCoordinates) : gives the localisation of the observer
     */
    public EquatorialToHorizontalConversion(ZonedDateTime when, GeographicCoordinates where) {

        double latObserver = where.lat();

        siderealTime = SiderealTime.local(when, where);
        cosPhi = Math.cos(latObserver);
        sinPhi = Math.sin(latObserver);
    }

    /**
     * Method calculating the values to return the horizontal coordinates related to the equatorial entered in parameters
     *
     * @param equ (EquatorialCoordinates) : gives the equatorial coordinates that we search to convert
     * @return hor (HorizontalCoordinates) : return the horizontal coordinates related to equ
     */
    @Override
    public HorizontalCoordinates apply(EquatorialCoordinates equ) {

        double sinDelta = Math.sin(equ.dec());
        double cosDelta = Math.cos(equ.dec());
        double hourAngle = siderealTime - equ.ra();

        double heightHoriz = Math.asin(sinDelta * sinPhi + cosDelta * cosPhi * Math.cos(hourAngle));
        final double azimuthHoriz = Math.atan2(-cosDelta * cosPhi * Math.sin(hourAngle), sinDelta - sinPhi * Math.sin(heightHoriz));

        return HorizontalCoordinates.of(Angle.normalizePositive(azimuthHoriz), heightHoriz);
    }

    /**
     * EquToHorConversion overrode method throwing UOE
     *
     * @throws UnsupportedOperationException : throws this exception when the method is called
     */
    @Override
    public final boolean equals(Object object) {
        throw new UnsupportedOperationException();
    }

    /**
     * EquToHorConversion overrode method throwing UOE
     *
     * @throws UnsupportedOperationException : throws this exception when the method is called
     */
    @Override
    public final int hashCode() {
        throw new UnsupportedOperationException();
    }
}
