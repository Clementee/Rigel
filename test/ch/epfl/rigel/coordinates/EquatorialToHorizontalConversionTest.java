package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquatorialToHorizontalConversionTest {
    EquatorialToHorizontalConversion convert = new
            EquatorialToHorizontalConversion(ZonedDateTime.of(2009,7,6,0,0,0,0, ZoneOffset.UTC),
            GeographicCoordinates.ofDeg(-135.352088857, 52));
    //-135.352088857
    @Test
    void applyLatWorks() {
        assertEquals(Angle.ofDeg(19.334345), convert.apply(EquatorialCoordinates.of(Angle.ofDMS(5 * 15, 51, 44),
                Angle.ofDMS(23, 13, 10))).lat());
    }

    @Test
    void applyLonWorks() {
        assertEquals(Angle.ofDeg(283.271027), convert.apply(EquatorialCoordinates.of(Angle.ofDMS(5 * 15, 51, 44),
                Angle.ofDMS(23, 13, 10))).lon());
    }
}
