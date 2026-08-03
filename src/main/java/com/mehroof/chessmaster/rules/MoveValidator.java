package com.mehroof.chessmaster.rules;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;

public class MoveValidator {

    private final CheckDetector detector =
            new CheckDetector();

    public boolean isLegalMove(
            BoardState board,
            Move move,
            boolean whiteTurn) {

        BoardState copied = board.copy();

        copied.movePiece(
                move.getFromRow(),
                move.getFromColumn(),
                move.getToRow(),
                move.getToColumn()
        );

        return !detector.isKingInCheck(
                copied,
                whiteTurn
        );
    }
}