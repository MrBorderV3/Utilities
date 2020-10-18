package me.border.utilities.ui.javafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InputBoxController {

    @FXML
    private Label ALERT_DETAILS;

    @FXML
    private TextField inputField;

    @FXML
    public void ok(ActionEvent e){
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.close();
    }

    public void setLabel(String text){
        ALERT_DETAILS.setText(text);
    }

    public String getInput(){
        return inputField.getText();
    }
}
