package GUI;

import Filter.CircleFilter;
import Filter.LineFilter;
import Playlist.Playlist;
import Testing.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import Playlist.*;

public class DrawFilter {

    private Controller controller;
    private Playlist playlist;
    private CircleFilter circleFilter;
    private LineFilter lineFilter;

    public DrawFilter(Controller controller){
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
        circleFilter = new CircleFilter(controller,gc);
        lineFilter = new LineFilter(controller,gc);
        final double[] oldX = {midx,midx,midx,midx};
        final double[] oldY = {midy,midy,midy,midy};

        controller.integerProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                circleFilter.drawFilter(oldX,oldY);
                lineFilter.drawFilter();
            }
        });

        controller.trackProperty().addListener(new ChangeListener<Track>() {
            @Override
            public void changed(ObservableValue<? extends Track> observable, Track oldValue, Track newValue) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
            }
        });

        pane.getChildren().addAll(canvas);
        box.getChildren().add(pane);
    }
}

