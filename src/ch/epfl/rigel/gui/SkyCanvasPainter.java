package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.*;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;

import javax.swing.*;
import java.util.List;

import static ch.epfl.rigel.math.Angle.ofDeg;
import static java.lang.Math.max;

public class SkyCanvasPainter {


    private final Canvas canvas;
    private final GraphicsContext ctx;


    public SkyCanvasPainter(Canvas canvas){
        this.canvas=canvas;
        ctx = canvas.getGraphicsContext2D();
    }

    public void clear(){
        ctx.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawStars(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform){

        final Color ASTERISM_COLOR = Color.BLUE;
        Color starColor;

        Bounds bounds = canvas.getBoundsInLocal();

        for(Star star : observedSky.stars()){

            starColor = BlackBodyColor
                    .colorForTemperature(star.colorTemperature());

            double starDiameter = objectDiameter(star.magnitude());

            double x = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star))];
            double y = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star)) + 1];

            ctx.setFill(starColor);
            ctx.fillOval(x, y , starDiameter , starDiameter);
        }

        for(Asterism asterism : observedSky.asterism()){

            List<Star> starFromAsterism = asterism.stars();

            ctx.setFill(ASTERISM_COLOR);
            int i = 0;
            for (Star star : starFromAsterism)
            {
                double x = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star))];
                double y = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star)) + 1];
                boolean containsCondition = bounds.contains(x, y);
                if(i==0 && containsCondition){
                    ctx.beginPath();
                    ctx.moveTo(x,y);
                    i++;
                }
                if(i==1 && containsCondition){
                    ctx.lineTo(x,y);
                    i--;
                }
            }

        }
    }

    public void drawPlanets(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform){

        final Color PLANET_COLOR = Color.LIGHTGRAY;

        for(Planet planet : observedSky.planets()){

            double planetDiameter = objectDiameter(planet.magnitude());

            double x = observedSky.planetsPosition()[2 * (observedSky.planets().indexOf(planet))];
            double y = observedSky.planetsPosition()[2 * (observedSky.planets().indexOf(planet)) + 1];

            ctx.setFill(PLANET_COLOR);
            ctx.fillOval(x, y , planetDiameter , planetDiameter);
        }
    }

    public void drawSun(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform){
        final Color SUN_WHITE = Color.WHITE;
        final Color SUN_YELLOW = Color.YELLOW;
        final Color SUN_YELLOW2 = Color.rgb(255,255,0, 0.25);

        final double sunAngularSize = observedSky.sun().angularSize();
        final double x = observedSky.sunPosition().x();
        final double y = observedSky.sunPosition().y();

        ctx.setFill(SUN_YELLOW2);
        ctx.fillOval(x,y,2.2*sunAngularSize,2.2*sunAngularSize);

        ctx.setFill(SUN_YELLOW);
        ctx.fillOval(x,y,2+sunAngularSize, 2+sunAngularSize);

        ctx.setFill(SUN_WHITE);
        ctx.fillOval(x,y,sunAngularSize,sunAngularSize);

    }

    public void drawMoon(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform){

        final Color MOON_COLOR = Color.WHITE;
        
        final double moonAngSize = observedSky.moon().angularSize();
        final double x = observedSky.moonPosition().x();
        final double y = observedSky.moonPosition().y();

        ctx.setFill(MOON_COLOR);
        ctx.fillOval(x,y,moonAngSize,moonAngSize);
    }


    private double cappedMagnitude(double magnitude){
        return (ClosedInterval.of(-2, 5)).clip(magnitude);
    }

    private double sizeFactor(double magnitude){
        return (99 - 17 * cappedMagnitude(magnitude)) / 140;
    }

    private double objectDiameter(double magnitude){
        return  sizeFactor(magnitude) * 2 * Math.tan(ofDeg(0.5) / 4);
    }
}
