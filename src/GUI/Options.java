package GUI;

import CustomButtons.SwitchButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class Options {

    private SimpleBooleanProperty circleb,lineb,backgroundb,freqb;
    public Options(){

    }

    public VBox init(){
        VBox options = new VBox(5);
        HBox content = new HBox (5);
        VBox names = new VBox(20);
        VBox buttons = new VBox(5);

        Label circlelabel = new Label("Circle");

        Label linelabel = new Label("Line");

        Label freqlabel = new Label("Freq");

        Label backgroundlabel = new Label("Background");

        SwitchButton circle = new SwitchButton();
        SwitchButton line = new SwitchButton();
        SwitchButton background = new SwitchButton();
        SwitchButton freq = new SwitchButton();

        options.setAlignment(Pos.CENTER);
        options.setId("optionBox");
        names.setId("nameBox");
        buttons.setId("buttonBox");

        circleb = circle.switchedProperty();
        lineb = line.switchedProperty();
        backgroundb = background.switchedProperty();
        freqb = freq.switchedProperty();

        buttons.getChildren().addAll(circle,line,freq,background);
        names.getChildren().addAll(circlelabel,linelabel,freqlabel,backgroundlabel);
        content.getChildren().addAll(names,buttons);
        options.getChildren().addAll(content);

        return options;


    }

    public SimpleBooleanProperty backgroundbProperty() {
        return backgroundb;
    }

    public SimpleBooleanProperty circlebProperty() {
        return circleb;
    }

    public SimpleBooleanProperty freqbProperty() {
        return freqb;
    }

    public SimpleBooleanProperty linebProperty() {
        return lineb;
    }


}
