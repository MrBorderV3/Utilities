package me.border.utilities.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class FieldBox extends Box<String>{

    public FieldBox(String title, String question){
        super(title);
        Label label = new Label(question);
        TextField field = new TextField();
        Button submit = new ButtonBuilder("Submit", e -> {
            var = field.getText();
            window.close();
        }).build();

        BorderPane borderPane = new BorderPane();
        HBox layout = new HBox(8);
        layout.setMinHeight(50);
        layout.getChildren().addAll(label, field);
        layout.setAlignment(Pos.CENTER);
        borderPane.setCenter(layout);
        BorderPane.setAlignment(submit, Pos.BOTTOM_CENTER);
        borderPane.setBottom(submit);
        Scene scene = new Scene(borderPane);
        window.setScene(scene);
    }

    @Override
    public String show(){
        window.showAndWait();

        return var;
    }
}
