package com.mehroof.chessmaster.board;

import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import com.mehroof.chessmaster.pieces.*;
import com.mehroof.chessmaster.dialog.PromotionDialog;
import com.mehroof.chessmaster.model.BoardState;

public class ChessBoard extends GridPane {
	private ChessSquare[][] squares;

    public ChessBoard() {

    	squares = new ChessSquare[BoardConstants.BOARD_SIZE][BoardConstants.BOARD_SIZE];
    	
        setHgap(0);
        setVgap(0);
        
        setAlignment(Pos.CENTER);

        createBoard();
        
        setupPieces();
        
        setPrefSize(640, 640);
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);

        setStyle(
                "-fx-border-color: black;" +
                "-fx-border-width: 3;"
        );
    }
    
    public ChessBoard(BoardState boardState) {

        this.squares = new ChessSquare[8][8];

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                this.squares[row][column] =
                        boardState.getSquare(row, column);

            }

        }

    }

    private void createBoard() {
    	
    	

    	for(int row=0; row<BoardConstants.BOARD_SIZE; row++) {

    		for(int column=0; column<BoardConstants.BOARD_SIZE; column++) {

    			ChessSquare square = new ChessSquare(row, column);

    			squares[row][column] = square;

    			add(square, column, row);
            }
        }
    }
    
    private void setupPieces() {

        // Black major pieces
        getSquare(0,0).setPiece(new Rook(false));
        getSquare(0,1).setPiece(new Knight(false));
        getSquare(0,2).setPiece(new Bishop(false));
        getSquare(0,3).setPiece(new Queen(false));
        getSquare(0,4).setPiece(new King(false));
        getSquare(0,5).setPiece(new Bishop(false));
        getSquare(0,6).setPiece(new Knight(false));
        getSquare(0,7).setPiece(new Rook(false));

        // Black pawns
        for (int column = 0; column < BoardConstants.BOARD_SIZE; column++) {
            getSquare(1, column).setPiece(new Pawn(false));
        }

        // White pawns
        for (int column = 0; column < BoardConstants.BOARD_SIZE; column++) {
            getSquare(6, column).setPiece(new Pawn(true));
        }

        // White major pieces
        getSquare(7,0).setPiece(new Rook(true));
        getSquare(7,1).setPiece(new Knight(true));
        getSquare(7,2).setPiece(new Bishop(true));
        getSquare(7,3).setPiece(new Queen(true));
        getSquare(7,4).setPiece(new King(true));
        getSquare(7,5).setPiece(new Bishop(true));
        getSquare(7,6).setPiece(new Knight(true));
        getSquare(7,7).setPiece(new Rook(true));
    }
    
    public ChessSquare getSquare(int row, int column) {

        return squares[row][column];

    }
    
    public BoardState getBoardState() {

        return new BoardState(squares);

    }
    
    public boolean movePiece(ChessSquare from, ChessSquare to) {
    	

        if (from.getPiece() == null) {
            return false;
        }

        if (to.getPiece() != null &&
            to.getPiece().isWhite() == from.getPiece().isWhite()) {

            return false;
        }
        
        Piece movingPiece = from.getPiece();
        
        Piece capturedPiece = to.getPiece();
        
     // En Passant
        if (movingPiece instanceof Pawn
                && capturedPiece == null
                && from.getColumn() != to.getColumn()) {

            ChessSquare capturedPawn =
                    getSquare(
                            from.getRow(),
                            to.getColumn()
                    );

            capturedPawn.setPiece(null);
        }

     // Castling
        if (movingPiece instanceof King
                && Math.abs(to.getColumn() - from.getColumn()) == 2) {

            // Kingside
           

            // Queenside
            
        }
        
        to.setPiece(movingPiece);
        
     // Castling
        if (movingPiece instanceof King) {

            int difference = to.getColumn() - from.getColumn();

            // Kingside
            if (difference == 2) {

                ChessSquare rookFrom = getSquare(from.getRow(), 7);
                ChessSquare rookTo = getSquare(from.getRow(), 5);

                rookTo.setPiece(rookFrom.getPiece());
                rookFrom.setPiece(null);

            }

            // Queenside
            else if (difference == -2) {

                ChessSquare rookFrom = getSquare(from.getRow(), 0);
                ChessSquare rookTo = getSquare(from.getRow(), 3);

                rookTo.setPiece(rookFrom.getPiece());
                rookFrom.setPiece(null);

            }
        }
        
        movingPiece.setHasMoved(true);
        
       

     // Pawn Promotion
     // Pawn Promotion
        if (to.getPiece() instanceof Pawn) {

            if (to.getRow() == 0 || to.getRow() == 7) {

                boolean white = to.getPiece().isWhite();

                String choice = PromotionDialog.show();

                switch (choice) {

                case "Queen":
                    to.setPiece(new Queen(white));
                    break;

                case "Rook":
                    to.setPiece(new Rook(white));
                    break;

                case "Bishop":
                    to.setPiece(new Bishop(white));
                    break;

                case "Knight":
                    to.setPiece(new Knight(white));
                    break;

                default:
                    to.setPiece(new Queen(white));
                    break;
                }
            }
        }

     from.setPiece(null);

        return true;

    }
    
    public void undoMove(
            ChessSquare from,
            ChessSquare to,
            Piece capturedPiece
    ) {

        from.setPiece(to.getPiece());

        to.setPiece(capturedPiece);

    }
    
    public ChessSquare[][] getSquares() {

        return squares;

    }
}