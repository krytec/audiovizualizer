package GUI;

import Playlist.Track;
import Testing.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Songinformation {
    private Controller controller;
    private Label titel, album, interpret, laenge;
    private VBox info;
    public Songinformation(Controller controller){
        this.controller = controller;
    }

    public VBox init(){
        info = new VBox(5);
        info.setStyle("-fx-background-color:#808080;");
        info.setStyle("-fx-opacity:0.8;");

        titel = new Label("titel");
        album = new Label("album");
        interpret = new Label("interpret");
        laenge = new Label("laenge");

        controller.trackProperty().addListener(new ChangeListener<Track>() {
            @Override
            public void changed(ObservableValue<? extends Track> observable, Track oldValue, Track newValue) {
                titel.setText(newValue.getTitel());
                album.setText(newValue.getAlbum().equals(" ")? "No Album" : newValue.getAlbum());
                interpret.setText(newValue.getAuthor().equals(" ")? "No Interpret" : newValue.getAuthor());
                laenge.setText(String.valueOf(newValue.getLength()));
            }
        });
        info.getChildren().addAll(titel,album,interpret,laenge);
        return info;
    }


}
