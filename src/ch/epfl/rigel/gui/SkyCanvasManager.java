package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
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
    public final DoubleBinding mouseAzDeg;
    public final DoubleBinding mouseAltDeg;

    public final ObjectBinding<CelestialObject> objectUnderMouse;

    private final ObserverLocationBean observerLocationBean;
    private final DateTimeBean dateTimeBean;
    private final ViewingParametersBean viewingParametersBean;

    private final ObservableValue<StereographicProjection> projection;
    private final ObjectBinding<Transform> planeToCanvas;
    private final ObservableValue<ObservedSky> observedSky;

    private final ObjectProperty<Point2D> mousePosition = new SimpleObjectProperty<>(Point2D.ZERO);
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
            double w = projection.getValue().applyToAngle(ofDeg(viewingParametersBean.getFieldOfViewDeg()));
            double s = canvas.getWidth() / w;
            return Transform.affine(s, 0, 0, -s, canvas.getWidth() / 2, canvas.getHeight() / 2);
        }, canvas.heightProperty(), canvas.widthProperty(), projection, viewingParametersBean.fieldOfViewDegProperty());

        planeToCanvas.addListener((e, i, o) -> {
            updateCanvas();
        });

        mouseHorizontalPosition = Bindings.createObjectBinding(() ->
                        projection.getValue().inverseApply(point2DToCartesianCoordinates(planeToCanvas.getValue().transform(mousePosition.getValue()))),
                planeToCanvas, mousePosition, projection);

        mouseAzDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.getValue().azDeg(), mouseHorizontalPosition);

        mouseAltDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.getValue().altDeg(), mouseHorizontalPosition);

        objectUnderMouse = Bindings.createObjectBinding(() -> {
                    if (mousePosition.get() == Point2D.ZERO)
                        return null;
                    else
                        return observedSky.getValue().objectClosestTo(point2DToCartesianCoordinates(planeToCanvas.get().inverseTransform(mousePosition.getValue())), planeToCanvas.get().inverseTransform(0, 10).magnitude()).orElse(null);
                },
                observedSky, planeToCanvas, mousePosition);

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