package com.mehroof.chessmaster.ai.evaluation;

import com.mehroof.chessmaster.board.ChessSquare;
import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.pieces.*;

public class BoardEvaluator {

	public int evaluate(BoardState boardState) {

	    int score = 0;

	    for (int row = 0; row < 8; row++) {

	        for (int column = 0; column < 8; column++) {

	            ChessSquare square = boardState.getSquare(row, column);

	            Piece piece = square.getPiece();

	            if (piece == null)
	                continue;

	            int value = getPieceValue(piece);

	            if (piece.isWhite()) {

	                score -= value;

	            } else {

	                score += value;

	            }
	        }
	    }

	    return score;
	}

    private int getPieceValue(Piece piece) {

        if (piece instanceof Pawn)
            return 100;

        if (piece instanceof Knight)
            return 320;

        if (piece instanceof Bishop)
            return 330;

        if (piece instanceof Rook)
            return 500;

        if (piece instanceof Queen)
            return 900;

        if (piece instanceof King)
            return 10000;

        return 0;
    }
}