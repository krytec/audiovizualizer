package GUI;

import Mp3Player.MP3Player;
import Playlist.Playlist;
import Playlist.PlaylistManager;
import Testing.Controller;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.awt.*;

public class Songdrawing {

    private Playlist playlist;
    private Controller controller;

    public Songdrawing(Controller controller){
        this.controller=controller;
    }

    public void init(Pane pane){
        Pane pane1 = new Pane();
        pane1.setMaxWidth(1920);
        pane1.setMaxHeight(800);
        CircleCanvas canvas = new CircleCanvas(controller);
        canvas.heightProperty().bind(pane1.heightProperty());
        canvas.widthProperty().bind(pane1.widthProperty());
        canvas.draw();
        pane1.getChildren().addAll(canvas);
        playlist= controller.getactPlaylist();
        pane.getChildren().addAll(pane1);


    }
}
