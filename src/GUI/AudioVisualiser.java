package GUI;

import Filter.Filter;
import Filter.FilterMap;
import Mp3Player.MP3Player;
import Playlist.Playlist;
import Playlist.PlaylistManager;
import Testing.Controller;
import Testing.Filtercontroller;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class AudioVisualiser {
    private Controller controller;
    private Filtercontroller filtercontroller;
    private PlaylistManager manager;
    private MP3Player player;
    private Playlist playlist;
    private DrawFilter draw;
    private PlaylistDrawing drawing;
    private FilterMap filterMap;
    private Controllbar controllbar;


    public AudioVisualiser(){

    }

    public Scene init() throws IOException {

        BorderPane root = new BorderPane();
        player = new MP3Player();
        manager = new PlaylistManager();
        playlist = new Playlist("default");
        playlist=manager.createTrack(playlist);
        controller = new Controller(player,manager,playlist);

        draw = new DrawFilter(controller);
        draw.init(root);
        filtercontroller = new Filtercontroller(draw);


        filterMap = new FilterMap(controller,filtercontroller.getGC());
        HashMap<String,Filter> map = filterMap.init();
        controllbar = new Controllbar(controller,map,filtercontroller);
        controllbar.init(root);
        root.setPrefSize(800,600);
        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                filtercontroller.setWidth(newValue.doubleValue());

            }
        });
        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                filtercontroller.setHeight(newValue.doubleValue()-150);

            }
        });
        Scene scene = new Scene(root,800,600);
       return scene;

    }
}
