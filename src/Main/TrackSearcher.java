package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TrackSearcher Class um Tracks auf der Festplatte des Users zu finden.
 * Fügt alle Tracks in eine playlist.M3U Datei ein, welche später vom Playlistmanager geladen wird.
 * @author Florian Ortmann, Lea Haugrund
 */
public class TrackSearcher {
    static BufferedWriter bw;

    /**
     * Tracksearcher Constructor
     */
    public TrackSearcher() {
    }


    public static void main(String[] args) throws IOException {
        walk("C:\\users\\");

    }

    /**
     * Walk funktion, welche das Filesystem des angegeben Verzeichnis durchläuft und nach ".mp3" Dateien sucht,
     * dabei geht es rekursiv die Verzeichnisse durch und fügt alle Files des aktuellen Verzeichnisses in ein Array hinzu, wenn das File auch ein Verzeichnis ist,
     * dann ruft es walk rekursiv mit dem neuen Verzeichnis aus.
     * @param path der Path der gelaufen werden soll
     * @throws IOException
     */
    public static void walk(String path) throws IOException {
        File playlist = new File("playlist.M3U");
        File root = new File(path);
        File[] list = root.listFiles();
        if(list != null) {
            for (int i = 0; i < list.length; i++) {
                bw = new BufferedWriter(new FileWriter(playlist, playlist.exists()));
                String f = list[i].toString();
                if (list[i].isDirectory()) {
                    walk(list[i].toString());
                }
                if (f.endsWith(".mp3")) {
                    try {
                        bw.write(f);
                        bw.newLine();
                        bw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
            bw.close();

        }

    }
}

