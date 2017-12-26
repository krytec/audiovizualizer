package Filter;

import Testing.Controller;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;

public class FilterMap {
    private GraphicsContext gc;
    private Controller controller;

    public FilterMap(Controller controller, GraphicsContext gc){
        this.controller=controller;
        this.gc=gc;
    }

    public HashMap<String,Filter> init(){
        HashMap<String,Filter> filterMap = new HashMap<String,Filter>();
        CircleFilter circleFilter = new CircleFilter(controller,gc);
        LineFilter lineFilter = new LineFilter(controller,gc);
        Freqfilter freqfilter = new Freqfilter(controller,gc);
        filterMap.put("Circle",circleFilter);
        filterMap.put("Line",lineFilter);
        filterMap.put("Frequencie",freqfilter);


        return filterMap;

    }
}
