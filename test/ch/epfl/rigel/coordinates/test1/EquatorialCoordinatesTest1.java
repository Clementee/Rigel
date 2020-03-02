package ch.epfl.rigel.coordinates.test1;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EquatorialCoordinatesTest1 {

    private static final double EPSILON = 1e-3;
    EquatorialCoordinates equatorialCoordinates = EquatorialCoordinates.of(Angle.ofHr(1.5333), Angle.ofDeg(45));

    @Test
    void raWorksWithValidValues() {
        assertEquals(0.401417, equatorialCoordinates.ra(), EPSILON);

    }

    @Test
    void raDegWorksWithValidValues() {
        assertEquals(23, equatorialCoordinates.raDeg(), EPSILON);

    }

    @Test
    void raHrWorksWithValidValues() {
        assertEquals(Angle.toHr(Angle.ofDeg(23)), equatorialCoordinates.raHr(), EPSILON);
    }

    @Test
    void decWorksWithValidValues() {
        assertEquals(Angle.ofDeg(45), equatorialCoordinates.dec());
        assertEquals((Math.PI/2), EquatorialCoordinates.of(0,Angle.ofDeg(90)).dec());
        assertEquals((0), EquatorialCoordinates.of(0,0).dec());

    }

    @Test
    void decDegWorksWithValidValues() {
        assertEquals((45), equatorialCoordinates.decDeg());
        assertEquals(0, EquatorialCoordinates.of(0,0).decDeg());
        assertEquals((5.050783699050783699050783699), EquatorialCoordinates.of(0, Angle.ofDeg(5.050783699050783699050783699)).decDeg());
        assertEquals((Math.PI), EquatorialCoordinates.of(0, Angle.ofDeg(Math.PI)).decDeg(), EPSILON);


    }

    @Test
    void testToStringWorksWithValidValues() {
        assertEquals("(ra=1.5333h, dec=45.0000°)", equatorialCoordinates.toString());
        assertEquals("(ra=0.0000h, dec=0.0000°)", EquatorialCoordinates.of(0, 0).toString());
        assertEquals("(ra=3.1416h, dec=0.0000°)", EquatorialCoordinates.of(Angle.ofHr(3.141592653589793), 0).toString());
        assertEquals("(ra=0.0000h, dec=1.2998°)", EquatorialCoordinates.of(0, Angle.ofDeg(1.299792458)).toString());
        ;
    }
}
