package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.Asterism;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.Planet;
import ch.epfl.rigel.astronomy.Star;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.geometry.Bounds;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Transform;
import org.w3c.dom.ls.LSOutput;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ch.epfl.rigel.math.Angle.ofDeg;

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


        for (Asterism asterism : observedSky.asterism()) {

            List<Star> starFromAsterism = asterism.stars();

            ctx.setStroke(ASTERISM_COLOR);
            ctx.setLineWidth(1);


            for(int i = 0 ; i < starFromAsterism.size();i++){

                Star star = starFromAsterism.get(i);
                double x1 = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star))];
                double y1 = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star)) + 1];

                Point2D begTransformed = transform.transform(x1, y1);

                if(i<starFromAsterism.size()-1){

                    Star star2 = starFromAsterism.get(i+1);

                    double x2 = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star2))];
                    double y2 = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star2)) + 1];

                    Point2D endTransformed = transform.transform(x2,y2);

                    if(bound.contains(endTransformed) || bound.contains(begTransformed)){
                        ctx.strokeLine(begTransformed.getX(),begTransformed.getY(),endTransformed.getX(),endTransformed.getY());
                    }
                }
            }
        }

        for (Star star : observedSky.stars()) {

            starColor = BlackBodyColor
                    .colorForTemperature(star.colorTemperature());

            double starDiameter = transform.deltaTransform(0, objectDiameter(star.magnitude(), stereographicProjection)).magnitude();


            double x = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star))];
            double y = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star)) + 1];

            Point2D point = transform.transform(x,y);

            ctx.setFill(starColor);
            drawCircle(ctx, point.getX(), point.getY(), starDiameter);
        }
    }

    public void drawPlanets(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform) {

        final Color PLANET_COLOR = Color.LIGHTGRAY;

        for (Planet planet : observedSky.planets()) {

            double planetDiameter = transform.deltaTransform(0, objectDiameter(planet.magnitude(), stereographicProjection)).magnitude();

            System.out.println(planetDiameter);

            double x = observedSky.planetsPosition()[2 * (observedSky.planets().indexOf(planet))];
            double y = observedSky.planetsPosition()[2 * (observedSky.planets().indexOf(planet)) + 1];

            Point2D point = transform.transform(x, y);

            ctx.setFill(PLANET_COLOR);
            drawCircle(ctx,point.getX(),point.getY(),planetDiameter);
        }
    }

    public void drawSun(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform) {

        final Color SUN_WHITE = Color.WHITE;
        final Color SUN_YELLOW = Color.YELLOW;
        final Color SUN_YELLOW2 = Color.rgb(255, 255, 0, 0.25);

        final double sunAngularSize = transform.deltaTransform(0,observedSky.sun().angularSize()).magnitude()/2;
        final double x = observedSky.sunPosition().x();
        final double y = observedSky.sunPosition().y();

        Point2D point = transform.transform(x, y);

        ctx.setFill(SUN_YELLOW2);
        drawCircle(ctx, point.getX(), point.getY(), 2.2*sunAngularSize);

        ctx.setFill(SUN_YELLOW);
        drawCircle(ctx, point.getX(), point.getY(), (2+sunAngularSize));

        ctx.setFill(SUN_WHITE);
        drawCircle(ctx, point.getX(), point.getY(), sunAngularSize);

    }

    public void drawMoon(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform) {

        final Color MOON_COLOR = Color.WHITE;

        final double moonAngSize = transform.deltaTransform(0,stereographicProjection.applyToAngle(observedSky.moon().angularSize())).magnitude();
        final double x = observedSky.moonPosition().x();
        final double y = observedSky.moonPosition().y();

        Point2D point = transform.transform(x, y);

        ctx.setFill(MOON_COLOR);
        drawCircle(ctx, point.getX(), point.getY(), moonAngSize);
    }

    public void drawHorizon (ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform) {
        ctx.setLineWidth(2);
        ctx.setStroke(Color.RED);
        ctx.setFill(Color.RED);

        /*
        Vérifier si optimisable avec horizontal coordinates
         */

        HashMap<String, CartesianCoordinates> cardinalPoints = new HashMap<>();
        cardinalPoints.put("N", stereographicProjection.apply(HorizontalCoordinates.ofDeg(0,-0.5)));
        cardinalPoints.put("NE", stereographicProjection.apply(HorizontalCoordinates.ofDeg(45,-0.5)));
        cardinalPoints.put("E", stereographicProjection.apply(HorizontalCoordinates.ofDeg(90, -0.5)));
        cardinalPoints.put("SE", stereographicProjection.apply(HorizontalCoordinates.ofDeg(135, -0.5)));
        cardinalPoints.put("S", stereographicProjection.apply(HorizontalCoordinates.ofDeg(180, -0.5)));
        cardinalPoints.put("SO", stereographicProjection.apply(HorizontalCoordinates.ofDeg(225, -0.5)));
        cardinalPoints.put("O", stereographicProjection.apply(HorizontalCoordinates.ofDeg(270, -0.5)));
        cardinalPoints.put("NO", stereographicProjection.apply(HorizontalCoordinates.ofDeg(315, -0.5)));

        for (String s : cardinalPoints.keySet()) {
            CartesianCoordinates cartesCoor = cardinalPoints.get(s);
            Point2D point = transform.transform(cartesCoor.x(), cartesCoor.y());
            ctx.setTextAlign(TextAlignment.CENTER);
            ctx.setTextBaseline(VPos.TOP);
            ctx.fillText(s, point.getX(), point.getY());
        }



        double r = stereographicProjection.circleRadiusForParallel(HorizontalCoordinates.of(0,0));
        CartesianCoordinates centerCoords = stereographicProjection.circleCenterForParallel(HorizontalCoordinates.of(0,0));
        Point2D transformedCenter = transform.transform(centerCoords.x() ,centerCoords.y());
        Point2D rad = transform.deltaTransform(-r, r);
        double radius = Math.abs(rad.getX()) + Math.abs(rad.getY());

        System.out.println("r: " +r);
        System.out.println("coords : " + centerCoords.x()  + " " +centerCoords.y());
        System.out.println("transformedCenter : " + transformedCenter.getX() + " " + transformedCenter.getY());


        Ellipse ellipse = new Ellipse(transformedCenter.getX()-radius, transformedCenter.getY()-radius, radius, radius);
        Point2D test = transform.transform(cardinalPoints.get("NO").x(), cardinalPoints.get("NO").y());
        System.out.println(ellipse.contains(test));

        ctx.setLineWidth(2);
        ctx.setStroke(Color.RED);
        ctx.strokeOval(transformedCenter.getX() - radius/2,transformedCenter.getY() -radius/2,radius,radius);
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


    private static void drawCircle(GraphicsContext ctx, double x, double y, double d){
        ctx.fillOval(x-d/2, y-d/2, d,d);
    }
}
