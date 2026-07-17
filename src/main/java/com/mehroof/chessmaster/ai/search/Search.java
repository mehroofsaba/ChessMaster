package com.mehroof.chessmaster.ai.search;

import com.mehroof.chessmaster.ai.AIMove;
import com.mehroof.chessmaster.board.ChessBoard;

public interface Search {

    AIMove findBestMove(ChessBoard board);

}