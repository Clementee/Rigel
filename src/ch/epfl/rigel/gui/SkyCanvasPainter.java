package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.Asterism;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.Planet;
import ch.epfl.rigel.astronomy.Star;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.util.List;

import static ch.epfl.rigel.math.Angle.ofDeg;
import static java.lang.Math.max;


public class SkyCanvasPainter {

    private final Canvas canvas;
    private final GraphicsContext ctx;


    public SkyCanvasPainter(Canvas canvas) {
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();
    }

    public void clear() {
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawStars(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform) {

        final Color ASTERISM_COLOR = Color.BLUE;
        Color starColor;
        Bounds bound = canvas.getBoundsInLocal();


        for (Star star : observedSky.stars()) {

            starColor = BlackBodyColor
                    .colorForTemperature(star.colorTemperature());
            double starDiameter = objectDiameter(star.magnitude(), stereographicProjection);

            double x = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star))];
            double y = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star)) + 1];

            Point2D point = getCoordsOnCanvas(x, y);

            System.out.println(point);

            int k = 1000;
            ctx.setFill(starColor);
            drawCircle(ctx, point.getX(), point.getY(), k*starDiameter);
        }

        for (Asterism asterism : observedSky.asterism()) {

            List<Star> starFromAsterism = asterism.stars();

            ctx.setFill(ASTERISM_COLOR);
            int i = 0;

            for (Star star : starFromAsterism) {

                double x = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star))];
                double y = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star)) + 1];
                boolean containsCondition = bound.contains(x, y);


                Scale scale = Transform.scale(x, y);
                Translate translate = Transform.translate(x, y);

                Transform transform1 = translate.createConcatenation(scale);

                Point2D coordsTranformed = transform1.deltaTransform(x,y);

                if (i == 0 && containsCondition) {
                    ctx.beginPath();
                    ctx.moveTo(coordsTranformed.getX(), coordsTranformed.getY());
                    i++;
                }
                if (i == 1 && containsCondition) {
                    ctx.lineTo(coordsTranformed.getX(), coordsTranformed.getY());
                    i--;
                }
            }
        }
    }

    public void drawPlanets(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform) {

        final Color PLANET_COLOR = Color.LIGHTGRAY;

        for (Planet planet : observedSky.planets()) {

            double planetDiameter = objectDiameter(planet.magnitude(), stereographicProjection);

            double x = observedSky.planetsPosition()[2 * (observedSky.planets().indexOf(planet))];
            double y = observedSky.planetsPosition()[2 * (observedSky.planets().indexOf(planet)) + 1];

            Point2D point = getCoordsOnCanvas(x, y);

            ctx.setFill(PLANET_COLOR);
            ctx.fillOval(point.getX(), point.getY(), planetDiameter, planetDiameter);
        }
    }

    public void drawSun(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform) {
        /*
        Attention : enlever la partie avec le k
         */
        final Color SUN_WHITE = Color.WHITE;
        final Color SUN_YELLOW = Color.YELLOW;
        final Color SUN_YELLOW2 = Color.rgb(255, 255, 0, 0.25);

        final double sunAngularSize = observedSky.sun().angularSize();
        final double x = observedSky.sunPosition().x();
        final double y = observedSky.sunPosition().y();

        Point2D point = getCoordsOnCanvas(x, y);

        int k = 1000;

        System.out.println("Sun : " + point);

        ctx.setFill(SUN_YELLOW2);
        drawCircle(ctx, point.getX(), point.getY(), k*2.2*sunAngularSize);

        ctx.setFill(SUN_YELLOW);
        drawCircle(ctx, point.getX(), point.getY(), (2+k*sunAngularSize));

        ctx.setFill(SUN_WHITE);
        drawCircle(ctx, point.getX(), point.getY(), k*sunAngularSize);


    }

    public void drawMoon(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform) {

        final Color MOON_COLOR = Color.WHITE;

        final double moonAngSize = observedSky.moon().angularSize();
        final double x = observedSky.moonPosition().x();
        final double y = observedSky.moonPosition().y();

        Point2D point = getCoordsOnCanvas(x, y);

        System.out.println("Moon : " + point);
        int k =1000;
        ctx.setFill(MOON_COLOR);
        drawCircle(ctx, point.getX(), point.getY(), k*moonAngSize);
    }

    public void drawHorizon (ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform) {
        
    }


    private double cappedMagnitude(double magnitude) {
        return ClosedInterval.of(-2,5).clip(magnitude);
    }

    private double sizeFactor(double magnitude) {
        return (99 - 17 * cappedMagnitude(magnitude)) / 140;
    }

    private double objectDiameter(double magnitude, StereographicProjection projection) {
        return sizeFactor(magnitude) * projection.applyToAngle(ofDeg(0.5));
    }

    private Point2D getCoordsOnCanvas(double x, double y) {
        Scale scale = Transform.scale(1300, -1300);
        Translate translate = Transform.translate(400, 300);

        Transform transform = translate.createConcatenation(scale);

        return transform.transform(x, y);
    }

    private static void drawCircle(GraphicsContext ctx, double x, double y, double d){
        ctx.fillOval(x-d/2, y-d/2, d,d);
    }
}
