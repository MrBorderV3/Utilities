package me.border.utilities.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ConfirmBox extends Box<Boolean> {

    public ConfirmBox(String title, String description){
        super(title);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            var = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            var = false;
            window.close();
        });

        Label label = new Label(description);
        label.setAlignment(Pos.TOP_CENTER);

        HBox layout = new HBox(8);
        layout.getChildren().addAll(yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        BorderPane.setAlignment(label, Pos.TOP_CENTER);
        borderPane.setTop(label);
        borderPane.setCenter(layout);

        Scene scene = new Scene(borderPane);
        window.setScene(scene);
    }

    @Override
    public Boolean show(){
        window.showAndWait();

        return var;
    }
}
