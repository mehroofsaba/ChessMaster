package com.mehroof.chessmaster.ai.ordering;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mehroof.chessmaster.ai.AIMove;

public class MoveOrdering {

    public void orderMoves(List<AIMove> moves) {

        Collections.sort(
                moves,
                Comparator.comparingInt(this::scoreMove)
                          .reversed()
        );

    }

    private int scoreMove(AIMove move) {

        return 0;

    }

}