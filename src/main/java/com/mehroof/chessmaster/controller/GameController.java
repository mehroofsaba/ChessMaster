package com.mehroof.chessmaster.controller;

import com.mehroof.chessmaster.board.ChessBoard;
import com.mehroof.chessmaster.board.ChessSquare;
import java.util.List;

import com.mehroof.chessmaster.model.BoardState;
import com.mehroof.chessmaster.move.Move;
import com.mehroof.chessmaster.pieces.Piece;
import com.mehroof.chessmaster.model.Turn;
import com.mehroof.chessmaster.model.GameState;
import com.mehroof.chessmaster.view.StatusBar;
import com.mehroof.chessmaster.pieces.Queen;
import com.mehroof.chessmaster.view.GameOverDialog;
import com.mehroof.chessmaster.pieces.King;
import com.mehroof.chessmaster.model.MoveRecord;
import com.mehroof.chessmaster.pieces.Pawn;
import com.mehroof.chessmaster.ai.ChessAI;
import com.mehroof.chessmaster.ai.AIMove;

public class GameController {

    private ChessBoard board;

    private ChessSquare selectedSquare;
    private BoardState boardState;
    private List<Move> legalMoves;
    private GameState gameState;
    private StatusBar statusBar;
    private ChessAI ai = new ChessAI();

    public GameController(
            ChessBoard board,
            StatusBar statusBar
    ){

    	this.board = board;
    	this.statusBar = statusBar;
        
        boardState = new BoardState(board.getSquares());
        
        gameState = new GameState();
        
        System.out.println("White check: " + isKingInCheck(true));
        System.out.println("Black check: " + isKingInCheck(false));

        System.out.println("White mate: " + isCheckmate(true));
        System.out.println("Black mate: " + isCheckmate(false));

        initializeBoard();
    }

    private void initializeBoard() {

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                ChessSquare square = board.getSquare(row, column);

                square.setOnMouseClicked(event -> {

                    selectSquare(square);

                });
            }
        }
    }

    private void selectSquare(ChessSquare square) {

    	// First click: select a piece
    	if (selectedSquare == null) {

    	    clearHighlights();

    	    Piece piece = square.getPiece();

    	    if (piece == null) {
    	        return;
    	    }

    	    if (piece.isWhite() && gameState.getCurrentTurn() != Turn.WHITE) {
    	        return;
    	    }

    	    if (!piece.isWhite() && gameState.getCurrentTurn() != Turn.BLACK) {
    	        return;
    	    }

    	    legalMoves = piece.generateMoves(
    	            boardState,
    	            square.getRow(),
    	            square.getColumn()
    	    );
    	    
    	    addEnPassantMove(square, legalMoves);
    	    
    	    legalMoves = filterIllegalMoves(square, legalMoves);

    	    selectedSquare = square;

    	    selectedSquare.select();

    	    highlightLegalMoves();

    	    System.out.println("Legal Moves:");

    	    for (Move move : legalMoves) {

    	        System.out.println(
    	                move.getRow() + ", " + move.getColumn()
    	        );

    	    }

    	    return;
    	}
    	
    	Piece piece = square.getPiece();

    	if (piece != null
    	        && piece.isWhite() == (gameState.getCurrentTurn() == Turn.WHITE)) {

    	    clearHighlights();

    	    selectedSquare.deselect();

    	    selectedSquare = null;

    	    selectSquare(square);

    	    return;
    	}

    	if (isLegalMove(square)
    	        && isMoveSafe(selectedSquare, square)) {

    	    executeMove(square);

    	    return;
    	}
    	// If the click wasn't a legal move, cancel the selection.
    	clearHighlights();
    	selectedSquare.deselect();
    	selectedSquare = null;
    }
    
    private void executeMove(
            ChessSquare destination) {

        Piece movingPiece = selectedSquare.getPiece();

        boolean moved =
                board.movePiece(
                        selectedSquare,
                        destination
                );

        if (!moved) {
            return;
        }

        gameState.setLastMove(
                new MoveRecord(
                        movingPiece,
                        selectedSquare.getRow(),
                        selectedSquare.getColumn(),
                        destination.getRow(),
                        destination.getColumn()
                )
        );

        clearHighlights();

        selectedSquare.deselect();

        boardState = new BoardState(board.getSquares());

        gameState.switchTurn();

        if (gameState.getCurrentTurn() == Turn.BLACK) {

            makeAIMove();

        }

        updateGameStatus();

        selectedSquare = null;

    }
    
    private boolean isLegalMove(ChessSquare square) {

        for (Move move : legalMoves) {

            if (move.getRow() == square.getRow()
                    && move.getColumn() == square.getColumn()) {

                return true;

            }

        }

        return false;

    }
    
    private void highlightLegalMoves() {

        for (Move move : legalMoves) {

            ChessSquare square = board.getSquare(
                    move.getRow(),
                    move.getColumn()
            );

            square.highlightMove();

        }

    }
    
    private void clearHighlights() {

        if (legalMoves == null) {
            return;
        }

        for (Move move : legalMoves) {

            ChessSquare square = board.getSquare(
                    move.getRow(),
                    move.getColumn()
            );

            square.removeHighlight();

        }

    }
    
    private boolean isKingInCheck(boolean white) {

        return isKingInCheck(boardState, white);

    }
    
    private boolean isKingInCheck(
            BoardState state,
            boolean white) {

        ChessSquare kingSquare = state.findKing(white);

        if (kingSquare == null) {
            return false;
        }

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                ChessSquare square = state.getSquare(row, column);

                Piece piece = square.getPiece();

                if (piece == null) {
                    continue;
                }

                if (piece.isWhite() == white) {
                    continue;
                }

                List<Move> moves =
                        piece.generateMoves(state, row, column);

                for (Move move : moves) {

                    if (move.getRow() == kingSquare.getRow()
                            && move.getColumn() == kingSquare.getColumn()) {
                    	
                    	System.out.println(
                    		    piece.getClass().getSimpleName()
                    		    + " at "
                    		    + row + "," + column
                    		    + " attacks king at "
                    		    + kingSquare.getRow() + "," + kingSquare.getColumn()
                    		);

                        return true;

                    }
                }
            }
        }

        return false;
    }
    
    private boolean isMoveSafe(
            ChessSquare from,
            ChessSquare to) {

        BoardState copy = boardState.copy();
        
        System.out.println("Board contents:");

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                Piece p = copy.getSquare(r, c).getPiece();

                if (p != null) {
                    System.out.println(
                        p.getClass().getSimpleName()
                        + " "
                        + (p.isWhite() ? "White" : "Black")
                        + " at "
                        + r + "," + c
                    );
                }
            }
        }

        Piece movingPiece =
                copy.getSquare(
                        from.getRow(),
                        from.getColumn()
                ).getPiece();
        
        System.out.println("--------------------");
        System.out.println("Testing " + movingPiece.getClass().getSimpleName());
        System.out.println("From: " + from.getRow() + "," + from.getColumn());
        System.out.println("To: " + to.getRow() + "," + to.getColumn());

        copy.movePiece(
                from.getRow(),
                from.getColumn(),
                to.getRow(),
                to.getColumn()
        );
        
        ChessSquare king = copy.findKing(movingPiece.isWhite());

        System.out.println(
            "King after move = "
            + king.getRow() + "," + king.getColumn()
        );

        System.out.println("Testing move:");
        System.out.println(
                movingPiece.getSymbol()
                + " "
                + from.getRow() + "," + from.getColumn()
                + " -> "
                + to.getRow() + "," + to.getColumn()
        );

        for (int r = 0; r < 8; r++) {

            for (int c = 0; c < 8; c++) {

                Piece p = copy.getSquare(r,c).getPiece();

                if (p == null)
                    System.out.print(". ");
                else
                    System.out.print(p.getSymbol() + " ");
            }

            System.out.println();
        }

        System.out.println("----------------");

        boolean result = !isKingInCheck(copy, movingPiece.isWhite());

        System.out.println("SAFE RESULT = " + result);

        return result;
    }
    
    private boolean isSquareAttacked(
            int row,
            int column,
            boolean white) {

        for (int r = 0; r < 8; r++) {

            for (int c = 0; c < 8; c++) {

                Piece piece = boardState.getSquare(r, c).getPiece();

                if (piece == null) {
                    continue;
                }

                if (piece.isWhite() == white) {
                    continue;
                }

                List<Move> moves =
                        piece.generateMoves(boardState, r, c);

                for (Move move : moves) {

                    if (move.getRow() == row
                            && move.getColumn() == column) {

                        return true;

                    }
                }
            }
        }

        return false;
    }
    
    private boolean hasLegalMove(boolean white) {

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                ChessSquare square = board.getSquare(row, column);

                Piece piece = square.getPiece();

                if (piece == null) {
                    continue;
                }

                System.out.println(
                    piece.getClass().getSimpleName()
                    + " at "
                    + row + "," + column
                );

                if (piece.isWhite() != white) {
                	
                    continue;
                   
                }
                
                List<Move> moves = piece.generateMoves(
                        boardState,
                        row,
                        column
                );
                
                addEnPassantMove(square, moves);

                moves = filterIllegalMoves(square, moves);
               
                if (piece instanceof Queen) {

                    System.out.println(
                            "Queen at "
                            + row + "," + column
                    );

                    for (Move move : moves) {
                    	
                    	System.out.println(
                    		    "Trying "
                    		    + piece.getClass().getSimpleName()
                    		    + " -> "
                    		    + move.getRow()
                    		    + ","
                    		    + move.getColumn()
                    		);

                        System.out.println(
                                " -> "
                                + move.getRow()
                                + ","
                                + move.getColumn()
                        );
                    }
                }
                
                for (Move move : moves) {

                	ChessSquare destination =
                	        boardState.getSquare(
                	            move.getRow(),
                	            move.getColumn()
                	        );

                    boolean safe = isMoveSafe(square, destination);

                    System.out.println(
                    	    "Move "
                    	    + move.getRow() + "," + move.getColumn()
                    	    + " safe = "
                    	    + safe
                    	);
                    
                    System.out.println(
                            piece.getSymbol()
                            + " -> "
                            + move.getRow()
                            + ", "
                            + move.getColumn()
                            + " safe = "
                            + safe
                    );

                    if (safe) {

                        System.out.println("LEGAL MOVE FOUND!");
                        System.out.println("Piece = " + piece.getClass().getSimpleName());
                        System.out.println("From = " + row + ", " + column);
                        System.out.println("To = " + move.getRow() + ", " + move.getColumn());

                        return true;

                    }
                }

            }

        }

        return false;

    }
    
    private boolean isCheckmate(boolean white) {

        boolean check = isKingInCheck(white);
        boolean legal = hasLegalMove(white);

        System.out.println("Checking " + (white ? "WHITE" : "BLACK"));
        System.out.println("Check = " + check);
        System.out.println("Legal = " + legal);

        return check && !legal;
    }
    
    private boolean isStalemate(boolean white) {

        return !isKingInCheck(white)
                && !hasLegalMove(white);

    }
    
    private List<Move> filterIllegalMoves(
            ChessSquare fromSquare,
            List<Move> moves) {

        List<Move> legal = new java.util.ArrayList<>();

        for (Move move : moves) {
        	
        	Piece piece = fromSquare.getPiece();

        	if (piece instanceof King
        	        && Math.abs(move.getColumn() - fromSquare.getColumn()) == 2) {

        	    // King cannot castle while already in check
        	    if (isKingInCheck(boardState, piece.isWhite())) {
        	        continue;
        	    }
        	}
        	BoardState copy = boardState.copy();

        	copy.movePiece(
        	        fromSquare.getRow(),
        	        fromSquare.getColumn(),
        	        move.getRow(),
        	        move.getColumn()
        	);

        	// Extra castling validation
        	if (piece instanceof King
        	        && Math.abs(move.getColumn() - fromSquare.getColumn()) == 2) {

        	    int middleColumn =
        	            (fromSquare.getColumn() + move.getColumn()) / 2;

        	    if (isSquareAttacked(
        	            fromSquare.getRow(),
        	            middleColumn,
        	            piece.isWhite())) {

        	        continue;
        	    }
        	}

        	if (!isKingInCheck(copy, piece.isWhite())) {

        	    legal.add(move);

        	}
        }

        return legal;
    }
    
    private void addEnPassantMove(
            ChessSquare square,
            List<Move> legalMoves) {

    	Piece currentPawn = square.getPiece();

        if (!(currentPawn instanceof Pawn)) {
            return;
        }

        MoveRecord lastMove = gameState.getLastMove();

        if (lastMove == null) {
            return;
        }

        Piece lastPiece = lastMove.getPiece();

        if (!(lastPiece instanceof Pawn)) {
            return;
        }

        int movedDistance =
                Math.abs(lastMove.getToRow() - lastMove.getFromRow());

        if (movedDistance != 2) {
            return;
        }

        if (lastPiece.isWhite() == currentPawn.isWhite()) {
            return;
        }

        if (lastMove.getToRow() != square.getRow()) {
            return;
        }

        if (Math.abs(lastMove.getToColumn() - square.getColumn()) != 1) {
            return;
        }

        int direction = currentPawn.isWhite() ? -1 : 1;

        legalMoves.add(
                new Move(
                        square.getRow() + direction,
                        lastMove.getToColumn()
                )
        );
        
    }
    
    private void makeAIMove() {

        AIMove aiMove = ai.chooseMove(board);

        if (aiMove == null) {
            return;
        }

        ChessSquare from =
                board.getSquare(
                        aiMove.getFromRow(),
                        aiMove.getFromColumn()
                );

        ChessSquare to =
                board.getSquare(
                        aiMove.getToRow(),
                        aiMove.getToColumn()
                );

        boolean moved = board.movePiece(from, to);

        if (!moved) {
            return;
        }

        boardState = board.getBoardState();

        gameState.switchTurn();

        if (gameState.getCurrentTurn() == Turn.WHITE) {

            statusBar.showWhiteTurn();

        } else {

            statusBar.showBlackTurn();

        }

    }
    
 

    private void updateGameStatus() {

    	    checkWhiteGameState();

    	    checkBlackGameState();

    	    updateTurnDisplay();

    	}

    
    private void checkWhiteGameState() {

        if (isCheckmate(true)) {

            GameOverDialog.show(
                    "Checkmate",
                    "Black Wins!"
            );

        } else if (isStalemate(true)) {

            GameOverDialog.show(
                    "Draw",
                    "Stalemate!"
            );

        } else if (isKingInCheck(true)) {

            System.out.println("White King is in CHECK!");

        }

    }
    
    private void checkBlackGameState() {

        if (isCheckmate(false)) {

            GameOverDialog.show(
                    "Checkmate",
                    "White Wins!"
            );

        } else if (isStalemate(false)) {

            GameOverDialog.show(
                    "Draw",
                    "Stalemate!"
            );

        } else if (isKingInCheck(false)) {

            System.out.println("Black King is in CHECK!");

        }

    }
    
    private void updateTurnDisplay() {

        if (gameState.getCurrentTurn() == Turn.WHITE) {

            statusBar.showWhiteTurn();

        } else {

            statusBar.showBlackTurn();

        }

    }
    
}