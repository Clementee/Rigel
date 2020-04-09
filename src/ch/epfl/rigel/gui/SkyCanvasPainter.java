package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.Asterism;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.Star;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.EquatorialToHorizontalConversion;
import ch.epfl.rigel.coordinates.StereographicProjection;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.epfl.rigel.math.Angle.ofDeg;
import static java.lang.Math.max;

public class SkyCanvasPainter {


    private Canvas canvas;
    private GraphicsContext ctx;
    private StereographicProjection projection;
    private EquatorialToHorizontalConversion conversion;


    public SkyCanvasPainter(Canvas canvas){
        this.canvas=canvas;
        ctx = canvas.getGraphicsContext2D();
    }

    public void clear(){
        ctx.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawStars(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform){

        projection = stereographicProjection;
        Map<Star,CartesianCoordinates> starPosition = new HashMap<>();
        final Color ASTERISM_COLOR = Color.BLUE;
        Color starColor;
        double starMagnitude;


        for(Star star : observedSky.stars()){

            starColor = BlackBodyColor.colorForTemperature(star.colorTemperature());

            if (star.magnitude() >= 5){
                starMagnitude = 5;
            }
            else {
                starMagnitude = max(star.magnitude(), -2);
            }

            double f = (99 - 17 * starMagnitude) / 140;
            double starDiameter = f * 2 * Math.tan(ofDeg(0.5)/4);

            CartesianCoordinates coords = projection.apply(conversion.apply(star.equatorialPos()));
            starPosition.put(star,coords);

            ctx.setFill(starColor);
            ctx.fillOval(coords.x(), coords.y() , starDiameter , starDiameter);
        }

        for(Asterism asterism : observedSky.asterism()){

            List<Star> starFromAsterism = asterism.stars();
            ctx.setFill(ASTERISM_COLOR);
            for(int i = 0 ; i < starFromAsterism.size(); i++){

            }

        }
    }
}
