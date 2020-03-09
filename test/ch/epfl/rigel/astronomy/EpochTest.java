package ch.epfl.astronomy;

import ch.epfl.rigel.astronomy.Epoch;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpochTest {

    private final static double EPSILON   = 1e-6;


    @Test
    void daysUntil() {
        ZonedDateTime date = ZonedDateTime.of(1980,4,22,14,36,51,27
                , ZoneOffset.UTC);
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2000, Month.JANUARY, 3),
                LocalTime.of(18, 0),
                ZoneOffset.UTC);
        assertEquals(2.25,Epoch.J2000.daysUntil(d));
        assertEquals(-7192.891076377315, Epoch.J2000.daysUntil(date),EPSILON);
        ZonedDateTime a = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 30),
                LocalTime.of(15, 0),
                ZoneOffset.UTC);
        ZonedDateTime b = ZonedDateTime.of(
                LocalDate.of(2020, Month.MARCH, 20),
                LocalTime.of(0, 0),
                ZoneOffset.UTC);
        ZonedDateTime c = ZonedDateTime.of(
                LocalDate.of(2006, Month.JUNE, 16),
                LocalTime.of(18, 13),
                ZoneOffset.UTC);
        ZonedDateTime de = ZonedDateTime.of(
                LocalDate.of(2000, Month.JANUARY, 3),
                LocalTime.of(18, 0),
                ZoneOffset.UTC);
        ZonedDateTime e = ZonedDateTime.of(
                LocalDate.of(1999, Month.DECEMBER, 6),
                LocalTime.of(23, 3),
                ZoneOffset.UTC);

        assertEquals(1306.125, Epoch.J2000.daysUntil(a));
        assertEquals(7383.5, Epoch.J2000.daysUntil(b));
        assertEquals(2358.259028, Epoch.J2000.daysUntil(c), 1e-6);
        assertEquals(2.25, Epoch.J2000.daysUntil(de));
        assertEquals(-25.539583, Epoch.J2000.daysUntil(e), 1e-6);

        assertEquals(-2345.375, Epoch.J2010.daysUntil(a), 1e-6);
        assertEquals(3732, Epoch.J2010.daysUntil(b));
        assertEquals(-1293.240972, Epoch.J2010.daysUntil(c), 1e-6);
        assertEquals(-3649.25, Epoch.J2010.daysUntil(de), 1e-6);
        assertEquals(-3677.039583, Epoch.J2010.daysUntil(e), 1e-6);
    }

    @Test
    void julianCenturiesUntil() {

        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2000, Month.JANUARY, 3),
                LocalTime.of(18, 0),
                ZoneOffset.UTC);

        ZonedDateTime d2 = ZonedDateTime.of(LocalDate.of(1980,Month.APRIL,22),
                LocalTime.of(14,36,51,(int)67e7),ZoneOffset.UTC);

        assertEquals(2.25 /36525.0 ,Epoch.J2000.julianCenturiesUntil(d));
        //assertEquals(-0.196947296,Epoch.J2000.julianCenturiesUntil(d2));
    }
}
