package me.border.utilities.ui.javafx;

import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class Box<S> {

    protected Stage window = new Stage();
    protected S var = null;

    public Box(String title){
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
    }

    public S show(){
        window.showAndWait();

        return var;
    }
}
