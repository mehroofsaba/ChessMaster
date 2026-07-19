package com.mehroof.chessmaster.rules;

import java.util.ArrayList;
import java.util.List;

import com.mehroof.chessmaster.board.ChessSquare;
import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;
import com.mehroof.chessmaster.pieces.Piece;

public class MoveGenerator {
	
	private MoveValidator validator = new MoveValidator();
	
	public List<Move> generateMoves(
	        BoardState board,
	        boolean whiteTurn) {

	    List<Move> moves = new ArrayList<>();

	    for (int row = 0; row < 8; row++) {

	        for (int column = 0; column < 8; column++) {

	            ChessSquare square = board.getSquare(row, column);

	            Piece piece = square.getPiece();

	            if (piece == null)
	                continue;

	            if (piece.isWhite() != whiteTurn)
	                continue;

	            List<Move> pieceMoves =
	                    piece.getLegalMoves(
	                            board,
	                            row,
	                            column
	                    );

	            for (Move move : pieceMoves) {

	                if (isLegalMove(
	                        board,
	                        row,
	                        column,
	                        move,
	                        whiteTurn)) {

	                    moves.add(move);

	                }

	            }
	        }

	    }

	    return moves;
	}
	
	private boolean isLegalMove(
	        BoardState board,
	        int fromRow,
	        int fromColumn,
	        Move move,
	        boolean whiteTurn) {

	    return validator.isLegalMove(
	            board,
	            fromRow,
	            fromColumn,
	            move,
	            whiteTurn
	    );
	}
	
}