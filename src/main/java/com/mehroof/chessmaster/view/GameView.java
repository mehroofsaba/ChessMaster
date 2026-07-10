package com.mehroof.chessmaster.view;

import com.mehroof.chessmaster.board.BoardFrame;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class GameView {

	public Scene createScene() {

	    BoardFrame boardFrame = new BoardFrame();

	    BorderPane root = new BorderPane();

	    StackPane centerPane = new StackPane(boardFrame);

	    root.setCenter(centerPane);

	    Scene scene = new Scene(root, 900, 700);

	    scene.getStylesheets().add(
	            getClass().getResource("/css/style.css").toExternalForm()
	    );

	    return scene;
	}
}