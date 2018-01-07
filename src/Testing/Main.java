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

import java.util.HashMap;

public class Main extends Application {

    private Controller controller;
    private Filtercontroller filtercontroller;
    private PlaylistManager manager;
    private MP3Player player;
    private Playlist playlist;
    private DrawFilter draw;
    private PlaylistDrawing drawing;
    private FilterMap filterMap;
    private Controllbar controllbar;

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        player = new MP3Player();
        manager = new PlaylistManager();
        playlist = new Playlist("default");
        playlist=manager.createTrack(playlist);
        controller = new Controller(player,manager,playlist);

        draw = new DrawFilter(controller);
        drawing = new PlaylistDrawing(controller);
        draw.init(root);
        filtercontroller = new Filtercontroller(draw);
        filterMap = new FilterMap(controller,filtercontroller.getGC());
        HashMap<String,Filter> map = filterMap.init();
        controllbar = new Controllbar(controller,map,filtercontroller);
        controllbar.init(root);
        primaryStage.setTitle("AudioVisualizer");
        Scene scene = new Scene(root, 1600, 900);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
