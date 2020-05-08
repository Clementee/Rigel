package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.transform.Transform;

import static ch.epfl.rigel.coordinates.CartesianCoordinates.point2DToCartesianCoordinates;
import static ch.epfl.rigel.math.Angle.*;
import static java.lang.Math.abs;

public class SkyCanvasManager {

    public final ObjectBinding<CelestialObject> objectUnderMouse;

    public ObserverLocationBean getObserverLocationBean() {
        return observerLocationBean;
    }

    public DateTimeBean getDateTimeBean() {
        return dateTimeBean;
    }

    public ViewingParametersBean getViewingParametersBean() {
        return viewingParametersBean;
    }

    private final ObserverLocationBean observerLocationBean;
    private final DateTimeBean dateTimeBean;
    private final ViewingParametersBean viewingParametersBean;
    private final DoubleBinding mouseAzDeg;
    private final DoubleBinding mouseAltDeg;

    private final ObjectBinding<StereographicProjection> projection;
    private final ObjectBinding<Transform> planeToCanvas;
    private final ObjectBinding<ObservedSky> observedSky;

    private final ObjectProperty<Point2D> mousePosition = new SimpleObjectProperty<>();
    private final ObjectBinding<HorizontalCoordinates> mouseHorizontalPosition;

    private Canvas canvas = new Canvas();
    private final SkyCanvasPainter painter = new SkyCanvasPainter(canvas);
    private final ClosedInterval zoomInter = ClosedInterval.of(30, 150);

    public SkyCanvasManager(StarCatalogue starCatalogue, DateTimeBean dTimeBean, ObserverLocationBean obsLocationBean, ViewingParametersBean vParametersBean) {

        viewingParametersBean = vParametersBean;
        dateTimeBean = dTimeBean;
        observerLocationBean = obsLocationBean;

        projection = Bindings.createObjectBinding(() ->
                new StereographicProjection(viewingParametersBean.getCenter()), viewingParametersBean.centerProperty());

        observedSky = Bindings.createObjectBinding(() ->
                        new ObservedSky(dateTimeBean.getZonedDateTime(), observerLocationBean.getCoordinates(), projection.getValue(), starCatalogue),
                dateTimeBean.dateProperty(), dateTimeBean.zoneProperty(), dateTimeBean.timeProperty(), projection, observerLocationBean.coordinatesProperty()
        );

        observedSky.addListener((e, i, o) -> {
            updateCanvas();
        });

        canvas = canvas();

        canvas.setOnMouseMoved(event -> mousePosition.set(new Point2D(event.getX(), event.getY())));

        planeToCanvas = Bindings.createObjectBinding(() -> {
            double w = projection.get().applyToAngle(ofDeg(viewingParametersBean.getFieldOfViewDeg()));
            double s = canvas.getWidth() / w;
            return Transform.affine(s, 0, 0, -s, canvas.getWidth() / 2, canvas.getHeight() / 2);
        }, canvas.heightProperty(), canvas.widthProperty(), projection, viewingParametersBean.fieldOfViewDegProperty());

        objectUnderMouse = Bindings.createObjectBinding(() -> {
                    if (mousePosition.get() ==null)
                        return null;
                    else
                        return observedSky.getValue().objectClosestTo(point2DToCartesianCoordinates(planeToCanvas.get().inverseTransform(mousePosition.getValue())), planeToCanvas.get().inverseTransform(0, 10).magnitude()).orElse(null);
                },
                observedSky, planeToCanvas, mousePosition);

        planeToCanvas.addListener((e, i, o) -> {
            updateCanvas();
        });

        // positions of the mouse in the horizontal coordinates system, non null.
        mouseHorizontalPosition =
                Bindings.createObjectBinding(() -> {
                    if (mousePosition.get() != null) {
                        CartesianCoordinates inverseCoordinates = CartesianCoordinates.point2DToCartesianCoordinates(planeToCanvas.get().inverseTransform(mousePosition.get()));
                        return projection.getValue().inverseApply(inverseCoordinates);
                    }
                    return HorizontalCoordinates.of(0,0);
                }, planeToCanvas, mousePosition, projection);

        mouseAzDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.get().azDeg(), mouseHorizontalPosition);
        mouseAltDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.get().altDeg(), mouseHorizontalPosition);

        canvas.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                canvas.requestFocus();
            }


        });

        canvas.setOnKeyPressed(event -> {
            canvas.requestFocus();
            switch (event.getCode()) {
                case LEFT:
                    modifyCenterPropertyAzDeg(-10);
                    break;
                case RIGHT:
                    modifyCenterPropertyAzDeg(10);
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

        canvas.setOnScroll(evt -> {
            double before = viewingParametersBean.getFieldOfViewDeg();
            double newFOV = before - (abs(evt.getDeltaX()) > abs(evt.getDeltaY()) ? evt.getDeltaX() / 2 : evt.getDeltaY() / 2);
            viewingParametersBean.setFieldOfViewDeg(zoomInter.clip(newFOV));
        });
    }

    public CelestialObject getObjectUnderMouse() {
        return objectUnderMouse.get();
    }

    public ObjectBinding<CelestialObject> objectUnderMouseProperty() {
        return objectUnderMouse;
    }

    public Canvas canvas() {
        return canvas;
    }

    public DoubleBinding getMouseAzDegProperty(){
        return mouseAzDeg;
    }

    public DoubleBinding getMouseAltDegProperty(){
        return mouseAltDeg;
    }

    private void updateCanvas() {
        painter.clear();
        painter.drawStars(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
        painter.drawPlanets(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
        painter.drawSun(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
        painter.drawMoon(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
        painter.drawHorizon(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
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