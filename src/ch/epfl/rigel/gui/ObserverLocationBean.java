package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

public class ObserverLocationBean {
    private final ObjectProperty<Double> lonDeg = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Double> latDeg = new SimpleObjectProperty<>(null);
    private ObservableValue<GeographicCoordinates> coordinates = new SimpleObjectProperty<>(null);

    public ObserverLocationBean(){
        coordinates = Bindings.createObjectBinding(
                ()-> GeographicCoordinates.ofDeg(lonDeg.get(), latDeg.get()), coordinates, lonDeg, latDeg);
    }


    public Double getLonDeg() {return lonDeg.get();}

    public ObjectProperty<Double> lonDegProperty() {return lonDeg;}

    public Double getLatDeg() {return latDeg.get();}

    public ObjectProperty<Double> latDegProperty() {return latDeg;}



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
