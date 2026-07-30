package com.mehroof.chessmaster.ai.book;

import java.util.HashMap;
import java.util.Map;

import com.mehroof.chessmaster.ai.AIMove;
import com.mehroof.chessmaster.ai.hash.ZobristHash;
import com.mehroof.chessmaster.model.BoardState;

import java.util.ArrayList;
import java.util.List;



public class OpeningBook {

    private final List<BookMove> bookMoves =
            new ArrayList<>();

    public OpeningBook() {

        loadOpenings();

    }

    private void loadOpenings() {

        /*
         * Position string format:
         *
         * e2e4 e7e5 g1f3
         *
         */

        // 1. e4  -> ...e5

        bookMoves.add(

                new BookMove(
                        "e2e4",
                        new AIMove(1,4,3,4)   // e7 -> e5
                )

        );

        // 1. d4 -> ...d5

        bookMoves.add(

                new BookMove(
                        "d2d4",
                        new AIMove(1,3,3,3)
                )

        );

        // 1. e4 e5 2. Nf3 -> ...Nc6

        bookMoves.add(

                new BookMove(
                        "e2e4 e7e5 g1f3",
                        new AIMove(0,1,2,2)
                )

        );

        // 1. e4 c5 (Sicilian)

        bookMoves.add(

                new BookMove(
                        "e2e4",
                        new AIMove(1,2,3,2)
                )

        );

    }

    public AIMove findBookMove(String position) {

        for (BookMove move : bookMoves) {

            if (move.getPosition().equals(position)) {

                return move.getMove();

            }

        }

        return null;

    }

}