package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class SunModelTest {

    @Test
    void at() {
        assertEquals(5.9325494700300885 ,SunModel.SUN.at(27 + 31, new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2010,  Month.FEBRUARY, 27), LocalTime.of(0,0), ZoneOffset.UTC))).equatorialPos().ra());
    }
}