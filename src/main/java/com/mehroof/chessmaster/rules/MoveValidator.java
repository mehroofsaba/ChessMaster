package com.mehroof.chessmaster.rules;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;
import com.mehroof.chessmaster.pieces.Piece;
import com.mehroof.chessmaster.pieces.King;
import com.mehroof.chessmaster.pieces.Piece;

public class MoveValidator {
	
	private CheckDetector detector =
	        new CheckDetector();

	public boolean isLegalMove(
	        BoardState board,
	        int fromRow,
	        int fromColumn,
	        Move move,
	        boolean whiteTurn) {

	    BoardState copied = board.copy();

	    Piece movingPiece =
	            board.getSquare(fromRow, fromColumn).getPiece();

	    copied.movePiece(
	            fromRow,
	            fromColumn,
	            move.getRow(),
	            move.getColumn()
	    );

	    if (movingPiece instanceof King) {

	        System.out.println(
	                "Testing king move to "
	                + move.getRow() + ","
	                + move.getColumn());

	        boolean inCheck =
	                detector.isKingInCheck(copied, whiteTurn);

	        System.out.println(
	                "King in check after move = "
	                + inCheck);
	    }

	    return !detector.isKingInCheck(
	            copied,
	            whiteTurn
	    );
	}
}