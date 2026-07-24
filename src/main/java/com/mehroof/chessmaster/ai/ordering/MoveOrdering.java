package com.mehroof.chessmaster.ai.ordering;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mehroof.chessmaster.ai.AIMove;

public class MoveOrdering {

	public void orderMoves(
	        List<AIMove> moves,
	        com.mehroof.chessmaster.ai.search.KillerMoves killerMoves,
	        int depth) {

		Collections.sort(
		        moves,
		        Comparator.comparingInt(
		        	    (AIMove move) ->
		        	    scoreMove(move, killerMoves, depth)
		        	)
		                  .reversed()
		);

    }

	private int scoreMove(
	        AIMove move,
	        com.mehroof.chessmaster.ai.search.KillerMoves killerMoves,
	        int depth) {

	    int score = 0;

	    if (killerMoves.isKillerMove(depth, move)) {

	        score += 10000;

	    }

	    return score;

	}

}