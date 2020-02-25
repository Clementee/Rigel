package ch.epfl.rigel.astronomy;

import java.time.*;
import java.time.temporal.ChronoUnit;


public enum Epoch {

    J2000(LocalDate.of(2000, Month.JANUARY,1),LocalTime.of(12,0), ZoneOffset.UTC),
    J2010(LocalDate.of(2010, Month.JANUARY, 1).minusDays(1),LocalTime.of(0,0),ZoneOffset.UTC);

    private ZonedDateTime thisZoneTime;

    Epoch(LocalDate currentDate, LocalTime currentHour, ZoneId zone) {
        thisZoneTime = ZonedDateTime.of(currentDate, currentHour, zone);
    }

    public double daysUntil(ZonedDateTime when){
        double deltaDays = -when.until(thisZoneTime, ChronoUnit.MILLIS);
        deltaDays = deltaDays/1000;
        deltaDays = deltaDays/3600;
        deltaDays = deltaDays/24;
        return deltaDays;
    }

    public double julianCenturiesUntil(ZonedDateTime when){
        return daysUntil(when)/36525;
    }
}
