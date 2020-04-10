package ch.epfl.rigel.gui;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

public class GUITest extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(800, 300);
        GraphicsContext ctx = canvas.getGraphicsContext2D();

        Color SUN_YELLOW  = Color.YELLOW;
        Color SUN_YELLOW2 = Color.rgb(255,255,0,0.25);
        Color SUN_WHITE = Color.WHITE;

        double sunAngularSize = 30;
        int k =1;

        ctx.setFill(Color.BLACK);
        ctx.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        double xy = 100;
        double distance  = 2.2*sunAngularSize;
        ctx.setFill(SUN_YELLOW2);
        ctx.fillOval( xy-distance/2,xy-distance/2,distance, distance);

        distance = sunAngularSize+2;
        ctx.setFill(SUN_YELLOW);
        ctx.fillOval( xy-distance/2,xy-distance/2,distance, distance);

        distance=sunAngularSize;
        ctx.setFill(SUN_WHITE);
        ctx.fillOval( xy-distance/2,xy-distance/2,distance, distance);

        primaryStage.setScene(new Scene(new BorderPane(canvas)));
        primaryStage.show();
    }
}