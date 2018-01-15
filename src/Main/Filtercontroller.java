package Main;

import GUI.DrawFilter;
import Filter.Filter;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

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

    public Pane getPane(){
        return drawFilter.getPane();
    }
}
