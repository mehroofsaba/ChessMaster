package com.mehroof.chessmaster.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.mehroof.chessmaster.view.GameView;

public class MainMenu {

    private Stage stage;

    public MainMenu(Stage stage) {
        this.stage = stage;
    }

    public Scene createScene() {
    	Label logo = new Label("♚");
    	logo.getStyleClass().add("logo");
        Label title = new Label("♚ ChessMaster ♔");
        title.getStyleClass().add("title");

        Label subtitle = new Label("Strategy. Precision. Victory.");
        subtitle.getStyleClass().add("subtitle");

        Button newGame = new Button("♟ New Game");
        newGame.setOnAction(e -> {

            GameView gameView = new GameView();

            stage.setScene(gameView.createScene());

        });
        Button aiGame = new Button("🤖 Play vs AI");
        Button multiplayer = new Button("🌐 Multiplayer");
        Button settings = new Button("⚙ Settings");
        Button exit = new Button("🚪 Exit");

        newGame.getStyleClass().add("menu-button");
        aiGame.getStyleClass().add("menu-button");
        multiplayer.getStyleClass().add("menu-button");
        settings.getStyleClass().add("menu-button");
        exit.getStyleClass().add("menu-button");

        VBox root = new VBox(20);

        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
        		logo,
                title,
                subtitle,
                newGame,
                aiGame,
                multiplayer,
                settings,
                exit
        );

        Scene scene = new Scene(root, 900, 700);

        scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
        );

        return scene;
    }
}