package GUI;

import CustomButtons.SwitchButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
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
        VBox.setMargin(circlelabel,new Insets(5));
        VBox.setMargin(linelabel,new Insets(5));
        VBox.setMargin(freqlabel,new Insets(5));
        VBox.setMargin(backgroundlabel,new Insets(5));
        VBox.setMargin(circle,new Insets(0,5,10,5));
        VBox.setMargin(line,new Insets(5,5,10,5));
        VBox.setMargin(freq,new Insets(5,5,10,5));
        VBox.setMargin(background,new Insets(5,5,10,5));
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
