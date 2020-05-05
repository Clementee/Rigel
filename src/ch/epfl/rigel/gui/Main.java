package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.util.function.UnaryOperator;

public class Main extends Application {

    private Stage Rigel;

    public void main() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        Rigel = stage;

        Pane skyView = new Pane();
        HBox controlBar = controlBarCreator();
        Pane informationBar = new Pane();

        BorderPane mainPane = new BorderPane(skyView, controlBar, null, informationBar, null);
        mainPane.setMinWidth(800);
        mainPane.setMinHeight(600);
    }

    private HBox controlBarCreator() {

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


        TextField lonTextField = longitudeTextField();
        TextField latTextField = latitudeTextField();

        lonTextField.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");
        latTextField.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");

        HBox controlBar = new HBox(separator, observationPos, observationInstant, timeAdvancement);
        controlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");
        controlBar.getChildren().addAll(longitude, lonTextField, latitude, latTextField);
        return controlBar;
    }

    private TextField longitudeTextField() {

        NumberStringConverter stringConverter =
                new NumberStringConverter("#0.00");

        UnaryOperator<TextFormatter.Change> lonFilter = (change -> {
            try {
                String newText =
                        change.getControlNewText();
                double newLonDeg = stringConverter.fromString(newText).doubleValue();
                return GeographicCoordinates.isValidLonDeg(newLonDeg)
                        ? change
                        : null;
            } catch (Exception e) {
                return null;
            }
        });

        TextFormatter<Number> lonTextFormatter =
                new TextFormatter<>(stringConverter, 0, lonFilter);

        TextField lonTextField =
                new TextField();
        lonTextField.setTextFormatter(lonTextFormatter);
        
        return lonTextField;
    }

    private TextField latitudeTextField(){

        NumberStringConverter stringConverter =
                new NumberStringConverter("#0.00");

            UnaryOperator<TextFormatter.Change> latFilter = (change -> {
                try {
                    String newText = change.getControlNewText();
                    double newLatDeg = stringConverter.fromString(newText).doubleValue();
                    return GeographicCoordinates.isValidLatDeg(newLatDeg) ? change : null;
                } catch (Exception e) {
                    return null;
                }
            });

            TextField latTextField = new TextField();
            latTextField.setTextFormatter(new TextFormatter<>(stringConverter, 0, latFilter));
            return latTextField;
        }
    }
