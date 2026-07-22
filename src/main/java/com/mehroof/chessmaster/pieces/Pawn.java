package com.mehroof.chessmaster.pieces;
import java.util.ArrayList;
import java.util.List;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;

public class Pawn extends Piece {

    public Pawn(boolean white) {
        super(white);
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "♙" : "♟";
        
    }
    
    @Override
    public List<Move> generateMoves(
            BoardState boardState,
            int row,
            int column) {

        List<Move> moves = new ArrayList<>();

        int direction;

        if (isWhite()) {
            direction = -1;
        } else {
            direction = 1;
        }

        int nextRow = row + direction;
        if (boardState.isInsideBoard(nextRow, column)) {

            if (boardState.getSquare(nextRow, column).getPiece() == null) {

                // Normal one-square move
                moves.add(new Move(nextRow, column));

                // Starting position
                int startRow = isWhite() ? 6 : 1;

                if (row == startRow) {

                    int doubleRow = row + direction * 2;

                    if (boardState.isInsideBoard(doubleRow, column)
                            && boardState.getSquare(doubleRow, column).getPiece() == null) {

                        moves.add(new Move(doubleRow, column));
                    }
                }
            }
        }
        
     // Diagonal captures
        int[] captureColumns = {column - 1, column + 1};

        for (int captureColumn : captureColumns) {

            if (boardState.isInsideBoard(nextRow, captureColumn)) {

                Piece target =
                        boardState.getSquare(nextRow, captureColumn).getPiece();

                if (target != null && target.isWhite() != isWhite()) {

                    moves.add(new Move(nextRow, captureColumn));

                }
            }
        }
        
        return moves;
    }
}