package it.unicam.cs.mpgc.rpg130397.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage stage;

    /// Used to load scenes by name. Use only the scene name without ".fxml" or path
    public static void loadScene(String scene) throws IOException {
        Parent root = FXMLLoader.load(SceneManager.class.getResource("/fxml/"+scene+".fxml"));
        stage.setScene(new Scene(root));
    }

    public static void setStage(Stage stage) {
        SceneManager.stage = stage;
    }
}
