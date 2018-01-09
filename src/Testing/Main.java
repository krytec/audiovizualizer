package Testing;

import Filter.Filter;
import GUI.*;
import Mp3Player.MP3Player;
import Playlist.Playlist;
import Playlist.PlaylistManager;
import Filter.FilterMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{


        AudioVisualiser audioVisualiser = new AudioVisualiser();
        Scene scene = audioVisualiser.init();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
