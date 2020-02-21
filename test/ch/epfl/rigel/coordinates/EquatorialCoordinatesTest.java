package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EquatorialCoordinatesTest {

    private static final double EPSILON = 1e-3;
    EquatorialCoordinates equatorialCoordinates = EquatorialCoordinates.of(Angle.ofHr(1.5333), Angle.ofDeg(45));

    @Test
    void raWorksWithValidValues() {
        assertEquals(0.401417, equatorialCoordinates.ra(), EPSILON);

    }

    @Test
    void raDegWorksWithValidValues() {
        assertEquals(23,equatorialCoordinates.raDeg(),EPSILON);

    }

    @Test
    void raHrWorksWithValidValues() {
        assertEquals(Angle.toHr(Angle.ofDeg(23)), equatorialCoordinates.raHr(), EPSILON);

    }

    @Test
    void decWorksWithValidValues() {
        assertEquals(Angle.ofDeg(45), equatorialCoordinates.dec());

    }

    @Test
    void decDegWorksWithValidValues() {
        assertEquals((45), equatorialCoordinates.decDeg());

    }

    @Test
    void testToStringWorksWithValidValues() {
        assertEquals("(ra=1.5333h, dec=45.0000°)", equatorialCoordinates.toString());
    }
