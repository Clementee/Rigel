package ch.epfl.rigel;

import ch.epfl.rigel.astronomy.AsterismLoader;
import ch.epfl.rigel.astronomy.HygDatabaseLoader;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.gui.SkyCanvasPainter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.time.ZonedDateTime;

public final class DrawSky extends Application {

    public static void main(String[] args) { launch(args); }

    private InputStream resourceStream(String resourceName) {
        return getClass().getResourceAsStream(resourceName);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        try (InputStream hs = resourceStream("/hygdata_v3.csv")) {
            try (InputStream is = resourceStream("/asterisms.txt")) {

                StarCatalogue catalogue = new StarCatalogue.Builder()
                        .loadFrom(hs, HygDatabaseLoader.INSTANCE).loadFrom(is, AsterismLoader.INSTANCE).build();

                ZonedDateTime when =
                        ZonedDateTime.parse("2020-02-17T20:15:00+01:00");
                GeographicCoordinates where =
                        GeographicCoordinates.ofDeg(6.57, 46.52);
                HorizontalCoordinates projCenter =
                        HorizontalCoordinates.ofDeg(180, 45);
                HorizontalCoordinates projCenterSun =
                        HorizontalCoordinates.ofDeg(277, -23);
                HorizontalCoordinates projCenterMoon =
                        HorizontalCoordinates.ofDeg(3.7, -65);
                HorizontalCoordinates projCenterNorth = HorizontalCoordinates.ofDeg(0, 23);
                HorizontalCoordinates projCenterGlobal = HorizontalCoordinates.ofDeg(0, 90);
                //Transform planeToCanvas = Transform.affine(260, 0, 0, -260, 400, 300);
                StereographicProjection projection =
                        new StereographicProjection(projCenter);
                ObservedSky sky =
                        new ObservedSky(when, where, projection, catalogue);

                Canvas canvas =
                        new Canvas(800, 600);
                Transform planeToCanvas =
                        Transform.affine(1300, 0, 0, -1300, 400, 300);
                SkyCanvasPainter painter =
                        new SkyCanvasPainter(canvas);

                painter.clear();
                painter.drawStars(sky, projection, planeToCanvas);
                painter.drawPlanets(sky, projection, planeToCanvas);
                painter.drawSun(sky, projection, planeToCanvas);
                painter.drawMoon(sky, projection, planeToCanvas);
                painter.drawHorizon(sky,projection, planeToCanvas);

                WritableImage fxImage =
                        canvas.snapshot(null, null);
                BufferedImage swingImage =
                        SwingFXUtils.fromFXImage(fxImage, null);
                ImageIO.write(swingImage, "png", new File("sky.png"));
            }
        }
        Platform.exit();
    }
}