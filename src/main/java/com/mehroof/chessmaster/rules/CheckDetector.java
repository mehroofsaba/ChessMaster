package com.mehroof.chessmaster.rules;

import com.mehroof.chessmaster.board.ChessSquare;
import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.pieces.Piece;
import java.util.List;
import com.mehroof.chessmaster.move.Move;

public class CheckDetector {

    public boolean isKingInCheck(
            BoardState board,
            boolean white) {

    	ChessSquare kingSquare = board.findKing(white);

    	if (kingSquare == null) {
    	    return false;
    	}

    	int kingRow = kingSquare.getRow();
    	int kingColumn = kingSquare.getColumn();

    	for (int row = 0; row < 8; row++) {

    	    for (int column = 0; column < 8; column++) {

    	        ChessSquare square =
    	                board.getSquare(row, column);

    	        Piece piece =
    	                square.getPiece();

    	        if (piece == null)
    	            continue;

    	        if (piece.isWhite() == white)
    	            continue;

    	        List<Move> attacks =
    	                piece.getLegalMoves(
    	                        board,
    	                        row,
    	                        column
    	                );
    	        
    	        for (Move move : attacks) {

    	            if (move.getRow() == kingRow &&
    	                move.getColumn() == kingColumn) {

    	                return true;

    	            }

    	        }
    	    }

    	}

    	return false;
    }

}