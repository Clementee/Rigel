package ch.epfl.rigel.bonus;

import ch.epfl.rigel.astronomy.Asterism;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.Star;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.gui.SkyCanvasPainter;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;

import java.util.List;

public final class BonusSkyCanvasPainter {

    private final Canvas canvas;
    private final GraphicsContext ctx;
    private final SkyCanvasPainter painter;

    /**
     * SkyCanvasPainter public constructor initializing the canvas and the
     * graphic context we will be using to draw the sky
     *
     * @param canvas (Canvas) : set the canvas we are going to use to draw the sky
     */
    public BonusSkyCanvasPainter(SkyCanvasPainter canvasPainter, Canvas canvas) {
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();
        painter = canvasPainter;
    }

    public void drawStars(ObservedSky observedSky, StereographicProjection stereographicProjection, Transform transform, boolean drawAsterism, boolean drawConstellations){
        if(drawAsterism)
            painter.drawStars(observedSky, stereographicProjection, transform);
        if(drawConstellations){
            final Color CONSTELLATION_COLOR = Color.RED;
            Bounds bound = canvas.getBoundsInLocal();
            double[] starPosition = observedSky.starsPosition();

            for (Constellation constellation : observedSky.constellations()) {

                List<Star> starFromConstellations = constellation.stars();

                ctx.setStroke(CONSTELLATION_COLOR);
                ctx.setLineWidth(1);


                for (int i = 0; i < starFromConstellations.size(); i++) {

                    Star star = starFromConstellations.get(i);
                    double x1 = starPosition[2 * (observedSky.stars().indexOf(star))];
                    double y1 = starPosition[2 * (observedSky.stars().indexOf(star)) + 1];

                    Point2D begTransformed = transform.transform(x1, y1);

                    if (i < starFromConstellations.size() - 1) {

                        Star star2 = starFromConstellations.get(i + 1);
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
    }
}
