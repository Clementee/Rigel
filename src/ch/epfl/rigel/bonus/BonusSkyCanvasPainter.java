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

}
