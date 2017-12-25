package GUI;

import Filter.CircleFilter;
import Filter.LineFilter;
import Playlist.Playlist;
import Testing.Controller;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import Playlist.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawFilter {

    private Controller controller;
    private Playlist playlist;
    private CircleFilter circleFilter;
    private LineFilter lineFilter;
    private Songinformation songinformation;
    private boolean showing = false;
    public DrawFilter(Controller controller){
        this.controller=controller;
    }

    public void init(VBox box)throws IOException{

        Group root = new Group();
        Pane pane = new Pane();
        pane.setMaxWidth(1920);
        pane.setMaxHeight(800);
        SimpleDoubleProperty width = new SimpleDoubleProperty(pane.getWidth());
        SimpleDoubleProperty height = new SimpleDoubleProperty(pane.getHeight());

        songinformation = new Songinformation(controller);
        playlist = controller.getactPlaylist();
        final Canvas canvas = new Canvas(1600,800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double midx =  gc.getCanvas().getWidth()/2;
        double midy =  gc.getCanvas().getHeight()/2;
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        circleFilter = new CircleFilter(controller,gc);
        lineFilter = new LineFilter(controller,gc);
        final double[] oldX = {midx,midx,midx,midx};
        final double[] oldY = {midy,midy,midy,midy};

        Circle circle = new Circle(midx,midy,250);
        BufferedImage img = ImageIO.read(new File("default.png"));
        Image im1 = SwingFXUtils.toFXImage(img,null);
        circle.setFill(new ImagePattern(im1));
        VBox info = songinformation.init();

        circle.setOnMouseClicked(e -> {
            if(showing==false){
                showing = true;
                pane.getChildren().addAll(info);
            }else{
                showing = false;
                pane.getChildren().remove(1);
            }

        });

      controller.integerProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                circleFilter.drawFilter(oldX,oldY);
                oldX[0] = circleFilter.getOldx();
                oldY[0] = circleFilter.getOldy();
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


        root.getChildren().addAll(canvas,pane);
        pane.getChildren().addAll(circle);
        box.getChildren().addAll(root);
    }
}

