package com.mehroof.chessmaster.model;

import com.mehroof.chessmaster.model.MoveRecord;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private Turn currentTurn;
    
    private MoveRecord lastMove;

    private final List<MoveRecord> moveHistory =
            new ArrayList<>();

    public GameState() {

        currentTurn = Turn.WHITE;

    }

    public Turn getCurrentTurn() {

        return currentTurn;

    }

    public void switchTurn() {

        if (currentTurn == Turn.WHITE) {

            currentTurn = Turn.BLACK;

        } else {

            currentTurn = Turn.WHITE;

        }

    }
    
    public MoveRecord getLastMove() {
        return lastMove;
    }

    public void setLastMove(MoveRecord lastMove) {
        this.lastMove = lastMove;
    }
    
    public void addMove(MoveRecord move) {

        lastMove = move;

        moveHistory.add(move);

    }
    
    public List<MoveRecord> getMoveHistory() {
        return moveHistory;
    }
    
}