package it.unicam.cs.mpgc.rpg130397.controllers;

import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import it.unicam.cs.mpgc.rpg130397.gamelogic.JDeserializer;
import it.unicam.cs.mpgc.rpg130397.gamelogic.SceneManager;
import javafx.fxml.FXML;

import java.io.IOException;

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