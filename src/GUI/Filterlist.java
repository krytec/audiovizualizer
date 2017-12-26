package GUI;


import Testing.Filtercontroller;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import Filter.Filter;
import javafx.scene.layout.VBox;
import java.util.HashMap;


public class Filterlist {
    private ListView<String> filterview;
    private HashMap<String,Filter> map;
    private Filtercontroller filtercontroller;

    public Filterlist(HashMap<String,Filter> map, Filtercontroller filtercontroller){
        this.map=map;
        this.filtercontroller=filtercontroller;
    }

    public void init(VBox pane){
        final Filter[] filter = new Filter[1];
        filterview = new ListView<>();
        map.forEach((k,f)-> filterview.getItems().add(k));

        filterview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                filter[0] = map.get(filterview.getSelectionModel().getSelectedItem());
                filtercontroller.setFilter(filter[0]);
            }
        });

        pane.getChildren().add(filterview);
    }
}
