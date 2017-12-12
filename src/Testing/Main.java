package Testing;

import GUI.Controllbar;
import GUI.PlaylistDrawing;
import Mp3Player.MP3Player;
import Playlist.Playlist;
import Playlist.PlaylistManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Controller controller;
    private PlaylistManager manager;
    private MP3Player player;
    private Playlist playlist;
    private PlaylistDrawing draw;
    private Controllbar controllbar;

    @Override
    public void start(Stage primaryStage) throws Exception{
        player = new MP3Player();
        manager = new PlaylistManager();
        playlist = new Playlist("default");
        playlist=manager.createTrack(playlist);
        controller = new Controller(player,manager,playlist);
        controllbar = new Controllbar(controller);
        draw = new PlaylistDrawing(controller);
        VBox root = new VBox();
        draw.init(root);
        controllbar.init(root);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
