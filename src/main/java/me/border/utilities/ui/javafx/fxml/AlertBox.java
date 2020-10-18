package me.border.utilities.ui.javafx.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.border.utilities.ui.javafx.fxml.controllers.AlertBoxController;
import me.border.utilities.utils.URLUtils;

import java.io.IOException;

public class AlertBox {

    private AlertBox(){ }

    public static void showAlert(String alertMessage, String windowTitle) {
        try {
            Stage window = new Stage();
            window.setTitle(windowTitle);
            window.getIcons().add(new Image("/assets/icon.png"));
            window.initStyle(StageStyle.TRANSPARENT);
            window.setResizable(false);
            window.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(URLUtils.getURL("/view/AlertBox.fxml"));
            Parent root = loader.load();
            window.setScene(new Scene(root));
            AlertBoxController alertBoxController = loader.getController();
            alertBoxController.setLabel(alertMessage);
            window.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
