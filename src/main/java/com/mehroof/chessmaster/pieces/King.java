package com.mehroof.chessmaster.pieces;

import java.util.ArrayList;
import java.util.List;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;

public class King extends Piece {

    public King(boolean white) {
        super(white);
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "♔" : "♚";
    }
    
    @Override
    public List<Move> getLegalMoves(
            BoardState boardState,
            int row,
            int column) {

        List<Move> moves = new ArrayList<>();

        int[] rowOffset = {
                -1, -1, -1,
                 0,  0,
                 1,  1,  1
        };

        int[] columnOffset = {
                -1, 0, 1,
                -1, 1,
                -1, 0, 1
        };

        for (int i = 0; i < 8; i++) {

            int newRow = row + rowOffset[i];
            int newColumn = column + columnOffset[i];

            if (!boardState.isInsideBoard(newRow, newColumn)) {
                continue;
            }

            Piece target =
                    boardState.getSquare(newRow, newColumn).getPiece();

            if (target == null) {

                moves.add(new Move(newRow, newColumn));

            } else if (target.isWhite() != isWhite()) {

                moves.add(new Move(newRow, newColumn));

            }
        }

        return moves;
    }
    
}