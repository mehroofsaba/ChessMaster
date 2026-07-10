package com.mehroof.chessmaster.pieces;

import java.util.List;
import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;

public abstract class Piece {

    private boolean white;

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
}