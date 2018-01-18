package Filter;

import Mp3Player.Controller;
import javafx.scene.canvas.GraphicsContext;


import java.util.HashMap;

public class FilterMap extends HashMap<String,Filter>{

        private Controller controller;
        private GraphicsContext gc;
    public FilterMap(Controller controller, GraphicsContext gc){
        super();
        this.gc=gc;
        this.controller=controller;
        init();



    }

    public void init(){
        CircleFilter circleFilter = new CircleFilter("Circle",controller,gc);
        LineFilter lineFilter = new LineFilter("Line",controller,gc);
        Freqfilter freqfilter = new Freqfilter("Freq",controller,gc);
        BackgroundFilter backgroundFilter = new BackgroundFilter("Background",controller,gc);
        SpiralFilter spiralFilter = new SpiralFilter("Spiral",controller,gc);

        put(circleFilter.toString(),circleFilter);
        put(lineFilter.toString(),lineFilter);
        put(freqfilter.toString(),freqfilter);
        put(backgroundFilter.toString(),backgroundFilter);
        put(spiralFilter.toString(),spiralFilter);
    }


}
