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
import javafx.scene.shape.Circle;
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
        final Canvas canvas = new Canvas(1920,1080);
        canvas.setOnMouseClicked(e-> {if(!controller.isPlaying().getValue()){controller.play(controller.getactPlaylist());}
        else controller.stop();});
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double midx =  gc.getCanvas().getWidth()/2;
        double midy =  gc.getCanvas().getHeight()/2;

        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
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
                double radius = 200;
                double points = fft.specSize()/3;
                double slice = 2*Math.PI/points;
                System.out.println("bs: "+(controller.getAudio().bufferSize()-1));

                for (int i =0;i<points;i++) {
                    gc.setStroke(Color.WHITE);
                    double angle = slice * i;
                    double buffer = fft.getBand(i)*8;
                    if(buffer<1){
                        buffer*=2;
                    }
                    if(buffer>3){
                        buffer=buffer*1/3;
                    }
                    double x = midx + radius * Math.cos(angle);
                    double y = midy + radius * Math.sin(angle);
                    double newX =x+(buffer*Math.cos(angle));
                    double newY =y+(buffer*Math.sin(angle));





                    System.out.println("midx: "+midx);
                    System.out.println("midy: "+midy);
                    System.out.println("Buffer: "+Math.abs(fft.getBand(i)));
                    System.out.println("y: "+y);
                    System.out.println("x: "+x);
                    gc.strokeLine(x,y,newX,newY);



                }
                for (int i=0;i< controller.getAudio().sampleRate()-1;i++){
                    gc.strokeLine(midx/2+i,midy+400+controller.getAudio().mix.get(i)*20,midx/2+i+1,midy+400+controller.getAudio().mix.get(i+1)*20);
                }

            }

        });

        controller.trackProperty().addListener(new ChangeListener<Track>() {
            @Override
            public void changed(ObservableValue<? extends Track> observable, Track oldValue,Track newValue) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
            }
        });
        pane.getChildren().addAll(canvas);
        box.getChildren().addAll(pane);

    }



}
