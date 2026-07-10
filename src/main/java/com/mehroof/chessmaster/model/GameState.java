package com.mehroof.chessmaster.model;

public class GameState {

    private Turn currentTurn;

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
}