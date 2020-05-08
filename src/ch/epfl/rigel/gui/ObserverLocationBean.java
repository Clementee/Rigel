package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

public class ObserverLocationBean {
    private final DoubleProperty lonDeg = new SimpleDoubleProperty(0);
    private final DoubleProperty latDeg = new SimpleDoubleProperty(0);
    private ObservableValue<GeographicCoordinates> coordinates = new SimpleObjectProperty<>(null);

    public ObserverLocationBean(){
        coordinates = Bindings.createObjectBinding(
                ()-> GeographicCoordinates.ofDeg(lonDeg.get(), latDeg.get()), coordinates, lonDeg, latDeg);
    }


    public Double getLonDeg() {return lonDeg.get();}

    public DoubleProperty lonDegProperty() {return lonDeg;}

    public Double getLatDeg() {return latDeg.get();}

    public DoubleProperty latDegProperty() {return latDeg;}



    public GeographicCoordinates getCoordinates() {
        return coordinates.getValue();
    }

    public ObservableValue<GeographicCoordinates> coordinatesProperty() {
        return coordinates;
    }

    public void setCoordinates(GeographicCoordinates coordinates) {
        latDeg.set(coordinates.latDeg());
        lonDeg.set(coordinates.lonDeg());
    }
}
