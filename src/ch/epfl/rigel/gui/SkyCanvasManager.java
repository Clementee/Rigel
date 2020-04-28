package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.transform.Transform;

import java.time.ZonedDateTime;

import static java.lang.Math.abs;

public class SkyCanvasManager {
    public ObjectProperty<Double> mouseAzDeg;
    public ObjectProperty<Double> mouseAltDeg;
    public ObjectProperty<CelestialObject> objectUnderMouse;

    private ObserverLocationBean observerLocationBean;
    private DateTimeBean dateTimeBean;
    private ViewingParametersBean viewingParametersBean;


    private ObservableValue<StereographicProjection> projection;
    private ObservableValue<Transform> planeToCanvas;
    private ObservableValue<ObservedSky> observedSky;

    private ObjectProperty<CartesianCoordinates> mousePosition = new SimpleObjectProperty<>(null);
    private ObservableValue<HorizontalCoordinates> mouseHorizontalPosition;

    private Canvas canvas;

    public SkyCanvasManager(StarCatalogue starCatalogue, ObserverLocationBean observerLocationBean, DateTimeBean dateTimeBean, ViewingParametersBean viewingParametersBean) {
        mouseAzDeg = new SimpleObjectProperty<>(null);
        mouseAltDeg = new SimpleObjectProperty<>(null);
        objectUnderMouse = new SimpleObjectProperty<>(null);

        projection = Bindings.createObjectBinding(() ->
                new StereographicProjection(viewingParametersBean.getCenter()));

        //planeToCanvas = Bindings.createObjectBinding(()->Transform.affine())

        observedSky = Bindings.createObjectBinding(() ->
                new ObservedSky(dateTimeBean.getZonedDateTime(), observerLocationBean.getCoordinates(), projection.getValue(), starCatalogue)
        );


        canvas.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                canvas.requestFocus();
            }
        });
        canvas.setOnMouseMoved(event -> {
            mousePosition.set(CartesianCoordinates.of(event.getX(), event.getY()));
        });
        canvas.setOnScroll(evt -> {
            double before = viewingParametersBean.getFieldOfViewDeg();
            viewingParametersBean.setFieldOfViewDeg(before + (abs(evt.getDeltaX() > abs(evt.getDeltaY()) ? evt.getDeltaX() : evt.getDeltaY())));
        });
        
        mouseHorizontalPosition = Bindings.createObjectBinding(() -> projection.getValue().inverseApply(mousePosition.get()));

    }

    public Double getMouseAzDeg() {
        return mouseAzDeg.get();
    }

    public ObjectProperty<Double> mouseAzDegProperty() {
        return mouseAzDeg;
    }

    public void setMouseAzDeg(Double mouseAzDeg) {
        this.mouseAzDeg.set(mouseAzDeg);
    }

    public Double getMouseAltDeg() {
        return mouseAltDeg.get();
    }

    public ObjectProperty<Double> mouseAltDegProperty() {
        return mouseAltDeg;
    }

    public void setMouseAltDeg(Double mouseAltDeg) {
        this.mouseAltDeg.set(mouseAltDeg);
    }

    public CelestialObject getObjectUnderMouse() {
        return objectUnderMouse.get();
    }

    public ObjectProperty<CelestialObject> objectUnderMouseProperty() {
        return objectUnderMouse;
    }

    public void setObjectUnderMouse(CelestialObject objectUnderMouse) {
        this.objectUnderMouse.set(objectUnderMouse);
    }

    public Canvas canvas() {
        SkyCanvasPainter painter = new SkyCanvasPainter(canvas);
        painter.drawPlanets(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
        painter.drawHorizon(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
        painter.drawMoon(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
        painter.drawSun(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
        painter.drawStars(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
        return canvas;
    }

}