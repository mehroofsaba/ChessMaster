package com.mehroof.chessmaster.model;

import com.mehroof.chessmaster.board.ChessSquare;
import com.mehroof.chessmaster.pieces.*;

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

    public BoardState copy() {

        ChessSquare[][] copiedSquares =
                new ChessSquare[8][8];

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                ChessSquare original =
                        squares[row][column];

                ChessSquare copy =
                        new ChessSquare(row, column);

                copy.setPiece(
                        copyPiece(original.getPiece())
                );

                copiedSquares[row][column] = copy;
            }
        }

        return new BoardState(copiedSquares);
    }

    public Piece movePiece(
            int fromRow,
            int fromColumn,
            int toRow,
            int toColumn) {

        if (!isInsideBoard(fromRow, fromColumn)
                || !isInsideBoard(toRow, toColumn)) {

            throw new IllegalArgumentException(
                    "Invalid move coordinates: "
                    + fromRow + ","
                    + fromColumn
                    + " -> "
                    + toRow + ","
                    + toColumn
            );
        }

        Piece moving =
                getSquare(fromRow, fromColumn).getPiece();

        if (moving == null) {

            throw new IllegalStateException(
                    "Attempted to move from an empty square: "
                    + fromRow + ","
                    + fromColumn
            );
        }

        Piece captured =
                getSquare(toRow, toColumn).getPiece();

        getSquare(fromRow, fromColumn).setPiece(null);

        getSquare(toRow, toColumn).setPiece(moving);

        return captured;
    }

    public void undoMove(
            int fromRow,
            int fromColumn,
            int toRow,
            int toColumn,
            Piece capturedPiece) {

        if (!isInsideBoard(fromRow, fromColumn)
                || !isInsideBoard(toRow, toColumn)) {

            throw new IllegalArgumentException(
                    "Invalid undo coordinates: "
                    + fromRow + ","
                    + fromColumn
                    + " -> "
                    + toRow + ","
                    + toColumn
            );
        }

        Piece moving =
                getSquare(toRow, toColumn).getPiece();

        if (moving == null) {

            throw new IllegalStateException(
                    "Attempted to undo a move from an empty destination: "
                    + toRow + ","
                    + toColumn
            );
        }

        getSquare(toRow, toColumn).setPiece(
                capturedPiece
        );

        getSquare(fromRow, fromColumn).setPiece(
                moving
        );
    }

    private Piece copyPiece(Piece piece) {

        if (piece == null) {
            return null;
        }

        Piece newPiece;

        if (piece instanceof Pawn) {

            newPiece = new Pawn(piece.isWhite());

        } else if (piece instanceof Knight) {

            newPiece = new Knight(piece.isWhite());

        } else if (piece instanceof Bishop) {

            newPiece = new Bishop(piece.isWhite());

        } else if (piece instanceof Rook) {

            newPiece = new Rook(piece.isWhite());

        } else if (piece instanceof Queen) {

            newPiece = new Queen(piece.isWhite());

        } else if (piece instanceof King) {

            newPiece = new King(piece.isWhite());

        } else {

            throw new IllegalStateException(
                    "Unknown piece type: "
                    + piece.getClass().getName()
            );
        }

        newPiece.setHasMoved(
                piece.hasMoved()
        );

        return newPiece;
    }
}