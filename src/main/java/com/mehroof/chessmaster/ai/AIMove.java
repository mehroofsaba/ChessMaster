package com.mehroof.chessmaster.ai;

import com.mehroof.chessmaster.board.ChessSquare;

public class AIMove {

    private ChessSquare from;
    private ChessSquare to;

    public AIMove(ChessSquare from, ChessSquare to) {

        this.from = from;
        this.to = to;
    }

    public ChessSquare getFrom() {
        return from;
    }

    public ChessSquare getTo() {
        return to;
    }
}