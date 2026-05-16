package it.unicam.cs.mpgc.rpg130397.controllers;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import it.unicam.cs.mpgc.rpg130397.gamelogic.JDeserializer;
import it.unicam.cs.mpgc.rpg130397.views.EnemyView;
import it.unicam.cs.mpgc.rpg130397.views.PlayerView;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
        Player playerModel = new Player("Player", 10, 10, JDeserializer.getPreviousInventory(data), new Position());
        PlayerView player = new PlayerView(playerModel);
        Weapon w = new Weapon("Fireball");

        player.setLayoutX(10);
        player.setLayoutY(10);

        Enemy skeletonModel = GameData.getEnemy("Skeleton Warrior");
        EnemyView skeleton = new EnemyView(skeletonModel);
        add(skeleton);
        add(player);
    }

    public void checkForBullets()
    {

    }

    public void add(Node object)
    {
        gamePane.getChildren().add(object);
    }

}