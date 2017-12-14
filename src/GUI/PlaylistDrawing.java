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
                gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                gc.setStroke(Color.WHITE);
                fft = new FFT(controller.getAudio().bufferSize(), controller.getAudio().sampleRate());
                beatDetect = new BeatDetect();
                beatDetect.detect(controller.getAudio().mix);
                fft.forward(controller.getAudio().mix);




                double radius = 250;
                double points = fft.specSize();
                double slice = 2 * Math.PI / points;


               for (int i = 0; i < points; i++) {



                    radius=250;
                    gc.setStroke(Color.WHITE);
                    double angle = slice * i;
                    double buffer =Math.abs(fft.getBand(i)*20+fft.getAverageBandWidth(i));
                    double x = midx + radius * Math.cos(angle);
                    double y = midy + radius * Math.sin(angle);
                    double newX = x + (buffer * Math.cos(angle));
                    double newY = y + (buffer * Math.sin(angle));


                    System.out.println("midx: " + midx);
                    System.out.println("midy: " + midy);
                    System.out.println("Buffer: " + buffer);
                    System.out.println("y: " + y);
                    System.out.println("x: " + x);


                    gc.strokeLine(x, y, newX, newY);

                    gc.setStroke(Color.rgb(((int)Math.abs(buffer)>255) ? (int) Math.abs(buffer)/255: (int) Math.abs(buffer),255,255));
                    gc.stroke();
                    gc.strokeLine(x,y,newX,newY);


                }
         /**       for (int i = 1; i < controller.getAudio().sampleRate()-1; i++) {
                    gc.strokeLine(midx / 2 + i, midy + 400 + controller.getAudio().mix.get(i-1) * 20, midx / 2 + i + 1, midy + 400 + controller.getAudio().mix.get(i ) * 20);
                } **/

                for (int i = 0; i < points; i++) {
                    radius=250;
                    gc.setStroke(Color.WHITE);
                    double angle = slice * i;
                    double buffer=0;
                    for (int j=0;j<fft.specSize();j++) {
                        buffer += fft.getBand(i) * 20;
                    }

                    buffer = buffer/fft.specSize();
                    double x = midx + radius * Math.cos(angle);
                    double y = midy + radius * Math.sin(angle);
                    double newX = x + (buffer * Math.cos(angle));
                    double newY = y + (buffer * Math.sin(angle));
                    gc.setFill(Color.rgb(255,((buffer>255) ?(int) buffer/255 : (int)buffer),255));
                    gc.fill();
                    gc.fillOval(newX,newY,(buffer>50) ? buffer/20 : buffer, (buffer>50) ? buffer/20 : buffer);
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
