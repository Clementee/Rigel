package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.w3c.dom.Text;

import javax.script.Bindings;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        controlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");
        Pane informationBar = new Pane();

        BorderPane mainPane = new BorderPane(skyView, controlBar, null, informationBar, null);
        mainPane.setMinWidth(800);
        mainPane.setMinHeight(600);
    }

    private HBox controlBarCreator() {

        HBox observationPos = createObservationPosition();

        HBox observationInstant = createObservationInstant();

        HBox timeAdvancement = new HBox();

        Separator separator = new Separator(Orientation.VERTICAL);

        HBox controlBar = new HBox(separator, observationPos, observationInstant, timeAdvancement);
        controlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");
        return controlBar;
    }


    private HBox createObservationInstant(){

        HBox position = new HBox();
        position.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");

        Label date = new Label("Date :");

        // à instancier I guess
        DatePicker datePicker = new DatePicker();
        datePicker.setStyle("-fx-pref-width: 120;");

        Label hour = new Label("Heure :");

        TextField actHour = new TextField();
        actHour.setStyle("-fx-pref-width: 75;\n" + "-fx-alignment: baseline-right;");
       
        DateTimeFormatter hmsFormatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTimeStringConverter stringConverter =
                new LocalTimeStringConverter(hmsFormatter, hmsFormatter);
        TextFormatter<LocalTime> timeFormatter =
                new TextFormatter<>(stringConverter);
        
        actHour.setTextFormatter(timeFormatter);
        
        position.getChildren().addAll(date, datePicker, hour, actHour);
        return position;
    }

    private HBox createObservationPosition(){

        Label longitude = new Label("Longitude (°) :");
        Label latitude = new Label("Latitude (°) :");

        TextField actualLon = new TextField();
        actualLon.setStyle("-fx-pref-width: 60;\n" + "-fx-alignment: baseline-right;");


        TextField lonTextField = longitudeTextField();
        TextField latTextField = latitudeTextField();

        lonTextField.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");
        latTextField.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");

        HBox observation = new HBox();
        observation.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");
        observation.getChildren().addAll(longitude, lonTextField, latitude, latTextField);
        return observation;
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
