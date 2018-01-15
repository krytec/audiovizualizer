package GUI;

import CustomButtons.SwitchButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;



public class Options {

    private SimpleBooleanProperty circleb,lineb,backgroundb,freqb;
    private Button close;
    public Options(){

    }

    public VBox init(){
        VBox options = new VBox(5);
        HBox circleSwitch = new HBox(5);
        Label circlelabel = new Label("Circle");
        HBox lineSwitch = new HBox(5);
        Label linelabel = new Label("Line");
        HBox freqSwitch = new HBox(5);
        Label freqlabel = new Label("Freq");
        HBox backgroundSwitch = new HBox(5);
        Label backgroundlabel = new Label("Background");
        close = new Button("Close");


        options.setAlignment(Pos.CENTER);
        SwitchButton circle = new SwitchButton();
        SwitchButton line = new SwitchButton();
        SwitchButton background = new SwitchButton();
        SwitchButton freq = new SwitchButton();
        circleSwitch.getChildren().addAll(circlelabel,circle);
        lineSwitch.getChildren().addAll(linelabel,line);
        freqSwitch.getChildren().addAll(freqlabel,freq);
        backgroundSwitch.getChildren().addAll(backgroundlabel,background);
        circleSwitch.setAlignment(Pos.CENTER);
        lineSwitch.setAlignment(Pos.CENTER);
        freqSwitch.setAlignment(Pos.CENTER);
        backgroundSwitch.setAlignment(Pos.CENTER);
        circleb = circle.switchedProperty();
        lineb = line.switchedProperty();
        backgroundb = background.switchedProperty();
        freqb = freq.switchedProperty();
        options.getChildren().addAll(circleSwitch,freqSwitch,lineSwitch,backgroundSwitch,close);
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

    public Button getClose(){
        return close;
    }
}
