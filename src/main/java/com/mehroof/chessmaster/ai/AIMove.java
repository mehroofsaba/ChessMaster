package com.mehroof.chessmaster.ai;

public class AIMove {

    private int fromRow;
    private int fromColumn;

    private int toRow;
    private int toColumn;

    public AIMove(
            int fromRow,
            int fromColumn,
            int toRow,
            int toColumn) {

        this.fromRow = fromRow;
        this.fromColumn = fromColumn;
        this.toRow = toRow;
        this.toColumn = toColumn;
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