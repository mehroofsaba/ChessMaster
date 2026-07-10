package com.mehroof.chessmaster.pieces;

import java.util.ArrayList;
import java.util.List;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;
import com.mehroof.chessmaster.pieces.Piece;

public class Bishop extends Piece {

    public Bishop(boolean white) {
        super(white);
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "♗" : "♝";
    }
    
    @Override
    public List<Move> getLegalMoves(
            BoardState boardState,
            int row,
            int column) {

        List<Move> moves = new ArrayList<>();

        int currentRow = row - 1;
        int currentColumn = column - 1;

        while (boardState.isInsideBoard(currentRow, currentColumn)) {

            Piece target =
                    boardState.getSquare(currentRow, currentColumn).getPiece();

            if (target == null) {

                moves.add(new Move(currentRow, currentColumn));

            } else {

                if (target.isWhite() != isWhite()) {

                    moves.add(new Move(currentRow, currentColumn));
                    
                        }

                break;
            }
            
            currentRow--;
            currentColumn--;
        }
        
        currentRow = row - 1;
        currentColumn = column + 1;

        while (boardState.isInsideBoard(currentRow, currentColumn)) {

            Piece target =
                    boardState.getSquare(currentRow, currentColumn).getPiece();

            if (target == null) {

                moves.add(new Move(currentRow, currentColumn));

            } else {

                if (target.isWhite() != isWhite()) {

                    moves.add(new Move(currentRow, currentColumn));

                }

                break;
            }

            currentRow--;
            currentColumn++;
        }

        currentRow = row + 1;
        currentColumn = column - 1;

        while (boardState.isInsideBoard(currentRow, currentColumn)) {

        	Piece target =
        	        boardState.getSquare(currentRow, currentColumn).getPiece();

        	System.out.println(
        	    "Checking square "
        	    + currentRow + "," + currentColumn
        	    + " target = "
        	    + (target == null ? "EMPTY"
        	        : target.getClass().getSimpleName())
        	);
            if (target == null) {

                moves.add(new Move(currentRow, currentColumn));

            } else {

                if (target.isWhite() != isWhite()) {

                    moves.add(new Move(currentRow, currentColumn));

                }

                break;
            }

            currentRow++;
            currentColumn--;
        }
        
        currentRow = row + 1;
        currentColumn = column + 1;

        while (boardState.isInsideBoard(currentRow, currentColumn)) {

            Piece target =
                    boardState.getSquare(currentRow, currentColumn).getPiece();

            if (target == null) {

                moves.add(new Move(currentRow, currentColumn));

            } else {

                if (target.isWhite() != isWhite()) {

                    moves.add(new Move(currentRow, currentColumn));

                }

                break;
            }

            currentRow++;
            currentColumn++;
        }
        
        return moves;
    }
}
