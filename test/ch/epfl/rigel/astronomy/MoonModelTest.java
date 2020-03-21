package ch.epfl.astronomy;

import ch.epfl.rigel.astronomy.MoonModel;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import org.junit.jupiter.api.Test;

import java.time.*;

import static ch.epfl.rigel.astronomy.Epoch.J2010;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoonModelTest {

    public final static double EPSILON = 10e-9;

    @Test
    void at() {
        assertEquals(-0.20114171346014934,MoonModel.MOON.at(-2313, new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003,  Month.SEPTEMBER, 1),LocalTime.of(0,0), ZoneOffset.UTC))).equatorialPos().dec(),EPSILON);
        assertEquals(14.211456457835897,MoonModel.MOON.at(-2313, new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003,  Month.SEPTEMBER, 1), LocalTime.of(0,0), ZoneOffset.UTC))).equatorialPos().raHr(),EPSILON);
        assertEquals(0.009225908666849136,MoonModel.MOON.at(J2010.daysUntil(ZonedDateTime.of(LocalDate.of(1979, 9, 1),LocalTime.of(0, 0),
                ZoneOffset.UTC)), new EclipticToEquatorialConversion(ZonedDateTime.of(
                LocalDate.of(1979, 9, 1),LocalTime.of(0, 0),ZoneOffset.UTC))).
                angularSize());
        assertEquals("Lune (22.5%)",MoonModel.MOON.at(J2010.daysUntil(ZonedDateTime.of(LocalDate.of(2003, 9, 1),LocalTime.of(0, 0),
                ZoneOffset.UTC)), new EclipticToEquatorialConversion(ZonedDateTime.of( LocalDate.of(2003, 9, 1),
                LocalTime.of(0, 0),ZoneOffset.UTC))).
                info());
    }
}
