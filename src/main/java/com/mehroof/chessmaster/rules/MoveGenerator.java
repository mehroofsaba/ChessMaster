package com.mehroof.chessmaster.rules;

import java.util.List;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;

public class MoveGenerator {

	private final LegalMoveGenerator legalMoveGenerator =
	        new LegalMoveGenerator();

    public List<Move> generateMoves(
            BoardState board,
            boolean whiteTurn) {

        return legalMoveGenerator.generateLegalMoves(
                board,
                whiteTurn
        );

    }

}