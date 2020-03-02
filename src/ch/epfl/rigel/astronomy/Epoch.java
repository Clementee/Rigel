package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.math.Angle;

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
    J2000(LocalDate.of(2000, 1,1),LocalTime.of(12,0), ZoneOffset.UTC),
    J2010(LocalDate.of(2010, 1, 1).minusDays(1),LocalTime.of(0,0),ZoneOffset.UTC);

    public ZonedDateTime thisZoneTime;

    /**
     * Epoch package-private constructor
     * @param currentDate     (LocalTime) : gives the date of the specific term of the enumeration
     * @param currentHour     (LocalTime) : gives the hour of the specific term of the enumeration
     * @param zone            (ZoneId) : gives the name of the time zone for the date
     */
    Epoch(LocalDate currentDate, LocalTime currentHour, ZoneId zone) { 
        thisZoneTime = ZonedDateTime.of(currentDate, currentHour, zone);
    }

    /**
     * Public method returning the number of days between the parameter value and a settled time in the enumeration
     * @param when       (ZonedDateTime) : select the zoned date time 
     *
     * @return deltaDays (double) : return the difference of days
     */
    public double daysUntil(ZonedDateTime when){
        double deltaDays = -when.until(thisZoneTime, ChronoUnit.MILLIS);
        deltaDays = deltaDays/1000;
        deltaDays = deltaDays/3600;
        deltaDays = deltaDays/24;
        return deltaDays;
    }

    /**
     * Public method returning the number of julian centuries between the parameter value and a settled time in the enumeration
     * @param when       (ZonedDateTime) : select the zoned date time 
     *
     * @return julianCenturies (double) : return the number of julian centuries between the two dates
     */
    public double julianCenturiesUntil(ZonedDateTime when){ return thisZoneTime.until(when, ChronoUnit.MILLIS)/(3600000.0*24*36525);}
}
