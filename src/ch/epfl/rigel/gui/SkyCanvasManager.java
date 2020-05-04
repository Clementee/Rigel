package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.*;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.Interval;
import ch.epfl.rigel.math.RightOpenInterval;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;

import java.util.spi.AbstractResourceBundleProvider;

import static ch.epfl.rigel.math.Angle.*;
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

    private ObjectProperty<CartesianCoordinates> mousePosition = new SimpleObjectProperty<>(CartesianCoordinates.of(0, 0));
    private ObservableValue<HorizontalCoordinates> mouseHorizontalPosition = new SimpleObjectProperty<>(null);

    private Canvas canvas = new Canvas();
    private SkyCanvasPainter painter = new SkyCanvasPainter(canvas);
    private ClosedInterval zoomInter = ClosedInterval.of(30, 150);

    public SkyCanvasManager(StarCatalogue starCatalogue, DateTimeBean dTimeBean, ObserverLocationBean obsLocationBean, ViewingParametersBean vParametersBean) {
        mouseAzDeg = new SimpleObjectProperty(0.0);
        mouseAltDeg = new SimpleObjectProperty(0.0);
        objectUnderMouse = new SimpleObjectProperty(SunModel.SUN);

        viewingParametersBean = vParametersBean;
        dateTimeBean = dTimeBean;
        observerLocationBean = obsLocationBean;

        projection = Bindings.createObjectBinding(() ->
                new StereographicProjection(viewingParametersBean.getCenter()), viewingParametersBean.centerProperty());

        planeToCanvas = Bindings.createObjectBinding(() -> {
            double w = projection.getValue().applyToAngle(ofDeg(viewingParametersBean.getFieldOfViewDeg()));
            double s = canvas.getWidth() / w;
            return Transform.affine(s, 0, 0, -s, canvas.getWidth() / 2, canvas.getHeight() / 2);
        }, canvas.heightProperty(), canvas.widthProperty(), projection, viewingParametersBean.fieldOfViewDegProperty());

        observedSky = Bindings.createObjectBinding(() ->
                        new ObservedSky(dateTimeBean.getZonedDateTime(), observerLocationBean.getCoordinates(), projection.getValue(), starCatalogue),
                dateTimeBean.dateProperty(), dateTimeBean.zoneProperty(), dateTimeBean.timeProperty(), projection, observerLocationBean.coordinatesProperty()
        );

        projection.addListener((e, i, o) -> {
            updateCanvas();
        });

        observedSky.addListener((e, i, o) -> {
            updateCanvas();
        });

        planeToCanvas.addListener((e, i, o) -> {
            updateCanvas();
        });

        mouseHorizontalPosition = Bindings.createObjectBinding(() -> projection.getValue().inverseApply(mousePosition.get()), projection);

        canvas = canvas();


        canvas.setOnKeyPressed(event -> {
            canvas.requestFocus();
            switch (event.getCode()) {
                case LEFT:
                    modifyCenterPropertyAzDeg(-10.0);
                    break;
                case RIGHT:
                    modifyCenterPropertyAzDeg(10.0);
                    break;
                case DOWN:
                    modifyCenterPropertyAltDeg(-5);
                    break;
                case UP:
                    modifyCenterPropertyAltDeg(5);
                    break;
                default:
                    break;
            }
            event.consume();
        });


        canvas.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                canvas.requestFocus();
            }
        });

        canvas.setOnMouseMoved(event -> {
            mousePosition.set(CartesianCoordinates.of(event.getX(), event.getY()));
            Point2D point = Point2D.ZERO;
            try {
                point = planeToCanvas.getValue().inverseTransform(mousePosition.getValue().x(), mousePosition.get().y());
            } catch (NonInvertibleTransformException e) {
                e.printStackTrace();
            }
            objectUnderMouse.set(observedSky.getValue().objectClosestTo(CartesianCoordinates.of(point.getX(), point.getY()), 1).get());
        });

        objectUnderMouse.addListener((e,i,o)->{
            System.out.println(o);
        });

        canvas.setOnScroll(evt -> {
            double before = viewingParametersBean.getFieldOfViewDeg();
            double newFOV = before - (abs(evt.getDeltaX()) > abs(evt.getDeltaY()) ? evt.getDeltaX() / 2 : evt.getDeltaY() / 2);
            viewingParametersBean.setFieldOfViewDeg(zoomInter.clip(newFOV));
        });

        mouseHorizontalPosition = Bindings.createObjectBinding(() -> HorizontalCoordinates.ofDeg(mouseAzDeg.get(), mouseAltDeg.get()), mouseAltDeg, mouseAzDeg);

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
        return canvas;
    }

    private void updateCanvas() {
        painter.clear();
        painter.drawPlanets(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
        painter.drawHorizon(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
        painter.drawMoon(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
        painter.drawSun(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
        painter.drawStars(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
    }

    private void modifyCenterPropertyAzDeg(double valueToAdd) {
        double newValue = viewingParametersBean.getCenter().azDeg() + valueToAdd;
        newValue = abs(normalizePositive(ofDeg(newValue)) - TAU) < 10e-6 ? normalizePositive(ofDeg(newValue)) - 10e-4 : normalizePositive(ofDeg(newValue));
        viewingParametersBean.setCenter(HorizontalCoordinates.of(newValue, viewingParametersBean.getCenter().alt()));
    }

    private void modifyCenterPropertyAltDeg(double valueToAdd) {
        double newValue = viewingParametersBean.getCenter().altDeg() + valueToAdd;
        viewingParametersBean.setCenter(HorizontalCoordinates.ofDeg(viewingParametersBean.getCenter().azDeg(), ClosedInterval.of(5, 90).clip(newValue)));

    }
}