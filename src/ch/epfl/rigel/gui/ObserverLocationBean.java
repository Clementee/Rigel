package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ObserverLocationBean {
    private final ObjectProperty<Double> lonDeg = new SimpleObjectProperty<>(null);
    private final ObjectProperty<DateTimeBean> latDeg = new SimpleObjectProperty<>(null);

    private final ObjectProperty<GeographicCoordinates> coordinates = new SimpleObjectProperty<>(null);
    private IntegerProperty d =

    public Double getLonDeg() {return lonDeg.get();}

    public ObjectProperty<Double> lonDegProperty() {return lonDeg;}

    public DateTimeBean getLatDeg() {return latDeg.get();}

    public ObjectProperty<DateTimeBean> latDegProperty() {return latDeg;}

    private ObjectProperty<GeographicCoordinates> testCoordinates(){
        ObjectProperty<GeographicCoordinates> test  =new SimpleObjectProperty<>(GeographicCoordinates.ofDeg(0,0));

    }
}
