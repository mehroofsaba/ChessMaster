package com.mehroof.chessmaster.ai.hash;

import java.util.Random;

public class ZobristHash {

    public static final long[][][] PIECE_KEYS =
            new long[2][6][64];

    public static final long SIDE_TO_MOVE;

    static {

        Random random = new Random(123456789);

        for (int color = 0; color < 2; color++) {

            for (int piece = 0; piece < 6; piece++) {

                for (int square = 0; square < 64; square++) {

                    PIECE_KEYS[color][piece][square] =
                            random.nextLong();

                }

            }

        }

        SIDE_TO_MOVE = random.nextLong();

    }

}