package me.border.utilities.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AlertBox extends Box<Void> {

    public AlertBox(String title, String description){
        super(title);
        Label label = new Label(description);

        Button button = new Button("OK");

        button.setOnAction(e -> window.close());

        VBox layout = new VBox(8);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
    }
}
