package GUI;

import Filter.Filter;
import Filter.FilterMap;
import Main.OptionsController;
import Mp3Player.MP3Player;
import Playlist.Playlist;
import Playlist.PlaylistManager;
import Main.Controller;
import Main.Filtercontroller;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

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
    private SimpleBooleanProperty showoption = new SimpleBooleanProperty(false);

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
        VBox option = optionsController.options();
        draw = new DrawFilter(controller,optionsController);
        draw.init(root);
        filtercontroller = new Filtercontroller(draw);
        filterMap = new FilterMap(controller,filtercontroller.getGC());
        HashMap<String,Filter> map = filterMap.init();
        controllbar = new Controllbar(controller,map,filtercontroller,optionsController);
        controllbar.init(root);
        root.setRight(option);
        root.getRight().setVisible(false);


        root.setPrefSize(800,600);
        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(showoption.getValue()){
                    filtercontroller.setWidth(root.getWidth()-150);
                }else {
                    filtercontroller.setWidth(newValue.doubleValue());
                }

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
            if(showoption.getValue()){
                root.getRight().setVisible(false);
                showoption.set(false);
            }else {
                root.getRight().setVisible(true);
                showoption.set(true);
            }

        });

        showoption.addListener((a,b,c)-> {
            if(c){
                filtercontroller.setWidth(root.getWidth()-150);
            }
            else{
                filtercontroller.setWidth(root.getWidth());
            }
        });
       return scene;

    }
}
