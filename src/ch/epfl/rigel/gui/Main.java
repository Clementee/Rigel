package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage Rigel;

    public void main(){
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        Rigel = stage;

        Pane skyView = new Pane();
        HBox controlBar = controlBarCreator();
        Pane informationBar = new Pane();

        BorderPane mainPane = new BorderPane(skyView,controlBar,null, informationBar,null);
        mainPane.setMinWidth(800);
        mainPane.setMinHeight(600);
    }

    private HBox controlBarCreator(){

        HBox observationPos = new HBox();
        HBox observationInstant = new HBox();
        HBox timeAdvancement = new HBox();

        Separator separator = new Separator(Orientation.VERTICAL);

        Label longitude = new Label("Longitude (°) :");
        longitude.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");
        Label latitude = new Label("Latitude (°) :");
        latitude.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");

        TextField actualLon = new TextField();
        actualLon.setStyle("-fx-pref-width: 60;\n" + "-fx-alignment: baseline-right;");

        HBox controlBar = new HBox(separator,observationPos,observationInstant,timeAdvancement);

        controlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");
        return controlBar;
    }
}
