package it.unicam.cs.mpgc.rpg130397.controllers;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.EnemyModel;
import it.unicam.cs.mpgc.rpg130397.elements.entities.PlayerModel;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import it.unicam.cs.mpgc.rpg130397.gamelogic.JDeserializer;
import it.unicam.cs.mpgc.rpg130397.views.EnemyView;
import it.unicam.cs.mpgc.rpg130397.views.PlayerView;
import it.unicam.cs.mpgc.rpg130397.views.GameObjectView;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;

import static it.unicam.cs.mpgc.rpg130397.gamelogic.GameData.getEnemiesMap;

public class GameController {

    GameData data;

    @FXML
    private ProgressBar healthBar;
    @FXML
    private AnchorPane gamePane;

    @FXML
    public void initialize() throws FileNotFoundException {
        data = new GameData();
        System.out.println(getEnemiesMap());
        PlayerModel playerModel = new PlayerModel("Player", 10, 10, JDeserializer.getPreviousInventory(data), new Position());
        PlayerView player = new PlayerView(playerModel);

        player.setLayoutX(10);
        player.setLayoutY(10);

        EnemyModel skeletonModel = GameData.getEnemy("Skeleton Warrior");
        EnemyView skeleton = new EnemyView(skeletonModel);
        add(skeleton);
        add(player);
    }

    public void add(GameObjectView object)
    {
        gamePane.getChildren().add(object);
    }

}