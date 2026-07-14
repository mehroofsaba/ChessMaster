package com.mehroof.chessmaster.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GameOverDialog {

    public static void show(String title, String message) {

        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }

}