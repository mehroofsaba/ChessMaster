package com.mehroof.chessmaster.ai.book;

import java.util.HashMap;
import java.util.Map;

import com.mehroof.chessmaster.ai.AIMove;
import com.mehroof.chessmaster.ai.hash.ZobristHash;
import com.mehroof.chessmaster.model.BoardState;

public class OpeningBook {

    private final Map<Long, AIMove> openings =
            new HashMap<>();

    public OpeningBook() {

        loadOpenings();

    }
    
    private void loadOpenings() {

    }

    public AIMove getBookMove(BoardState board) {

        long hash =
                ZobristHash.computeHash(board);

        return openings.get(hash);

    }

}