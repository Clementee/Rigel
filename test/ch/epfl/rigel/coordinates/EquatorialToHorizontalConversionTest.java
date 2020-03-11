package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.coordinates.EquatorialToHorizontalConversion;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquatorialToHorizontalConversionTest {

    private final static double EPSILON = 1e-6;

    EquatorialToHorizontalConversion convert = new
            EquatorialToHorizontalConversion(ZonedDateTime.of(2009,7,6,0,0,0,0, ZoneOffset.UTC),
            GeographicCoordinates.ofDeg(-135.352088857, 52));
    //-135.352088857

    @Test
    void applyLatWorks() {
        assertEquals(Angle.ofDeg(19.334345), convert.apply(EquatorialCoordinates.of(convert.siderealTime-Angle.ofHr(5.862222),
                Angle.ofDeg(23.219444))).alt(),EPSILON);
    }

    @Test
    void applyLonWorks() {
        assertEquals(Angle.ofDeg(283.271027), convert.apply(EquatorialCoordinates.of(convert.siderealTime-Angle.ofHr(5.862222),
                Angle.ofDeg(23.219444))).az(),EPSILON);
    }
}
