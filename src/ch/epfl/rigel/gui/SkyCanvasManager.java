package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.astronomy.StarCatalogue;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class SkyCanvasManager {

    public SkyCanvasManager(StarCatalogue catalogue, DateTimeBean dateTime, ObserverLocationBean observerLocation, ViewingParametersBean viewingParameters){

    }

    public ObjectProperty<Double> mouseAzDeg = new SimpleObjectProperty<>();
    public ObjectProperty<Double> mouseAltDeg = new SimpleObjectProperty<>();
    public ObjectProperty<CelestialObject> objectUnderMouse = new SimpleObjectProperty<>();

}
