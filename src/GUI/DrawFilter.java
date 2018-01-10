package GUI;

import Filter.*;
import Playlist.Playlist;
import Testing.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import Playlist.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import Filter.FilterMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawFilter {

    private Controller controller;
    private Playlist playlist;
    private Filter filter;
    private GraphicsContext gc;
    private CircleFilter circleFilter;
    private LineFilter lineFilter;
    private Freqfilter freqfilter;
    private Songinformation songinformation;
    private boolean showing = false;
    private FilterMap filterMap;
    private double width,height;
    private Canvas canvas;
    public DrawFilter(Controller controller){
        this.controller=controller;

    }

    public void init(BorderPane box)throws IOException{

        Group root = new Group();
        Pane pane = new Pane();



        songinformation = new Songinformation(controller);
        playlist = controller.getactPlaylist();
        canvas = new Canvas(box.getWidth(),box.getHeight()-150);
        gc = canvas.getGraphicsContext2D();
        final double[] midx = {gc.getCanvas().getWidth() / 2};
        final double[] midy = {gc.getCanvas().getHeight() / 2};
        final double[] oldX = {midx[0], midx[0], midx[0], midx[0]};
        final double[] oldY = {midy[0], midy[0], midy[0], midy[0]};
        Circle circle = new Circle(midx[0], midy[0],gc.getCanvas().getHeight()/4);
        BufferedImage img = ImageIO.read(new File("default.png"));
        Image im1 = SwingFXUtils.toFXImage(img,null);
        circle.setFill(new ImagePattern(im1));

        canvas.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

               circle.setRadius(canvas.getWidth()>canvas.getHeight()?canvas.getHeight()/4:canvas.getWidth()/4);
               circle.setCenterY(canvas.getHeight()/2);
               circle.setCenterX(canvas.getWidth()/2);
                midx[0] =canvas.getWidth()/2;
                midy[0] =canvas.getHeight()/2;
                oldX[0]=midx[0];
                oldY[0]=midy[0];

            }
        });
        canvas.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                circle.setRadius(canvas.getWidth()>canvas.getHeight()?canvas.getHeight()/4:canvas.getWidth()/4);
                circle.setCenterY(canvas.getHeight()/2);
                circle.setCenterX(canvas.getWidth()/2);
                midx[0] =canvas.getWidth()/2;
                midy[0] =canvas.getHeight()/2;
                oldX[0]=midx[0];
                oldY[0]=midy[0];

            }
        });
        filterMap = new FilterMap(controller,gc);

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        circleFilter = new CircleFilter(controller,gc);
        lineFilter = new LineFilter(controller,gc);
        freqfilter = new Freqfilter(controller,gc);



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
                if(filter == null){
                    filter=lineFilter;

                }
                else {
                    if(filter instanceof CircleFilter){
                        ((CircleFilter) filter).drawFilter(oldX,oldY);
                        oldX[0] = ((CircleFilter)filter).getOldx();
                        oldY[0] = ((CircleFilter)filter).getOldy();
                    }
                    if(filter instanceof  LineFilter){
                        canvas.minWidth(100);
                        canvas.minHeight(100);
                    }
                    filter.drawFilter();
                }



            }
        });

        controller.trackProperty().addListener(new ChangeListener<Track>() {
            @Override
            public void changed(ObservableValue<? extends Track> observable, Track oldValue, Track newValue) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
                try {
                    Image image = SwingFXUtils.toFXImage(newValue.getImage(),null);
                    circle.setFill(new ImagePattern(image));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        root.getChildren().addAll(canvas,pane);
        pane.getChildren().addAll(circle);
        box.setCenter(root);
    }

    public void setFilter(Filter filter){
        this.filter=filter;
    }

    public GraphicsContext getGC(){
        return gc;
    }

    public void setWidth(double width){
        canvas.setWidth(width);
    }

    public void setHeight(double height){
        canvas.setHeight(height);
    }
}

