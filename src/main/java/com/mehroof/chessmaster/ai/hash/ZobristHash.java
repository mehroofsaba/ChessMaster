package com.mehroof.chessmaster.ai.hash;

import com.mehroof.chessmaster.board.ChessSquare;
import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.pieces.*;

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

    // ← ADD THIS METHOD

    public static long computeHash(BoardState boardState) {

        long hash = 0L;

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                ChessSquare square =
                        boardState.getSquare(row, column);

                Piece piece = square.getPiece();

                if (piece == null)
                    continue;

                int color =
                        piece.isWhite() ? 0 : 1;

                int pieceType =
                        getPieceIndex(piece);

                int squareIndex =
                        row * 8 + column;

                hash ^= PIECE_KEYS[color][pieceType][squareIndex];

            }

        }

        return hash;
    }

    private static int getPieceIndex(Piece piece) {

        if (piece instanceof Pawn) return 0;
        if (piece instanceof Knight) return 1;
        if (piece instanceof Bishop) return 2;
        if (piece instanceof Rook) return 3;
        if (piece instanceof Queen) return 4;

        return 5; // King
    }

}