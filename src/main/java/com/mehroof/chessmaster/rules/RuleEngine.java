package com.mehroof.chessmaster.rules;

import java.util.List;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;

public class RuleEngine {

	private final MoveGenerator moveGenerator =
	        new MoveGenerator();

	private final MoveValidator moveValidator =
	        new MoveValidator();

	private final CheckDetector checkDetector =
	        new CheckDetector();

	private final CheckmateDetector checkmateDetector =
	        new CheckmateDetector();

	private final DrawDetector drawDetector =
	        new DrawDetector();
	
	public boolean isMoveLegal(
	        BoardState board,
	        Move move,
	        boolean whiteTurn) {

	    return moveValidator.isLegalMove(
	            board,
	            move,
	            whiteTurn
	    );

	}
	
	public boolean isKingInCheck(
	        BoardState board,
	        boolean whiteTurn) {

	    return checkDetector.isKingInCheck(
	            board,
	            whiteTurn
	    );

	}
	
	public boolean isCheckmate(
	        BoardState board,
	        boolean whiteTurn) {

	    return checkmateDetector.isCheckmate(
	            board,
	            whiteTurn
	    );

	}
	
	public List<Move> generateMoves(
	        BoardState board,
	        boolean whiteTurn) {

	    return moveGenerator.generateMoves(
	            board,
	            whiteTurn
	    );

	}
	
	public boolean isStalemate(
	        BoardState board,
	        boolean whiteTurn) {

	    return drawDetector.isStalemate(
	            board,
	            whiteTurn
	    );
	}

	public boolean isDrawByInsufficientMaterial(
	        BoardState board) {

	    return drawDetector.isInsufficientMaterial(board);
	}

	public boolean isDrawByFiftyMoveRule() {

	    return drawDetector.isFiftyMoveRule();
	}

	public boolean isDrawByThreefoldRepetition() {

	    return drawDetector.isThreefoldRepetition();
	}
	
}
