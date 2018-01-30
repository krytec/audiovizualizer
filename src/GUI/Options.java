package GUI;

import CustomButtons.SwitchButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import Filter.Filter;
import Filter.FilterMap;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * @author Florian Ortmann, Lea Haugrund
 * Objekt auf dem die Filteroptionen angezeigt werden
 */
public class Options extends ListView<Filter>{

    private FilterMap filterMap;
    private Filtercontroller filtercontroller;

    /**
     * Standard constructor für die Obtionen
     * @param filterMap
     * @param filtercontroller
     */
    public Options(FilterMap filterMap,Filtercontroller filtercontroller){
       this.filterMap=filterMap;
       this.filtercontroller=filtercontroller;
       filterMap.forEach((s, filter) -> getItems().add(filter));

       this.setCellFactory(new Callback<ListView<Filter>, ListCell<Filter>>() {
           @Override
           public ListCell<Filter> call(ListView<Filter> param) {
               return new FilterCell();
           }
       });

       setPrefSize(150,150);

    }


    /**
     * erstellt die Zellen der Filteroptionen
     */
    private class FilterCell extends ListCell<Filter> {
        private SwitchButton filterbtn;
        private Label filtername;
        private HBox box;
        private Pane pane;

        public FilterCell(){
            super();
            filterbtn = new SwitchButton();
            filtername = new Label();
            box = new HBox();
            box.getChildren().addAll(filtername,filterbtn);
            filterbtn.setAlignment(Pos.CENTER_RIGHT);
            HBox.setMargin(filterbtn,new Insets(0,0,0,20));
            HBox.setMargin(filtername,new Insets(5,0,0,0));
            pane = new Pane();
            filterbtn.switchedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue){
                        filtercontroller.setFilter(getItem());
                    }
                    else{
                        filtercontroller.removeFilter(getItem());
                    }
                }
            });
            pane.getChildren().addAll(box);


        }
        @Override
        protected void updateItem(Filter item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                filtername.setText(item.toString());
                setGraphic(pane);
            }

        }


    }


}
