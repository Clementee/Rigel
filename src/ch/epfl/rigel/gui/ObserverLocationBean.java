package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

/**
 * ObserverLocation bean
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public class ObserverLocationBean {
    
    private final DoubleProperty lonDeg = new SimpleDoubleProperty(0);
    private final DoubleProperty latDeg = new SimpleDoubleProperty(0);
    private ObservableValue<GeographicCoordinates> coordinates = new SimpleObjectProperty<>(null);

    /**
     * Public constructor of ObserverLocationBean creating a binding between the coordinates and the property
     */
    public ObserverLocationBean(){
        coordinates = Bindings.createObjectBinding(
                ()-> GeographicCoordinates.ofDeg(lonDeg.get(), latDeg.get()), coordinates, lonDeg, latDeg);
    }

    /**
     * Public method returning the longitude in degrees
     * 
     * @return (Double) : the longitude in degrees
     */
    public Double getLonDeg() {return lonDeg.get();}

    /**
     * Public method returning the DoubleProperty for the longitude in degrees
     * 
     * @return lonDeg (DoubleProperty) : the property for the longitude in degrees
     */
    public DoubleProperty lonDegProperty() {return lonDeg;}

    /**
     * Public method returning the latitude in degrees
     * 
     * @return (Double) : the latitude in degrees
     */
    public Double getLatDeg() {return latDeg.get();}

    /**
     * Public method returning the DoubleProperty for the latitude in degrees
     *
     * @return latDeg (DoubleProperty) : the property for the latitude in degrees
     */
    public DoubleProperty latDegProperty() {return latDeg;}

    /**
     * Public method returning the geographic coordinates
     * 
     * @return (GeographicCoordinates) : the geographic coordinates from the property
     */
    public GeographicCoordinates getCoordinates() {
        return coordinates.getValue();
    }

    /**
     * Public method returning the Property for the coordinates
     * 
     * @return coordinates (ObservableValue<GeographicCoordinates>) : the property for the coordinates
     */
    public ObservableValue<GeographicCoordinates> coordinatesProperty() {
        return coordinates;
    }

    /**
     * Public method setting the coordinates of the property with the ones entered in parameters
     * 
     * @param coordinates (GeographicCoordinates) : the coordinates we set the property to
     */
    public void setCoordinates(GeographicCoordinates coordinates) {
        latDeg.set(coordinates.latDeg());
        lonDeg.set(coordinates.lonDeg());
    }
}
