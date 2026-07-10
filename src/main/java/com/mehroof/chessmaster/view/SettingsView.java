package com.mehroof.chessmaster.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class SettingsView {

    public Scene createScene() {

        Label label = new Label("Settings");

        label.setStyle("-fx-font-size:32px; -fx-text-fill:white;");

        BorderPane root = new BorderPane();

        root.setCenter(label);

        BorderPane.setAlignment(label, Pos.CENTER);

        Scene scene = new Scene(root,900,700);

        scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
        );

        return scene;
    }
}