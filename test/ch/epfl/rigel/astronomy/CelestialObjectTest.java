package ch.epfl.astronomy;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CelestialObjectTest {

    @Test
    void CelestialObject(){
        CelestialObject celestial = new CelestialObject("Arion", EquatorialCoordinates.of(Angle.ofDeg(55.8),
                Angle.ofDeg(19.7)), 0.4f, 1.2f);
        assertEquals("Arion", celestial.info());
        assertEquals("Arion", celestial.name());
    }
}
