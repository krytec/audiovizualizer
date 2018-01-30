package Playlist;

import Exceptions.SongNotFoundException;
import com.mpatric.mp3agic.Mp3File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * PlayListManager class um die Playlist zu Laden, neue Playlists hinzuzufügen, ändern usw.
 * @author Florian Ortmann, Lea Haugrund
 */
public class PlaylistManager {
    private List<Playlist> playlists;
    private Playlist list;

    /**
     * Constructor eines PlaylistManagers, hat eine Arraylist mit Playlists
     */
    public PlaylistManager(){
        playlists = new ArrayList<>();
    }

    /**
     * CreateTrack Methode, welche aus jeder Zeile der playlist.M3U ein Track macht und diesen einer Playlist hinzufügt
     * @param playlist Playlist in der die Tracks gespeichert werden sollen
     * @return Die fertige Playlist mit allen Tracks die sich im User Verzeichnis befinden.
     * @throws IOException
     */
    public Playlist createTrack(Playlist playlist)throws SongNotFoundException,IOException{
        BufferedReader br = new BufferedReader(new FileReader("playlist.M3U"));
        String f,titel,album,intepret;
        byte[] b;
        int length;

        while((f=br.readLine())!=null) {
            Mp3File mp3=null;
            try {
                    mp3 = new Mp3File(f);
            } catch (Exception e) {
                throw new SongNotFoundException("Song nicht Gefunden");
            }
            if(mp3!=null){
                if(mp3.hasId3v2Tag()){
                    b = mp3.getId3v2Tag().getAlbumImage();
                }else{b = null;}
                if(mp3.hasId3v1Tag()) {
                    if (mp3.getId3v1Tag().getTitle() != null) {
                        titel = mp3.getId3v1Tag().getTitle();
                    
                    if (Objects.equals(titel, "")) {
                        titel = f.split(Pattern.quote("\\"))[f.split(Pattern.quote("\\")).length - 1];

                    }
                    }
                    else{
                        titel = f.split(Pattern.quote("\\"))[f.split(Pattern.quote("\\")).length-1];
                    }
                    if(mp3.getId3v1Tag().getAlbum()!=null){
                        album=mp3.getId3v1Tag().getAlbum();
                    }else{ album = " ";}
                    if(mp3.getId3v1Tag().getArtist()!=null){
                        intepret=mp3.getId3v1Tag().getArtist();}else{ intepret=" ";
                    }
                }else{
                    titel = f.split(Pattern.quote("\\"))[f.split(Pattern.quote("\\")).length-1];
                    intepret= " ";
                    album = " ";
                }
                length = (int) mp3.getLengthInMilliseconds();
                Track t = new Track(f,titel,album,intepret,b,length);
                playlist.add(t);
            }else{
                Track t = new Track(f,f," "," ",null,0);
                playlist.add(t);
            }



        }
        playlists.add(playlist);
        return playlist;
    }

    /**
     * Sucht eine Playlist nach Namen
     * @param name der Name der Playlist
     * @return die gesuchte Playlist
     */
    public Playlist findPlaylist(String name){
        list = new Playlist("");
        for (Playlist playlist:playlists
             ) { if(playlist.getName().equalsIgnoreCase(name)){
                 list = playlist;
        }
        }
        return list;
    }

    /**
     * Fügt eine Playlist der Liste von Playlists hinzu
     * @param actPlaylist die Playlist die hinzugefügt wird
     */
    public void setPlaylist(Playlist actPlaylist){
        playlists.add(actPlaylist);
    System.out.print("playlist" + actPlaylist.getName() +"created");}

    /**
     * Entfernt die Playlist
     * @param actPlaylist die aktuelle Playlist
     */
    public void deletePlaylist(Playlist actPlaylist){
        playlists.remove(actPlaylist);
    }

    /**
     * Updatet die Playlist
     * @param actPlaylist aktuelle Playlist
     */
    public void updatePlaylist(Playlist actPlaylist){
        deletePlaylist(actPlaylist);
        setPlaylist(actPlaylist);
    }

    /**
     * Gibt die Playlist an der Stelle i aus
     * @param i die Stelle der Playlist
     * @return die gesuchte Playlist an der Stelle i
     */
    public Playlist getPlaylist(int i){
        return playlists.get(i);
    }

    /**
     *
     * @return gibt die Liste der Playlists aus
     */
    public List<Playlist> getPlaylists(){
        return playlists;
    }



}
