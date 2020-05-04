package ch.epfl.rigel.gui;

import javafx.application.Application;
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
        HBox controlBar = new HBox();
        Pane informationBar = new Pane();

        BorderPane mainPane = new BorderPane(skyView,controlBar,null, informationBar,null);
        mainPane.setMinWidth(800);
        mainPane.setMinHeight(600);
    }

}
