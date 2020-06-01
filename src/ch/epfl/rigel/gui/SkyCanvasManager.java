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
import javafx.beans.property.*;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Transform;

import javax.imageio.ImageIO;

import static ch.epfl.rigel.coordinates.CartesianCoordinates.point2DToCartesianCoordinates;
import static ch.epfl.rigel.math.Angle.*;
import static java.lang.Math.abs;

/**
 * A sky canvas manager
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public class SkyCanvasManager {

    private final ObserverLocationBean observerLocationBean;
    private final DateTimeBean dateTimeBean;
    private final ViewingParametersBean viewingParametersBean;
    private final DoubleBinding mouseAzDeg;
    private final DoubleBinding mouseAltDeg;
    private final BooleanProperty drawAsterism = new SimpleBooleanProperty(false);
    private final BooleanProperty drawConstellation = new SimpleBooleanProperty(false);


    private final ObjectBinding<CelestialObject> objectUnderMouse;
    private final ObjectBinding<StereographicProjection> projection;
    private final ObjectBinding<Transform> planeToCanvas;
    private final ObjectBinding<ObservedSky> observedSky;

    private final ObjectProperty<Point2D> mousePosition = new SimpleObjectProperty<>();
    private final ObjectBinding<HorizontalCoordinates> mouseHorizontalPosition;

    private Canvas canvas = new Canvas();
    private final SkyCanvasPainter painter = new SkyCanvasPainter(canvas);
    private final ClosedInterval zoomInter = ClosedInterval.of(30, 150);
    private boolean elon=false;

    /**
     * Public SkyCanvasManager constructor initializing many properties and beans
     *
     * @param starCatalogue (StarCatalogue) : the star catalogue used in the canvas
     * @param dTimeBean (DateTimeBean) : the date-time bean used
     * @param obsLocationBean (ObservationLocationBean) : the observation location bean used
     * @param vParametersBean (ViewingParametersBean) : the viewing parameters bean used
     */
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
                    if (mousePosition.get() == null)
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
                    return HorizontalCoordinates.of(0, 0);
                }, planeToCanvas, mousePosition, projection);

        mouseAzDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.get().azDeg(), mouseHorizontalPosition);
        mouseAltDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.get().altDeg(), mouseHorizontalPosition);

        canvas.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                canvas.requestFocus();
            }
            if (event.isSecondaryButtonDown()){
                if (objectUnderMouse.get() != null) {
                    if (objectUnderMouse.get().name().equals("Mars")) {
                        Image elonPic = new Image("elon.jpg");
                        ImageView elonView = new ImageView();
                        elonView.setImage(elonPic);
                        elonView.setPreserveRatio(true);
                        Scene elonScene = new Scene(new HBox(elonView), 1400, 900);
                        elon = true;
                        System.out.println("Elon is coming");
                        updateCanvas();
                    }else elon = false;
                }
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

    /**
     * Public method getObserverlocationBean returning the Observer location bean
     * 
     * @return observerLocationBean(ObserverLocationBean) : the observer location bean from the manager
     */
    public ObserverLocationBean getObserverLocationBean() {
        return observerLocationBean;
    }
    
    /**
     * Public method getDateTimeBean returning the DateTimeBean
     *
     * @return dateTimeBean (DateTimeBean) : the DateTimeBean from the manager
     */
    public DateTimeBean getDateTimeBean() {
        return dateTimeBean;
    }

    /**
     * Public method getViewingParametersBean returning the viewing parameters bean
     *
     * @return viewingParametersBean (ViewingParametersBean) : the viewing parameters bean from the manager
     */
    public ViewingParametersBean getViewingParametersBean() {
        return viewingParametersBean;
    }

    /**
     * Public method getObjectUnderMouse returning the CelestialObject under the mouse
     *
     * @return (CelestialObject) : the celestialObject under the mouse
     */
    public CelestialObject getObjectUnderMouse() {
        return objectUnderMouse.get();
    }

    /**
     * Public method getDrawAsterismProperty returning the BooleanProperty of the drawAsterismProperty
     * 
     * @return drawAsterism (BooleanProperty) : the boolean property if the asterisms are drawn
     */
    public BooleanProperty getDrawAsterismProperty(){
        return drawAsterism;
    }

    /**
     * Public method getDrawAsterism returning the Boolean of the drawAsterismProperty
     *
     * @return (boolean) : the boolean linked to if the asterisms are drawn
     */
    public boolean getDrawAsterism(){
        return drawAsterism.get();
    }

    /**
     * Public method getDrawConstellationProperty returning the BooleanProperty of the drawConstellationProperty
     *
     * @return drawConstellation (BooleanProperty) : the boolean property if the constellations are drawn
     */
    public BooleanProperty getDrawConstellationProperty(){
        return drawConstellation;
    }

    /**
     * Public method getDrawConstellation returning the Boolean of the drawConstellationProperty
     *
     * @return (boolean) : the boolean linked to if the constellations are drawn
     */
    public boolean getDrawConstellation(){
        return drawConstellation.get();
    }

    /**
     * Public method setDrawAsterism setting the Boolean of the drawAsterismProperty
     *
     * @param status (boolean) : the boolean to set the drawAsterismProperty
     */
    public void setDrawAsterism(boolean status){
        drawAsterism.setValue(status);
        updateCanvas();
    }

    /**
     * Public method setDrawConstellation setting the Boolean of the drawConstellationProperty
     *
     * @param status (boolean) : the boolean to set the drawConstellationProperty
     */
    public void setDrawConstellation(boolean status){
        drawConstellation.set(status);
        updateCanvas();
    }

    /**
     * Public method returning the objectUnderMouseProperty
     * 
     * @return objectUnderMouse (ObjectBinding<CelestialObject>) : return the property of the objectUnderMouse
     */
    public ObjectBinding<CelestialObject> objectUnderMouseProperty() {
        return objectUnderMouse;
    }

    /**
     * Public method returning the canvas
     * 
     * @return canvas (Canvas) : the canvas
     */
    public Canvas canvas() {
        return canvas;
    }

    /**
     * Public method returning the mouseAzDegProperty
     * 
     * @return mouseAzDeg (DoubleBinding) : the property of the Azimuth of the mouse 
     */
    public DoubleBinding getMouseAzDegProperty() {
        return mouseAzDeg;
    }

    /**
     * Public method returning the mouseAltDegProperty
     *
     * @return mouseAzDeg (DoubleBinding) : the property of the Altitude of the mouse
     */
    public DoubleBinding getMouseAltDegProperty() {
        return mouseAltDeg;
    }

    /**
     * Private method updating the canvas by clearing then redrawing it
     */
    private void updateCanvas() {
        painter.clear();
        System.out.println(elon);
        if (elon){
            System.out.println(" asking to draw elon");
            painter.drawElon();
        }
         else{
                painter.drawStars(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue(), drawAsterism.getValue(), drawConstellation.getValue());
                painter.drawPlanets(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
                painter.drawSun(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
                painter.drawMoon(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
                painter.drawHorizon(observedSky.getValue(), projection.getValue(), planeToCanvas.getValue());
            }
    }

    /**
     * Private method used to modify the centerPropertyAzDeg
     * 
     * @param valueToAdd (double) : value to add to modify the azimuth of the center property
     */
    private void modifyCenterPropertyAzDeg(double valueToAdd) {
        double newValue = viewingParametersBean.getCenter().azDeg() + valueToAdd;
        newValue = abs(normalizePositive(ofDeg(newValue)) - TAU) < 10e-6 ? normalizePositive(ofDeg(newValue)) - 10e-4 : normalizePositive(ofDeg(newValue));
        viewingParametersBean.setCenter(HorizontalCoordinates.of(newValue, viewingParametersBean.getCenter().alt()));
    }

    /**
     * Private method used to modify the centerPropertyAltDeg
     * 
     * @param valueToAdd (double) : value to add to modify the altitude of the center
     */
    private void modifyCenterPropertyAltDeg(double valueToAdd) {
        double newValue = viewingParametersBean.getCenter().altDeg() + valueToAdd;
        viewingParametersBean.setCenter(HorizontalCoordinates.ofDeg(viewingParametersBean.getCenter().azDeg(), ClosedInterval.of(5, 90).clip(newValue)));
    }
}
