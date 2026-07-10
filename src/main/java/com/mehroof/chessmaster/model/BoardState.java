package com.mehroof.chessmaster.model;

import com.mehroof.chessmaster.board.ChessSquare;
import com.mehroof.chessmaster.pieces.King;
import com.mehroof.chessmaster.pieces.Piece;

public class BoardState {

    private ChessSquare[][] squares;

    public BoardState(ChessSquare[][] squares) {

        this.squares = squares;

    }

    public ChessSquare getSquare(int row, int column) {

        return squares[row][column];

    }
    
    public boolean isInsideBoard(int row, int column) {

        return row >= 0
                && row < 8
                && column >= 0
                && column < 8;

    }
    
    public ChessSquare findKing(boolean white) {
    	

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                ChessSquare square = squares[row][column];

                if (square.getPiece() instanceof King) {

                    if (square.getPiece().isWhite() == white) {

                        return square;

                    }
                }
            }
        }

        return null;
    }
    
    public BoardState copy ()
     
    {
    	

        ChessSquare[][] copiedSquares = new ChessSquare[8][8];

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                ChessSquare original = squares[row][column];

                ChessSquare copy =
                        new ChessSquare(row, column);

                copy.setPiece(original.getPiece());

                copiedSquares[row][column] = copy;

            }

        }

        return new BoardState(copiedSquares);
        

    }
    
    public void movePiece(
            int fromRow,
            int fromColumn,
            int toRow,
            int toColumn) {

        Piece piece =
                getSquare(fromRow, fromColumn).getPiece();

        getSquare(toRow, toColumn).setPiece(piece);

        getSquare(fromRow, fromColumn).setPiece(null);

    }

}