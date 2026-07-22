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
	                    piece.generateMoves(
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

	    score -= evaluateKingSafety(boardState, true);
	    score += evaluateKingSafety(boardState, false);

	    score -= evaluatePawnStructure(boardState, true);
	    score += evaluatePawnStructure(boardState, false);

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
	
	private int evaluateKingSafety(
	        BoardState boardState,
	        boolean white) {

	    ChessSquare kingSquare =
	            boardState.findKing(white);

	    if (kingSquare == null)
	        return 0;

	    int score = 0;

	    int row = kingSquare.getRow();
	    int col = kingSquare.getColumn();

	    for (int dr = -1; dr <= 1; dr++) {

	        for (int dc = -1; dc <= 1; dc++) {

	            if (dr == 0 && dc == 0)
	                continue;

	            int r = row + dr;
	            int c = col + dc;

	            if (r < 0 || r > 7 || c < 0 || c > 7)
	                continue;

	            Piece piece =
	                    boardState.getSquare(r, c).getPiece();

	            if (piece != null && piece.isWhite() == white) {

	                score += 10;

	            }

	        }

	    }

	    return score;

	}
	
	private int evaluatePawnStructure(
	        BoardState boardState,
	        boolean white) {

	    int score = 0;

	    int[] pawnCount = new int[8];

	    for (int row = 0; row < 8; row++) {

	        for (int column = 0; column < 8; column++) {

	            Piece piece =
	                    boardState.getSquare(row, column).getPiece();

	            if (piece instanceof Pawn
	                    && piece.isWhite() == white) {

	                pawnCount[column]++;

	            }

	        }

	    }

	    // Doubled pawns
	    for (int file = 0; file < 8; file++) {

	        if (pawnCount[file] > 1) {

	            score -= 15 * (pawnCount[file] - 1);

	        }

	    }
	    
	 // Isolated pawns
	    for (int file = 0; file < 8; file++) {

	        if (pawnCount[file] == 0)
	            continue;

	        boolean left =
	                file > 0 && pawnCount[file - 1] > 0;

	        boolean right =
	                file < 7 && pawnCount[file + 1] > 0;

	        if (!left && !right) {

	            score -= 20;

	        }

	    }   
	    
	 // Passed pawns
	    for (int row = 0; row < 8; row++) {

	        for (int column = 0; column < 8; column++) {

	            Piece piece =
	                    boardState.getSquare(row, column).getPiece();

	            if (!(piece instanceof Pawn))
	                continue;

	            if (piece.isWhite() != white)
	                continue;

	            if (isPassedPawn(
	                    boardState,
	                    row,
	                    column,
	                    white)) {

	                if (white) {

	                    score += (6 - row) * 10;

	                } else {

	                    score += (row - 1) * 10;

	                }

	            }

	        }

	    }

	    return score;

	}
	
	private boolean isPassedPawn(
	        BoardState boardState,
	        int row,
	        int column,
	        boolean white) {

	    int direction = white ? -1 : 1;

	    for (int r = row + direction;
	         r >= 0 && r < 8;
	         r += direction) {

	        for (int c = column - 1;
	             c <= column + 1;
	             c++) {

	            if (c < 0 || c > 7)
	                continue;

	            Piece piece =
	                    boardState.getSquare(r, c).getPiece();

	            if (piece instanceof Pawn
	                    && piece.isWhite() != white) {

	                return false;

	            }

	        }

	    }

	    return true;

	}
	
}