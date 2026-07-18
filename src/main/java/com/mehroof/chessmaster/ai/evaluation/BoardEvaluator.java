package com.mehroof.chessmaster.ai.evaluation;

import com.mehroof.chessmaster.board.ChessSquare;
import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.pieces.*;
import com.mehroof.chessmaster.ai.tables.PieceSquareTables;

public class BoardEvaluator {

	public int evaluate(BoardState boardState) {

	    int score = 0;

	    for (int row = 0; row < 8; row++) {

	        for (int column = 0; column < 8; column++) {

	            ChessSquare square = boardState.getSquare(row, column);

	            Piece piece = square.getPiece();

	            if (piece == null)
	                continue;

	            int value =
	                    getPieceValue(
	                            piece,
	                            row,
	                            column
	                    );
	            
	            int mobility =
	                    piece.getLegalMoves(
	                            boardState,
	                            row,
	                            column
	                    ).size();

	            if (piece.isWhite()) {

	            	score -= value + mobility * 5;

	            } else {

	            	score += value + mobility * 5;
	            }
	        }
	    }

	    return score;
	}

	private int getPieceValue(
	        Piece piece,
	        int row,
	        int column) {
		
		if (piece instanceof Pawn) {

		    int positionBonus;

		    if (piece.isWhite()) {

		        positionBonus =
		                PieceSquareTables.PAWN[7-row][column];

		    } else {

		        positionBonus =
		                PieceSquareTables.PAWN[row][column];

		    }

		    return 100 + positionBonus;
		}

	    if (piece instanceof Knight) {

	        int positionBonus;

	        if (piece.isWhite()) {

	            positionBonus =
	                    PieceSquareTables.KNIGHT[7 - row][column];

	        } else {

	            positionBonus =
	                    PieceSquareTables.KNIGHT[row][column];

	        }

	        return 320 + positionBonus;
	    }

	    if (piece instanceof Bishop) {

	        int positionBonus;

	        if (piece.isWhite()) {

	            positionBonus =
	                    PieceSquareTables.BISHOP[7 - row][column];

	        } else {

	            positionBonus =
	                    PieceSquareTables.BISHOP[row][column];

	        }

	        return 330 + positionBonus;
	    }
	    
	    if (piece instanceof Rook) {

	        int positionBonus;

	        if (piece.isWhite()) {

	            positionBonus =
	                    PieceSquareTables.ROOK[7 - row][column];

	        } else {

	            positionBonus =
	                    PieceSquareTables.ROOK[row][column];

	        }

	        return 500 + positionBonus;
	    }

	    if (piece instanceof Queen) {

	        int positionBonus;

	        if (piece.isWhite()) {

	            positionBonus =
	                    PieceSquareTables.QUEEN[7 - row][column];

	        } else {

	            positionBonus =
	                    PieceSquareTables.QUEEN[row][column];

	        }

	        return 900 + positionBonus;
	    }

	    if (piece instanceof King) {

	        int positionBonus;

	        if (piece.isWhite()) {

	            positionBonus =
	                    PieceSquareTables.KING[7 - row][column];

	        } else {

	            positionBonus =
	                    PieceSquareTables.KING[row][column];

	        }

	        return 10000 + positionBonus;
	    }

	    return 0;
	}
}