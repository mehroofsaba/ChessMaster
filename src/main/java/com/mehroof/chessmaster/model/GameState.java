package com.mehroof.chessmaster.model;

import com.mehroof.chessmaster.model.MoveRecord;

public class GameState {

    private Turn currentTurn;
    
    private MoveRecord lastMove;

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
    
}