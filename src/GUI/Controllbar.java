package GUI;

import Testing.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import Playlist.*;


/**
 * @author Florian Ortmann, Lea Haugrund
 * Controllbar für ein Layout
 */
public class Controllbar {
    private Playlist playlist;
    private Button play,shuffle,stop,next,previous,repeat;
    private Slider volume,tracklength;
    private VBox controllbar;
    private HBox PlayPauseStop,buttonsandslider;
    private Controller controller;

    /**
     * Standard constructor für eine Controllbar
     * @param controller Controller der mit den GUI interagiert
     */
    public Controllbar(Controller controller){
        this.controller =controller;

    }

    /**
     * Initialisiert eine Controllbar
     * @param pane das Layout auf dem die Controllbar ist
     */
    public void init(VBox pane){
        playlist= controller.getactPlaylist();
        play = new Button("\u23F5");
        play.setPrefSize(35,10);
        play.setOnAction(e -> {

            if(!controller.isPlaying().get()) {
                controller.play(playlist);

            }
            else{
                controller.pause();

            }

        });
        stop = new Button("\u23F9");
        stop.setPrefSize(35,10);
        stop.setOnAction(e -> controller.stop());
        next = new Button ("\u23ED");
        next.setOnAction(e -> controller.skip());
        next.setPrefSize(50,10);
        previous = new Button("\u23EE");
        previous.setOnAction(e -> controller.skipBack());
        previous.setPrefSize(50,10);
        shuffle = new Button("\uD83D\uDD00");
        shuffle.setOnAction(e -> {
            if(controller.isShuffle()){
                controller.shuffle(false);
                shuffle.setTooltip(new Tooltip("aus"));

            }
            else{
                controller.shuffle(true);
                shuffle.setTooltip(new Tooltip("an"));
            }
        });
        shuffle.setPrefSize(50,10);
        repeat = new Button("\u21BA");
        repeat.setOnAction(e -> {
            if(controller.isRepeat()){
            controller.repeat(false);}
            else{
                controller.repeat(true);
            }
        });
        repeat.setPrefSize(50,10);
        volume = new Slider();
        volume.setMin(0);
        volume.setMax(100);
        volume.setPrefSize(75,10);

        volume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                float volume;
                volume = newValue.floatValue()/100;
                controller.volume(volume);
            }
        });
        volume.setShowTickLabels(true);
        tracklength = new Slider();
        tracklength.setMin(0);
        tracklength.setMax(5);

        controller.trackProperty().addListener(new ChangeListener<Track>() {
            @Override
            public void changed(ObservableValue<? extends Track> observable, Track oldValue, Track newValue) {
                tracklength.setMax(newValue.getLength());
            }
        });
        controller.integerProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                tracklength.setValue(newValue.intValue());
            }
        });

        controller.isPlaying().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue.booleanValue()){
                    play.setText("\u23F8");
                }else{
                    play.setText("\u23F5");
                }
            }
        });
        PlayPauseStop = new HBox(5);
        PlayPauseStop.setAlignment(Pos.BASELINE_CENTER);
        PlayPauseStop.setPadding(new Insets(0,0,10,0));
        PlayPauseStop.getChildren().addAll(shuffle,previous,play,stop,next,repeat);
        PlayPauseStop.setPrefSize(300,10);
        HBox.setHgrow(PlayPauseStop, Priority.ALWAYS);
        HBox.setHgrow(play,Priority.ALWAYS);
        HBox.setHgrow(stop,Priority.ALWAYS);
        buttonsandslider = new HBox(5);
        buttonsandslider.getChildren().addAll(PlayPauseStop,volume);
        HBox.setHgrow(PlayPauseStop,Priority.ALWAYS);
        HBox.setHgrow(volume,Priority.ALWAYS);
        buttonsandslider.setAlignment(Pos.CENTER);

        controllbar = new VBox(5);
        controllbar.setStyle("-fx-background-color:#212121;");
        controllbar.getChildren().addAll(tracklength,buttonsandslider);
        pane.getChildren().addAll(controllbar);
    }



}
