package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyAsterismTest {
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

    @Test
    void starsIsImmutable() {
        List<Star> starList = new ArrayList<>();
        starList.add(new Star(0456, "star0", EquatorialCoordinates.of(0, 0), -0.5f, -0.5f));
        starList.add(new Star(0443, "stir", EquatorialCoordinates.of(0, 0), -0.5f, 3.5f));
        starList.add(new Star(45789070, "staaar", EquatorialCoordinates.of(0, 0), -0.5f, 0.5f));

        assertThrows(UnsupportedOperationException.class, () -> { new Asterism(starList).stars().add(new Star(5676545, "starLight", EquatorialCoordinates.of(0,0), 0.2f, 1)); });
        assertThrows(UnsupportedOperationException.class, () -> { new Asterism(starList).stars().remove(1); });

        Asterism astres = new Asterism(starList);
        starList.remove(2);
        astres.stars().get(2);

        starList.add(new Star(45789070, "staaar", EquatorialCoordinates.of(0, 0), -0.5f, 0.5f));
        starList.add(new Star(45770, "staaoar", EquatorialCoordinates.of(0, 0), -0.5f, 0.2f));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {astres.stars().get(3);});


    }
}