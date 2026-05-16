package it.unicam.cs.mpgc.rpg130397.controllers;

import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import it.unicam.cs.mpgc.rpg130397.gamelogic.JDeserializer;
import it.unicam.cs.mpgc.rpg130397.gamelogic.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.io.IOException;

public class GameController {
    @FXML
    private ProgressBar healthBar;
    @FXML
    private AnchorPane gamePane;

    @FXML
    public void initialize() throws FileNotFoundException {
        GameData data = new GameData();
        Player player = new Player("Player", 10, 10, JDeserializer.getPreviousInventory(data));

        player.setLayoutX(10);
        player.setLayoutY(10);
        add(player);
    }

    public void add(GameObject object)
    {
        gamePane.getChildren().add(object);
    }

}