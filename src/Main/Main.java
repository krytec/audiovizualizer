package Main;

import Filter.Filter;
import Filter.FilterMap;
import GUI.*;
import Mp3Player.MP3Player;
import Playlist.Playlist;
import Playlist.PlaylistManager;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.HashMap;

public class Main extends Application {
    private Controller controller;
    private Filtercontroller filtercontroller;
    private PlaylistManager manager;
    private MP3Player player;
    private Playlist playlist;
    private DrawFilter draw;
    private Options options;
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
        options = new Options();
        OptionsController optionsController = new OptionsController(options);
        Scene optionscene = new Scene(optionsController.options(),800,600);
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

        optionscene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Scene scene = new Scene(root,800,600);
        controllbar.getOptions().setOnAction(e-> {
            primaryStage.setScene(optionscene);

        });
        options.getClose().setOnAction(e-> primaryStage.setScene(scene));
        /**AudioVisualiser audioVisualiser = new AudioVisualiser();
        Scene scene = audioVisualiser.init();**/
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
