package com.mehroof.chessmaster.board;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import com.mehroof.chessmaster.controller.GameController;
import com.mehroof.chessmaster.view.StatusBar;
import javafx.scene.layout.VBox;

public class BoardFrame extends GridPane {

    private ChessBoard chessBoard;
    private StatusBar statusBar;

    public BoardFrame() {

        chessBoard = new ChessBoard();
        statusBar = new StatusBar();

        new GameController(
                chessBoard,
                statusBar
        );
        
        createLayout();

    }
  
    private void createLayout() {

        VBox topSection = new VBox();

        topSection.setAlignment(Pos.CENTER);

        topSection.setSpacing(10);

        topSection.getChildren().addAll(
                statusBar,
                createTopCoordinates()
        );

        add(topSection, 1, 0);

        add(createLeftCoordinates(), 0, 1);

        add(chessBoard, 1, 1);

        setAlignment(Pos.CENTER);
    }
    
    private GridPane createTopCoordinates() {

        GridPane top = new GridPane();

        for (int column = 0; column < BoardConstants.BOARD_SIZE; column++) {

            Label label = new Label(BoardCoordinates.FILES[column]);

            label.setPrefWidth(BoardConstants.SQUARE_SIZE);
            label.setAlignment(Pos.CENTER);

            top.add(label, column, 0);

            GridPane.setHalignment(label, HPos.CENTER);
        }

        top.setAlignment(Pos.CENTER);

        return top;
    }
    
    private GridPane createLeftCoordinates() {

        GridPane left = new GridPane();

        for (int row = 0; row < BoardConstants.BOARD_SIZE; row++) {

            Label label = new Label(String.valueOf(8 - row));

            label.setPrefHeight(BoardConstants.SQUARE_SIZE);
            label.setAlignment(Pos.CENTER);

            left.add(label, 0, row);

            GridPane.setHalignment(label, HPos.CENTER);
        }

        left.setAlignment(Pos.CENTER);

        return left;
    }
    
    public StatusBar getStatusBar() {

        return statusBar;

    }

}