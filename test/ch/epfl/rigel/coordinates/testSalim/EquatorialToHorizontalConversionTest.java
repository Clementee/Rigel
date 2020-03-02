package ch.epfl.rigel.coordinates.testSalim;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.coordinates.EquatorialToHorizontalConversion;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static ch.epfl.rigel.math.Angle.*;
import static java.lang.Math.*;
import static org.junit.jupiter.api.Assertions.*;

class EquatorialToHorizontalConversionTest {
    private final static GeographicCoordinates testGeo = GeographicCoordinates.ofDeg(0, 52);
    private final static ZonedDateTime testDate = ZonedDateTime.of(2020,3,1,5,51,44,0, ZoneId.of("UTC"));

    @Test
    void applyDeg() {
        assertEquals(Angle.ofDMS(19,20,3.64), (new EquatorialToHorizontalConversion(testDate, testGeo).apply(EquatorialCoordinates.of(ofHr(5)+ofDMS(0,51,44), ofDMS(23,13,10)))).alt());
    }
}