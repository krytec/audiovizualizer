package Expections;

/**
 * @author Florian Ortmann , Lea Haugrund
 * NoSongException falls der Audioplayer keinen Song geladen hat
 */
public class NoSongException extends Exception {

    /**
     * Constructor für NoSongException
     * @param msg Message die ausgegeben wird
     */
    public NoSongException(String msg){
        System.err.println(msg);
    }
}
