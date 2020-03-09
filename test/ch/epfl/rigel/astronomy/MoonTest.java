package ch.epfl.astronomy;

import ch.epfl.rigel.astronomy.Moon;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoonTest {

    @Test
    void moonTest(){
        Moon moon = new Moon(EquatorialCoordinates.of(Angle.ofDeg(55.8),
                Angle.ofDeg(19.7)), 37.5f, -1, 0.3752f);
        assertEquals("Lune", moon.name());
        assertEquals("Lune (37.5%)", moon.info());
        assertEquals(EquatorialCoordinates.of(Angle.ofDeg(55.8),
                Angle.ofDeg(19.7)).dec(), moon.equatorialPos().dec());
        assertEquals(EquatorialCoordinates.of(Angle.ofDeg(55.8),
                Angle.ofDeg(19.7)).ra(), moon.equatorialPos().ra()); //checking equatorial position
        assertThrows(IllegalArgumentException.class, () -> {new Moon(EquatorialCoordinates.of(Angle.ofDeg(55.8),
                Angle.ofDeg(19.7)), 37.5f, -1, -0.1f); });
    }
}
