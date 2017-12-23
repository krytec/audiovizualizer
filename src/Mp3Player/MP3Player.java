package Mp3Player;
import Playlist.Track;
import Playlist.Playlist;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Mp3Player Class
 * @author Florian Ortmann, Lea Haugrund
 * Mp3Player welcher Observable ist, für den Timer und den TrackTask
 * Dient zum Abspielen von Songs und der aktualisierung einzelner Label und Bilder
 */
public class MP3Player{


    private Playlist actPlaylist;
    private Minim minim = new SimpleMinim(true);
    private AudioPlayer audioplayer;
    private Timer timer;
    private Boolean shuffle = false;
    private Boolean playing;
    private Boolean repeat = false;
    private SimpleIntegerProperty length = new SimpleIntegerProperty(0);
    private int track = 0;
    private Track actTrack;
    private Timeline timeline;
    private SimpleObjectProperty<Track> actTrackp = new SimpleObjectProperty<>(actTrack);
    private BeatDetect beat;
    private FFT fft;

    /**
     * Constructor für den Mp3Player, gibt Labels und Image mit, um diese dann zu updaten
     *
     */
    public MP3Player(){

    }

    /**
     * Spielt ein File ab, ein neuer Timer wird erstellt, der mit einem TrackTask scheduled wird.
     * Der Timer hat eine Sekunde delay und aktualisiert jede Sekunde, dadurch wird die Länge des Songs in Sekunden jede Sekunde abgefragt und die aktuelle Zeit berechnet.
     * Am Ende der Play methode wird updateLabel() aufgerufen, damit die Szene aktualisiert wird
     * @param filename
     */
    public void play(String filename){
        actTrackp.set(actPlaylist.getTrack(filename));
        track= actPlaylist.getTrackNo(filename);
        System.out.println(actTrack.getLength());
        playing=true;
        audioplayer = minim.loadFile(filename);
        audioplayer.setGain(-12);
        timer = new Timer();
        length.set(0);
        TrackTask task = new TrackTask();
        timer.schedule(task,10,10);
        audioplayer.play();


    }

    /**
     * Spielt eine Playlist ab
     * @param playlist
     */
    public void play(Playlist playlist){
        length.set(0);
        playing=true;
        actPlaylist = playlist;
        actTrack = actPlaylist.getTrack(track);
        play(actTrack.getFile());

    }

    /**
     * Wenn der Player spielt, wird er pausiert. Der Timer hält an.
     */
    public void pause(){
        if(playing) {
            timer.cancel();
            playing = false;
            audioplayer.pause();

        }
    }

    /**
     * Spielt den Track ab der grade geladen ist, unbenutzt für das Projekt
     */
    public void play(){
        timer = new Timer();
        TrackTask task = new TrackTask();
        timer.schedule(task, 100, 100);
        playing=true;
        System.out.print(actTrack.getFile());
        audioplayer.play();


    }

    /**
     * Setzt die Balance des Players
     * @param value float Variable für die Balance
     */
    public void balance(float value){
        audioplayer.setBalance(value);
    }

    /**
     * Setzt das Volume des Players
     * @param value float Variable die das Volume angibt
     */
    public void volume(float value){
        value = (float) ( 10* (Math.log10(value)));
        audioplayer.setGain(value);
    }

    /**
     * Stoppt das Abspielen des Song
     */
    public void stop(){
        timer.cancel();
        minim.stop();

    }

    /**
     * Skipped einen Song nach vorne, wenn es auf Shuffle ist wird ein beliebiger Song ausgewählt, bei repeat der selbe Song nochmal
     * Der Timer wird dabei immer wieder gecanceld und neu gesetzt in der Playmethode, damit man weiß das Lied ist vorbei
     */
    public void skip(){
        if(audioplayer!=null) {
            if (repeat) {
                minim.stop();
                timer.cancel();
                play(actPlaylist.getTrack(track).getFile());


            }
            if (shuffle) {
                track = (int) (Math.random() * actPlaylist.getLength()-1);
                minim.stop();
                timer.cancel();
                play(actPlaylist.getTrack(track).getFile());

            }
            else if (track < actPlaylist.getLength()-1) {
                minim.stop();
                timer.cancel();
                play(actPlaylist.getTrack(++track).getFile());

            } else {
                track = 0;
                minim.stop();
                timer.cancel();
                play(actPlaylist.getTrack(track).getFile());

            }
        }
    }

    /**
     * Geht einen Song zurück, wenn auf Repeat wird der gleiche Song gespielt, ansonsten geht er einen Song zurück
     */
    public void skipBack(){
        if(audioplayer!=null) {
            if (repeat) {
                minim.stop();
                timer.cancel();
                play(actPlaylist.getTrack(track).getFile());

            }
            if (track > 0) {
                minim.stop();
                timer.cancel();
                play(actPlaylist.getTrack(--track).getFile());

            } else {
                track = actPlaylist.getLength() - 1;
                minim.stop();
                timer.cancel();
                play(actPlaylist.getTrack(track--).getFile());

            }
        }
    }

    /**
     * Schaltet shuffle ein oder aus
     * @param on shuffle ein oder aus
     */
    public void shuffle(Boolean on){
        shuffle=on;
    }

    /**
     * Schaltet repeat ein oder aus
     * @param on repeat ein oder aus
     */
    public void repeat(Boolean on){
        repeat=on;
    }


    /**
     * Bisher umbenutze Methode um in dem Song um Sec zu skippen
     * @param sec die Sekunden die geskipped werden sollen
     */
    public void skipSec(int sec){
        length.set(length.get()+sec);
        audioplayer.skip(sec*1000);

    }


    /**
     * getter für Cover
     * @return das Cover als Buffered Image
     * @throws IOException
     */
    public BufferedImage getCover() throws IOException { return actPlaylist.getTrack(0).getImage();}

    /**
     * Getter für Playing, zeigt an ob der Player grade spielt
     * @return true wenn der Player spielt, ansonsten false
     */
    public Boolean isPlaying(){ return playing; }

    /**
     * getter für Shuffle
     * @return true wenn Shuffle an ist, ansosnten false
     */
    public Boolean isShuffle(){ return shuffle;}

    /**
     * getter für Repeat
     * @return true wenn Repeat an ist, ansonsten false
     */
    public Boolean isRepeat(){ return repeat;}

    /**
     * getter für die Länge des aktuellen Tracks
     * @return Länge in ms
     */
    public int getLength(){return actPlaylist.getTrack(track).getLength();}

    /**
     * getter für den aktuellen Track
     * @return der aktuell gespielte Track
     */
    public Track getActTrack(){
        return actTrack;
    }


    /**
     * Timertask um die aktuelle Spielzeit zu ermitteln, wenn der Track vorbei ist, dann wird der nächste Track mithilfe der skip() Funktion aufgerufen
     */
    private class TrackTask extends TimerTask{
        @Override
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if(length.get() < (audioplayer.getMetaData().length()/10)){
                        length.set(length.get()+1);
                    }
                    else{
                        skip();
                    }
                }
            });

        }
    }


    /**
     *
     * @return SimpleIntegerProperty aktuelle Länge des Songs
     */
    public  SimpleIntegerProperty lengthProperty(){
        return this.length;
    }

    /**
     *
     * @return SimpleObjectProperty Track
     */
    public  SimpleObjectProperty<Track> trackProperty(){
        return this.actTrackp;
    }

    public int getBeat(){
        int beats = 0;
        if(isPlaying()){
            beat = new BeatDetect();
            beat.detect(audioplayer.mix);
            if(beat.isOnset()){
                beats=80;
            }else{
                beats=20;
            }
        }
        return beats;
    }

    public FFT getFFT(){
        fft=new FFT(audioplayer.bufferSize(),audioplayer.sampleRate());
        fft.forward(audioplayer.mix);
        return fft;

    }

    public AudioPlayer getAudioplayer() {
        return audioplayer;
    }
}
