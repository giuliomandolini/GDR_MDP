package it.unicam.cs.mpgc.rpg130397;

import it.unicam.cs.mpgc.rpg130397.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 800;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setWidth(SCREEN_WIDTH);
        stage.setHeight(SCREEN_HEIGHT);
        stage.setResizable(false);

        SceneManager.setStage(stage);
        SceneManager.loadScene("menu");

        stage.show();
    }

}