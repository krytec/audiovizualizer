package Main;


import GUI.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        File playlist = new File("playlist.m3u");
        if(!playlist.exists()){
            TrackSearcher trackSearcher = new TrackSearcher();
            trackSearcher.walk("C:\\users\\");
        }
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
