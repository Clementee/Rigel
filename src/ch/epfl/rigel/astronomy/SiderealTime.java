package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Polynomial;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static ch.epfl.rigel.astronomy.Epoch.J2000;
import static ch.epfl.rigel.math.Angle.*;

/**
 * A sidereal time
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class SiderealTime {

    private final static double MILLI_TO_SECONDS = 1000;
    private final static double SECONDS_TO_HOURS = 3600;
    private final static double S1_COEFF = 1.002737909;

    private final static Polynomial S0POLY = Polynomial.of(0.000025862, 2400.051336, 6.697374558);

    /**
     * Sidereal Time empty private constructor
     */
    private SiderealTime() {
    }

    /**
     * Method giving the sidereal Greenwich time
     *
     * @param when (ZonedDateTime) : a date/hour couple with a time zone
     * @return (double) : the Sidereal Greenwich time in radians(in the interval [0, PI[
     */
    public static double greenwich(ZonedDateTime when) {

        ZonedDateTime whenBis = when.withZoneSameInstant(ZoneId.of("UTC"));

        double T = J2000
                .julianCenturiesUntil(whenBis.truncatedTo(ChronoUnit.DAYS));
        double t = whenBis
                .truncatedTo(ChronoUnit.DAYS)
                .until(whenBis, ChronoUnit.MILLIS) / (MILLI_TO_SECONDS * SECONDS_TO_HOURS);

        double S0 = S0POLY.at(T);
        double S1 = S1_COEFF * t;
        double Sg = S0 + S1;

        return normalizePositive(ofHr(Sg));
    }

    /**
     * Method giving the local Sidereal time
     *
     * @param when  (ZoneDateTime) : a date/hour couple with a time
     * @param where (Geographic Coordinates) : the position
     * @return (double) : the local Sidereal time
     */
    public static double local(ZonedDateTime when, GeographicCoordinates where) {

        double Sg = greenwich(when);
        double lambda = ofDeg(where.lonDeg());

        return Angle.normalizePositive(Sg + lambda);
    }
}
