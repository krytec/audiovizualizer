package Filter;

import Mp3Player.PlayerFassade;
import javafx.scene.canvas.GraphicsContext;


import java.util.HashMap;

/**
 * @author Florian Ortmann ,Lea Haugrund
 * FilterMap als HashMap
 */
public class FilterMap extends HashMap<String,Filter>{

    private PlayerFassade playerFassade;
    private GraphicsContext gc;

    /**
     * Constructor für eine FilterMap, welche die verschiedenen Filter enthält
     * @param playerFassade Zur Ansteuerung des Mp3Players via Minim
     * @param gc Graphiccontentext des Canvas
     */
    public FilterMap(PlayerFassade playerFassade, GraphicsContext gc){
        super();
        this.gc=gc;
        this.playerFassade = playerFassade;
        init();



    }

    /**
     * Initialisiert eine FilterMap mit gegebenen Filtern
     */
    public void init(){
        CircleFilter circleFilter = new CircleFilter("Circle", playerFassade,gc);
        LineFilter lineFilter = new LineFilter("Line", playerFassade,gc);
        Freqfilter freqfilter = new Freqfilter("Freq", playerFassade,gc);
        BackgroundFilter backgroundFilter = new BackgroundFilter("Background", playerFassade,gc);
        SpiralFilter spiralFilter = new SpiralFilter("Spiral", playerFassade,gc);
        ColorFilter colorFilter = new ColorFilter("RGB",playerFassade,gc);
        put(colorFilter.toString(),colorFilter);
        put(circleFilter.toString(),circleFilter);
        put(lineFilter.toString(),lineFilter);
        put(freqfilter.toString(),freqfilter);
        put(backgroundFilter.toString(),backgroundFilter);
        put(spiralFilter.toString(),spiralFilter);

    }


}
