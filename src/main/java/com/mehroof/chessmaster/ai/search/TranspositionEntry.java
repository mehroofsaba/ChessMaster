package com.mehroof.chessmaster.ai.search;

public class TranspositionEntry {

    private final int depth;

    private final int score;

    public TranspositionEntry(
            int depth,
            int score) {

        this.depth = depth;
        this.score = score;
    }

    public int getDepth() {
        return depth;
    }

    public int getScore() {
        return score;
    }

}