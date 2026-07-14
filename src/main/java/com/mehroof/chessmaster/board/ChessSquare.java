package com.mehroof.chessmaster.board;

import javafx.scene.layout.StackPane;
import com.mehroof.chessmaster.board.BoardConstants;
import com.mehroof.chessmaster.pieces.Piece;
import javafx.scene.control.Label;

public class ChessSquare extends StackPane {

    private final int row;
    private final int column;
    private Piece piece;
    public ChessSquare(int row, int column) {

        this.row = row;
        this.column = column; 

        setPrefSize(
                BoardConstants.SQUARE_SIZE,
                BoardConstants.SQUARE_SIZE
        );

        updateColor();

    }

    private void updateColor() {

        if ((row + column) % 2 == 0) {

            setStyle("-fx-background-color:" + BoardTheme.LIGHT + ";");

        } else {

            setStyle("-fx-background-color:" + BoardTheme.DARK + ";");

        }

    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setPiece(Piece piece) {
    	
    	this.piece = piece;

        updatePiece();
    }
    
    public Piece getPiece() {

        return piece;

    }

   


    private void updatePiece() {

        getChildren().clear();

        if (piece != null) {

            Label pieceLabel = new Label(piece.getSymbol());

            pieceLabel.setStyle("-fx-font-size:40px;");

            getChildren().add(pieceLabel);
        }
    }
    
    public void select() {

        setStyle(
                "-fx-background-color:" +
                (((row + column) % 2 == 0)
                        ? BoardTheme.LIGHT
                        : BoardTheme.DARK)
                + ";" +
                "-fx-border-color: gold;" +
                "-fx-border-width: 4;"
        );

    }
    public void deselect() {

        updateColor();

        updatePiece();

    }
    
    public void highlightMove() {

        setStyle(
                "-fx-background-color:" +
                (((row + column) % 2 == 0)
                        ? BoardTheme.LIGHT
                        : BoardTheme.DARK)
                + ";" +
                "-fx-border-color: limegreen;" +
                "-fx-border-width: 4;"
        );

    }

    public void removeHighlight() {

        updateColor();

        updatePiece();

    }
}