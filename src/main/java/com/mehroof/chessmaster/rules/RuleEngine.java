package com.mehroof.chessmaster.rules;

import java.util.List;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;

public class RuleEngine {

	private MoveGenerator moveGenerator =
	        new MoveGenerator();

	private MoveValidator moveValidator =
	        new MoveValidator();

	private CheckDetector checkDetector =
	        new CheckDetector();

	private CheckmateDetector checkmateDetector =
	        new CheckmateDetector();

	private DrawDetector drawDetector =
	        new DrawDetector();
	
	public boolean isMoveLegal(
	        BoardState board,
	        int fromRow,
	        int fromColumn,
	        Move move,
	        boolean whiteTurn) {

	    return moveValidator.isLegalMove(
	            board,
	            fromRow,
	            fromColumn,
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
	
	
	
}
