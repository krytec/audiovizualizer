package CustomButtons;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @author Florian Ortmann , Lea Haugrund
 * SwitchButton, ein Button der einen On/Off Schalter besitzt
 */
public class SwitchButton extends HBox {
    private Button onoff = new Button();
    private Label on = new Label();
    private SimpleBooleanProperty switched = new SimpleBooleanProperty(false);

    /**
     * Constructor für einen Switch Button
     */
    public SwitchButton(){
        on.setText("off");
        this.getChildren().addAll(on,onoff);
        onoff.setOnAction(e-> switched.set(!switched.getValue()));
        on.setOnMouseClicked(e->switched.set(!switched.getValue()) );

        setWidth(40);

        on.prefHeightProperty().bind(heightProperty());
        onoff.prefHeightProperty().bind(heightProperty());
        on.prefWidthProperty().bind(widthProperty().divide(2));
        onoff.prefWidthProperty().bind(widthProperty().divide(2));

        switched.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    on.setText("on");
                    on.setStyle("-fx-background-color: green;");
                    on.toFront();
                }
                else{
                    on.setText("off");
                    on.setStyle("-fx-background-color: #212121;");
                    onoff.toFront();
                }
            }
        });
    }

    /**
     * switchedProperty getter der eine SimpleBooleanProperty zurückgibt, welche den Status des Buttons ausgibt
     * @return SimpleBooleanProperty true=on,false=off
     */
    public SimpleBooleanProperty switchedProperty() {
        return switched;
    }
}
