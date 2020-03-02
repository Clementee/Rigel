package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.of;
import static org.junit.jupiter.api.Assertions.*;

class SiderealTimeTest {

    @Test
    void greenwich() {
        assertEquals(1.2220619247737088,SiderealTime.greenwich(of(1980,4,22,14,36,51,670000000, ZoneId.of("UTC"))), 10e-5);
        assertEquals(5.355270290366605, SiderealTime.greenwich(of(2001,1,27, 12, 0 , 0,0, ZoneId.of("UTC"))),10e-5);
        assertEquals(2.9257399567031235 , SiderealTime.greenwich(of(2004,9,23, 11,0,0,0,ZoneId.of("UTC"))),10e-5);
        assertEquals(1.9883078130455532 , SiderealTime.greenwich(of(2001,9,11,8,14,0,0, ZoneId.of("UTC"))),10e-5);
    }

    @Test
    void local() {
        assertEquals(1.74570958832716, SiderealTime.local(ZonedDateTime.of(1980,4,22,14,36,51,27,ZoneId.of("UTC")),
                GeographicCoordinates.ofDeg(30,45)), 10e-5);
    }
}