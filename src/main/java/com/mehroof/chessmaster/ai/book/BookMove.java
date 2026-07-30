package com.mehroof.chessmaster.ai.book;

import com.mehroof.chessmaster.ai.AIMove;

public class BookMove {

    private final String position;
    private final AIMove move;

    public BookMove(String position, AIMove move) {
        this.position = position;
        this.move = move;
    }

    public String getPosition() {
        return position;
    }

    public AIMove getMove() {
        return move;
    }
}