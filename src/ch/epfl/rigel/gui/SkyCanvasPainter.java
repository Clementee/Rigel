package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.Asterism;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.Planet;
import ch.epfl.rigel.astronomy.Star;
import ch.epfl.rigel.bonus.Constellation;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Transform;

import java.util.HashMap;
import java.util.List;

import static ch.epfl.rigel.math.Angle.ofDeg;

/**
 * A painter of canvas for the sky
 *
 * @author Baptiste Lecoeur (316223)
 * @author Clement Sanh (311427)
 */
public final class SkyCanvasPainter {

    private final Canvas canvas;
    private final GraphicsContext ctx;

    /**
     * SkyCanvasPainter public constructor initializing the canvas and the
     * graphic context we will be using to draw the sky
     *
     * @param canvas (Canvas) : set the canvas we are going to use to draw the sky
     */
    public SkyCanvasPainter(Canvas canvas) {
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();
    }

    /**
     * SkyCanvasPainter public method used to clear the canvas by reformatting it as a black canvas
     */
    public void clear() {
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * SkyCanvasPainter public method used to draw the multiple stars onto the canvas in their right position
     *
     * @param observedSky             (ObservedSky) : gives the components of the sky we are going to draw onto the canvas
     * @param stereographicProjection (StereographicProjection) : offers the projection we will be using to place the
     *                                stars in the canvas
     * @param transform               (Transform) : gives the transformation we will be using to modify the projection
     *                                in the good frame
     */
    public void drawStars(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform, boolean drawAsterism, boolean drawConstellation) {

        final Color ASTERISM_COLOR = Color.BLUE;
        final Color CONSTELLATION_COLOR = Color.SPRINGGREEN;
        Bounds bound = canvas.getBoundsInLocal();
        double[] starPosition = observedSky.starsPosition();
        if (drawAsterism) {
            for (Asterism asterism : observedSky.asterism()) {

                List<Star> starFromAsterism = asterism.stars();

                ctx.setStroke(ASTERISM_COLOR);
                ctx.setLineWidth(1);


                for (int i = 0; i < starFromAsterism.size(); i++) {

                    Star star = starFromAsterism.get(i);
                    double x1 = starPosition[2 * (observedSky.stars().indexOf(star))];
                    double y1 = starPosition[2 * (observedSky.stars().indexOf(star)) + 1];

                    Point2D begTransformed = transform.transform(x1, y1);
                    if (i < starFromAsterism.size() - 1) {

                        Star star2 = starFromAsterism.get(i + 1);
                        int u = observedSky.stars().indexOf(star2);
                        double x2 = starPosition[2 * u];
                        double y2 = starPosition[2 * u + 1];

                        Point2D endTransformed = transform.transform(x2, y2);

                        if (bound.contains(endTransformed) || bound.contains(begTransformed)) {
                            ctx.strokeLine(begTransformed.getX(), begTransformed.getY(), endTransformed.getX(), endTransformed.getY());
                        }
                    }
                }
            }

        }
        if(drawConstellation){
            List<String> constellationNameAlreadyUsed = new ArrayList<>();
            for (Constellation constellation : observedSky.constellations()) {

                List<Star> starFromConstellation = constellation.asterism().stars();

                ctx.setStroke(CONSTELLATION_COLOR);
                ctx.setLineWidth(1);


                for (int i = 0; i < starFromConstellation.size(); i++) {
                    Star star = starFromConstellation.get(i);
                    double x1 = starPosition[2 * (observedSky.stars().indexOf(star))];
                    double y1 = starPosition[2 * (observedSky.stars().indexOf(star)) + 1];

                    ctx.setFill(CONSTELLATION_COLOR);
                    ctx.setTextAlign(TextAlignment.CENTER);
                    ctx.setTextBaseline(VPos.TOP);


                    Point2D begTransformed = transform.transform(x1, y1);
                    if (i == 0 && !constellationNameAlreadyUsed.contains(constellation.getConstellationName())) {
                        ctx.fillText(constellation.getConstellationName(), begTransformed.getX(), begTransformed.getY());
                        constellationNameAlreadyUsed.add(constellation.getConstellationName());
                    }
                    if (i < starFromConstellation.size() - 1) {

                        Star star2 = starFromConstellation.get(i + 1);
                        int u = observedSky.stars().indexOf(star2);
                        double x2 = starPosition[2 * u];
                        double y2 = starPosition[2 * u + 1];

                        Point2D endTransformed = transform.transform(x2, y2);

                        if (bound.contains(endTransformed) || bound.contains(begTransformed)) {
                            ctx.strokeLine(begTransformed.getX(), begTransformed.getY(), endTransformed.getX(), endTransformed.getY());
                        }
                    }
                }
            }
        }

        for (int i = 0; i < starPosition.length; i += 2) {
            Star star = observedSky.stars().get(i / 2);
            Color starColor = BlackBodyColor
                    .colorForTemperature(star.colorTemperature());
            double starDiameter = transform.deltaTransform(0, objectDiameter(star.magnitude(), stereographicProjection)).magnitude();


            double x = starPosition[i];
            double y = starPosition[i + 1];

            Point2D point = transform.transform(x, y);

            if(starDiameter > 4){
                ctx.setTextAlign(TextAlignment.CENTER);
                ctx.setTextBaseline(VPos.TOP);
                ctx.fillText(star.name(),point.getX(),point.getY());
            }

            ctx.setFill(starColor);
            drawCircle(ctx, point.getX(), point.getY(), starDiameter);
        }
    }

    /**
     * SkyCanvasPainter public method used to draw the planets onto the canvas in their right position
     *
     * @param observedSky             (ObservedSky) : gives the components of the sky we are going to draw onto the canvas
     * @param stereographicProjection (StereographicProjection) : offers the projection we will be using to place the
     *                                stars in the canvas
     * @param transform               (Transform) : gives the transformation we will be using to modify the projection
     *                                in the good frame
     */
    public void drawPlanets(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform) {

        final Color PLANET_COLOR = Color.LIGHTGRAY;

        for (Planet planet : observedSky.planets()) {

            double planetDiameter = transform.deltaTransform(0, objectDiameter(planet.magnitude(), stereographicProjection)).magnitude();

            double x = observedSky.planetsPosition()[2 * (observedSky.planets().indexOf(planet))];
            double y = observedSky.planetsPosition()[2 * (observedSky.planets().indexOf(planet)) + 1];

            Point2D point = transform.transform(x, y);

            ctx.setFill(PLANET_COLOR);
            drawCircle(ctx, point.getX(), point.getY(), planetDiameter);
        }
    }

    /**
     * SkyCanvasPainter public method used to draw the sun onto the canvas in their right position
     *
     * @param observedSky             (ObservedSky) : gives the components of the sky we are going to draw onto the canvas
     * @param stereographicProjection (StereographicProjection) : offers the projection we will be using to place the
     *                                sun in the canvas
     * @param transform               (Transform) : gives the transformation we will be using to modify the projection
     *                                in the good frame
     */
    public void drawSun(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform) {

        final Color SUN_WHITE = Color.WHITE;
        final Color SUN_YELLOW = Color.YELLOW;
        final Color SUN_YELLOW2 = Color.rgb(255, 255, 0, 0.25);

        final double sunAngularSize = transform.deltaTransform(0, observedSky.sun().angularSize()).magnitude() / 2;
        final double x = observedSky.sunPosition().x();
        final double y = observedSky.sunPosition().y();

        Point2D point = transform.transform(x, y);

        ctx.setFill(SUN_YELLOW2);
        drawCircle(ctx, point.getX(), point.getY(), 2.2 * sunAngularSize);

        ctx.setFill(SUN_YELLOW);
        drawCircle(ctx, point.getX(), point.getY(), (2 + sunAngularSize));

        ctx.setFill(SUN_WHITE);
        drawCircle(ctx, point.getX(), point.getY(), sunAngularSize);

    }

    /**
     * SkyCanvasPainter public method used to draw the moon onto the canvas in their right position
     *
     * @param observedSky             (ObservedSky) : gives the components of the sky we are going to draw onto the canvas
     * @param stereographicProjection (StereographicProjection) : offers the projection we will be using to place the
     *                                moon in the canvas
     * @param transform               (Transform) : gives the transformation we will be using to modify the projection
     *                                in the good frame
     */
    public void drawMoon(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform) {

        final Color MOON_COLOR = Color.WHITE;

        final double moonAngSize = transform.deltaTransform(0, stereographicProjection.applyToAngle(observedSky.moon().angularSize())).magnitude();
        final double x = observedSky.moonPosition().x();
        final double y = observedSky.moonPosition().y();

        Point2D point = transform.transform(x, y);

        ctx.setFill(MOON_COLOR);
        drawCircle(ctx, point.getX(), point.getY(), moonAngSize);
    }

    /**
     * SkyCanvasPainter public method used to draw the horizon and the cardinal points onto the canvas in their right position
     *
     * @param observedSky             (ObservedSky) : gives the components of the sky we are going to draw onto the canvas
     * @param stereographicProjection (StereographicProjection) : offers the projection we will be using to place the
     *                                horizon and the cardinal points in the canvas
     * @param transform               (Transform) : gives the transformation we will be using to modify the projection
     *                                in the good frame
     */
    public void drawHorizon(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform) {

        ctx.setLineWidth(2);
        ctx.setStroke(Color.RED);
        ctx.setFill(Color.RED);

        ctx.setLineWidth(2);
        ctx.setStroke(Color.RED);
        ctx.setFill(Color.RED);


        for (double i = 0; i < 360; i += 45) {

            HorizontalCoordinates coords = HorizontalCoordinates.ofDeg(i, -0.5);

            String octantName = coords.azOctantName("N", "E", "S", "O");

            Point2D point = transform.transform(stereographicProjection.apply(coords).x(), stereographicProjection.apply(coords).y());

            ctx.setTextAlign(TextAlignment.CENTER);
            ctx.setTextBaseline(VPos.TOP);
            ctx.fillText(octantName, point.getX(), point.getY());
        }

        double r = stereographicProjection.circleRadiusForParallel(HorizontalCoordinates.of(0, 0));

        CartesianCoordinates centerCoords = stereographicProjection.circleCenterForParallel(HorizontalCoordinates.of(0, 0));

        Point2D transformedCenter = transform.transform(centerCoords.x(), centerCoords.y());
        Point2D rad = transform.deltaTransform(-r, r);
        double radius = Math.abs(rad.getX()) + Math.abs(rad.getY());
        ctx.setLineWidth(2);
        ctx.setStroke(Color.RED);
        ctx.strokeOval(transformedCenter.getX() - radius / 2, transformedCenter.getY() - radius / 2, radius, radius);
    }

    /**
     * SkyCanvasPainter private method used to cap the magnitude
     *
     * @param magnitude (double) : gives the magnitude we want to cap
     * @return (double) : return the capped magnitude
     */
    private double cappedMagnitude(double magnitude) {
        return ClosedInterval.of(-2, 5).clip(magnitude);
    }

    /**
     * SkyCanvasPainter private method used to calculate the size factor of some celestial objects
     *
     * @param magnitude (double) : gives the magnitude we want to cap
     * @return (double) : return the size factor obtained thanks to the magnitude
     */
    private double sizeFactor(double magnitude) {
        return (99 - 17 * cappedMagnitude(magnitude)) / 140;
    }

    /**
     * SkyCanvasPainter private method used to calculate the diameter of some celestial objects
     *
     * @param magnitude  (double) : gives the magnitude we want to cap
     * @param projection (StereographicProjection) : gives the projection used to set the objects onto the canvas
     * @return (double) : return the diameter of the chosen object
     */
    private double objectDiameter(double magnitude, StereographicProjection projection) {
        return sizeFactor(magnitude) * projection.applyToAngle(ofDeg(0.5));
    }

    /**
     * SkyCanvasPainter private method used to draw a circle
     *
     * @param ctx (GraphicsContext) : gives the place of canvas where we want to draw the circle
     * @param x   (double) : gives the x for the center of the circle
     * @param y   (double) : gives the y for the center of the circle
     * @param d   (double) : gives the diameter of the circle we search to draw
     */
    private static void drawCircle(GraphicsContext ctx, double x, double y, double d) {
        ctx.fillOval(x - d / 2, y - d / 2, d, d);
    }
}
