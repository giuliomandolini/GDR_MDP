package it.unicam.cs.mpgc.rpg130397.controllers;

import it.unicam.cs.mpgc.rpg130397.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

/// Controller for the menu scene and the "how to play" scene. Uses the utility class SceneManager to load the scene by name.
public class MenuController {
    @FXML
    private void playButtonPressed() throws IOException {
        SceneManager.loadScene("game");
    }
    @FXML
    private void quitButtonPressed()
    {
        //Seen at tutoring. Creates a new confirmation window
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to close the game?");
        //Waits until the user inputs something
        Optional<ButtonType> result = confirm.showAndWait();
        //if the result is the OK button, the application closes
        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            System.exit(0);
        }
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