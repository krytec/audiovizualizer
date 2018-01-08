package GUI;

import Testing.Filtercontroller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Options {

    private int height,width;
    private Button size1,size2,size3,ok;
    private Filtercontroller filtercontroller;
    public Options(Filtercontroller filtercontroller){
        this.filtercontroller=filtercontroller;
    }

    public Scene init() {
        VBox options = new VBox(10);

        size1 = new Button("800x600");
        size1.setPrefSize(100, 10);
        size1.setOnAction(e -> {
            width = 800;
            height = 500;
            filtercontroller.setHeight(height);
            filtercontroller.setWidth(width);
        });

        size2 = new Button("1600x900");
        size2.setPrefSize(100, 10);
        size2.setOnAction(e -> {
            width = 1600;
            height = 800;
            filtercontroller.setHeight(height);
            filtercontroller.setWidth(width);
        });
        size3 = new Button("1920x1080");
        size3.setPrefSize(100, 10);
        size3.setOnAction(e -> {
            width = 1920;
            height = 980;
            filtercontroller.setHeight(height);
            filtercontroller.setWidth(width);
        });
        ok = new Button("Ok");
        ok.setPrefSize(50,10);


        options.setPadding(new Insets(10));
        options.setAlignment(Pos.CENTER);
        options.getChildren().addAll(size1,size2,size3,ok);
        VBox.setVgrow(size1, Priority.ALWAYS);
        VBox.setVgrow(size2, Priority.ALWAYS);
        VBox.setVgrow(size3, Priority.ALWAYS);
        VBox.setVgrow(ok, Priority.ALWAYS);

        Scene option = new Scene(options,200,200);

        return option;
    }

    public Button getOk(){
        return ok;
    }
    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }
}
