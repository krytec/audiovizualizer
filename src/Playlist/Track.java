package Playlist;

import com.mpatric.mp3agic.Mp3File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Track Class für die Erstellung neuer Tracks
 * @author Florian Ortmann,Lea Haugrund
 *
 */
public class Track {
    String file,titel,album,interpret;
    Mp3File mp3;
    byte[] image;
    int length;

    /**
     * Constructor eines Tracks
     * @param file Filename um den Ort des Files zu finden
     *
     */

    public Track(String file, String titel, String album, String interpret, byte[] image, int length){
        this.file=file;
        this.album=album;
        this.titel=titel;
        this.interpret=interpret;
        this.image=image;
        this.length=length;
    }

    /**
     * getter für den Titel des Songs
     * @return Titel des Songs, falls Id3v1tag vorhanden, ansonsten den Filename
     */
    public String getTitel(){

        return titel;
    }

    /**
     * getter für File
     * @return File
     */
    public String getFile() {
        return file;
    }

    /**
     * getter für Album
     * @return Album des Songs, wenn keins vorhanden " "
     */
    public String getAlbum(){
        return album;
    }
    /**
     * getter für Author
     * @return Author des Songs, wenn keiner vorhanden " "
     */
    public String getAuthor(){
        return interpret;
    }
    /**
     * getter für Image
     * @return Image des Songs, wenn keins vorhanden " "
     */
    public BufferedImage getImage() throws IOException {
        if(image!=null){

            BufferedImage bi = ImageIO.read(new ByteArrayInputStream(image));
            return bi;
        }
        else{
            BufferedImage bi = ImageIO.read(new File("default.png"));
            return bi;
        }

    }

    /**
     * Länge des Songs
     * @return wenn keine Länge vorhanden 0, ansonsten die Länge des Songs in Sekunden
     */
    public int getLength(){

            return length/10;

    }
}
