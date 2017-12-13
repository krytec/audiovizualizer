package GUI;

import Playlist.Playlist;
import Testing.Controller;
import ddf.minim.analysis.BeatDetect;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class PlaylistDrawing {

    private Controller controller;
    private Playlist playlist;
    private FFT fft;
    private BeatDetect beatDetect;
    public PlaylistDrawing(Controller controller){
        this.controller=controller;
    }

    public void init(VBox box){
        int w = 3;
        HBox pane = new HBox();
        playlist = controller.getactPlaylist();
        final Canvas canvas = new Canvas(800,300);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        controller.integerProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
                gc.setStroke(Color.WHITE);
                fft= new FFT(controller.getAudio().bufferSize(),controller.getAudio().sampleRate());
                beatDetect = new BeatDetect();
                beatDetect.detect(controller.getAudio().mix);
                fft.forward(controller.getAudio().mix);
                System.out.println(fft.specSize());
                for (int i =0;i<fft.specSize()/2;i++) {
                    gc.setStroke(Color.WHITE);

                   // gc.strokeLine(i,250,i,(250-(fft.getBand(i)*10)));
                    double x = fft.getFreq(i)+fft.getBand(i)*8;
                    double y = (fft.getBand(i))*16;
                    System.out.println(y);
                    gc.strokeLine(i*w,250,w-2,250-y);
                    gc.fillRect(i*w,250,w-2,);

                   // gc.fillOval(gc.getCanvas().getWidth()/3,10,200+fft.getBand(i)*50,200+fft.getBand(i)*50);


                }

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
