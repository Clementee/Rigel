package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

public class ObserverLocationBean {
    private final ObjectProperty<Double> lonDeg = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Double> latDeg = new SimpleObjectProperty<>(null);
    private final ObjectProperty<GeographicCoordinates> coordinates = new SimpleObjectProperty<>(null);
    //Faut peut on utiliser GeographicCoordinates.ofDeg(lonDeg, latDeg)  ?
    private final ObservableValue<GeographicCoordinates> coordObs = initializeCoordObs();
    public Double getLonDeg() {return lonDeg.get();}

    public ObjectProperty<Double> lonDegProperty() {return lonDeg;}

    public Double getLatDeg() {return latDeg.get();}

    public ObjectProperty<Double> latDegProperty() {return latDeg;}

    private ObservableValue<GeographicCoordinates> initializeCoordObs(){
        ObservableValue<GeographicCoordinates> obj = Bindings.createObjectBinding(
                ()-> coordinates.getValue().ofDeg(lonDeg.get(), latDeg.get()), coordinates, lonDeg, latDeg);
        obj.addListener(o-> System.out.println(obj.getValue()));
        return obj;
    }
}
