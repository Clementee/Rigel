package ch.epfl.rigel.coordinates.test1;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class EclipticCoordinatesTest1 {

    EclipticCoordinates eclipticCoordinates = EclipticCoordinates.of(Angle.ofDeg(23), Angle.ofDeg(45));
    EclipticCoordinates eclipticCoordinates2 = EclipticCoordinates.of(Angle.ofDeg(179), Angle.ofDeg(80));

    @Test
    void ofDegFailsWithWrongValues() {
        assertThrows(IllegalArgumentException.class, () -> {
                    EclipticCoordinates.of(23, 180);
        });
        assertThrows(IllegalArgumentException.class, () -> {
                    EclipticCoordinates.of(23, 180);
        });
        assertThrows(IllegalArgumentException.class, () -> {
                    EclipticCoordinates.of(-300, 2);
        });

    }

    @Test
    void ofDegWorksWithValidValues(){
        assertEquals(23, eclipticCoordinates.lonDeg());
        assertEquals(45, eclipticCoordinates.latDeg());
        assertEquals(179, eclipticCoordinates2.lonDeg());
        assertEquals(80, eclipticCoordinates2.latDeg());

    }

    @Test
    void lon() {
        assertEquals(Angle.ofDeg(23), eclipticCoordinates.lon());
        assertEquals(Angle.ofDeg(179), eclipticCoordinates2.lon());
    }

    @Test
    void lonDegWorksWithValidValues() {
        assertEquals(23, eclipticCoordinates.lonDeg());
    }

    @Test
    void lat() {
        assertEquals(Angle.ofDeg(45), eclipticCoordinates.lat());
        assertEquals(Angle.ofDeg(80), eclipticCoordinates2.lat());
    }

    @Test
    void latDeg() {
        assertEquals(45, eclipticCoordinates.latDeg());
    }

    @Test
    void testToString() {
        assertEquals("(λ=23.0000°, β=45.0000°)", eclipticCoordinates.toString());
    }

}
