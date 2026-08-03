package com.mehroof.chessmaster.rules;

import java.util.List;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;

public class CheckmateDetector {

    private final CheckDetector checkDetector =
            new CheckDetector();

    private final MoveGenerator moveGenerator =
            new MoveGenerator();

    public boolean isCheckmate(
            BoardState board,
            boolean whiteTurn) {

        if (!checkDetector.isKingInCheck(
                board,
                whiteTurn)) {

            return false;
        }

        List<Move> moves =
                moveGenerator.generateMoves(
                        board,
                        whiteTurn
                );

        return moves.isEmpty();
    }
}