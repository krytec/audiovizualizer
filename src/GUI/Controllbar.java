package GUI;

import Filter.Filter;
import Main.Controller;
import Main.Filtercontroller;
import Main.OptionsController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import Playlist.*;

import java.util.HashMap;


/**
 * @author Florian Ortmann, Lea Haugrund
 * Controllbar für ein Layout
 */
public class Controllbar {
    private Playlist playlist;
    private Button play,shuffle,stop,next,previous,repeat,options;
    private Slider volume,tracklength;
    private VBox controllbar;
    private HBox PlayPauseStop,buttonsandslider;
    private Controller controller;
    private ListView<String> filterview;
    private HashMap<String,Filter> map;
    private Filtercontroller filtercontroller;
    private OptionsController optionsController;

    /**
     * Standard constructor für eine Controllbar
     * @param controller Controller der mit den GUI interagiert
     */
    public Controllbar(Controller controller, HashMap<String,Filter> map, Filtercontroller filtercontroller,OptionsController optionsController){
        this.controller =controller;
        this.map=map;
        this.filtercontroller=filtercontroller;
        this.optionsController=optionsController;
    }

    /**
     * Initialisiert eine Controllbar
     * @param pane das Layout auf dem die Controllbar ist
     */
    public void init(BorderPane pane){
        playlist= controller.getactPlaylist();
        play = new Button("\u23F5");
        play.setPrefSize(50,10);
        play.setOnAction(e -> {

            if(!controller.isPlaying().get()) {
                controller.play(playlist);

            }
            else{
                controller.pause();

            }

        });
        stop = new Button("\u23F9");
        stop.setPrefSize(50,10);
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

        options= new Button("Filter");
        options.setPrefSize(70,10);


        volume = new Slider();
        volume.setMin(0);
        volume.setMax(100);
        volume.setPrefSize(40,10);
        volume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                float volume;
                volume = newValue.floatValue()/100;
                if(controller.isPlaying().getValue()) {
                    controller.volume(volume);
                }

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

        final Filter[] filter = new Filter[1];
        filterview = new ListView<>();
        ComboBox<String> filterComboBox = new ComboBox<String>();

        map.forEach((k,f)-> filterComboBox.getItems().add(k));
        filterComboBox.getSelectionModel().selectFirst();
        filterComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filter[0] = map.get(newValue);
                filtercontroller.setFilter(filter[0]);
            }
        });
        PlayPauseStop = new HBox(5);
        PlayPauseStop.setAlignment(Pos.BASELINE_CENTER);
        PlayPauseStop.setPadding(new Insets(0,0,10,0));
        PlayPauseStop.getChildren().addAll(shuffle,previous,play,stop,next,repeat,options);
        PlayPauseStop.setPrefSize(300,10);
        HBox.setHgrow(play,Priority.ALWAYS);
        HBox.setHgrow(stop,Priority.ALWAYS);
        buttonsandslider = new HBox(5);
        buttonsandslider.getChildren().addAll(PlayPauseStop,volume);
        HBox.setHgrow(PlayPauseStop,Priority.ALWAYS);
        HBox.setHgrow(volume,Priority.ALWAYS);
        buttonsandslider.setAlignment(Pos.CENTER);

        controllbar = new VBox(5);
        controllbar.getChildren().addAll(tracklength,buttonsandslider);

        pane.setBottom(controllbar);

    }

    public Button getOptions(){
        return options;
    }

}
