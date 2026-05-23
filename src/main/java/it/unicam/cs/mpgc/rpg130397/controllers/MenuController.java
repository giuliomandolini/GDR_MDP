package it.unicam.cs.mpgc.rpg130397.controllers;

import it.unicam.cs.mpgc.rpg130397.utils.SceneManager;
import javafx.fxml.FXML;

import java.io.IOException;

/// Controller for the menu scene.
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

}