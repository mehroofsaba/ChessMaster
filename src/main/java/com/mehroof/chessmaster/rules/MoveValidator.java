package com.mehroof.chessmaster.rules;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;

public class MoveValidator {
	
	private CheckDetector detector =
	        new CheckDetector();

	public boolean isLegalMove(
	        BoardState board,
	        int fromRow,
	        int fromColumn,
	        Move move,
	        boolean whiteTurn) {

	    BoardState copied =
	            board.copy();

	    copied.movePiece(
	            fromRow,
	            fromColumn,
	            move.getRow(),
	            move.getColumn()
	    );

	    return !detector.isKingInCheck(
	            copied,
	            whiteTurn
	    );
	}

}