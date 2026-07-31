package com.mehroof.chessmaster.rules;

import java.util.List;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;

public class DrawDetector {

	private CheckDetector checkDetector =
	        new CheckDetector();

	private MoveGenerator moveGenerator =
	        new MoveGenerator();
	
	public boolean isStalemate(
	        BoardState board,
	        boolean whiteTurn) {
		

	    if (checkDetector.isKingInCheck(
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
	
	public boolean isInsufficientMaterial(BoardState board) {

	    return false;

	}

	public boolean isFiftyMoveRule() {

	    return false;

	}

	public boolean isThreefoldRepetition() {

	    return false;

	}
	
}
