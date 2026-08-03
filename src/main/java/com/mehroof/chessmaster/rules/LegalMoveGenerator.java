package com.mehroof.chessmaster.rules;

import java.util.ArrayList;
import java.util.List;

import com.mehroof.chessmaster.board.ChessSquare;
import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;
import com.mehroof.chessmaster.pieces.Piece;

public class LegalMoveGenerator {

    private final MoveValidator validator =
            new MoveValidator();

    public List<Move> generateLegalMoves(
            BoardState board,
            boolean whiteTurn) {

        List<Move> legalMoves = new ArrayList<>();

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                ChessSquare square =
                        board.getSquare(row, column);

                Piece piece = square.getPiece();

                if (piece == null)
                    continue;

                if (piece.isWhite() != whiteTurn)
                    continue;

                List<Move> pseudoMoves =
                        piece.generateMoves(
                                board,
                                row,
                                column
                        );

                for (Move move : pseudoMoves) {

                    if (validator.isLegalMove(
                            board,
                            move,
                            whiteTurn)) {

                        legalMoves.add(move);

                    }

                }

            }

        }

        return legalMoves;
    }

}