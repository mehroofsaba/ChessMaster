package com.mehroof.chessmaster.model;

import com.mehroof.chessmaster.pieces.Piece;

public class MoveRecord {

    private Piece piece;

    private int fromRow;
    private int fromColumn;

    private int toRow;
    private int toColumn;

    public MoveRecord(
            Piece piece,
            int fromRow,
            int fromColumn,
            int toRow,
            int toColumn) {

        this.piece = piece;
        this.fromRow = fromRow;
        this.fromColumn = fromColumn;
        this.toRow = toRow;
        this.toColumn = toColumn;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getFromColumn() {
        return fromColumn;
    }

    public int getToRow() {
        return toRow;
    }

    public int getToColumn() {
        return toColumn;
    }
}