package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.astronomy.SunModel;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class SunModelTest {

    private final static double EPSILON = 10e-7;

    @Test
    void at() {
        assertEquals(5.9325494700300885 , SunModel.SUN.at(27 + 31, new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2010,  Month.FEBRUARY, 27), LocalTime.of(0,0), ZoneOffset.UTC))).equatorialPos().ra(),EPSILON);
        assertEquals(8.392682808297808,SunModel.SUN.at(-2349, new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.JULY,
                27), LocalTime.of(0, 0, 0, 0), ZoneOffset.UTC))).equatorialPos().raHr(),EPSILON);
        assertEquals(19.35288373097352,SunModel.SUN.at(-2349, new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.JULY,
                27), LocalTime.of(0, 0, 0, 0), ZoneOffset.UTC))).equatorialPos().decDeg(),EPSILON);
    }

}
