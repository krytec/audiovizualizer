package GUI;


import Filter.FilterMap;
import Mp3Player.MP3Player;
import Mp3Player.PlayerFassade;
import Playlist.Playlist;
import Playlist.PlaylistManager;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;


/**
 * @author Florian Ortmann, Lea Haugrund
 * Audiovisualiser der eine Scene erstellt und zurück gibt
 */
public class AudioVisualiser{
    
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
    private SimpleBooleanProperty showcontrollbar = new SimpleBooleanProperty(false);


    /**
     * Standard constructor für den Visualiser
     */
    public AudioVisualiser() throws IOException {


    }

    /**
     * Initialisiert den Visualiser, returnt eine Scene
     * @throws IOException wenn Song nicht gefunden wurde
     *
     **/

    public Scene init() throws IOException {
        HBox main = new HBox();
        VBox root = new VBox();
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
        main.getChildren().addAll(draw);

        root.getChildren().addAll(main,controllbar);
        root.setOnMouseMoved(e-> {
            if(root.getHeight()-e.getSceneY()>150){
                showcontrollbar.set(false);
                root.getChildren().remove(controllbar);
            }else{
                    showcontrollbar.set(true);
                if(!root.getChildren().contains(controllbar)) {

                    root.getChildren().add(controllbar);
                }


            }
        });


        root.setPrefSize(800,600);
        root.setMinWidth(100);

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
                if(showcontrollbar.getValue()){
                    filtercontroller.setHeight(newValue.doubleValue()-150);
                }else{
                    filtercontroller.setHeight(newValue.doubleValue());
                }

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
                main.getChildren().remove(options);
                showoption.set(false);
            }else {
                main.getChildren().add(1,options);
                HBox.setMargin(options,new Insets(0,5,0,0));
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

        showcontrollbar.addListener((a,b,c)-> {
            if(c){

                filtercontroller.setHeight(root.getHeight()-150);
            }else{

                filtercontroller.setHeight(root.getHeight());
            }
        });

       return scene;

    }

}
