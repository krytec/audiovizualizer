package Playlist;


import java.util.ArrayList;
import java.util.List;

/**
 * Playlist Class für das Erstellen von Playlists
 * @author Florian Ortmann, Lea Haugrund
 */
public class Playlist {
    private String name;
    int i = 0;
    List<Track> playlist = new ArrayList<>();

    /**
     * Constructor für eine Playlist, welche eine Arraylist bestitzt
     * @param name Name der Playlist
     */
    public Playlist(String name){this.name=name; }

    /**
     * fügt einen Track der Playlist hinzu
     * @param t Track der hinzugefügt wird
     */
    public void add(Track t){
        playlist.add(i,t);
        i++;
    }

    /**
     *
     * @param name Name des Tracks
     * @return den gesuchten Track der Playlist
     */
    public Track getTrack(String name){
        for (Track track:playlist
             ) { if(track.getFile().equalsIgnoreCase(name)){
                 return track;
        } if(track.getTitel().equalsIgnoreCase(name)){
                 return track;
        }
            
        }
        return null;
    }

    /**
     *
     * @param name Name des Tracks
     * @return die Position des Tracks in der Playlist
     */
    public int getTrackNo(String name){
        int trackNo=0;
        for (int i=0; i< playlist.size();i++){
            if(playlist.get(i).getFile().equalsIgnoreCase(name)){
                trackNo=i;
            }
        }
        return trackNo;
    }

    /**
     *
     * @param i Stelle der Playlist
     * @return den Track an der Stelle i der Playlist
     */
    public Track getTrack(int i){
        return playlist.get(i);
    }

    /**
     *
     * @return Die Länge der Playlist
     */
    public int getLength(){
        return playlist.size();
    }

    /**
     *
     * @return der Name der Playlist
     */
    public String getName(){return name;}


}
