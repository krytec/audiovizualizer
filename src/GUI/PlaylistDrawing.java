package GUI;

import Playlist.Playlist;
import Testing.Controller;
import ddf.minim.analysis.FFT;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import Playlist.Track;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class PlaylistDrawing {

    private Controller controller;
    private Playlist playlist;
    private FFT fft;
    public PlaylistDrawing(Controller controller){
        this.controller=controller;
    }

    public void init(VBox box){
        HBox pane = new HBox();
        playlist = controller.getactPlaylist();
        final Canvas canvas = new Canvas(250,250);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        controller.integerProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                fft=controller.getFFT();
                gc.strokeLine(newValue.intValue(),250,newValue.intValue(),(250-(fft.getBand(newValue.intValue())*8)));
            }

        });

        controller.trackProperty().addListener(new ChangeListener<Track>() {
            @Override
            public void changed(ObservableValue<? extends Track> observable, Track oldValue,Track newValue) {

            }
        });
        pane.getChildren().addAll(canvas);
        box.getChildren().addAll(pane);

    }



}
