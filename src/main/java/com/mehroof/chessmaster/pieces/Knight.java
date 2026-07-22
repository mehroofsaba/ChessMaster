package com.mehroof.chessmaster.pieces;

import java.util.ArrayList;
import java.util.List;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;

public class Knight extends Piece {

    public Knight(boolean white) {
        super(white);
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "♘" : "♞";
    }

    @Override
    public List<Move> generateMoves(
            BoardState boardState,
            int row,
            int column) {

        List<Move> moves = new ArrayList<>();

        int[] rowMoves = {
                -2, -2,
                -1, -1,
                 1,  1,
                 2,  2
        };

        int[] columnMoves = {
                -1,  1,
                -2,  2,
                -2,  2,
                -1,  1
        };
        
        for (int i = 0; i < 8; i++) {

            int newRow = row + rowMoves[i];
            int newColumn = column + columnMoves[i];

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