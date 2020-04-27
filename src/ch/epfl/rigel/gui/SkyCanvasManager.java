package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class SkyCanvasManager {

    public ObjectProperty<Double> mouseAzDeg = new SimpleObjectProperty<>();
    public ObjectProperty<Double> mouseAltDeg = new SimpleObjectProperty<>();
    public ObjectProperty<CelestialObject> objectUnderMouse = new SimpleObjectProperty<>();

    private ObjectProperty<StereographicProjection> projection = new SimpleObjectProperty<>();
    private ObjectProperty<CartesianCoordinates> planeToCanvas = new SimpleObjectProperty<>();
    private ObjectProperty<ObservedSky> observedSky = new SimpleObjectProperty<>();
    private ObjectProperty<CartesianCoordinates> mousePosition = new SimpleObjectProperty<>();
    private ObjectProperty<HorizontalCoordinates> mouseHorizontalPosition = new SimpleObjectProperty<>();

    public SkyCanvasManager(StarCatalogue catalogue, DateTimeBean dateTime, ObserverLocationBean observerLocation, ViewingParametersBean viewingParameters){

        projection.set(new StereographicProjection(viewingParameters.getCenter()));
        GeographicCoordinates coordinates = GeographicCoordinates.ofDeg(observerLocation.getLonDeg(),observerLocation.getLatDeg());
        observedSky.set(new ObservedSky(dateTime.getZonedDateTime(), coordinates, projection.get(), catalogue));
        

    }
}
