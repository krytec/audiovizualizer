package GUI;

import Mp3Player.MP3Player;
import Playlist.Playlist;
import Playlist.PlaylistManager;
import Testing.Controller;
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
        HBox draw = new HBox();
        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        playlist= controller.getactPlaylist();



    }
}
