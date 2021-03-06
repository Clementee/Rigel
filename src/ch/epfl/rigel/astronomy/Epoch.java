package ch.epfl.rigel.astronomy;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Enumeration Epoch
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public enum Epoch {

    //values of the enumeration with its characteristics
    J2000(LocalDate.of(2000, 1, 1), LocalTime.NOON, ZoneOffset.UTC),
    J2010(LocalDate.of(2010, 1, 1).minusDays(1), LocalTime.MIDNIGHT, ZoneOffset.UTC);

    private final ZonedDateTime thisZoneTime;
    private final double millisToDays = 1000*3600*24;

    /**
     * Epoch package-private constructor
     *
     * @param currentDate (LocalTime) : gives the date of the specific term of the enumeration
     * @param currentHour (LocalTime) : gives the hour of the specific term of the enumeration
     * @param zone        (ZoneId) : gives the name of the time zone for the date
     */
    Epoch(LocalDate currentDate, LocalTime currentHour, ZoneId zone) {
        thisZoneTime = ZonedDateTime.of(currentDate, currentHour, zone);
    }

    /**
     * Public method returning the number of days between the parameter value and a settled time in the enumeration
     *
     * @param when (ZonedDateTime) : select the zoned date time
     * @return deltaDays (double) : return the difference of days
     */
    public double daysUntil(ZonedDateTime when) {

        double deltaDays = -when.until(thisZoneTime, ChronoUnit.MILLIS);
        deltaDays = deltaDays / millisToDays;

        return deltaDays;
    }

    /**
     * Public method returning the number of julian centuries between the parameter value and a settled time in the enumeration
     *
     * @param when (ZonedDateTime) : select the zoned date time
     * @return (double) : return the number of julian centuries between the two dates
     */
    public double julianCenturiesUntil(ZonedDateTime when) {

        double millisToJulianCenturies = millisToDays*36525;
        
        return thisZoneTime.until(when, ChronoUnit.MILLIS) / millisToJulianCenturies;
    }
}
