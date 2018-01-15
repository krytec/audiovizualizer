package Main;


import Mp3Player.MP3Player;
import Playlist.PlaylistManager;
import Playlist.Track;
import Playlist.Playlist;
import ddf.minim.AudioPlayer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;


/**
 * @author Florian Ortmann, Lea Haugrund
 * Controller class um den Player anzusteuern
 */
public class Controller {

    private MP3Player player;
    private PlaylistManager manager;
    private Playlist playlist;
    private SimpleBooleanProperty playing = new SimpleBooleanProperty(false);
    private boolean pause,repeat,shuffle;

    /**
     *
     * @param player M3Player
     * @param manager PlaylistManager
     * @param playlist aktuelle Playlist
     */
    public Controller(MP3Player player,PlaylistManager manager,Playlist playlist){
        this.player=player;
        this.manager =manager;
        this.playlist=playlist;
    }

    /**
     * Playmethode für den Player
     * @param playlist aktuelle Playlist
     */
    public void play(Playlist playlist){
        playing.set(true);
        if(pause){
            pause=false;
            player.play();
        }else {
            player.play(playlist);
        }
    }

    /**
     * Play methode für ein file
     * @param file Filename des Songs
     */
    public void play(String file){
        playing.set(true);
        player.play(file);
    }

    /**
     * Skip Methode um den nächsten Song abzuspielen
     */
    public void skip(){
        if(pause) {
            playing.set(true);
        }
        player.skip();
    }

    /**
     * Skipback Methode um den vorherigen Song abzuspielen
     */
    public void skipBack(){
        if(pause) {
            playing.set(true);
        }
        player.skipBack();
    }

    /**
     * Setzt repeat auf on
     * @param on true oder false ob Repeat eingeschaltet sein soll
     */
    public void repeat(boolean on){
        repeat=on;
        player.repeat(on);
    }

    /**
     * Setzt shuffle auf on
     * @param on true or false ob Shuffle an sein soll
     */
    public void shuffle(boolean on){
        shuffle=on;
        player.shuffle(on);
    }

    /**
     * pausiert den Song
     */
    public void pause(){
        playing.set(false);
        pause=true;
        player.pause();
    }

    /**
     * Stopt den Mp3Player
     */
    public void stop(){
        playing.set(false);
        pause=false;
        player.stop();
    }

    /**
     * Setzt das Volume des Players
     * @param value Volume, welches später in Dezibel umgewandelt wird
     */
    public void volume(float value){
        player.volume(value);
    }

    /**
     * Getter für eine Liste von Playlists
     * @return eine Liste von Playlists
     */
    public List<Playlist> getPlaylists(){
        return manager.getPlaylists();
    }

    public SimpleObjectProperty<Track> trackProperty(){
        return player.trackProperty();
    }

    public SimpleIntegerProperty integerProperty(){
        return player.lengthProperty();
    }

    public SimpleBooleanProperty isPlaying() {
        return playing;
    }

    public boolean isPause() {
        return pause;
    }

    public Playlist getactPlaylist(){
        return manager.getPlaylist(0);
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public AudioPlayer getAudio() { return player.getAudioplayer();}
}
