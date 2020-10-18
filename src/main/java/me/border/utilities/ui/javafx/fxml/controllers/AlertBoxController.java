package me.border.utilities.ui.javafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class AlertBoxController {

    @FXML
    public Label ALERT_DETAILS;

    @FXML
    public void ok(ActionEvent e){
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.close();
    }

    public void setLabel(String text){
        ALERT_DETAILS.setText(text);
    }
}
