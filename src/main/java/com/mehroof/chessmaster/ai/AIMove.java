package com.mehroof.chessmaster.ai;

import java.util.Objects;

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
    
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        AIMove other = (AIMove) obj;

        return fromRow == other.fromRow
                && fromColumn == other.fromColumn
                && toRow == other.toRow
                && toColumn == other.toColumn;
    }

    @Override
    public int hashCode() {

        return java.util.Objects.hash(
                fromRow,
                fromColumn,
                toRow,
                toColumn
        );
    }
}