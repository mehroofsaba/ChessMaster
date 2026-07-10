package com.mehroof.chessmaster.pieces;

import java.util.ArrayList;
import java.util.List;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;
import com.mehroof.chessmaster.pieces.Piece;

public class Rook extends Piece {

    public Rook(boolean white) {
        super(white);
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "♖" : "♜";
    }
    
    
    @Override
    public List<Move> getLegalMoves(
            BoardState boardState,
            int row,
            int column) {

        List<Move> moves = new ArrayList<>();

        int currentRow = row - 1;

        while (boardState.isInsideBoard(currentRow, column)) {

            Piece target =
                    boardState.getSquare(currentRow, column).getPiece();

            if (target == null) {

                moves.add(new Move(currentRow, column));

            } else {

                if (target.isWhite() != isWhite()) {

                    moves.add(new Move(currentRow, column));

                }

                break;
            }

            currentRow--;
        }
       
        
        currentRow = row + 1;

        currentRow = row + 1;

        while (boardState.isInsideBoard(currentRow, column)) {

            Piece target =
                    boardState.getSquare(currentRow, column).getPiece();

            if (target == null) {

                moves.add(new Move(currentRow, column));

            } else {

                if (target.isWhite() != isWhite()) {

                    moves.add(new Move(currentRow, column));

                }

                break;
            }

            currentRow++;
        }   

        int currentColumn = column - 1;

        while (boardState.isInsideBoard(row, currentColumn)) {

            Piece target =
                    boardState.getSquare(row, currentColumn).getPiece();

            if (target == null) {

                moves.add(new Move(row, currentColumn));

            } else {

                if (target.isWhite() != isWhite()) {

                    moves.add(new Move(row, currentColumn));

                }

                break;
            }

            currentColumn--;
        }
          
            
        currentColumn = column + 1;

        while (boardState.isInsideBoard(row, currentColumn)) {

            Piece target =
                    boardState.getSquare(row, currentColumn).getPiece();

            if (target == null) {

                moves.add(new Move(row, currentColumn));

            } else {

                if (target.isWhite() != isWhite()) {

                    moves.add(new Move(row, currentColumn));

                }

                break;
            }

            currentColumn++;
        }

        return moves;
    }
    
}