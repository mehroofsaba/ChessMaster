package com.mehroof.chessmaster.pieces;
import java.util.ArrayList;
import java.util.List;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;
public class Queen extends Piece {

    public Queen(boolean white) {
        super(white);
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "♕" : "♛";
    }
    
    @Override
    public List<Move> getLegalMoves(
            BoardState boardState,
            int row,
            int column) {

        List<Move> moves = new ArrayList<>();

        Rook rook = new Rook(isWhite());
        moves.addAll(rook.getLegalMoves(boardState, row, column));

        Bishop bishop = new Bishop(isWhite());
        moves.addAll(bishop.getLegalMoves(boardState, row, column));

        System.out.println("Queen moves:");

        for (Move m : moves) {
            System.out.println(
                m.getRow() + "," + m.getColumn()
            );
        }

        return moves;
    }
}