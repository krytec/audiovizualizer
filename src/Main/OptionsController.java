package Main;

import GUI.Options;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.VBox;

public class OptionsController {

    private Options options;

    public OptionsController(Options options){
        this.options=options;
    }

    public SimpleBooleanProperty circle(){
        return options.circlebProperty();
}

    public SimpleBooleanProperty line(){
        return options.linebProperty();
    }
    public SimpleBooleanProperty freq(){
        return options.freqbProperty();
    }
    public SimpleBooleanProperty backround(){
        return options.backgroundbProperty();
    }

    public VBox options(){
        return options.init();
    }
}
