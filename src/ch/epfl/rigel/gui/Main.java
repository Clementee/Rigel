package ch.epfl.rigel.gui;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
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
        Pane controlBar = new Pane();
        Pane informationBar = new Pane();

        BorderPane mainPane = new BorderPane(skyView,controlBar,null, informationBar,null);
    }
}
