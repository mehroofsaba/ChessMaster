package com.mehroof.chessmaster.ai.search;

import com.mehroof.chessmaster.ai.AIMove;

public class KillerMoves {

    private AIMove[][] killerMoves =
            new AIMove[20][2];

    public void addKillerMove(int depth, AIMove move) {

        if (killerMoves[depth][0] == null ||
                !killerMoves[depth][0].equals(move)) {

            killerMoves[depth][1] =
                    killerMoves[depth][0];

            killerMoves[depth][0] =
                    move;
        }
    }

    public boolean isKillerMove(int depth,
                                AIMove move) {

        return move.equals(killerMoves[depth][0]) ||
               move.equals(killerMoves[depth][1]);
    }

}