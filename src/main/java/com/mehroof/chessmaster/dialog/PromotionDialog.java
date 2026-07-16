package com.mehroof.chessmaster.dialog;

import java.util.Optional;

import javafx.scene.control.ChoiceDialog;

public class PromotionDialog {

	public static String show() { 

        ChoiceDialog<String> dialog =
                new ChoiceDialog<>("Queen");

        dialog.setTitle("Pawn Promotion");
        dialog.setHeaderText("Choose a piece");
        dialog.setContentText("Promote to:");

        dialog.getItems().addAll(
                "Queen",
                "Rook",
                "Bishop",
                "Knight"
        );

        Optional<String> result = dialog.showAndWait();

        return result.orElse("Queen");
    }
}