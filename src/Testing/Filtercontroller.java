package Testing;

import GUI.DrawFilter;
import Filter.Filter;
import javafx.scene.canvas.GraphicsContext;

public class Filtercontroller {
    DrawFilter drawFilter;

    public Filtercontroller(DrawFilter drawFilter){
        this.drawFilter = drawFilter;
    }

    public void setFilter(Filter filter){
        drawFilter.setFilter(filter);
    }

    public GraphicsContext getGC(){
        return drawFilter.getGC();
    }

    public void setHeight(double height){
        drawFilter.setHeight(height);
    }

    public void setWidth(double width){
        drawFilter.setWidth(width);
    }
}
