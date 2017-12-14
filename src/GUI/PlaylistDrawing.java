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
        final Canvas canvas = new Canvas(1920,800);
        canvas.setOnMouseClicked(e-> {if(!controller.isPlaying().getValue()){controller.play(controller.getactPlaylist());}
        else controller.stop();});
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double midx =  gc.getCanvas().getWidth()/2;
        double midy =  gc.getCanvas().getHeight()/2;
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        beatDetect = new BeatDetect(1024,44100);
        final double[] oldX = {midx};
        final double[] oldY = {midy};


        controller.integerProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {


                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                gc.setStroke(Color.WHITE);
                fft = new FFT(controller.getAudio().bufferSize(), controller.getAudio().sampleRate());
                beatDetect.detect(controller.getAudio().mix);
                fft.forward(controller.getAudio().mix);


                double radius;
                double points = fft.specSize();
                double slice = 2 * Math.PI / points;

                float spread = map(450, 0, (float) points, 1, 21.5f);
                float r;
                /**
                 for (int i = 0; i < points; i++) {
                 radius=150;
                 gc.setStroke(Color.WHITE);
                 double angle = slice * i;
                 double buffer =Math.abs(fft.getBand(i)*4);
                 double x = midx + radius * Math.cos(angle);
                 double y = midy + radius * Math.sin(angle);
                 double newX = x + (buffer * Math.cos(angle));
                 double newY = y + (buffer * Math.sin(angle));

                 System.out.println("midx: " + midx);
                 System.out.println("midy: " + midy);
                 System.out.println("Buffer: " + buffer);
                 System.out.println("y: " + y);
                 System.out.println("x: " + x);

                 if(beatDetect.isOnset()){
                 gc.setFill(Color.rgb(0,0,(int) (Math.random()*255)));
                 gc.fill();
                 gc.fillOval(midx-radius,midy-radius,radius*2,radius*2);
                 }
                 if(beatDetect.isSnare()){
                 gc.setFill(Color.rgb(0,(int) (Math.random()*255),0));
                 gc.fill();
                 gc.fillOval(midx-radius,midy-radius,radius*2,radius*2);
                 }
                 if(beatDetect.isKick()){
                 gc.setFill(Color.rgb((int) (Math.random()*255),0,0));
                 gc.fill();
                 gc.fillOval(midx-radius,midy-radius,radius*2,radius*2);
                 }
                 if(beatDetect.isHat()){
                 gc.setFill(Color.rgb((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255)));
                 gc.fill();
                 gc.fillOval(midx-radius,midy-radius,radius*2,radius*2);
                 }
                 gc.strokeLine(x, y, newX, newY);

                 }
                 **/
                for (int i = 0; i < points; i += spread) {
                    double buffer = Math.abs(fft.getBand(i) * 4);
                    float rgb = map(fft.getFreq(i), 0, 256, 0, 360) * 2;

                    gc.setStroke(Color.hsb(rgb, 1, 1, 1));
                    gc.stroke();
                    gc.strokeOval(i + 720 - (7 / 2), 740 - (buffer / 2), 7, buffer);
                    gc.setFill(Color.hsb(rgb, 1, 1));
                    gc.fill();
                    gc.fillOval(i + 720 - (7 / 2), 740 - (buffer / 2), 7, buffer);
                }


                for (int i = 0; i < points; i += spread) {
                    r = map(fft.getFreq(i), 0, 1, 250, 255);
                    double angle = slice * i;
                    double buffer = 0;
                    for (int j = 0; j < fft.specSize(); j++) {
                        buffer += fft.getFreq(i) * 20;
                    }

                    buffer = buffer / fft.specSize();
                    double x = midx + r * Math.cos(angle);
                    double y = midy + r * Math.sin(angle);
                    double newX = x + (buffer * Math.cos(angle));
                    double newY = y + (buffer * Math.sin(angle));

                    gc.fillOval(newX, newY, (buffer > 5) ? buffer / 5 : buffer, (buffer > 5) ? buffer / 5 : buffer);
                }
                /** Halbkreis!
                 *
                 *
                oldX[0] = midx-map(fft.getBand(0), 0, 1, 250, 255)*Math.cos(0);;
                oldY[0] = midy-map(fft.getBand(0), 0, 1, 250, 255)*Math.sin(0);;

                for (int i = 0; i < 360; i++) {
                    int radmultiplikation = i/2;
                    slice=2*Math.PI/360;
                    r = map(fft.getBand(i), 0, 1, 250, 255);
                    double x2 = midx - r * Math.cos(slice*radmultiplikation);
                    double y2 = midy - r * Math.sin(slice*radmultiplikation);
                    gc.strokeLine(oldX[0], oldY[0], x2, y2);
                    oldX[0] = x2;
                    oldY[0] = y2;


                }
                 **/
                /** Kreis!
                 *
                 */
                for (int i = 0; i < points; i++) {

                    r = map(fft.getBand(i), 0, 1, 250, 255);
                    double x2 = midx - r * Math.cos(slice*i);
                    double y2 = midy - r * Math.sin(slice*i);
                    gc.strokeLine(oldX[0], oldY[0], x2, y2);
                    oldX[0] = x2;
                    oldY[0] = y2;


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



    static public final float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }
}

