package com.mehroof.chessmaster.ai;

import java.util.ArrayList;
import java.util.List;

import com.mehroof.chessmaster.board.ChessBoard;
import com.mehroof.chessmaster.board.ChessSquare;
import com.mehroof.chessmaster.move.Move;
import com.mehroof.chessmaster.pieces.Piece;
import java.util.Random;
import com.mehroof.chessmaster.ai.evaluation.BoardEvaluator;
import com.mehroof.chessmaster.model.BoardState;

public class ChessAI {

	private BoardEvaluator evaluator = new BoardEvaluator();
	
	public AIMove chooseMove(ChessBoard board) {

	    List<AIMove> possibleMoves = new ArrayList<>();

	    for (int row = 0; row < 8; row++) {

	        for (int column = 0; column < 8; column++) {

	            ChessSquare square =
	                    board.getSquare(row, column);

	            Piece piece = square.getPiece();

	            if (piece == null) {
	                continue;
	            }

	            // AI plays Black
	            if (piece.isWhite()) {
	                continue;
	            }

	            List<Move> moves =
	                    piece.getLegalMoves(
	                            board.getBoardState(),
	                            row,
	                            column
	                    );

	            for (Move move : moves) {

	                ChessSquare destination =
	                        board.getSquare(
	                                move.getRow(),
	                                move.getColumn()
	                        );

	                possibleMoves.add(
	                        new AIMove(
	                                row,
	                                column,
	                                move.getRow(),
	                                move.getColumn()
	                        )
	                );
	            }

	        }

	    }
	    
	    System.out.println(
	            "AI found "
	            + possibleMoves.size()
	            + " legal moves."
	    );

	    if (possibleMoves.isEmpty()) {
	        return null;
	    }

	    int bestScore = Integer.MIN_VALUE;

	    AIMove bestMove = null;
	    
	    for (AIMove move : possibleMoves) {

	        BoardState copiedBoard =
	                board.getBoardState().copy();

	        Piece captured =
	                copiedBoard.movePiece(
	                		move.getFromRow(),
	                		move.getFromColumn(),
	                		move.getToRow(),
	                		move.getToColumn()
	                );

	        int score = evaluator.evaluate(copiedBoard);

	        copiedBoard.undoMove(
	        		move.getFromRow() ,
	        		move.getFromColumn(),
	        		move.getToRow(),
	        		move.getToColumn(),
	                captured
	        );

	        if (score > bestScore) {

	            bestScore = score;

	            bestMove = move;

	        }

	    }

	    return bestMove;
	    
	}

}