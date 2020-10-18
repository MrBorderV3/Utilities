package me.border.utilities.ui.javafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

public class ConfirmBoxController {

    private AtomicBoolean bool = new AtomicBoolean();

    public boolean getBool(){
        return bool.get();
    }

    @FXML
    public Label ALERT_DETAILS;

    public void setLabel(String text){
        ALERT_DETAILS.setText(text);
    }

    @FXML
    public void yes(ActionEvent e){
        bool.set(true);
        ((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
    }

    @FXML
    public void no(ActionEvent e){
        bool.set(false);
        ((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
    }
}
