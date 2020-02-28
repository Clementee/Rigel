package ch.epfl.rigel.coordinates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;

class GeographicCoordinatesTest1 {
    GeographicCoordinates geographicCoordinates = GeographicCoordinates.ofDeg(23, 45);

    @Test
    public void ofDegWorksForValidValues() {
        assertEquals(23,geographicCoordinates.lonDeg());
        assertEquals(45,geographicCoordinates.latDeg());
    }
    
    @Test
    public void ofDegFailsForInvalidValues() {
        
        assertThrows(IllegalArgumentException.class,()->{
            GeographicCoordinates.ofDeg(180, 23);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates.ofDeg(0, 91);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates.ofDeg(-181, -91);
        });    
    }

    
    @Test
    public void isValidLonDegWorksOnKnownValues() {

        assertTrue(GeographicCoordinates.isValidLonDeg(21));
        assertTrue(GeographicCoordinates.isValidLonDeg(0));
        assertTrue(GeographicCoordinates.isValidLonDeg(-180));
        assertFalse(GeographicCoordinates.isValidLonDeg(180));
        assertFalse(GeographicCoordinates.isValidLonDeg(-400));
    }

    
    @Test
    public void isValidLatDegWorksOnKnownValues() {

        assertTrue(GeographicCoordinates.isValidLatDeg(23));
        assertTrue(GeographicCoordinates.isValidLatDeg(0));
        assertTrue(GeographicCoordinates.isValidLatDeg(90));
        assertTrue(GeographicCoordinates.isValidLatDeg(-90));
        assertFalse(GeographicCoordinates.isValidLatDeg(-180));
        assertFalse(GeographicCoordinates.isValidLatDeg(180));
        assertFalse(GeographicCoordinates.isValidLatDeg(-400));
    }

    @Test
    public void lonWorksOnKnownValues() {
        assertEquals(Angle.ofDeg(23), geographicCoordinates.lon());
    }

    @Test
    public void lonDegWorksOnKnownValues(){
        assertEquals(23, geographicCoordinates.lonDeg());
    }

    @Test
    public void latWorksOnKnownValues() {
        assertEquals(Angle.ofDeg(45), geographicCoordinates.lat());
    }

    @Test
    public void latDegWorksOnKnownValues() {
        assertEquals(45, geographicCoordinates.latDeg());
    }


    @Test
    public void testToStringWorksOnKnownValues() {
        assertEquals("(lon=23.0000°, lat=45.0000°)", geographicCoordinates.toString());

    }
}