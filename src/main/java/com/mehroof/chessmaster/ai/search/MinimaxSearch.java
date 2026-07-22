package com.mehroof.chessmaster.ai.search;

import com.mehroof.chessmaster.ai.AIMove;
import com.mehroof.chessmaster.board.ChessBoard;
import com.mehroof.chessmaster.ai.evaluation.BoardEvaluator;

import java.util.ArrayList;
import java.util.List;

import com.mehroof.chessmaster.board.ChessSquare;
import com.mehroof.chessmaster.move.Move;
import com.mehroof.chessmaster.pieces.Piece;
import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.rules.RuleEngine;

public class MinimaxSearch implements Search {

	private BoardEvaluator evaluator = new BoardEvaluator();
	
	private RuleEngine ruleEngine = new RuleEngine();
	
	private static final int SEARCH_DEPTH = 3;
	
	@Override
	public AIMove findBestMove(ChessBoard board) {

	    int bestScore = Integer.MIN_VALUE;

	    AIMove bestMove = null;

	    List<AIMove> moves =
	            generateMoves(
	                    board.getBoardState(),
	                    false
	            );

	    for (AIMove move : moves) {

	        BoardState copied =
	                board.getBoardState().copy();

	        Piece captured =
	                copied.movePiece(
	                        move.getFromRow(),
	                        move.getFromColumn(),
	                        move.getToRow(),
	                        move.getToColumn()
	                );

	        int score =
	                minimax(
	                        new ChessBoard(copied),
	                        SEARCH_DEPTH - 1,
	                        false
	                );

	        copied.undoMove(
	                move.getFromRow(),
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
    
    private int minimax(
            ChessBoard board,
            int depth,
            boolean maximizingPlayer) {

    	if (depth == 0
    	        || ruleEngine.isCheckmate(board.getBoardState(), true)
    	        || ruleEngine.isCheckmate(board.getBoardState(), false)) {

    	    return evaluator.evaluate(board.getBoardState());

    	}

        if (maximizingPlayer) {

        	int best = Integer.MIN_VALUE;

        	List<AIMove> moves =
        	        generateMoves(
        	                board.getBoardState(),
        	                false
        	        );
        	
        	for (AIMove move : moves) {

        	    // We'll simulate the move next
        		
        		BoardState copied =
        		        board.getBoardState().copy();
        		Piece captured =
        		        copied.movePiece(
        		                move.getFromRow(),
        		                move.getFromColumn(),
        		                move.getToRow(),
        		                move.getToColumn()
        		        );
        		
        		int score =
        		        minimax(
        		                new ChessBoard(copied),
        		                depth - 1,
        		                false
        		        );

        		copied.undoMove(
        		        move.getFromRow(),
        		        move.getFromColumn(),
        		        move.getToRow(),
        		        move.getToColumn(),
        		        captured
        		);

        		best = Math.max(best, score);
        		
        	}

        	return best;
        } 
        
        else {

            int best = Integer.MAX_VALUE;

            List<AIMove> moves =
                    generateMoves(
                            board.getBoardState(),
                            true
                    );

            for (AIMove move : moves) {

                BoardState copied =
                        board.getBoardState().copy();

                Piece captured =
                        copied.movePiece(
                                move.getFromRow(),
                                move.getFromColumn(),
                                move.getToRow(),
                                move.getToColumn()
                        );

                int score =
                        minimax(
                                new ChessBoard(copied),
                                depth - 1,
                                true
                        );

                copied.undoMove(
                        move.getFromRow(),
                        move.getFromColumn(),
                        move.getToRow(),
                        move.getToColumn(),
                        captured
                );

                best = Math.min(best, score);

            }

            return best;

        }
    }
    
    private List<AIMove> generateMoves(
            BoardState board,
            boolean white) {

        List<AIMove> moves = new ArrayList<>();

        BoardState boardState = board;

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                ChessSquare square =
                        board.getSquare(row, column);

                Piece piece = square.getPiece();

                if (piece == null)
                    continue;

                if (piece.isWhite() != white)
                    continue;

                List<Move> legalMoves =
                        piece.generateMoves(
                                boardState,
                                row,
                                column
                        );

                for (Move move : legalMoves) {

                    

                    moves.add(
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

        return moves;

    }

}
