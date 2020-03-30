package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStarTest {

    @Test
    void hipparcosId() {
        assertEquals(24436, new Star(24436, "Rigel", EquatorialCoordinates.of(0, 0), 0, -0.03f).hipparcosId());
    }

    @Test
    void name(){
        assertEquals("Rigel",new Star(24436, "Rigel", EquatorialCoordinates.of(0, 0), 0, -0.03f).name() );
    }

    @Test
    void colorTemperature() {
        assertEquals(10515 ,new Star(24436, "Rigel", EquatorialCoordinates.of(0, 0), 0, -0.03f)
                .colorTemperature());
        assertEquals(3793,new Star(27989, "Betelgeuse", EquatorialCoordinates.of(0, 0), 0, 1.50f).colorTemperature());
    }

    @Test
    void StarConstructorThrowsIAE(){
        assertThrows(IllegalArgumentException.class, ()->{new Star(-15, "Rigel", EquatorialCoordinates.of(0,0),0,-0.03f);});
        assertThrows(IllegalArgumentException.class, ()->{new Star(1567, "Coronavirus", EquatorialCoordinates.of(0,0), 0, -0.6f);});
        assertThrows(IllegalArgumentException.class, ()->{new Star(1567, "Coronavirus", EquatorialCoordinates.of(0,0), 0, 5.7f);});

    }
}