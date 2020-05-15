package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.AsterismLoader;
import ch.epfl.rigel.astronomy.HygDatabaseLoader;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.bonus.ConstellationLoader;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
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
import java.util.Arrays;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Main extends Application {

    private SkyCanvasManager canvasManager;
    private TimeAnimator timeAnimator;

    private final String undoString = "\uf0e2";
    private final String pauseString = "\uf04b";
    private final String playString = "\uf04c";
    private final String onString = "\uf06e";
    private final String offString = "\uf070";

    private final Font fontAwesome;

    public Main() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/Font Awesome 5 Free-Solid-900.otf")) {
            fontAwesome = Font.loadFont(inputStream, 15);
        }
    }

    @Override
    public void start(Stage stage) {

        stage.setMinWidth(800);
        stage.setMinHeight(600);

        Pane skyView = createSkyView();
        HBox controlBar = controlBarCreator();
        Pane informationBar = createInformationBar();

        BorderPane mainPane = new BorderPane(skyView, controlBar, null, informationBar, null);
        stage.setScene(new Scene(mainPane));
        stage.show();
        skyView.requestFocus();
    }

    private HBox controlBarCreator() {
        HBox timeAdvancement = createTimeAnimator();

        HBox observationPos = createObservationPosition();

        HBox observationInstant = createObservationInstant();

        HBox setView = createViewSetter();

        Separator separator = new Separator(Orientation.VERTICAL);

        HBox controlBar = new HBox(separator, observationPos, observationInstant, timeAdvancement, setView);
        controlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");
        return controlBar;
    }

    private HBox createObservationInstant() {
        Label date = new Label("Date :");
        DatePicker datePicker = createDatePicker();

        Label hour = new Label("Heure :");
        TextField actHour = createActHour();

        ComboBox<ZoneId> zoneIdComboBox = createZoneIdComboBox();


        HBox position = new HBox(date, datePicker, hour, actHour, zoneIdComboBox);
        position.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");
        return position;
    }

    private HBox createViewSetter(){
        Label viewAst = new Label("Asterism :");
        Button buttonAst = createAstButton();

        Label viewConstellation = new Label("Constellation :");
        Button buttonConstellation = createConstButton();

        HBox viewBox = new HBox(viewAst, buttonAst, viewConstellation, buttonConstellation );
        viewBox.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");
        return viewBox;
    }


    private HBox createObservationPosition() {
        Label longitude = new Label("Longitude (°) :");
        TextField lonTextField = longitudeTextField();

        Label latitude = new Label("Latitude (°) :");
        TextField latTextField = latitudeTextField();


        HBox observation = new HBox(longitude, lonTextField, latitude, latTextField);
        observation.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");
        return observation;
    }

    private HBox createTimeAnimator() {

        timeAnimator = new TimeAnimator(canvasManager.getDateTimeBean());

        ChoiceBox<NamedTimeAccelerator> choiceOfTheAnimator = createNamedTimeAnimatorChoiceBox();

        Button resetButton = createResetButton();

        Button playPauseButton = createPlayPauseButton();


        HBox timeAnimatorBox = new HBox(choiceOfTheAnimator, resetButton, playPauseButton);
        timeAnimatorBox.setStyle("-fx-spacing: inherit;");
        return timeAnimatorBox;
    }

    private Pane createSkyView() {

        Pane skyView = new Pane();
        try (InputStream hs = getClass().getResourceAsStream("/hygdata_v3.csv"); InputStream is = getClass().getResourceAsStream("/asterisms.txt"); InputStream cs = getClass().getResourceAsStream("/constellations.txt")) {

            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hs, HygDatabaseLoader.INSTANCE).loadFrom(is, AsterismLoader.INSTANCE).loadFrom(cs, ConstellationLoader.INSTANCE)
                    .build();

            ObserverLocationBean observerLocationBean = new ObserverLocationBean();
            DateTimeBean dateTimeBean = new DateTimeBean();
            ViewingParametersBean viewingParametersBean = new ViewingParametersBean();

            dateTimeBean.setZonedDateTime(ZonedDateTime.now());
            observerLocationBean.setCoordinates(
                    GeographicCoordinates.ofDeg(6.57, 46.52));
            viewingParametersBean.setCenter(
                    HorizontalCoordinates.ofDeg(180.000000000001, 15));
            viewingParametersBean.setFieldOfViewDeg(100);

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

        UnaryOperator<TextFormatter.Change> lonFilter = change -> {
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
        };

        TextFormatter<Number> lonTextFormatter =
                new TextFormatter<>(stringConverter, 0, lonFilter);

        TextField lonTextField =
                new TextField();
        lonTextField.setTextFormatter(lonTextFormatter);
        lonTextFormatter.valueProperty().bindBidirectional(canvasManager.getObserverLocationBean().lonDegProperty());
        lonTextField.textProperty().bind(Bindings.format("%.2f", canvasManager.getObserverLocationBean().getLonDeg()));
        lonTextField.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");
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
        latTextFormatter.valueProperty()
                .bindBidirectional(canvasManager.getObserverLocationBean().latDegProperty());
        latTextField.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");
        latTextField.textProperty().bind(Bindings.format("%.2f", canvasManager.getObserverLocationBean().getLatDeg()));

        return latTextField;
    }

    private BorderPane createInformationBar() {
        Text fovText = new Text();
        fovText.textProperty().bind(Bindings.format("Champ de vue : %.1f°", canvasManager.getViewingParametersBean().fieldOfViewDegProperty()));
        Text objectUnderMouseText = new Text();
        objectUnderMouseText.textProperty().bind(canvasManager.objectUnderMouseProperty().asString());
        Text azimutText = new Text();
        azimutText.textProperty().bind(Bindings.format("Azimut : %.2f°, hauteur : %.2f°", canvasManager.getMouseAzDegProperty(), canvasManager.getMouseAltDegProperty()));
        BorderPane borderPane = new BorderPane(objectUnderMouseText, null, azimutText, null, fovText);
        borderPane.setStyle("-fx-padding: 4;-fx-background-color: white;");
        return borderPane;
    }

    private ChoiceBox<NamedTimeAccelerator> createNamedTimeAnimatorChoiceBox() {
        ChoiceBox<NamedTimeAccelerator> choiceOfTheAnimator = new ChoiceBox<>();
        choiceOfTheAnimator.setItems(FXCollections.observableList(Arrays.asList(NamedTimeAccelerator.values())));
        choiceOfTheAnimator.setOnAction(e -> timeAnimator.setAccelerator(choiceOfTheAnimator.getValue().getAccelerator()));
        choiceOfTheAnimator.setValue(NamedTimeAccelerator.TIMES_300);
        choiceOfTheAnimator.disableProperty().bind(timeAnimator.runningProperty());
        return choiceOfTheAnimator;
    }

    private Button createResetButton() {
        Button resetButton = new Button(undoString);
        resetButton.setOnAction((e) -> canvasManager.getDateTimeBean().setZonedDateTime(ZonedDateTime.now()));
        resetButton.setFont(fontAwesome);
        resetButton.disableProperty().bind(timeAnimator.runningProperty());
        return resetButton;
    }

    private Button createPlayPauseButton() {
        Button playPauseButton = new Button(pauseString);
        playPauseButton.setFont(fontAwesome);
        playPauseButton.setOnAction((e) -> {
            playPauseButton.setText(playPauseButton.getText().equals(playString) ? pauseString : playString);
            if (playPauseButton.getText().equals(playString)) {
                timeAnimator.start();
            } else {
                timeAnimator.stop();
            }
        });
        return playPauseButton;
    }

    private Button createAstButton(){
        Button astButton = new Button(offString);
        astButton.setFont(fontAwesome);
        astButton.setOnAction((e)-> {
            astButton.setText(astButton.getText().equals(onString) ? offString : onString);
            astButton.disableProperty().bind(canvasManager.getDrawConstellationProperty());
            canvasManager.setDrawAsterism(astButton.getText().equals(onString));

        });
        return astButton;
    }

    private Button createConstButton(){
        Button constButton = new Button(offString);
        constButton.setFont(fontAwesome);
        constButton.setOnAction((e)-> {
            constButton.setText(constButton.getText().equals(onString) ? offString : onString);
            constButton.disableProperty().bind(canvasManager.getDrawAsterismProperty());
            canvasManager.setDrawConstellation(constButton.getText().equals(onString));

        });
        return constButton;
    }

    private ComboBox<ZoneId> createZoneIdComboBox() {
        ComboBox<ZoneId> zoneIdComboBox = new ComboBox<>(FXCollections.observableList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).collect(Collectors.toList())));
        zoneIdComboBox.setPromptText(canvasManager.getDateTimeBean().getZone().getId());
        zoneIdComboBox.valueProperty().bindBidirectional(canvasManager.getDateTimeBean().zoneProperty());
        zoneIdComboBox.setStyle("-fx-pref-width: 180;");
        zoneIdComboBox.disableProperty().bind(timeAnimator.runningProperty());
        return zoneIdComboBox;
    }

    private ComboBox<ZoneId> createViewComboBox() {
        ComboBox<ZoneId> zoneIdComboBox = new ComboBox<>(FXCollections.observableList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).collect(Collectors.toList())));
        zoneIdComboBox.setPromptText(Boolean.toString(canvasManager.getDrawAsterism()));
        zoneIdComboBox.valueProperty().bindBidirectional(canvasManager.getDateTimeBean().zoneProperty());
        zoneIdComboBox.setStyle("-fx-pref-width: 180;");
        zoneIdComboBox.disableProperty().bind(timeAnimator.runningProperty());
        return zoneIdComboBox;
    }

    private DatePicker createDatePicker() {
        DatePicker datePicker = new DatePicker(canvasManager.getDateTimeBean().getDate());
        datePicker.setStyle("-fx-pref-width: 120;");
        datePicker.valueProperty().bindBidirectional(canvasManager.getDateTimeBean().dateProperty());
        datePicker.valueProperty().addListener((e, i, o) -> canvasManager.getDateTimeBean().setDate(o));
        datePicker.disableProperty().bind(timeAnimator.runningProperty());
        return datePicker;
    }

    private TextField createActHour() {
        TextField actHour = new TextField(canvasManager.getDateTimeBean().getTime().toString());
        actHour.setStyle("-fx-pref-width: 75;\n" + "-fx-alignment: baseline-right;");
        DateTimeFormatter hmsFormatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTimeStringConverter stringConverter =
                new LocalTimeStringConverter(hmsFormatter, hmsFormatter);
        TextFormatter<LocalTime> timeFormatter =
                new TextFormatter<>(stringConverter);

        actHour.setTextFormatter(timeFormatter);
        actHour.setText(canvasManager.getDateTimeBean().getTime().toString());
        actHour.disableProperty().bind(timeAnimator.runningProperty());
        timeFormatter.valueProperty().bindBidirectional(canvasManager.getDateTimeBean().timeProperty());
        return actHour;
    }
}
