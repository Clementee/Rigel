package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.astronomy.Sun;
import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MySunTest {

    @Test
    void sunTest(){

        //tests variés pour sun et moon
        Sun sun = new Sun(EclipticCoordinates.of(Angle.ofDeg(53), Angle.ofDeg(38)),
                EquatorialCoordinates.of(Angle.ofDeg(55.8),Angle.ofDeg(24)),
                0.4f, 5.f);
        assertEquals("Soleil", sun.info());
        assertEquals(EquatorialCoordinates.of(Angle.ofDeg(55.8),
                Angle.ofDeg(24)).dec(), sun.equatorialPos().dec());
        assertEquals(EquatorialCoordinates.of(Angle.ofDeg(55.8),
                Angle.ofDeg(19.7)).ra(), sun.equatorialPos().ra()); //checking equatorial position
        assertEquals(5.f, sun.meanAnomaly());
        assertEquals(-26.7f, sun.magnitude());

        //test pour eclipticPos throws un null
        assertThrows(NullPointerException.class, () -> { new Sun(null,
                EquatorialCoordinates.of(Angle.ofDeg(55.8),Angle.ofDeg(24)),
                0.4f, 5.f); });
    }
}
