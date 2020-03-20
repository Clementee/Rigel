package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AsterismTest {
    @Test
    void starsNormal() {
        List<Star> list = new ArrayList<>();
        list.add(new Star(1342, "Coronavirus", EquatorialCoordinates.of(0,0),0,0));
        assertEquals(List.copyOf(list), (new Asterism(List.copyOf(list))).stars());
    }

    @Test
    void constructorThrowsIllegalArgumentException(){
        List<Star> list = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, ()->{
           new Asterism(list);
        });
    }
}