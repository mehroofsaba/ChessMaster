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
import com.mehroof.chessmaster.ai.hash.ZobristHash;
import com.mehroof.chessmaster.ai.book.OpeningBook;

public class MinimaxSearch implements Search {

	private final BoardEvaluator evaluator = new BoardEvaluator();

	private final RuleEngine ruleEngine = new RuleEngine();

	private final MoveOrdering moveOrdering = new MoveOrdering();
	
	private static final int MAX_DEPTH = 5;
	
	private static final int CHECKMATE_SCORE = 100000;
	
	private final KillerMoves killerMoves =
	        new KillerMoves();
	
	private final TranspositionTable transpositionTable =
	        new TranspositionTable();
	
	private final OpeningBook openingBook =
	        new OpeningBook();
	
	@Override
	public AIMove findBestMove(ChessBoard board) {

		// Opening Book temporarily disabled

	    AIMove bestMove = null;

	    for (int currentDepth = 1;
	    		currentDepth <= MAX_DEPTH;
	         currentDepth++) {

	        int bestScore = Integer.MIN_VALUE;

	        List<AIMove> moves =
	                generateMoves(
	                        board.getBoardState(),
	                        false
	                );

	        moveOrdering.orderMoves(
	                moves,
	                killerMoves,
	                currentDepth
	        );

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
	                            currentDepth - 1,
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
	            }

	            alpha = Math.max(alpha, bestScore);
	        }

	        System.out.println(
	                "Depth "
	                + currentDepth
	                + " completed. Best score = "
	                + bestScore
	        );
	    }

	    return bestMove;
	}
	

    
	private int minimax(
	        ChessBoard board,
	        int depth,
	        int alpha,
	        int beta,
	        boolean maximizingPlayer) {

	    // White is checkmated -> AI (Black) wins
	    if (ruleEngine.isCheckmate(board.getBoardState(), true)) {
	        return CHECKMATE_SCORE;
	    }

	    // Black is checkmated -> AI loses
	    if (ruleEngine.isCheckmate(board.getBoardState(), false)) {
	        return -CHECKMATE_SCORE;
	    }
	    
	    if(ruleEngine.isStalemate(board.getBoardState(), true))
	        return 0;

	    if(ruleEngine.isStalemate(board.getBoardState(), false))
	        return 0;
	    
	    long hash = ZobristHash.computeHash(board.getBoardState());

	    TranspositionEntry entry =
	            transpositionTable.get(hash);

	    if (entry != null && entry.getDepth() >= depth) {
	        return entry.getScore();
	    }

	 // Search depth reached
	    if (depth == 0) {
	    	return quiescence(
	    	        board,
	    	        alpha,
	    	        beta,
	    	        !maximizingPlayer
	    	);
	    }

        if (maximizingPlayer) {

        	int best = Integer.MIN_VALUE;

        	List<AIMove> moves =
        			generateMoves(
        			        board.getBoardState(),
        			        false
        			);

        	moveOrdering.orderMoves(
        	        moves,
        	        killerMoves,
        	        depth
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

        		    killerMoves.addKillerMove(depth, move);

        		    break;
        		}
        		
        		
        	}
        	
        	transpositionTable.put(
        	        hash,
        	        new TranspositionEntry(depth, best)
        	);

        	return best;

        	
        } 
        
        else {

            int best = Integer.MAX_VALUE;

            List<AIMove> moves =
                    generateMoves(
                            board.getBoardState(),
                            true
                    );

            moveOrdering.orderMoves(
                    moves,
                    killerMoves,
                    depth
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

                    killerMoves.addKillerMove(depth, move);

                    break;
                }

            }

            return best;

        }
    }
	
	private int quiescence(
	        ChessBoard board,
	        int alpha,
	        int beta,
	        boolean blackToMove) {

	    int standPat =
	            evaluator.evaluate(board.getBoardState());

	    if (standPat >= beta) {
	        return beta;
	    }

	    if (alpha < standPat) {
	        alpha = standPat;
	    }

	    List<AIMove> moves =
	            generateMoves(
	                    board.getBoardState(),
	                    !blackToMove
	            );
	    
	    moves.removeIf(move -> {

	        Piece target =
	                board.getBoardState()
	                     .getSquare(
	                             move.getToRow(),
	                             move.getToColumn())
	                     .getPiece();

	        return target == null;

	    });
	    
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
	        	    -quiescence(
	        	            new ChessBoard(copied),
	        	            -beta,
	        	            -alpha,
	        	            !blackToMove
	        	    );
	        
	        copied.undoMove(
	                move.getFromRow(),
	                move.getFromColumn(),
	                move.getToRow(),
	                move.getToColumn(),
	                captured
	        );

	        if (score >= beta) {
	            return beta;
	        }

	        if (score > alpha) {
	            alpha = score;
	        }
	    }

	    return alpha;
	}
    
    private List<AIMove> generateMoves(
            BoardState board,
            boolean white) {
    	
    	

        List<AIMove> moves = new ArrayList<>();

        

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                ChessSquare square =
                        board.getSquare(row, column);

                Piece piece = square.getPiece();

                if (piece == null)
                    continue;

                if (piece.isWhite() != white)
                    continue;

                List<Move> pseudoMoves =
                		piece.generateMoves(
                		        board,
                		        row,
                		        column
                		);

                for (Move move : pseudoMoves) {

                	if (!ruleEngine.isMoveLegal(
                	       board,
                	        move,
                	        white)) {

                	    continue;
                	}

                	moves.add(
                		    new AIMove(
                		            move.getFromRow(),
                		            move.getFromColumn(),
                		            move.getToRow(),
                		            move.getToColumn()
                		    )
                		);
                }

            }

        }

        return moves;

    }

}
