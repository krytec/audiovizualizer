package GUI;

import Playlist.Track;
import Mp3Player.Controller;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Songinformation extends VBox{
    private Controller controller;
    private Label titel, album, interpret, laenge;
    private VBox info;
    public Songinformation(Controller controller){
        this.controller = controller;
        init();
    }

    public void init(){

        setStyle("-fx-background-color:#808080;");
        setStyle("-fx-opacity:0.8;");

        titel = new Label("titel");
        album = new Label("album");
        interpret = new Label("interpret");
        laenge = new Label("laenge");

        controller.trackProperty().addListener(new ChangeListener<Track>() {
            @Override
            public void changed(ObservableValue<? extends Track> observable, Track oldValue, Track newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        titel.setText(newValue.getTitel());
                        album.setText(newValue.getAlbum().equals(" ")? "No Album" : newValue.getAlbum());
                        interpret.setText(newValue.getAuthor().equals(" ")? "No Interpret" : newValue.getAuthor());
                        laenge.setText(String.format("%02d Minuten, %02d Sekunden", (int) (newValue.getLength()/100/60),(int) (newValue.getLength()/100)%60));
                    }
                });
            }
        });
        setPadding(new Insets(20,0,0,20));
        VBox.setVgrow(titel, Priority.ALWAYS);
        VBox.setVgrow(album, Priority.ALWAYS);
        VBox.setVgrow(interpret, Priority.ALWAYS);
        VBox.setVgrow(laenge, Priority.ALWAYS);
        getChildren().addAll(titel,album,interpret,laenge);

    }


}
