package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.of;
import static org.junit.jupiter.api.Assertions.*;

class SiderealTimeTest {

    @Test
    void greenwich() {
        assertEquals(Angle.ofHr(10), SiderealTime.greenwich(of(1980,4,22,14,36,51,67, ZoneId.of("UTC"))));
    }

    @Test
    void local() {
    }
}