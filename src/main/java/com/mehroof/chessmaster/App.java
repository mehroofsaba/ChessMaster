package com.mehroof.chessmaster;

import com.mehroof.chessmaster.view.MainMenu;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("ChessMaster");

        MainMenu menu = new MainMenu(stage);

        stage.setScene(menu.createScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}