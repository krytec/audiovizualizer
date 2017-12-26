package Testing;

import Filter.Filter;
import GUI.*;
import Mp3Player.MP3Player;
import Playlist.Playlist;
import Playlist.PlaylistManager;
import javafx.application.Application;
import javafx.scene.Scene;
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
    private Filterlist filterlist;
    private Filter.FilterMap filterMap;
    private Controllbar controllbar;

    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox root = new VBox();
        player = new MP3Player();
        manager = new PlaylistManager();
        playlist = new Playlist("default");
        playlist=manager.createTrack(playlist);
        controller = new Controller(player,manager,playlist);
        controllbar = new Controllbar(controller);
        draw = new DrawFilter(controller);
        drawing = new PlaylistDrawing(controller);
        draw.init(root);
        filtercontroller = new Filtercontroller(draw);
        filterMap = new Filter.FilterMap(controller,filtercontroller.getGC());
        HashMap<String,Filter> map = filterMap.init();
        filterlist = new Filterlist(map,filtercontroller);



        controllbar.init(root);
        filterlist.init(root);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1600, 900));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
