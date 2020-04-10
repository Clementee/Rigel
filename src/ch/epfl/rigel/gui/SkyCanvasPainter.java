package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.Asterism;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.Planet;
import ch.epfl.rigel.astronomy.Star;
import ch.epfl.rigel.coordinates.StereographicProjection;
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

        for(Star star : observedSky.stars()){

            starColor = BlackBodyColor
                    .colorForTemperature(star.colorTemperature());

            double starDiameter = objectDiameter(star.magnitude(), stereographicProjection);

            double x = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star))];
            double y = observedSky.starsPosition()[2 * (observedSky.stars().indexOf(star)) + 1];

            Point2D point = getCoordsOnCanvas(x,y);

            ctx.setFill(starColor);
            ctx.fillOval(point.getX(), point.getY() , starDiameter , starDiameter);
        }

        for(Asterism asterism : observedSky.asterism()){

            List<Star> starFromAsterism = asterism.stars();

            ctx.setFill(ASTERISM_COLOR);

            for(int i = 0 ; i < starFromAsterism.size(); i++){

            }
        }
    }

    public void drawPlanets(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform){

        final Color PLANET_COLOR = Color.LIGHTGRAY;

        for(Planet planet : observedSky.planets()){

            double planetDiameter = objectDiameter(planet.magnitude(),stereographicProjection);

            double x = observedSky.planetsPosition()[2 * (observedSky.planets().indexOf(planet))];
            double y = observedSky.planetsPosition()[2 * (observedSky.planets().indexOf(planet)) + 1];

            Point2D point = getCoordsOnCanvas(x,y);

            ctx.setFill(PLANET_COLOR);
            ctx.fillOval(point.getX(), point.getY() , planetDiameter , planetDiameter);
        }
    }

    public void drawSun(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform){
        // à compléter pogchamp
    }

    public void drawMoon(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform){

        final Color MOON_COLOR = Color.WHITE;
        
        final double moonAngSize = observedSky.moon().angularSize();
        final double x = observedSky.moonPosition().x();
        final double y = observedSky.moonPosition().y();

        Point2D point = getCoordsOnCanvas(x,y);

        ctx.setFill(MOON_COLOR);
        ctx.fillOval(point.getX(), point.getY(), moonAngSize, moonAngSize);
    }


    private double cappedMagnitude(double magnitude){

        if (magnitude >= 5){
            return 5;
        }
        else {
            return max(magnitude, -2);
        }
    }

    private double sizeFactor(double magnitude){
        return (99 - 17 * cappedMagnitude(magnitude)) / 140;
    }

    private double objectDiameter(double magnitude, StereographicProjection projection){
        return  sizeFactor(magnitude) * projection.applyToAngle(ofDeg(0.5));
    }

    private Point2D getCoordsOnCanvas(double x , double y){
        Scale scale = Transform.scale(x,y);
        Translate translate = Transform.translate(x,y);

        Transform transform = translate.createConcatenation(scale);

        return transform.transform(x, y);
    }
}
