package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.math.Angle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


class EclipticCoordinatesTest {

    EclipticCoordinates eclipticCoordinates = EclipticCoordinates.of(Angle.ofDeg(23), Angle.ofDeg(45));
    EclipticCoordinates eclipticCoordinates2 = EclipticCoordinates.of(Angle.ofDeg(350), Angle.ofDeg(80));

    @Test
    void ofDeg() {
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
    void lon() {
        assertEquals(Angle.ofDeg(23), eclipticCoordinates.lon());
        assertEquals(Angle.ofDeg(350), eclipticCoordinates2.lon());
    }

    @Test
    void lonDeg() {
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