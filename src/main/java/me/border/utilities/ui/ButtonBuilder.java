package me.border.utilities.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ButtonBuilder {

    private String buttonName;
    private EventHandler<ActionEvent> eventHandler;
    private int[] constraints;

    public ButtonBuilder(){
    }

    public ButtonBuilder(String name){
        setButtonName(name);
    }

    public ButtonBuilder(String name, EventHandler<ActionEvent> value){
        this(name);
        setOnAction(value);
    }

    public ButtonBuilder(String name, EventHandler<ActionEvent> value, int column, int row){
        this(name, value);
        setConstraints(new int[]{column, row});
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public void setOnAction(EventHandler<ActionEvent> value){
        this.eventHandler = value;
    }

    public void setConstraints(int[] constraints){
        this.constraints = constraints;
    }

    public Button build(){
        Button button = new Button(buttonName);
        button.setOnAction(eventHandler);

        return button;
    }

    public Button buildGrid(){
        Button button = build();
        GridPane.setConstraints(button, constraints[0], constraints[1]);

        return button;
    }
}
