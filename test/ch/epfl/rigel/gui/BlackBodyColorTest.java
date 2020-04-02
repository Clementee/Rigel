package ch.epfl.rigel.gui;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlackBodyColorTest {

    @Test
    void colorForTemperature() {
        assertEquals(Color.web("#ffcc99"),BlackBodyColor.colorForTemperature(3798.1409));
    }
}