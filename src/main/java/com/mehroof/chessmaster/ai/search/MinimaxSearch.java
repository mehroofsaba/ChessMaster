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
import com.mehroof.chessmaster.ai.ordering.MoveOrdering;

public class MinimaxSearch implements Search {

	private final BoardEvaluator evaluator = new BoardEvaluator();

	private final RuleEngine ruleEngine = new RuleEngine();

	private final MoveOrdering moveOrdering = new MoveOrdering();
	
	private static final int SEARCH_DEPTH = 3;
	
	private static final int CHECKMATE_SCORE = 100000;
	
	@Override
	public AIMove findBestMove(ChessBoard board) {

	    int bestScore = Integer.MIN_VALUE;

	    AIMove bestMove = null;

	    List<AIMove> moves =
	            generateMoves(
	                    board.getBoardState(),
	                    false
	            );
	    
	    moveOrdering.orderMoves(moves);
	    
	    int alpha = Integer.MIN_VALUE;
	    int beta = Integer.MAX_VALUE;

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
	                        alpha,
	                        beta,
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
	            
	            alpha = Math.max(alpha, bestScore);

	        }

	    }

	    return bestMove;

	}
    
	private int minimax(
	        ChessBoard board,
	        int depth,
	        int alpha,
	        int beta,
	        boolean maximizingPlayer) {

		// White is checkmated → Black (AI) wins
		if (ruleEngine.isCheckmate(board.getBoardState(), true)) {
			return CHECKMATE_SCORE;
		}

		// Black is checkmated → White wins
		if (ruleEngine.isCheckmate(board.getBoardState(), false)) {
			return CHECKMATE_SCORE;
		}

		// Search depth reached
		if (depth == 0) {
		    return evaluator.evaluate(board.getBoardState());
		}

        if (maximizingPlayer) {

        	int best = Integer.MIN_VALUE;

        	List<AIMove> moves =
        	        generateMoves(
        	                board.getBoardState(),
        	                false
        	        );
        	
        	moveOrdering.orderMoves(moves);
        	
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
        		                alpha,
        		                beta,
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

        		alpha = Math.max(alpha, best);

        		if (beta <= alpha) {
        		    break;
        		}
        		
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
            
            moveOrdering.orderMoves(moves);

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
                                alpha,
                                beta,
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

                beta = Math.min(beta, best);

                if (beta <= alpha) {
                    break;
                }

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
