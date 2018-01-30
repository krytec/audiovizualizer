package Exceptions;

/**
 * @author Florian Ortmann , Lea Haugrund
 * Song nicht gefunden Exception falls das File nicht geladen werden kann
 */
public class SongNotFoundException extends Exception {

    /**
     * Standard Constructor für SongNotFoundException
     */
    public SongNotFoundException(){
        super();
    }

    /**
     * Constructor für SongNotFoundException
     * @param msg Message die Ausgegeben werden soll
     */
    public SongNotFoundException(String msg){
        super(msg);
    }


}
