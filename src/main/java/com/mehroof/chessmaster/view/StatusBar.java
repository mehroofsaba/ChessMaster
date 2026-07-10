package com.mehroof.chessmaster.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatusBar extends VBox {

    private Label titleLabel;
    private Label statusLabel;

    public StatusBar() {

        titleLabel = new Label("♔ ChessMaster");

        statusLabel = new Label("White's Turn");

        titleLabel.setStyle(
                "-fx-font-size:24px;" +
                "-fx-font-weight:bold;"
        );

        statusLabel.setStyle(
                "-fx-font-size:18px;"
        );

        setAlignment(Pos.CENTER);

        setSpacing(10);

        getChildren().addAll(
                titleLabel,
                statusLabel
        );
    }

    public void setStatus(String text) {

        statusLabel.setText(text);

    }
    
    public void showWhiteTurn() {

        setStatus("White's Turn");

    }

    public void showBlackTurn() {

        setStatus("Black's Turn");

    }

}