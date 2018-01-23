package GUI;

import Filter.*;
import Mp3Player.PlayerFassade;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import Playlist.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawFilter extends Group{

    private PlayerFassade playerFassade;
    private List<Filter> filterlist = new ArrayList<Filter>();
    private GraphicsContext gc;

    private Songinformation songinformation;
    private boolean showing = false;
    private Pane pane;
    private Canvas canvas;
    private BufferedImage img;
    private Color background;
    private Color notbackground;

    public DrawFilter(PlayerFassade playerFassade){
        this.playerFassade = playerFassade;
        try {
            init();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init()throws IOException{


        pane = new Pane();



        songinformation = new Songinformation(playerFassade);
        canvas = new Canvas(0,0);
        gc = canvas.getGraphicsContext2D();
        final double[] midx = {gc.getCanvas().getWidth() / 2};
        final double[] midy = {gc.getCanvas().getHeight() / 2};
        final double[] oldX = {midx[0], midx[0], midx[0], midx[0]};
        final double[] oldY = {midy[0], midy[0], midy[0], midy[0]};
        Circle circle = new Circle(midx[0], midy[0],gc.getCanvas().getHeight()/4);
        img = ImageIO.read(new File("default.png"));
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
                if(playerFassade.isPause()) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                }

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
                if(playerFassade.isPause()) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                }

            }
        });


        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());



        circle.setOnMouseClicked(e -> {
            if(showing==false){
                showing = true;
                pane.getChildren().addAll(songinformation);
            }else{
                showing = false;
                pane.getChildren().remove(1);
            }

        });

      playerFassade.integerProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gc.setFill(background);
                        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                        gc.setStroke(notbackground);
                        gc.setFill(notbackground);

                        filterlist.forEach(e-> {


                                if(e instanceof  ColorFilter && filterlist.size()<3){
                                e.drawFilter();
                                e = filterlist.get(0);
                                }
                                if (e instanceof CircleFilter) {
                                    oldX[0] = ((CircleFilter) e).getOldx();
                                    oldY[0] = ((CircleFilter) e).getOldy();
                                    ((CircleFilter) e).drawFilter(oldX, oldY);
                                } else {

                                        e.drawFilter();

                                }

                        });

                    }
                });

            }
        });

        playerFassade.trackProperty().addListener(new ChangeListener<Track>() {
            @Override
            public void changed(ObservableValue<? extends Track> observable, Track oldValue, Track newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            img = newValue.getImage();
                            background = averageBackGroundColor(img);
                            notbackground = complementaryColor(img);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        gc.setFill(background);

                        gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
                        try {
                            Image image = SwingFXUtils.toFXImage(newValue.getImage(),null);
                            circle.setFill(new ImagePattern(image));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });


        getChildren().addAll(canvas,pane);
        pane.getChildren().addAll(circle);

    }

    public void setFilter(Filter filter){
        filterlist.add(filter);
    }

    public void removeFilter(Filter filter){
        filterlist.remove(filter);
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

    private Color averageBackGroundColor(BufferedImage img){
        int x = img.getWidth();
        int y = img.getHeight();

        long r=0,g=0,b = 0;
        for(int i=0;i<x;i++){
            for (int j=0;j<y;j++){
                java.awt.Color pixel = new java.awt.Color(img.getRGB(i,j));
                r += pixel.getRed();
                g += pixel.getGreen();
                b += pixel.getBlue();

            }
        }

        double a = x*y;
        return Color.rgb((int) (r/a),(int) (g/a),(int) (b/a));


    }
    private Color complementaryColor(BufferedImage img){
        int x = img.getWidth();
        int y = img.getHeight();

        long r=0,g=0,b = 0;
        for(int i=0;i<x;i++){
            for (int j=0;j<y;j++){
                java.awt.Color pixel = new java.awt.Color(img.getRGB(i,j));
                r += pixel.getRed();
                g += pixel.getGreen();
                b += pixel.getBlue();

            }
        }

        double a = x*y;
        return Color.rgb(255-(int) (r/a),255-(int) (g/a),255-(int) (b/a));

    }

}

