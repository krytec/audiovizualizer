package GUI;

import Filter.FilterMap;
import Mp3Player.MP3Player;
import Mp3Player.PlayerFassade;
import Playlist.Playlist;
import Playlist.PlaylistManager;
import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;

public class AudioVisualiser {
    private PlayerFassade playerFassade;
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

        HBox root = new HBox();
        VBox main = new VBox();
        player = new MP3Player();
        manager = new PlaylistManager();
        playlist = new Playlist("default");
        playerFassade = new PlayerFassade(player,manager,playlist);
        playlist=manager.createTrack(playlist);
        draw = new DrawFilter(playerFassade);
        filtercontroller = new Filtercontroller(draw);
        filterMap = new FilterMap(playerFassade,filtercontroller.getGC());
        options = new Options(filterMap,filtercontroller);
        controllbar = new Controllbar(playerFassade);
        main.getChildren().addAll(draw,controllbar);
        root.getChildren().add(main);



        root.setPrefSize(800,600);
        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(showoption.getValue()){
                    filtercontroller.setWidth(root.getWidth()-150);
                }else {
                    filtercontroller.setWidth(newValue.doubleValue());
                }
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1),draw);
                fadeTransition.setFromValue(0.5);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();

            }
        });
        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                filtercontroller.setHeight(newValue.doubleValue()-150);
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1),draw);
                fadeTransition.setFromValue(0.5);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();

            }
        });

        Scene scene = new Scene(root,800,600);
        controllbar.getOptions().setOnAction(e-> {
            if(showoption.getValue()){
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1),options);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.play();
                root.getChildren().remove(options);
                showoption.set(false);
            }else {
                root.getChildren().add(1,options);
                HBox.setMargin(options,new Insets(5));
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1),options);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
                showoption.set(true);
            }

        });

        showoption.addListener((a,b,c)-> {
            if(c){
                filtercontroller.setWidth(root.getWidth()-150);
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1),draw);
                fadeTransition.setFromValue(0.5);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
            }
            else{
                filtercontroller.setWidth(root.getWidth());
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1),draw);
                fadeTransition.setFromValue(0.5);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
            }
        });
       return scene;

    }
}
