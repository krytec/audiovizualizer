package GUI;

import Filter.Filter;
import Filter.FilterMap;
import Main.OptionsController;
import Mp3Player.MP3Player;
import Playlist.Playlist;
import Playlist.PlaylistManager;
import Main.Controller;
import Main.Filtercontroller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.HashMap;

public class AudioVisualiser {
    private Controller controller;
    private Filtercontroller filtercontroller;
    private PlaylistManager manager;
    private MP3Player player;
    private Playlist playlist;
    private DrawFilter draw;
    private Options options;
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
        options = new Options();
        OptionsController optionsController = new OptionsController(options);
        draw = new DrawFilter(controller,optionsController);
        draw.init(root);
        filtercontroller = new Filtercontroller(draw);
        filterMap = new FilterMap(controller,filtercontroller.getGC());
        HashMap<String,Filter> map = filterMap.init();
        controllbar = new Controllbar(controller,map,filtercontroller,optionsController);
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
        controllbar.getOptions().setOnAction(e-> {
            Scene options = new Scene(optionsController.options(),800,600);

        });
       return scene;

    }
}
