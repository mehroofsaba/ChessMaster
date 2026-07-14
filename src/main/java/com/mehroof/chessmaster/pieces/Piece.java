package com.mehroof.chessmaster.pieces;

import java.util.List;
import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;

public abstract class Piece {

    private boolean white;
    
    private boolean hasMoved = false;

    public Piece(boolean white) {
        this.white = white;
    }

    public boolean isWhite() {
        return white;
    }

    public abstract String getSymbol();
    
    public abstract List<Move> getLegalMoves(
            BoardState boardState,
            int row,
            int column
    );
    
    public boolean hasMoved() {

        return hasMoved;

    }

    public void setHasMoved(boolean hasMoved) {

        this.hasMoved = hasMoved;

    }
}