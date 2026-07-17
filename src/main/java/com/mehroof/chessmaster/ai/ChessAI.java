package com.mehroof.chessmaster.ai;

import java.util.ArrayList;
import java.util.List;

import com.mehroof.chessmaster.board.ChessBoard;
import com.mehroof.chessmaster.board.ChessSquare;
import com.mehroof.chessmaster.move.Move;
import com.mehroof.chessmaster.pieces.Piece;
import java.util.Random;

public class ChessAI {

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
	                        new AIMove(square, destination)
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

	    Random random = new Random();

	    return possibleMoves.get(
	            random.nextInt(possibleMoves.size())
	    );
	}

}