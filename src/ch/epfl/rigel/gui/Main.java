package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.HygDatabaseLoader;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Main extends Application {

    private Stage Rigel;
    private SkyCanvasManager canvasManager;

    public void main() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        Rigel = stage;
        Rigel.setMinWidth(800);
        Rigel.setMinHeight(600);

        Pane skyView = createSkyView();
        HBox controlBar = controlBarCreator();
        controlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");
        Pane informationBar = createInformationBar();

        BorderPane mainPane = new BorderPane(skyView, controlBar, null, informationBar, null);
        mainPane.setMinWidth(800);
        mainPane.setMinHeight(600);
        Rigel.setScene(new Scene(mainPane));
        Rigel.show();
        Rigel.requestFocus();

    }

    private HBox controlBarCreator() throws IOException {

        HBox observationPos = createObservationPosition();

        HBox observationInstant = createObservationInstant();

        HBox timeAdvancement = createTimeAnimator();

        Separator separator = new Separator(Orientation.VERTICAL);

        HBox controlBar = new HBox(separator, observationPos, observationInstant, timeAdvancement);
        controlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");
        return controlBar;
    }


    private HBox createObservationInstant() {

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
        timeFormatter.valueProperty().addListener((e, i, o) -> canvasManager.getDateTimeBean().setTime(o));

        ComboBox<String> zoneIdComboBox = new ComboBox<>();
        zoneIdComboBox.getItems().addAll(ZoneId.getAvailableZoneIds().stream().sorted().collect(Collectors.toList()));
        zoneIdComboBox.setOnAction((e) -> canvasManager.getDateTimeBean().zoneProperty().set(ZoneId.of(zoneIdComboBox.getValue())));
        zoneIdComboBox.setStyle("-fx-pref-width: 180;");

        position.getChildren().addAll(date, datePicker, hour, actHour,zoneIdComboBox);
        return position;
    }

    private HBox createObservationPosition() {

        Label longitude = new Label();
        longitude.setText("Longitude (°) :");
        Label latitude = new Label();
        latitude.setText("Latitude (°) :");

        TextField lonTextField = longitudeTextField();
        lonTextField.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");
        lonTextField.setText(canvasManager.getObserverLocationBean().getLonDeg().toString());

        TextField latTextField = latitudeTextField();
        latTextField.setText(canvasManager.getObserverLocationBean().getLatDeg().toString());
        latTextField.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");

        HBox observation = new HBox();
        observation.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");
        observation.getChildren().addAll(longitude, lonTextField, latitude, latTextField);
        return observation;
    }

    private HBox createTimeAnimator() throws IOException {

        HBox timeAnimator = new HBox();
        ChoiceBox<NamedTimeAccelerator> choiceOfTheAnimator = new ChoiceBox<>();
        choiceOfTheAnimator.setItems(FXCollections.observableList(Arrays.asList(NamedTimeAccelerator.values())));
        timeAnimator.setStyle("-fx-spacing: inherit;");


        InputStream fontStream = getClass()
                .getResourceAsStream("/Font Awesome 5 Free-Solid-900.otf");
        Font fontAwesome = Font.loadFont(fontStream, 15);
        fontStream.close();

        String undoString = "\uf0e2";
        String playString = "\uf04b";
        String pauseString = "\uf04c";

        Button resetButton = new Button(undoString);
        resetButton.setOnAction((e) -> System.out.println("resetButtonPressed"));
        resetButton.setFont(fontAwesome);

        Button playPauseButton = new Button(pauseString);
        playPauseButton.setFont(fontAwesome);
        playPauseButton.setOnAction((e) -> playPauseButton.setText(playPauseButton.getText().equals(playString) ? pauseString : playString));
        timeAnimator.getChildren().addAll(choiceOfTheAnimator, resetButton, playPauseButton);
        return timeAnimator;
    }

    private Pane createSkyView() {

        Pane skyView = new Pane();
        try (InputStream hs = getClass().getResourceAsStream("/hygdata_v3.csv")) {

            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hs, HygDatabaseLoader.INSTANCE)
                    .build();

            ObserverLocationBean observerLocationBean = new ObserverLocationBean();
            DateTimeBean dateTimeBean = new DateTimeBean();
            ViewingParametersBean viewingParametersBean = new ViewingParametersBean();

            ZonedDateTime when =
                    ZonedDateTime.parse("2020-02-17T20:15:00+01:00");
            dateTimeBean.setZonedDateTime(when);
            observerLocationBean.setCoordinates(
                    GeographicCoordinates.ofDeg(6.57, 46.52));
            viewingParametersBean.setCenter(
                    HorizontalCoordinates.ofDeg(180, 42));
            viewingParametersBean.setFieldOfViewDeg(70);

            canvasManager = new SkyCanvasManager(
                    catalogue,
                    dateTimeBean,
                    observerLocationBean,
                    viewingParametersBean);

            Canvas sky = canvasManager.canvas();

            skyView = new Pane();
            skyView.getChildren().add(sky);
            sky.widthProperty().bind(skyView.widthProperty());
            sky.heightProperty().bind(skyView.heightProperty());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return skyView;
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
        lonTextFormatter.valueProperty().addListener((e, i, o) -> canvasManager.getObserverLocationBean().lonDegProperty().set(o.doubleValue()));
        return lonTextField;
    }

    private TextField latitudeTextField() {

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

        TextFormatter<Number> latTextFormatter =
                new TextFormatter<>(stringConverter, 0, latFilter);

        TextField latTextField =
                new TextField();
        latTextField.setTextFormatter(latTextFormatter);
        latTextFormatter.valueProperty().addListener((e, i, o) -> canvasManager.getObserverLocationBean().latDegProperty().set(o.doubleValue()));
        return latTextField;
    }

    private BorderPane createInformationBar(){
        BorderPane borderPane =  new BorderPane();
        borderPane.setStyle("-fx-padding: 4;" +
                "-fx-background-color: white;");
        Text fovText = new Text(Bindings.format("Champ de vue : %1f°", canvasManager.getViewingParametersBean().getFieldOfViewDeg()).toString());
        Text objectUnderMouseInfo = new Text(Objects.requireNonNullElse(canvasManager.getObjectUnderMouse().info(), " ") );
        Text azimut = new Text(Bindings.format("Azimut : %2f°, hauteur : %2f°", canvasManager.mouseAzDeg, canvasManager.mouseAltDeg).toString());
        borderPane.getChildren().addAll(fovText, objectUnderMouseInfo, azimut);
        return borderPane;
    }
}
