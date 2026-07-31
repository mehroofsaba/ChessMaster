package com.mehroof.chessmaster.ai.book;

import java.util.HashMap;
import java.util.Map;

import com.mehroof.chessmaster.ai.AIMove;

public class OpeningBook {

	private final Map<String, AIMove> openings =
	        new HashMap<>();

    public OpeningBook() {

        loadOpenings();

    }
    
    private void loadOpenings() {

        // 1. e4 -> ...e5
        openings.put(
                "e2e4",
                new AIMove(1, 4, 3, 4)
        );

        // 1. d4 -> ...d5
        openings.put(
                "d2d4",
                new AIMove(1, 3, 3, 3)
        );

        // 1. c4 -> ...e5
        openings.put(
                "c2c4",
                new AIMove(1, 4, 3, 4)
        );

        // 1. Nf3 -> ...d5
        openings.put(
                "g1f3",
                new AIMove(1, 3, 3, 3)
        );

    }

    public AIMove getBookMove(String position) {

        return openings.get(position);

    }

}