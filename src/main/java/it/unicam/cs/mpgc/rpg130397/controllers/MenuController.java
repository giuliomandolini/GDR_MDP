package it.unicam.cs.mpgc.rpg130397.controllers;

import it.unicam.cs.mpgc.rpg130397.utils.SceneManager;
import javafx.fxml.FXML;

import java.io.IOException;
/// Controller for the menu scene and the "how to play" scene. Uses the utility class SceneManager to load the scene by name.
public class MenuController {
    @FXML
    private void playButtonPressed() throws IOException {
        SceneManager.loadScene("game");
    }
    @FXML
    private void quitButtonPressed()
    {
        System.exit(0);
    }

    @FXML
    private void howToPlayButtonPressed() throws IOException {
        SceneManager.loadScene("how to play");
    }

    @FXML
    private void backButtonPressed() throws IOException {
        SceneManager.loadScene("menu");
    }

}